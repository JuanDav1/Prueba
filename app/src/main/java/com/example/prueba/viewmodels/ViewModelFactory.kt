package com.example.prueba.viewmodels

import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.prueba.repositories.LocalRepository
import com.example.prueba.repositories.RemoteRepository
import javax.inject.Inject


class ViewModelFactory @Inject constructor(
    var remoteRepository: RemoteRepository,
   var localRepository: LocalRepository
) : ViewModelProvider.Factory {

    @NonNull
    override fun <T : ViewModel> create(@NonNull modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(NasaViewModel::class.java)) {
           return NasaViewModel(remoteRepository,localRepository) as T

        }else if(modelClass.isAssignableFrom(FavoriteViewModel::class.java)){
        return FavoriteViewModel(remoteRepository,localRepository) as T
        }else{

            return throw IllegalArgumentException("Unknown class name")
        }



    }
}
