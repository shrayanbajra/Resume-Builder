package com.example.resumebuilder.ui.create_resume.education_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.resumebuilder.R
import com.example.resumebuilder.data.entities.EducationDetails
import com.example.resumebuilder.databinding.FragmentEducationDetailsBinding
import com.example.resumebuilder.ui.ResumeViewModel

class EducationDetailsFragment : Fragment() {

    private var _binding: FragmentEducationDetailsBinding? = null
    private val mBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEducationDetailsBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    private val mEducationDetails: ArrayList<EducationDetails> by lazy {

        val educationDetails = arrayListOf<EducationDetails>()
        val currentEducationDetails = mViewModel.resume.educationDetails
        if (currentEducationDetails == null) {

            val emptyEducationDetails = EducationDetails()
            educationDetails.clear()
            educationDetails.addAll(listOf(emptyEducationDetails))

        } else {

            educationDetails.clear()
            educationDetails.addAll(currentEducationDetails)

        }
        educationDetails

    }

    private val mEducationDetailsAdapter by lazy {
        EducationDetailsAdapter(
            mEducationDetails = mEducationDetails,
            clickListener = mEducationDetailsChangeListener
        )
    }
    private val mEducationDetailsChangeListener: EducationDetailsAdapter.EducationDetailsChangeListener
        get() = object : EducationDetailsAdapter.EducationDetailsChangeListener {

            override fun onChange(educationDetails: EducationDetails, position: Int) {

                mEducationDetails[position] = educationDetails

            }

            override fun onDelete(position: Int) {

                mEducationDetails.removeAt(position)
                mEducationDetailsAdapter.removeItemAt(position)

            }

        }

    private val mViewModel: ResumeViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRvEducationDetails()

        fabAddEducationDetailsListener()

        btnPreviousListener()
        btnNextListener()

    }

    private fun initRvEducationDetails() {
        mBinding.rvEducationDetails.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mEducationDetailsAdapter
        }
    }

    private fun fabAddEducationDetailsListener() {
        mBinding.fabAddEducationDetails.setOnClickListener {
            mEducationDetailsAdapter.addNewEducationDetails()
            mBinding.rvEducationDetails.smoothScrollToPosition(mEducationDetailsAdapter.getLastItemIndex())
        }
    }

    private fun btnPreviousListener() {
        mBinding.btnPrevious.setOnClickListener { findNavController().navigateUp() }
    }

    private fun btnNextListener() {
        mBinding.btnNext.setOnClickListener {

            mViewModel.resume.educationDetails = mEducationDetailsAdapter.getEducationDetails()
            findNavController().navigate(R.id.action_educationDetailsFragment_to_projectDetailsFragment)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}