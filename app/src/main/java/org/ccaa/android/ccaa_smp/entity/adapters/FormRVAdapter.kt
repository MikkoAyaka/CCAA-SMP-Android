package org.ccaa.android.ccaa_smp.entity.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import org.ccaa.android.ccaa_smp.R
import org.ccaa.android.ccaa_smp.api.abstracts.CommonRVAdapter
import org.ccaa.android.ccaa_smp.api.enums.TextType
import org.ccaa.android.ccaa_smp.entity.datas.FormItemData
import org.ccaa.android.ccaa_smp.entity.viewholders.FormVH

class FormRVAdapter(private val formItemDataList : List<FormItemData>) : CommonRVAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        view = LayoutInflater.from(parent.context).inflate(R.layout.blank_form_item, parent, false)
        return FormVH(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val formItemData = formItemDataList[position]
        val formVH = holder as FormVH

        val layout = view.findViewById<LinearLayout>(R.id.layout_blank_form_item)
        val para = layout.layoutParams
        para.height = 150 * formItemData.sizeWeight
        layout.layoutParams = para

        formVH.textView.text = formItemData.name
        when(formItemData.textType) {
            TextType.TEXT -> {}
            TextType.SIMPLE_DATE -> {
                formVH.editText.hint = "暂未实现 简易日期选择器"
            }
            TextType.FULL_DATE -> {
                formVH.editText.hint = "暂未实现 全量日期选择器"
            }
            TextType.OPTIONAL -> {
                formVH.editText.hint = "暂未实现 单选功能"
            }
            TextType.BOOLEAN -> {
                formVH.editText.hint = "暂未实现 布尔型选择功能"
            }
            TextType.MULTI_OPTIONAL -> {
                formVH.editText.hint = "暂未实现 多选功能"
            }
            TextType.FILE -> {
                formVH.editText.hint = "暂未实现 文件选择器"
            }
            TextType.IMAGE -> {
                formVH.editText.hint = "暂未实现 图片选择器"
            }
        }
    }

    override fun getItemCount(): Int = formItemDataList.size
}