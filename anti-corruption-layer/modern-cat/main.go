package main

import (
	"encoding/json"
	"fmt"
	"log"
	"net/http"
)

func main() {
	http.HandleFunc("/cats", catHandler)
	port := ":3000"
	log.Println("[INFO] Serving to port", port)
	err := http.ListenAndServe(port, nil)
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

func getCats(w http.ResponseWriter, req *http.Request) {
	cats := []Cat{
		Cat{
			"Tony",
			"Orange",
		},
		Cat{
			"Tadar Sauce",
			"Black & White",
		},
		Cat{
			"Nyan cat",
			"Multi-color",
		},
	}

	log.Printf("[INFO] Got %d cats from modern app", len(cats))
	write(w, http.StatusOK, cats)
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
