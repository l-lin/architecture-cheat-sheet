# Dog protobuf

Unfortunately, I had to duplicate the protobuf because I want to use docker-compose and
the dog folder must be in the same build context (otherwise we get the error 
`Forbidden path outside the build context`).

## Getting started

- Install [protobuf](https://github.com/google/protobuf/releases)
- Update the environment variable `PATH` to include the path to the protoc binary file

```bash
protoc -I . dog.proto --go_out=plugins=grpc:.
```
