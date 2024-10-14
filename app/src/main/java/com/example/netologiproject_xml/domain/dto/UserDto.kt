package com.example.netologiproject_xml.domain.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.lang.reflect.Constructor


@Entity(tableName = "user_table")
data class UserDto(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "max_point") val max_point: Int,
){
    constructor() : this(0,"",0)
}