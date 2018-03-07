FROM golang:1.10-alpine as builder

# Install GIT needed for the project to download its dependencies
RUN apk update && \
    apk upgrade && \
    apk add git

# Download the dependencies
RUN go get github.com/gorilla/mux

# Create a working directory
WORKDIR /opt/app/src

# Add source code
ADD . .

# Run main.go
RUN go build -o modern-bed .
# -----------
FROM alpine:3.7

WORKDIR /opt/app

# Fetch the executable
COPY --from=builder /opt/app/src/modern-bed .

EXPOSE 3000

# Run the binary
ENTRYPOINT ["./modern-bed"]

