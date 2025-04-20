import basic.*

fun main() {
    while (true) {
        val input = consoleInput()
        if (input == "exit" || input == "quit") break
        if (input.isEmpty()) continue
        when {
            input.startsWith("var") -> {
                val splitIndex = input.indexOf('=')
                val name = input.substring(3, splitIndex).trim()
                if (vecs.containsKey(name)) throw IllegalArgumentException("[NailERROR] $name has already exist but it is a vector")
                val value = input.substring(splitIndex + 1).trim()
                vars[name] = calculateFormula(stringToFormula(value))
                consoleOutput("$name = ${vars[name]}")
            }

            input.startsWith("vec") -> {
                val splitIndex = input.indexOf('=')
                val name = input.substring(3, splitIndex).trim()
                if (vars.containsKey(name)) throw IllegalArgumentException("[NailERROR] $name has already exist but it is a variable")
                val value = input.substring(splitIndex + 1).trim()
                val valueList = value.split(",").map { it.trim() }.toMutableList()
                vecs[name] = valueList.map { calculateFormula(stringToFormula(it)) }.toMutableList()
                consoleOutput("$name = ${vecs[name]}")
            }

            input.startsWith("show") -> {
                val value = input.substring(4).trim()
                val valueList = value.split(",").map { calculateFormula(stringToFormula(it.trim())) }.toMutableList()
                val input = consoleInput()
                // TODO: deal with "show" command
            }
            else -> {
                val ans = calculateFormula(stringToFormula(input))
                consoleOutput("$ans")
            }
        }
    }
}
