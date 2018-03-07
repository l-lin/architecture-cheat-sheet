package main

// Cat represents the DTO to be displayed for the client
type Cat struct {
	Name  string `json:"name"`
	Type  string `json:"type"`
	Color string `json:"color"`
	Bed   Bed    `json:"bed"`
}
