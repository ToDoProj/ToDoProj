package ru.androidlab.todoproj.adapters

sealed class Item{
    data class Card(val textTitle: String, val textTime: String, val alarmClockImage: Int, val time: String): Item()
}