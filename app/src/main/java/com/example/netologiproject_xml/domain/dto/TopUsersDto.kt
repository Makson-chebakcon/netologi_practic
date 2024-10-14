package com.example.netologiproject_xml.domain.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "top_user_table")
data class TopUsersDto(
    @PrimaryKey(autoGenerate = true) val game_id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "point") val point: Int,
){
    constructor():this(0,"",0)
}