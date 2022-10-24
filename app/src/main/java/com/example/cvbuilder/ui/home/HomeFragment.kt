package com.example.cvbuilder.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.cvbuilder.databinding.FragmentHomeBinding
import com.example.cvbuilder.db.CVBuilderDatabase
import com.example.cvbuilder.ui.BaseFragment
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        /*val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val cont: Context?= activity?.applicationContext;
        val spf= cont!!.getSharedPreferences("myspf",Context.MODE_PRIVATE)
        binding.etHomeTitle.setText(spf.getString("title",""))
        binding.etHomeDescription.setText(spf.getString("description",""))

        binding.etHomeName.setText(spf.getString("name",""))
        binding.etHomePhone.setText(spf.getString("phone",""))
        binding.etHomeEmail.setText(spf.getString("email",""))

        val template=spf.getString("template","")
        val spinner=binding.spCvTemplate
        for(i in 0 until spinner.count){
            if(spinner.getItemAtPosition(i).toString().equals(template)){
                spinner.setSelection(i)
                break
            }
        }

        binding.homeBtnSave.setOnClickListener {
            val spf= cont!!.getSharedPreferences("myspf",Context.MODE_PRIVATE)
            val spe=spf.edit()
            spe.putString("title",binding.etHomeTitle.text.toString())
            spe.putString("description",binding.etHomeDescription.text.toString())

            spe.putString("name",binding.etHomeName.text.toString())
            spe.putString("phone",binding.etHomePhone.text.toString())
            spe.putString("email",binding.etHomeEmail.text.toString())

            spe.putString("template",binding.spCvTemplate.selectedItem.toString())

            spe.apply()
            Toast.makeText(cont,"Saved succesfully",Toast.LENGTH_LONG).show()
        }



        //binding.homeRv.layoutManager=LinearLayoutManager(cont)
        //val cvs = CVBuilderDatabase(cont!!).getCVDao().getAllCVs()
        //binding.homeRv.adapter = HomeAdapter(cvs)
        //launch {
            /* here context is the getContext() from the Fragment, if the context
            * is not null, let's execute the block of code using let scope function
            * with the argument it's context object - Inline functions
            * similar like  if(context!=null){}*/


       /*    context?.let{
                val cvs = CVBuilderDatabase(it).getCVDao().getAllCVs()
                binding.homeRv.adapter = HomeAdapter(cvs)
            }
        }*/
    }
}