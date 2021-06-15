package com.juandelarosa.foreigncurrency.ui

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.Spanned
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.juandelarosa.foreigncurrency.app.FixerCurrenciesApp
import com.juandelarosa.foreigncurrency.databinding.ActivityConvertionBinding
import java.util.regex.Matcher
import java.util.regex.Pattern


class ConvertionActivity : AppCompatActivity() {
    private lateinit var base : String
    private val binding: ActivityConvertionBinding by lazy { ActivityConvertionBinding.inflate(layoutInflater) }
    private val vm: ConvertionActivityViewModel by lazy { ConvertionActivityViewModel.ConvertionActivityViewModelFactory((application as FixerCurrenciesApp)).create(ConvertionActivityViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        binding.vm = vm
        binding.lifecycleOwner = this
        base = intent.extras!!.getString("base").orEmpty()
        vm.prepateUI(base)
        binding.list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.list.adapter = LatestAdapter()
        vm.currencies.observe(this, { currencys ->
            currencys?.let {
                (binding.list.adapter as LatestAdapter).setData(it)
            }
        })

        vm.error.observe(this, {
            alertDialog(it)
        })

        binding.amount.filters = arrayOf<InputFilter>(DecimalDigitsInputFilter(6, 2))
        binding.amount.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                (binding.list.adapter as LatestAdapter).filter.filter(binding.amount.text)
            }
        })
    }

    fun alertDialog(message: String){
        val alertDialog: AlertDialog = AlertDialog.Builder(this@ConvertionActivity).create()
        alertDialog.setTitle("Alert")
        alertDialog.setMessage(message)
        alertDialog.setCancelable(false)
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Back"
        ) { dialog, which ->
            onBackPressed()
            dialog.dismiss() }
        alertDialog.show()
    }

    internal class DecimalDigitsInputFilter(digitsBeforeZero: Int, digitsAfterZero: Int) : InputFilter {
        private val mPattern: Pattern = Pattern.compile("[0-9]{0," + (digitsBeforeZero - 1) + "}+((\\.[0-9]{0," + (digitsAfterZero - 1) + "})?)||(\\.)?")
        override fun filter(source: CharSequence, start: Int, end: Int, dest: Spanned, dstart: Int, dend: Int): CharSequence? {
            val matcher: Matcher = mPattern.matcher(dest)
            return if (!matcher.matches()) "" else null
        }
    }
}