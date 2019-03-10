# Location-Tracker
Location-Tracker is a Project for tracking location updates and send it to the server periodically.

  - You have to provide your location access to use this app
  - Tracking can be enabled and disabled by simple tap
  - Mock details are used to upload Location for now

### Tech

Location Tracker uses a number of open source projects to work properly:

* [Retrofit] - For networking
* [Koin] - Dependency Injection
* [RxKotlin] - Reactive programming
* [Play Services] - Fused API for tracking location

### Installation

Location Tracker requires [Android Studio](https://developer.android.com/studio/install) v3+ to run. Open the project in Android Studio, sync the gradle file and run the project.

### Todos

 - Improvements required in battery optimisation
 - Location upload API configuration is missing, since it's not available
 - WorkManager can be used to optimised for Android P

   [Retrofit]: <https://square.github.io/retrofit/>
   [Koin]: <https://insert-koin.io/>
   [RxKotlin]: <https://github.com/ReactiveX/RxKotlin>
   [Play Services]: <https://developers.google.com/android/guides/setup>
   [Twitter Bootstrap]: <https://developer.android.com/studio/install>