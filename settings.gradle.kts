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
include(":feature")
include(":core:auth")
include(":core:common")
include(":core:data")
include(":core:database")
include(":core:datastore")
include(":core:designsystem")
include(":core:model")
include(":core:network")
include(":core:testing")
include(":feature:account")
include(":feature:addtoplaylist")
include(":feature:album")
include(":feature:home")
include(":feature:library")
include(":feature:login")
include(":feature:player")
include(":feature:playlist")
include(":feature:search")
include(":sync")
