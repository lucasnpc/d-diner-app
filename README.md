# DDiner App

## About the App

Ordering App is an application aimed for Waiters and Waitresses with the intention of annotating orders, follow up the order delivery
and see a compilation of completed order in a list
It's main features are:
* Orders delivery
* Place Orders
* See orders info

## Development Methodology
* Test Driven Development
* Offline Caching (With Firestore API)
* Connection with Firestore Database
* Reactive programming
* Dependency Injection
* Singletons
* Dependency Inversion Technique

## Basic Instructions to Build
````
1. Copy and paste the google-services.json file at app/ directory
2. Synchonize dependencies and execute the App
3. use the Login User: lucas@teste.com password: 123456 to enter the App
````

## Built With

* [Room](https://developer.android.com/jetpack/androidx/releases/room?hl=pt-br) - A persistence library that provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite.
* [FirebaseUI Authentication](https://github.com/firebase/FirebaseUI-Android/blob/master/auth/README.md) - FirebaseUI provides a drop-in auth solution that handles the UI flows for signing
* [Hilt](https://dagger.dev/hilt/) - Hilt provides a standard way to incorporate Dagger dependency injection into an Android application.
* [Jetpack Compose](https://developer.android.com/jetpack/compose) - Jetpack Compose is Android’s recommended modern toolkit for building native UI.
* [Github Actions](https://docs.github.com/en/actions) - GitHub Actions is a feature of GitHub that allows you to automate, customize, and execute your software development workflows right in your repository.
* [Kotlin Symbol Processing (KSP)](https://kotlinlang.org/docs/ksp-overview.html) - Kotlin Symbol Processing (KSP) is an API that you can use to develop lightweight compiler plugins. Compared to kapt, annotation processors that use KSP can run up to two times faster.
