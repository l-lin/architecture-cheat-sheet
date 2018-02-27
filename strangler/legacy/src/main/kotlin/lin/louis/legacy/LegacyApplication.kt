package lin.louis.legacy

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class LegacyApplication

fun main(args: Array<String>) {
    SpringApplication.run(LegacyApplication::class.java, *args)
}

