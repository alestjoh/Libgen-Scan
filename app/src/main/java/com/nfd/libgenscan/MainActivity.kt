package com.nfd.libgenscan

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import me.dm7.barcodescanner.zbar.Result
import me.dm7.barcodescanner.zbar.ZBarScannerView

/* Scanning UI and main menu. This should always be the first thing the user sees on launch.
 * TODO: add history menu, autoscan option, and restore support for pre-Marshmallow if possible
 */

//AppCompatActivity was actually causing crashes?
class MainActivity : Activity(), ZBarScannerView.ResultHandler {
    private var mScannerView: ZBarScannerView? = null

    //TODO: annotate s.t. < 23 are accepted
    public override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        mScannerView = ZBarScannerView(this)    // Programmatically initialize the scanner view
        setContentView(mScannerView)                // Set the scanner view as the content view

        // Request permission. This does it asynchronously so we have to wait for onRequestPermissionResult before
        // trying to open the camera.
        if (!haveCameraPermission())
            requestPermissions(arrayOf(Manifest.permission.CAMERA), PERMISSION_REQUEST_CAMERA)
    }

    private fun haveCameraPermission(): Boolean =
        checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        // This is because the dialog was cancelled when we recreated the activity.
        if (permissions.isEmpty() || grantResults.isEmpty())
            return

        when (requestCode) {
            PERMISSION_REQUEST_CAMERA -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mScannerView!!.startCamera()
                } else {
                    finish()
                }
            }
        }
    }


    public override fun onResume() {
        super.onResume()
        mScannerView!!.setResultHandler(this) // Register ourselves as a handler for scan results.
        mScannerView!!.startCamera()          // Start camera on resume
    }

    public override fun onPause() {
        super.onPause()
        mScannerView!!.stopCamera()           // Stop camera on pause
    }

    public override fun onDestroy() {
        super.onDestroy()
        mScannerView!!.stopCamera()
        mScannerView = null
    }

    //intended to fire URL opener intents from searches
    internal fun fire(i: Intent) {
        startActivity(i)
    }

    override fun handleResult(rawResult: Result) {

        try {

            val b = BookRef(
                    rawResult.contents,
                    rawResult.barcodeFormat,
                    this)
            BookRef.addToList(b)

            //remove; if set to auto-open, immediately call openers before throwing ref out
            // gotta figure out settings activities first
            b.searchBook()

            mScannerView!!.resumeCameraPreview(this)

        } catch (e: IllegalArgumentException) {
            Toast.makeText(applicationContext, "Barcode format not supported; try another book.",
                    Toast.LENGTH_SHORT).show()
            mScannerView!!.resumeCameraPreview(this)
        }
    }

    companion object {
        const val PERMISSION_REQUEST_CAMERA = 1
    }
}
