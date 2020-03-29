# AppDirs
AppDirs provides cross platform access to the special folders/directories like application data.

## Benefits
* AppDirs is written in Kotlin/MultiPlatform, who let you to use it not only in Kotlin/JVM!
* Usage is pretty easy and do not change between platforms (see examples below)

## Usage
The usage is very straightforward
```kotlin
// Find appdata folder
val appdata = AppDirs.getUserConfigDir("Program name", "Program version", "Program author (you!)")
```