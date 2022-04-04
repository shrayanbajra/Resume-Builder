package com.example.resumebuilder.ui.resumes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.resumebuilder.R
import com.example.resumebuilder.data.entities.Resume
import com.example.resumebuilder.databinding.FragmentResumesBinding
import com.example.resumebuilder.ui.ResumeViewModel
import com.example.resumebuilder.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResumesFragment : Fragment() {

    private var _binding: FragmentResumesBinding? = null
    private val mBinding get() = _binding!!

    private val mResumeAdapter by lazy { ResumeAdapter() }

    private val mViewModel: ResumeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResumesBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRvResumes()
        getAllResumes()

        fabAddResumeListener()

    }

    private fun initRvResumes() {
        mBinding.rvResumes.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mResumeAdapter
        }
    }

    private fun getAllResumes() {
        mViewModel.getAllResumesFromDatabase().observe(viewLifecycleOwner) { resumes ->

            if (resumes.isNullOrEmpty()) {

                showToast(message = "No resumes found")

            } else {

                mResumeAdapter.setData(resumes = resumes)

            }

        }
    }

    private fun fabAddResumeListener() {
        mBinding.fabAddResume.setOnClickListener {

            mViewModel.isNew = true
            mViewModel.resume = Resume()
            findNavController().navigate(R.id.action_resumesFragment_to_personalInfoFragment)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}