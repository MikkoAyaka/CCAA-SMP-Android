package org.ccaa.android.ccaa_smp.api.abstracts

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

abstract class CommonRVAdapter : RecyclerView.Adapter<ViewHolder>() {

    lateinit var view : View

}