@echo off
"C:\\Users\\SHREE\\AppData\\Local\\Android\\Sdk\\cmake\\3.18.1\\bin\\cmake.exe" ^
  "-HC:\\Users\\SHREE\\AndroidStudioProjects\\bluetoothApp - Copy\\sdk1\\libcxx_helper" ^
  "-DCMAKE_SYSTEM_NAME=Android" ^
  "-DCMAKE_EXPORT_COMPILE_COMMANDS=ON" ^
  "-DCMAKE_SYSTEM_VERSION=21" ^
  "-DANDROID_PLATFORM=android-21" ^
  "-DANDROID_ABI=x86" ^
  "-DCMAKE_ANDROID_ARCH_ABI=x86" ^
  "-DANDROID_NDK=C:\\Users\\SHREE\\AppData\\Local\\Android\\Sdk\\ndk\\23.1.7779620" ^
  "-DCMAKE_ANDROID_NDK=C:\\Users\\SHREE\\AppData\\Local\\Android\\Sdk\\ndk\\23.1.7779620" ^
  "-DCMAKE_TOOLCHAIN_FILE=C:\\Users\\SHREE\\AppData\\Local\\Android\\Sdk\\ndk\\23.1.7779620\\build\\cmake\\android.toolchain.cmake" ^
  "-DCMAKE_MAKE_PROGRAM=C:\\Users\\SHREE\\AppData\\Local\\Android\\Sdk\\cmake\\3.18.1\\bin\\ninja.exe" ^
  "-DCMAKE_LIBRARY_OUTPUT_DIRECTORY=C:\\Users\\SHREE\\AndroidStudioProjects\\bluetoothApp - Copy\\sdk1\\build\\intermediates\\cxx\\RelWithDebInfo\\1k1t201a\\obj\\x86" ^
  "-DCMAKE_RUNTIME_OUTPUT_DIRECTORY=C:\\Users\\SHREE\\AndroidStudioProjects\\bluetoothApp - Copy\\sdk1\\build\\intermediates\\cxx\\RelWithDebInfo\\1k1t201a\\obj\\x86" ^
  "-DCMAKE_BUILD_TYPE=RelWithDebInfo" ^
  "-BC:\\Users\\SHREE\\AndroidStudioProjects\\bluetoothApp - Copy\\sdk1\\.cxx\\RelWithDebInfo\\1k1t201a\\x86" ^
  -GNinja ^
  "-DANDROID_STL=c++_shared"
