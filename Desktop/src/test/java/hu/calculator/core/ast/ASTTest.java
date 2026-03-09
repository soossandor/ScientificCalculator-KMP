package hu.calculator.core.ast;

import hu.calculator.core.types.RealNumber;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ASTTest {

    @Test
    void testComplexExpressionTree() {
        // Kifejezés: (10.5 + 2.5) * 4
        // Elvárt eredmény: 52

        // 1. Létrehozzuk a számokat (levelek a fában)
        ExpressionNode num1 = new NumberNode(new RealNumber("10.5"));
        ExpressionNode num2 = new NumberNode(new RealNumber("2.5"));
        ExpressionNode num3 = new NumberNode(new RealNumber("4"));

        // 2. Összerakjuk az összeadást: (10.5 + 2.5)
        ExpressionNode addition = new AddNode(num1, num2);

        // 3. Összerakjuk a szorzást, aminek a bal ága az összeadás eredménye, a jobb ága a 4-es szám
        ExpressionNode root = new MultiplyNode(addition, num3);

        // 4. Kiértékeljük a gyökeret (ez automatikusan elindítja a rekurziót lefelé)
        String result = root.evaluate().toString();

        // 5. Ellenőrizzük, hogy jó-e az eredmény
        assertEquals("52", result, "A fa kiértékelése hibás!");
    }
}