package com.example.leftdrawer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast

import com.sam.library.base.BaseActivity

import com.example.leftdrawer.contract.ContractAdapter
import com.example.leftdrawer.contract.Contracts
import com.example.leftdrawer.contract.ContractsAdapter
import com.example.leftdrawer.utils.PinyinComparator
import com.example.leftdrawer.utils.PinyinUtils
import com.example.leftdrawer.contract.SlideBar
import kotlinx.android.synthetic.main.activity_contract.*
import java.util.*


/**
 * Created by sam on 2018/2/6.
 */

class ContractActivity : AppCompatActivity() {

    var contracts = ArrayList<Contracts>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contract)
        initView()
        initData()
    }

    fun initData() {
        contracts = initContract()
        Collections.sort(contracts, PinyinComparator())
        var linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerview.layoutManager = linearLayoutManager
        var adapter = ContractsAdapter(recyclerview, contracts)
        recyclerview.adapter = adapter

        slidebar.setOnTouchingLetterChangedListener(object : SlideBar.OnTouchingLetterChangedListener {
            override fun onTouchingLetterChanged(s: String) {
                //该字母首次出现的位置
                val firstChar = s?.toUpperCase()[0]
                val position = adapter.getPositionForSection(firstChar.toInt())
                if (position != -1) {
                    linearLayoutManager.scrollToPositionWithOffset(position, 0)
                }
            }
        })

    }

    fun initView() {
        slidebar.setTextView(textview_char)

    }

    fun initContract(): ArrayList<Contracts> {
        val contractTemp = resources.getStringArray(R.array.contacts)
        val mSortList = ArrayList<Contracts>()
        val indexString = ArrayList<String>()

        for (i in 0 until contractTemp.size) {
            val contract = Contracts()
            contract.setName(contractTemp[i])
            val pinyin = PinyinUtils.getPingYin(contractTemp[i])
            val sortString = pinyin.substring(0, 1).toUpperCase()
            if (sortString.matches("[A-Z]".toRegex())) {
                contract.setSortLetters(sortString.toUpperCase())
                if (!indexString.contains(sortString)) {
                    indexString.add(sortString)
                }
            }
            mSortList.add(contract)
        }
        Collections.sort(indexString)
        slidebar.setIndexText(indexString)
        return mSortList
    }
}
