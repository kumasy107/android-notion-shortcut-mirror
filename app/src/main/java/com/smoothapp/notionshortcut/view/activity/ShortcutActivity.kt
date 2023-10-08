package com.smoothapp.notionshortcut.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.WindowManager
import android.widget.PopupWindow
import androidx.fragment.app.FragmentManager
import com.smoothapp.notionshortcut.R
import com.smoothapp.notionshortcut.controller.provider.NotionApiProvider
import com.smoothapp.notionshortcut.controller.util.NotionApiPostPageUtil
import com.smoothapp.notionshortcut.databinding.ActivityShortcutBinding
import com.smoothapp.notionshortcut.model.constant.NotionApiPropertyEnum
import com.smoothapp.notionshortcut.model.constant.NotionColorEnum
import com.smoothapp.notionshortcut.model.entity.NotionDatabaseProperty
import com.smoothapp.notionshortcut.model.entity.NotionPostTemplate
import com.smoothapp.notionshortcut.view.component.notion_shortcut.ShortcutRootView
import com.smoothapp.notionshortcut.view.component.notion_shortcut.main_element.ShortcutSelectView
import com.smoothapp.notionshortcut.view.fragment.NotionSelectFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ShortcutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShortcutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShortcutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = this.getColor(R.color.transparent)

        binding.apply {
            root.setOnClickListener {
                val d = Date()
                val sf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault())
                val formatD = sf.format(d)
//                MainScope().launch {
//                    Log.d("", NotionApiPostPageUtil.postPageToDatabase(
//                        "94f6ca48-d506-439f-9d2e-0fa7a2bcd5d4",
//                        listOf(
//                            NotionDatabaseProperty(NotionApiPropertyEnum.TITLE,"名前", listOf("hoge")),
//                            NotionDatabaseProperty(NotionApiPropertyEnum.RICH_TEXT,"テキスト 1", listOf("こんにちは")),
//                            NotionDatabaseProperty(NotionApiPropertyEnum.NUMBER,"数値bar", listOf("30.5")),
//                            NotionDatabaseProperty(NotionApiPropertyEnum.CHECKBOX, "チェックボックス", listOf("true")),
//                            NotionDatabaseProperty(NotionApiPropertyEnum.SELECT, "セレクト", listOf("オプション1")),
//                            NotionDatabaseProperty(NotionApiPropertyEnum.MULTI_SELECT, "タグだよ", listOf("わ", "をおー")),
//                            NotionDatabaseProperty(NotionApiPropertyEnum.STATUS, "ステータス", listOf("Done")),
//                            NotionDatabaseProperty(NotionApiPropertyEnum.RELATION, "aiueoとのリレーション", listOf("ecd1c8b627f54ecca674a309b5826279", "f1d5a00e704f491080cf883d3815a6ba")),
//                            NotionDatabaseProperty(NotionApiPropertyEnum.DATE, "日付", listOf(formatD, formatD))
//                            )
//                    ))
//                }
            }

            shortcutRoot.setTemplate(
                NotionPostTemplate(
                    NotionPostTemplate.TemplateType.DATABASE,
                    listOf(
                        NotionPostTemplate.Property(NotionApiPropertyEnum.TITLE, "名前"),
                        NotionPostTemplate.Property(NotionApiPropertyEnum.RICH_TEXT, "テキスト 1"),
                        NotionPostTemplate.Property(NotionApiPropertyEnum.NUMBER, "数値bar"),
                        NotionPostTemplate.Property(NotionApiPropertyEnum.CHECKBOX, "チェックボックス"),
                        NotionPostTemplate.Property(
                            NotionApiPropertyEnum.SELECT, "セレクト", listOf(
                                NotionPostTemplate.Select("hoge", NotionColorEnum.BLUE),
                                NotionPostTemplate.Select("nice", NotionColorEnum.RED)
                            )
                        )
                    )
                )
            )

        }
    }

    private fun ShortcutRootView.setTemplate(template: NotionPostTemplate) {
        for (property in template.propertyList) {
            when (property.type) {
                NotionApiPropertyEnum.TITLE -> addTitleBlock(property.name)
                NotionApiPropertyEnum.RICH_TEXT -> addRichTextBlock(property.name)
                NotionApiPropertyEnum.NUMBER -> addNumberBlock(property.name)
                NotionApiPropertyEnum.CHECKBOX -> addCheckboxBlock(property.name)
                NotionApiPropertyEnum.SELECT -> {
                    addSelectBlock(property.name, listener = object : ShortcutSelectView.Listener {
                        override fun onClick(shortcutSelectView: ShortcutSelectView) {
                            val fragment = NotionSelectFragment.newInstance().apply {
                                setListener(
                                    object : NotionSelectFragment.Listener {
                                        override fun onSelect(selected: NotionPostTemplate.Select) {
                                            shortcutSelectView.setSelected(selected)
                                        }
                                        override fun onUnselect(unselected: NotionPostTemplate.Select) {
                                            shortcutSelectView.setSelected(null)
                                        }
                                    }
                                )

                                setCanSelectMultiple(false)

                                MainScope().launch {
                                    val unselectedList = getSelectList().toMutableList()
                                    val selectedList = shortcutSelectView.getSelected()
                                    unselectedList.removeAll(selectedList)
                                    setSelectList(unselectedList, selectedList)
                                }

                            }
                            supportFragmentManager.beginTransaction()
                                .add(binding.overlayContainer.id, fragment)
                                .addToBackStack(null)
                                .commit()
                        }
                    })
                }

                NotionApiPropertyEnum.MULTI_SELECT -> addMultiSelectBlock(property.name)
                NotionApiPropertyEnum.STATUS -> addStatusBlock(property.name)
                NotionApiPropertyEnum.RELATION -> addRelationBlock(property.name)
                NotionApiPropertyEnum.DATE -> addDateBlock(property.name)
            }
        }
    }

    private suspend fun getSelectList() = withContext(Dispatchers.IO){
        delay(500)
        return@withContext listOf(
            NotionPostTemplate.Select("apple", NotionColorEnum.BLUE),
            NotionPostTemplate.Select("banana", NotionColorEnum.BLUE),
            NotionPostTemplate.Select("chocolate", NotionColorEnum.BLUE),
            NotionPostTemplate.Select("document", NotionColorEnum.BLUE),
            NotionPostTemplate.Select("effect", NotionColorEnum.BLUE),
            NotionPostTemplate.Select("filter", NotionColorEnum.BLUE),
            NotionPostTemplate.Select("green", NotionColorEnum.BLUE)
        )
    }


}