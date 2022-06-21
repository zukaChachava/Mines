package com.lashaandzura.mines.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.lashaandzura.mines.GameActivity
import com.lashaandzura.mines.R

class HomeFragment: Fragment(R.layout.fragment_home) {
    private lateinit var startButton: Button
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startButton = view.findViewById<Button>(R.id.startBtn)
        startButton.setOnClickListener {
            val intent = Intent(context, GameActivity::class.java)
            startActivity(intent)
        }
    }
}