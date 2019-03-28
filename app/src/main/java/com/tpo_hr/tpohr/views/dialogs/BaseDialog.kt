package com.tpo_hr.tpohr.views.dialogs

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.tpo_hr.tpohr.R


@SuppressLint("ValidFragment")
class BaseDialog(
    private val title: String,
    private val message: String,
    private val icon: Int,
    private val posBtn: String,
    private val posCode: () -> Unit,
    private val dismissCode: () -> Unit) : DialogFragment() {

    private lateinit var tvTitle: TextView
    private lateinit var tvMessage: TextView
    private lateinit var ivIcon: ImageView
    private lateinit var btnPos: Button

    private val LOG_TAG = BaseDialog::class.java.simpleName


    // onCreate --> (onCreateDialog) --> onCreateView --> onActivityCreated
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.v(LOG_TAG, "onCreateView")

        val dialogView = inflater.inflate(R.layout.dialog_content, container, false)

        tvTitle = dialogView.findViewById(R.id.tvTitle)
        if (title == "") {
            tvTitle.visibility = View.GONE
        } else {
            tvTitle.text = title
        }

        ivIcon = dialogView.findViewById(R.id.ivIcon)
        Glide.with(this).load(icon).into(ivIcon)

        tvMessage = dialogView.findViewById(R.id.tvMessage)
        tvMessage.text = message

        // "Got it" button
        btnPos = dialogView.findViewById<View>(R.id.btnAction) as Button
        btnPos.text = posBtn
        btnPos.setOnClickListener {
            posCode.invoke()
            this.dismiss()
        }

        return dialogView
    }

    // If shown as dialog, set the width of the dialog window
    // onCreateView --> onActivityCreated -->  onViewStateRestored --> onStart --> onResume
    override fun onResume() {
        super.onResume()
        Log.v(LOG_TAG, "onResume")
        if (showsDialog) {
            // Set the width of the dialog to the width of the screen in portrait mode
            activity?.let {
                val metrics = it.resources.displayMetrics
                val dialogWidth = Math.min(metrics.widthPixels, metrics.heightPixels)
                dialog.window.setLayout(dialogWidth, ViewGroup.LayoutParams.WRAP_CONTENT)
            }
        }
    }

    // If dialog is cancelled: onCancel --> onDismiss
    override fun onCancel(dialog: DialogInterface?) {
        Log.v(LOG_TAG, "onCancel")
    }

    // If dialog is cancelled: onCancel --> onDismiss
    // If dialog is dismissed: onDismiss
    override fun onDismiss(dialog: DialogInterface?) {
        if (fragmentManager != null){
            dismissCode.invoke()
            super.dismiss()
        }
    }

}