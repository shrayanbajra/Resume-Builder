package com.example.resumebuilder.ui.create_resume.view_pdf

import com.example.resumebuilder.data.entities.*
import com.example.resumebuilder.utils.Constants
import com.itextpdf.text.Paragraph

class PdfWriteHelper {

    fun getEducationDetails(educationDetails: List<EducationDetails>?): com.itextpdf.text.List {

        val list = com.itextpdf.text.List()

        educationDetails?.forEach { education ->

            val stringBuilder = StringBuilder()

            stringBuilder.append(education.courseOrDegree).append(Constants.NEW_LINE)

            if (education.passingYear != null)
                stringBuilder.append("Passing Year: ${education.passingYear}")
                    .append(Constants.NEW_LINE)

            if (education.score != null)
                stringBuilder.append("Percentage/CGPA: ${education.score}")
                    .append(Constants.NEW_LINE)

            val courseOrDegree = stringBuilder.append(Constants.NEW_LINE).toString()

            list.add(courseOrDegree)

        }

        return list

    }

    fun getSkills(skills: List<Skill>?): com.itextpdf.text.List {

        val list = com.itextpdf.text.List()

        skills?.forEach { skill ->

            val stringBuilder = StringBuilder()

            stringBuilder.append(skill.title).append(Constants.NEW_LINE)
            val skillName = stringBuilder.toString()

            list.add(skillName)

        }

        return list

    }

    fun getWorkSummary(workSummary: List<WorkDetails>?): com.itextpdf.text.List {

        val list = com.itextpdf.text.List()

        workSummary?.forEach { work ->

            val stringBuilder = StringBuilder()

            stringBuilder.append(work.companyName).append(Constants.NEW_LINE)

            if (work.startDate != null)
                stringBuilder.append("Start Date: " + work.startDate)
                    .append(Constants.TAB_LENGTH_SPACES)

            if (work.endDate != null)
                stringBuilder.append("End Date: " + work.endDate).append(Constants.NEW_LINE)

            val workDetails = stringBuilder.append(Constants.NEW_LINE).toString()

            list.add(workDetails)

        }

        return list

    }

    fun getProjects(projects: List<Project>?): com.itextpdf.text.List {

        val list = com.itextpdf.text.List()

        projects?.forEach { project ->

            val stringBuilder = StringBuilder()

            stringBuilder.append(project.projectName).append(Constants.NEW_LINE)

            if (project.teamSize != null)
                stringBuilder.append("Team Size: " + project.teamSize)
                    .append(Constants.TAB_LENGTH_SPACES)

            if (project.role != null)
                stringBuilder.append("Role: " + project.role).append(Constants.NEW_LINE)

            if (project.technologyUsed != null)
                stringBuilder.append("Technologies Used: " + project.technologyUsed)
                    .append(Constants.NEW_LINE)

            if (project.summary != null)
                stringBuilder.append("Summary: " + Constants.NEW_LINE + project.summary)
                    .append(Constants.NEW_LINE)

            val workDetails = stringBuilder.append(Constants.NEW_LINE).toString()

            list.add(workDetails)

        }

        return list

    }

    fun getContactDetails(resume: Resume): Paragraph {

        val stringBuilder = StringBuilder().apply {

            resume.mobileNumber?.let {
                append("Mobile Number: $it")
                append(Constants.NEW_LINE)
            }

            resume.emailAddress?.let {
                append("Email Address: $it")
                append(Constants.NEW_LINE)
            }

            resume.address?.let {
                append("Address: $it")
                append(Constants.NEW_LINE)
            }

        }

        val contactDetails = stringBuilder.toString()

        return Paragraph(contactDetails)

    }

}