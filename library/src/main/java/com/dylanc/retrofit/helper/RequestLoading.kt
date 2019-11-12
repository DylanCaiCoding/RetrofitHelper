package com.dylanc.retrofit.helper

import android.content.Context

/**
 * @author Dylan Cai
 * @since 2019/8/15
 */
interface RequestLoading {
  fun show(context: Context)

  fun dismiss()
}