package com.atech.attendance.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.atech.attendance.R
import com.atech.attendance.databinding.RowAttendanceBinding
import com.atech.core.room.attendance.AttendanceModel

class AttendanceAdapter(
    private val onItemClickListener: (attendance: AttendanceModel) -> Unit = {},
    private val onCheckClick: (attendance: AttendanceModel) -> Unit = {},
    private val onWrongClick: (attendance: AttendanceModel) -> Unit = {},
    private val onLongClick: (attendance: AttendanceModel) -> Unit = {}
) : RecyclerView.Adapter<AttendanceViewHolder>() {


    var defPercentage = 75
        set(value) {
            field = value
            notifyViewsChanged()
        }

    private fun notifyViewsChanged() {
        items.forEachIndexed { index, item ->
            if (item is AttendanceItem.AttendanceData) {
                notifyItemChanged(index)
            }
        }
    }

    var items = mutableListOf<AttendanceItem>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttendanceViewHolder =
        when (viewType) {
            R.layout.row_attendance -> AttendanceViewHolder.AttendanceHolder(
                RowAttendanceBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ),
                defPercentage,
                onItemClickListener = {
                    onItemClickListener.invoke((items[it] as AttendanceItem.AttendanceData).data)
                },
                onCheckClick = {
                    onCheckClick.invoke((items[it] as AttendanceItem.AttendanceData).data)
                },
                onWrongClick = {
                    onWrongClick.invoke((items[it] as AttendanceItem.AttendanceData).data)
                },
                onLongClick = {
                    onLongClick.invoke((items[it] as AttendanceItem.AttendanceData).data)
                }
            )
            com.atech.theme.R.layout.row_ads_view -> AttendanceViewHolder.AdsHolder(
                com.atech.theme.databinding.RowAdsViewBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            else -> throw IllegalArgumentException("Invalid view type")
        }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: AttendanceViewHolder, position: Int) =
        when (holder) {
            is AttendanceViewHolder.AttendanceHolder -> holder.bind((items[position] as AttendanceItem.AttendanceData).data)
            is AttendanceViewHolder.AdsHolder -> holder.bind((items[position] as AttendanceItem.Ads))
        }

    override fun getItemViewType(position: Int): Int = when (items[position]) {
        is AttendanceItem.AttendanceData -> R.layout.row_attendance
        is AttendanceItem.Ads -> com.atech.theme.R.layout.row_ads_view
    }
}