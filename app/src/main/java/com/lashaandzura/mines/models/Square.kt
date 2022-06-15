package com.lashaandzura.mines.models

data class Square(
    val xCord: Int,
    val yCord: Int,
    val isMined: Boolean,
    val nearbyBombsQuantity: Int
)
