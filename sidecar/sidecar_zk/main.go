package main

import (
	"encoding/json"
	"io/ioutil"
	"log"
	"strings"
	"time"

	"github.com/samuel/go-zookeeper/zk"
)

const fileName string = "./agent.json"

func main() {
	file, err := ioutil.ReadFile(fileName)
	if err != nil {
		log.Fatal(err)
	}
	var serviceConf ServiceConfig
	if json.Unmarshal(file, &serviceConf) != nil {
		log.Fatalf("[ERROR] Could not parse the json file: %v", err)
	}
	registerToZk(serviceConf)
}

// ServiceConfig represents the configuration
type ServiceConfig struct {
	ZookeeperHosts string `json:"zookeeperHosts"`
	Name           string `json:"name"`
	Description    string `json:"description"`
}

func registerToZk(serviceConfig ServiceConfig) {
	servers := strings.Split(serviceConfig.ZookeeperHosts, ",")
	descriptionJSON, err := json.Marshal(serviceConfig)
	if err != nil {
		log.Fatalf("[ERROR] Could not marshal the service description: %v", err)
	}

	c, _, err := zk.Connect(servers, time.Minute)
	if err != nil {
		log.Fatalf("[ERROR] Could not connect to zookeeper: %v", err)
	}
	path := "/" + serviceConfig.Name
	ephemeralPath, err := c.Create(path, descriptionJSON, 0, zk.WorldACL(zk.PermAll))
	if err != nil {
		log.Fatalf("[ERROR] Could not create the node: %v", err)
	}
	log.Printf("[INFO] Service '%s' registered to '%s'", serviceConfig.Name, ephemeralPath)
}
