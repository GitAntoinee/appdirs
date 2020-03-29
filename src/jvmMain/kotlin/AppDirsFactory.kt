package fr.pottime.appdirs

internal actual object AppDirsFactory {
    private val operatingSystemName = System.getProperty("os.name").toLowerCase()

    actual val instance: AppDirs = when {
        operatingSystemName.startsWith("windows") -> WindowsAppDirs
        operatingSystemName.startsWith("mac os x") -> MacOsXAppDirs
        else -> NixAppDirs
    }
}