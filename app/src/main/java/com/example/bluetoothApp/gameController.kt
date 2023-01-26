package com.example.bluetoothApp

import android.content.Context
import android.util.Log
import android.view.*

class gameController(context: Context){
}
    fun getGameControllerIds(): List<Int> {
        val gameControllerDeviceIds = mutableListOf<Int>()
        val deviceIds = InputDevice.getDeviceIds()
        deviceIds.forEach { deviceId ->
            InputDevice.getDevice(deviceId).apply {
                // Verify that the device has gamepad buttons, control sticks, or both.
                if (sources and InputDevice.SOURCE_GAMEPAD == InputDevice.SOURCE_GAMEPAD
                    || sources and InputDevice.SOURCE_JOYSTICK == InputDevice.SOURCE_JOYSTICK
                ) {
                    // This device is a game controller. Store its device ID.
                    gameControllerDeviceIds
                        .takeIf { !it.contains(deviceId) }
                        ?.add(deviceId)
                    Log.d("game controller", "$deviceIds")
                }
            }
        }
        return gameControllerDeviceIds
    }

//class GameView(c:Context) :View(c) {
//
//    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
//        var handled = false
//        if (event.source and InputDevice.SOURCE_GAMEPAD == InputDevice.SOURCE_GAMEPAD) {
//            if (event.repeatCount == 0) {
//                when (keyCode) {
//                    // Handle gamepad and D-pad button presses to navigate the ship
//
//                    else -> {
//                        keyCode.takeIf { isFireKey(it) }?.run {
//                            // Update the ship object to fire lasers
//                            handled = true
//                        }
//                    }
//                }
//            }
//            if (handled) {
//                return true
//            }
//        }
//        return super.onKeyDown(keyCode, event)
//
//    }
//
//    // Here we treat Button_A and DPAD_CENTER as the primary action
//    // keys for the game.
//    private fun isFireKey(keyCode: Int): Boolean =
//        keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_BUTTON_A
//}
//
//class Dpad {
//
//    private var directionPressed = -1 // initialized to -1
//
//    fun getDirectionPressed(event: InputEvent): Int {
//        if (!isDpadDevice(event)) {
//            return -1
//        }
//
//        // If the input event is a MotionEvent, check its hat axis values.
//        (event as? MotionEvent)?.apply {
//
//            // Use the hat axis value to find the D-pad direction
//            val xaxis: Float = event.getAxisValue(MotionEvent.AXIS_HAT_X)
//            val yaxis: Float = event.getAxisValue(MotionEvent.AXIS_HAT_Y)
//
//            directionPressed = when {
//                // Check if the AXIS_HAT_X value is -1 or 1, and set the D-pad
//                // LEFT and RIGHT direction accordingly.
//                xaxis.compareTo(-1.0f) == 0 -> Dpad.LEFT
//                xaxis.compareTo(1.0f) == 0 -> Dpad.RIGHT
//                // Check if the AXIS_HAT_Y value is -1 or 1, and set the D-pad
//                // UP and DOWN direction accordingly.
//                yaxis.compareTo(-1.0f) == 0 -> Dpad.UP
//                yaxis.compareTo(1.0f) == 0 -> Dpad.DOWN
//                else -> directionPressed
//            }
//        }
//        // If the input event is a KeyEvent, check its key code.
//        (event as? KeyEvent)?.apply {
//
//            // Use the key code to find the D-pad direction.
//            directionPressed = when(event.keyCode) {
//                KeyEvent.KEYCODE_DPAD_LEFT -> Dpad.LEFT
//                KeyEvent.KEYCODE_DPAD_RIGHT -> Dpad.RIGHT
//                KeyEvent.KEYCODE_DPAD_UP -> Dpad.UP
//                KeyEvent.KEYCODE_DPAD_DOWN -> Dpad.DOWN
//                KeyEvent.KEYCODE_DPAD_CENTER ->  Dpad.CENTER
//                else -> directionPressed
//            }
//        }
//        return directionPressed
//    }
//
//    companion object {
//        internal const val UP = 0
//        internal const val LEFT = 1
//        internal const val RIGHT = 2
//        internal const val DOWN = 3
//        internal const val CENTER = 4
//
//        fun isDpadDevice(event: InputEvent): Boolean =
//            // Check that input comes from a device with directional pads.
//            event.source and InputDevice.SOURCE_DPAD != InputDevice.SOURCE_DPAD
//    }
//}
//
//fun onGenericMotionEvent(event: MotionEvent) {
//    val dpad = Dpad()
//    if (Dpad.isDpadDevice(event)) {
//        when (dpad.getDirectionPressed(event)) {
//            Dpad.LEFT -> {
//                // Do something for LEFT direction press
//
//
//            }
//            Dpad.RIGHT -> {
//                // Do something for RIGHT direction press
//
//
//            }
//            Dpad.UP -> {
//                // Do something for UP direction press
//
//            }
//            Dpad.DOWN -> {
//                // Do something for UP direction press
//            }
//            Dpad.CENTER -> {
//                // Do something for UP direction press
//            }
//
//        }
//    }
//
//    // Check if this event is from a joystick movement and process accordingly.
//}
//
//
