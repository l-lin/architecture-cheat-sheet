FROM golang:1.10-alpine as builder

# Create a working directory
WORKDIR /opt/app/src

# Add source code
COPY . .

# Run main.go
RUN go build -o gateway .
# -----------
FROM alpine:3.7

WORKDIR /opt/app

# Fetch the executable
COPY --from=builder /opt/app/src/gateway .

EXPOSE 3000

# Run the binary
ENTRYPOINT ["./gateway"]
