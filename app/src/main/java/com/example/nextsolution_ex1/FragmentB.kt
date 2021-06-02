package com.example.nextsolution_ex1

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.nextsolution_ex1.database.CNCDataBase
import com.example.nextsolution_ex1.database.CNCObject
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import kotlinx.android.synthetic.main.activity_detail_c_n_c.*
import kotlinx.android.synthetic.main.fragment_b.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class FragmentB :Fragment(), CoroutineScope {
    private var cncDB: CNCDataBase? = null
    var index: Int = 0
    lateinit var list: ArrayList<CNC>
    lateinit var title:String
    lateinit var url:String
    private lateinit var mJob: Job
    override val coroutineContext: CoroutineContext
        get() = mJob + Dispatchers.Main

    fun displayDetails(index: Int,title: String, url: String,list: ArrayList<CNC>){

        this.index = index
        this.list = list
        this.title = title
        this.url=url

        imageViewTymDo.visibility = View.GONE
        imageViewTymTrang.visibility = View.VISIBLE
        launch {
            val cnc: List<CNCObject>? = cncDB?.cncDao()?.getListCNC()
            for (i in 0..cnc!!.size - 1) {
                if (cnc[i].index == index) {
                    imageViewTymDo.visibility = View.VISIBLE
                    imageViewTymTrang.visibility = View.GONE
                }
            }
        }
        loadWebView(url)
        textViewIndexDetail.text = "${index + 1}/${list.size}"


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


    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
    //sqlite
        mJob = Job()
        cncDB = context?.let { CNCDataBase.getDatabase(it) }

        return inflater.inflate(R.layout.fragment_b, container, false)
    }
    fun loadWebView(url: String) {
        webView.webViewClient = WebViewClient()
        url?.let { webView.loadUrl(it) }
        var webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true
    }

    override fun onResume() {
        super.onResume()

        imageViewNext.setOnClickListener {
            this.index += 1
            imageViewTymTrang.visibility = View.VISIBLE
            imageViewTymDo.visibility = View.GONE
            launch {
                val cnc: List<CNCObject>? = cncDB?.cncDao()?.getListCNC()
                for (i in 0..cnc!!.size - 1) {
                    if (cnc[i].index == index) {
                        imageViewTymDo.visibility = View.VISIBLE
                        imageViewTymTrang.visibility = View.GONE
                    }
                }
            }
            if (index <= list!!.size - 1) {
                val urlNext: String = list[index].url
                textViewIndexDetail.text = "${index + 1}/${list.size}"
                loadWebView(urlNext)
            } else {
                this.index = list.size - 1
            }
        }
        imageViewBack.setOnClickListener {
            this.index -= 1
            imageViewTymTrang.visibility = View.VISIBLE
            imageViewTymDo.visibility = View.GONE
            launch {
                val cnc: List<CNCObject>? = cncDB?.cncDao()?.getListCNC()
                for (i in 0..cnc!!.size - 1) {
                    if (cnc[i].index == index) {
                        imageViewTymDo.visibility = View.VISIBLE
                        imageViewTymTrang.visibility = View.GONE
                    }
                }
            }
            if (index >= 0) {
                val urlBack: String = list?.get(index)!!.url
                textViewIndexDetail.text = "${index + 1}/${list.size}"
                loadWebView(urlBack)
            } else {
                this.index = 0
            }
        }
        imageViewTymTrang.setOnClickListener {
            imageViewTymTrang.visibility = View.GONE
            imageViewTymDo.visibility = View.VISIBLE
            var indexT = this.index
            var titleT = list[indexT].title
            var urlT = this.url
            launch {
                cncDB?.cncDao()?.insertCNC(CNCObject(index = indexT, title = titleT, url = urlT))
                //finish()
            }
        }
    }
    override fun onDestroy() {
        mJob.cancel()
        super.onDestroy()
    }

}