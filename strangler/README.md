# Stranger pattern

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
