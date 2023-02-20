package org.ccaa.android.ccaa_smp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import org.ccaa.android.ccaa_smp.api.abstracts.CommonFragment
import org.ccaa.android.ccaa_smp.databinding.FragmentBaseReportBinding
import org.ccaa.android.ccaa_smp.entity.adapters.FormRVAdapter
import org.ccaa.android.ccaa_smp.entity.datas.FormItemData

class CommonReportFragment : CommonFragment() {
    private lateinit var binding: FragmentBaseReportBinding

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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener(view)
        initComponent(view)
    }
}