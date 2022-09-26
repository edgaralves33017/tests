package com.project.test.ui.listresult.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.test.data.Repository
import com.project.test.data.models.Entry
import com.project.test.data.util.ApiResult
import com.project.test.util.locate
import kotlinx.coroutines.launch

/**
 * [ViewModel] for the PostalCodeListFragment.
 */
class ListResultViewModel : ViewModel() {
    /**
     * Instance of [Repository]
     */
    private val repository: Repository = locate()

    /**
     * [MutableLiveData] that will contain the states emitted by flow.
     */
    private var _loadState: MutableLiveData<ApiResult<List<Entry>>> =
        MutableLiveData<ApiResult<List<Entry>>>()

    /**
     * [LiveData] that will be used by the fragment to discern the flow states.
     */
    val loadState: LiveData<ApiResult<List<Entry>>> = _loadState

    /**
     * Starts download of the CSV file.
     */
    fun getEntries() {
        viewModelScope.launch {
            repository.getPublicFeed().collect {
                _loadState.postValue(it)
            }
        }
    }
}