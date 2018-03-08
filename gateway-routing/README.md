# Gateway routing pattern

![Gateway routing](gateway_routing.png)

## Example

## Running the example

```bash
docker-compse up
# Calling this url will call the cat service
curl -L http://localhost/cats
# Calling this url will call the dog service
curl -L http://localhost/dogs
# The client does not need to know every service domain names
```

## Sources

- [Microsoft Azure](https://docs.microsoft.com/en-us/azure/architecture/patterns/gateway-routing)

