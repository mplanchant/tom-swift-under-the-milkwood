package uk.co.logiccache

import io.kotlintest.shouldNotBe
import io.kotlintest.specs.StringSpec

internal class TrigramTest : StringSpec({

    "nothing to sum" {
        val foo = Trigram.generate(file = "43-0.txt")
        print(foo)
        foo shouldNotBe ""
    }


})