package com.github.emmpann.dashfleet.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.emmpann.dashfleet.data.Bus
import com.github.emmpann.dashfleet.repository.BusDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val busDataRepository: BusDataRepository
) : ViewModel() {

    private val _busStatusList = MutableLiveData<List<Bus>>()

    val busStatusList: LiveData<List<Bus>> = _busStatusList

    fun loadBusData() {
        val buses = busDataRepository.getAllBuses()
        _busStatusList.value = buses
    }
}