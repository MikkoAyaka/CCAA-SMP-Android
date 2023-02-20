package org.ccaa.android.ccaa_smp.entity.datas

import org.ccaa.android.ccaa_smp.api.enums.TextType

data class FormItemData(val name : String,val sizeWeight : Int,val nullable : Boolean,val textType : TextType,val extraData : Any = Any())
{
    companion object{

        var nowDataList = listOf<FormItemData>()

        val voluntaryReportFormDataList = listOf(
            FormItemData("所属机场",1,false,TextType.TEXT),
            FormItemData("填报人",1,false,TextType.TEXT),
            FormItemData("填报部门",1,false,TextType.TEXT),
            FormItemData("填报日期",1,false,TextType.FULL_DATE),
            FormItemData("报告编号",1,false,TextType.TEXT),
            FormItemData("事发时间",1,true,TextType.FULL_DATE),
            FormItemData("事发区域",1,true,TextType.OPTIONAL),
            FormItemData("事发地点",2,false,TextType.TEXT),
            FormItemData("问题描述及建议",3,true,TextType.TEXT),
            FormItemData("附件",3,false,TextType.FILE),
        )

        val forceReportFormDataList = listOf(
            FormItemData("所属机场",1,false,TextType.TEXT),
            FormItemData("填报人",1,false,TextType.TEXT),
            FormItemData("填报部门",1,false,TextType.TEXT),
            FormItemData("填报日期",1,false,TextType.FULL_DATE),
            FormItemData("报告编号",1,false,TextType.TEXT),
            FormItemData("是否报局方",1,false,TextType.BOOLEAN),
            FormItemData("事件是否紧急",1,false,TextType.BOOLEAN),
            FormItemData("事发时间",1,true,TextType.FULL_DATE),
            FormItemData("事发性质",1,false,TextType.OPTIONAL),
            FormItemData("事发区域",1,true,TextType.OPTIONAL),
            FormItemData("事发地点",2,false,TextType.TEXT),
            FormItemData("报告标题",2,true,TextType.TEXT),
            FormItemData("事件经过",3,true,TextType.TEXT),
            FormItemData("初步分析说明",3,true,TextType.TEXT),
            FormItemData("事件类型",1,true,TextType.OPTIONAL),
            FormItemData("事件类型",1,true,TextType.OPTIONAL),
            FormItemData("涉及相关",1,true,TextType.MULTI_OPTIONAL),
            FormItemData("附件",3,false,TextType.FILE),
        )

    }
}
