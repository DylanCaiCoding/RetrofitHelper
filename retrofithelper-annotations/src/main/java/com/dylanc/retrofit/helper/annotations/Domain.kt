package com.dylanc.retrofit.helper.annotations

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.FIELD)
annotation class Domain(val value: String)