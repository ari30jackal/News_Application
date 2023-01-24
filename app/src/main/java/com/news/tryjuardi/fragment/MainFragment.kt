package com.news.tryjuardi.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.news.tryjuardi.R
import com.news.tryjuardi.adapter.CategoryAdapter
import com.news.tryjuardi.databinding.FragmentMainBinding
import com.news.tryjuardi.model.news.Article
import com.news.tryjuardi.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {
    private val viewModel: NewsViewModel by viewModels()
    lateinit var binding: FragmentMainBinding
    var page = 1
    var type:String="keyword"
    val newsCategory =
        listOf("Business", "Entertainment", "General", "Health", "Science", "Sports", "Technology")
    var keyword: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val categoryAdapter = CategoryAdapter(newsCategory, requireActivity())
        binding.rvCategory.adapter = categoryAdapter
        binding.rvCategory.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val bundle = bundleOf("category" to newsCategory[position])
                findNavController().navigate(R.id.mainFragmenttoSourceFragment, bundle)
            }

        binding.btnSearchByKeyword.setOnClickListener {
            keyword = binding.etSearch.text.toString()
            type = "keyword"
            if (keyword != "") {
                viewModel.getByKeyword(keyword, page)
            }
        }
        binding.btnSearchBySource.setOnClickListener {
            keyword = binding.etSearch.text.toString()
            type="source"
            if (keyword != "") {
                viewModel.getArticle(keyword, page)
            }
        }
        setObserver()
    }

    private fun setObserver() {
        viewModel.getErrorCodeResponse().observe(requireActivity(), Observer {
            Toast.makeText(requireActivity(), "$it", Toast.LENGTH_SHORT).show()
        })
        viewModel.getNewsLiveData().observe(requireActivity(), Observer {
            val bundle = bundleOf("data" to keyword+"&$type")
            findNavController().navigate(R.id.maintoSearchResultFragment, bundle)
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        requireActivity().finishAffinity()
    }

}