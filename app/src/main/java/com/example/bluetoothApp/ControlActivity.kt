package com.example.bluetoothApp

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothAdapter.*
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import java.util.*
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.CountDownTimer
import android.speech.RecognizerIntent
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnClickListener
import android.view.View.OnTouchListener
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.bluetoothapp.R
import com.vitpunerobortics.joysticklibary.JoystickView
import java.io.IOException

class ControlActivity: AppCompatActivity() {

    private val RQ_SPEECH_REC =100
//    private var angleTextView: TextView? = null
//    private var powerTextView: TextView? = null
//    private var directionTextView: TextView? = null
    private var joystick: JoystickView? = null
    private var joystick1: JoystickView? = null
    private var joystick2: JoystickView? = null
    var counter = 0

    companion object {
        var m_myUUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
        var m_bluetoothSocket: BluetoothSocket? = null
        lateinit var m_progress: ProgressDialog
        lateinit var m_bluetoothAdapter: BluetoothAdapter
        var m_isConnected: Boolean = false
        lateinit var m_address: String
    }
    @SuppressLint("MissingInflatedId", "ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_control)

        m_address = intent.getStringExtra(blutoothActivity.EXTRA_ADDRESS).toString()

//        angleTextView = findViewById<View>(R.id.angleTextView) as TextView
//        powerTextView = findViewById<View>(R.id.powerTextView) as TextView
//        directionTextView = findViewById<View>(R.id.directionTextView) as TextView

        val disconnect = findViewById<TextView>(R.id.disconnect)
        val anti = findViewById<Button>(R.id.antiClockwise)
        val clk = findViewById<Button>(R.id.clockwise)
//        val hl = findViewById<Button>(R.id.hl)
//        val hr = findViewById<Button>(R.id.hr)
        val grp = findViewById<Button>(R.id.grip)
        val voiceBtn = findViewById<ImageView>(R.id.voiceBtn)
        joystick = findViewById<View>(R.id.joystickView) as JoystickView
//        joystick1 = findViewById(R.id.joystickView1)
//        joystick2 = findViewById(R.id.joystickView2)
        val voiceCommand = findViewById<TextView>(R.id.voiceCommand)
//        val no = findViewById<Button>(R.id.no)
        val stop = findViewById<Button>(R.id.stop)
        var dataReceiver = findViewById<Button>(R.id.dataReceiver)
//        var center = findViewById<Button>(R.id.center)
        var pre_command =""

        dataReceiver?.setOnClickListener {
            var intent = Intent(this, ReceiveDataActivity::class.java)
            startActivity((intent))
        }
        var gyro = findViewById<Button>(R.id.gyro)
//        val reset = findViewById<Button>(R.id.resetBtn)
//        var fast_slow = findViewById<Button>(R.id.fast_slowBtn)
//        var hf = findViewById<Button>(R.id.hf)
//
//        hf?.setOnClickListener{sendCommand("M")
//        voiceCommand.setText("Hf")}
//        reset?.setOnClickListener{sendCommand("r")
//            voiceCommand.setText("reset")}
//
//        fast_slow?.setOnClickListener(object :OnClickListener{
//            override fun onClick(p0: View?) {
//                val ButtonText = fast_slow.text
//                if (ButtonText.equals("fast")){
//                    sendCommand("f")
//                    voiceCommand.setText("fast")
//                    fast_slow.setText("slow")
//                }
//                else{
//                    sendCommand("s")
//                    voiceCommand.setText("slow")
//                    fast_slow.setText("fast")
//                }
//            }
//
//        })

        gyro?.setOnClickListener( object : OnClickListener{

            override fun onClick(p0: View?) {
                var intent = Intent(this@ControlActivity,gyroscopeActivity::class.java)
                startActivity(intent)
            }
        })

        disconnect?.setOnClickListener { disconnect() }


//        ConnectToDevice(this).execute()

        stop?.setOnClickListener {
            sendCommand("S")
            voiceCommand.setText("S")
        }
//        center?.setOnClickListener {
//            sendCommand("P")
//            voiceCommand.setText("center")
//        }
//
        anti?.setOnTouchListener(object :OnTouchListener {
            @RequiresApi(Build.VERSION_CODES.M)
            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                Log.d("button", "Enter")
//                if (p1?.action ==MotionEvent.ACTION_BUTTON_PRESS){
//                    Log.d("button", "pressed")
//                }
//                else if(p1?.getAction()==MotionEvent.ACTION_BUTTON_RELEASE){
//                    Log.d("button", "off")
//                }
                when (p1?.action) {
                    MotionEvent.ACTION_UP -> {
                        Log.d("button", "pressed")
                        anti.setTextColor(Color.parseColor("#FF000000"))
                        voiceCommand.setText("S")
                        sendCommand("S")
                    }
                    MotionEvent.ACTION_DOWN -> {
                        Log.d("button", "released")
                        anti.setTextColor(Color.parseColor("#8E8C8C"))
                        voiceCommand.setText("A")
                        sendCommand("A")
                    }
                }
                return true
            }
        }
    )
//
//
        clk?.setOnTouchListener(object :OnTouchListener {
            @RequiresApi(Build.VERSION_CODES.M)
            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                when (p1?.action) {
                    MotionEvent.ACTION_UP -> {
                        Log.d("button", "pressed")
                        clk.setTextColor(Color.parseColor("#FF000000"))
                        voiceCommand.setText("S")
                        sendCommand("S")
                    }
                    MotionEvent.ACTION_DOWN -> {
                        Log.d("button", "released")
                        clk.setTextColor(Color.parseColor("#8E8C8C"))
                        voiceCommand.setText("C")
                        sendCommand("C")
                    }
                }
                return true
            }
        }
        )
