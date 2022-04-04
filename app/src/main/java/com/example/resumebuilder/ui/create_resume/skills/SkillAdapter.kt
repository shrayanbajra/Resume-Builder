package com.example.resumebuilder.ui.create_resume.skills

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.resumebuilder.data.entities.Skill
import com.example.resumebuilder.databinding.ItemSkillBinding

class SkillAdapter(
    private val mSkills: ArrayList<Skill>,
    private val clickListener: SkillChangeListener
)

    : RecyclerView.Adapter<SkillAdapter.SkillViewHolder>() {

    interface SkillChangeListener {

        fun onChange(skill: Skill, position: Int)
        fun onDelete(position: Int)

    }

    fun addNewSkill() {
        val skill = Skill()
        mSkills.add(skill)
        notifyItemInserted(mSkills.lastIndex)
    }

    fun getLastItemIndex() = mSkills.lastIndex

    fun setData(skills: List<Skill>) {
        mSkills.clear()
        mSkills.addAll(skills)
        notifyDataSetChanged()
    }

    fun onItemUpdated(position: Int, skill: Skill) {
        mSkills[position] = skill
    }

    fun removeItemAt(position: Int) {
        notifyItemRemoved(position)
    }

    fun getSkills(): List<Skill> {

        val hasOnlyPlaceholderSkill = itemCount == 1 && mSkills[0] == Skill()
        return if (hasOnlyPlaceholderSkill)
            emptyList()
        else
            mSkills

    }

    override fun getItemCount() = mSkills.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkillViewHolder {
        val itemBinding = ItemSkillBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SkillViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: SkillViewHolder, position: Int) {

        val skill = mSkills[position]
        holder.bind(skill, position)

    }

    inner class SkillViewHolder(private val binding: ItemSkillBinding)

        : RecyclerView.ViewHolder(binding.root) {

        fun bind(skill: Skill, position: Int) {

            binding.apply {

                tilSkill.editText?.setText(skill.title)

                tilSkill.editText?.doAfterTextChanged { text ->

                    skill.title = if (text.isNullOrBlank()) "" else text.toString()
                    clickListener.onChange(skill = skill, position = position)

                }

                btnDelete.setOnClickListener {

                    clickListener.onDelete(position = position)

                }

            }

        }

    }

}