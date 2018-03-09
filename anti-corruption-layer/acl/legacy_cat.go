package main

import (
	"encoding/json"
	"io/ioutil"
	"net/http"
)

const legacyCatURL = "http://legacy_cat:8080/cats"

// LegacyCat represents the DTO from the legacy app
type LegacyCat struct {
	Name    string `json:"name"`
	CatType string `json:"catType"`
	Weigth  int    `json:"weight"`
}

func getLegacyCats() []LegacyCat {
	var legacyCats []LegacyCat
	resp, _ := http.Get(legacyCatURL)
	defer resp.Body.Close()
	body, _ := ioutil.ReadAll(resp.Body)
	json.Unmarshal(body, &legacyCats)
	return legacyCats
}
