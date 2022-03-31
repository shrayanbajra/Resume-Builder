package com.example.resumebuilder.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.resumebuilder.database.ResumeEntity
import com.example.resumebuilder.repository.ResumeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FirstViewModel
@Inject
constructor(private val resumeRepository: ResumeRepository) : ViewModel() {

    fun insertResume(resumeEntity: ResumeEntity) {

        viewModelScope.launch {

            resumeRepository.insertResume(resumeEntity = resumeEntity)

        }

    }

}