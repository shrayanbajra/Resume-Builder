package com.example.resumebuilder.ui.create_resume.personal_info

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.resumebuilder.R
import com.example.resumebuilder.data.entities.Resume
import com.example.resumebuilder.databinding.FragmentPersonalInfoBinding
import com.example.resumebuilder.ui.ResumeViewModel
import com.example.resumebuilder.utils.getText
import com.example.resumebuilder.utils.setErrorIfInvalid
import com.example.resumebuilder.utils.showToast
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class PersonalInfoFragment : Fragment() {

    private var _binding: FragmentPersonalInfoBinding? = null
    private val mBinding get() = _binding!!

    private var mProfilePhotoUri: Uri? = null

    private val mViewModel: ResumeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPersonalInfoBinding.inflate(inflater, container, false)
        return mBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnPickImageListener()
        btnNextListener()

    }

    override fun onStart() {
        super.onStart()

        mBinding.apply {

            mViewModel.resume.profilePhoto?.let { ivProfilePhoto.setImageURI(Uri.parse(it)) }
            tilFullName.editText?.setText(mViewModel.resume.fullName)
            tilEmailAddress.editText?.setText(mViewModel.resume.emailAddress)
            tilMobileNumber.editText?.setText(mViewModel.resume.mobileNumber)
            tilAddress.editText?.setText(mViewModel.resume.address)

        }

    }

    private fun btnNextListener() {
        mBinding.apply {

            btnNext.setOnClickListener {

                validateInputFields()

                if (areAllFieldsValid()) {

                    val photoUri = if (mProfilePhotoUri == null)
                        null
                    else
                        mProfilePhotoUri.toString()

                    mViewModel.resume.apply {
                        profilePhoto = photoUri
                        fullName = tilFullName.getText()
                        emailAddress = tilEmailAddress.getText()
                        mobileNumber = tilMobileNumber.getText()
                        address = tilAddress.getText()
                    }

                    Timber.d("Resume values -> ${mViewModel.resume}")
                    findNavController().navigate(R.id.action_personalInfoFragment_to_workSummaryFragment)
//                    saveResumeToDatabase()

                }

            }

        }
    }

    private fun FragmentPersonalInfoBinding.validateInputFields() {
        tilFullName.setErrorIfInvalid(errorMessage = "Please enter full name")
        tilEmailAddress.setErrorIfInvalid(errorMessage = "Please enter email address")
        tilMobileNumber.setErrorIfInvalid(errorMessage = "Please enter mobile number")
        tilAddress.setErrorIfInvalid(errorMessage = "Please enter address")
    }

    fun areAllFieldsValid(): Boolean {

        mBinding.apply {

            return tilFullName.error.isNullOrBlank()
                    && tilEmailAddress.error.isNullOrBlank()
                    && tilMobileNumber.error.isNullOrBlank()
                    && tilAddress.error.isNullOrBlank()

        }

    }

    private fun saveResumeToDatabase() {

        val profilePhoto = if (mProfilePhotoUri == null) null else mProfilePhotoUri.toString()

        val resume = Resume(
            profilePhoto = profilePhoto,
            fullName = mBinding.tilFullName.getText(),
            emailAddress = mBinding.tilEmailAddress.getText(),
            mobileNumber = mBinding.tilMobileNumber.getText(),
            address = mBinding.tilAddress.getText()
        )

        mViewModel.saveResumeToDatabase(resume = resume).observe(viewLifecycleOwner) { wasSaved ->

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
                findNavController().popBackStack()
            }

        builder.create().show()
    }

    private fun btnPickImageListener() {
        mBinding.btnChoosePhoto.setOnClickListener {

            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .createIntent { intent -> imageResult.launch(intent) }

        }
    }

    private val imageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            when (resultCode) {
                Activity.RESULT_OK -> {
                    //Image Uri will not be null for RESULT_OK
                    val fileUri = data?.data!!

                    mProfilePhotoUri = fileUri
                    mBinding.ivProfilePhoto.setImageURI(fileUri)

                }
                ImagePicker.RESULT_ERROR -> {

                    Toast.makeText(context, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()

                }
                else -> {

                    Toast.makeText(context, "Task Cancelled", Toast.LENGTH_SHORT).show()

                }
            }
        }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}