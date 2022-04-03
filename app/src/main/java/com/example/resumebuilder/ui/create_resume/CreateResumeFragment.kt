package com.example.resumebuilder.ui.create_resume

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.resumebuilder.R
import com.example.resumebuilder.databinding.FragmentCreateResumeBinding
import com.example.resumebuilder.utils.SingleParamClickListener
import com.example.resumebuilder.utils.showToast

class CreateResumeFragment : Fragment() {

    private var _binding: FragmentCreateResumeBinding? = null
    private val mBinding get() = _binding!!

    private val mSectionAdapter by lazy { SectionAdapter(clickListener = mSectionClickListener) }
    private val mSectionClickListener
        get() = object : SingleParamClickListener<ResumeSections> {

            override fun onItemClicked(item: ResumeSections) {

                when (item) {

                    ResumeSections.PERSONAL_INFORMATION -> {
                        findNavController().navigate(R.id.action_createResumeFragment_to_personalInfoFragment)
                    }

                    else -> {
                        showToast(message = item.title)
                    }

                }

            }

        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateResumeBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRvSections()
        mSectionAdapter.setData(ResumeSections.values().toList())

    }

    private fun initRvSections() {
        mBinding.rvSections.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = mSectionAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}