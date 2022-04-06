package com.example.resumebuilder.ui

import androidx.lifecycle.*
import com.example.resumebuilder.data.entities.Resume
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

        return resumeRepository.getAllResumesFromDatabase().asLiveData()

    }

    fun deleteResumeFromDatabase(resume: Resume): LiveData<Boolean> {

        val result = MutableLiveData<Boolean>()

        viewModelScope.launch {

            val deletedRows = resumeRepository.deleteResumeFromDatabase(resume = resume)
            val wasDeleted = deletedRows > 0
            result.postValue(wasDeleted)

        }

        return result

    }

}