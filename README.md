# Prosciutto-Mob [![Apache 2.0 License](https://img.shields.io/badge/license-Apache_2.0-blue.svg)](https://github.com/rafaelfiume/Prosciutto-Mob/blob/master/LICENSE)

## Running the App

The following environment variable must be set:
* $SUPPLIER_STAGING_URL

Most commonly used command-line tasks are:

    $./gradlew connectedDevDebugAndroidTest
    $./gradlew testDevDebugUnitTest
    $./gradlew assembleDebug
    $./gradlew assembleRelease
    $./gradlew tasks
    $./gradlew test --continue

## User Stories

### Request [Salume](https://github.com/rafaelfiume/Salume) Advice Based on Customer Profile (Parent Story)
* ~~Request advice happy path~~
* ~~Request advice sad path~~
* ~~Improve UI displaying product price~~
* ~~Improve UI displaying product details when clicking on an item in the list~~
* ~~Improve UI displaying a illustrative picture of the product in the Details View~~
* Displays other suggested products in the Details View so user doesn't need to switch views back and forth to see other product details

## Most Interesting Things Happening Here...

* The two end-to-end black-box tests describing (and testing) the Request Advice Based on Customer Profile: 
[AdviserEndToEndHappyPathTest](https://github.com/rafaelfiume/Prosciutto-Mob/blob/master/app/src/androidTest/java/com/rafaelfiume/prosciutto/adviser/test/AdviserEndToEndHappyPathTest.java) 
and [AdviserEndToEndSadPathTest](https://github.com/rafaelfiume/Prosciutto-Mob/blob/master/app/src/androidTest/java/com/rafaelfiume/prosciutto/adviser/test/AdviserEndToEndSadPathTest.java)

* The use of CollapsingToolbarLayout in the product details view.
