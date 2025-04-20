package basic

fun separateString(string: String): MutableList<Any> {
    val ans = mutableListOf<Any>()
    var i = 0
    val n = string.length
    while (i < n) {
        when {
            string[i].isDigit() -> {
                var tmp = string[i].digitToInt()
                while (++i < n && string[i].isDigit()) tmp = tmp * 10 + string[i].digitToInt()
                ans.add(tmp)
            }

            string[i] == ' ' -> ++i
            string[i] in operators || string[i] in brackets || string[i].isLetter() -> ans.add(string[i++])
            else -> throw IllegalArgumentException("[NailERROR] Invalid character: '${string[i]}'")
        }
    }
    return ans
}

fun combineBracket(formula: MutableList<Any>): MutableList<Any> {
    var i = -1
    val n = formula.size
    fun recursion(): Any {
        val ans: MutableList<Any> = mutableListOf()
        while (++i < n) {
            when (formula[i]) {
                '(' -> ans.add(recursion())
                ')' -> return if (ans.size == 1) ans[0] else ans.toMutableList()
                else -> ans.add(formula[i])
            }
        }
        return if (ans.size == 1) ans[0] else ans
    }

    val ans = recursion()
    return if (ans is MutableList<*>) ans as MutableList<Any> else mutableListOf(ans)
}

fun negativeNumber(formula: MutableList<Any>): MutableList<Any> {
    val ans = mutableListOf<Any>()
    var i = -1
    val n = formula.size
    while (++i < n) {
        when (formula[i]) {
            is Int -> {
                if (i - 1 >= 0 && formula[i - 1] == '-') {
                    ans.removeAt(ans.size - 1)
                    if (i - 2 >= 0 && formula[i - 2] !in operators) ans.add('+')
                    ans.add(formula[i] as Int * -1)
                } else ans.add(formula[i])
            }

            is MutableList<*> -> ans.add(negativeNumber(formula[i] as MutableList<Any>))
            else -> ans.add(formula[i])
        }
    }
    return ans
}

fun combinePriority(formula: MutableList<Any>): MutableList<Any> {
    fun recursion(formula: List<Any>): Any {
        val tmp = mutableListOf<Any>()
        val ans = mutableListOf<Any>()

        for (element in formula) {
            when (element) {
                is Char -> {
                    if (element == '+' || element == '-') {
                        if (tmp.size == 1) ans.add(tmp[0]) else ans.add(tmp.toMutableList())
                        tmp.clear()
                        ans.add(element)
                    } else tmp.add(element)
                }

                is MutableList<*> -> tmp.add(recursion(element as MutableList<Any>))
                else -> tmp.add(element)
            }
        }

        if (tmp.isNotEmpty()) if (tmp.size == 1) ans.add(tmp[0]) else ans.add(tmp.toMutableList())
        return if (ans.size == 1) ans[0] else ans.toMutableList()
    }

    val ans = recursion(formula)
    return if (ans is MutableList<*>) ans as MutableList<Any> else mutableListOf(ans)
}

fun combineUnsignedMul(formula: MutableList<Any>): MutableList<Any> {
    fun recursion(formula: List<Any>): Any {
        val tmp = mutableListOf<Any>()
        val ans = mutableListOf<Any>()

        if (formula.size == 1) return formula[0]
        if (formula.isEmpty()) throw IllegalArgumentException("[NailERROR] Invalid formula: $formula")
        var status = false
        for (i in 0..formula.size - 2) {
            if ((formula[i] is Int || formula[i] is MutableList<*>) && (formula[i + 1] is Int || formula[i + 1] is MutableList<*>)) {
                if (!status) {
                    status = true
                    tmp.add(formula[i] as? Int ?: recursion(formula[i] as MutableList<Any>))
                }
                tmp.add('*')
                tmp.add(formula[i + 1] as? Int ?: recursion(formula[i + 1] as MutableList<Any>))
            } else {
                if (status) {
                    status = false
                    ans.add(tmp.toMutableList())
                    tmp.clear()
                } else ans.add(
                    if (formula[i] is MutableList<*>) recursion(formula[i] as MutableList<Any>)
                    else formula[i]
                )
            }
        }

        ans.add(
            if (status) tmp else {
                if (formula.last() is MutableList<*>) recursion(formula.last() as MutableList<Any>) else formula.last()
            }
        )
        return if (ans.size == 1) ans[0] else ans.toMutableList()
    }

    val ans = recursion(formula)
    return if (ans is MutableList<*>) ans as MutableList<Any> else mutableListOf(ans)
}

