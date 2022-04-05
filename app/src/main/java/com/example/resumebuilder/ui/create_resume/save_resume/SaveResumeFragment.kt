package com.example.resumebuilder.ui.create_resume.save_resume

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.resumebuilder.R
import com.example.resumebuilder.databinding.FragmentSaveResumeBinding
import com.example.resumebuilder.ui.ResumeViewModel
import com.example.resumebuilder.utils.getText
import com.example.resumebuilder.utils.setErrorIfInvalid
import com.example.resumebuilder.utils.showToast

class SaveResumeFragment : Fragment() {

    private var _binding: FragmentSaveResumeBinding? = null
    private val mBinding get() = _binding!!

    private val mViewModel: ResumeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSaveResumeBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()

        mBinding.tilFileName.editText?.setText(mViewModel.resume.fileName)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnPreviousListener()
        btnSaveListener()

    }

    private fun btnPreviousListener() {
        mBinding.btnPrevious.setOnClickListener { findNavController().navigateUp() }
    }

    private fun btnSaveListener() {
        mBinding.btnSave.setOnClickListener {

            mBinding.tilFileName.setErrorIfInvalid(errorMessage = "Please enter filename")

            val fileName = mBinding.tilFileName.getText()
            if (fileName.isBlank()) return@setOnClickListener

            mViewModel.resume.fileName = fileName

            saveResumeToDatabase()

        }
    }

    private fun saveResumeToDatabase() {

        mViewModel.saveResumeToDatabase(resume = mViewModel.resume)
            .observe(viewLifecycleOwner) { wasSaved ->

                if (wasSaved) {

                    showSuccessDialog()

                } else {

                    showToast(message = "Resume Save Failed")

                }

            }

    }

    private fun showSuccessDialog() {
        val builder = AlertDialog.Builder(requireContext())
            .setTitle(R.string.saved)
            .setMessage(R.string.resume_has_been_saved)
            .setPositiveButton(R.string.ok) { dialog, _ ->
                dialog.dismiss()
                findNavController().navigate(R.id.action_saveResumeFragment_to_viewPdfFragment)
            }

        builder.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}