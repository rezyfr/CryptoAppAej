package com.rezyfr.cryptoapp.persistence

import android.content.Context
import androidx.room.Room
import com.rezyfr.cryptoapp.persistence.dao.FeedDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {
    @Provides
    fun provideCryptoDb(@ApplicationContext appContext: Context): CryptoDb {
        return Room.databaseBuilder(appContext, CryptoDb::class.java, "crypto.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideFeedDao(cryptoDb: CryptoDb): FeedDao {
        return cryptoDb.feedDao()
    }
}