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
        google()
        mavenCentral()
        maven ("https://jitpack.io" )
    }
}

rootProject.name = "My Music"
include(":app")
include(":core")
include(":core:model")
include(":core:data")
include(":core:database")
include(":core:network")
include(":core:auth")
include(":core:common")
include(":sync")
include(":core:datastore")
