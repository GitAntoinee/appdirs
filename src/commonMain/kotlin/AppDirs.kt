package fr.pottime.appdirs

interface AppDirs {
    companion object : AppDirs by AppDirsFactory.instance

    fun getUserDataDir(name: String, version: String? = null, author: String? = null, roaming: Boolean = false): String
    fun getUserConfigDir(name: String, version: String? = null, author: String? = null, roaming: Boolean = false): String
    fun getUserCacheDir(name: String, version: String? = null, author: String? = null): String
    fun getUserLogDir(name: String, version: String? = null, author: String? = null): String
    fun getSiteDataDir(name: String, version: String? = null, author: String? = null): String
    fun getSiteConfigDir(name: String, version: String? = null, author: String? = null): String
}

internal expect object AppDirsFactory {
    val instance: AppDirs
}