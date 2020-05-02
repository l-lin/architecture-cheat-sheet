# Modular project structure

> Group by business logic.

```text
.
├── app                     # module regrouping all modules
│   └── ...                 #
│       └── app             #
├── order                   # order component
│   └── ...                 #
│       └── order           #
│           ├── config      # component configuration
│           ├── controller  # web layer
│           ├── dao         # persistence layer interfaces
│           │   └── impl    # persistence layer implementation
│           ├── model       # data representations
│           └── service     # business logic layer interfaces
│               └── impl    # business logic layer implementation
└── pet                     # pet component
    └── ...                 #
        └── pet             #
            ├── config      # component configuration
            ├── controller  # web layer
            ├── dao         # persistence layer interfaces
            │   └── impl    # persistence layer implementation
            ├── model       # data representations
            └── service     # business logic layer interfaces
                └── impl    # business logic layer implementation
```

Each business logic has their own component. The content of each component can be independent from each
other. Here, I applied the `flat` pattern for each component, but we can also apply different pattern.

## Pattern analysis

It's quite straightforward to extract part of the project into a micro-service as each component
represents a part of the business logic.

This type of structure can be considered as [screaming
architecture](https://blog.cleancoder.com/uncle-bob/2011/09/30/Screaming-Architecture.html) as we
immediately know what the component is all about.

## Considerations

Even if this organisation recommends having each component their structure, the external
dependencies are still common for all components.

In this example, I used Spring Framework to assemble the components and the [app](app) component
uses Spring Boot features to glue the components. Thus, it's quite tightly coupled to the framework,
which means if we want to switch to another, like VertX, it will be quite painful to do it.

