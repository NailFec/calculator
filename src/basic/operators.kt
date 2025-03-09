package basic

val operators = listOf('+', '-', '*', '/', 'P', 'C', '^', '!')
val brackets = listOf('(', ')')

fun opIntInt(a: Int, b: Int, op: Char) = when (op) {
    '+' -> a + b
    '-' -> a - b
    '*' -> a * b
    '/' -> a / b
    else -> throw IllegalArgumentException("[NailERROR]!02")
}