package main

import (
	"context"
	"encoding/json"
	"fmt"
	"io/ioutil"
	"log"
	"net/http"
	"time"

	pb "./dog"
	"github.com/gorilla/mux"
	"google.golang.org/grpc"
)

const (
	catServiceURL  = "http://cat_rest:3000/cats"
	dogServiceHost = "dog_grpc:3000"
)

func main() {
	router := mux.NewRouter().StrictSlash(true)
	router.HandleFunc("/cats", catHandler)
	router.HandleFunc("/dogs/{dogName}", dogHandler)
	port := ":3000"
	log.Println("[INFO] Serving to port", port)
	err := http.ListenAndServe(port, router)
	if err != nil {
		log.Fatal("ListenAndServe:", err)
	}
}

type Cat struct {
	Name  string `json:"name"`
	Color string `json:"color"`
}

func catHandler(w http.ResponseWriter, req *http.Request) {
	switch req.Method {
	case "GET":
		getCats(w, req)
	default:
		log.Printf("[WARN] Only GET method are supported. %s is not supported", req.Method)
		fmt.Fprintf(w, "Only GET method are supported")
	}
}

func dogHandler(w http.ResponseWriter, req *http.Request) {
	switch req.Method {
	case "GET":
		getDog(w, req)
	default:
		log.Printf("[WARN] Only GET method are supported. %s is not supported", req.Method)
		fmt.Fprintf(w, "Only GET method are supported")
	}
}

func getCats(w http.ResponseWriter, req *http.Request) {
	var cats []Cat
	resp, _ := http.Get(catServiceURL)
	defer resp.Body.Close()
	body, _ := ioutil.ReadAll(resp.Body)
	json.Unmarshal(body, &cats)

	log.Printf("[INFO] Fetched %d cats from cat app...", len(cats))
	write(w, http.StatusOK, cats)
}

func getDog(w http.ResponseWriter, req *http.Request) {
	vars := mux.Vars(req)
	dogName := vars["dogName"]

	// Set up a connection to the server.
	conn, err := grpc.Dial(dogServiceHost, grpc.WithInsecure())
	if err != nil {
		log.Fatalf("did not connect: %v", err)
	}
	defer conn.Close()
	c := pb.NewDogServiceClient(conn)

	// Contact the server and print out its response.
	ctx, cancel := context.WithTimeout(context.Background(), time.Second)
	defer cancel()
	r, err := c.GetDog(ctx, &pb.DogRequest{Name: dogName})
	if err != nil {
		log.Fatalf("could not fetch the dog %v: %v", dogName, err)
	}
	write(w, http.StatusOK, r)
}

func write(w http.ResponseWriter, status int, n interface{}) {
	w.Header().Set("Content-Type", "application/json; charset=UTF-8")
	w.WriteHeader(status)
	if n != nil {
		if err := json.NewEncoder(w).Encode(n); err != nil {
			panic(err)
		}
	}
}
