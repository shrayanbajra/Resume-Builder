package com.example.resumebuilder.ui.resumes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.resumebuilder.R
import com.example.resumebuilder.databinding.FragmentResumesBinding
import com.example.resumebuilder.ui.ResumeViewModel
import com.example.resumebuilder.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResumesFragment : Fragment() {

    private var _binding: FragmentResumesBinding? = null
    private val mBinding get() = _binding!!

    private val mResumeAdapter by lazy { ResumeAdapter() }

    private val mViewModel by lazy { ViewModelProvider(this)[ResumeViewModel::class.java] }

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

        mBinding.fabAddResume.setOnClickListener {

            findNavController().navigate(R.id.action_resumesFragment_to_createResumeFragment)

        }

    }

    private fun initRvResumes() {
        mBinding.rvResumes.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}