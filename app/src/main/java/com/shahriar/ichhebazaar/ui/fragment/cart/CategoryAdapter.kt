package com.shahriar.ichhebazaar.ui.fragment.cart

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shahriar.ichhebazaar.R
import com.shahriar.ichhebazaar.data.category.Data
import com.shahriar.ichhebazaar.data.product.Product
import java.util.Locale

class CategoryAdapter(
    private var categoryList: List<Data?>?,
    private val onProductClick: (Product) -> Unit

) :RecyclerView.Adapter<CategoryAdapter.ParentViewHolder>() {

    class ParentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var productsCategory: TextView = view.findViewById(R.id.tvCategoryTitle)
        var subCategoryRV: RecyclerView = view.findViewById(R.id.rvChildItems)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentViewHolder {
        var view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.category_item, parent, false)
        return ParentViewHolder(view)
    }

    override fun onBindViewHolder(holder: ParentViewHolder, position: Int) {

        val category = categoryList?.get(position)
        holder.productsCategory.text = category?.name?.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.ROOT
            ) else it.toString()
        }
        holder.subCategoryRV.layoutManager = LinearLayoutManager(holder.itemView.context, RecyclerView.HORIZONTAL,false)
        holder.subCategoryRV.adapter = SubCategoryAdapter(category?.sub_categories, onProductClick)

    }

    override fun getItemCount(): Int {
        return categoryList?.size ?: 0
    }

    // Function to update the adapter with new data
    @SuppressLint("NotifyDataSetChanged")
    fun addNewCategory(response: List<Data?>?) {
        categoryList = response
        notifyDataSetChanged()
    }
}
