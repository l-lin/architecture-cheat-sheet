# Layered project structure

> Most common architecture pattern 

The project structure follows the layered architecture pattern, otherwise known as the __n-tier
architecture pattern__. The project is split in N components that represent a horizontal layer that
performs a specific role within the application.

```text
.
├── persistence             # persistence layer
│   └── ...                 #
│       └── persistence     # contains the persistence layer interfaces
│           ├── memory      # memory implementation of the persistence layer
│           └── model       # model / entities of the project (useful for ORM)
├── domain                  # business layer
│   └── ...                 #
│       └── domain          # contains the business logic interfaces
│           └── simple      # simple implementation of the business logic
└── web                     # web layer
    └── ...                 #
        └── web             #
            ├── config      # app configuration
            ├── controller  # controllers to manage request inputs
            └── dto         # Data Transfer Objects used by the controllers
```

## Pattern analysis

Each abstraction is put in their own package and is performing a specific role and responsibility
within the application (e.g. data representations or business logic).

It's an easy way to separate the classes. If we want to add new functionalities, we can add the
classes into the corresponding packages.

The package names are quite straightforward and understandable by most developers, as they are
named after the "n-tier architecture" pattern's layers, which is (or was) the de facto standard for
most Java EE applications, and therefore they are widely known by most developers.

Compared to the [flat pattern](../flat), each layer is isolated and can only be accessible if the
component is dependent of one another, thus mitigating some silly mistakes, like having the
persistence layer accessible directly from the controllers.

## Considerations

The split by abstraction layer is easy and allows grouping common function concerns. However, it's
much too focused on the technical part of the project, especially too focused on the database and
the entities, not on the business part of the project. Hence, it brings lots of constraints when
dealing with new features, which sometimes leads to twist some functionalities to make it work with
this project structure.

There is often a `core`, `utils`, or `common` project in this type of structure because some classes
do not belong to any of the abstraction layer. However, this type of component is often a "garbage"
component, somewhere to put anything.

Like the [flat pattern](../flat), it's really difficult to extract part of the business logic to
another component when using this pattern, as components tends to be tightly coupled.

Another point is that it's also tightly coupled to vendors. So as the project grows, it's quite hard
to change the vendor or the framework.

## Resources

- [O Reilly layered architecture](https://www.oreilly.com/library/view/software-architecture-patterns/9781491971437/ch01.html)

