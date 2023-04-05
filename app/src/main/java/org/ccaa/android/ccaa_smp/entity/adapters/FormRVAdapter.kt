package org.ccaa.android.ccaa_smp.entity.adapters

import android.app.AlertDialog
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.fragment.app.findFragment
import androidx.recyclerview.widget.RecyclerView.LayoutParams
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
import org.ccaa.android.ccaa_smp.fragments.CommonReportFragment
import org.ccaa.android.ccaa_smp.utils.toFullDate
import org.ccaa.android.ccaa_smp.utils.toSimpleDate
import org.json.JSONObject
import java.io.File


class FormRVAdapter(private val formItemDataList : List<FormItemData>) : CommonRVAdapter() {


    //TODO 目前一个适配器中只能有一个附件、附图表单项，否则数据容器会重复

    var bundle = Bundle()
    var selectedFiles : List<File> = listOf()
    var selectedImgs : List<File> = listOf()

    lateinit var linearLayout: LinearLayout

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
                    val extraData : Array<String> = formItemData.extraData as? Array<String> ?: arrayOf()
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
                    val extraData : Array<String> = formItemData.extraData as? Array<String> ?: arrayOf()
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
                formVH.editText.hint = "选择文件"
                formVH.editText.focusable = View.NOT_FOCUSABLE
                formVH.editText.setOnClickListener {
                    val popWindowView = View.inflate(view.context,R.layout.file_selector_popwindow,null)
                    val dialog = AlertDialog.Builder(view.context)
                        .setView(popWindowView)
                        .create()
                    linearLayout = popWindowView.findViewById(R.id.linear_layout)
                    refreshDialogFiles()
                    dialog.show()
                    val select_button = popWindowView.findViewById<Button>(R.id.select_button)
                    select_button.setOnClickListener {
                        bundle = Bundle()
                        bundle.putString("type","file")
                        val intent = Intent(Intent.ACTION_GET_CONTENT)
                        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                        intent.type = "*/*"
                        view.findFragment<CommonReportFragment>().fileSelectorResultLauncher.launch(intent)
                    }
                    val confirm_button = popWindowView.findViewById<Button>(R.id.confirm_button)
                    confirm_button.setOnClickListener {
                        dialog.dismiss()
                    }
                }
            }
            TextType.IMAGE -> {

                formVH.editText.hint = "选择图片"
                formVH.editText.focusable = View.NOT_FOCUSABLE
                formVH.editText.setOnClickListener {
                    val popWindowView = View.inflate(view.context,R.layout.file_selector_popwindow,null)
                    val dialog = AlertDialog.Builder(view.context)
                        .setView(popWindowView)
                        .create()
                    linearLayout = popWindowView.findViewById(R.id.linear_layout)
                    refreshDialogImages()
                    dialog.show()
                    val select_button = popWindowView.findViewById<Button>(R.id.select_button)
                    select_button.setOnClickListener {
                        bundle = Bundle()
                        bundle.putString("type","image")
                        val intent = Intent(Intent.ACTION_GET_CONTENT)
                        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                        intent.type = "image/*"
                        view.findFragment<CommonReportFragment>().fileSelectorResultLauncher.launch(intent)
                    }
                    val confirm_button = popWindowView.findViewById<Button>(R.id.confirm_button)
                    confirm_button.setOnClickListener {
                        dialog.dismiss()
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int = formItemDataList.size

    fun refreshDialogFiles()
    {
        linearLayout.removeAllViews()
        for (file in selectedFiles)
        {
            val view = View.inflate(linearLayout.context,R.layout.simple_graphic_item,null)
            val textView = view.findViewById<TextView>(R.id.simple_text_view)
            var fileName = file.name
            if(fileName.length >= 10)
            {
                fileName = fileName.substring(0,10)
                fileName += "..."
            }
            textView.text = fileName
            linearLayout.addView(view)
        }
    }
    fun refreshDialogImages()
    {
        linearLayout.removeAllViews()
        for (img in selectedImgs)
        {
            val view = View.inflate(linearLayout.context,R.layout.simple_graphic_item,null)
            val imageView = view.findViewById<ImageView>(R.id.simple_image)
            imageView.setImageURI(img.toUri())
            val textView = view.findViewById<TextView>(R.id.simple_text_view)
            var fileName = img.name
            if(fileName.length >= 10)
            {
                fileName = fileName.substring(0,10)
                fileName += "..."
            }
            textView.text = fileName
            linearLayout.addView(view)
        }
    }
}