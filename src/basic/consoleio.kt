package basic

fun consoleInput(vars: Boolean = false): String {
    if (vars) {
        printVars()
        println()
    }
    print(">>> ")
    val str = readlnOrNull()
    if (str.isNullOrBlank()) throw IllegalArgumentException("Invalid input: Input cannot be null or blank")
    return str
}

fun consoleOutput(msg: String) {
    println(msg)
    println()
}

fun main() {
    // normal input & output
    var tmpStr1 = consoleInput()
    consoleOutput(tmpStr1)

    // input with printVars = true & vars = empty & output
    tmpStr1 = consoleInput(vars = true)
    consoleOutput(tmpStr1)

    // input with printVars = true & vars is complex & output
    __complex_vars()
    tmpStr1 = consoleInput(vars = true)
    consoleOutput(tmpStr1)
}