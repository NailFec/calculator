package basic

val vars: MutableMap<String, MutableList<Any>> = mutableMapOf()

fun printVars() {
    println("Variables:")
    for ((name, value) in vars) println("$name = $value")
    if (vars.isEmpty()) println("Empty")
}

fun __complex_vars() {
    val tmpLst: MutableList<Any> = mutableListOf()
    tmpLst.apply {
        add("abc")
        add(123)
        add(mutableListOf(4, "ghi", 5, mutableListOf(1, "def", 2)))
    }
    vars["x"] = tmpLst
    vars["abc"] = tmpLst
}

fun main() {
    // print empty vars
    printVars()

    // print complex vars
    __complex_vars()
    printVars()
}