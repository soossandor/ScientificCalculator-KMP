package hu.calculator.android

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import hu.calculator.core.parser.ExpressionParser

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. A felület felépítése tisztán Kotlin kódbol (nincs szükség XML fájlokra!)
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(60, 60, 60, 60)
        }

        val inputField = EditText(this).apply { hint = "Írd be a kifejezést (pl. 2+2 vagy sin(x))" }
        val calcButton = Button(this).apply { text = "Számolás" }
        val resultText = TextView(this).apply {
            textSize = 24f
            setPadding(0, 50, 0, 0)
        }

        layout.addView(inputField)
        layout.addView(calcButton)
        layout.addView(resultText)

        setContentView(layout)

        // 2. A közös KMP matematikai motorunk példányosítása!
        val parser = ExpressionParser()

        // 3. Gombnyomás eseménykezelése
        calcButton.setOnClickListener {
            try {
                val expr = inputField.text.toString()
                // Itt hívjuk meg a shared modult!
                val result = parser.parse(expr).evaluate().getValue().toString()
                resultText.text = "Eredmény:\n$result"
            } catch (e: Exception) {
                resultText.text = "Hiba: ${e.message}"
            }
        }
    }
}