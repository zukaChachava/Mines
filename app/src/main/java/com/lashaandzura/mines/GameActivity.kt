package com.lashaandzura.mines

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.lashaandzura.mines.constants.Constants
import com.lashaandzura.mines.databinding.ActivityGameBinding
import com.lashaandzura.mines.models.Square
import com.lashaandzura.mines.notifications.NotificationUtil
import com.lashaandzura.mines.receivers.MineReceiver
import com.lashaandzura.mines.room.Statistic
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.collections.ArrayList

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding
    private lateinit var matrix: ArrayList<ArrayList<Square>>
    private lateinit var mineReceiver: MineReceiver

    private var exploded: Boolean = false
    private var openedBox: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        registerMineReceiver()
        createChannel()


        binding.restartBtn.setOnClickListener {
            restart()
        }

        matrix = generateMatrix()
        addButtonLogic(binding.mineZone, matrix)
    }

    private fun checkIfWon(){
        openedBox++;
        if(openedBox == 55)
            won()
    }

    private fun won(){
        binding.gameHeader.text = "მეიგო სმნ"
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
        val formatted = current.format(formatter)
        App.instance.db.getStatisticDao().insert(Statistic(0, result = true, date=formatted))
        throwBroadcast(true)
    }

    private fun lost(){
        exploded = true
        binding.gameHeader.text = "არადა რა კარგად მიდიოდი"
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
        val formatted = current.format(formatter)
        App.instance.db.getStatisticDao().insert(Statistic(0, result = false, date=formatted))
        throwBroadcast(false)
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
                    if(exploded || openedBox == 55)
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
            val num = (0..63).random()

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
                rowArray.add(Square(column, row, bombs.find { it == row * 8 + column } != null))
                if(bombs.find { it == row * 8 + column }  != null)
                    Log.d("zura", "${row} ${column}")
            }
        }

        return matrix
    }

    private fun throwBroadcast(won: Boolean){
        val intent = Intent()
        intent.setAction("com.lashaandzura.mines")
        intent.putExtra(Constants.BROADCAST_KEY, won)
        sendBroadcast(intent)
    }

    private fun registerMineReceiver(){
        mineReceiver = MineReceiver()
        IntentFilter("com.lashaandzura.mines").also {
            registerReceiver(mineReceiver, it)
        }
    }

    private fun createChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val lasha = NotificationChannel(
                NotificationUtil.CHANNEL_ID, NotificationUtil.CHANNEL_ID,
                NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = "Mine Notification"
            }

            val manager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(lasha)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mineReceiver)
    }
}