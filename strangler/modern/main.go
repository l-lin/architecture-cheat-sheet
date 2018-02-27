package main

import (
	"encoding/json"
	"log"
	"net/http"
)

func main() {
	http.Handle("/cats", http.HandlerFunc(GetCat))
	port := ":3000"
	log.Println("[INFO] Serving to port", port)
	err := http.ListenAndServe(port, nil)
	if err != nil {
		log.Fatal("ListenAndServe:", err)
	}
}

func GetCat(w http.ResponseWriter, req *http.Request) {
	w.Header().Set("Content-Type", "application/json; charset=UTF-8")
	w.WriteHeader(http.StatusOK)

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

	log.Printf("[INFO] Get %d cats", len(cats))
	if err := json.NewEncoder(w).Encode(cats); err != nil {
		panic(err)
	}
}

type Cat struct {
	Name string `json:"name"`
	Type string `json:"type"`
}
