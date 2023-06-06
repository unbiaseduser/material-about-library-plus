plugins {
    id("com.android.library")
    id("maven-publish")
}

android {
    compileSdk = 33

    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }

    defaultConfig {
        minSdk = 15
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles += getDefaultProguardFile("proguard-android-optimize.txt")
            proguardFiles += file("proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        viewBinding = true
        buildConfig = false
    }

    namespace = "com.sixtyninefourtwenty.materialaboutlibrary"
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "com.sixtyninefourtwenty"
            artifactId = "material-about-library-plus"
            version = "1.0"

            afterEvaluate {
                from(components["release"])
            }

            pom {
                name.set("material-about-library-plus")
                description.set("Fork of material-about-library with some cleanup")

                licenses {
                    license {
                        name.set("The Apache Software License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }

                developers {
                    developer {
                        id.set("unbiaseduser")
                        name.set("Dang Quang Trung")
                        email.set("quangtrung02hn16@gmail.com")
                        url.set("https://github.com/unbiaseduser")
                    }
                }
            }
        }
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.core)
    implementation(libs.androidx.lifecycle.runtime.ktx)
}
