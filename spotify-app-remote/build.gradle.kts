plugins {
    base //allows IDE clean to trigger clean on this module too
}

configurations.maybeCreate("default")
artifacts.add("default", file("spotify-app-remote-release-0.8.0.aar"))

//Change group to whatever you want. Here I'm using the package from the aar that I'm importing from
group = "com.spotify.android"
version = "0.8.0"