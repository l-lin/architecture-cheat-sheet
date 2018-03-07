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
	legacyCats := getLegacyCats()
	log.Printf("[INFO] Fetched %d cats from legacy app...", len(legacyCats))
	modernCats := getModernCats()
	log.Printf("[INFO] Fetched %d cats from modern app...", len(modernCats))

	cats := make([]Cat, 0)

	for _, legacyCat := range legacyCats {
		modernCat := findModernCat(modernCats, legacyCat.Name)
		if modernCat == nil {
			log.Printf("[WARN] Could not find the cat with name '%s' from modern app", legacyCat.Name)
		} else {
			log.Printf("[INFO] Found cat with name '%s' in the modern app", legacyCat.Name)
			cats = append(cats, Cat{
				Name:  modernCat.Name,
				Type:  legacyCat.CatType,
				Color: modernCat.Color,
				Bed:   getBed(modernCat.Name),
			})
		}
	}

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
