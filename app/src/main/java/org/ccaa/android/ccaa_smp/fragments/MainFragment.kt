package org.ccaa.android.ccaa_smp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.ccaa.android.ccaa_smp.R
import org.ccaa.android.ccaa_smp.api.abstracts.CommonFragment
import org.ccaa.android.ccaa_smp.databinding.FragmentMainBinding
import org.ccaa.android.ccaa_smp.entity.datas.FormItemData

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MainFragment : CommonFragment() {

    private var _binding: FragmentMainBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener(view)
        initComponent(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun initListener(view: View) {
        binding.linearlayoutVoluntaryReport.setOnClickListener{
            // 打开自愿报告页面
            FormItemData.nowDataList = FormItemData.voluntaryReportFormDataList
            findNavController().navigate(R.id.nav_form_page)
        }
        binding.linearlayoutForceReport.setOnClickListener{
            // 打开强制报告页面
            FormItemData.nowDataList = FormItemData.forceReportFormDataList
            findNavController().navigate(R.id.nav_form_page)
        }
        binding.linearlayoutSafeCheck.setOnClickListener{
            // TODO 打开安全检查页面
            FormItemData.nowDataList = listOf()
            findNavController().navigate(R.id.nav_form_page)
        }
    }

    override fun initComponent(view: View) {

    }
}