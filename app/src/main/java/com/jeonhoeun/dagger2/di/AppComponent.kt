package com.jeonhoeun.dagger2.di

import android.app.Application
import android.content.Context
import com.jeonhoeun.dagger2.main.MainActivityComponent
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Dagger with Android 예제
 */

@Singleton
@Component( modules=[AppModule::class])
interface AppComponent {
    fun inject(app:Application)

    fun mainActivityComponentBuilder() : MainActivityComponent.Builder

    @Component.Factory
    interface Factory{
        fun create(
            @BindsInstance app:Application
        ) : AppComponent
    }
}

@Module(subcomponents = [MainActivityComponent::class])
class AppModule{
    @Provides
    @Singleton
    fun provideSharedPreferences(app:Application) =
        app.getSharedPreferences("default", Context.MODE_PRIVATE)
}