package com.lashaandzura.mines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.lashaandzura.mines.databinding.ActivityGameBinding
import com.lashaandzura.mines.models.Square
import kotlin.collections.ArrayList

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding
    private lateinit var matrix: ArrayList<ArrayList<Square>>

    private var exploded: Boolean = false
    private var openedBox: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.restartBtn.setOnClickListener {
            restart()
        }

        matrix = generateMatrix()
        addButtonLogic(binding.mineZone, matrix)
    }

    private fun checkIfWon(){
        openedBox++;
        if(openedBox == 56)
            won()
    }

    private fun won(){
        binding.gameHeader.text = "მეიგო სმნ"
    }

    private fun lost(){
        exploded = true
        binding.gameHeader.text = "არადა რა კარგად მიდიოდი"
    }

    private fun restart(){
        matrix = generateMatrix()
        addButtonLogic(binding.mineZone, matrix)
        exploded = false
        openedBox = 0
        binding.gameHeader.text = "ჰე ჰე დააჩქარე"
    }

    private fun addButtonLogic(layout: TableLayout, matrix: ArrayList<ArrayList<Square>>){
        for(i in 0..7){
            val tableRow = layout.getChildAt(i) as TableRow
            for (j in 0..7){
                val frame = tableRow.getChildAt(j) as FrameLayout
                val imageButton = frame.getChildAt(0) as ImageButton
                val textView = frame.getChildAt(1) as TextView
                imageButton.setBackgroundResource(R.drawable.warning)
                textView.text = null
                frame.setOnClickListener {
                    if(exploded || openedBox == 56)
                        return@setOnClickListener

                    if(matrix[i][j].isMined){
                        imageButton.setBackgroundResource(R.drawable.bomb)
                        lost()
                        return@setOnClickListener
                    }
                    imageButton.setBackgroundColor(0)
                    textView.text = countBombsNearby(matrix[i][j]).toString()
                    checkIfWon()
                }
            }
        }
    }

    private fun generateRandomArray(): ArrayList<Int>{
        val elements = ArrayList<Int>()
        while (elements.size <= 8){
            val num = (1..64).random()

            if(elements.find { it == num} == null)
                elements.add(num)
        }

        return elements
    }

    private fun countBombsNearby(square: Square): Int{
        var count: Int = 0;

        if(square.xCord - 1 > -1){
            if(matrix[square.yCord][square.xCord - 1].isMined)
                count++

            if(square.yCord + 1 < 8 && matrix[square.yCord + 1][square.xCord - 1].isMined)
                count++

            if(square.yCord - 1 > -1 && matrix[square.yCord - 1][square.xCord - 1].isMined)
                count++
        }

        if(square.xCord + 1 < 8){
            if(matrix[square.yCord][square.xCord + 1].isMined)
                count++;

            if(square.yCord + 1 < 8 && matrix[square.yCord + 1][square.xCord + 1].isMined)
                count++

            if(square.yCord - 1 > -1 && matrix[square.yCord - 1][square.xCord + 1].isMined)
                count++
        }

        if(square.yCord + 1 < 8 && matrix[square.yCord + 1][square.xCord].isMined)
            count++;

        if(square.yCord - 1 > -1 && matrix[square.yCord - 1][square.xCord].isMined)
            count++;

        return count
    }

    private fun generateMatrix(): ArrayList<ArrayList<Square>>{
        val matrix = ArrayList<ArrayList<Square>>()
        val bombs = generateRandomArray()

        for(row in 0..7){
            val rowArray = ArrayList<Square>();
            matrix.add(rowArray)
            for(column in 0..7){
                rowArray.add(Square(column, row, bombs.find { it == row * 8 + column } != null, 5))
            }
        }

        return matrix
    }

     // Want to generate matrix of buttons from code || Not using for now
    private fun generateMineField(){
        for (i in 1..8){
            val row = createTableRow()
            binding.mineZone.addView(row)
            for (j in 1..8){
                row.addView(createButton())
                row.invalidate()
            }
            binding.mineZone.invalidate()
        }
    }

    private fun createTableRow(): TableRow{
        val row = TableRow(this)
        val params = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 8.0f)
        row.layoutParams = params
        return row
    }

    private fun createButton(): Button {
        val button = Button(this)
        val params = TableLayout.LayoutParams(0, 0, 1.0f)
        button.layoutParams = params
        return button
    }
}