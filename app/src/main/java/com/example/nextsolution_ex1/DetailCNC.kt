package com.example.nextsolution_ex1

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Parcelable
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebViewClient
import android.widget.Toast
import com.example.nextsolution_ex1.database.CNCDataBase
import com.example.nextsolution_ex1.database.CNCObject
import com.google.android.gms.ads.*
import com.google.android.gms.ads.initialization.InitializationStatus
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

import kotlinx.android.synthetic.main.activity_detail_c_n_c.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.json.JSONObject
import kotlin.coroutines.CoroutineContext

class DetailCNC : AppCompatActivity(), CoroutineScope {
    private var mInterstitialAd: InterstitialAd? = null

    private var cncDB: CNCDataBase? = null
    var arrayList: ArrayList<CNCObject> = arrayListOf()
    lateinit var title: String
    lateinit var url: String
    var index: Int = 0

    private lateinit var mJob: Job
    override val coroutineContext: CoroutineContext
        get() = mJob + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_c_n_c)

        //sqlite
        mJob = Job()
        cncDB = CNCDataBase.getDatabase(this)
        //quang cao
        MobileAds.initialize(this) {}
        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder()
                .setTestDeviceIds(listOf("ABCDEF012345"))
                .build()
        )
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        MobileAds.initialize(this, object : OnInitializationCompleteListener {
            override fun onInitializationComplete(p0: InitializationStatus) {
                createInterstitialAd()
            }

        })

        val intent = intent
        title = intent.getStringExtra("title").toString()
        index = intent.getIntExtra("index", 0)
        url = intent.getStringExtra("url").toString()
        val list = intent.getParcelableArrayListExtra<CNC>("list")

        //check tym

        launch {
            val cnc: List<CNCObject>? = cncDB?.cncDao()?.getListCNC()
            for (i in 0..cnc!!.size - 1) {
                if (cnc[i].index == index) {
                    imageViewTymDo.visibility = View.VISIBLE
                    imageViewTymTrang.visibility = View.GONE
                }
            }
        }


        url?.let { loadWebView(it) }
        textViewIndexDetail.text = "${index + 1}/${list?.size}"

        imageViewNext.setOnClickListener {
            index += 1
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
                index = list.size - 1
            }
        }
        imageViewBack.setOnClickListener {
            index -= 1
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
            imageViewTymTrang.visibility = View.GONE
            imageViewTymDo.visibility = View.VISIBLE
            launch {
                cncDB?.cncDao()?.insertCNC(CNCObject(index = index, title = title, url = url))
                //finish()
            }
        }

    }

    fun loadWebView(url: String) {
        webView.webViewClient = WebViewClient()
        url?.let { webView.loadUrl(it) }
        var webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true
    }


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
        mJob.cancel()
        super.onDestroy()
    }

    override fun onBackPressed() {
        super.onBackPressed()

        mInterstitialAd?.show(this)

    }

    fun createInterstitialAd() {
        var adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            this,
            "ca-app-pub-3940256099942544/1033173712",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mInterstitialAd = null
                    mInterstitialAd?.fullScreenContentCallback =
                        object : FullScreenContentCallback() {
                            override fun onAdDismissedFullScreenContent() {

                            }

                            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {

                            }

                            override fun onAdShowedFullScreenContent() {

                                mInterstitialAd = null;
                            }
                        }
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd

                }
            })
    }

}