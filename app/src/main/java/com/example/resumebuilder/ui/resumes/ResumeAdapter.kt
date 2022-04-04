package com.example.resumebuilder.ui.resumes


import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.resumebuilder.R
import com.example.resumebuilder.data.entities.Resume
import com.example.resumebuilder.databinding.ItemResumeBinding
import com.example.resumebuilder.utils.SingleParamClickListener

class ResumeAdapter(
    private val clickListener: SingleParamClickListener<Resume>
) :
    RecyclerView.Adapter<ResumeAdapter.ResumeViewHolder>() {

    private val mResumes = arrayListOf<Resume>()

    fun setData(resumes: List<Resume>) {
        mResumes.clear()
        mResumes.addAll(resumes)
        notifyDataSetChanged()
    }

    override fun getItemCount() = mResumes.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResumeViewHolder {
        val itemBinding = ItemResumeBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ResumeViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ResumeViewHolder, position: Int) {

        val resume = mResumes[position]
        holder.bind(resume)

    }

    inner class ResumeViewHolder(private val binding: ItemResumeBinding)

        : RecyclerView.ViewHolder(binding.root) {

        fun bind(resume: Resume) {

            binding.apply {

                loadProfilePhoto(resume)
                tvFullName.text = resume.fullName

                btnEditResume.setOnClickListener { clickListener.onItemClicked(item = resume) }

            }

        }

        private fun ItemResumeBinding.loadProfilePhoto(resume: Resume) {
            resume.profilePhoto?.let { photoUri ->

                val profilePhoto = Uri.parse(photoUri)
                ivProfilePhoto.setImageURI(profilePhoto)

            } ?: ivProfilePhoto.setImageResource(R.drawable.ic_user_place_holder_outlined)
        }

    }

}