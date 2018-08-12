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

type JsonError struct {
	Code  int    `json:"code"`
	Error string `json:"error"`
}

func catHandler(w http.ResponseWriter, req *http.Request) {
	if "POST" == req.Method {
		cat, err := readRequest(req)
		if err != nil {
			write(w, err.Code, err)
			return
		}
		respStatus, err := getResponseStatusFromRequest(req)
		if err != nil {
			write(w, err.Code, err)
			return
		}
		if respStatus == "SUCCESS" {
			log.Printf("[INFO] Returning a success response. Saving cat '%s'", cat.Name)
			write(w, http.StatusCreated, cat)
		} else {
			log.Printf("[INFO] Returning a failure response")
			write(w, http.StatusServiceUnavailable, &JsonError{http.StatusServiceUnavailable, "Service is not available"})
		}
	} else {
		log.Printf("[WARN] Only POST method is supported. %s is not supported", req.Method)
		fmt.Fprintf(w, "Only POST method are supported")
	}
}

func readRequest(req *http.Request) (*Cat, *JsonError) {
	body, err := ioutil.ReadAll(io.LimitReader(req.Body, 1048576))
	if err != nil {
		log.Printf("[ERROR] Could not read the body. Reason: %s", err.Error())
		return nil, &JsonError{Code: http.StatusInternalServerError, Error: "Could not read the body."}
	}
	if err := req.Body.Close(); err != nil {
		log.Printf("[ERROR] Could not close ready the body. Reason: %s", err.Error())
		return nil, &JsonError{Code: http.StatusInternalServerError, Error: "Could not close the body."}
	}
	var cat Cat
	if err := json.Unmarshal(body, &cat); err != nil {
		return nil, &JsonError{Code: http.StatusUnprocessableEntity, Error: "Could not parse the given parameter"}
	}
	return &cat, nil
}

func getResponseStatusFromRequest(req *http.Request) (string, *JsonError) {
	keys, ok := req.URL.Query()["status"]
	if !ok || len(keys[0]) < 1 {
		return "", &JsonError{Code: http.StatusBadRequest, Error: "Missing query parameter 'status'"}
	}

	return keys[0], nil
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
