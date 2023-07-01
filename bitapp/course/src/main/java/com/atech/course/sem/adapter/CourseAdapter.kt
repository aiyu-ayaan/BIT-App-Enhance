package com.atech.course.sem.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.atech.course.R
import com.atech.course.databinding.RowSubjectsBinding
import com.atech.theme.databinding.RowTitleBinding

class CourseAdapter() : RecyclerView.Adapter<CourseViewHolder>() {
    var item: List<CourseItem> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged") set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder =
        when (viewType) {
            com.atech.theme.R.layout.row_title -> CourseViewHolder.TitleHolder(
                RowTitleBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            R.layout.row_subjects -> CourseViewHolder.SubjectHolder(
                RowSubjectsBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            else -> throw IllegalArgumentException("Invalid view type")
        }


    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) =
        when (holder) {
            is CourseViewHolder.TitleHolder -> holder.bind(item[position] as CourseItem.Title)
            is CourseViewHolder.SubjectHolder -> holder.bind(item[position] as CourseItem.Subject)
        }

    override fun getItemViewType(position: Int): Int = when (item[position]) {
        is CourseItem.Title -> com.atech.theme.R.layout.row_title
        is CourseItem.Subject -> R.layout.row_subjects
    }

    override fun getItemCount(): Int = item.size
}
