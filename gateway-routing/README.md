# Gateway routing pattern

> Unified interface to services

![Gateway routing](gateway_routing.png)

## Example

We have the following applications:

- One app exposing a REST endpoint to fetch `cats`:
  - It is exposed at port 3001
  - It contains 1 endpoint: http://localhost:3001/cats
- Another app exposing a gRPC endpoint to fetch a `dog`:
  - It is exposed at port 3000

We want to get be able to fetch the information of both `cats` and `dog`
from REST endpoints.

## Running the example

```bash
docker-compse up
# Calling this url will call the cat service
curl -L http://localhost/cats
# Calling this url will call the dog service
curl -L http://localhost/dogs/snoopy
# The client does not need to know every service domain names
# and he is only using one "protocol"
```

## Sources

- [Microsoft Azure](https://docs.microsoft.com/en-us/azure/architecture/patterns/gateway-routing)
- [microservices.io](http://microservices.io/patterns/apigateway.html)
