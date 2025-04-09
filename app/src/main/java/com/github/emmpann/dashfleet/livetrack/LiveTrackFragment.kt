package com.github.emmpann.dashfleet.livetrack

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.emmpann.dashfleet.R
import com.github.emmpann.dashfleet.data.POI
import com.google.android.gms.maps.SupportMapFragment
import com.github.emmpann.dashfleet.databinding.FragmentLiveTrackBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LiveTrackFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: FragmentLiveTrackBinding
    private val viewModel: LiveTrackViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLiveTrackBinding.inflate(layoutInflater, container, false)

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.google_maps) as SupportMapFragment

        mapFragment.getMapAsync(this)

        setupObserver()

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }

    private fun setupObserver() {
        val busId = LiveTrackFragmentArgs.fromBundle(arguments as Bundle).id
        viewModel.loadBusRoute(busId)

        binding.tvBusName.text = LiveTrackFragmentArgs.fromBundle(arguments as Bundle).busName
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true

        viewModel.engineStatus.observe(viewLifecycleOwner) {
            binding.tvEngineStatus.text = it
        }

        viewModel.currentSpeed.observe(viewLifecycleOwner) {
            binding.tvSpeed.text = it
        }

        viewModel.doorStatus.observe(viewLifecycleOwner) {
            binding.tvDoorStatus.text = it
        }

        viewModel.alertMessage.observe(viewLifecycleOwner) { alert ->
            alert?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.currentPOI.observe(viewLifecycleOwner) {
            val posisi = it.toLatLng()

            mMap.clear()
            mMap.addMarker(
                MarkerOptions()
                    .position(posisi)
                    .title("Bus")
                    .icon(
                        bitmapDescriptorFromVector(
                            requireContext(),
                            R.drawable.baseline_directions_bus_24
                        )
                    )
            )
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(posisi, 17f))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.alertMessage.removeObservers(viewLifecycleOwner)
    }

    private fun POI.toLatLng(): LatLng {
        return LatLng(this.latitude, this.longitude)
    }

    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap =
                Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }
}