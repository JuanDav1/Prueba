package com.example.prueba.db

import androidx.room.*
import com.example.prueba.models.*

@Dao
interface DataBaseDao {


    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertApollo(dataApollo: List<Apollo>)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKeywords(keyWords: List<KeyWords>)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(apollo: Favorite)

    @Query("UPDATE Apollo SET isFavorite= 1 WHERE id=:id")
    suspend fun updateApolloToFavorite(id:Int)

    @Transaction
    @Query("SELECT  * FROM Apollo ")
    suspend fun getApollo(): List<Apollo>

    @Query("SELECT  * FROM Favorite ")
    suspend fun getFavorite(): List<Favorite>
/*
    @Query("""SELECT * FROM Apollo ap1 INNER JOIN KeyWords ap2 WHERE
                         (SELECT ap2.id_apollo FROM KeyWords ap2 WHERE ap2.keyword LIKE '%' || :keyWords || '%' )= ap1.id""")
    suspend fun filterApollo(keyWords: String):List<Apollo>*/

    @Query(" SELECT ap2.id_apollo FROM KeyWords ap2 WHERE ap2.keyword LIKE '%' || :keyWords || '%' ")
    suspend fun filterApollo(keyWords: String):List<Int>

    @Transaction
    @Query("Select * FROM Apollo INNER JOIN Favorite on Apollo.id = Favorite.id_apollo")
    suspend fun getFavoriteWithApollo():List<Apollo>

   @Query("DELETE  FROM Apollo ")
   suspend fun deleteApollo()

    @Query("Delete FROM Favorite")
    suspend fun deleteFavorite()

    @Query("DELETE FROM KeyWords")
    suspend fun deleteKeyWords()

    @Query("Select count(*) From Apollo")
    suspend fun emptyData():Int






    //@Query("SELECT * FROM(Select href From Link  UNION ALL Select dateCreated, title, mediaType,description,location FROM Data)")
    //suspend fun getApollo():List<Apollo>
}