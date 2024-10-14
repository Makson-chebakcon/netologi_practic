package com.example.netologiproject_xml.domain.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import androidx.room.Update
import com.example.netologiproject_xml.domain.dto.UserDto

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg userDto: UserDto)

    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT * FROM user_table LIMIT 1")
    suspend fun getUser(): UserDto?

    @Update
    suspend fun update(userDto: UserDto)
}
