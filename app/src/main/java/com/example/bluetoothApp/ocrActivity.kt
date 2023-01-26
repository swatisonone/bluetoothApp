package com.example.bluetoothApp

import android.Manifest
import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.SparseArray
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import com.example.bluetoothapp.R
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.text.TextBlock
import com.google.android.gms.vision.text.TextRecognizer


class ocrActivity : AppCompatActivity() {
    private lateinit var textRecognizer: TextRecognizer
    private val MY_PERMISSIONS_REQUEST_CAMERA: Int = 101
    lateinit var mCameraSource :CameraSource
    private val tag :String? = "MainActivity"
    lateinit var clipboard: ClipboardManager
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ocr)
        clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        textRecognizer = TextRecognizer.Builder(this).build()
        if (!textRecognizer.isOperational){
            Toast.makeText(this, "dependency loading....", Toast.LENGTH_SHORT).show()
        return
        }
         mCameraSource = CameraSource.Builder(applicationContext, textRecognizer).setFacing(CameraSource.CAMERA_FACING_BACK).setRequestedPreviewSize(1200,1024).setAutoFocusEnabled(true).setRequestedFps(
             2F).build()
        var surface_camera_preview = findViewById<SurfaceView>(R.id.surfaceView3)
        surface_camera_preview.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(p0: SurfaceHolder) {
                try {
                    if (isCameraPermissionGranted()) {

                        if (ActivityCompat.checkSelfPermission(
                                this@ocrActivity,
                                Manifest.permission.CAMERA
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            ActivityCompat.requestPermissions(
                                this@ocrActivity,
                                arrayOf(android.Manifest.permission.CAMERA),
                                MY_PERMISSIONS_REQUEST_CAMERA
                            )
                        }
                        mCameraSource.start(surface_camera_preview.holder)
                    } else {
                        requestForPermission()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@ocrActivity, "${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {

            }

            override fun surfaceDestroyed(p0: SurfaceHolder) {
                mCameraSource.stop()
            }
    })
        textRecognizer.setProcessor(object  : Detector.Processor<TextBlock>{

            override fun receiveDetections(p0: Detector.Detections<TextBlock>) {
               val items:SparseArray<TextBlock> = p0.detectedItems
                if (items.size()<=0){
                    return
                }
                var result = findViewById<TextView>(R.id.result)
                result.post{
                    val stringBuilder = StringBuilder()
                    for(i in 0 until items.size()){
                        var textBlock :TextBlock = items.valueAt(i)
                        if (textBlock != null && textBlock.value != null){
                            stringBuilder.append(textBlock.value + "  ")
                        }
                    }
                    result.text = stringBuilder.toString()

//                    var stringResult :String
//                    var StringText = stringBuilder.toString()
//                    var handler : Handler = Handler(Looper.getMainLooper())
//                    handler.post(object : Runnable {
//                        override fun run() {
//                            stringResult = StringText
//                            resultObtain()
//                        }
//
//                    })
                }

            }

            override fun release() {
                TODO("Not yet implemented")
            }

        })
    }

//    private fun resultObtain() {
//        var result = findViewById<TextView>(R.id.result)
//        result.setText(stringResult)
//
//    }

    private fun requestForPermission() {

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(
                this@ocrActivity,
                Manifest.permission.CAMERA
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this@ocrActivity,
                    Manifest.permission.CAMERA
                )
            ) {
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    this@ocrActivity,
                    arrayOf(
                        Manifest.permission.CAMERA
                    ),
                    MY_PERMISSIONS_REQUEST_CAMERA
                )
            }
        } else {
            Toast.makeText(this, "permissions already granted", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isCameraPermissionGranted(): Boolean {

        return ContextCompat.checkSelfPermission(
            this@ocrActivity,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

    }

    //method for toast
    fun toast(text: String) {

        Toast.makeText(this@ocrActivity, text, Toast.LENGTH_SHORT).show()

    }

    //for handling permissions
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
             MY_PERMISSIONS_REQUEST_CAMERA-> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    requestForPermission()
                }
                return
            }
            else -> {
                // Ignore all other requests.
            }
        }
    }

    fun copyText(view: View) {
        var result = findViewById<TextView>(R.id.result)
        var myClip = ClipData.newPlainText("text", result.text);
        clipboard?.setPrimaryClip(myClip);

        Toast.makeText(this, "Text Copied", Toast.LENGTH_SHORT).show();
    }

}

