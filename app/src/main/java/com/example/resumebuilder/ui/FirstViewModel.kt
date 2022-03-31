package com.example.resumebuilder.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.resumebuilder.database.ResumeEntity
import com.example.resumebuilder.repository.ResumeRepository
import com.example.resumebuilder.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FirstViewModel
@Inject
constructor(private val resumeRepository: ResumeRepository) : ViewModel() {

    fun saveResumeToDatabase(resume: ResumeEntity): LiveData<Boolean> {

        val result = MutableLiveData<Boolean>()

        viewModelScope.launch {

            val rowId = resumeRepository.saveResumeToDatabase(resumeEntity = resume)
            result.postValue(rowId != Constants.NO_PRIMARY_KEY)

        }

        return result

    }

    fun getResumeFromDatabase(id: Long): LiveData<ResumeEntity> {

        val result = MutableLiveData<ResumeEntity>()

        viewModelScope.launch {

            val resume = resumeRepository.getResumeFromDatabase(id = id)
            result.postValue(resume)

        }

        return result

    }

}