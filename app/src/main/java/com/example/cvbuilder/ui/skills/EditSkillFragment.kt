package com.example.cvbuilder.ui.skills

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.cvbuilder.R
import com.example.cvbuilder.databinding.FragmentEditSkillBinding
import com.example.cvbuilder.databinding.FragmentSkillsBinding
import com.example.cvbuilder.db.CVBuilderDatabase
import com.example.cvbuilder.db.Skill
import com.example.cvbuilder.ui.BaseFragment
import kotlinx.coroutines.launch

class EditSkillFragment : BaseFragment() {

    private var skill: Skill?=null

    private var _binding: FragmentEditSkillBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = EditSkillFragment()
    }

    private lateinit var viewModel: EditSkillViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEditSkillBinding.inflate(inflater, container, false)
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
        viewModel = ViewModelProvider(this).get(EditSkillViewModel::class.java)
        // TODO: Use the ViewModel
        arguments?.let {
            // get the note value from the HomeFragment using Bundle instance
            skill = EditSkillFragmentArgs.fromBundle(it).skill
            val spinner=binding.spSkillType
            val type=skill?.type
            for(i in 0 until spinner.count){
                if(spinner.getItemAtPosition(i).toString().equals(type)){
                    spinner.setSelection(i)
                    break
                }
            }
            binding.etSkillName.setText(skill?.name)
            binding.etSkillPercentage.setText(skill?.percentage.toString())
        }
        binding.btDeleteSkill.setOnClickListener {
            if (skill != null) deleteSkill() else Toast.makeText(context,"Cannot delete",Toast.LENGTH_LONG).show()
        }
        binding.btSaveSkill.setOnClickListener {
            val type=binding.spSkillType.selectedItem.toString()
            val name=binding.etSkillName.text.toString()
            val percentage=binding.etSkillPercentage.text.toString()
            if (name.isEmpty()) {
                binding.etSkillName.error="Required"
                return@setOnClickListener
            }
            if (percentage.isEmpty()) {
                binding.etSkillPercentage.error="Required"
                return@setOnClickListener
            }
            try{
                val i=Integer.parseInt(percentage)
                if (i<0 || i>100) {
                    binding.etSkillPercentage.error="Must be integer between 0 and 100"
                    return@setOnClickListener
                }
            }catch (e: NumberFormatException){
                binding.etSkillPercentage.error="Must be integer"
                return@setOnClickListener
            }

            launch {
                context?.let {
                    val mSkill = Skill(type, name, Integer.parseInt(percentage))
                    // note == null means Inserting a new Note
                    if (skill == null) {
                        CVBuilderDatabase(it).getSkillDao().addSkill(mSkill)
                    } else {
                        // Update the note
                        mSkill.id = skill!!.id
                        CVBuilderDatabase(it).getSkillDao().updateSkill(mSkill)
                    }
                    // after adding a note need to return to Home_Fragment as per the navigation directions
                    val action = EditSkillFragmentDirections.actionEditSkillFragmentToNavSkills()
                    findNavController().navigate(action)
                }
            }
        }
    }

    private fun deleteSkill() {
        AlertDialog.Builder(context).apply {
            setTitle("Are you sure?")
            setMessage("You cannot undo this operation")
            setPositiveButton("Yes") {dialog, which ->
                launch {
                    CVBuilderDatabase(context).getSkillDao().deleteSkill(skill!!)
                    val action = EditSkillFragmentDirections.actionEditSkillFragmentToNavSkills()
                    findNavController().navigate(action)
                }
            }
            setNegativeButton("No") {dialog, which->

            }
        }.create().show()
    }

}