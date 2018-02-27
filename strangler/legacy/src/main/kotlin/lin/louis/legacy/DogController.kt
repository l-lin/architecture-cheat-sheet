package lin.louis.legacy

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["/dogs"])
class DogController {
    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping
    fun getDogs(): Array<Dog> {
        val dogs = arrayOf(Dog("Doge"), Dog("Pluto"), Dog("Ogie"), Dog("Snoopy"))
        logger.info("Got ${dogs.size} cats from legacy app")
        return dogs
    }

    @PostMapping
    fun saveDog(@RequestBody dog: Dog): Dog {
        logger.info("Saving dog '${dog.name}' in legacy app")
        return dog
    }

    @PutMapping
    fun updateDog(@RequestBody dog: Dog): Dog {
        logger.info("Updating dog '${dog.name}' in legacy app")
        return dog
    }
}
