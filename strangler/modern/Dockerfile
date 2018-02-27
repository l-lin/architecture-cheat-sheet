FROM golang:1.10-alpine

# Create a working directory
WORKDIR /opc/app/src

# Add source code
ADD . .

# Run main.go
ENTRYPOINT ["go", "run", "main.go"]
