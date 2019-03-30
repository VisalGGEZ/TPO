package com.tpo_hr.tpohr.views.dialogs

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.view.ViewGroup
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.tpo_hr.tpohr.R


@SuppressLint("ValidFragment")
class BaseProgressDialog @SuppressLint("ValidFragment") constructor
(private val appCompatActivity: AppCompatActivity) : DialogFragment() {

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window.setLayout(width, height)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreate(savedInstanceState)
        val alertDialog = AlertDialog.Builder(appCompatActivity).create()
        alertDialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.setContentView(R.layout.progress_layout)
        return alertDialog
    }

    private var shown = false

    override fun show(manager: FragmentManager?, tag: String?) {
        if (shown) return
        super.show(manager, tag)
        shown = true
    }

    override fun onDismiss(dialog: DialogInterface?) {
        shown = false
        super.onDismiss(dialog)
    }

    fun killItSelf() {
        if(shown){
            this.dismiss()
        }
    }
}