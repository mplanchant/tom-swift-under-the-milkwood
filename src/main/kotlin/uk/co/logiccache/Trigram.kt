package uk.co.logiccache

import java.io.File

object Trigram {

    fun generate(file: String, numberOfWords: Int = 1000): String {
        val trigrams = extractTrigrams(file)
        val randomTrigram = trigrams.entries.random()
        val generatedText = mutableListOf(randomTrigram.key.first, randomTrigram.key.second)
        while (generatedText.size < numberOfWords) {
            generatedText.add(
                trigrams.getOrDefault(
                    Pair(generatedText[generatedText.size - 2], generatedText[generatedText.size - 1]),
                    setOf<String>()
                ).random()
            )
        }
        return generatedText.joinToString(separator = " ")
    }

    private fun extractTrigrams(file: String): MutableMap<Pair<String, String>, MutableSet<String>> {
        val trigrams = mutableMapOf<Pair<String, String>, MutableSet<String>>()
        readFileAsString(file)
            .toWords()
            .windowed(size = 3, step = 1, partialWindows = false) {
                trigrams.getOrPut(Pair(it[0], it[1])) { mutableSetOf() }.add(it[2])
            }
        return trigrams
    }

    private fun readFileAsString(fileName: String): String =
        File(ClassLoader.getSystemResource(fileName).file)
            .readText()
            .replace(Regex("[^a-zA-Z\\s]"), "")
            .replace(Regex("\\r\\n"), " ")
            .toLowerCase()
            .trim()

    private fun String.toWords() = splitToSequence(" ").filter { it.isNotEmpty() }.toList()
}