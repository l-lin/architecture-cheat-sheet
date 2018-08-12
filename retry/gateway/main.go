package main

import (
	"encoding/json"
	"errors"
	"io/ioutil"
	"log"
	"net/http"
	"strconv"
)

const catServiceURL = "http://cat:3000/cats"

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
	var cats []Cat
	nbRetry := 1
	maxRetry, err := getMaxRetry(req)
	if err != nil {
		write(w, http.StatusBadRequest, err.Error())
		return
	}
	for {
		resp, _ := http.Get(catServiceURL)
		defer resp.Body.Close()
		if resp.StatusCode == http.StatusOK {
			body, _ := ioutil.ReadAll(resp.Body)
			json.Unmarshal(body, &cats)

			log.Printf("[INFO] Fetched %d cats from cat app...", len(cats))
			write(w, http.StatusOK, cats)
			break
		}
		if nbRetry > maxRetry {
			write(w, http.StatusGatewayTimeout, JsonError{http.StatusGatewayTimeout, "Could not fetch cats from cat service. Response status was: " + resp.Status})
			break
		}
		log.Printf("[INFO] Could not fetch the data from cat service. Response was %s. Retrying %d/%d", resp.Status, nbRetry, maxRetry)
		nbRetry++
	}
}

func getMaxRetry(req *http.Request) (int, error) {
	keys, ok := req.URL.Query()["maxRetry"]
	if !ok || len(keys[0]) < 1 {
		return 0, errors.New("Missing query parameter 'maxRetry'")
	}

	maxRetry, err := strconv.Atoi(keys[0])
	if err != nil {
		return 0, errors.New("Query parameter 'maxRetry' must be an integer")
	}
	return maxRetry, nil
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
