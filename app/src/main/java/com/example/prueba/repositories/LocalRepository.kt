package com.example.prueba.repositories

import com.example.prueba.db.DataBaseDao
import com.example.prueba.models.*

class LocalRepository(private var dataBaseDao: DataBaseDao) {

    suspend fun insertApollo(apollo: List<Apollo>){
        dataBaseDao.insertApollo(apollo)
    }

    suspend fun insertKeywords(keyWords: List<KeyWords>){
        dataBaseDao.insertKeywords(keyWords)
    }

    suspend fun insertFavorite(apollo: Favorite){
        dataBaseDao.insertFavorite(apollo)
    }

    suspend fun updateApolloToFavorite(id:Int){
        dataBaseDao.updateApolloToFavorite(id)
    }
    suspend fun getApollo():List<Apollo>{
        return dataBaseDao.getApollo()
    }

    suspend fun filterApollo(keyWord:String):List<Int>{
        return dataBaseDao.filterApollo(keyWord)
    }

    suspend fun getFavorite():List<Favorite>{
        return dataBaseDao.getFavorite()
    }
    suspend fun deleteApollo(){
        dataBaseDao.deleteApollo()
    }

    suspend fun deleteFavorite(){
        dataBaseDao.deleteFavorite()

    }

    suspend fun deletekeyword(){
        dataBaseDao.deleteKeyWords()

    }    suspend fun getFavoriteWithApollo():List<Apollo>{
        return dataBaseDao.getFavoriteWithApollo()
    }
    suspend fun emptyData():Int{
        return dataBaseDao.emptyData()
    }




}
