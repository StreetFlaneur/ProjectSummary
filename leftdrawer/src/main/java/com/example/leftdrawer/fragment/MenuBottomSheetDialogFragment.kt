package com.example.leftdrawer.fragment

import android.app.Dialog
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetDialog
import android.support.design.widget.BottomSheetDialogFragment
import android.view.View

import com.example.leftdrawer.R

/**
 * Created by sam on 2018/2/5.
 */

class MenuBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private var mBehavior: BottomSheetBehavior<*>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogStyle)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(context, R.layout.bottomsheet_dialog, null)
        dialog.setContentView(view)
        mBehavior = BottomSheetBehavior.from(view.parent as View)
        return dialog
    }

    override fun getTheme(): Int {
        return super.getTheme()
    }

    override fun onStart() {
        super.onStart()
        mBehavior!!.state = BottomSheetBehavior.STATE_EXPANDED;
    }
}
