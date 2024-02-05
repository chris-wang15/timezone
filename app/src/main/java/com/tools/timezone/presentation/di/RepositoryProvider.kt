package com.tools.timezone.presentation.di

import android.content.Context
import com.tools.timezone.data.repository.MainRepositoryImpl
import com.tools.timezone.domain.repository.MainRepository
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
        return MainRepositoryImpl(context = appContext)
    }
}