package com.example.a22byte.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import com.example.a22byte.R


class FullArticleFragment : Fragment() {
     override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_full_article, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val webView = view.findViewById<WebView>(R.id.article_wv)
        val url = getUrlFromArgs()
        if (url.isNullOrEmpty()) {
            parentFragmentManager.popBackStack()
        } else {
            webView.loadUrl(url)
        }
    }

    private fun getUrlFromArgs(): String? {
        val args = requireArguments()
        return args.getString("URL")
    }
}