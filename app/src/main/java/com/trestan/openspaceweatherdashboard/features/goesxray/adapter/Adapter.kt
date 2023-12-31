package com.trestan.openspaceweatherdashboard.features.goesxray.adapter

import android.view.ViewGroup
import androidx.collection.SparseArrayCompat
import androidx.recyclerview.widget.RecyclerView
import com.trestan.openspaceweatherdashboard.commons.adapter.AdapterConstants
import com.trestan.openspaceweatherdashboard.commons.adapter.ViewType
import com.trestan.openspaceweatherdashboard.commons.adapter.ViewTypeDelegateAdapter

class Adapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: ArrayList<ViewType>
    private var delegateAdapters = SparseArrayCompat<ViewTypeDelegateAdapter>()
    private val loadingItem = object: ViewType {
        override fun getViewType() = AdapterConstants.LOADING
    }

    init {
        delegateAdapters.put(AdapterConstants.LOADING, LoadingDelegateAdapter())
        delegateAdapters.put(AdapterConstants.SOLARREGIONS, SolarRegionDelegateAdapter())
        delegateAdapters.put(AdapterConstants.GOESXRAY, GOESXRayDelegateAdapter())
        delegateAdapters.put(AdapterConstants.SOLARPROBS, SolarProbabilitiesDelegateAdapter())
        items = ArrayList()
        items.add(loadingItem)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegateAdapters.get(viewType)!!.onCreateViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateAdapters.get(getItemViewType(position))!!.onBindViewHolder(
            holder, this.items[position]
        )
    }

    override fun getItemViewType(position: Int): Int {
        return this.items[position].getViewType()
    }

    fun addItems(viewTypes: List<ViewType>) {
        val initPosition = items.size - 1
        items.removeAt(initPosition)
        notifyItemRemoved(initPosition)

        items.addAll(viewTypes)
        items.add(loadingItem)
        notifyItemRangeChanged(initPosition, items.size + 1)
    }

    fun clearAndAddViewTypes(viewTypes: List<ViewType>) {
        items.clear()
        notifyItemRangeRemoved(0, getLastPosition())

        items.addAll(viewTypes)
        items.add(loadingItem)
        notifyItemRangeInserted(0, items.size)
    }

    fun getViewTypes(): List<ViewType> {
        return items
            .filter { it.getViewType() != AdapterConstants.LOADING }
            .map { it as ViewType }
    }

    private fun getLastPosition() = if (items.lastIndex == -1) 0 else items.lastIndex
}