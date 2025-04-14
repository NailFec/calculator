package basic

fun gcd(a: Int, b: Int): Int {
    var a = a
    var b = b
    while (b != 0) {
        val tmp = a % b
        a = b
        b = tmp
    }
    return a
}

fun factorization(number: Int): Map<Int, Int> {
    var number = number
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
    var base = base
    var exp = exp
    while (exp > 0) {
        if (exp % 2 == 1) ans *= base
        base *= base
        exp /= 2
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
