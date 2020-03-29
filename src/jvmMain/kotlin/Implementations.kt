package fr.pottime.appdirs

import com.sun.jna.platform.win32.Guid
import com.sun.jna.platform.win32.KnownFolders
import com.sun.jna.platform.win32.Shell32Util
import com.sun.jna.platform.win32.ShlObj
import java.io.File

internal object MacOsXAppDirs : AppDirs {
    private val home = System.getProperty("user.home")!!

    override fun getUserDataDir(name: String, version: String?, author: String?, roaming: Boolean) =
            buildPath(home, "Library", "Application Support", author, name, version)

    override fun getUserConfigDir(name: String, version: String?, author: String?, roaming: Boolean) =
            buildPath(home, "Library", "Application Support", author, name, version)

    override fun getUserCacheDir(name: String, version: String?, author: String?) =
            buildPath(home, "Library", "Caches", author, name, version)

    override fun getUserLogDir(name: String, version: String?, author: String?) =
            buildPath(home, "Library", "Logs", author, name, version)

    override fun getSiteDataDir(name: String, version: String?, author: String?) =
            buildPath("Library", "Application Support", root = true)

    override fun getSiteConfigDir(name: String, version: String?, author: String?) =
            buildPath("Library", "Application Support", root = true)

    private fun buildPath(vararg paths: String?, root: Boolean = false) =
            paths.filterNotNull().joinToString(File.separator!!, if (root) File.separator!! else "")
}

internal object NixAppDirs : AppDirs {
    private const val XDG_CONFIG_DIRS = "XDG_CONFIG_DIRS"
    private const val XDG_DATA_DIRS = "XDG_DATA_DIRS"
    private const val XDG_CACHE_HOME = "XDG_CACHE_HOME"
    private const val XDG_CONFIG_HOME = "XDG_CONFIG_HOME"
    private const val XDG_DATA_HOME = "XDG_DATA_HOME"

    private val environment: Map<String, String> = System.getenv()!!.filter { it.value != null }
    private val home by lazy { System.getProperty("user.home")!! }


    override fun getUserDataDir(name: String, version: String?, author: String?, roaming: Boolean) =
            buildPath(environment.getOrElse(XDG_DATA_HOME) { buildPath(home, ".local", "share") }, author, name, version)

    override fun getUserConfigDir(name: String, version: String?, author: String?, roaming: Boolean) =
            buildPath(environment.getOrElse(XDG_CONFIG_HOME) { buildPath(home, ".config") }, author, name, version)

    override fun getUserCacheDir(name: String, version: String?, author: String?) =
            buildPath(environment.getOrElse(XDG_CACHE_HOME) { buildPath(home, ".cache") }, author, name, version)

    override fun getUserLogDir(name: String, version: String?, author: String?) =
            buildPath(environment.getOrElse(XDG_CACHE_HOME) { buildPath(home, ".cache") }, author, name, version, "logs")

    override fun getSiteDataDir(name: String, version: String?, author: String?) =
            buildPath(environment.getOrElse(XDG_DATA_DIRS) { buildPath("usr", "locale", "share") }, author, name, version)

    override fun getSiteConfigDir(name: String, version: String?, author: String?) =
            buildPath(environment.getOrElse(XDG_CONFIG_DIRS) { buildPath("/etc/xdg") }, author, name, version)

    private fun buildPath(vararg paths: String?) = paths.filterNotNull().joinToString(File.separator!!)
}

internal object WindowsAppDirs : AppDirs {
    private val appdata = resolveDirectory(DirectoryId.APPDATA)
    private val localAppdata = resolveDirectory(DirectoryId.LOCAL_APPDATA)
    private val programData = resolveDirectory(DirectoryId.PROGRAM_DATA)

    override fun getUserDataDir(name: String, version: String?, author: String?, roaming: Boolean) =
            buildPath(if (roaming) appdata else localAppdata, author, name, version)

    override fun getUserConfigDir(name: String, version: String?, author: String?, roaming: Boolean) =
            buildPath(if (roaming) appdata else localAppdata, author, name, version)

    override fun getUserCacheDir(name: String, version: String?, author: String?) =
            buildPath(localAppdata, author, name, version, "caches")

    override fun getUserLogDir(name: String, version: String?, author: String?) =
            buildPath(localAppdata, author, name, version, "logs")

    override fun getSiteDataDir(name: String, version: String?, author: String?) =
            buildPath(programData, author, name, version)

    override fun getSiteConfigDir(name: String, version: String?, author: String?) =
            buildPath(programData, author, name, version)

    private fun buildPath(vararg paths: String?) = paths.filterNotNull().joinToString(File.separator!!)

    //region Directory resolver
    private enum class DirectoryId(val guid: Guid.GUID, val csidl: Int) {
        APPDATA(KnownFolders.FOLDERID_RoamingAppData!!, ShlObj.CSIDL_APPDATA),
        LOCAL_APPDATA(KnownFolders.FOLDERID_LocalAppData, ShlObj.CSIDL_LOCAL_APPDATA),
        PROGRAM_DATA(KnownFolders.FOLDERID_ProgramData, ShlObj.CSIDL_COMMON_APPDATA)
    }

    private fun resolveDirectory(directory: DirectoryId): String = try {
        Shell32Util.getKnownFolderPath(directory.guid)
    } catch (e: UnsatisfiedLinkError) {
        // Pre-vista
        Shell32Util.getFolderPath(directory.csidl)
    }
    //endregion
}

