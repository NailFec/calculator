package basic

val operators = listOf('+', '-', '*', '/')
val expOperators = listOf('^', 'P', 'C', '!', '`')
val brackets = listOf('(', ')')

fun add(a: Num, b: Num): Num {
    if (a.radicand != b.radicand || a.variables != b.variables) throw IllegalArgumentException("[NailFecERROR] cannot add $a and $b")
    return Num().apply {
        numerator = a.numerator * b.denominator + b.numerator * a.denominator
        denominator = a.denominator * b.denominator
        radicand = a.radicand
        variables = a.variables
        simplify()
    }
}

fun add(a: Num, list: MutableList<Num>): MutableList<Num> {
    for (i in 0..<list.size) {
        if (a.radicand == list[i].radicand && a.variables == list[i].variables) {
            list[i] = add(list[i], a)
            return list
        }
    }
    return list.apply { add(a) }
}

fun add(listA: MutableList<Num>, listB: MutableList<Num>): MutableList<Num> {
    var ans = listB
    for (a in listA) ans = add(a, ans)
    return ans
}

fun mul(a: Num, b: Num) = Num().apply {
    numerator = a.numerator * b.numerator
    denominator = a.denominator * b.denominator
    radicand = a.radicand * b.radicand
    variables = a.variables.toMutableMap()
    for ((name, pow) in b.variables) {
        variables[name] = variables.getOrDefault(name, 0) + pow
    }
    simplify()
}

fun mul(a: Num, list: MutableList<Num>): MutableList<Num> {
    var ans = mutableListOf<Num>()
    for (b in list) ans = add(mul(a, b), ans)
    return ans
}

fun mul(listA: MutableList<Num>, listB: MutableList<Num>): MutableList<Num> {
    var ans = mutableListOf<Num>()
    for (a in listA) ans = add(ans, mul(a, listB))
    return ans
}

fun cal(a: Num, list: MutableList<Num>, op: Char): MutableList<Num> {
    if (op == '+') return add(a, list)
    if (op == '*') return mul(a, list)
    else throw IllegalArgumentException("[NailERROR] Invalid operator: $op")
}

fun cal(listA: MutableList<Num>, listB: MutableList<Num>, op: Char): MutableList<Num> {
    if (op == '+') return add(listA, listB)
    if (op == '*') return mul(listA, listB)
    else throw IllegalArgumentException("[NailERROR] Invalid operator: $op")
}
