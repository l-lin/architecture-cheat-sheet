# Stranger pattern

> How to migrate legacy apps efficiently

![strangler pattern](strangler.png)

## Example

- One legacy app made as a Kotlin application with Spring Boot
  - It is exposed at port 8080
  - It contains 2 endpoints:
    - http://localhost:8080/cats
    - http://localhost:8080/dogs
- One modern app made with Golang
  - It is exposed at port 3000
  - It contains only one endpoint:
    - http://localhost:3000/cats
- A facade made with Nginx as the reverse proxy
  - It redirects the path `/cats` to the modern app
  - It redirects the rest to the legacy app

```bash
docker-compose up
# Calling this url will target the legacy app. Check the logs to verify the call.
curl -L http://localhost/dogs
# Calling this url will target the modern app. Check the logs to verify the call.
curl -L http://localhost/cats
```

## Sources

- [Stackoverflow](https://stackoverflow.com/questions/1118804/application-strangler-pattern-experiences-thoughts/13002712)
- [Paul Hammant](https://paulhammant.com/2013/07/14/legacy-application-strangulation-case-studies/)
- [Martin Fowler](https://www.martinfowler.com/bliki/StranglerApplication.html)
- [Michiel Rook](https://www.michielrook.nl/2016/11/strangler-pattern-practice/)
- [Microsoft Azure](https://docs.microsoft.com/en-us/azure/architecture/patterns/strangler)

