package com.example.cvbuilder.ui.education

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.cvbuilder.R
import com.example.cvbuilder.db.Education


import kotlinx.android.synthetic.main.education_detail.view.*


class EducationAdapter(private val educations: List<Education>) : RecyclerView.Adapter<EducationAdapter.EducationViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EducationAdapter.EducationViewHolder {
        return EducationViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.education_detail, parent, false)
        )
    }

    override fun getItemCount() = educations.size

    override fun onBindViewHolder(holder: EducationAdapter.EducationViewHolder, position: Int) {
        holder.view.tv_school_education.text= educations[position].schoolName
        holder.view.tv_city_education.text=educations[position].city
        holder.view.tv_state_education.text=educations[position].state
        holder.view.tv_degree_education.text=educations[position].degree
        holder.view.tv_completionDate_education.text=educations[position].completionDate



        holder.view.setOnClickListener{
            // Set the Navigation action
            val action = EducationFragmentDirections.actionEducationFragmentToEditEducationFragment()
            // add the selected Note
            action.education = educations[position]
            Navigation.findNavController(it).navigate(action)
        }
    }
    class EducationViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}