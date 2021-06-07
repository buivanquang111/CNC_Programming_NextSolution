package com.global.tutorial

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.global.tutorial.R
import com.global.tutorial.R.string
import com.google.android.gms.ads.*
import com.google.android.gms.ads.initialization.InitializationStatus
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import kotlinx.android.synthetic.main.activity_detail_c_n_c.*

class DetailCNC : AppCompatActivity() {
    private var sIndex: String = "index"
    private var mInterstitialAd: InterstitialAd? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_c_n_c)
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
        val index = intent.getIntExtra(sIndex, 0)

        val fragmentB =
            supportFragmentManager.findFragmentById(R.id.fragmentB) as FragmentDetailCNC?
        fragmentB?.displayDetails(index)
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
        var alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Advertisement")
        alertDialogBuilder.setMessage("Please watch the ad to continue!!")
        alertDialogBuilder.setPositiveButton(
            "Yes",
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
            getString(string.fullDisplay),
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