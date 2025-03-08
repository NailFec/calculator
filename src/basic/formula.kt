package basic

fun separateString(string: String): MutableList<Any> {
    val ans: MutableList<Any> = mutableListOf()
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
            else -> throw IllegalArgumentException("[NailERROR] Invalid character: ${string[i]}")
        }
    }
    return ans
}

fun combineFormula(formula: MutableList<Any>): MutableList<Any> {
    var i = -1
    val n = formula.size
    fun recursion(): MutableList<Any> {
        val ans: MutableList<Any> = mutableListOf()
        while (++i < n) {
            when (formula[i]) {
                '(' -> ans.add(recursion())
                ')' -> return ans
                else -> ans.add(formula[i])
            }
        }
        return ans
    }
    return recursion()
}

fun negativeNumber(formula: MutableList<Any>): MutableList<Any> {
    var i = -1
    val n = formula.size
    val ans: MutableList<Any> = mutableListOf()
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

// TODO: simpFormula

fun processFormula(string: String): MutableList<Any> {
    var tmp = separateString(string)
    tmp = combineFormula(tmp)
    tmp = negativeNumber(tmp)
    return tmp
}

fun main() {
    val tmp1 = readln()

    fun test() {
        println("original: $tmp1")
        var tmp2 = separateString(tmp1)
        println("separateString: $tmp2")
        tmp2 = combineFormula(tmp2)
        println("combineFormula: $tmp2")
        tmp2 = negativeNumber(tmp2)
        println("negativeNumber: $tmp2")
    }

    test()

    // process formula from string
    println(processFormula(tmp1))
}