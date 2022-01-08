package net.danlew.wordle

fun loadWordList(resName: String): List<String> {
  return Thread.currentThread()
    .contextClassLoader
    .getResourceAsStream(resName)!!
    .bufferedReader()
    .use { it.readText() }
    .split("\n")
}