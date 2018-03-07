package main

import (
	"encoding/json"
	"fmt"
	"io/ioutil"
	"net/http"
)

const modernBedURL = "http://modern_bed:3000"

// Bed represents the bed used by a cat
type Bed struct {
	Name string `json:"name"`
}

func getBed(catName string) Bed {
	var bed Bed
	resp, _ := http.Get(fmt.Sprintf("http://modern_bed:3000/cats/%s/bed", catName))
	defer resp.Body.Close()
	body, _ := ioutil.ReadAll(resp.Body)
	json.Unmarshal(body, &bed)
	return bed
}