//        hl?.setOnTouchListener(object :OnTouchListener {
//            @RequiresApi(Build.VERSION_CODES.M)
//            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
//                when (p1?.action) {
//                    MotionEvent.ACTION_UP -> {
//                        Log.d("button", "pressed")
//                        hl.setBackgroundColor(Color.parseColor("#8E8C8C"))
//                        voiceCommand.setText("S")
//                        sendCommand("S")
//                    }
//                    MotionEvent.ACTION_DOWN -> {
//                        Log.d("button", "released")
//                        hl.setBackgroundColor(Color.parseColor("#FFBB86FC"))
//                        voiceCommand.setText("H")
//                        sendCommand("H")
//                    }
//                }
//                return true
//            }
//        }
//        )
//        hr?.setOnTouchListener(object :OnTouchListener {
//            @RequiresApi(Build.VERSION_CODES.M)
//            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
//                when (p1?.action) {
//                    MotionEvent.ACTION_UP -> {
//                        Log.d("button", "pressed")
//                        hr.setBackgroundColor(Color.parseColor("#8E8C8C"))
//                        voiceCommand.setText("S")
//                        sendCommand("S")
//                    }
//                    MotionEvent.ACTION_DOWN -> {
//                        Log.d("button", "released")
//                        hr.setBackgroundColor(Color.parseColor("#FFBB86FC"))
//                        voiceCommand.setText("h")
//                        sendCommand("h")
//                    }
//                }
//                return true
//            }
//        }
//        )
//        no?.setOnClickListener {
//            sendCommand("N")
//            voiceCommand.setText("NO")
//        }
        grp?.setOnClickListener(object :OnClickListener{
            override fun onClick(p0: View?) {
                val ButtonText = grp.text
                if (ButtonText.equals("UGRP")){
                    sendCommand("g")
                    voiceCommand.setText("UGRP")
                        grp.setText("GRP")
                    }
                else{
                    sendCommand("G")
                    voiceCommand.setText("GRP")
                        grp.setText("UGRP")
                    }
            }

        })



//        leftArm.setOnClickListener { sendCommand("LA") }
//        rightArm.setOnClickListener { sendCommand("RA") }
        voiceBtn.setOnClickListener{ askSpeechInput()}


        joystick!!.setOnMoveListener(object : JoystickView.OnMoveListener{
            var command = "";

            override fun onMove(angle: Int, strength: Int) {
//                angleTextView!!.text = " $angle"
//                powerTextView!!.text = " $strength"

                if ((angle >= 320 || angle<=50) && (strength>30)){
//                    directionTextView!!.setText("Right")
                    command = "R"
                    voiceCommand.setText("R")
//                    sendCommand("R")
                }
                else if ((angle<120 && angle>60) && (strength>30)){
//                    directionTextView!!.setText("Forward")
                    command = "F"
                    voiceCommand.setText("F")
//                    sendCommand("F")
                }
                else if ((angle<210 && angle >120) && (strength>30) ){
//                    directionTextView!!.setText("Left")
                    command= "L"
                    voiceCommand.setText("L")
//                   sendCommand("L")
                }
                else if ((angle<320 && angle>240) && (strength>30)){
//                    directionTextView!!.setText("Backward")
                    command = "B"
                    voiceCommand.setText("B")
//                    sendCommand("B")
                }
                else {
                    command = "S"
                    voiceCommand.setText("S")
//                    directionTextView!!.setText("Stop")
//                    sendCommand("s")
                }



                if (pre_command == voiceCommand.text){
                    Log.d("pre command", "previous commanad")
                }
                else{
                    sendCommand(command)
                    pre_command = "$command"
                    Log.d("command", "onMove: $pre_command" +
                            " ")
                }


            }

        }, JoystickView.DEFAULT_LOOP_INTERVAL)
