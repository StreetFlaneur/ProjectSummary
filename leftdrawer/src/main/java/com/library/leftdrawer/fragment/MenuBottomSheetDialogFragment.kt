package com.library.leftdrawer.fragment

import android.app.Dialog
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetDialog
import android.support.design.widget.BottomSheetDialogFragment
import android.view.View

import com.library.leftdrawer.R

/**
 * Created by sam on 2018/2/5.
 */

class MenuBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private var mBehavior: BottomSheetBehavior<*>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(context)
        val view = View.inflate(context, R.layout.bottomsheet_dialog, null)
        dialog.setContentView(view)
        dialog.window.findViewById<View>(R.id.design_bottom_sheet)
                .setBackgroundColor(resources.getColor(R.color.transparent))
        mBehavior = BottomSheetBehavior.from(view.parent as View)
        return dialog
    }

    override fun getTheme(): Int {
        return super.getTheme()
    }

    override fun onStart() {
        super.onStart()
        mBehavior!!.state = BottomSheetBehavior.STATE_EXPANDED
    }
}
