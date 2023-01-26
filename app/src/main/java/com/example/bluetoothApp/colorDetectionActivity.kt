package com.example.bluetoothApp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.SurfaceView
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.bluetoothapp.R
import org.opencv.android.*
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame
import org.opencv.core.*
import org.opencv.imgproc.Imgproc
import java.io.IOException
import java.util.*


class colorDetectionActivity : AppCompatActivity(), CameraBridgeViewBase.CvCameraViewListener2 {

    var h_min: SeekBar? = null
    var h_max: SeekBar? = null
    var v_min: SeekBar? = null
    var v_max: SeekBar? = null
    var s_min: SeekBar? = null
    var s_max: SeekBar? = null
    var set_vis = 0
    var A: Mat? = null
    var B: Mat? = null
    var C: Mat? = null
    var scalarLow :Scalar? = null
    var scalarHigh :Scalar? = null
    var pre_command =""
    private lateinit var javaCameraView: CameraBridgeViewBase

    private val baseLoaderCallback: BaseLoaderCallback = object : BaseLoaderCallback(this) {
        override fun onManagerConnected(status: Int) {
            when (status) {
                SUCCESS -> {
                    javaCameraView.enableView()
                    Log.i("camera", "OpenCV loaded successfully")
                }
                else -> {
                    super.onManagerConnected(status)
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val b = Intent(applicationContext, MainActivity::class.java)
        startActivity(b)
    }

    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
        setContentView(R.layout.activity_color_detection)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 101)
            Toast.makeText(this, "give camera permission", Toast.LENGTH_SHORT).show()
        }

        var red = findViewById<ImageView>(R.id.red)
        var blue = findViewById<ImageView>(R.id.blue)
        var green = findViewById<ImageView>(R.id.green)

        var com = findViewById<TextView>(R.id.com)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        javaCameraView= findViewById(R.id.surfaceView) as JavaCameraView
        javaCameraView.setCameraIndex(0)

        h_min = findViewById(R.id.h_min)
        h_max = findViewById(R.id.h_max)
        v_min = findViewById(R.id.v_min)
        v_max = findViewById(R.id.v_max)
        s_min = findViewById(R.id.s_min)
        s_max = findViewById(R.id.s_max)

            h_max?.max = 255
            h_min?.max = 255
            v_max?.max = 255
            v_min?.max = 255
            s_max?.max = 255
            s_min?.max = 255
        com.setText("0")

        red.setOnClickListener{

            h_min!!.progress = 120
            h_max!!.progress = 150
            s_min!!.progress = 97
            s_max!!.progress = 255
            v_min!!.progress = 73
            v_max!!.progress =255
            red.visibility = View.INVISIBLE
            green.visibility = View.VISIBLE
            blue.visibility = View.VISIBLE
            com.setText("1")
        }
        blue.setOnClickListener{
            h_min!!.progress = 0
            h_max!!.progress = 73
            s_min!!.progress = 127
            s_max!!.progress = 200
            v_min!!.progress = 100
            v_max!!.progress = 255
            red.visibility = View.VISIBLE
            green.visibility = View.VISIBLE
            blue.visibility = View.INVISIBLE
            com.setText("1")
        }
            green.setOnClickListener{
            h_min!!.progress = 25
            h_max!!.progress = 102
            s_min!!.progress = 52
            s_max!!.progress = 255
            v_min!!.progress = 72
            v_max!!.progress = 255
            red.visibility = View.VISIBLE
            green.visibility = View.INVISIBLE
            blue.visibility = View.VISIBLE
                com.setText("1")
        }


        javaCameraView.visibility = SurfaceView.VISIBLE
//        javaCameraView.setMaxFrameSize(1024,768);
        javaCameraView.clearFocus();
        javaCameraView.setCvCameraViewListener(this)

//        baseLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS)
        Toast.makeText(this, "camera is enable", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        if (OpenCVLoader.initDebug()) {
            baseLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS)
        } else {
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_4_0, this, baseLoaderCallback)
        }
    }
    override fun onCameraViewStarted(width: Int, height: Int) {
        A = Mat(width, height, CvType.CV_8UC4)
        B = Mat(width, height, CvType.CV_8UC1)
        C = Mat(width, height, CvType.CV_8UC4)
//        Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show()
        Log.d("Hello world", A.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        javaCameraView.disableView()
    }
    override fun onPause() {
        super.onPause()
        javaCameraView.disableView()
    }


    override fun onCameraViewStopped(){
        A!!.release()
        B!!.release()
        C!!.release()
    }

    override fun onCameraFrame(inputFrame: CvCameraViewFrame?): Mat? {

        var a = h_min!!.progress
        var b = s_min!!.progress
        var c = v_min!!.progress
        var d = h_max!!.progress
        var e = s_max!!.progress
        var f = v_max!!.progress
        A = inputFrame!!.rgba()
        var x1:Int
        var x2:Int
        var y1:Int
        x1 = ((A?.width())?.div(2) ?: Int) as Int
        y1 = ((A?.height())?.div(2) ?: Int) as Int
        Imgproc.line(
            A, Point(x1.toDouble(),0.0), Point(
                x1.toDouble(),
                y1.toDouble()*2
            ), Scalar(255.0, 0.0, 0.0),5
        )

        Log.d("camera", "Camera started")
        Imgproc.cvtColor(inputFrame!!.rgba(),A,Imgproc.COLOR_BGR2HSV)
        Core.inRange(A,Scalar(a.toDouble(), c.toDouble(), b.toDouble()), Scalar(d.toDouble(), e.toDouble(), f.toDouble()),B)
//        A = inputFrame?.rgba()
        val hierarchy = Mat()
        val contours: List<MatOfPoint> = ArrayList()
        //Log.d("TAG","Before Toast");
        Imgproc.findContours(B, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE)
        var current_contour: Mat
        var largest_contour: Mat? = null
        var largest_area = 0.0
        var largest_contour_index = 0
        var com = findViewById<TextView>(R.id.com)

        for (contourIdx in contours.indices){
            current_contour = contours[contourIdx]
            val contourArea = Imgproc.contourArea(current_contour)
            if (contourArea > largest_area) {
                largest_area = contourArea
                largest_contour_index = contourIdx
                largest_contour = current_contour
            }
        }

        var command = "";

        if (largest_contour != null) {
            val p = Imgproc.moments(largest_contour)
            val x = (p._m10 / p._m00).toInt()
            val y = (p._m01 / p._m00).toInt()

            A = inputFrame.rgba()
            Imgproc.drawContours(A, contours, largest_contour_index, Scalar(0.0, 255.0, 0.0, 255.0), 3)
            var Bound_img = Imgproc.boundingRect(largest_contour)
            Imgproc.rectangle(A, Point(Bound_img.x.toDouble(), Bound_img.y.toDouble()),Point(Bound_img.x.toDouble()+Bound_img.width, Bound_img.y.toDouble()+Bound_img.height),
                Scalar(0.0,0.90,255.0,255.0),3
            )
            x2 = ((Bound_img?.width)?.div(2) ?: Int) as Int
           var dist = x1-(Bound_img.x +x2)
            Log.d("distance", "$dist")
            if (dist >= 300){
                Log.d("detected left", "$dist")
                command = "L"
//                com.setText("L")
            }
            else if (dist <= 0){
                Log.d("detected right", "$dist")
//                    Command.setText("R")
                command = "R"
//                com.setText("R")

            }
            else {
                Log.d("detected front", "$dist")
//                    Command.setText("F")
                command = "F"
//                com.setText("F")

            }
            if ((pre_command == command || com.text == "0")){
                Log.d("pre command", "previous commanad")
            }
            else{
                sendCommand(command)
                pre_command = "$command"
                Log.d("command", "onMove: $pre_command" +
                        " ")
            }
        }else{
            if (pre_command != "S"){
                sendCommand("S")
                pre_command = "S"
            }
            else{}
        }
       return A
    }

    private fun sendCommand(input: String) {
        if (ControlActivity.m_bluetoothSocket != null){
            try {
//                var stringAsBytes: ByteArray = input.encodeToByteArray()
//                var stringAsBytes: Int = input
//                stringAsBytes[stringAsBytes.size - 1] = 0;
                ControlActivity.m_bluetoothSocket!!.outputStream.write(input.toByteArray())
//                sendJSONCOM(input)
                Log.d("data", "$input")
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}
