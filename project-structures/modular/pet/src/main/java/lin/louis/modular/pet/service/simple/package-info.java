/**
 * This package contains only simple implementations of the services. They use the adapter pattern so that it's easy to
 * switch the DAO implementation. We can also create another implementation that encapsulates the simple implementation
 * to enrich the functionalities, e.g. add a transactional function.
 */
package lin.louis.modular.pet.service.simple;