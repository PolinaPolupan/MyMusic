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
include(":core:designSystem")
include(":feature")
include(":feature:home")
include(":feature:account")
include(":feature:addToPlaylist")
include(":feature:album")
include(":feature:library")
include(":feature:login")
include(":feature:player")
include(":feature:playlist")
include(":feature:search")
include(":core:testing")
