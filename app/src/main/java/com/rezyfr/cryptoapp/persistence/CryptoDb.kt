package com.rezyfr.cryptoapp.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rezyfr.cryptoapp.persistence.dao.FeedDao
import com.rezyfr.cryptoapp.persistence.entity.FeedEntity

@Database(entities = [FeedEntity::class], version = 1)
abstract class CryptoDb : RoomDatabase() {
    abstract fun feedDao(): FeedDao
}