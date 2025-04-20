package basic

val vars: MutableMap<String, MutableList<Num>> = mutableMapOf()

fun printVars() {
    println("Variables:")
    for ((name, value) in vars) println("$name = $value")
    if (vars.isEmpty()) println("Empty")
}

val vecs: MutableMap<String, MutableList<MutableList<Num>>> = mutableMapOf()

fun printVecs() {
    println("Vectors:")
    for ((name, value) in vecs) println("$name = $value")
    if (vecs.isEmpty()) println("Empty")
}