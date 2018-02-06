package com.example.leftdrawer.contract

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.SectionIndexer
import android.widget.TextView

import com.example.leftdrawer.R
import com.sam.library.Constant

import java.util.ArrayList

/**
 * Created by sam on 2018/2/6.
 */

class ContractAdapter(contractsList: ArrayList<Contracts>, private val context: Context) : BaseAdapter(), SectionIndexer {

    private var contractsList = ArrayList<Contracts>()

    init {
        this.contractsList = contractsList
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        var viewHolder: ViewHolder? = null
        val mContent = contractsList[position]
        if (convertView == null) {
            viewHolder = ViewHolder()
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_contract, null)
            viewHolder.tvTitle = convertView!!.findViewById<View>(R.id.textview_name) as TextView
            viewHolder.tvLetter = convertView.findViewById<View>(R.id.textview_tag) as TextView
            convertView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
        }

        //根据position获取分类的首字母的char ascii值
        val section = getSectionForPosition(position)

        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            viewHolder.tvLetter!!.visibility = View.VISIBLE
            viewHolder.tvLetter!!.text = mContent.sortLetters
        } else {
            viewHolder.tvLetter!!.visibility = View.GONE
        }

        viewHolder.tvTitle!!.text = this.contractsList[position].name

        return convertView
    }

    override fun getCount(): Int {
        return this.contractsList.size
    }

    override fun getItem(position: Int): Any {
        return contractsList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的char ascii值
     */
    override fun getSectionForPosition(position: Int): Int {
        return contractsList[position].sortLetters[0].toInt()
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    override fun getPositionForSection(section: Int): Int {
        for (i in 0 until count) {
            val sortStr = contractsList[i].sortLetters
            val firstChar = sortStr.toUpperCase()[0]
            if (firstChar.toInt() == section) {
                return i
            }
        }

        return -1
    }

    override fun getSections(): Array<Any>? {
        return null
    }

    internal class ViewHolder {
        var tvLetter: TextView? = null
        var tvTitle: TextView? = null
    }
}
