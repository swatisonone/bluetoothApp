@echo off
"C:\\Users\\SHREE\\AppData\\Local\\Android\\Sdk\\cmake\\3.18.1\\bin\\cmake.exe" ^
  "-HC:\\Users\\SHREE\\AndroidStudioProjects\\bluetoothApp - Copy\\sdk\\libcxx_helper" ^
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
  "-DCMAKE_LIBRARY_OUTPUT_DIRECTORY=C:\\Users\\SHREE\\AndroidStudioProjects\\bluetoothApp - Copy\\sdk\\build\\intermediates\\cxx\\RelWithDebInfo\\2x1s4c4o\\obj\\x86" ^
  "-DCMAKE_RUNTIME_OUTPUT_DIRECTORY=C:\\Users\\SHREE\\AndroidStudioProjects\\bluetoothApp - Copy\\sdk\\build\\intermediates\\cxx\\RelWithDebInfo\\2x1s4c4o\\obj\\x86" ^
  "-DCMAKE_BUILD_TYPE=RelWithDebInfo" ^
  "-BC:\\Users\\SHREE\\AndroidStudioProjects\\bluetoothApp - Copy\\sdk\\.cxx\\RelWithDebInfo\\2x1s4c4o\\x86" ^
  -GNinja ^
  "-DANDROID_STL=c++_shared"
