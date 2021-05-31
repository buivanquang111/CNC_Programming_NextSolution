package com.example.nextsolution_ex1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_yeu_thich_acivity.*
import org.json.JSONObject
import java.lang.reflect.Type

class YeuThichAcivity : AppCompatActivity(),CNCAdapter.OnItemClickLister  {
    lateinit var arrayListTym: ArrayList<CNC>
    lateinit var list: ArrayList<CNC>
    lateinit var adapter: CNCAdapter
    //private val adapter: CNCAdapter = CNCAdapter(arrayListTym, this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yeu_thich_acivity)

        val intent1=intent
        list = intent1.getParcelableArrayListExtra<CNC>("listGoTym") as ArrayList<CNC>
        /*loadData()
        adapter = CNCAdapter(arrayListTym,this)
        recyclerViewTym.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewTym.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerViewTym.adapter = adapter*/

    }
    /*fun loadData() {
        var sharePreferences: SharedPreferences= getSharedPreferences("listTym", Context.MODE_PRIVATE)
        val gson: Gson = Gson()
        val json: String? = sharePreferences.getString("list",null)
        val type: Type = object : TypeToken<ArrayList<CNC>>(){}.type
        arrayListTym= gson.fromJson(json,type)

    }
*/

    override fun onItemClick(position: Int) {
        var intent: Intent = Intent(this, DetailCNC::class.java)
        intent.putExtra("index", arrayListTym[position].index)
        intent.putExtra("title",arrayListTym[position].title)
        intent.putExtra("url", arrayListTym[position].url)
        intent.putParcelableArrayListExtra("list", list)
        startActivity(intent)
    }
}
