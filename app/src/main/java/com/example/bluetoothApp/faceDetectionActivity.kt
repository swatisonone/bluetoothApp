package com.example.bluetoothApp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.SurfaceView
import android.view.WindowManager
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.bluetoothapp.R
import org.json.JSONException
import org.opencv.android.*
import org.opencv.core.*
import org.opencv.imgproc.Imgproc
import org.opencv.objdetect.CascadeClassifier
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

class faceDetectionActivity : AppCompatActivity(), CameraBridgeViewBase.CvCameraViewListener2{

    var A: Mat? = null
    var B: Mat? = null
    var C: Mat? = null

    private var absoluteFaceSize = 0
    var faceDetector: CascadeClassifier? = null
    lateinit var cascFile : File
    lateinit var list :Array<String>
    var pre_command =""


    private lateinit var javaCameraView: CameraBridgeViewBase

    private val baseLoaderCallback: BaseLoaderCallback = object : BaseLoaderCallback(this) {
        override fun onManagerConnected(status: Int) {
            when (status) {
                SUCCESS -> {
                    var `is`: InputStream = getResources().openRawResource (R.raw.haarcascade_fullbody)
                    var cascadesDir: File = getDir("cascade", Context.MODE_PRIVATE)
                    cascFile = File(cascadesDir,"haarcascade_fullbody.xml")
                    var fos : FileOutputStream = FileOutputStream(cascFile)
//                    var buffer : Array<Int?> = arrayOfNulls(4098)
                    var buffer = ByteArray(4096)
                    var byteRead : Int
                    while((`is`.read(buffer).also { byteRead = it })!= -1){
                        fos.write(buffer,0,byteRead)
                    }
                    `is`.close()
                    fos.close()
                    faceDetector = CascadeClassifier(cascFile.absolutePath)

                    if (faceDetector!!.empty()){
                        faceDetector=null
                    }
                    else {
                        cascadesDir.delete()
                    }
                    javaCameraView.enableView()
                    Log.i("camera", "OpenCV loaded successfully")
                    Toast.makeText(this@faceDetectionActivity, "OpenCV loaded successfully", Toast.LENGTH_SHORT).show()
                }

                else -> {
                    Log.d("camera", "OpenCVActivity\", \"Error loading cascade")
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
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
        setContentView(R.layout.activity_face_detection)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 101)
            Toast.makeText(this, "give camera permission", Toast.LENGTH_SHORT).show()
        }
        var switch = findViewById<Switch>(R.id.cam)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        javaCameraView= findViewById(R.id.surfaceView1) as JavaCameraView

        switch.setOnCheckedChangeListener { _, isChecked ->
            if (switch.isChecked) {
              javaCameraView.setCameraIndex(1)

            } else {
                javaCameraView.setCameraIndex(0)
            }
            javaCameraView.visibility = SurfaceView.VISIBLE
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
        absoluteFaceSize = (height * 0.2).toInt()
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


    override fun onCameraViewStopped() {
        A!!.release()
        B!!.release()
        C!!.release()
    }

    override fun onCameraFrame(inputFrame: CameraBridgeViewBase.CvCameraViewFrame?): Mat? {

        var Command = findViewById<TextView>(R.id.command)

        A = inputFrame?.rgba()
        B = inputFrame?.gray()
        Imgproc.cvtColor(A, B, Imgproc.COLOR_RGBA2RGB)
//        var faceDetections = MatOfRect()
//        faceDetector?.detectMultiScale(A,faceDetections, 1.3,4, CASCADE_SCALE_IMAGE, Size(
//            absoluteFaceSize.toDouble(), absoluteFaceSize.toDouble()
//        ))
//        faceDetector?.detectMultiScale(
//            B,faceDetections , 2.0, 2, 2,
//            Size(absoluteFaceSize.toDouble(), absoluteFaceSize.toDouble()), Size(200.0, 200.0)
//        )

        var x1: Int
        var x2: Int
////        faceDetector?.detectMultiScale(A,faceDetections)
//        val facesArray:Array<Rect> = faceDetections.toArray()
//        for (rect:Rect in facesArray){
////            Toast.makeText(this, "human detected", Toast.LENGTH_SHORT).show()
        x1 = ((A?.width())?.div(2) ?: Int) as Int
        x2 = ((A?.height())?.div(2) ?: Int) as Int
//                Imgproc.rectangle(
//                    A, Point(rect.x.toDouble(), rect.y.toDouble()), Point(
//                        (rect.x + rect.width).toDouble(),
//                        (rect.y + rect.height).toDouble()
//                    ), Scalar(255.0, 0.0, 0.0),5
//                )
        Imgproc.line(
            A, Point(x1.toDouble(), 0.0), Point(
                x1.toDouble(),
                x2.toDouble() * 2
            ), Scalar(255.0, 0.0, 0.0), 5
        )


//
//            }
//
//        Imgproc.cvtColor(A, B, Imgproc.COLOR_RGBA2RGB)

        val faces = MatOfRect()

        //Use the classifier to detect faces

        //Use the classifier to detect faces
        faceDetector?.detectMultiScale(
            B, faces, 1.1, 2, 2,
            Size(absoluteFaceSize.toDouble(), absoluteFaceSize.toDouble()), Size()
        )
        // If there are any faces found, draw a rectangle around it
        // If there are any faces found, draw a rectangle around it
        var facesArray = faces.toArray()
//        for (i in facesArray.indices) {
//            Log.i("face : ", "Detected")
//            Imgproc.rectangle(
//                A,
//                facesArray[i].tl(),
//                facesArray[i].br(),
//                Scalar(0.0, 255.0, 0.0, 255.0),
//                5
//            )
//        }
        var focalLength: Int
        var command = "";
        var dist: Int
        var dist1: Int
        Log.d("face Array", "$facesArray")
        if (facesArray != null) {
            for (rect: Rect in facesArray) {
                Imgproc.circle(
                    A,
                    Point(rect.x + rect.width.toDouble() / 2, rect.y + rect.height.toDouble() / 2),
                    2,
                    Scalar(255.0, 0.0, 0.0),
                    3
                )
                Imgproc.rectangle(
                    A, Point(rect.x.toDouble(), rect.y.toDouble()), Point(
                        (rect.x + rect.width).toDouble(),
                        (rect.y + rect.height).toDouble()
                    ), Scalar(255.0, 0.0, 0.0), 5
                )
                dist = (x1 - rect.x + rect.width.toDouble() / 2).toInt()
                dist1 = ((9 * (x1 - (rect.x + rect.width) / 2)) / 32)
//                Log.d("detected distance", "$dist")
//                Log.d("detected distance", "$dist1")

//                sendCommand("$dist1")

                if (dist >= 300) {
                    Log.d("detected left", "$dist")
                    command = "L"
//                    Command.setText("L")
//                    sendCommand("L")
                } else if (dist <= 0) {
                    Log.d("detected right", "$dist")
//                    Command.setText("R")
                    command = "R"
//                    sendCommand("R")
                } else {
                    Log.d("detected front", "$dist")
//                    Command.setText("F")
                    command = "F"
//                    sendCommand("F")
                }
                if (pre_command == command) {
                    Log.d("pre command", "previous commanad")
                } else {
                    sendCommand(command)
                    pre_command = "$command"
                    Log.d(
                        "command", "onMove: $pre_command" +
                                " "
                    )
                }
            }
        }
        else{
            Log.d("Stop", "Stop")
//        if (pre_command != "S") {
            sendCommand("S")
            pre_command = "S"
//        }
    }
//        if (circle == 1){
//            Log.d("Stop", "Stop")
//            sendCommand("S")
//        }


        return A
    }
    private fun sendCommand(input: String) {
        if (ControlActivity.m_bluetoothSocket != null) {
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
    fun sendJSONCOM(command: String) {
        try {
            val js = org.json.JSONObject()
            js.put("Command", command)
//            js.put("Strength", Strength)
            //wsCOM.send("COM:-");
//            wsCOM.send("COM$js")
            Log.d("Command", (js).toString())
            ControlActivity.m_bluetoothSocket!!.outputStream.writer(js as Charset)
            Log.d("Command", js["Command"] as String)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
}


