package com.example.cvbuilder.ui.experience

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.cvbuilder.R
import com.example.cvbuilder.db.Experience
import kotlinx.android.synthetic.main.experience_detail.view.*


class ExperienceAdapter(private val experiences: List<Experience>) : RecyclerView.Adapter<ExperienceAdapter.ExperienceViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExperienceAdapter.ExperienceViewHolder {
        return ExperienceViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.experience_detail, parent, false)
        )
    }

    override fun getItemCount() = experiences.size

    override fun onBindViewHolder(holder: ExperienceAdapter.ExperienceViewHolder, position: Int) {
        holder.view.tv_company.text= experiences[position].company
        holder.view.tv_city.text=experiences[position].city
        holder.view.tv_state.text=experiences[position].state
        holder.view.tv_startDate.text=experiences[position].startDate
        holder.view.tv_endDate.text=experiences[position].endDate
        holder.view.tv_JobTitle.text=experiences[position].jobTitle
        holder.view.tv_description.text=experiences[position].description



        holder.view.setOnClickListener{
            // Set the Navigation action
            val action = ExperienceFragmentDirections.actionNavExperienceToEditExperienceFragment()
            // add the selected Note
            action.experience = experiences[position]
            Navigation.findNavController(it).navigate(action)
        }
    }
    class ExperienceViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}