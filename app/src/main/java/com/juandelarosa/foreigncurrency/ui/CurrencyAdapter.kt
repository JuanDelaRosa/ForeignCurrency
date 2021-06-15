package com.juandelarosa.foreigncurrency.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.juandelarosa.domain.entities.Currency
import com.juandelarosa.foreigncurrency.databinding.CurrencyItemBinding
import java.util.*
import kotlin.collections.ArrayList

class CurrencyAdapter(val currencyClick: (Currency)-> Unit): RecyclerView.Adapter<CurrencyAdapter.SearchViewHolder>(), Filterable {

    var currencyList: ArrayList<Currency> = arrayListOf()
    var currencyFilterList : ArrayList<Currency> = arrayListOf()

    lateinit var binding : CurrencyItemBinding

    fun setData(list: List<Currency>){
        if(!list.isNullOrEmpty()){
            if(currencyList.isNullOrEmpty())
                currencyList = list as ArrayList<Currency>
            else {
                list.forEach {
                    if(!currencyList.contains(it))
                        currencyList.add(it)
                }
            }
            currencyFilterList = currencyList
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        binding = CurrencyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return currencyFilterList.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val currency = currencyFilterList[position]
        holder.binding.name.text = currency.name
        holder.binding.symbol.text = currency.symbol
        holder.binding.root.setOnClickListener{ currencyClick(currency)}
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                currencyFilterList = if (charSearch.isEmpty()) {
                    currencyList
                } else {
                    val resultList = ArrayList<Currency>()
                    for (row in currencyList) {
                        if (row.name.lowercase(Locale.ROOT).contains(charSearch.lowercase(Locale.ROOT)) || row.symbol.lowercase(Locale.ROOT).contains(charSearch.lowercase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = currencyFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                currencyFilterList = results?.values as ArrayList<Currency>
                notifyDataSetChanged()
            }

        }
    }

    class SearchViewHolder(val binding: CurrencyItemBinding): RecyclerView.ViewHolder(binding.root)
}