package hu.calculator.core.ast

// Itt szedjük ki a hibát okozó ? jeleket, garantálva, hogy sosem Null a csomópont!
abstract class OperatorNode(val left: ExpressionNode, val right: ExpressionNode) : ExpressionNode