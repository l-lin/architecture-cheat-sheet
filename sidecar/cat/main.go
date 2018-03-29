package main

import (
	"encoding/json"
	"fmt"
	"io"
	"io/ioutil"
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
	Name string `json:"name"`
	Type string `json:"type"`
}

type JsonErr struct {
	Code int
	Text string
}

func catHandler(w http.ResponseWriter, req *http.Request) {
	switch req.Method {
	case "GET":
		getCats(w, req)
	case "POST":
		saveCat(w, req)
	case "PUT":
		saveCat(w, req)
	default:
		log.Printf("[WARN] Only GET, POST and PUT methods are supported. %s is not supported", req.Method)
		fmt.Fprintf(w, "Only GET, POST and PUT methods are supported")
	}
}

func getCats(w http.ResponseWriter, req *http.Request) {
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

func saveCat(w http.ResponseWriter, req *http.Request) {
	cat, err := readRequest(req)
	if err != nil {
		write(w, err.Code, err)
		return
	}
	log.Printf("[INFO] Saving cat '%s' in modern app", cat.Name)
	write(w, http.StatusCreated, cat)
}

func updateCat(w http.ResponseWriter, req *http.Request) {
	cat, err := readRequest(req)
	if err != nil {
		write(w, err.Code, err)
		return
	}
	log.Printf("[INFO] Updating cat '%s' in modern app", cat.Name)
	write(w, http.StatusCreated, cat)
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

func readRequest(req *http.Request) (*Cat, *JsonErr) {
	body, err := ioutil.ReadAll(io.LimitReader(req.Body, 1048576))
	if err != nil {
		log.Printf("[ERROR] Could not read the body. Reason: %s", err.Error())
		return nil, &JsonErr{Code: http.StatusInternalServerError, Text: "Could not read the body."}
	}
	if err := req.Body.Close(); err != nil {
		log.Printf("[ERROR] Could not close ready the body. Reason: %s", err.Error())
		return nil, &JsonErr{Code: http.StatusInternalServerError, Text: "Could not close the body."}
	}
	var cat Cat
	if err := json.Unmarshal(body, &cat); err != nil {
		return nil, &JsonErr{Code: 422, Text: "Could not parse the given parameter"}
	}
	return &cat, nil
}
