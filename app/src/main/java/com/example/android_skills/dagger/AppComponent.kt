package com.example.android_skills.dagger

import android.app.Application
import com.example.android_skills.dagger.view.ViewComponent
import com.example.android_skills.dagger.view.ViewModelModule
import com.example.android_skills.dagger.source.SourceComponent
import com.example.android_skills.dagger.source.SourceModule
import com.example.android_skills.dagger.view.RepositoryProvider
import com.example.android_skills.view.MainActivity
import com.example.android_skills.view.fragments.StoriesFragment
import com.example.android_skills.viewmodel.DaggerViewModel
import dagger.*
import dagger.android.AndroidInjectionModule
import dagger.android.ContributesAndroidInjector
import javax.inject.Singleton

@Component(modules = [ViewModelModule::class, RepositoryProvider::class, AndroidInjectionModule::class, ViewModule::class, FragmentModule::class])
@Singleton
interface AppComponent {
    fun createSourceComponent(sourceModule: SourceModule): SourceComponent
    fun createViewComponent(): ViewComponent

    fun inject(application: DaggerApp)

    fun provideViewModel(): DaggerViewModel

    @Component.Builder
    interface Builder{
        fun build(): AppComponent

        @BindsInstance
        fun bindApplication(application: Application): Builder
    }
}

@Module
abstract class ViewModule {
    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun contributeActivityAndroidInjector(): MainActivity
}

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun contributeFragmentViewModelInjector(): StoriesFragment
}