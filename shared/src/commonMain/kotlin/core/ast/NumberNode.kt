package hu.calculator.core.ast
import hu.calculator.core.types.MathValue

class NumberNode(private val value: MathValue<*>) : ExpressionNode {
    override fun evaluate(): MathValue<*> = value
}