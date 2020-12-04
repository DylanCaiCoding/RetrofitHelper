@file:Suppress("unused")

package com.dylanc.retrofit.helper.coroutines

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import java.util.HashMap

inline fun <reified T : ViewModelRequest> requests() = lazy {
  RequestProvider.get(T::class.java)
}

fun List<ViewModelRequest>.observeLoading(owner: LifecycleOwner, observer: (Boolean) -> Unit) =
  forEach {
    it.isLoading.observe(owner, observer)
  }

fun List<ViewModelRequest>.observeException(owner: LifecycleOwner, observer: (Throwable) -> Unit) =
  forEach {
    it.exception.observe(owner, observer)
  }

abstract class ViewModelRequest {
  val isLoading = LoadingLiveData()
  val exception = ExceptionLiveData()
}