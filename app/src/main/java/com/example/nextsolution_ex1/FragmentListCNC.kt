package com.example.nextsolution_ex1

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_list_cnc.view.*

class FragmentListCNC : Fragment() {
    private val configAds: ConfigAds = ConfigAds()
    lateinit var rootView: View
    private var listSearch: ArrayList<CNC> = arrayListOf()
    private var list: ArrayList<CNC> = arrayListOf()
    lateinit var myListItem: Array<String>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_list_cnc, container, false)
        myListItem = resources.getStringArray(R.array.my_list_item)
        setData()
        val adapter: CNCAdapter = CNCAdapter(context, list)
        rootView.recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rootView.recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        rootView.recyclerView.adapter = adapter
        rootView.imageViewSearch.setOnClickListener {
            rootView.relativeLayoutSearch.visibility = View.VISIBLE
            rootView.imageViewSearch.visibility = View.GONE
            rootView.editTextSearch.setOnEditorActionListener(object :
                TextView.OnEditorActionListener {
                override fun onEditorAction(
                    v: TextView?,
                    actionId: Int,
                    event: KeyEvent?
                ): Boolean {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                        actionId == EditorInfo.IME_ACTION_DONE ||
                        event != null &&
                        event.action == KeyEvent.ACTION_DOWN &&
                        event.keyCode == KeyEvent.KEYCODE_ENTER
                    ) {
                        if (event == null || !event.isShiftPressed()) {
                            var titleSearch: String =
                                rootView.editTextSearch.text.toString()
                            listSearch.clear()
                            for (i in 0 until list.size - 1) {
                                if (list[i].title.toLowerCase().contains(titleSearch)) {
                                    listSearch.add(
                                        CNC(
                                            list[i].index,
                                            list[i].title,
                                            list[i].url
                                        )
                                    )
                                }
                            }
                            displaySearch(listSearch)
                            return true
                        }
                    }
                    return false
                }
            })
        }
        rootView.imageViewClose.setOnClickListener {
            rootView.relativeLayoutSearch.visibility = View.GONE
            rootView.imageViewSearch.visibility = View.VISIBLE
            rootView.recyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            rootView.recyclerView.addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
            rootView.recyclerView.adapter = adapter
        }
        rootView.imageViewShare.setOnClickListener {
            var intentShare: Intent = Intent(Intent.ACTION_SEND)
            intentShare.type = "text/plain"
            intentShare.putExtra(Intent.EXTRA_SUBJECT, "My app")
            var url: String =
                "https://play.google.com/store/apps/details?id=" + configAds.idShare
            intentShare.putExtra(Intent.EXTRA_TEXT, url)
            startActivity(Intent.createChooser(intentShare, "Share with"))
        }
        rootView.imageViewListTym.setOnClickListener {
            var intent: Intent = Intent(context, YeuThichAcivity::class.java)
            intent.putParcelableArrayListExtra("listGoTym", list)
            startActivity(intent)
        }
        return rootView
    }

    fun setData() {
        for (i in 0 until myListItem.size) {
            list.add(CNC(i, myListItem[i], getURL(i)))
        }
    }

    fun getURL(index: Int): String {
        return "file:///android_asset/$index.html"
    }

    fun displaySearch(listSearch: ArrayList<CNC>) {
        val adapterSearch: CNCAdapter = CNCAdapter(context, listSearch)
        rootView.recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rootView.recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        rootView.recyclerView.adapter = adapterSearch
    }
}