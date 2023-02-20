package org.ccaa.android.ccaa_smp.entity.viewholders

import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import org.ccaa.android.ccaa_smp.R

class FormVH(val view: View) : ViewHolder(view) {

    val textView : TextView = view.findViewById(R.id.text_view)
    val editText : EditText = view.findViewById(R.id.edit_text)
}