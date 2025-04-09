package com.github.emmpann.dashfleet.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.emmpann.dashfleet.data.Bus
import com.github.emmpann.dashfleet.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var busAdapter: BusListAdapter
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.loadBusData()

        viewModel.busStatusList.observe(viewLifecycleOwner) {
            busAdapter.submitList(it)
        }
    }

    private fun setupRecyclerView() {
        binding.rvBus.overScrollMode = View.OVER_SCROLL_NEVER
        binding.rvBus.layoutManager = LinearLayoutManager(requireContext())
        busAdapter = BusListAdapter()
        binding.rvBus.adapter = busAdapter
        busAdapter.setOnClickCallback(object : BusListAdapter.OnItemClickCallback {
            override fun onItemClicked(bus: Bus) {
                val toLivetrack = HomeFragmentDirections.actionHomeFragmentToLiveTrackFragment()
                toLivetrack.id = bus.id
                toLivetrack.busName = bus.name
                findNavController().navigate(toLivetrack)
            }
        })
    }

}