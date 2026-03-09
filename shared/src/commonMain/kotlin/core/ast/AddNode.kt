package hu.calculator.core.ast
import hu.calculator.core.types.MathValue

class AddNode(left: ExpressionNode, right: ExpressionNode) : OperatorNode(left, right) {
    override fun evaluate(): MathValue<*> = left.evaluate().add(right.evaluate())
}