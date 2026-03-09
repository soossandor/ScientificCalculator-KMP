package hu.calculator.core.ast

import hu.calculator.core.exceptions.CalculatorException
import hu.calculator.core.types.MathValue
import hu.calculator.core.types.RealNumber
import kotlin.math.*

class FunctionNode(private val functionName: String, private val argument: ExpressionNode) : ExpressionNode {
    override fun evaluate(): MathValue<*> {
        val doubleVal = (argument.evaluate() as RealNumber).asDouble()

        return when (functionName) {
            "sqrt" -> {
                if (doubleVal < 0) throw CalculatorException("Negatív számból nem vonhatunk gyököt!")
                RealNumber(sqrt(doubleVal))
            }
            "sin" -> RealNumber(sin(doubleVal))
            "cos" -> RealNumber(cos(doubleVal))
            "tan" -> RealNumber(tan(doubleVal))
            "abs" -> RealNumber(abs(doubleVal))
            "log" -> {
                if (doubleVal <= 0) throw CalculatorException("Logaritmus csak pozitív számra!")
                RealNumber(ln(doubleVal))
            }
            "log10" -> {
                if (doubleVal <= 0) throw CalculatorException("Logaritmus csak pozitív számra!")
                RealNumber(log10(doubleVal))
            }
            else -> throw CalculatorException("Ismeretlen függvény: $functionName")
        }
    }
}