package com.example.prueba.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prueba.models.Apollo
import com.example.prueba.repositories.LocalRepository
import com.example.prueba.repositories.RemoteRepository
import kotlinx.coroutines.launch


class FavoriteViewModel (
    private val remoteRepository: RemoteRepository,
    private val localReposiroty: LocalRepository
) : ViewModel(){

    private val _getFavoriteWithApollo = MutableLiveData<List<Apollo>>()
    val getFavoriteWithApollo: LiveData<List<Apollo>> = _getFavoriteWithApollo


    fun getFavoriteWithApollo(){
    viewModelScope.launch {

       _getFavoriteWithApollo.postValue(localReposiroty.getFavoriteWithApollo())
    }
}
    fun getApollo(){
        viewModelScope.launch {
        localReposiroty.getApollo()
        }
    }

}