package uk.co.logiccache

import java.io.File

object Trigram {

    fun generate(file: String, numberOfWords: Int = 100): String {
        val map = extractWords(file)
        val start = map.keys.random()
        var nextWord = map[start]
        val generatedText = mutableListOf(start.first, start.second)
        while (nextWord != null && generatedText.size < numberOfWords) {
            generatedText.add(nextWord.random())
            nextWord = map[Pair(generatedText[generatedText.size - 2], generatedText[generatedText.size - 1])]
        }
        return generatedText.joinToString(separator = " ")
    }

    private fun extractWords(file: String): MutableMap<Pair<String, String>, MutableSet<String>> {
        val map = mutableMapOf<Pair<String, String>, MutableSet<String>>()
        readFileAsText(file)
            .toWords()
            .windowed(size = 3, step = 1, partialWindows = false) {
                map.getOrPut(Pair(it[0], it[1])) { mutableSetOf() }.add(it[2])
            }
        return map
    }

    private fun readFileAsText(fileName: String): String =
        File(ClassLoader.getSystemResource(fileName).file)
            .readText()
            .replace(Regex("[^a-zA-Z\\s]"), "")
            .replace(Regex("\\r\\n"), " ")
            .toLowerCase()
            .trim()

    private fun String.toWords() = splitToSequence(" ").filter { it.isNotEmpty() }.toList()
}