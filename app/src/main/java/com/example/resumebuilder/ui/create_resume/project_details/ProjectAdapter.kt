package com.example.resumebuilder.ui.create_resume.project_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.resumebuilder.data.entities.Project
import com.example.resumebuilder.databinding.ItemProjectBinding

class ProjectAdapter(
    private val mProjects: ArrayList<Project>,
    private val clickListener: ProjectChangeListener
)

    : RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder>() {

    interface ProjectChangeListener {

        fun onChange(project: Project, position: Int)
        fun onDelete(position: Int)

    }

    fun addNewProject() {
        val project = Project()
        mProjects.add(project)
        notifyItemInserted(mProjects.lastIndex)
    }

    fun getLastItemIndex() = mProjects.lastIndex

    fun setData(projects: List<Project>) {
        mProjects.clear()
        mProjects.addAll(projects)
        notifyDataSetChanged()
    }

    fun onItemUpdated(position: Int, project: Project) {
        mProjects[position] = project
    }

    fun removeItemAt(position: Int) {
        notifyItemRemoved(position)
    }

    fun getProjects(): List<Project> {

        val hasOnlyPlaceholderProject = itemCount == 1 && mProjects[0] == Project()
        return if (hasOnlyPlaceholderProject)
            emptyList()
        else
            mProjects

    }

    override fun getItemCount() = mProjects.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val itemBinding = ItemProjectBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ProjectViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {

        val project = mProjects[position]
        holder.bind(project, position)

    }

    inner class ProjectViewHolder(private val binding: ItemProjectBinding)

        : RecyclerView.ViewHolder(binding.root) {

        fun bind(project: Project, position: Int) {

            binding.apply {

                tilProjectName.editText?.setText(project.projectName)
                tilProjectName.editText?.doAfterTextChanged { text ->

                    project.projectName = if (text.isNullOrBlank()) "" else text.toString()
                    clickListener.onChange(project = project, position = position)

                }

                tilTeamSize.editText?.setText(project.teamSize)
                tilTeamSize.editText?.doAfterTextChanged { text ->

                    project.teamSize = if (text.isNullOrBlank()) "" else text.toString()
                    clickListener.onChange(project = project, position = position)

                }

                tilRole.editText?.setText(project.role)
                tilRole.editText?.doAfterTextChanged { text ->

                    project.role = if (text.isNullOrBlank()) "" else text.toString()
                    clickListener.onChange(project = project, position = position)

                }

                tilTechnologyUsed.editText?.setText(project.technologyUsed)
                tilTechnologyUsed.editText?.doAfterTextChanged { text ->

                    project.technologyUsed = if (text.isNullOrBlank()) "" else text.toString()
                    clickListener.onChange(project = project, position = position)

                }

                tilSummary.editText?.setText(project.summary)
                tilSummary.editText?.doAfterTextChanged { text ->

                    project.summary = if (text.isNullOrBlank()) "" else text.toString()
                    clickListener.onChange(project = project, position = position)

                }

                btnDelete.setOnClickListener {

                    clickListener.onDelete(position = position)

                }

            }

        }

    }

}