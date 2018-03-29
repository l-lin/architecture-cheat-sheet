package main

import (
	"context"
	"log"
	"math/rand"
	"net"

	pb "./dog"
	"google.golang.org/grpc"
	"google.golang.org/grpc/reflection"
)

var dogTypes = [...]string{"Labrador", "Afghan Hound", "Akita", "Doge"}

func main() {
	port := ":3000"
	lis, err := net.Listen("tcp", port)
	if err != nil {
		log.Fatalf("Failed to serve: %v", err)
	}

	s := grpc.NewServer()
	pb.RegisterDogServiceServer(s, &server{})
	// Register reflection service on gRPC server.
	reflection.Register(s)
	log.Println("[INFO] Serving dog gRPC to port", port)
	if err := s.Serve(lis); err != nil {
		log.Fatalf("failed to serve: %v", err)
	}
}

// server is used to implement dog.DogServiceServer
type server struct{}

// GetDog implements dog.DogServiceServer
func (s *server) GetDog(ctx context.Context, in *pb.DogRequest) (*pb.DogResponse, error) {
	dogType := dogTypes[randInt(0, len(dogTypes))]
	dog := pb.DogResponse{Name: in.Name, Type: dogType}
	log.Printf("[INFO] Got %v dogs", dog)
	return &dog, nil
}

func randInt(min, max int) int {
	return min + rand.Intn(max-min)
}
