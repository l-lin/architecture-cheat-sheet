package main

import (
	"encoding/json"
	"io/ioutil"
	"net/http"
)

const modernCatURL = "http://modern_cat:3000/cats"

// ModernCat represents the DTO from the modern app
type ModernCat struct {
	Name  string `json:"name"`
	Color string `json:"color"`
}

func getModernCats() []ModernCat {
	var modernCats []ModernCat
	resp, _ := http.Get(modernCatURL)
	defer resp.Body.Close()
	body, _ := ioutil.ReadAll(resp.Body)
	json.Unmarshal(body, &modernCats)
	return modernCats
}

func findModernCat(cats []ModernCat, name string) *ModernCat {
	for _, cat := range cats {
		if name == cat.Name {
			return &cat
		}
	}
	return nil
}
