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
import androidx.recyclerview.widget.RecyclerView
import com.news.tryjuardi.R
import com.news.tryjuardi.adapter.CategoryAdapter
import com.news.tryjuardi.adapter.NewsAdapter
import com.news.tryjuardi.adapter.SourceAdapter
import com.news.tryjuardi.databinding.FragmentDetailBinding
import com.news.tryjuardi.model.Source
import com.news.tryjuardi.model.SourceResponse
import com.news.tryjuardi.model.news.Article
import com.news.tryjuardi.utils.EndlessScrollingRecyclerView
import com.news.tryjuardi.viewmodel.SourceViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SourceFragment : Fragment(), SourceAdapter.SourceSelectedCallback {
    lateinit var binding: FragmentDetailBinding
    private lateinit var adapter: SourceAdapter
    val list = mutableListOf<Source>()
    var size = 0
    var page = 1
    var category: String = ""
    var layoutManager: RecyclerView.LayoutManager? = null
    var sourceArray: ArrayList<Source> = ArrayList<Source>()
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
        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvSource.layoutManager = layoutManager
        val scrollListener =
            object : EndlessScrollingRecyclerView(LinearLayoutManager(requireContext())) {
                override fun onLoadMore(totalItemsCount: Int, recyclerView: RecyclerView) {
                    if (page <=10) {
                        page++
                        doLoadData()
                    }
                    else{
                        Toast.makeText(requireContext(), "Developer accounts are limited to a max of 100 results", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        adapter = SourceAdapter(this)
        doLoadData()
        setObserver()
        binding.rvSource.addOnScrollListener(scrollListener)

    }

    private fun setObserver() {
        viewModel.getSourceLiveData().observe(requireActivity(), Observer {
            if (page != 1) {
                sourceArray.addAll(sourceArray.size,it.sources as ArrayList<Source>)
                adapter.notifyItemRangeChanged(
                    0,adapter.itemCount
                )
            } else {
                sourceArray = it.sources as ArrayList<Source>
                binding.rvSource.adapter = adapter
                adapter.initData(sourceArray)
            }
            size =sourceArray.size
        })
    }


    private fun doLoadData() {
        sourceArray = adapter.sourceList
        viewModel.getSource(category, page)
    }
    override fun onSourceSelected(source: Source) {
        val bundle = bundleOf("source" to source.id+"&"+source.category)
        findNavController().navigate(R.id.sourceFragmenttoArticleFragment, bundle)
    }


}