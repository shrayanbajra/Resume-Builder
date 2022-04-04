package com.example.resumebuilder.ui.create_resume.work_summary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.resumebuilder.R
import com.example.resumebuilder.data.entities.WorkDetails
import com.example.resumebuilder.databinding.FragmentWorkSummaryBinding
import com.example.resumebuilder.ui.ResumeViewModel
import com.example.resumebuilder.utils.getText

class WorkSummaryFragment : Fragment() {

    private var _binding: FragmentWorkSummaryBinding? = null
    private val mBinding get() = _binding!!

    private val mWorkSummary: ArrayList<WorkDetails> by lazy {

        val workSummary = arrayListOf<WorkDetails>()
        val summary = mViewModel.resume.workSummary
        if (summary == null) {

            val workDetails = WorkDetails()
            workSummary.clear()
            workSummary.addAll(listOf(workDetails))

        } else {

            workSummary.clear()
            workSummary.addAll(summary)

        }
        workSummary

    }

    private val mWorkDetailsAdapter by lazy {
        WorkDetailsAdapter(mWorkDetails = mWorkSummary, clickListener = mWorkDetailsChangeListener)
    }
    private val mWorkDetailsChangeListener: WorkDetailsAdapter.WorkDetailsChangeListener
        get() = object : WorkDetailsAdapter.WorkDetailsChangeListener {

            override fun onChange(workDetails: WorkDetails, position: Int) {

                mWorkSummary[position] = workDetails

            }

            override fun onDelete(position: Int) {

                mWorkSummary.removeAt(position)
                mWorkDetailsAdapter.removeItemAt(position)

            }

        }

    private val mViewModel: ResumeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkSummaryBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()

        mBinding.apply {

            tilCareerObjective.editText?.setText(mViewModel.resume.careerObjective)
            tilTotalYearsOfExperience.editText?.setText(mViewModel.resume.totalYearsOfExperience)

        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRvWorkSummary()

        fabAddWorkDetailsListener()

        btnPreviousListener()
        btnNextListener()

    }

    private fun initRvWorkSummary() {
        mBinding.rvWorkSummary.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mWorkDetailsAdapter
        }
    }

    private fun fabAddWorkDetailsListener() {
        mBinding.fabAddWorkDetails.setOnClickListener {
            mWorkDetailsAdapter.addNewWorkDetails()
            mBinding.rvWorkSummary.smoothScrollToPosition(mWorkDetailsAdapter.getLastItemIndex())
        }
    }

    private fun btnPreviousListener() {
        mBinding.btnPrevious.setOnClickListener { findNavController().navigateUp() }
    }

    private fun btnNextListener() {
        mBinding.btnNext.setOnClickListener {

            mViewModel.resume.apply {
                careerObjective = mBinding.tilCareerObjective.getText()
                totalYearsOfExperience = mBinding.tilTotalYearsOfExperience.getText()

                workSummary = mWorkDetailsAdapter.getWorkDetails()
            }

            findNavController().navigate(R.id.action_workSummaryFragment_to_skillsFragment)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}