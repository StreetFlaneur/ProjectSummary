package com.example.leftdrawer.utils

import com.example.leftdrawer.contract.Contracts

import java.util.Comparator

/**
 * Created by sam on 2018/2/6.
 */

class PinyinComparator : Comparator<Contracts> {

    override fun compare(o1: Contracts, o2: Contracts): Int {
        if (o1.sortLetters.equals("@")
                || o2.sortLetters.equals("#")) {
            return -1
        } else if (o1.sortLetters.equals("#")
                || o2.sortLetters.equals("@")) {
            return 1
        } else {
            return o1.sortLetters.compareTo(o2.sortLetters)
        }
    }
}
