package com.example.nextsolution_ex1

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.ads.*
import com.google.android.gms.ads.initialization.InitializationStatus
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import kotlinx.android.synthetic.main.activity_detail_c_n_c.*

class DetailCNC : AppCompatActivity() {
    private val configAds: ConfigAds = ConfigAds()
    private var mInterstitialAd: InterstitialAd? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_c_n_c)
        //quang cao
        MobileAds.initialize(this) {}
        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder()
                .setTestDeviceIds(listOf(""))
                .build()
        )
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
        MobileAds.initialize(this, object : OnInitializationCompleteListener {
            override fun onInitializationComplete(p0: InitializationStatus) {
                createInterstitialAd()
            }
        })
        val index = intent.getIntExtra("index", 0)
        val title = intent.getStringExtra("title")
        val url = intent.getStringExtra("url")
        val list = intent.getParcelableArrayListExtra<CNC>("list")
        val fragmentB =
            supportFragmentManager.findFragmentById(R.id.fragmentB) as FragmentDetailCNC?
        if (title != null) {
            if (url != null) {
                if (list != null) {
                    fragmentB?.displayDetails(index, title, url, list)
                }
            }
        }
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
        super.onDestroy()
    }

    override fun onBackPressed() {
        // super.onBackPressed()
        var alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Advertisement")
        alertDialogBuilder.setMessage("Please watch the ad to continue!!")
        alertDialogBuilder.setPositiveButton(
            "YES",
            DialogInterface.OnClickListener { dialog, which ->
                mInterstitialAd?.show(this)
                super.onBackPressed()
            })

        var alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    fun createInterstitialAd() {
        var adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            this,
            configAds.fullDisplay,
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