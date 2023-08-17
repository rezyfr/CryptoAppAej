package com.rezyfr.cryptoapp.persistence.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "feed_entity")
data class FeedEntity (
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "fullName") val fullName: String,
    @ColumnInfo(name = "imageUrl") val imageUrl: String,
    @ColumnInfo(name = "price") val price: Double,
    @ColumnInfo(name = "changePctDay") val changePctDay: Float
)