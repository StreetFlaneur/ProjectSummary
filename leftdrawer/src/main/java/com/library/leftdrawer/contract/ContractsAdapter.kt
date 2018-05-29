package com.library.leftdrawer.contract

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.library.leftdrawer.R

import java.util.ArrayList

/**
 * Created by sam on 2018/2/6.
 */

class ContractsAdapter(recyclerView: RecyclerView, datas: ArrayList<Contracts>) : RecyclerView.Adapter<ContractsAdapter.ViewHolderContract>() {

    private var datas = ArrayList<Contracts>()
    private var context: Context

    init {
        context = recyclerView.context
        this.datas = datas
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolderContract {
        val itemLayout = LayoutInflater.from(this.context).inflate(R.layout.layout_contract, viewGroup, false)
        return ViewHolderContract(itemLayout)
    }

    override fun onBindViewHolder(viewHolderContract: ViewHolderContract, i: Int) {
        //根据position获取分类的首字母的char ascii值
        val section = getSectionForPosition(i)

        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (i == getPositionForSection(section)) {
            viewHolderContract.textViewTag!!.visibility = View.VISIBLE
            viewHolderContract.textViewTag!!.text = datas[i].sortLetters
        } else {
            viewHolderContract.textViewTag!!.visibility = View.GONE
        }
        viewHolderContract.textViewName.text = datas[i].name

    }

    override fun getItemCount(): Int {
        return datas.size
    }

      class ViewHolderContract(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var textViewName: TextView
        var textViewTag: TextView

        init {
            textViewName = itemView.findViewById(R.id.textview_name)
            textViewTag = itemView.findViewById(R.id.textview_tag)

        }
    }



    /**
     * 根据ListView的当前位置获取分类的首字母的char ascii值
     */
    fun getSectionForPosition(position: Int): Int {
        return datas[position].sortLetters[0].toInt()
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    fun getPositionForSection(section: Int): Int {
        for (i in 0 until itemCount) {
            val sortStr = datas[i].sortLetters
            val firstChar = sortStr.toUpperCase()[0]
            if (firstChar.toInt() == section) {
                return i
            }
        }
        return -1
    }
}
