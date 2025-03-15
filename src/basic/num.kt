package basic

class Num(
    var numerator: Int = 0, var denominator: Int = 1, var radicand: Int = 0
) {
    init {
        simplify()
    }

    fun simplify() {
        val facRadicand = factorization(radicand)
        var outer = 1
        var inner = 1
        for ((i, v) in facRadicand) {
            outer *= pow(i, v / 2)
            inner *= pow(i, v % 2)
        }
        radicand = inner
        numerator *= outer

        val gcd = gcd(numerator, denominator)
        numerator /= gcd
        denominator /= gcd

        if (denominator < 0) {
            numerator *= -1
            denominator *= -1
        }
    }

    override fun toString(): String {
        var ans = numerator.toString()
        if (radicand != 1) ans += "[$radicand]"
        if (denominator != 1) ans += "/$denominator"
        return ans
    }
}

fun intToNum(n: Int) = Num().apply { numerator = n }

fun floatToNum(n: Float): Num {
    val decimalStr = n.toString().split(".")
    val integerPart = decimalStr[0].toIntOrNull() ?: 0
    val fractionalPart = decimalStr.getOrNull(1)?.toIntOrNull() ?: 0
    val denominator = pow(10, decimalStr.getOrNull(1)?.length ?: 0)
    val numerator = integerPart * denominator + fractionalPart

    return Num().apply {
        this.numerator = numerator
        this.denominator = denominator
    }
}
