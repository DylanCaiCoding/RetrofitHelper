package com.dylanc.retrofit.helper.sample.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.dylanc.retrofit.helper.sample.R
import com.dylanc.retrofit.helper.sample.network.LoadingDialog

@Suppress("UNUSED_PARAMETER")
class KotlinCoroutinesActivity : AppCompatActivity() {

  private val viewModel: KotlinCoroutinesViewModel by viewModels()
  private val loadingDialog = LoadingDialog()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_common)
    viewModel.requestException
      .observe(this, Observer {
        loadingDialog.dismiss()
        toast(it.message)
      })
  }

  /**
   * 测试普通请求
   */
  fun requestArticleList(view: View) {
    loadingDialog.show(supportFragmentManager)
    viewModel.geArticleList()
      .observe(this, Observer {
        loadingDialog.dismiss()
        alert(it)
      })
  }

  /**
   * 测试不同 base url 的请求
   */
  fun requestGankTodayList(view: View) {
    loadingDialog.show(supportFragmentManager)
    viewModel.getGankTodayList()
      .observe(this, Observer {
        loadingDialog.dismiss()
        alert(it)
      })
  }

  /**
   * 测试返回本地 json 的模拟请求
   */
  fun requestLogin(view: View) {
    loadingDialog.show(supportFragmentManager)
    viewModel.login()
      .observe(this, Observer { result ->
        loadingDialog.dismiss()
        toast("登录${result.data.userName}成功")
      })
  }

  /**
   * 测试下载文件
   */
  fun download(view: View) {

  }

  private fun alert(msg: String) {
    AlertDialog.Builder(this)
      .setTitle("Response data")
      .setMessage(msg)
      .create()
      .show()
  }

  private fun toast(msg: String?) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
  }
}