package main

import (
	"log"
)

func main() {
	log.Printf("Starting app")

}

type Cat struct {
	Name string `json:"name"`
	Type string `json:"type"`
}

type JsonError struct {
	Code  int    `json:"code"`
	Error string `json:"error"`
}
