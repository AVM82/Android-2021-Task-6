package rs.school.rs.android2021task6.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import rs.school.rs.exoplayer.service.ServiceConnection
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideServiceConnection(@ApplicationContext context: Context) = ServiceConnection(context)
}
