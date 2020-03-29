plugins {
    kotlin("multiplatform") version "1.3.71"
}

group = "fr.pottime.appdirs"
version = "2.0-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
}

kotlin {
    jvm()
    linuxX64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib"))
                implementation("net.java.dev.jna:jna-platform:5.5.0")
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
    }
}