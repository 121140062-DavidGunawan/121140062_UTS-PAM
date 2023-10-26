package com.example.projectuts.homepage

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectuts.R
import com.example.projectuts.model.DataItem
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import java.util.Locale

class RecyclerViewAdapter (private var data: MutableList<DataItem>, val activity : Activity): RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>(), Filterable {
    private var dataListOriginal: MutableList<DataItem> = data

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.rv_list_item, parent, false)
        return ViewHolder(view)
    }

    fun addUser(newUsers: DataItem) {
        data.add(newUsers)
        notifyItemInserted(data.lastIndex)
        dataListOriginal = data
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val query = p0.toString().lowercase(Locale.getDefault())
                val filteredList = if (query.isEmpty()) {
                    dataListOriginal
                } else {
                    dataListOriginal.filter { name ->
                        val nameTemp = "${name.firstName} ${name.lastName}"
                        nameTemp.lowercase(Locale.getDefault()).contains(query)
                    }
                }
                val results = FilterResults()
                results.values = filteredList
                return results
            }
            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                data = p1?.values as MutableList<DataItem>
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = data[position]

        val name = "${user.firstName} ${user.lastName}"
        holder.Username.text = name
        Glide.with(holder.itemView.context)
            .load(user.avatar)
            .apply(RequestOptions().override(80, 80).placeholder(R.drawable.icon_avatar))
            .transform(CircleCrop())
            .into(holder.avatar)
        holder.mail.text = user.email

    }


    class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
        var avatar: ImageView = itemView.findViewById(R.id.img_view)
        var Username: TextView = itemView.findViewById(R.id.name_view)
        var mail: TextView = itemView.findViewById(R.id.email_view)

        interface ItemClickListener {
            fun onItemClick(position: Int)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(value: DataItem)
    }
}
