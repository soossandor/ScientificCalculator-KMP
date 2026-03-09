package hu.calculator.core.ast

import hu.calculator.core.types.MathValue

interface ExpressionNode {
    fun evaluate(): MathValue<*>
}