package com.example.resumebuilder.ui.create_resume


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.resumebuilder.databinding.ItemSectionBinding
import com.example.resumebuilder.utils.SingleParamClickListener

class SectionAdapter(private val clickListener: SingleParamClickListener<ResumeSections>)

    : RecyclerView.Adapter<SectionAdapter.SectionViewHolder>() {

    private val mSections = arrayListOf<ResumeSections>()

    fun setData(sections: List<ResumeSections>) {
        mSections.clear()
        mSections.addAll(sections)
        notifyDataSetChanged()
    }

    override fun getItemCount() = mSections.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionViewHolder {
        val itemBinding = ItemSectionBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SectionViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: SectionViewHolder, position: Int) {

        val section = mSections[position]
        holder.bind(section)

    }

    inner class SectionViewHolder(private val binding: ItemSectionBinding)

        : RecyclerView.ViewHolder(binding.root) {

        fun bind(section: ResumeSections) {

            binding.root.setOnClickListener { clickListener.onItemClicked(item = section) }

            binding.tvTitle.setText(section.title)

        }

    }

}