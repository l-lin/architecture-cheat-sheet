package main

import (
	"encoding/json"
	"log"
	"net/http"

	"github.com/throttled/throttled"
	"github.com/throttled/throttled/store/memstore"
)

var myHandler = http.HandlerFunc(catHandler)

func main() {
	store, err := memstore.New(65536)
	if err != nil {
		log.Fatal(err)
	}
	quota := throttled.RateQuota{MaxRate: throttled.PerMin(5), MaxBurst: 5}
	rateLimiter, err := throttled.NewGCRARateLimiter(store, quota)
	if err != nil {
		log.Fatal(err)
	}
	httpRateLimiter := throttled.HTTPRateLimiter{
		RateLimiter: rateLimiter,
		VaryBy:      &throttled.VaryBy{Path: true},
	}

	port := ":3000"
	log.Println("[INFO] Serving to port", port)
	err = http.ListenAndServe(port, httpRateLimiter.RateLimit(myHandler))
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
