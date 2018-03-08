package main

import (
	"encoding/json"
	"fmt"
	"log"
	"net/http"
)

func main() {
	http.HandleFunc("/dogs", dogsHandler)
	port := ":3000"
	log.Println("[INFO] Serving to port", port)
	err := http.ListenAndServe(port, nil)
	if err != nil {
		log.Fatal("ListenAndServe:", err)
	}
}

type Dog struct {
	Name string `json:"name"`
}

func dogsHandler(w http.ResponseWriter, req *http.Request) {
	switch req.Method {
	case "GET":
		getDogs(w, req)
	default:
		log.Printf("[WARN] Only GET method are supported. %s is not supported", req.Method)
		fmt.Fprintf(w, "Only GET method are supported")
	}
}

func getDogs(w http.ResponseWriter, req *http.Request) {
	dogs := []Dog{
		Dog{"Doge"},
		Dog{"Pluto"},
		Dog{"Ogie"},
		Dog{"Snoop"},
	}
	log.Printf("[INFO] Got %d dogs", len(dogs))
	write(w, http.StatusOK, dogs)
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
