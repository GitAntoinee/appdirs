package fr.pottime.appdirs

import kotlin.test.Test

class Tests {
    private val appDirs get() = AppDirs

    @Test
    fun test() {
        val programName = "hello-world"
        val programVersion = "1.0-SNAPSHOT"
        val programAuthor = "GitAntoinee"

        println("Using $programName, $programVersion, $programAuthor")
        println(appDirs.getUserDataDir(programName, programVersion, programAuthor))
        println(appDirs.getUserConfigDir(programName, programVersion, programAuthor))
        println(appDirs.getUserCacheDir(programName, programVersion, programAuthor))
        println(appDirs.getUserLogDir(programName, programVersion, programAuthor))
        println(appDirs.getSiteConfigDir(programName, programVersion, programAuthor))
        println(appDirs.getSiteDataDir(programName, programVersion, programAuthor))
    }
}