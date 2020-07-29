package com.example.prueba.viewmodels

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prueba.api.OperationResult
import com.example.prueba.models.*
import com.example.prueba.repositories.LocalRepository
import com.example.prueba.repositories.RemoteRepository
import kotlinx.coroutines.*
import javax.inject.Inject

class NasaViewModel (
    private val remoteRepository: RemoteRepository,
    private val localReposiroty: LocalRepository
) : ViewModel() {

    private val _responseLiveDataApollo = MutableLiveData<Data>()
    val responseLiveDataApollo: LiveData<Data> = _responseLiveDataApollo




    private val _getDataApollo = MutableLiveData<List<Apollo>>()
    val getDataApollo: LiveData<List<Apollo>> = _getDataApollo

    private val _isViewLoading=MutableLiveData<Boolean>()
    val isViewLoading:LiveData<Boolean> = _isViewLoading

    private val _getFavoriteWithApollo = MutableLiveData<List<Apollo>>()
    val getFavoriteWithApollo: LiveData<List<Apollo>> = _getFavoriteWithApollo

    var emptyData = true

    fun emptyData(){
        viewModelScope.launch {
            emptyData = localReposiroty.emptyData() == 0

        }
    }

    fun loadData() {
        _isViewLoading.postValue(true)
        viewModelScope.launch {
            var result: OperationResult<Data> = withContext(Dispatchers.IO) {
                remoteRepository.getCollection()
            }
            _isViewLoading.postValue(false)
            when (result) {
                is OperationResult.Success -> {
                    if (result.data == null) {
                        //_isEmptyList.postValue(true)
                    } else {

                        if(emptyData){
                        val apollo:ArrayList<Apollo> = arrayListOf()
                            val keyWords: ArrayList<KeyWords> = arrayListOf()
                            var indexkey = 0

                        for ((index,item) in result.data!!.collection.items.withIndex()) {
                            val header: DataX = item.data[0]

                            if (header != null) {

                                if(item.links != null){
                                    for (subItem in item.links) {

                                        if(subItem.href.contains(".jpg") ){
                                            apollo.add(Apollo(index,subItem.href, header.title, header.dateCreated, header.description, header.nasaId, header.mediaType,false))
                                               for (word in header.keywords){
                                                   keyWords.add(KeyWords(indexkey,header.nasaId,word,index))
                                                   indexkey += 1
                                               }

                                            }

                                    }
                                }else{
                                    apollo.add(Apollo(index,"null", header.title, header.dateCreated, header.description, header.nasaId, header.mediaType,false))
                                    for (word in header.keywords){

                                        keyWords.add(KeyWords(indexkey,header.nasaId,word,index))
                                        indexkey += 1
                                    }
                                }
                            }
                        }
                            localReposiroty.insertApollo(apollo)
                            localReposiroty.insertKeywords(keyWords)                    }

                    }
                }
                is OperationResult.Error -> {
                    //_onMessageError.postValue(result.exception)
                    Log.w("api", result.exception)
                }
            }
        }
    }


    fun getApollo(){
        viewModelScope.launch {

            _getDataApollo.postValue( localReposiroty.getApollo())


        }
    }

    fun getFavoriteWithApollo(){
        viewModelScope.launch {

            _getFavoriteWithApollo.postValue(localReposiroty.getFavoriteWithApollo())
        }

    }


    fun addFavorite(apollo: Apollo){
        viewModelScope.launch {
 val favorite = Favorite(apollo.id,true)
            localReposiroty.insertFavorite(favorite)
            localReposiroty.updateApolloToFavorite(apollo.id)
        }
    }

    fun filterAPollo(keyWord: String){
        viewModelScope.launch {
            localReposiroty.filterApollo(keyWord)
    }}

        fun deleteTable(){
            viewModelScope.launch {
            localReposiroty.deleteApollo()
            localReposiroty.deleteFavorite()
            localReposiroty.deletekeyword()
            }
        }

}