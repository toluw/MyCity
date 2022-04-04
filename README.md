# MyCity
Search for cities in the world and view on map

The app uses the android paging3 library to display paged data from Room db to the UI and back feed the data from [Api](http://connect-demo.mobile1.io/square1/connect/v1/city).

## Architecture
- MVVM
- Hilt Dependency Injection
- Retrofit
- Coroutines
- Paging3
- Room

## System Requirements
- Android Studio Arctic Fox or Higher
- Android Gradle Plugin 7.0 or Higher

## Setup Instructions
1. Clone the repository 
2. Get an Api Key for Android Map SDK. [See Guidelines](https://developers.google.com/maps/documentation/android-sdk/get-api-key)
3. Open the local.properties in your project level directory, and then add the following code. Replace YOUR_API_KEY with your API key. 
- MAPS_API_KEY=YOUR_API_KEY
