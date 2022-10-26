package com.example.cvbuilder.ui.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.example.cvbuilder.R
import com.example.cvbuilder.databinding.FragmentHomeCloudAccountBinding
import com.example.cvbuilder.databinding.FragmentSkillsBinding


class HomeFragmentCloudAccount : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val bindings= FragmentHomeCloudAccountBinding.inflate(inflater, container, false)
        bindings.homeWv.settings.javaScriptEnabled=true
        bindings.homeWv.webViewClient= WebViewClient()
        val spf=context?.getSharedPreferences("myspf", Context.MODE_PRIVATE)

        bindings.homeWv.loadUrl("https://ged21c9a6c3b8b0-free1.adb.us-phoenix-1.oraclecloudapps.com/ords/r/mdp/cloud-account-cv-builder/home?p1_cv_id="+spf?.getString("cloud_id","24"))
        val root: View = bindings.root
        //return inflater.inflate(R.layout.fragment_home_cloud_account, container, false)
        return root

    }


}