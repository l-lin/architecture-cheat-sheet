package lin.louis.legacy

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["/cats"])
class CatController {
    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping
    fun getCats(): Array<Cat> {
        val cats = arrayOf(Cat("Tony"), Cat("Nyan cat"))
        logger.info("Got ${cats.size} cats from legacy app")
        return cats
    }

    @PostMapping
    fun saveCat(@RequestBody cat: Cat): Cat {
        logger.info("Saving cat '${cat.name}' in legacy app")
        return cat
    }

    @PutMapping
    fun updateCat(@RequestBody cat: Cat): Cat {
        logger.info("Updating cat '${cat.name}' in legacy app")
        return cat
    }
}
