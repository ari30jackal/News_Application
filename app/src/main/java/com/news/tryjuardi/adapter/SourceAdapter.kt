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
import com.news.tryjuardi.databinding.SourceItemBinding
import com.news.tryjuardi.model.Source
import com.news.tryjuardi.model.SourceResponse

class SourceAdapter(
    private var sourceModels: List<Source>,
    private val sourceSelectedCallback: SourceSelectedCallback,
    var category: String
) :
    RecyclerView.Adapter<SourceAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: SourceItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.btnSource.setOnClickListener {
                sourceSelectedCallback.onSourceSelected(sourceModels[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            SourceItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sourceModel = sourceModels[position]
        with(holder) {
            binding.btnSource.text = sourceModel.name

        }
    }

    override fun getItemCount(): Int {
        return sourceModels.size
    }

    interface SourceSelectedCallback {
        fun onSourceSelected(source: Source)
    }
}
