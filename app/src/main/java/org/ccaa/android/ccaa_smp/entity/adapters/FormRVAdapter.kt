package org.ccaa.android.ccaa_smp.entity.adapters

import android.app.AlertDialog
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.loper7.date_time_picker.DateTimeConfig
import com.loper7.date_time_picker.dialog.CardDatePickerDialog
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.ccaa.android.ccaa_smp.R
import org.ccaa.android.ccaa_smp.api.abstracts.CommonRVAdapter
import org.ccaa.android.ccaa_smp.api.enums.TextType
import org.ccaa.android.ccaa_smp.entity.datas.FormItemData
import org.ccaa.android.ccaa_smp.entity.viewholders.FormVH
import org.ccaa.android.ccaa_smp.utils.toFullDate
import org.ccaa.android.ccaa_smp.utils.toSimpleDate

class FormRVAdapter(private val formItemDataList : List<FormItemData>) : CommonRVAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        view = LayoutInflater.from(parent.context).inflate(R.layout.blank_form_item, parent, false)
        return FormVH(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val formItemData = formItemDataList[position]
        val formVH = holder as FormVH
        val para = formVH.textView.layoutParams
        para.height = 150 * formItemData.sizeWeight
        formVH.textView.layoutParams = para

        formVH.textView.text = formItemData.name
        when(formItemData.textType) {
            TextType.TEXT -> {
                formVH.editText.hint = "请填写"
                formVH.editText.focusable = View.FOCUSABLE
                formVH.editText.setOnClickListener {  }
            }
            TextType.SIMPLE_DATE -> {
                formVH.editText.hint = "选择日期"
                formVH.editText.focusable = View.NOT_FOCUSABLE
                formVH.editText.setOnClickListener {
                    CardDatePickerDialog.builder(view.context)
                        .setTitle("请选择日期")
                        .setDisplayType(DateTimeConfig.YEAR,DateTimeConfig.MONTH,DateTimeConfig.DAY)
                        .setOnChoose {ms -> formVH.editText.setText(ms.toSimpleDate()) }.build().show()
                }
            }
            TextType.FULL_DATE -> {
                formVH.editText.hint = "选择时间"
                formVH.editText.focusable = View.NOT_FOCUSABLE
                formVH.editText.setOnClickListener {
                    CardDatePickerDialog.builder(view.context)
                        .setTitle("请选择详细时间")
                        .setDisplayType(DateTimeConfig.YEAR,DateTimeConfig.MONTH,DateTimeConfig.DAY,DateTimeConfig.HOUR,DateTimeConfig.MIN,DateTimeConfig.SECOND)
                        .setOnChoose {ms -> formVH.editText.setText(ms.toFullDate()) }.build().show()
                }
            }
            TextType.OPTIONAL -> {
                formVH.editText.hint = "点击选择"
                formVH.editText.focusable = View.NOT_FOCUSABLE
                formVH.editText.setOnClickListener{
                    val extraData : Array<String> = formItemData.extraData as Array<String>
                    AlertDialog.Builder(view.context)
                        .setSingleChoiceItems(extraData,-1) { dialog, id ->
                            formVH.editText.setText(extraData[id])
                            MainScope().launch {
                                delay(250)
                                dialog.dismiss()
                            }
                        }
                        .create()
                        .show()
                }
            }
            TextType.BOOLEAN -> {
                formVH.editText.hint = "点击选择 是/否"
                formVH.editText.focusable = View.NOT_FOCUSABLE
                formVH.editText.setOnClickListener{
                    val extraData = arrayOf("是","否")
                    AlertDialog.Builder(view.context)
                        .setSingleChoiceItems(extraData,-1) { dialog, id ->
                            formVH.editText.setText(extraData[id])
                            MainScope().launch {
                                delay(250)
                                dialog.dismiss()
                            }
                        }
                        .create()
                        .show()
                }
            }
            TextType.MULTI_OPTIONAL -> {
                formVH.editText.hint = "点击选择(可多选)"
                formVH.editText.focusable = View.NOT_FOCUSABLE
                formVH.editText.setOnClickListener{
                    val extraData : Array<String> = formItemData.extraData as Array<String>
                    val boolArray = BooleanArray(extraData.size) { false }
                    var resultStr = ""
                    AlertDialog.Builder(view.context)
                        .setMultiChoiceItems(extraData, boolArray) { dialog, slot, bool ->
                            resultStr = ""
                            for (index in extraData.indices) {
                                if(boolArray[index])resultStr += "${extraData[index]} "
                            }
                            formVH.editText.setText(resultStr)
                        }
                        .create()
                        .show()
                }
            }
            TextType.FILE -> {
                formVH.editText.hint = "暂未实现 文件选择器"
                formVH.editText.focusable = View.FOCUSABLE
                formVH.editText.setOnClickListener {  }
            }
            TextType.IMAGE -> {
                formVH.editText.hint = "暂未实现 图片选择器"
                formVH.editText.focusable = View.FOCUSABLE
                formVH.editText.setOnClickListener {  }
            }
        }
    }

    override fun getItemCount(): Int = formItemDataList.size
}