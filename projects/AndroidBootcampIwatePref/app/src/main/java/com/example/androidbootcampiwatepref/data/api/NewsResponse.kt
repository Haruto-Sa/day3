package com.example.androidbootcampiwatepref.data.api

import kotlinx.serialization.Serializable

@Serializable
data class NewsResponse(
    val news: List<FoodItem>,
) {

    @Serializable
    data class FoodItem(
        val id: Int,
        val title: String,
        val description: String,
        val image: String,
        val textdes: String
    )
}