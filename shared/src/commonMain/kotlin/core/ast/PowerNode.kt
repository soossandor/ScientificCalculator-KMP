package hu.calculator.core.ast

import hu.calculator.core.types.MathValue
import hu.calculator.core.types.RealNumber
import kotlin.math.pow

class PowerNode(left: ExpressionNode, right: ExpressionNode) : OperatorNode(left, right) {
    override fun evaluate(): MathValue<*> {
        val base = (left.evaluate() as RealNumber).asDouble()
        val exponent = (right.evaluate() as RealNumber).asDouble()
        return RealNumber(base.pow(exponent))
    }
}