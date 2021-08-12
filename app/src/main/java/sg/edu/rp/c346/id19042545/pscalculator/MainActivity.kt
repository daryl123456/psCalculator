package sg.edu.rp.c346.id19042545.pscalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {
    var lastNumeric: Boolean = false
    var stateError: Boolean = false
    var lastDot: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvDisplay.text = "0"

        btnAC.setOnClickListener {
            tvDisplay.text = "0"
            lastNumeric = false
            stateError = false
            lastDot = false
        }

    }
    fun numBtnOnClick(view: View){
        var msg = ""
        val btnSelected = view as Button
        msg = btnSelected.text.toString()
        if (tvDisplay.text=="0"){
            tvDisplay.text = msg
        }else{
            tvDisplay.append(msg)
        }
        lastNumeric = true

    }
    fun onDoc(view: View) {
        if (lastNumeric && !stateError && !lastDot) {
            tvDisplay.append(".")
            lastNumeric = false
            lastDot = true
        }
    }
    fun onOperator(view: View) {
        if (lastNumeric && !stateError) {
            tvDisplay.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        }
    }
    fun onEqual(view: View) {
        // If the current state is error, nothing to do.
        // If the last input is a number only, solution can be found.
        if (lastNumeric && !stateError) {
            // Read the expression
            val txt = tvDisplay.text.toString()
            // Create an Expression (A class from exp4j library)
            val expression = ExpressionBuilder(txt).build()
            try {
                // Calculate the result and display
                val result = expression.evaluate()
                tvDisplay.text = result.toString()
                lastDot = true // Result contains a dot
            } catch (ex: ArithmeticException) {
                // Display an error message
                tvDisplay.text = "Error"
                stateError = true
                lastNumeric = false
            }
        }
    }

}