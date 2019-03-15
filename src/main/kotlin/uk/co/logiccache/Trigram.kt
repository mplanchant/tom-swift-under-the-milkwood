package uk.co.logiccache

import java.io.File

object Trigram {

    fun generate(from: String, numberOfWords: Int = 100): String {
        val map = mutableMapOf<Pair<String, String>, MutableSet<String>>()
        readFileAsText(from)
            .toWords()
            .windowed(size = 3, step = 1, partialWindows = false) {
                map.getOrPut(Pair(it[0], it[1])) { mutableSetOf() }.add(it[2])
            }
        val start = map.keys.random()
        var nextWord = map[start]
        val newText = mutableListOf(start.first, start.second)
        while (nextWord != null && newText.size < numberOfWords) {
            newText.add(nextWord.random())
            nextWord = map[Pair(newText[newText.size - 2], newText[newText.size - 1])]
        }
        return newText.joinToString(separator = " ")
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