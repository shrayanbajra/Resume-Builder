package com.example.resumebuilder.ui.create_resume

import androidx.annotation.StringRes
import com.example.resumebuilder.R

enum class ResumeSections(@StringRes val title: Int) {

    PERSONAL_INFORMATION(title = R.string.personal_information),
    SKILLS(title = R.string.skills),
    EDUCATION(title = R.string.education),
    WORK_EXPERIENCE(title = R.string.work_experience)

}