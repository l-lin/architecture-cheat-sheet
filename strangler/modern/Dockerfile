FROM golang:1.10-alpine as builder

# Create a working directory
WORKDIR /opt/app/src

# Add source code
ADD . .

# Run main.go
RUN go build -o modern .
# -----------
FROM alpine:3.7

WORKDIR /opt/app

COPY --from=builder /opt/app/src/modern .

EXPOSE 3000

ENTRYPOINT ["./modern"]

