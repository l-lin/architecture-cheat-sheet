package lin.louis.legacy

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["/cats"])
class CatsController {
    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping
    fun getCats(): Array<Cat> {
        return arrayOf(Cat("Tony"), Cat("Nyan cat"))
    }

    @PostMapping
    fun saveCat(@RequestBody cat: Cat): Cat {
        logger.info("Saving cat '${cat.name}'")
        return cat
    }

    @PutMapping
    fun updateCat(@RequestBody cat: Cat): Cat {
        logger.info("Updating cat '${cat.name}'")
        return cat
    }
}
