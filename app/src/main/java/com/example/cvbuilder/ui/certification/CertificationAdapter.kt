package com.example.cvbuilder.ui.certification

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.cvbuilder.R
import com.example.cvbuilder.db.Certification
import com.example.cvbuilder.ui.certification.CertificationFragmentDirections
import kotlinx.android.synthetic.main.certification_detail.view.*


class CertificationAdapter(private val certifications: List<Certification>) : RecyclerView.Adapter<CertificationAdapter.CertificationViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CertificationViewHolder {
        return CertificationViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.certification_detail, parent, false)
        )
    }

    override fun getItemCount() = certifications.size

    override fun onBindViewHolder(holder: CertificationViewHolder, position: Int) {
        holder.view.tv_name_cert.text= certifications[position].name
        holder.view.tv_organization_cert.text=certifications[position].organization
        holder.view.tv_date_cert.text=certifications[position].date
        holder.view.tv_expiration_cert.text=certifications[position].expirationDate




        holder.view.setOnClickListener{
            // Set the Navigation action
            val action =
                CertificationFragmentDirections.actionNavCertificationToEditCertificationFragment()
            // add the selected Note
            action.certification = certifications[position]
            Navigation.findNavController(it).navigate(action)
        }
    }
    class CertificationViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}