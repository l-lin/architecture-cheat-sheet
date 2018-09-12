FROM golang:1.10-alpine as builder

# Create a working directory
WORKDIR /opt/app/src

# Add source code
COPY . .

# Run main.go
RUN go build -o app .
# -----------
FROM alpine:3.7

WORKDIR /opt/app

# Add the executable file built previously
COPY --from=builder /opt/app/src/app .

EXPOSE 3000

# Run the binary
ENTRYPOINT ["./app"]
