package com.nfd.libgenscan

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.nfd.libgenscan.openLibrary.BookResponse
import com.nfd.libgenscan.openLibrary.OpenLibraryService
import me.dm7.barcodescanner.zbar.Result
import me.dm7.barcodescanner.zbar.ZBarScannerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/*
 * Scanning UI and main menu. This should always be the first thing the user sees on launch.
 * TODO: add history menu, autoscan option, and restore support for pre-Marshmallow if possible
 */

//AppCompatActivity was actually causing crashes?
class MainActivity : Activity(), ZBarScannerView.ResultHandler {
    private val mScannerView: ZBarScannerView by lazy { ZBarScannerView(this) }

    //TODO: annotate s.t. < 23 are accepted
    public override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        setContentView(mScannerView)

        // Request permission. This does it asynchronously so we have to wait for onRequestPermissionResult before
        // trying to open the camera.
        if (!haveCameraPermission()) {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), PERMISSION_REQUEST_CAMERA)
        } else {
            prepareCamera()
        }
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
                    prepareCamera()
                } else {
                    finish()
                }
            }
        }
    }

    private fun prepareCamera() {
        mScannerView.startCamera()
        mScannerView.setResultHandler(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        mScannerView.stopCamera()
    }

    override fun handleResult(rawResult: Result) {

        try {
            val bookRef = BookRef(rawResult.contents, rawResult.barcodeFormat)
            BookRef.addToList(bookRef)

            //remove; if set to auto-open, immediately call openers before throwing ref out
            // gotta figure out settings activities first
//            val intent = Intent(Intent.ACTION_VIEW, b.getIsbnUri())
//            startActivity(intent)

            findBookInOpenLibrary(bookRef)

            mScannerView.resumeCameraPreview(this)

        } catch (e: Exception) {
            Toast.makeText(applicationContext, "Barcode format not supported; try another book.",
                    Toast.LENGTH_SHORT).show()
            mScannerView.resumeCameraPreview(this)
        }
    }

    private fun findBookInOpenLibrary(bookRef: BookRef) {
        OpenLibraryService.getInstance().getBook(bookRef.id).enqueue(
                object : Callback<BookResponse> {
                    override fun onFailure(call: Call<BookResponse>, t: Throwable) {
                        Log.e(TAG, "Error retrieving book from Open Library:")
                        Log.e(TAG, t.message)
                    }

                    override fun onResponse(call: Call<BookResponse>, response: Response<BookResponse>) {
                        Log.i(TAG, "Book successfully found in Open Library:")
                        Log.i(TAG, response.body().toString())
                    }
                }
        )
    }

    companion object {
        const val PERMISSION_REQUEST_CAMERA = 1
        val TAG: String = MainActivity::class.java.simpleName
    }
}
