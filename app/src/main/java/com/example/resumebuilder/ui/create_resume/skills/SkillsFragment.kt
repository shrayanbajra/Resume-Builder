package com.example.resumebuilder.ui.create_resume.skills

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.resumebuilder.R
import com.example.resumebuilder.data.entities.Skill
import com.example.resumebuilder.databinding.FragmentSkillsBinding
import com.example.resumebuilder.ui.ResumeViewModel

class SkillsFragment : Fragment() {

    private var _binding: FragmentSkillsBinding? = null
    private val mBinding get() = _binding!!

    private val mSkills: ArrayList<Skill> by lazy {

        val skills = arrayListOf<Skill>()
        val currentSkills = mViewModel.resume.skills
        if (currentSkills == null) {

            val skill = Skill()
            skills.clear()
            skills.addAll(listOf(skill))

        } else {

            skills.clear()
            skills.addAll(currentSkills)

        }
        skills

    }

    private val mSkillAdapter by lazy {
        SkillAdapter(mSkills = mSkills, clickListener = mSkillChangeListener)
    }
    private val mSkillChangeListener: SkillAdapter.SkillChangeListener
        get() = object : SkillAdapter.SkillChangeListener {

            override fun onChange(skill: Skill, position: Int) {

                mSkills[position] = skill

            }

            override fun onDelete(position: Int) {

                mSkills.removeAt(position)
                mSkillAdapter.removeItemAt(position)

            }

        }

    private val mViewModel: ResumeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSkillsBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRvSkills()

        fabAddSkillListener()

        btnPreviousListener()
        btnNextListener()

    }

    private fun initRvSkills() {
        mBinding.rvSkills.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mSkillAdapter
        }
    }

    private fun fabAddSkillListener() {
        mBinding.fabAddSkill.setOnClickListener {
            mSkillAdapter.addNewSkill()
            mBinding.rvSkills.smoothScrollToPosition(mSkillAdapter.getLastItemIndex())
        }
    }

    private fun btnPreviousListener() {
        mBinding.btnPrevious.setOnClickListener { findNavController().navigateUp() }
    }

    private fun btnNextListener() {
        mBinding.btnNext.setOnClickListener {

            mViewModel.resume.skills = mSkillAdapter.getSkills()

            findNavController().navigate(R.id.action_skillsFragment_to_educationDetailsFragment)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}