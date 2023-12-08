pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
           }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven { setUrl("https://jitpack.io")  }
        google()
        mavenCentral()
        //noinspection JcenterRepositoryObsolete
        jcenter()
    }
}

rootProject.name = "DA1_QLDH_YUII"
include(":app")
