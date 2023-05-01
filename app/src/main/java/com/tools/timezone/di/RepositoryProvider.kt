package com.tools.timezone.di

import android.content.Context
import com.tools.timezone.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryProvider {

    @Singleton
    @Provides
    fun provideMainRepository(
        @ApplicationContext appContext: Context
    ): MainRepository {
        return MainRepository().apply { init(context = appContext) }
    }
}