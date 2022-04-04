package com.example.resumebuilder.ui.create_resume.project_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.resumebuilder.R
import com.example.resumebuilder.data.entities.Project
import com.example.resumebuilder.databinding.FragmentProjectDetailsBinding
import com.example.resumebuilder.ui.ResumeViewModel

class ProjectDetailsFragment : Fragment() {

    private var _binding: FragmentProjectDetailsBinding? = null
    private val mBinding get() = _binding!!

    private val mProjects: ArrayList<Project> by lazy {

        val projects = arrayListOf<Project>()
        val currentProjects = mViewModel.resume.projects
        if (currentProjects == null) {

            val project = Project()
            projects.clear()
            projects.addAll(listOf(project))

        } else {

            projects.clear()
            projects.addAll(currentProjects)

        }
        projects

    }

    private val mProjectsAdapter by lazy {
        ProjectAdapter(mProjects = mProjects, clickListener = mProjectsChangeListener)
    }
    private val mProjectsChangeListener: ProjectAdapter.ProjectChangeListener
        get() = object : ProjectAdapter.ProjectChangeListener {

            override fun onChange(project: Project, position: Int) {

                mProjects[position] = project

            }

            override fun onDelete(position: Int) {

                mProjects.removeAt(position)
                mProjectsAdapter.removeItemAt(position)

            }

        }

    private val mViewModel: ResumeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProjectDetailsBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRvProjects()

        fabAddProjectListener()

        btnPreviousListener()
        btnNextListener()

    }

    private fun initRvProjects() {
        mBinding.rvProjects.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mProjectsAdapter
        }
    }

    private fun fabAddProjectListener() {
        mBinding.fabAddProject.setOnClickListener {
            mProjectsAdapter.addNewProject()
            mBinding.rvProjects.smoothScrollToPosition(mProjectsAdapter.getLastItemIndex())
        }
    }

    private fun btnPreviousListener() {
        mBinding.btnPrevious.setOnClickListener { findNavController().navigateUp() }
    }

    private fun btnNextListener() {
        mBinding.btnNext.setOnClickListener {

            mViewModel.resume.projects = mProjectsAdapter.getProjects()

            findNavController().navigate(R.id.action_projectDetailsFragment_to_saveResumeFragment)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}