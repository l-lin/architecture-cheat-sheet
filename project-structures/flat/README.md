# Flat project structure

> The fastest way to structure a project.

This project structure is quite straightforward: split the project into abstraction functions:

```text
.
└── src
    └── main
        └── java
            └── lin
                └── louis
                    ├── config     # app configuration
                    ├── controller # web layer
                    ├── dao        # persistence layer
                    │   └── memory # in memory persistence implementation
                    ├── model      # data representations
                    └── service    # business logic layer
                        └── simple # simple business logic implementation
```

## Pattern analysis

Each abstraction is put in their own package and is performing a specific role and responsibility
within the application (e.g. data representations or business logic).

It's an easy way to separate the classes. If we want to add new functionalities, we can add the
classes into the corresponding packages.

The package names are quite straightforward and understandable by most developers, as they are
named after the "n-tier architecture" pattern's layers, which is (or was) the de facto standard for
most Java EE applications, and therefore they are widely known by most developers.

This flat structure can make a good starting point to quickly start developing new web applications.

## Considerations

All classes are in `public` scope, hence everyone can use everyone, e.g. the `controller` package
can directly access to the `dao` classes, which can be a source of unmaintainable code.

This project structure tends to lend itself toward monolithic applications, and it's really not
recommended to continue using this pattern if the application starts to be big. Indeed, because each
package can be tightly coupled, it's difficult to scale the project.

Moreover, it's really difficult to extract part of the business logic to another component when
using this pattern, as classes tends to be tightly coupled (see
[OrderService](./src/main/java/lin/louis/flat/service/OrderService.java)).

