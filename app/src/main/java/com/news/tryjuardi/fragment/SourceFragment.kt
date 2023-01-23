package com.news.tryjuardi.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.news.tryjuardi.R
import com.news.tryjuardi.adapter.CategoryAdapter
import com.news.tryjuardi.adapter.SourceAdapter
import com.news.tryjuardi.databinding.FragmentDetailBinding
import com.news.tryjuardi.model.Source
import com.news.tryjuardi.model.SourceResponse
import com.news.tryjuardi.viewmodel.SourceViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SourceFragment : Fragment(), SourceAdapter.SourceSelectedCallback {
    lateinit var binding: FragmentDetailBinding
    private lateinit var adapter: SourceAdapter
    val list = mutableListOf<Source>()
    var category: String = ""
    private val viewModel: SourceViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        category = arguments?.getString("category").toString()
        viewModel.getSource()
        setObserver()

    }

    private fun setObserver() {
        viewModel.getSourceLiveData().observe(requireActivity(), Observer {
            for (i in it.sources.indices) {
                if (it.sources[i].category.equals(category.lowercase())) {
                    list.add(it.sources[i])
                }
            }
            binding.rvSource.layoutManager = LinearLayoutManager(requireContext())
            adapter = SourceAdapter(list, this, category)
            binding.rvSource.adapter = adapter
        })
    }


    override fun onSourceSelected(source: Source) {
        val bundle = bundleOf("source" to source.name+"&"+source.category)
        findNavController().navigate(R.id.sourceFragmenttoArticleFragment, bundle)
    }


}