# Prosciutto-Mob

## Running the App

The following environment variable must be set:
* $SUPPLIER_STAGING_URL

Most commonly used command-line tasks are:

    $./gradlew connectedDevDebugAndroidTest
    $./gradlew testDevDebugUnitTest
    $./gradlew assembleDebug
    $./gradlew assembleRelease
    $./gradlew tasks

## User Stories

### Request [Salume](https://github.com/rafaelfiume/Salume) Advice Based on Customer Profile (Parent Story)
* ~~Request advice happy path~~
* ~~Request advice sad path~~
* ~~Improve UI displaying product price~~
* Improve UI displaying product details when clicking on an item in the list

## Most Interesting Thing Happening Here...

The two end-to-end black-box tests describing (and testing) the Request Advice Based on Customer Profile: 
[AdviserEndToEndHappyPathTest](https://github.com/rafaelfiume/Prosciutto-Mob/blob/master/app/src/androidTest/java/com/rafaelfiume/prosciutto/adviser/test/AdviserEndToEndHappyPathTest.java) 
and [AdviserEndToEndSadPathTest](https://github.com/rafaelfiume/Prosciutto-Mob/blob/master/app/src/androidTest/java/com/rafaelfiume/prosciutto/adviser/test/AdviserEndToEndSadPathTest.java)
