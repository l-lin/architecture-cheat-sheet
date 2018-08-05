package main

import (
	"encoding/json"
	"log"
	"net/http"
	"time"
)

const maxRetry = 5

var retryCount = 0

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
	Name string `json:"name"`
	Type string `json:"type"`
}

type JsonError struct {
	Code  int    `json:"code"`
	Error string `json:"error"`
}

func catHandler(w http.ResponseWriter, req *http.Request) {
	if retryCount <= maxRetry {
		log.Printf("[INFO] Simulating network failure...")
		time.Sleep(2 * time.Second)
		retryCount++
		write(w, http.StatusServiceUnavailable, JsonError{http.StatusServiceUnavailable, "Network failure..."})
		return
	}
	cats := []Cat{
		Cat{
			"Tony",
			"Arabian Mau",
		},
		Cat{
			"Tadar Sauce",
			"Grumpy cat",
		},
		Cat{
			"Nyan cat",
			"Cat with Pop-Tart for a body, flying through space, and leaving a rainbow trail behind it",
		},
	}

	log.Printf("[INFO] Got %d cats from modern app", len(cats))
	retryCount = 0
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
