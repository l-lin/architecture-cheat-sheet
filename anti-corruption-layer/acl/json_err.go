package main

// JSONErr represents the error in JSON
type JSONErr struct {
	Code int    `json:"code"`
	Text string `json:"text"`
}
