package com.example.cvbuilder.ui.experience

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.cvbuilder.databinding.FragmentEditExperienceBinding
import com.example.cvbuilder.databinding.FragmentExperienceBinding
import com.example.cvbuilder.db.CVBuilderDatabase
import com.example.cvbuilder.db.Experience
import com.example.cvbuilder.ui.BaseFragment
import kotlinx.coroutines.launch

class EditExperienceFragment : BaseFragment() {

    private var experience: Experience?=null

    private var _binding: FragmentEditExperienceBinding? = null



    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = EditExperienceFragment()
    }




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentEditExperienceBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(EditSkillViewModel::class.java)
        // TODO: Use the ViewModel
        arguments?.let {
            // get the note value from the HomeFragment using Bundle instance
            experience = EditExperienceFragmentArgs.fromBundle(it).experience
            val company= experience?.company
            val city=experience?.city
            val state=experience?.state
            val startDate=experience?.startDate
            val endDate= experience?.endDate
            val jobTitle=experience?.jobTitle
            val description=experience?.description


            binding.nameCompanyExperience.setText(company)
            binding.cityCompanyExperience.setText(city)
            binding.stateCompanyExperience.setText(state)
            binding.startDateExperience.setText(startDate)
            binding.endDateExperience.setText(endDate)
            binding.jobTitleExperience.setText(jobTitle)
            binding.descriptionExperience.setText(description)
//            val spinner=binding.spSkillType
//            val type=skill?.type
//            for(i in 0 until spinner.count){
//                if(spinner.getItemAtPosition(i).toString().equals(type)){
//                    spinner.setSelection(i)
//                    break
//                }
//            }
//            binding.etSkillName.setText(skill?.name)
        }
        binding.btDeleteExperience.setOnClickListener {
            if (experience != null) deleteExperience() else Toast.makeText(context,"Cannot delete", Toast.LENGTH_LONG).show()
        }
        binding.btSaveExperience.setOnClickListener {
            val company=binding.nameCompanyExperience.text.toString()
            val city=binding.cityCompanyExperience.text.toString()
            val state=binding.stateCompanyExperience.text.toString()
            val startDate=binding.startDateExperience.text.toString()
            val endDate=binding.endDateExperience.text.toString()
            val jobTitle=binding.jobTitleExperience.text.toString()
            val description=binding.descriptionExperience.text.toString()

            if (company.isEmpty()) {
                binding.nameCompanyExperience.error="Required"
                return@setOnClickListener
            }
            if (city.isEmpty()) {
                binding.cityCompanyExperience.error="Required"
                return@setOnClickListener
            }
            if (state.isEmpty()) {
                binding.stateCompanyExperience.error="Required"
                return@setOnClickListener
            }
            if (startDate.isEmpty()) {
                binding.startDateExperience.error="Required"
                return@setOnClickListener
            }
            if (endDate.isEmpty()) {
                binding.endDateExperience.error="Required"
                return@setOnClickListener
            }
            if (jobTitle.isEmpty()) {
                binding.jobTitleExperience.error="Required"
                return@setOnClickListener
            }
            if (description.isEmpty()) {
                binding.descriptionExperience.error = "Required"
                return@setOnClickListener
            }

            launch {
                context?.let {
                    val mExperience = Experience(company, city, state, startDate, endDate, jobTitle, description)
                    // note == null means Inserting a new Note
                    if (experience == null) {
                        CVBuilderDatabase(it).getExperienceDao().addExperience(mExperience)
                    } else {
                        // Update the note
                        mExperience.id = experience!!.id
                        CVBuilderDatabase(it).getExperienceDao().updateExperience(mExperience)
                    }
                    // after adding a note need to return to Home_Fragment as per the navigation directions
                    val action = EditExperienceFragmentDirections.actionEditExperienceFragmentToNavExperience()
                    findNavController().navigate(action)
                }
            }
        }
    }

    private fun deleteExperience() {
        AlertDialog.Builder(context).apply {
            setTitle("Are you sure?")
            setMessage("You cannot undo this operation")
            setPositiveButton("Yes") {dialog, which ->
                launch {
                    CVBuilderDatabase(context).getExperienceDao().deleteExperience(experience!!)
                    val action = EditExperienceFragmentDirections.actionEditExperienceFragmentToNavExperience()
                    findNavController().navigate(action)
                }
            }
            setNegativeButton("No") {dialog, which->

            }
        }.create().show()
    }
}