package com.example.resumebuilder.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.resumebuilder.utils.Constants

@Entity(tableName = Constants.RESUME_TABLE)
data class Resume(

    @ColumnInfo(name = "profile_photo")
    var profilePhoto: String?,

    @ColumnInfo(name = "full_name")
    var fullName: String,

    @ColumnInfo(name = "email_address")
    var emailAddress: String,

    @ColumnInfo(name = "phone_number")
    var phoneNumber: String,

    @ColumnInfo(name = "address")
    var address: String

) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = Constants.NO_PRIMARY_KEY

}