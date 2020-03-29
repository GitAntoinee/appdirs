package fr.pottime.appdirs

import kotlinx.cinterop.pointed
import kotlinx.cinterop.toKString
import platform.posix.getenv
import platform.posix.getpwuid
import platform.posix.getuid

internal actual object AppDirsFactory {
    actual val instance: AppDirs = LinuxAppDirs
}

object LinuxAppDirs : AppDirs {
    private const val XDG_CONFIG_DIRS = "XDG_CONFIG_DIRS"
    private const val XDG_DATA_DIRS = "XDG_DATA_DIRS"
    private const val XDG_CACHE_HOME = "XDG_CACHE_HOME"
    private const val XDG_CONFIG_HOME = "XDG_CONFIG_HOME"
    private const val XDG_DATA_HOME = "XDG_DATA_HOME"

    private val pw = getpwuid(getuid()) ?: error("Cannot get password database for user ${getuid()}")
    private val home = pw.pointed.pw_dir!!.toKString()

    private fun getEnvOrElse(name: String, default: () -> String): String = getenv(name)?.toKString() ?: default()
    private fun buildPath(vararg paths: String?) = paths.filterNotNull().joinToString("/")

    override fun getUserDataDir(name: String, version: String?, author: String?, roaming: Boolean): String =
        buildPath(getEnvOrElse(XDG_DATA_HOME) { buildPath(home, ".local", "share") }, author, name, version)

    override fun getUserConfigDir(name: String, version: String?, author: String?, roaming: Boolean): String =
        buildPath(getEnvOrElse(XDG_CONFIG_HOME) { buildPath(home, ".config") }, author, name, version)

    override fun getUserCacheDir(name: String, version: String?, author: String?): String =
        buildPath(getEnvOrElse(XDG_CACHE_HOME) { buildPath(home, ".cache") }, author, name, version)

    override fun getUserLogDir(name: String, version: String?, author: String?): String =
        buildPath(getEnvOrElse(XDG_CACHE_HOME) { buildPath(home, ".cache") }, author, name, version, "logs")

    override fun getSiteDataDir(name: String, version: String?, author: String?): String =
        buildPath(getEnvOrElse(XDG_DATA_DIRS) { buildPath("/usr", "locale", "share") }, author, name, version)

    override fun getSiteConfigDir(name: String, version: String?, author: String?): String  =
        buildPath(getEnvOrElse(XDG_CONFIG_DIRS) { buildPath("/etc", "xdg") })
}