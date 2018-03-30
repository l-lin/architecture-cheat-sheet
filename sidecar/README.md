# Sidecar  pattern

> How to enhance your service with 3rd party apps/services

![sidecar pattern](sidecar.png)

## Example

We have the following applications:

- One app exposing a REST endpoint to fetch `cats`:
  - It is exposed at port 3001
  - It contains 1 endpoint: http://localhost:3000/cats
- A frontal service:
  - It is exposed at port 80
  - It contains an index.html at: http://localhost/index.html
  - This service serves static file content that calls the cat app
- A Zookeeper
- A tiny process:
  - It register the cat app to the zookeeper

## Running the example

```bash
docker-compose up
# Calling this url will call the cat service
curl -L http://localhost:3000/cats

# Go to the index.html served by the Nginx with your favorite web browser
google-chrome http://localhost
firefox http://localhost
# The cat service can continue to live without the Nginx
# The Nginx is only used to facilitate the call to the cat service

# Open a TTY to the zookeeper container
docker exec -it sidecar_zk_1 sh
# Connect to the zookeeper service
/zookeeper-3.4.11 $ ./bin/zkCli.sh -server localhost:2181
# This will display the content of the pat /
[zk: localhost:2181(CONNECTED) 0] ls /
[zookeeper, cat]
# This will display the info of the node /cat
[zk: localhost:2181(CONNECTED) 1] get /cat
{"zookeeperHosts":"zk","name":"cat","description":"Cat service"}
cZxid = 0x2
ctime = Fri Mar 30 07:50:59 GMT 2018
mZxid = 0x2
mtime = Fri Mar 30 07:50:59 GMT 2018
pZxid = 0x2
cversion = 0
dataVersion = 0
aclVersion = 0
ephemeralOwner = 0x0
dataLength = 64
numChildren = 0
# This tiny process is used as an external process as the cat service
# in order to register the service to zookeeper for app/services that do
# not support zookeeper natively
```

In this example, we have 2 sidecars for our cat service:

- a Nginx that serves static file content to facilitate calls to our cat service
- a process that register our service to zookeeper

We can also add other types of sidecars:

- a logstash to parse the cat service logs and do things with them (like emailing when there are erros)
- a watchdog service that monitor the cat service and do things accordingly (ie, restart the service if crashed)
- ...

## Sources

- [Microsoft Azure](https://docs.microsoft.com/en-us/azure/architecture/patterns/sidecar)
- [IBM](https://developer.ibm.com/code/2017/05/22/polyglot-microservices-and-the-sidecar-pattern/)
- [Kubernetes](http://blog.kubernetes.io/2015/06/the-distributed-system-toolkit-patterns.html)
