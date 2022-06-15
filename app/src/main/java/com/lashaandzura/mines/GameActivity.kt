package com.lashaandzura.mines

import android.graphics.drawable.Drawable
import android.graphics.drawable.DrawableWrapper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.core.view.children
import com.lashaandzura.mines.databinding.ActivityGameBinding
import com.lashaandzura.mines.models.Square
import java.util.ArrayList

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding
    private lateinit var matrix: ArrayList<ArrayList<Square>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)


        matrix = generateMatrix()
        getButtons(binding.mineZone, matrix)
    }

    private fun getButtons(layout: TableLayout, matrix: ArrayList<ArrayList<Square>>){
        for(i in 0..7){
            val tableRow = layout.getChildAt(i) as TableRow
            for (j in 0..7){
                val frame = tableRow.getChildAt(j) as FrameLayout
                frame.setOnClickListener {
                    val imageButton = frame.getChildAt(0) as ImageButton
                    if(matrix[i][j].isMined){
                        imageButton.setBackgroundResource(R.drawable.bomb)
                        return@setOnClickListener
                    }
                    imageButton.setBackgroundColor(0)
                    val textView = frame.getChildAt(1) as TextView
                    textView.text = countBombsNearby(matrix[i][j]).toString()
                }
            }
        }
    }

    private fun countBombsNearby(square: Square): Int{
        var count: Int = 0;

        if(square.xCord - 1 > -1 && matrix[square.yCord][square.xCord - 1].isMined){
            count++

            if(square.yCord + 1 < 8 && matrix[square.yCord + 1][square.xCord - 1].isMined)
                count++

            if(square.yCord - 1 > -1 && matrix[square.yCord - 1][square.xCord - 1].isMined)
                count++
        }

        if(square.xCord + 1 < 8 && matrix[square.yCord][square.xCord + 1].isMined){
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

        for(row in 0..7){
            val rowArray = ArrayList<Square>();
            matrix.add(rowArray)
            for(column in 0..7){
                rowArray.add(Square(column, row, (0..6).random() and 1 == 0, 5))
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