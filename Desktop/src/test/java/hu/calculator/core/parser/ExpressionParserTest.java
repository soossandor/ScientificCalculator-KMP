package hu.calculator.core.parser;

import hu.calculator.core.ast.ExpressionNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExpressionParserTest {

    @Test
    void testSimpleAddition() {
        ExpressionParser parser = new ExpressionParser();
        ExpressionNode result = parser.parse("10 + 20");
        assertEquals("30", result.evaluate().toString());
    }

    @Test
    void testOperatorPrecedence() {
        ExpressionParser parser = new ExpressionParser();
        // A szorzásnak előbb kell lefutnia! Helyes eredmény: 22 (nem pedig 72)
        ExpressionNode result = parser.parse("10 + 2 * 6");
        assertEquals("22", result.evaluate().toString());
    }

    @Test
    void testParentheses() {
        ExpressionParser parser = new ExpressionParser();
        // Itt a zárójel miatt az összeadás fut előbb
        ExpressionNode result = parser.parse("(10 + 2) * 6");
        assertEquals("72", result.evaluate().toString());
    }

    @Test
    void testComplexExpression() {
        ExpressionParser parser = new ExpressionParser();
        ExpressionNode result = parser.parse("100 / (2 + 3) * 4.5");
        // 100 / 5 = 20 -> 20 * 4.5 = 90
        assertEquals("90", result.evaluate().toString());
    }
}