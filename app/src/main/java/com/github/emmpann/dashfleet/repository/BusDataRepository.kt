package com.github.emmpann.dashfleet.repository

import android.content.Context
import com.github.emmpann.dashfleet.data.Bus
import com.github.emmpann.dashfleet.data.BusRoute
import com.github.emmpann.dashfleet.data.POI
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BusDataRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun getAllBuses(): List<Bus> {
        val json = context.assets.open("bus_data.json").bufferedReader().use { it.readText() }
        val type = object : TypeToken<List<Bus>>() {}.type
        return Gson().fromJson(json, type)
    }

    private fun getAllRoutes(): List<BusRoute> {
        val json = context.assets.open("bus_routes.json").bufferedReader().use { it.readText() }
        val type = object : TypeToken<List<BusRoute>>() {}.type
        return Gson().fromJson(json, type)
    }

    fun getRouteByBusId(busId: String): List<POI> {
        val allRoutes = getAllRoutes()
        return allRoutes.find { it.id == busId }?.route ?: emptyList()
    }
}