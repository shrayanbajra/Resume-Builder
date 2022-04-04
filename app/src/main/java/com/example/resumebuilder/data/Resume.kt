package com.example.resumebuilder.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.resumebuilder.utils.Constants

@Entity(tableName = Constants.RESUME_TABLE)
data class Resume(

    @ColumnInfo(name = "profile_photo")
    var profilePhoto: String? = null,

    @ColumnInfo(name = "full_name")
    var fullName: String? = null,

    @ColumnInfo(name = "email_address")
    var emailAddress: String? = null,

    @ColumnInfo(name = "mobile_number")
    var mobileNumber: String? = null,

    @ColumnInfo(name = "address")
    var address: String? = null,

    @ColumnInfo(name = "career_objective")
    var careerObjective: String? = null,

    @ColumnInfo(name = "total_years_of_experience")
    var totalYearsOfExperience: String? = null,

    @ColumnInfo(name = "work_summary")
    var workSummary: List<WorkDetails>? = null,

    @ColumnInfo(name = "skills")
    var skills: List<Skill>? = null,

    @ColumnInfo(name = "education_details")
    var educationDetails: List<EducationDetails>? = null

) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = Constants.NO_PRIMARY_KEY

}