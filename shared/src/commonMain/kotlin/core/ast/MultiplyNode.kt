package hu.calculator.core.ast
import hu.calculator.core.types.MathValue

class MultiplyNode(left: ExpressionNode, right: ExpressionNode) : OperatorNode(left, right) {
    override fun evaluate(): MathValue<*> = left.evaluate().multiply(right.evaluate())
}