package com.github.emmpann.dashfleet.livetrack

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.emmpann.dashfleet.data.POI
import com.github.emmpann.dashfleet.repository.BusDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import javax.inject.Inject
import kotlin.math.pow
import kotlin.math.sqrt

@HiltViewModel
class LiveTrackViewModel @Inject constructor(
    private val busDataRepository: BusDataRepository
) : ViewModel() {

    private val _currentPOI = MutableLiveData<POI>()
    val currentPOI: LiveData<POI> = _currentPOI

    private val _currentSpeed = MutableLiveData<String>()
    val currentSpeed: LiveData<String> = _currentSpeed

    private val _engineStatus = MutableLiveData<String>()
    val engineStatus: LiveData<String> = _engineStatus

    private val _doorStatus = MutableLiveData<String>()
    val doorStatus: LiveData<String> = _doorStatus

    private val _alertMessage = MutableLiveData<String?>()
    val alertMessage: LiveData<String?> = _alertMessage

    private var simulateBusTrack: Job? = null

    fun loadBusRoute(id: String, delayMillis: Long = 1000L) {
        simulateBusTrack?.cancel()

        val fullRoute = busDataRepository.getRouteByBusId(id)

        simulateBusTrack = viewModelScope.launch {
            var engineOn = false

            for (i in fullRoute.indices) {
                val current = fullRoute[i]
                val next = fullRoute.getOrNull(i + 1)

                _currentPOI.postValue(current)

                val speed = if (next != null) {
                    val calculated = calculateSpeed(
                        current.latitude, current.longitude, next.latitude, next.longitude, delayMillis / 1000
                    )
                    _currentSpeed.postValue("%.2f".format(calculated))
                    calculated
                } else {
                    _currentSpeed.postValue("0.0")
                    0.0
                }

                engineOn = when {
                    i == 0 -> false
                    i == fullRoute.lastIndex -> false
                    else -> true
                }
                _engineStatus.postValue(if (engineOn) "ON" else "OFF")

                val doorOpen = speed == 0.0
                _doorStatus.postValue(if (doorOpen) "OPEN" else "CLOSED")

                when {
                    speed >= 80 -> _alertMessage.postValue("⚠️ Kecepatan melebihi 80 km/h!")
                    speed < 80 -> _alertMessage.postValue(null)
                    doorOpen && speed > 0.1 -> _alertMessage.postValue("⚠️ Pintu terbuka saat bus berjalan!")
                    i == 1 -> _alertMessage.postValue("✅ Mesin dinyalakan")
                    i == fullRoute.lastIndex -> _alertMessage.postValue("🛑 Mesin dimatikan")
                    else -> _alertMessage.postValue(null)
                }

                delay(delayMillis)
            }

            _currentSpeed.postValue("0.0")
            _engineStatus.postValue("OFF")
            _doorStatus.postValue("OPEN")
            _alertMessage.postValue(null)
        }
    }

    private fun calculateSpeed(
        lat1: Double, lon1: Double,
        lat2: Double, lon2: Double,
        timeSeconds: Long
    ): Double {
        val R = 6371000.0
        val phi1 = Math.toRadians(lat1)
        val phi2 = Math.toRadians(lat2)
        val deltaPhi = Math.toRadians(lat2 - lat1)
        val deltaLambda = Math.toRadians(lon2 - lon1)

        val a = sin(deltaPhi / 2).pow(2.0) + cos(phi1) * cos(phi2) * sin(deltaLambda / 2).pow(2.0)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        val distance = R * c
        val speedMps = distance / timeSeconds
        return speedMps * 3.6
    }
}
