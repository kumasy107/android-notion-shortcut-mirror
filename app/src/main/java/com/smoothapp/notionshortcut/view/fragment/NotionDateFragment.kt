package com.smoothapp.notionshortcut.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.smoothapp.notionshortcut.controller.util.MaterialComponentUtil
import com.smoothapp.notionshortcut.databinding.FragmentNotionDateBinding
import com.smoothapp.notionshortcut.databinding.FragmentNotionSelectBinding
import com.smoothapp.notionshortcut.databinding.FragmentNotionStatusBinding
import com.smoothapp.notionshortcut.model.constant.NotionColorEnum
import com.smoothapp.notionshortcut.model.entity.NotionPostTemplate
import com.smoothapp.notionshortcut.view.adapter.NotionSelectListAdapter

class NotionDateFragment : Fragment() {


    private lateinit var binding: FragmentNotionDateBinding
    private var listener: Listener? = null

    private lateinit var startDatePicker: MaterialDatePicker<Long>
    private lateinit var startTimePicker: MaterialTimePicker
    private lateinit var endDatePicker: MaterialDatePicker<Long>
    private lateinit var endTimePicker: MaterialTimePicker


    private var isViewCreated = false
    private var isListInitialized = false
    private var isPickerShowing = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotionDateBinding.inflate(inflater, container, false)
        binding.apply {
            val commonPickerListener = createCommonPickerListener()
            startDatePicker = MaterialComponentUtil.createDatePicker(listener = commonPickerListener)
            startTimePicker = MaterialComponentUtil.createTimePicker(requireContext(), listener = commonPickerListener)
            endDatePicker = MaterialComponentUtil.createDatePicker(listener = commonPickerListener)
            endTimePicker = MaterialComponentUtil.createTimePicker(requireContext(), listener = commonPickerListener)

            startDateContainer.setOnClickListener {
                if(!isPickerShowing){
                    isPickerShowing = true
                    startDatePicker.show(parentFragmentManager, null)
                }
            }

            startTimeContainer.setOnClickListener {
                if(!isPickerShowing){
                    isPickerShowing = true
                    startTimePicker.show(parentFragmentManager, null)
                }
            }

            sendBtn.setOnClickListener {
                parentFragmentManager.popBackStack()
            }

            isViewCreated = true
            initSelectList()
            return root
        }
    }

    fun createCommonPickerListener(): MaterialComponentUtil.PickerListener {
        return object : MaterialComponentUtil.PickerListener{
            override fun onDismissed() {
                isPickerShowing = false
            }
        }
    }


    fun setListener(listener: Listener) {
        this.listener = listener
    }

    fun setSelectList(

    ) {

        isListInitialized = true
        initSelectList()
    }

    private fun initSelectList() {
        // viewCreate と listInitializeどちらでも呼び出し、後に呼ばれた方で実行される

    }

    private fun updateSelectList() {

    }

    interface Listener {
        fun onSelectChanged(selected: NotionPostTemplate.Select?)
    }

    companion object {
        @JvmStatic
        fun newInstance() = NotionDateFragment()
    }
}