FROM golang:1.10-alpine as builder

# Install GIT needed for the project to download its dependencies
RUN apk update && \
    apk upgrade && \
    apk add git

# Download the dependencies
RUN go get -u github.com/samuel/go-zookeeper/zk

# Create a working directory
WORKDIR /opt/app/src

# Add source code
ADD . .

# Run main.go
RUN go build -o sidecar_zk .
# -----------
FROM alpine:3.7

WORKDIR /opt/app

# Add the executable file built previously
COPY --from=builder /opt/app/src/sidecar_zk .

# Add the agent.json to the working directory
COPY agent.json .

EXPOSE 3000

# Run the binary
ENTRYPOINT ["./sidecar_zk"]

