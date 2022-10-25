package com.example.cvbuilder.ui.education

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.cvbuilder.databinding.FragmentEditEducationBinding
import com.example.cvbuilder.db.CVBuilderDatabase
import com.example.cvbuilder.db.Education
import com.example.cvbuilder.ui.BaseFragment
import com.example.cvbuilder.ui.certification.EditCertificationFragmentArgs
import com.example.cvbuilder.ui.certification.EditCertificationFragmentDirections
import kotlinx.coroutines.launch

class EditEducationFragment : BaseFragment() {

    private var education: Education?=null

    private var _binding: FragmentEditEducationBinding? = null



    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = EditEducationFragment()
    }




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentEditEducationBinding.inflate(inflater, container, false)
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
            education = EditEducationFragmentArgs.fromBundle(it).education
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
        binding.btDeleteEducation.setOnClickListener {
            if (education != null) deleteEducation() else Toast.makeText(context,"Cannot delete", Toast.LENGTH_LONG).show()
        }
        binding.btSaveEducation.setOnClickListener {
            val schoolName=binding.schoolNameEducation.text.toString()
            val city=binding.cityEducation.text.toString()
            val state=binding.stateEducation.text.toString()
            val degree=binding.degreeEducation.text.toString()
            val completionDate=binding.completionDateEducation.text.toString()

            launch {
                context?.let {
                    val mEducation = Education(schoolName, city, state, degree, completionDate)
                    // note == null means Inserting a new Note
                    if (education == null) {
                        CVBuilderDatabase(it).getEducationDao().addEducation(mEducation)
                    } else {
                        // Update the note
                        mEducation.id = education!!.id
                        CVBuilderDatabase(it).getEducationDao().updateEducation(mEducation)
                    }
                    // after adding a note need to return to Home_Fragment as per the navigation directions
                    val action = EditEducationFragmentDirections.actionEditEducationFragmentToEducationFragment()
                    findNavController().navigate(action)
                }
            }
        }
    }

    private fun deleteEducation() {
        AlertDialog.Builder(context).apply {
            setTitle("Are you sure?")
            setMessage("You cannot undo this operation")
            setPositiveButton("Yes") {dialog, which ->
                launch {
                    CVBuilderDatabase(context).getEducationDao().deleteEducation(education!!)
                    val action = EditEducationFragmentDirections.actionEditEducationFragmentToEducationFragment()
                    findNavController().navigate(action)
                }
            }
            setNegativeButton("No") {dialog, which->

            }
        }.create().show()
    }
}