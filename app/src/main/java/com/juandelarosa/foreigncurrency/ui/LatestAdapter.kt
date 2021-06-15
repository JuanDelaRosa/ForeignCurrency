package com.juandelarosa.foreigncurrency.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.juandelarosa.domain.entities.Latest
import com.juandelarosa.foreigncurrency.databinding.CurrencyItemBinding

class LatestAdapter: RecyclerView.Adapter<LatestAdapter.SearchViewHolder>(), Filterable {

    var currencyList: ArrayList<Latest> = arrayListOf()
    var currencyFilterList : ArrayList<Latest> = arrayListOf()

    lateinit var binding : CurrencyItemBinding

    fun setData(list: List<Latest>){
        if(!list.isNullOrEmpty()){
            if(currencyList.isNullOrEmpty())
                currencyList = list as ArrayList<Latest>
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
        holder.binding.name.text = currency.getCurrencyWithSymbol()
        holder.binding.symbol.text = currency.symbol
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                currencyFilterList = if (charSearch.isEmpty()) {
                    currencyList
                } else {
                    val resultList = ArrayList<Latest>()
                    for (row in currencyList) {
                        if(charSearch!="") {
                            val tmpDuoble = try{ charSearch.toDouble() }catch (ex: Exception){0.00}
                            val tmp = Latest(row.symbol, row.value * tmpDuoble)
                            resultList.add(tmp)
                        }else resultList.add(row)

                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = currencyFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                currencyFilterList = results?.values as ArrayList<Latest>
                notifyDataSetChanged()
            }

        }
    }

    class SearchViewHolder(val binding: CurrencyItemBinding): RecyclerView.ViewHolder(binding.root)
}