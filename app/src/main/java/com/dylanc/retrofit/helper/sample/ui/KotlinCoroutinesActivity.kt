package com.dylanc.retrofit.helper.sample.ui

import android.Manifest
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.dylanc.retrofit.helper.rxjava.autoDispose
import com.dylanc.retrofit.helper.sample.R
import com.dylanc.retrofit.helper.sample.constant.DOWNLOAD_URL
import com.dylanc.retrofit.helper.sample.network.LoadingDialog
import com.dylanc.retrofit.helper.coroutines.observeRequestState
import com.tbruyelle.rxpermissions2.RxPermissions

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
    viewModel.login()
      .observeRequestState(this,
        onLoading = {
          if (it) {
            loadingDialog.show(supportFragmentManager)
          } else {
            loadingDialog.dismiss()
          }
        },
        onSuccess = { response ->
          toast("登录${response!!.data.userName}成功")
        },
        onError = {
          toast(it.message)
        }
      )
  }

  /**
   * 测试下载文件
   */
  fun download(view: View) {
    val pathname = externalCacheDir!!.path + "/test.png"
    RxPermissions(this)
      .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
      .autoDispose(this)
      .subscribe { granted ->
        if (!granted) {
          toast("请授权访问文件权限")
        } else {
          loadingDialog.show(supportFragmentManager)
          viewModel.download(DOWNLOAD_URL, pathname)
            .observe(this, Observer { file ->
              loadingDialog.dismiss()
              toast("已下载到${file.path}")
            })
        }
      }
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