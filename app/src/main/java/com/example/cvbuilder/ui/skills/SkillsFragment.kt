package com.example.cvbuilder.ui.skills

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cvbuilder.databinding.FragmentSkillsBinding
import com.example.cvbuilder.db.CVBuilderDatabase
import com.example.cvbuilder.ui.BaseFragment
import kotlinx.coroutines.launch

class SkillsFragment : BaseFragment() {

    private var _binding: FragmentSkillsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val skillsViewModel =
            ViewModelProvider(this).get(SkillsViewModel::class.java)

        _binding = FragmentSkillsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        /*val textView: TextView = binding.textGallery
        skillsViewModel.text.observe(viewLifecycleOwner) {
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
        binding.skillRv.layoutManager= LinearLayoutManager(cont)
        binding.btSkillAdd.setOnClickListener {
            val action=SkillsFragmentDirections.actionNavSkillsToEditSkillFragment()
            Navigation.findNavController(it).navigate(action)
        }
        //val cvs = CVBuilderDatabase(cont!!).getCVDao().getAllCVs()
        //binding.homeRv.adapter = HomeAdapter(cvs)
        launch {
            /* here context is the getContext() from the Fragment, if the context
            * is not null, let's execute the block of code using let scope function
            * with the argument it's context object - Inline functions
            * similar like  if(context!=null){}*/


            context?.let{
                val skills = CVBuilderDatabase(it).getSkillDao().getAllSkills()
                binding.skillRv.adapter = SkillAdapter(skills)
            }
        }
    }
}