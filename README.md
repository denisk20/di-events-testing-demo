# Demo project for presentation "Modern Android Projects: dependency injection, events, testing".
This is an example of so-called "modern" android project which is used in this presentation:

http://slides.com/deniskniazhev/modern-android-projects#/

The application is a simple weather report which is capable of fetching weather for 2 Ukrainian cities: Kharkiv and Lviv.

![image](https://cloud.githubusercontent.com/assets/3080318/9034351/9555d8a8-39d7-11e5-8254-7a91d99a6f21.png)

Technologies used:

 * [Dagger 1](http://square.github.io/dagger/) for dependency injection
 * [Otto](http://square.github.io/otto/) for event handling
 * [Espresso 2](https://code.google.com/p/android-test-kit/) for testing
 * (bonus) [Volley](https://developer.android.com/training/volley/index.html) for networking
 * (bonus) [Butterknife](http://jakewharton.github.io/butterknife/) for view injection

# Building and running
As with any android gradle project, after cloning create a file named `local.properties` in project root. This file should contain a path to your Android SDK:

    sdk.dir=/Users/dkniaz/Downloads/android-sdk-macosx

After doing this you should be able to run the project in Android Studio (you'll need 1.3 or higher).

Unit and Espresso tests can also be run from Android Studio, just make sure you have selected the correct testing profile:

![image](https://cloud.githubusercontent.com/assets/3080318/9034292/2d623fde-39d7-11e5-9d02-9b64b716e295.png)
