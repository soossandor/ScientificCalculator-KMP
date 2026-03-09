package hu.calculator.core.parser

import hu.calculator.core.ast.*
import hu.calculator.core.exceptions.CalculatorException
import hu.calculator.core.types.RealNumber

class ExpressionParser {

    fun parse(expr: String): ExpressionNode {
        val expression = expr.replace("\\s+".toRegex(), "")
        val values = ArrayDeque<ExpressionNode>()
        val ops = ArrayDeque<String>()

        var i = 0
        while (i < expression.length) {
            val c = expression[i]

            if (c.isDigit() || c == '.') {
                val sbuf = StringBuilder()
                while (i < expression.length && (expression[i].isDigit() || expression[i] == '.')) {
                    sbuf.append(expression[i++])
                }
                i--
                values.addLast(NumberNode(RealNumber(sbuf.toString())))
            } else if (c.isLetter()) {
                val sbuf = StringBuilder()
                while (i < expression.length && expression[i].isLetter()) {
                    sbuf.append(expression[i++])
                }
                i--
                val word = sbuf.toString().lowercase()

                if (word == "x" || word == "pi" || word == "e") {
                    values.addLast(VariableNode(word))
                } else {
                    ops.addLast(word)
                }
            } else if (c == '(') {
                ops.addLast("(")
            } else if (c == ')') {
                while (ops.isNotEmpty() && ops.last() != "(") {
                    processOperator(values, ops.removeLast())
                }
                if (ops.isNotEmpty()) {
                    ops.removeLast()
                } else {
                    throw CalculatorException("Hibás zárójelezés!")
                }

                if (ops.isNotEmpty() && isFunction(ops.last())) {
                    processOperator(values, ops.removeLast())
                }
            } else if (c == '+' || c == '-' || c == '*' || c == '/' || c == '^') {
                val opStr = c.toString()
                if (c == '-' && (i == 0 || expression[i - 1] == '(')) {
                    values.addLast(NumberNode(RealNumber("0")))
                }
                while (ops.isNotEmpty() && hasPrecedence(opStr, ops.last())) {
                    processOperator(values, ops.removeLast())
                }
                ops.addLast(opStr)
            } else {
                throw CalculatorException("Ismeretlen karakter: $c")
            }
            i++
        }

        while (ops.isNotEmpty()) {
            val op = ops.removeLast()
            if (op == "(" || op == ")") throw CalculatorException("Hibás zárójelezés!")
            processOperator(values, op)
        }

        if (values.size != 1) throw CalculatorException("Hibás kifejezés formátum!")
        return values.removeLast()
    }

    private fun processOperator(values: ArrayDeque<ExpressionNode>, op: String) {
        if (isFunction(op)) {
            if (values.isEmpty()) throw CalculatorException("Hiányzó argumentum!")
            values.addLast(FunctionNode(op, values.removeLast()))
        } else {
            if (values.size < 2) throw CalculatorException("Hibás művelet!")
            val right = values.removeLast()
            val left = values.removeLast()
            when (op) {
                "+" -> values.addLast(AddNode(left, right))
                "-" -> values.addLast(SubtractNode(left, right))
                "*" -> values.addLast(MultiplyNode(left, right))
                "/" -> values.addLast(DivideNode(left, right))
                "^" -> values.addLast(PowerNode(left, right))
                else -> throw CalculatorException("Ismeretlen operátor: $op")
            }
        }
    }

    private fun isFunction(op: String): Boolean {
        return op == "sin" || op == "cos" || op == "tan" ||
                op == "sqrt" || op == "abs" || op == "log" || op == "log10"
    }

    private fun hasPrecedence(op1: String, op2: String): Boolean {
        if (op2 == "(" || op2 == ")") return false
        if (isFunction(op2)) return true
        if (op1 == "^") return false // A hatványozás jobbról balra értékelődik ki (vagy legalábbis a legerősebb)
        if (op2 == "^") return true
        if ((op1 == "*" || op1 == "/") && (op2 == "+" || op2 == "-")) return false
        return true
    }
}