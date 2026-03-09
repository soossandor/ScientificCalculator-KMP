# 🧮 Tudományos Számológép és Függvényrajzoló

![Java](https://img.shields.io/badge/Java-21-orange.svg)
![JavaFX](https://img.shields.io/badge/JavaFX-GUI-blue.svg)
![Gradle](https://img.shields.io/badge/Gradle-Build_Tool-green.svg)
![License](https://img.shields.io/badge/License-MIT-lightgrey.svg)

Egy magas pontosságú, objektumorientált szemlélettel (OOP) épült tudományos számológép és függvényrajzoló asztali alkalmazás, JavaFX grafikus felülettel. A program saját beépített matematikai értelmezővel (Parser) és Absztrakt Szintaxisfa (AST) kiértékelővel rendelkezik.

## ✨ Főbb funkciók

* **Nagy pontosságú számítások:** A motor a motorháztető alatt `BigDecimal` (100 tizedesjegy precízió) segítségével számol, így elkerüli a lebegőpontos kerekítési hibákat.
* **Intelligens Parser (Shunting-yard algoritmus):** Képes komplex, zárójelezett matematikai kifejezések (pl. `sin(x^2) + 2*(3-1)`) stringből történő felépítésére és kiértékelésére.
* **Tudományos műveletek:** Trigonometria (`sin`, `cos`, `tan`), logaritmus (`log`, `log10`), gyökvonás (`sqrt`), abszolútérték (`abs`) és hatványozás (`^`).
* **Beépített konstansok:** `pi` és `e` támogatása.
* **Interaktív Előzmények:** A korábbi számítások listázódnak, és kattintásra visszatöltődnek a kijelzőre további szerkesztés céljából.
* **Fejlett Függvényrajzoló (Plotter):** * Akár 3 függvény (`f(x)`, `g(x)`, `h(x)`) egyidejű megjelenítése.
    * Dinamikusan paraméterezhető koordináta-rendszer (Min, Max, Lépésköz).
    * Teljes képernyős nézet (Fullscreen) a részletes grafikonok elemzéséhez.
* **Modern UI:** "Dark Mode" (sötét téma) dizájn, dinamikus betűméretek és fizikai billentyűzet támogatás (kurzorkezeléssel).

## 📸 Képernyőképek
*(Ide érdemes beszúrnod 1-2 képernyőfotót a GitHub repódban! Például: `![Kalkulátor](link_a_kephez.png)`)*

## 🛠️ Architektúra és Technológiák

A projekt három fő rétegre tagolódik:
1. **Core (AST & Types):** Polimorfikus csomópontok (`ExpressionNode`), amelyek a matematikai kifejezések fa struktúráját alkotják.
2. **Parser:** A Dijkstra-féle tolatóudvar (Shunting-yard) algoritmus kiterjesztett változata.
3. **UI (JavaFX):** TabPane alapú, reszponzív asztali grafikus felület.

## 🚀 Telepítés és Futtatás

Mivel a projekt Gradle alapú, a futtatás rendkívül egyszerű bármilyen IDE-ből (pl. IntelliJ IDEA):

1. Klónozd a repót:
   ```bash
   git clone [https://github.com/FELHASZNALONEVED/ScientificCalculator-JavaFX.git](https://github.com/FELHASZNALONEVED/ScientificCalculator-JavaFX.git)

2. Nyisd meg a projektet IntelliJ IDEA-ban.

3. Frissítsd a Gradle függőségeket (Reload Gradle Project).

4. Futtasd a hu.calculator.ui.Main osztályt!

---

### **2. A frissített Osztálydiagram (PlantUML) a Wikihez**
Mivel a rendszerünk hatalmasat fejlődött a legelső verzió óta, a diagram is jelentősen kibővült. Ezt a PlantUML kódot nyugodtan kimásolhatod, és egyenesen beillesztheted a GitHub Wiki oldalára (a GitHub beépítve támogatja, csak rakd ` ```plantuml ` és ` ``` ` blokk közé), vagy legenerálhatod az IntelliJ PlantUML bővítményével képként.

```plantuml
@startuml
skinparam handwritten false
skinparam classBackgroundColor #f9f9f9
skinparam classBorderColor #2b2b2b
skinparam arrowColor #cc7832

package "hu.calculator.core.types" {
    interface MathValue<T> {
        + getValue(): T
        + add(MathValue<?> other): MathValue<?>
        + subtract(MathValue<?> other): MathValue<?>
        + multiply(MathValue<?> other): MathValue<?>
        + divide(MathValue<?> other): MathValue<?>
    }

    class RealNumber {
        - value: BigDecimal
        + {static} MC: MathContext
    }
    MathValue <|.. RealNumber
}

package "hu.calculator.core.ast" {
    interface ExpressionNode {
        + evaluate(): MathValue<?>
    }

    class NumberNode {
        - value: MathValue<?>
    }
    
    class VariableNode {
        - name: String
        + {static} xValue: BigDecimal
    }

    class FunctionNode {
        - functionName: String
        - argument: ExpressionNode
    }

    abstract class OperatorNode {
        # left: ExpressionNode
        # right: ExpressionNode
    }

    class AddNode
    class SubtractNode
    class MultiplyNode
    class DivideNode
    class PowerNode

    ExpressionNode <|.. NumberNode
    ExpressionNode <|.. VariableNode
    ExpressionNode <|.. FunctionNode
    ExpressionNode <|.. OperatorNode

    OperatorNode <|-- AddNode
    OperatorNode <|-- SubtractNode
    OperatorNode <|-- MultiplyNode
    OperatorNode <|-- DivideNode
    OperatorNode <|-- PowerNode
}

package "hu.calculator.core.parser" {
    class ExpressionParser {
        + parse(expression: String): ExpressionNode
        - processOperator(values: Stack, op: String)
        - isFunction(op: String): boolean
        - hasPrecedence(op1: String, op2: String): boolean
    }
}

package "hu.calculator.core.exceptions" {
    class CalculatorException <<RuntimeException>>
}

package "hu.calculator.ui" {
    class Main {
        + {static} main(args: String[])
    }

    class CalculatorApp {
        - display: TextField
        - parser: ExpressionParser
        - historyList: ListView<String>
        + start(primaryStage: Stage)
        - createButtonGrid(buttonLabels: String[][]): GridPane
        - handleButtonClick(value: String)
        - calculateResult()
    }
}

' Kapcsolatok
Main --> CalculatorApp : "Launches"
CalculatorApp --> ExpressionParser : "Uses"
ExpressionParser ..> ExpressionNode : "Creates (AST Tree)"
ExpressionNode ..> MathValue : "Evaluates to"

@enduml