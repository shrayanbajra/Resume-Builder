package com.example.resumebuilder.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.resumebuilder.data.Resume
import com.example.resumebuilder.repository.ResumeRepository
import com.example.resumebuilder.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResumeViewModel
@Inject
constructor(private val resumeRepository: ResumeRepository) : ViewModel() {

    lateinit var resume: Resume
    var isNew: Boolean = true

    fun saveResumeToDatabase(resume: Resume): LiveData<Boolean> {

        val result = MutableLiveData<Boolean>()

        viewModelScope.launch {

            val rowId = resumeRepository.saveResumeToDatabase(resume = resume)
            result.postValue(rowId != Constants.NO_PRIMARY_KEY)

        }

        return result

    }

    fun getResumeFromDatabase(id: Long): LiveData<Resume> {

        val result = MutableLiveData<Resume>()

        viewModelScope.launch {

            val resume = resumeRepository.getResumeFromDatabase(id = id)
            result.postValue(resume)

        }

        return result

    }

    fun getAllResumesFromDatabase(): LiveData<List<Resume>> {

        val result = MutableLiveData<List<Resume>>()

        viewModelScope.launch {

            val resume = resumeRepository.getAllResumesFromDatabase()
            result.postValue(resume)

        }

        return result

    }

}