# Architecture cheat sheet

> This project is only used for my own learning purpose. The infographics are greatly inspired by the sources (especially from [Microsoft cloud design patterns](https://docs.microsoft.com/en-us/azure/architecture/)).
> 
> The examples are used to illustrate the patterns, albeit in a really small scale.

| Name                                                   | Description                                                               |
| ------------------------------------------------------ | ------------------------------------------------------------------------- |
| [Anti-corruption layer pattern](anti-corruption-layer) | How to work around legacy apps                                            |
| [Circuit Breaker](circuit-breaker)                     | Handle failures that might take a variable amount of time to recover from |
| [Event sourcing](event-sourcing)                       | How to record states in a non-destructive way                             |
| [Gateway routing pattern](gateway-routing)             | Route requests to multiple services using a single endpoint               |
| [Retry pattern](retry)                                 | Handle transient failures to improve application stability                |
| [Sidecar pattern](sidecar)                             | How to enhance your service with 3rd party apps/services                  |
| [Strangler pattern](strangler)                         | How to migrate legacy apps efficiently                                    |
| [Throttling pattern](throttling)                       | Control the resource consumption by throttling them when limit is reached |
