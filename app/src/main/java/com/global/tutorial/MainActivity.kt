package com.global.tutorial

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.global.tutorial.R

class MainActivity : AppCompatActivity(), MyCommunicator {
    private val sIndex: String = "index"
    private var mIsDualPane = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragmentBView = findViewById<View>(R.id.fragmentB)
        mIsDualPane = fragmentBView?.visibility == View.VISIBLE
    }

    override fun onBackPressed() {
        var alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Exit")
        alertDialogBuilder.setMessage("Are you sure you want to quit application?")
        alertDialogBuilder.setPositiveButton(
            "Yes",
            DialogInterface.OnClickListener { dialog, which ->
                moveTaskToBack(true)
                System.exit(0)

            })
        alertDialogBuilder.setNegativeButton(
            "No",
            DialogInterface.OnClickListener { dialog, which ->
                dialog.cancel()
            })
        var alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    override fun displayDetails(index: Int) {
        if (mIsDualPane) {
            //tablet
            val fragmentB =
                supportFragmentManager.findFragmentById(R.id.fragmentB) as FragmentDetailCNC?
            fragmentB?.displayDetails(index)
        } else {
            //mobile
            val intent: Intent = Intent(this, DetailCNC::class.java)
            intent.putExtra(sIndex, index)
            startActivity(intent)
        }
    }
}