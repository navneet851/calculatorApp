package com.example.calvert

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.calvert.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder


open class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var expression: Expression
    private var lastNumeric = false
    private var stateError = false
    private var lastDot = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.oneButton.setOnClickListener {
            binding.oneButton.numberButtonEvent()
        }
        binding.twoButton.setOnClickListener {
            binding.twoButton.numberButtonEvent()
        }
        binding.threeButton.setOnClickListener {
            binding.threeButton.numberButtonEvent()
        }
        binding.fourButton.setOnClickListener {
            binding.fourButton.numberButtonEvent()
        }
        binding.fiveButton.setOnClickListener {
            binding.fiveButton.numberButtonEvent()
        }
        binding.sixButton.setOnClickListener {
            binding.sixButton.numberButtonEvent()
        }
        binding.sevenButton.setOnClickListener {
            binding.sevenButton.numberButtonEvent()
        }
        binding.eightButton.setOnClickListener {
            binding.eightButton.numberButtonEvent()
        }
        binding.nineButton.setOnClickListener {
            binding.nineButton.numberButtonEvent()
        }
        binding.zeroButton.setOnClickListener {
            binding.zeroButton.numberButtonEvent()
        }



        binding.plusButton.setOnClickListener {
            binding.plusButton.operatorButtonEvent()
        }
        binding.subButton.setOnClickListener {
            binding.subButton.operatorButtonEvent()
        }
        binding.mulButton.setOnClickListener {
            binding.mulButton.operatorButtonEvent()
        }
        binding.divideButton.setOnClickListener {
            binding.divideButton.operatorButtonEvent()
        }
        binding.moduleButton.setOnClickListener {
            binding.moduleButton.operatorButtonEvent()
        }
        binding.decimalButton.setOnClickListener {
            binding.decimalButton.operatorButtonEvent()
        }

        binding.allClearButton.setOnClickListener {
            binding.resultView.text = ""
            binding.dataProcessView.text = ""
            lastNumeric = false
            stateError = false
            lastDot = false
            binding.resultView.visibility = View.GONE
        }
        binding.clearButton.setOnClickListener {
            binding.dataProcessView.text = ""
            lastNumeric = false
        }
        binding.backspace.setOnClickListener {
            binding.dataProcessView.text = binding.dataProcessView.text.toString().dropLast(1)

            try {
                val lastChar = binding.dataProcessView.text.toString().last()
                if (lastChar.isDigit())
                    onEqualClick()
            }
            catch (e : Exception){
                binding.resultView.text = ""
                binding.resultView.visibility = View.GONE
                Log.e("last char error", e.toString())
            }
        }

        binding.equalButton.setOnClickListener {

            onEqualClick()
            binding.resultView.textSize = 45.0f
            binding.dataProcessView.textSize = 35.0f
            binding.dataProcessView.text = binding.resultView.text.toString().drop(1)
        }

    }

    private fun onEqualClick(){
        if(lastNumeric && !stateError){
            val txt = binding.dataProcessView.text.toString()
            fun isInt() : Boolean{
                for (i in txt.indices){
                    if (txt[i] == '.' || txt[i] == '/')
                        return false
                }
                return true
            }
            expression = ExpressionBuilder(txt).build()

            try {
                val result = if (isInt())
                        expression.evaluate().toLong()
                    else
                    expression.evaluate()

                binding.resultView.visibility = View.VISIBLE
                binding.resultView.text = "=$result"
                binding.resultView.textSize = 35.0f
                binding.dataProcessView.textSize = 45.0f
            }
            catch (e : ArithmeticException){
                Log.e("finalResultError", e.toString() )
                binding.resultView.text = "Enter Valid Expression"
                lastNumeric = false
                stateError = true
            }
        }
    }

    private fun Button.operatorButtonEvent(){
        if (!stateError && lastNumeric){
            binding.dataProcessView.append(this.text.toString())
            lastDot = false
            lastNumeric = false
            onEqualClick()
        }
    }
    private fun Button.numberButtonEvent(){
        if (stateError){
            binding.dataProcessView.text = this.text.toString()
            stateError = false
        }else{
            binding.dataProcessView.append(this.text.toString())
        }
        lastNumeric = true
        onEqualClick()
    }
}