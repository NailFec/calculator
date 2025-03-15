package basic

val operators = listOf('+', '-', '*', '/')
val expOperators = listOf('^', 'P', 'C', '!', '`')
val brackets = listOf('(', ')')

fun addNumNum(a: Num, b: Num): Num {
    if (a.radicand != b.radicand)
        throw IllegalArgumentException("Radicand of a and b are not equal")
    return Num().apply {
        this.numerator = a.numerator * b.denominator + b.numerator * a.denominator
        this.denominator = a.denominator * b.denominator
        this.simplify()
    }
}

fun mulNumNum(a: Num, b: Num) = Num().apply {
    numerator = a.numerator * b.numerator
    denominator = a.denominator * b.denominator
    radicand = a.radicand * b.radicand
    simplify()
}

fun divNum(a: Num) = Num().apply {
    numerator = a.denominator
    denominator = a.numerator * a.denominator
    radicand = a.radicand
    simplify()
}

fun calNumNum(a: Num, b: Num, op: Char): Num {
    if (op == '+') return addNumNum(a, b)
    if (op == '-') return addNumNum(a, Num(-b.numerator, b.denominator, b.radicand))
    if (op == '*') return mulNumNum(a, b)
    if (op == '/') return mulNumNum(a, divNum(b))
    else throw IllegalArgumentException("[NailERROR] Invalid operator: $op")
}
