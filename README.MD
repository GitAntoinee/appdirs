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

More functions are available in the same manner (followed by an example in Kali Linux):
* `getUserDataDir` (`/home/user/.local/share/author/program/1.0`)
* `getUserConfigDir` (`/home/user/.config/author/program/1.0`)
* `getUserCacheDir` (`/home/user/.cache/author/program/1.0`)
* `getUserLogDir` (`/home/user/.cache/author/program/1.0/logs`)
* `getSiteDataDir` (`/etc/xdg`)
* `getSiteConfigDir` (`/usr/share/author/program/1.0`)