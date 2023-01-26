package com.example.bluetoothApp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.bluetoothapp.R
import java.io.IOException


class ReceiveDataActivity : AppCompatActivity() {

    private var mHandler: Handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            Log.d("ReceivedData", msg.obj.toString())
            var data = findViewById<TextView>(R.id.info)
            val writeBuf = msg.obj as ByteArray
            val begin = msg.arg1 as Int
            val end = msg.arg2 as Int
            when (msg.what) {
                1 -> {
                    var writeMessage = String(writeBuf)
                    writeMessage = writeMessage.substring(begin, end+1)
                    Log.d("received", writeMessage)
                    data.setText(writeMessage)
                }
            }
        }
    }
    val list1:ArrayList<String> = ArrayList()
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        val b = Intent(applicationContext, ControlActivity::class.java)
        startActivity(b)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receive_data)

        val Start = findViewById<Button>(R.id.dataReceiver)

        Start?.setOnClickListener {
//            ConnectedThread(ControlActivity.m_bluetoothSocket)
            var resut = ReceiveData()

            val data = findViewById<TextView>(R.id.info)

        }

    }

    private fun ReceiveData(): String? {

        val data = findViewById<TextView>(R.id.info)
        val byte = ByteArray(1024)
        var begin = 0
        var bytes = 0
        while (true) {
            try {
                bytes = ControlActivity.m_bluetoothSocket?.inputStream?.read()!!

                for (i in begin until bytes) {
//                    if (byte.get(i) == "#".toByteArray()[0]) {
                        mHandler.obtainMessage(1, begin, i, byte).sendToTarget()
                        begin = i + 1

//                    }
                }

                val readMessage = bytes?.toChar().toString()
                if (readMessage != null) {
                    Log.d("ReceivedData", readMessage)
                    data.post{
                        data.text = readMessage
                    }
                }
                return readMessage

            } catch (e: IOException) {
                e.printStackTrace()
                Log.e("socket", "Could not close the connect socket", e)
            }
        }

    }
    fun cancel() {
        try {
            ControlActivity.m_bluetoothSocket?.close()
        } catch (e: IOException) {
        }
    }

}