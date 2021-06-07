package com.example.nextsolution_ex1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nextsolution_ex1.database.CNCDataBase
import com.example.nextsolution_ex1.database.CNCObject
import kotlinx.android.synthetic.main.activity_yeu_thich_acivity.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class YeuThichAcivity : AppCompatActivity(), CoroutineScope, YeuThichAdapter.OnItemClickLister {
    private var cncDB: CNCDataBase? = null
    private var listTym: List<CNCObject>? = null
    //lateinit var list: ArrayList<CNC>
    private var adapter: YeuThichAdapter? = null
    private val sIndex: String = "index"

    private lateinit var mJob: Job
    override val coroutineContext: CoroutineContext
        get() = mJob + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yeu_thich_acivity)
        mJob = Job()
        cncDB = CNCDataBase.getDatabase(this)
        launch {
            var cnc: List<CNCObject>? = cncDB?.cncDao()?.getListCNC()
            listTym = cnc
        }
        adapter = YeuThichAdapter(YeuThichAcivity@ this, cncDB!!)
        recyclerViewTym.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewTym.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        recyclerViewTym.adapter = adapter
    }

    override fun onItemClick(position: Int) {
        var intent: Intent = Intent(this, DetailCNC::class.java)
        intent.putExtra(sIndex, listTym?.get(position)?.index)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        launch {
            var cnc: List<CNCObject>? = cncDB?.cncDao()?.getListCNC()
            listTym = cnc
        }
        getAllCNC()
    }

    fun getAllCNC() {
        launch {
            val cnc: List<CNCObject>? = cncDB?.cncDao()?.getListCNC()
            if (cnc != null) {
                adapter?.setCNC(cnc, this@YeuThichAcivity)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mJob.cancel()
    }
}