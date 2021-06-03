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
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_list_cnc, container, false)
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
        list.add(CNC(0, "[1] What is CNC?", "file:///android_asset/0.html"))
        list.add(CNC(1, "[2] How to Make CNC Program?", "file:///android_asset/1.html"))
        list.add(CNC(2, "[3] Make Your First CNC Program", "file:///android_asset/2.html"))
        list.add(CNC(3, "[4] G Code Introduction", "file:///android_asset/3.html"))
        list.add(
            CNC(
                4,
                "[5] Modal G-Code- Learn G Code Programming",
                "file:///android_asset/4.html"
            )
        )
        list.add(
            CNC(
                5,
                "[6] One Shot G-Codes- Learn G Code Programming",
                "file:///android_asset/5.html"
            )
        )
        list.add(
            CNC(
                6,
                "[7] Mill/Lathe G/M-Codes- Learn G Code Programming",
                "file:///android_asset/6.html"
            )
        )
        list.add(
            CNC(
                7,
                "[8] Popular CNC G-Code Lists- Learn G Code Programming",
                "file:///android_asset/7.html"
            )
        )
        list.add(
            CNC(
                8,
                "[9] Din 66025 Programming- Learn G Code Programming",
                "file:///android_asset/8.html"
            )
        )
        list.add(CNC(9, "[10] M Code Introduction", "file:///android_asset/9.html"))
        list.add(CNC(10, "[11] CNC Program Block", "file:///android_asset/10.html"))
        list.add(CNC(11, "[12] Why to use Canned Cycles?", "file:///android_asset/11.html"))
        list.add(CNC(12, "[13] Subprograms Basics", "file:///android_asset/12.html"))
        list.add(CNC(13, "[14] CNC Machine Modes", "file:///android_asset/13.html"))
        list.add(CNC(14, "[15] Speed and Feed Override", "file:///android_asset/14.html"))
        list.add(CNC(15, "[16] Block Delete", "file:///android_asset/15.html"))
        list.add(CNC(16, "[17] Dry Run", "file:///android_asset/16.html"))
        list.add(CNC(17, "[18] Reference Point Return", "file:///android_asset/17.html"))
        list.add(CNC(18, "[19] CNC Operating Modes", "file:///android_asset/18.html"))
        list.add(CNC(19, "[20] CNC Reference Point Return", "file:///android_asset/19.html"))
        list.add(CNC(20, "[21] CNC Speed and Feed Override", "file:///android_asset/20.html"))
        list.add(CNC(21, "[22] CNC Optional Block Skip", "file:///android_asset/21.html"))
        list.add(CNC(22, "[23] CNC Dry Run", "file:///android_asset/22.html"))
        list.add(CNC(23, "[24] How to Set Up CNC Milling?", "file:///android_asset/23.html"))
        list.add(CNC(24, "[25] Tool Presetter", "file:///android_asset/24.html"))
        list.add(CNC(25, "[26] Who is CNC Machine Setter?", "file:///android_asset/25.html"))
        list.add(CNC(26, "[27] Tool Selection (CNC Lathe)", "file:///android_asset/26.html"))
        list.add(CNC(27, "[28] Main Parts", "file:///android_asset/27.html"))
        list.add(CNC(28, "[29] Who is CNC Machine Setter?", "file:///android_asset/28.html"))
        list.add(CNC(29, "[30] Chuck Jaws", "file:///android_asset/29.html"))
        list.add(CNC(30, "[31] Tool Turret", "file:///android_asset/30.html"))
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