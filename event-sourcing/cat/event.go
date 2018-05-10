package main

import (
	"encoding/json"
	"log"
	"time"

	"github.com/Shopify/sarama"
)

const (
	version    = "1.0.0"
	kafkaAddr  = "kafka:9092"
	kafkaTopic = "events"
)

type Event struct {
	Version string    `json:"version"`
	Time    time.Time `json:"time"`
	Message string    `json:"message"`
	Cat     *Cat      `json:"cat"`
}

func (e *Event) String() string {
	out, _ := json.Marshal(e)
	return string(out)
}

func saveEvent(message string, cat *Cat) *Event {
	log.Printf("[INFO] Append event '%s' to kafka", message)
	event := &Event{version, time.Now(), message, cat}
	sendMessage(event)
	return event
}

func sendMessage(event *Event) {
	producer, err := sarama.NewSyncProducer([]string{kafkaAddr}, nil)
	if err != nil {
		log.Fatalf("[ERROR] Could not create a new kafka producer. Error was: %v", err)
	}
	defer func() {
		if err := producer.Close(); err != nil {
			log.Fatalf("[ERROR] Could not close kafka producer. Error was: %v", err)
		}
	}()

	msg := &sarama.ProducerMessage{Topic: kafkaTopic, Value: sarama.StringEncoder(event.String())}
	partition, offset, err := producer.SendMessage(msg)
	if err != nil {
		log.Fatalf("[ERROR] FAILED to send message to kafka: %v", err)
	} else {
		log.Printf("[INFO] Message sent to kafka partition %d at offset %d", partition, offset)
	}
}
