package com.smoothapp.notionshortcut.model.entity.notiondatabaseproperty

import com.smoothapp.notionshortcut.controller.util.DateTimeUtil
import com.smoothapp.notionshortcut.model.constant.NotionApiPropertyEnum

class NotionDatabasePropertyDate(
    name: String,
    private var dateFrom: DateTimeUtil.DateTime?,
    private var dateTo: DateTimeUtil.DateTime?
) : NotionDatabaseProperty(NotionApiPropertyEnum.DATE, name, listOf()) {

    init {
        updateParentContents()
    }

    private fun updateParentContents(){
        val contents: MutableList<String?> = MutableList(SET_SIZE){null}
        contents[FROM_INDEX] = dateFrom?.convertToString()
        contents[TO_INDEX] = dateTo?.convertToString()
        setPropertyContents(contents)
    }

    fun updateContents(dateFrom: DateTimeUtil.DateTime?, dateTo: DateTimeUtil.DateTime?){
        this.dateFrom = dateFrom
        this.dateTo = dateTo
        updateParentContents()
    }

    fun updateFromDate(dateLong: Long){
        dateFrom?.setDate(dateLong)
        updateParentContents()
    }

    fun updateToDate(dateLong: Long){
        dateTo?.setDate(dateLong)
        updateParentContents()
    }

    fun updateFromTime(hour: Int, minute: Int){
        dateFrom?.setHour(hour)
        dateFrom?.setMinute(minute)
        updateParentContents()
    }

    fun updateToTime(hour: Int, minute: Int){
        dateTo?.setHour(hour)
        dateTo?.setMinute(minute)
        updateParentContents()
    }

    fun getDateFrom(): DateTimeUtil.DateTime? = dateFrom

    fun getDateTo(): DateTimeUtil.DateTime? = dateTo

    companion object {
        private const val FROM_INDEX = 0 // primary
        private const val TO_INDEX = 1

        private const val SET_SIZE = 2
    }
}


