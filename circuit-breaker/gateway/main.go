package main

import (
	"encoding/json"
	"fmt"
	"io"
	"io/ioutil"
	"log"
	"net/http"
	"strings"
	"time"
)

const (
	catServiceURL = "http://cat:3000/cats"
	maxCount      = 3
)

var (
	state        = "CLOSED"
	failureCount = 0
	successCount = 0
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
		circuitBreaker(w, req)
	} else {
		log.Printf("[WARN] Only POST method is supported. %s is not supported", req.Method)
		fmt.Fprintf(w, "Only POST method are supported")
	}
}

func circuitBreaker(w http.ResponseWriter, req *http.Request) {
	if state == "OPEN" {
		msg := "State is 'OPEN', no call to be performed in the cat service in order to avoid impacting the cat service performance"
		log.Printf("[WARNING] %s", msg)
		write(w, http.StatusServiceUnavailable, JsonError{http.StatusServiceUnavailable, msg})
		return
	}

	log.Printf("[INFO] Received request. State is '%s', failureCount is %d, successCount is %d", state, failureCount, successCount)

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

	url := fmt.Sprintf("%s?status=%s", catServiceURL, respStatus)
	resp, _ := http.Post(url, "application/json", strings.NewReader(cat.String()))

	if resp.StatusCode != http.StatusCreated {
		log.Printf("[WARNING] Received an error from cat service...")
		if state == "HALF_OPEN" {
			log.Printf("[WARNING] State was 'HALF_OPEN' and we got an error")
			setState("OPEN")
			go setStateToHalfOpen()
		} else {
			failureCount++
			if failureCount > maxCount {
				log.Printf("[WARNING] Failure thresold has been reached")
				setState("OPEN")
				go setStateToHalfOpen()
			} else {
				log.Printf("[INFO] Thresold still not reached: %d/%d => keeping state to '%s'", failureCount, maxCount, state)
			}
		}
		write(w, http.StatusGatewayTimeout, JsonError{http.StatusGatewayTimeout, "Could not save cat to cat service. Response status was: " + resp.Status})
	} else {
		log.Printf("[INFO] Managed to perform successfully in the cat service")
		if state == "HALF_OPEN" {
			successCount++
			if successCount > maxCount {
				log.Printf("[INFO] Success thresold has been reached")
				setState("CLOSED")
			}
		}
		log.Printf("[INFO] Saving cat '%s'", cat.Name)
		write(w, http.StatusCreated, cat)
	}
}

func setStateToHalfOpen() {
	time.Sleep(10 * time.Second)
	log.Printf("[INFO] Timeout reached")
	setState("HALF_OPEN")
}

func setState(newState string) {
	log.Printf("[INFO] Changing state: %s => %s", state, newState)
	state = newState
	successCount = 0
	failureCount = 0
}

func getResponseStatusFromRequest(req *http.Request) (string, *JsonError) {
	keys, ok := req.URL.Query()["status"]
	if !ok || len(keys[0]) < 1 {
		return "", &JsonError{Code: http.StatusBadRequest, Error: "Missing query parameter 'status'"}
	}

	return keys[0], nil
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
		return nil, &JsonError{Code: http.StatusUnprocessableEntity, Error: "Could not parse the given request body"}
	}
	return &cat, nil
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

func (c *Cat) String() string {
	b, err := json.Marshal(c)
	if err != nil {
		panic(err)
	}
	return string(b[:])
}
