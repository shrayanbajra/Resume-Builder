package com.example.resumebuilder.ui

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.resumebuilder.data.Resume
import com.example.resumebuilder.databinding.FragmentCreateResumeBinding
import com.example.resumebuilder.utils.getText
import com.example.resumebuilder.utils.setErrorIfInvalid
import com.example.resumebuilder.utils.showToast
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CreateResumeFragment : Fragment() {

    private var _binding: FragmentCreateResumeBinding? = null
    private val mBinding get() = _binding!!

    private var mProfilePhotoUri: Uri? = null

    private val resume by lazy {
        Resume(
            profilePhoto = null, fullName = "", emailAddress = "", phoneNumber = "", address = ""
        )
    }

    private val mViewModel by lazy { ViewModelProvider(this)[CreateResumeViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCreateResumeBinding.inflate(inflater, container, false)
        return mBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnPickImageListener()
        fabSaveImageListener()

    }

    private fun fabSaveImageListener() {
        mBinding.apply {

            btnSave.setOnClickListener {

                tilFullName.setErrorIfInvalid(errorMessage = "Please enter full name")
                tilEmailAddress.setErrorIfInvalid(errorMessage = "Please enter email address")
                tilPhoneNumber.setErrorIfInvalid(errorMessage = "Please enter phone number")
                tilAddress.setErrorIfInvalid(errorMessage = "Please enter address")

                if (areAllFieldsValid()) {
                    saveResumeToDatabase()
                }

            }

        }
    }

    fun areAllFieldsValid(): Boolean {

        mBinding.apply {

            return tilFullName.error.isNullOrBlank()
                    && tilEmailAddress.error.isNullOrBlank()
                    && tilPhoneNumber.error.isNullOrBlank()
                    && tilAddress.error.isNullOrBlank()

        }


    }

    private fun saveResumeToDatabase() {

        val profilePhoto = if (mProfilePhotoUri == null) null else mProfilePhotoUri.toString()

        val resume = Resume(
            profilePhoto = profilePhoto,
            fullName = mBinding.tilFullName.getText(),
            emailAddress = mBinding.tilEmailAddress.getText(),
            phoneNumber = mBinding.tilPhoneNumber.getText(),
            address = mBinding.tilAddress.getText()
        )

        mViewModel.saveResumeToDatabase(resume = resume).observe(viewLifecycleOwner) { wasSaved ->

            if (wasSaved) {

                showToast(message = "Insert Success")

            } else {

                showToast(message = "Insert Failed")

            }

        }
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