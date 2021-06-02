package com.example.nextsolution_ex1

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.TelephonyManager
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.webkit.WebSettings
import android.webkit.WebViewClient
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity(),MyCommunicator {
    private var mIsDualPane = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentBView = findViewById<View>(R.id.fragmentB)
        mIsDualPane = fragmentBView?.visibility == View.VISIBLE

    }

    override fun onBackPressed() {
        //super.onBackPressed()
        var alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Exit")
        alertDialogBuilder.setMessage("Are you sure you want to quit application ?")
        alertDialogBuilder.setPositiveButton(
            "YES",
            DialogInterface.OnClickListener { dialog, which ->
                moveTaskToBack(true)
                System.exit(0)

            })
        alertDialogBuilder.setNegativeButton(
            "NO",
            DialogInterface.OnClickListener { dialog, which ->
                dialog.cancel()

            })

        var alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    override fun displayDetails(index: Int, title: String, url: String, list: ArrayList<CNC>) {
        if (mIsDualPane){
            //tablet
            val fragmentB = supportFragmentManager.findFragmentById(R.id.fragmentB) as FragmentB?
            fragmentB?.displayDetails(index,title,url,list)
        }else{
            //mobile
            val intent:Intent = Intent(this,DetailCNC::class.java)
            intent.putExtra("index",index)
            intent.putExtra("title",title)
            intent.putExtra("url",url)
            intent.putParcelableArrayListExtra("list",list)
            startActivity(intent)
        }
    }


}