package com.news.tryjuardi.fragment

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.news.tryjuardi.R
import com.news.tryjuardi.databinding.FragmentArticleBinding
import com.news.tryjuardi.databinding.FragmentViewArticleBinding


class ViewArticleFragment : Fragment() {

    lateinit var binding: FragmentViewArticleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewArticleBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var url = arguments?.getString("url").toString()
        binding.webview.webChromeClient = WebChromeClient()
        binding.webview.settings.javaScriptEnabled = true
        binding.webview.settings.domStorageEnabled = true
        binding.webview.settings.allowContentAccess = true
        binding.webview.settings.databaseEnabled = true
        binding.webview.settings.javaScriptCanOpenWindowsAutomatically = true
        binding.webview.settings.safeBrowsingEnabled = true
        binding.webview.loadUrl(url)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        findNavController().navigate(R.id.backtoMain)
    }
}