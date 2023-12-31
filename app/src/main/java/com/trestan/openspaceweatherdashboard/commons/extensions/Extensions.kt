@file:JvmName("ExtensionsUtils")

package com.trestan.openspaceweatherdashboard.commons.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.renderer.XAxisRenderer

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}
