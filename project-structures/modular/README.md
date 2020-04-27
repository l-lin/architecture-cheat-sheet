# Modular project structure

> Group by business logic.

```text
.
├── app # module regrouping all modules
│   └── src
│       └── main
│           ├── java
│           │   └── lin
│           │       └── louis
│           │           └── app
│           └── resources
├── order # order module
│   └── src
│       └── main
│           ├── java
│           │   └── lin
│           │       └── louis
│           │           └── order
│           │               ├── config
│           │               ├── controller
│           │               ├── dao
│           │               │   └── memory
│           │               ├── model
│           │               └── service
│           │                   └── simple
│           └── resources
│               └── META-INF
└── pet # pet module
    └── src
        └── main
            ├── java
            │   └── lin
            │       └── louis
            │           └── pet
            │               ├── config
            │               ├── controller
            │               ├── dao
            │               │   └── memory
            │               ├── model
            │               └── service
            │                   └── simple
            └── resources
                └── META-INF
```

Each business logic has their own component. The content of each component can be independent from each
other. Here, I applied the `flat` pattern for each component, but we can apply different pattern.

## Considerations

- tightly coupled to framework
- beware of circular dependencies
  - e.g. the `order` module depends on the `pet` module

