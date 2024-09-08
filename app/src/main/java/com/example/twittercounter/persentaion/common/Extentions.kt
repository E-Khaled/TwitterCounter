package com.example.twittercounter.persentaion.common

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.text.Editable
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.util.Locale

private var showToast = true

fun Fragment.toast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    if (showToast)
        Toast.makeText(this.context, text, duration).show()
}

fun Context.setToastEnabled(enabled: Boolean) {
    showToast = enabled
}
fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
fun Context.getLocalizedResources(language: String): Resources {
    var conf: Configuration = resources.configuration
    conf = Configuration(conf)
    conf.setLocale(Locale(language))
    val localizedContext = createConfigurationContext(conf)
    return localizedContext.resources
}