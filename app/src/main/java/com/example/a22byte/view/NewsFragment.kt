package com.example.a22byte.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a22byte.NewsAdapter
import com.example.a22byte.NewsViewModel
import com.example.a22byte.R
import com.example.a22byte.databinding.FragmentNewsBinding


class NewsFragment : Fragment() {
    private var currentSearch: String? = "22"
    private lateinit var binding: FragmentNewsBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentNewsBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
        setUpSearch()
        viewModel.newsList.observe(viewLifecycleOwner) {
            newsAdapter.loadNextPage(it?.articles)
            binding.noResultsTv.isVisible = newsAdapter.currentList.isEmpty()
        }
        getNews(1)
    }

    private fun setUpAdapter() {
        newsAdapter = NewsAdapter(requireContext()) {
            getNews(it)
        }
        binding.newsRv.layoutManager = LinearLayoutManager(requireContext())
        binding.newsRv.adapter = newsAdapter
        newsAdapter.onArticleClickListener = {
            val fullArticleFragment = FullArticleFragment().apply {
                arguments = Bundle().apply {
                    putString("URL", it)
                }
            }
            parentFragmentManager.beginTransaction()
                .add(R.id.news_fragment_container, fullArticleFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun setUpSearch() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                clearList()
                currentSearch = p0
                getNews(1)

                return false
            }
        })
    }

    private fun clearList() {
        newsAdapter.submitList(null)
    }

    private fun getNews(page: Int) {
        if (currentSearch.isNullOrEmpty())
            viewModel.getNews("22", page)
        else
            viewModel.getNews(currentSearch!!, page)
    }
}