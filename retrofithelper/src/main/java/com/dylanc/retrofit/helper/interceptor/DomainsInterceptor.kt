package com.dylanc.retrofit.helper.interceptor

import com.dylanc.retrofit.helper.DOMAIN_NAME
import com.dylanc.retrofit.helper.default
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author Dylan Cai
 */
class DomainsInterceptor(
  private val domains: Map<String, String> = default.domains,
  private val headerKey: String = DOMAIN_NAME
) : Interceptor {

  override fun intercept(chain: Interceptor.Chain): Response {
    val request = chain.request()
    val baseUrl = request.url
    val builder = request.newBuilder()
    val headers = request.headers(headerKey)
    return if (headers.isEmpty()) {
      chain.proceed(request)
    } else {
      builder.removeHeader(headerKey)
      val headerValue = headers[0]
      val url = if (domains.containsKey(headerValue)) {
        domains[headerValue]?.toHttpUrlOrNull()!!
      } else {
        baseUrl
      }
      val newFullUrl = request.url.newBuilder()
        .scheme(url.scheme)
        .host(url.host)
        .port(url.port)
        .build()
      chain.proceed(builder.url(newFullUrl).build())
    }
  }
}