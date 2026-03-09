package hu.calculator.core.ast

import com.ionspin.kotlin.bignum.decimal.BigDecimal
import hu.calculator.core.types.MathValue
import hu.calculator.core.types.RealNumber
import kotlin.math.E
import kotlin.math.PI
import kotlin.jvm.JvmStatic

class VariableNode(name: String) : ExpressionNode {
    private val name: String = name.lowercase()

    companion object {
        var xValue: BigDecimal = BigDecimal.ZERO

        @JvmStatic
        fun setX(x: Double) {
            xValue = BigDecimal.fromDouble(x)
        }
    }

    override fun evaluate(): MathValue<*> {
        return when (name) {
            "x" -> RealNumber(xValue)
            "pi" -> RealNumber(PI)
            "e" -> RealNumber(E)
            else -> RealNumber(BigDecimal.ZERO)
        }
    }
}