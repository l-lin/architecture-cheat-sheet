package main

import (
	"encoding/json"
	"fmt"
	"io"
	"io/ioutil"
	"log"
	"net/http"
	"strconv"

	"github.com/gorilla/mux"
)

func main() {
	router := mux.NewRouter().StrictSlash(true)
	router.HandleFunc("/cats", catHandler)
	router.HandleFunc("/cats/{id}/events", eventHandler)
	port := ":3000"
	log.Println("[INFO] Serving to port", port)
	err := http.ListenAndServe(port, router)
	if err != nil {
		log.Fatal("ListenAndServe:", err)
	}
}

type JSONErr struct {
	Code int
	Text string
}

func catHandler(w http.ResponseWriter, req *http.Request) {
	switch req.Method {
	case "GET":
		cats := getCats()
		log.Printf("[INFO] Got %d cats", len(cats))
		write(w, http.StatusOK, cats)
	default:
		msg := fmt.Sprintf("Only GET method is supported. %s is not supported", req.Method)
		log.Printf("[WARN] %s", msg)
		fmt.Fprintf(w, msg)
	}
}

func eventHandler(w http.ResponseWriter, req *http.Request) {
	vars := mux.Vars(req)
	idStr := vars["id"]
	id, _ := strconv.Atoi(idStr)
	cat := getCat(id)
	if cat == nil {
		write(w, http.StatusNotFound, &JSONErr{http.StatusNotFound, fmt.Sprintf("Could not find the cat with id %d", id)})
		return
	}
	switch req.Method {
	case "POST":
		event, _ := readRequest(req)
		event = saveEvent(event.Message, cat)
		write(w, http.StatusOK, event)
	default:
		msg := fmt.Sprintf("Only POST methods is supported. %s is not supported", req.Method)
		log.Printf("[WARN] %s", msg)
		fmt.Fprintf(w, msg)
	}
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

func readRequest(req *http.Request) (*Event, *JSONErr) {
	body, err := ioutil.ReadAll(io.LimitReader(req.Body, 1048576))
	if err != nil {
		log.Printf("[ERROR] Could not read the body. Reason: %s", err.Error())
		return nil, &JSONErr{Code: http.StatusInternalServerError, Text: "Could not read the body."}
	}
	if err := req.Body.Close(); err != nil {
		log.Printf("[ERROR] Could not close ready the body. Reason: %s", err.Error())
		return nil, &JSONErr{Code: http.StatusInternalServerError, Text: "Could not close the body."}
	}
	var event Event
	if err := json.Unmarshal(body, &event); err != nil {
		return nil, &JSONErr{Code: 422, Text: "Could not parse the given parameter"}
	}
	return &event, nil
}
