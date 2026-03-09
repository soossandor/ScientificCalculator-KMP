package hu.calculator.core.ast
import hu.calculator.core.types.MathValue

class DivideNode(left: ExpressionNode, right: ExpressionNode) : OperatorNode(left, right) {
    override fun evaluate(): MathValue<*> = left.evaluate().divide(right.evaluate())
}