package com.example.birdgame

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    val points =MutableLiveData<Int>()

    init {
        points.value = 0
    }

    fun addPoints() {
        points.value = points.value?.plus(1)
    }

}