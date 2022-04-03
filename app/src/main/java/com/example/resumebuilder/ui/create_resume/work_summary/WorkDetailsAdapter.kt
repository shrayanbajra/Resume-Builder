package com.example.resumebuilder.ui.create_resume.work_summary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.resumebuilder.data.WorkDetails
import com.example.resumebuilder.databinding.ItemWorkDetailsBinding

class WorkDetailsAdapter(
    private val mWorkDetails: ArrayList<WorkDetails>,
    private val clickListener: WorkDetailsChangeListener
)

    : RecyclerView.Adapter<WorkDetailsAdapter.WorkDetailsViewHolder>() {

    interface WorkDetailsChangeListener {

        fun onChange(workDetails: WorkDetails, position: Int)
//        fun onDelete()

    }

    fun setData(workDetails: List<WorkDetails>) {
        mWorkDetails.clear()
        mWorkDetails.addAll(workDetails)
        notifyDataSetChanged()
    }

    fun onItemUpdated(position: Int, workDetails: WorkDetails) {
        mWorkDetails[position] = workDetails
    }

    fun getWorkDetails(): List<WorkDetails> {

        val hasOnlyPlaceholderWorkDetails = itemCount == 1 && mWorkDetails[0] == WorkDetails()
        return if (hasOnlyPlaceholderWorkDetails)
            emptyList()
        else
            mWorkDetails

    }

    override fun getItemCount() = mWorkDetails.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkDetailsViewHolder {
        val itemBinding = ItemWorkDetailsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return WorkDetailsViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: WorkDetailsViewHolder, position: Int) {

        val workDetails = mWorkDetails[position]
        holder.bind(workDetails, position)

    }

    inner class WorkDetailsViewHolder(private val binding: ItemWorkDetailsBinding)

        : RecyclerView.ViewHolder(binding.root) {

        fun bind(workDetails: WorkDetails, position: Int) {

            binding.apply {
                tilCompanyName.editText?.setText(workDetails.companyName)
                tilStartDate.editText?.setText("2022-01-22")
                tilEndDate.editText?.setText("2022-10-22")
            }

            binding.tilCompanyName.editText?.doAfterTextChanged { text ->

                workDetails.companyName = if (text.isNullOrBlank()) "" else text.toString()
                clickListener.onChange(workDetails = workDetails, position = position)

            }

        }

    }

}