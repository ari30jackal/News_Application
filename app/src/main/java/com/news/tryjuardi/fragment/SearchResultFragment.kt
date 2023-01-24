package com.news.tryjuardi.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.news.tryjuardi.R
import com.news.tryjuardi.adapter.NewsAdapter
import com.news.tryjuardi.databinding.FragmentMainBinding
import com.news.tryjuardi.databinding.FragmentSearchResultBinding
import com.news.tryjuardi.model.news.Article
import com.news.tryjuardi.utils.EndlessScrollingRecyclerView
import com.news.tryjuardi.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchResultFragment : Fragment(),NewsAdapter.NewsSelectedCallback {
 lateinit var binding:FragmentSearchResultBinding
    lateinit var type: String
    lateinit var keyword: String
    var size = 0
    var page = 1
    var totalResult = 0
    var layoutManager: RecyclerView.LayoutManager? = null
    private val viewModel: NewsViewModel by viewModels()
    private lateinit var adapter: NewsAdapter
    var newsArray: ArrayList<Article> = ArrayList<Article>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchResultBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var arg = arguments?.get("data").toString()
        var args = arg.split("&").toTypedArray()
        keyword = args[0]
        type = args[1]
        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvNews.layoutManager = layoutManager
        val scrollListener =
            object : EndlessScrollingRecyclerView(LinearLayoutManager(requireContext())) {
                override fun onLoadMore(totalItemsCount: Int, recyclerView: RecyclerView) {
                    if (size + 10 <=100) {
                        page++
                        doLoadData()
                    }
                    else{
                        Toast.makeText(requireContext(), "Developer accounts are limited to a max of 100 results", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        adapter = NewsAdapter(this)
        doLoadData()
        setObserver()
        binding.rvNews.addOnScrollListener(scrollListener)
    }
    private fun setObserver() {
        viewModel.getErrorCodeResponse().observe(requireActivity(), Observer {
            Toast.makeText(requireContext(), "$it", Toast.LENGTH_SHORT).show()
        })
        viewModel.getNewsLiveData().observe(requireActivity(), Observer {
            totalResult = it.totalResults
            if (page != 1) {
                newsArray.addAll(newsArray.size,it.articles as ArrayList<Article>)
                adapter.notifyItemRangeChanged(
                    0,adapter.itemCount
                )
            } else {
                newsArray = it.articles as ArrayList<Article>
                binding.rvNews.adapter = adapter
                adapter.initData(newsArray)
            }
            size = newsArray.size
        })

    }

    private fun doLoadData() {
        newsArray = adapter.newsList
        when(type){
            "keyword"->{
                viewModel.getByKeyword(keyword,page)
            }
            "source"->{
                viewModel.getArticle(keyword,page)
            }

        }
    }

    override fun onNewsSelected(article: Article) {
        val bundle = bundleOf("url" to article.url)
        findNavController().navigate(R.id.searchFragmenttoViewArticleFragment, bundle)
    }

    override fun onDestroy() {
        super.onDestroy()
        findNavController().navigate(R.id.backtoMain)

    }

}