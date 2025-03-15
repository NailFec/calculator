package basic

fun gcd(a: Int, b: Int): Int {
    var x = a
    var y = b

    while (y != 0) {
        val tmp = x % y
        x = y
        y = tmp
    }

    return x
}

fun factorization(n: Int): Map<Int, Int> {
    var number = n
    val factors = mutableMapOf<Int, Int>()

    var divisor = 2
    while (divisor * divisor <= number) {
        while (number % divisor == 0) {
            factors[divisor] = factors.getOrDefault(divisor, 0) + 1
            number /= divisor
        }
        divisor++
    }
    if (number > 1) {
        factors[number] = factors.getOrDefault(number, 0) + 1
    }

    return factors
}

fun pow(base: Int, exp: Int): Int {
    var ans = 1
    var x = base
    var y = exp
    while (y > 0) {
        if (y % 2 == 1) ans *= x
        x *= x
        y /= 2
    }
    return ans
}

fun main() {
    print("calculate GCD: ")
    var input = readln().split(" ")
    var values = input.map { it.toInt() }
    var a = values[0]
    var b = values[1]
    val gcdAB = gcd(a, b)
    println(gcdAB)

    print("calculate factorization: ")
    a = readln().toInt()
    println(factorization(a))

    print("calculate pow: ")
    input = readln().split(" ")
    values = input.map { it.toInt() }
    a = values[0]
    b = values[1]
    println(pow(a, b))
}