fun numFormula(formula: MutableList<Any>): MutableList<Any> {
    val ans: MutableList<Any> = mutableListOf()
    for (element in formula) {
        when (element) {
            is Int -> ans.add(intToNum(element))
            is Char -> ans.add(if (element in operators) element else charToNum(element))
            is MutableList<*> -> ans.add(numFormula(element as MutableList<Any>))
            else -> ans.add(element)
        }
    }
    return ans
}

fun negativeFormula(formula: MutableList<Any>, allNegative: Boolean = false): MutableList<Any> {
    var negative = false
    val ans = mutableListOf<Any>()
    for (element in formula) {
        when (element) {
            is Char -> {
                if (element in "+-") {
                    ans.add('+')
                    if (element == '-') negative = !negative
                } else ans.add(element)
            }

            is Num -> {
                ans.add(if (negative != allNegative) negativeNum(element) else element)
                if (negative) negative = false
            }

            is MutableList<*> -> {
                ans.add(negativeFormula(element as MutableList<Any>, negative != allNegative))
                if (negative) negative = false
            }

            else -> throw IllegalArgumentException("[NailERROR] Invalid formula: $formula")
        }
    }
    return ans
}

fun invertFormula(formula: MutableList<Any>): MutableList<Any> {
    // TODO: invert a MutableList
    var invert = false
    val ans = mutableListOf<Any>()
    for (element in formula) {
        when (element) {
            is Char -> {
                if (element in "*/") {
                    ans.add('*')
                    if (element == '/') invert = true
                } else ans.add(element)
            }

            is Num -> {
                ans.add(if (invert) invertNum(element) else element)
                invert = false
            }

            is MutableList<*> -> {
                val tmp = invertFormula(element as MutableList<Any>)
                if (invert) {
                    val tmp = calculateFormula(tmp as MutableList<Any>)
                    if (tmp.size == 1) ans.add(invertNum(tmp[0]))
                    else throw IllegalArgumentException("[NailERROR] Unfinished: invert a MutableList")
                } else ans.add(tmp)
                invert = false
            }

            else -> throw IllegalArgumentException("[NailERROR] Invalid formula: $formula")
        }
    }
    return ans
}

fun stringToFormula(string: String): MutableList<Any> {
    var ans = separateString(string)
    ans = combineBracket(ans)
    ans = negativeNumber(ans)
    ans = combinePriority(ans)
    ans = combineUnsignedMul(ans)
    ans = numFormula(ans)
    ans = negativeFormula(ans)
    ans = invertFormula(ans)
    return ans
}

fun calculateFormula(formula: MutableList<Any>): MutableList<Num> {
    var ans =
        if (formula[0] is MutableList<*>) calculateFormula(formula[0] as MutableList<Any>) else mutableListOf<Num>(
            formula[0] as Num
        )
    if (formula.size == 1) return ans
    if (formula.isEmpty() || formula.size == 2) throw IllegalArgumentException("[NailERROR] Invalid formula: $formula")
    for (i in 1..<formula.size - 1) if (formula[i] is Char && formula[i] in operators) {
        ans = if (formula[i + 1] is MutableList<*>) cal(
            calculateFormula(formula[i + 1] as MutableList<Any>), ans, formula[i] as Char
        ) else cal(formula[i + 1] as Num, ans, formula[i] as Char)
    }
    return ans
}

fun main() {
    fun test(line: String) {
        println("original: $line")
        var tmp2 = separateString(line)
        println("separateString: $tmp2")
        tmp2 = combineBracket(tmp2)
        println("combineBracket: $tmp2")
        tmp2 = negativeNumber(tmp2)
        println("negativeNumber: $tmp2")
        tmp2 = combinePriority(tmp2)
        println("combinePriority: $tmp2")
        tmp2 = combineUnsignedMul(tmp2)
        println("combineUnsignedMul: $tmp2")
        tmp2 = numFormula(tmp2)
        println("numFormula: $tmp2")
        tmp2 = negativeFormula(tmp2)
        println("negativeFormula: $tmp2")
        tmp2 = invertFormula(tmp2)
        println("invertFormula: $tmp2")
        println("result: ${calculateFormula(tmp2)}")
    }

    val lines = java.io.File(".repository/testcases.txt").readLines()
    for (line in lines) {
        if (line.contains("[!skip]")) continue
        val processedLine = line.substringAfter(": ")
        test(processedLine)
        println()
    }
//    val processedLine = readln()
//    test(processedLine)
}