//
//        joystick2!!.setOnMoveListener(object : JoystickView.OnMoveListener {
//            var command = ""
//            override fun onMove(angle: Int, strength: Int) {
//                if ((angle >= 60 && angle<=120) && (strength>50)){
////                    directionTextView!!.setText("Right")
//                    command = "U"
//                    voiceCommand.setText("U")
//                }
//                else if ((angle<300 && angle>220) && (strength>50) ){
////                    directionTextView!!.setText("Forward")
//                    command = "D"
//                    voiceCommand.setText("D")
//                }
//                else{
//                    command = "S"
//                    voiceCommand.setText("S")
//                }
//                if (pre_command ==voiceCommand.text){
//                    Log.d("pre command", "previous commanad")
//                }
//                else{
//                    sendCommand(command)
//                    pre_command = "$command"
//                }
//
//
//            }
//
//        })
//
//        joystick1!!.setOnMoveListener(object : JoystickView.OnMoveListener {
//            var command = ""
//            override fun onMove(angle: Int, strength: Int) {
//                if ((angle >= 50 && angle<=120) && (strength>50)){
////                    directionTextView!!.setText("Right")
//                    command = "u"
//                    voiceCommand.setText("u")
//                }
//                else if ((angle<300 && angle>220) && (strength>50)){
////                    directionTextView!!.setText("Forward")
//                    command = "d"
//                    voiceCommand.setText("d")
//                }
//                else{
//                    command = "S"
//                    voiceCommand.setText("S")
//                }
//                if (pre_command ==voiceCommand.text){
//                    Log.d("pre command", "previous commanad")
//                }
//                else{
//                    sendCommand(command)
//                    pre_command = "$command"
//
//                }
//
//            }
//
//        })

        gameController()
    }

    private fun gameController() {
        getGameControllerIds()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
        val voiceCommand = findViewById<TextView>(R.id.voiceCommand)
        voiceCommand.setText(result?.get(0).toString())
        Log.d("commands", "$result")
//        sendCommand("S")
        val text = voiceCommand.text
        Log.d("text", "$text")

        if ("$text" == "forward") {
            sendCommand("F")
        } else if ("$text" == "backward") {
            sendCommand("B")
        } else if ("$text" == "left") {
            sendCommand("L")
        } else if ("$text" == "right") {
            sendCommand("R")
        } else if ("$text" == "stop") {
            sendCommand("S")
        } else {
            sendCommand("S")

        }

        startTimeCounter()
    }

    private fun startTimeCounter() {
        val voiceCommand = findViewById<TextView>(R.id.voiceCommand)
        object : CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                voiceCommand.text = counter.toString()
                counter++
            }

            override fun onFinish() {
                voiceCommand.text = "S"
                sendCommand("S")
            }
        }.start()    }


    private fun askSpeechInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Say Something")
        startActivityForResult(intent,RQ_SPEECH_REC)
//        if (!SpeechRecognizer.isRecognitionAvailable(this)){
//            Toast.makeText(this,"Speech Recognition not available",Toast.LENGTH_SHORT).show()
//        }else{
//            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
//            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
////            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,Locale.getDefault())
//            intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Say Something")
//            startActivityForResult(intent,RQ_SPEECH_REC)
//        }
    }


    private fun sendCommand(input: String) {

        if (m_bluetoothSocket != null) {
            try {

                m_bluetoothSocket!!.outputStream.write(input.toByteArray())
                Log.d("data", "send")
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun disconnect() {
        if (m_bluetoothSocket != null) {
            try {
                m_bluetoothSocket!!.close()
                m_bluetoothSocket = null
                m_isConnected = false
                val intent = Intent(this, blutoothActivity ::class.java)
                startActivity(intent)
                Toast.makeText(this, "Disconnect", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        finish()
    }

    class ConnectToDevice(c: Context) : AsyncTask<Void, Void, String?>() {
        private var connectSuccess: Boolean = true
        private val context: Context

        init {
            this.context = c
        }

        override fun onPreExecute() {
            super.onPreExecute()

            m_progress = ProgressDialog.show(context, "Connecting...", "please wait")
        }

        override fun doInBackground(vararg p0: Void?): String? {
            try {
                if (m_bluetoothSocket == null || !m_isConnected) {
                    m_bluetoothAdapter = getDefaultAdapter()
                    val device: BluetoothDevice = m_bluetoothAdapter.getRemoteDevice(m_address)
                    if (ContextCompat.checkSelfPermission(
                            this.context,
                            Manifest.permission.BLUETOOTH_CONNECT
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions(
                            this.context as Activity, arrayOf(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.BLUETOOTH_CONNECT
                            ), 1001
                        )
                    }
                    m_bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(m_myUUID)
                    getDefaultAdapter().cancelDiscovery()
                    m_bluetoothSocket?.connect()
                }
            } catch (e: IOException) {
                connectSuccess = false
                e.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
//            if (m_isConnected) {
//                Toast.makeText(this.context, "Connected", Toast.LENGTH_SHORT).show()
//            } else Toast.makeText(this.context, "Connection Failed", Toast.LENGTH_SHORT).show()

            if (!connectSuccess) {
                Log.i("data", "couldn't connect")
                Toast.makeText(this.context, "Couldn't Connect", Toast.LENGTH_SHORT).show()
            } else {
                m_isConnected = true
                Toast.makeText(this.context, "Connected", Toast.LENGTH_SHORT).show()
            }
            m_progress?.dismiss()

        }
    }
}

