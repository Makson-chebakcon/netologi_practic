package com.example.netologiproject_xml.domain.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import com.example.netologiproject_xml.domain.dto.TopUsersDto


@Dao
interface TopUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg topUser: TopUsersDto):Unit

    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT * FROM top_user_table")
    fun getAllUsers():List<TopUsersDto>

    @RewriteQueriesToDropUnusedColumns
    @Delete
    suspend fun delete(topUser: TopUsersDto)


}