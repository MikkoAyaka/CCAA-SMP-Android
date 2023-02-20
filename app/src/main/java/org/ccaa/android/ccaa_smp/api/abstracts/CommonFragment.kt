package org.ccaa.android.ccaa_smp.api.abstracts

import android.view.View
import androidx.fragment.app.Fragment

abstract class CommonFragment : Fragment(){
    abstract fun initListener(view : View)
    abstract fun initComponent(view : View)
}