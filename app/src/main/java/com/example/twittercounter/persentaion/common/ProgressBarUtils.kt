package com.example.twittercounter.persentaion.common

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.CountDownTimer
import android.widget.LinearLayout
import com.example.twittercounter.R

@SuppressLint("StaticFieldLeak")
object ProgressBarUtils {


    var progressDialog: Dialog? = null

    private var mContext: Context? = null
    private var countDownTimer: CountDownTimer? = null
    fun showProgressDialog(context: Context) {
        if (context !== mContext) progressDialog = Dialog(context)
        mContext = context
        progressDialog?.setContentView(R.layout.progress_bar)
        val window = progressDialog!!.window
        window!!.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        progressDialog!!.setCancelable(false)
        if (countDownTimer != null) {
            countDownTimer!!.cancel()
        }
        countDownTimer = object : CountDownTimer(30000, 30000) {
            override fun onTick(millisUntilFinished: Long) {
                //here you can have your logic to set text to edittext
            }

            override fun onFinish() {
                if (progressDialog!!.isShowing) hideProgressDialog()
            }
        }.start()
        progressDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressDialog!!.show()
    }

    fun hideProgressDialog() {
        if (progressDialog != null) progressDialog!!.dismiss()
    }

}