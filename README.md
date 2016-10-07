# Prosciutto-Mob [![Apache 2.0 License](https://img.shields.io/badge/license-Apache_2.0-blue.svg)](https://github.com/rafaelfiume/Prosciutto-Mob/blob/master/LICENSE)

## Most Interesting Things Happening Here...

* The end-to-end black-box tests describing (and testing) the Request Advice Based on Customer Profile, specially: 
[AdviserEndToEndHappyPathTest](https://github.com/rafaelfiume/Prosciutto-Mob/blob/master/app/src/androidTest/java/com/rafaelfiume/prosciutto/adviser/test/AdviserEndToEndHappyPathTest.java) 
and [ShowAdvisedProductDetailsTest.java](https://github.com/rafaelfiume/Prosciutto-Mob/blob/master/app/src/androidTest/java/com/rafaelfiume/prosciutto/adviser/test/ShowAdvisedProductDetailsTest.java)

* The use of CollapsingToolbarLayout in the product details view.

## User Stories

### Request <a href="http://rafaelfiume.github.io/Salume/com/rafaelfiume/salume/acceptance/adviser/AdviseProductBasedOnCustomerProfileEndToEndTest.html" target="blank">Advice Based on Customer Profile</a> (Parent Story)
* ~~Request advice happy path~~
* ~~Request advice sad path~~
* ~~Improve UI displaying product details when clicking on an item in the list~~
* ~~Improve UI displaying a illustrative picture of the product in the Details View~~
* ~~Show a picture and description of the product according to the product variety~~ 

Todo: Consider to use fragments to compose the view and [Dagger](http://google.github.io/dagger/) for dependency injection. 

## Running the App

The following environment variable must be set:
* $SUPPLIER_STAGING_URL when running the app in staging mode.

Most commonly used command-line tasks are:

    $./gradlew connectedDevDebugAndroidTest
    $./gradlew testDevDebugUnitTest
    $./gradlew assembleDebug
    $./gradlew assembleRelease
    $./gradlew tasks
    $./gradlew test --continue
