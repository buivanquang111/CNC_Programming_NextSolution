package com.example.nextsolution_ex1

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Parcelable
import android.view.MotionEvent
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebViewClient
import android.widget.Toast
import com.example.nextsolution_ex1.database.CNCDataBase
import com.example.nextsolution_ex1.database.CNCObject
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_detail_c_n_c.*
import org.json.JSONObject

class DetailCNC : AppCompatActivity() {
    var arrayList: ArrayList<CNCObject> = arrayListOf()
    lateinit var title: String
    lateinit var url: String
    var index: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_c_n_c)

        MobileAds.initialize(this){}
        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder()
                .setTestDeviceIds(listOf("ABCDEF012345"))
                .build()
        )
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        val intent = intent
        title = intent.getStringExtra("title").toString()
         index = intent.getIntExtra("index", 0)
         url = intent.getStringExtra("url").toString()
        val list = intent.getParcelableArrayListExtra<CNC>("list")
        url?.let { loadWebView(it) }
        textViewIndexDetail.text = "${index + 1}/${list?.size}"
        
        imageViewNext.setOnClickListener {
            index += 1
            if (index <= list!!.size - 1) {
                val urlNext: String = list[index].url
                textViewIndexDetail.text = "${index + 1}/${list.size}"
                loadWebView(urlNext)
            } else {
                index = list.size - 1
            }
        }
        imageViewBack.setOnClickListener {
            index -= 1
            if (index >= 0) {
                val urlBack: String = list?.get(index)!!.url
                textViewIndexDetail.text = "${index + 1}/${list.size}"
                loadWebView(urlBack)
            } else {
                index = 0
            }
        }
        var mHandler: Handler = Handler()
        var hideZoom = Runnable {
            zoomControls.hide()
        }
        zoomControls.hide()

        webView.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                zoomControls.show()
                return false
            }

        })
        zoomControls.setOnZoomOutClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                var x: Float = webView.scaleX
                var y: Float = webView.scaleY
                if (x == 1f && y == 1f) {
                    webView.scaleX = x
                    webView.scaleY = y
                } else {
                    webView.scaleX = x - 1
                    webView.scaleY = y - 1
                    zoomControls.hide()
                }
            }

        })
        zoomControls.setOnZoomInClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                var x: Float = webView.scaleX
                var y: Float = webView.scaleY
                webView.scaleX = x + 1
                webView.scaleY = y + 1
                zoomControls.hide()
            }

        })

        imageViewTymTrang.setOnClickListener {
            imageViewTymTrang.visibility= View.GONE
            imageViewTymDo.visibility = View.VISIBLE

            var cncObject: CNCObject = CNCObject(index,title,url)



           /* arrayList.add(CNC(index,title,url))
            saveData()*/

        }

    }

    fun loadWebView(url: String) {
        webView.webViewClient = WebViewClient()
        url?.let { webView.loadUrl(it) }
        var webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true
    }

   /* fun saveData(){
        var sharePreferences: SharedPreferences = getSharedPreferences("listTym", Context.MODE_PRIVATE)
        var editor: SharedPreferences.Editor = sharePreferences.edit()
        var gson: Gson = Gson()
        var json: String = gson.toJson(arrayList)
        editor.putString("list",json)
        editor.apply()
    }*/




    override fun onPause() {
        adView.pause()
        super.onPause()
    }

    override fun onResume() {
        adView.resume()
        super.onResume()
    }

    override fun onDestroy() {
        adView.destroy()
        super.onDestroy()
    }
}