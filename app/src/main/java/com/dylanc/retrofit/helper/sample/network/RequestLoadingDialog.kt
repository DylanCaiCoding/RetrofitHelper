package com.dylanc.retrofit.helper.sample.network

import android.app.Dialog
import android.content.Context
import android.support.v7.app.AlertDialog
import com.dylanc.retrofit.helper.RequestLoading

/**
 * @author Dylan Cai
 * @since 2019/8/15
 */
class RequestLoadingDialog : RequestLoading {
  private lateinit var dialog: Dialog
  override fun show(context: Context) {
    dialog = AlertDialog.Builder(context)
      .setTitle("loading")
      .setMessage("wait for a minute...")
      .show()
  }

  override fun dismiss() {
    dialog.dismiss()
  }
}