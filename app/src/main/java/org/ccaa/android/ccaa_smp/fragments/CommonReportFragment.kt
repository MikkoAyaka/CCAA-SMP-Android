package org.ccaa.android.ccaa_smp.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.findFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import org.ccaa.android.ccaa_smp.api.abstracts.CommonFragment
import org.ccaa.android.ccaa_smp.databinding.FragmentBaseReportBinding
import org.ccaa.android.ccaa_smp.entity.adapters.FormRVAdapter
import org.ccaa.android.ccaa_smp.entity.datas.FormItemData
import org.ccaa.android.ccaa_smp.utils.FileUtil
import java.io.File

class CommonReportFragment : CommonFragment() {
    private lateinit var binding: FragmentBaseReportBinding

    lateinit var fileSelectorResultLauncher : ActivityResultLauncher<Intent>

    override fun initListener(view: View) {

    }

    override fun initComponent(view: View) {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentBaseReportBinding.inflate(inflater, container, false)
        binding.recyclerview.adapter = FormRVAdapter(FormItemData.nowDataList)
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        binding.recyclerview.layoutManager = layoutManager
        binding.recyclerview.addItemDecoration(DividerItemDecoration(binding.root.context, DividerItemDecoration.VERTICAL))

        val view = binding.root

        fileSelectorResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if(it.resultCode == Activity.RESULT_OK)
            {
                val intent = it.data ?: return@registerForActivityResult
                val tempFiles: MutableList<File> = mutableListOf()
                if (intent.data != null) //选择一张图片
                {
                    tempFiles.add(FileUtil.uri2File(view.context, intent.data!!) ?: return@registerForActivityResult)
                } else if (intent.clipData != null) //选择多张图片
                {
                    val selectFiles = intent.clipData ?: return@registerForActivityResult
                    for (i in 0 until selectFiles.itemCount) {
                        tempFiles.add(
                            FileUtil.uri2File(view.context, selectFiles.getItemAt(i).uri) ?: continue)
                    }
                }
                val adapter : FormRVAdapter = binding.recyclerview.adapter as FormRVAdapter
                if(adapter.bundle.getString("type") == "file")
                {
                    adapter.selectedFiles = tempFiles
                    adapter.refreshDialogFiles()
                }
                if(adapter.bundle.getString("type") == "image")
                {
                    adapter.selectedImgs = tempFiles
                    adapter.refreshDialogImages()
                }
//                Toast.makeText(this.context,tempFiles.map { file -> file.path }.toString(),Toast.LENGTH_LONG).show()
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener(view)
        initComponent(view)
    }
}