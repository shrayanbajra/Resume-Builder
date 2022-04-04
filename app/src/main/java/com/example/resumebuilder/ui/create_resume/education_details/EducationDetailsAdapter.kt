package com.example.resumebuilder.ui.create_resume.education_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.resumebuilder.data.entities.EducationDetails
import com.example.resumebuilder.databinding.ItemEducationDetailsBinding

class EducationDetailsAdapter(
    private val mEducationDetails: ArrayList<EducationDetails>,
    private val clickListener: EducationDetailsChangeListener
)

    : RecyclerView.Adapter<EducationDetailsAdapter.EducationDetailsViewHolder>() {

    interface EducationDetailsChangeListener {

        fun onChange(educationDetails: EducationDetails, position: Int)
        fun onDelete(position: Int)

    }

    fun addNewEducationDetails() {
        val educationDetails = EducationDetails()
        mEducationDetails.add(educationDetails)
        notifyItemInserted(mEducationDetails.lastIndex)
    }

    fun getLastItemIndex() = mEducationDetails.lastIndex

    fun setData(educationDetails: List<EducationDetails>) {
        mEducationDetails.clear()
        mEducationDetails.addAll(educationDetails)
        notifyDataSetChanged()
    }

    fun onItemUpdated(position: Int, educationDetails: EducationDetails) {
        mEducationDetails[position] = educationDetails
    }

    fun removeItemAt(position: Int) {
        notifyItemRemoved(position)
    }

    fun getEducationDetails(): List<EducationDetails> {

        val hasOnlyPlaceholderEducationDetails =
            itemCount == 1 && mEducationDetails[0] == EducationDetails()
        return if (hasOnlyPlaceholderEducationDetails)
            emptyList()
        else
            mEducationDetails

    }

    override fun getItemCount() = mEducationDetails.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EducationDetailsViewHolder {
        val itemBinding = ItemEducationDetailsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return EducationDetailsViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: EducationDetailsViewHolder, position: Int) {

        val educationDetails = mEducationDetails[position]
        holder.bind(educationDetails, position)

    }

    inner class EducationDetailsViewHolder(private val binding: ItemEducationDetailsBinding)

        : RecyclerView.ViewHolder(binding.root) {

        fun bind(educationDetails: EducationDetails, position: Int) {

            binding.apply {

                tilCourseOrDegree.editText?.setText(educationDetails.courseOrDegree)
                tilCourseOrDegree.editText?.doAfterTextChanged { text ->

                    educationDetails.courseOrDegree =
                        if (text.isNullOrBlank())
                            ""
                        else
                            text.toString()
                    clickListener.onChange(educationDetails = educationDetails, position = position)

                }

                tilPassingYear.editText?.setText(educationDetails.passingYear)
                tilPassingYear.editText?.doAfterTextChanged { text ->

                    educationDetails.passingYear =
                        if (text.isNullOrBlank())
                            ""
                        else
                            text.toString()
                    clickListener.onChange(educationDetails = educationDetails, position = position)

                }

                tilScore.editText?.setText(educationDetails.score)
                tilScore.editText?.doAfterTextChanged { text ->

                    educationDetails.score =
                        if (text.isNullOrBlank())
                            ""
                        else
                            text.toString()
                    clickListener.onChange(educationDetails = educationDetails, position = position)

                }

                btnDelete.setOnClickListener {

                    clickListener.onDelete(position = position)

                }

            }

        }

    }

}