package com.example.prueba.dagger

import com.example.prueba.NasaActivity
import com.example.prueba.views.ApolloFragment
import com.example.prueba.views.FavoriteFragment
import dagger.Component
import javax.inject.Singleton


@Component(modules = [AppModule::class, UtilsModule::class])
@Singleton
interface AppComponent {

    fun doInjection(nasaActivity: NasaActivity)
    fun doInjection(favoriteFragment: FavoriteFragment)
    fun doInjection(apolloFragment: ApolloFragment)


}