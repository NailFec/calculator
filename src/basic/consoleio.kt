package basic

fun consoleInput(printVars: Boolean = false): String {
    if (printVars) {
        printVars()
        printVecs()
        println()
    }
    print(">>> ")
    val str = readln()
    return str
}

fun consoleOutput(msg: String) {
    println(msg)
    println()
}