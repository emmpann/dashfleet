package com.github.emmpann.dashfleet.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.emmpann.dashfleet.data.Bus
import com.github.emmpann.dashfleet.databinding.BusItemBinding

class BusListAdapter : RecyclerView.Adapter<BusListAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    private val listBus: ArrayList<Bus> = arrayListOf()

    interface OnItemClickCallback {
        fun onItemClicked(bus: Bus)
    }

    inner class ViewHolder(private val binding: BusItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(bus: Bus) {
            with(binding) {
                tvBusTitle.text = bus.name
            }
        }
    }

    fun setOnClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun submitList(listBus: List<Bus>) {
        this.listBus.addAll(listBus)
        if (listBus.size > 1) notifyItemRangeChanged(0, listBus.lastIndex) else notifyItemInserted(0)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = BusItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listBus.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bus = listBus[position]
        holder.bind(bus)
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(bus) }
    }
}