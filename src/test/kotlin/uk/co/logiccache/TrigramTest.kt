package uk.co.logiccache

import io.kotlintest.shouldNotBe
import io.kotlintest.specs.StringSpec

internal class TrigramTest : StringSpec({
    "nothing to sum" {
        Trigram.generate(file = "43-1.txt") shouldNotBe ""
    }
})