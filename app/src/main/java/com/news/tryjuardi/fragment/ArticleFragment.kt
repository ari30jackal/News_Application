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
import com.news.tryjuardi.adapter.SourceAdapter
import com.news.tryjuardi.databinding.FragmentArticleBinding
import com.news.tryjuardi.databinding.FragmentMainBinding
import com.news.tryjuardi.model.news.Article
import com.news.tryjuardi.model.news.NewsResponse
import com.news.tryjuardi.utils.EndlessScrollingRecyclerView
import com.news.tryjuardi.viewmodel.NewsViewModel
import com.news.tryjuardi.viewmodel.SourceViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleFragment : Fragment(),NewsAdapter.NewsSelectedCallback {
    lateinit var category: String
    lateinit var source: String
    var size = 0
    var page = 1
    var totalResult = 0
    var layoutManager: RecyclerView.LayoutManager? = null
    private val viewModel: NewsViewModel by viewModels()
    private lateinit var adapter: NewsAdapter
    var newsArray: ArrayList<Article> = ArrayList<Article>()
    lateinit var binding: FragmentArticleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArticleBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var arg = arguments?.getString("source").toString()
        var args = arg.split("&").toTypedArray()
        source = args[0]
        category = args[1]
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
        viewModel.getNewsLiveData().observe(requireActivity(), Observer {
            totalResult = it.totalResults
            if (totalResult==0){
                findNavController().navigate(R.id.backtoMain)
                Toast.makeText(requireContext(), "no news from this source", Toast.LENGTH_SHORT).show()
            }
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
        viewModel.getArticle(source, page)
    }

    override fun onNewsSelected(article: Article) {
        val bundle = bundleOf("url" to article.url)
        findNavController().navigate(R.id.articleFragmenttoViewArticleFragment, bundle)
    }



}