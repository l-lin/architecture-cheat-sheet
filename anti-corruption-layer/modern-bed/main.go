package main

import (
	"encoding/json"
	"fmt"
	"log"
	"math/rand"
	"net/http"

	"github.com/gorilla/mux"
)

func main() {
	router := mux.NewRouter().StrictSlash(true)
	router.HandleFunc("/cats/{catName}/bed", bedHandler)
	port := ":3000"
	log.Println("[INFO] Serving to port", port)
	err := http.ListenAndServe(port, router)
	if err != nil {
		log.Fatal("ListenAndServe:", err)
	}
}

var bedTypes = [...]string{"Cardbox", "Oval mat", "Cuddle cup"}

type Bed struct {
	Name    string `json:"name"`
	CatName string `json:"catName"`
}

func bedHandler(w http.ResponseWriter, req *http.Request) {
	switch req.Method {
	case "GET":
		getBed(w, req)
	default:
		log.Printf("[WARN] Only GET method are supported. %s is not supported", req.Method)
		fmt.Fprintf(w, "Only GET method are supported")
	}
}

func getBed(w http.ResponseWriter, req *http.Request) {
	vars := mux.Vars(req)
	catName := vars["catName"]
	bedType := bedTypes[randInt(0, 3)]
	log.Printf("[INFO] Got '%s' for cat '%s' from modern app", catName, bedType)
	write(w, http.StatusOK, Bed{
		Name:    bedType,
		CatName: catName,
	})
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

func randInt(min, max int) int {
	return min + rand.Intn(max-min)
}
