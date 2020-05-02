# Flat project structure

> The fastest way to structure a project.

This project structure is quite straightforward: one single project and split the project into
abstraction functions by package:

```text
.
└── ...
    ├── config     # app configuration
    ├── controller # web layer
    ├── dao        # persistence layer interfaces
    │   └── impl   # persistence layer implementation
    ├── model      # data representations
    └── service    # business logic layer interfaces
        └── impl   # business logic layer implementation
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
can directly access to the `dao` classes, which can violate the layer scopes if one is not careful.
In this organisation, one may be lazy and will tend to create a GOD `service` class that depends on
everyone to perform some complex logic. In other words, this structure does not prevent using
anti-patterns.

This project structure tends to lend itself toward monolithic applications, and it's really not
recommended to continue using this pattern if the application starts to be big. Indeed, because each
package can be tightly coupled, it's difficult to scale the project.

Moreover, it's really difficult to extract part of the business logic to another component when
using this pattern, as classes tends to be tightly coupled.

