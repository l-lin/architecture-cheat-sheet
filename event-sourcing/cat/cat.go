package main

import (
	"encoding/json"
)

type Cat struct {
	ID   int    `json:"id"`
	Name string `json:"name"`
	Type string `json:"type"`
}

func (c *Cat) String() string {
	out, _ := json.Marshal(c)
	return string(out)
}

func getCats() []Cat {
	return []Cat{
		Cat{
			1,
			"Tony",
			"Arabian Mau",
		},
		Cat{
			2,
			"Tadar Sauce",
			"Grumpy cat",
		},
		Cat{
			3,
			"Nyan cat",
			"Cat with Pop-Tart for a body, flying through space, and leaving a rainbow trail behind it",
		},
	}
}

func getCat(id int) *Cat {
	for _, cat := range getCats() {
		if cat.ID == id {
			return &cat
		}
	}
	return nil
}
