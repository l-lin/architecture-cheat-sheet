# Clean architecture pattern

![clean architecture](https://blog.cleancoder.com/uncle-bob/images/2012-08-13-the-clean-architecture/CleanArchitecture.jpg)

## Pattern analysis

The main idea is the separation of concerns and the focus on the use cases. Frameworks and drivers
are just detail implementations, thus allowing the developers to defer the decision to pick which
database, which framework, etc...

With such organisation, it's quite easy to switch the database type (e.g. MySQL to PostgreSQL), or
even the framework. As you can see in this sample, the
[application/spring-app](application/spring-app) is completely isolated. The other components do not
depend on the framework. Moreover, with this structure, it's also easy to package the project to use
it on something else than a webapp, for instance a cron job.

There is lots of benefits of using this organisation. I will not enumerate them as there are already
lots of people that already done that. See the [resources](#resources).

## Considerations

This pattern might feel over-engineered, especially for tiny projects.

The code to produce is also way more than other project structures, but it's still a cost well
spent.

For legacy projects, it's possible to switch to such structure, but it will be hard and painful...
Not sure if it's worth the effort...

## Resources

- https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html
- https://dev.to/pereiren/clean-architecture-series-part-3-2795?signin=true
- https://github.com/carlphilipp/clean-architecture-example
- https://github.com/mattia-battiston/clean-architecture-example
- https://github.com/gshaw-pivotal/spring-hexagonal-example
- https://www.slideshare.net/mattiabattiston/real-life-clean-architecture-61242830
- https://fr.slideshare.net/ThomasPierrain/coder-sans-peur-du-changement-avec-la-meme-pas-mal-hexagonal-architecture

