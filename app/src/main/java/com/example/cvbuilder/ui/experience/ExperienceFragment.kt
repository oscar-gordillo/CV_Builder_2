package com.example.cvbuilder.ui.experience

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cvbuilder.databinding.FragmentExperienceBinding
import com.example.cvbuilder.db.CVBuilderDatabase
import com.example.cvbuilder.ui.BaseFragment
import kotlinx.coroutines.launch

class ExperienceFragment : BaseFragment() {

    private var _binding: FragmentExperienceBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val experienceViewModel =
            ViewModelProvider(this).get(ExperienceViewModel::class.java)

        _binding = FragmentExperienceBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textSlideshow
//        experienceViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val cont: Context?= activity?.applicationContext;
        binding.experienceRv.layoutManager= LinearLayoutManager(cont)
        binding.btExperienceAdd.setOnClickListener {
            val action= ExperienceFragmentDirections.actionNavExperienceToEditExperienceFragment()
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
                val experiences = CVBuilderDatabase(it).getExperienceDao().getAllExperience()
                binding.experienceRv.adapter = ExperienceAdapter(experiences)
            }
        }
    }
}