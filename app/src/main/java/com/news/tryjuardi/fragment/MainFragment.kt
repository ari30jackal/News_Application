package com.news.tryjuardi.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.news.tryjuardi.R
import com.news.tryjuardi.adapter.CategoryAdapter
import com.news.tryjuardi.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    lateinit var binding: FragmentMainBinding
    val newsCategory =
        listOf("Business", "Entertainment", "General", "Health", "Science", "Sports", "Technology")

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
    }

}