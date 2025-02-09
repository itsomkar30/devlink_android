package com.devlink.app.data

import androidx.compose.runtime.mutableStateListOf

data class DummyData(
    val name :String,
    val skill:String
)


val dummyDataList = mutableStateListOf(
    DummyData("Omkar",  "Kotlin"),
    DummyData("Jitesh",  "Kotlin"),
    DummyData("Ujwal",  "Kotlin"),
    DummyData("Dhruv",  "Kotlin"),
    DummyData("OmkarK",  "Kotlin")
)

fun dummyDataDelete (item :DummyData){
    dummyDataList.remove(item)
}