FROM golang:1.10-alpine as builder

# Create a working directory
WORKDIR /opt/app/src

# Add source code
ADD . .

# Run main.go
RUN go build -o cat .
# -----------
FROM alpine:3.7

WORKDIR /opt/app

# Add the executable file built previously
COPY --from=builder /opt/app/src/cat .

EXPOSE 3000

# Run the binary
ENTRYPOINT ["./cat"]

