package com.atech.core.module

import android.content.Context
import android.content.SharedPreferences
import com.atech.core.utils.BitAppScope
import com.atech.core.utils.SharePrefKeys
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(SharePrefKeys.SharedPreferenceName.name, Context.MODE_PRIVATE)


    @Singleton
    @Provides
    fun getCalender(): Calendar = Calendar.getInstance()


    @BitAppScope
    @Singleton
    @Provides
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())


}