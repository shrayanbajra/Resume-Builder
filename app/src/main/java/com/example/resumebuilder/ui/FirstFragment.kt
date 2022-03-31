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
import com.example.resumebuilder.database.ResumeEntity
import com.example.resumebuilder.databinding.FragmentFirstBinding
import com.example.resumebuilder.utils.showToast
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val mBinding get() = _binding!!

    private var mProfileUri: Uri? = null

    private val mViewModel by lazy { ViewModelProvider(this)[FirstViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return mBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnPickImageListener()
        fabSaveImageListener()


    }

    private fun fabSaveImageListener() {
        mBinding.fabSaveImage.setOnClickListener {

            if (mProfileUri == null) {

                mViewModel.getResumeFromDatabase(id = 1L).observe(viewLifecycleOwner) { resume ->

                    val uri = Uri.parse(resume.imageUri)
                    mBinding.ivImage.setImageURI(uri)

                }

                return@setOnClickListener

            }

            saveResumeToDatabase()

        }
    }

    private fun saveResumeToDatabase() {
        val resume = ResumeEntity(imageUri = mProfileUri.toString())
        mViewModel.saveResumeToDatabase(resume = resume).observe(viewLifecycleOwner) { wasSaved ->

            if (wasSaved) {

                showToast(message = "Insert Success")

            } else {

                showToast(message = "Insert Failed")

            }

        }
    }

    private fun btnPickImageListener() {
        mBinding.btnPickImage.setOnClickListener {

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

                    mProfileUri = fileUri
                    mBinding.ivImage.setImageURI(fileUri)

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