package com.news.tryjuardi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.news.tryjuardi.R
import com.news.tryjuardi.databinding.NewsItemBinding
import com.news.tryjuardi.databinding.SourceItemBinding
import com.news.tryjuardi.model.Source
import com.news.tryjuardi.model.SourceResponse
import com.news.tryjuardi.model.news.Article

class SourceAdapter(
    private val sourceSelectedCallback:SourceSelectedCallback,
) : RecyclerView.Adapter<SourceAdapter.ViewHolder>() {
    var sourceList = arrayListOf<Source>()
    private var context: Context? = null
    inner class ViewHolder(val binding: SourceItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.btnSource.setOnClickListener {
                sourceSelectedCallback.onSourceSelected(sourceList[position])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            SourceItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(sourceList[position]) {
                binding.btnSource.text = name
            }
        }
    }

    override fun getItemCount() = sourceList.size

    interface SourceSelectedCallback {
        fun onSourceSelected(source: Source)
    }

    fun initData(sourceList: ArrayList<Source>) {
        this.sourceList= sourceList

    }
}