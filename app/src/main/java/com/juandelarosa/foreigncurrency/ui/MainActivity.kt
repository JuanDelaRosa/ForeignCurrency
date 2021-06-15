package com.juandelarosa.foreigncurrency.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.juandelarosa.foreigncurrency.app.FixerCurrenciesApp
import com.juandelarosa.foreigncurrency.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val vm: MainActivityViewModel by lazy { MainActivityViewModel.MainActivityViewModelFactory((application as FixerCurrenciesApp)).create(MainActivityViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        binding.list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.list.adapter = CurrencyAdapter {
           val intent = Intent(this, ConvertionActivity::class.java)
            intent.putExtra("base", it.symbol)
            startActivity(intent)
        }
        vm.getCurrencies()
        vm.currencies.observe(this, { currencys ->
            currencys?.let {
                (binding.list.adapter as CurrencyAdapter).setData(it)
            }
        })

        binding.search.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                (binding.list.adapter as CurrencyAdapter).filter.filter(newText)
                return false
            }

        })
    }
}