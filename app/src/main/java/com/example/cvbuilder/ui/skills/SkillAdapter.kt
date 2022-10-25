package com.example.cvbuilder.ui.skills

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.cvbuilder.R
import com.example.cvbuilder.db.CV
import com.example.cvbuilder.db.Skill
import kotlinx.android.synthetic.main.experience_detail.view.*
import kotlinx.android.synthetic.main.home_cv_detail.view.*
import kotlinx.android.synthetic.main.skill_detail.view.*


class SkillAdapter(private val skills: List<Skill>) : RecyclerView.Adapter<SkillAdapter.SkillViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkillAdapter.SkillViewHolder {
        return SkillViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.skill_detail, parent, false)
        )
    }

    override fun getItemCount() =skills.size

    override fun onBindViewHolder(holder: SkillAdapter.SkillViewHolder, position: Int) {
        holder.view.tv_type_skill.text=skills[position].type
        holder.view.tv_name_skill.text=skills[position].name
        holder.view.tv_percentage_skill.text=skills[position].percentage.toString()


        holder.view.setOnClickListener{
            // Set the Navigation action
            val action = SkillsFragmentDirections.actionNavSkillsToEditSkillFragment()
            // add the selected Note
            action.skill = skills[position]
            Navigation.findNavController(it).navigate(action)
        }
    }
    class SkillViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}