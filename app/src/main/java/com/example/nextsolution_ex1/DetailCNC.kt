package com.example.nextsolution_ex1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Parcelable
import android.view.MotionEvent
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebViewClient
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_detail_c_n_c.*

class DetailCNC : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_c_n_c)

        val intent = intent
        var index: Int = intent.getIntExtra("index", 0)
        val url: String? = intent.getStringExtra("url")
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
        /*var y: Int = 0
        webView.setOnTouchListener {
            v, event ->
            var action = event.actionMasked
            when(action){
                MotionEvent.ACTION_DOWN -> {
                    y =event.getY().toInt()
                }
                MotionEvent.ACTION_MOVE -> {
                    if (y>event.getY()){
                        zoomControls.show()
                    }else if(y<event.getY()){
                        zoomControls.hide()
                    }
                    y = event.getY().toInt()
                }
                MotionEvent.ACTION_UP -> {
                    mHandler.postDelayed(hideZoom,5000)
                }
            }
            false
        }*/
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
    }

    fun loadWebView(url: String) {
        webView.webViewClient = WebViewClient()
        url?.let { webView.loadUrl(it) }
        var webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true
    }
}