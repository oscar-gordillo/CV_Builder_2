package com.example.cvbuilder.ui.certification

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.cvbuilder.databinding.FragmentEditCertificationBinding
import com.example.cvbuilder.db.CVBuilderDatabase
import com.example.cvbuilder.db.Certification
import com.example.cvbuilder.ui.BaseFragment
import com.example.cvbuilder.ui.certification.EditCertificationFragmentArgs


import kotlinx.coroutines.launch

class EditCertificationFragment : BaseFragment() {

    private var certification: Certification?=null

    private var _binding: FragmentEditCertificationBinding? = null



    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = EditCertificationFragment()
    }




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentEditCertificationBinding.inflate(inflater, container, false)
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
            certification = EditCertificationFragmentArgs.fromBundle(it).certification
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
        binding.btDeleteCertification.setOnClickListener {
            if (certification != null) deleteCertification() else Toast.makeText(context,"Cannot delete", Toast.LENGTH_LONG).show()
        }
        binding.btSaveCertification.setOnClickListener {
            val name=binding.nameCertification.text.toString()
            val organization=binding.organizationCertification.text.toString()
            val date=binding.dateCertification.text.toString()
            val expiration=binding.expirationCertification.text.toString()

            if (name.isEmpty()) {
                binding.nameCertification.error="Required"
                return@setOnClickListener
            }
            if (organization.isEmpty()) {
                binding.organizationCertification.error="Required"
                return@setOnClickListener
            }
            if (date.isEmpty()) {
                binding.dateCertification.error="Required"
                return@setOnClickListener
            }
            if (expiration.isEmpty()) {
                binding.expirationCertification.error="Required"
                return@setOnClickListener
            }

            launch {
                context?.let {
                    val mCertification = Certification(name, organization, date, expiration)
                    // note == null means Inserting a new Note
                    if (certification == null) {
                        CVBuilderDatabase(it).getCertificationDao().addCertification(mCertification)
                    } else {
                        // Update the note
                        mCertification.id = certification!!.id
                        CVBuilderDatabase(it).getCertificationDao().updateCertification(mCertification)
                    }
                    // after adding a note need to return to Home_Fragment as per the navigation directions
                    val action =
                        EditCertificationFragmentDirections.actionEditCertificationFragmentToNavCertification()
                    findNavController().navigate(action)
                }
            }
        }
    }

    private fun deleteCertification() {
        AlertDialog.Builder(context).apply {
            setTitle("Are you sure?")
            setMessage("You cannot undo this operation")
            setPositiveButton("Yes") {dialog, which ->
                launch {
                    CVBuilderDatabase(context).getCertificationDao().deleteCertification(certification!!)
                    val action =
                        EditCertificationFragmentDirections.actionEditCertificationFragmentToNavCertification()
                    findNavController().navigate(action)
                }
            }
            setNegativeButton("No") {dialog, which->

            }
        }.create().show()
    }
}