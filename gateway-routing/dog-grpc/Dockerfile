FROM golang:1.10-alpine as builder

# Install GIT needed for the project to download its dependencies
RUN apk update && \
    apk upgrade && \
    apk add git

# Download the dependencies
RUN go get -u google.golang.org/grpc && \
    go get -u github.com/golang/protobuf/protoc-gen-go

# Create a working directory
WORKDIR /opt/app/src

# Add source code
ADD . ./dog-grpc

ADD ./dog ./dog

# Change working directory
WORKDIR /opt/app/src/dog-grpc

# Run main.go
RUN go build -o dog-grpc .
# -----------
FROM alpine:3.7

WORKDIR /opt/app

# Fetch the executable
COPY --from=builder /opt/app/src/dog-grpc .

EXPOSE 3000

# Run the binary
ENTRYPOINT ["./dog-grpc"]
