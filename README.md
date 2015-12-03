# Prosciutto-Mob

## Running the App

The following environment variable must be set:
* $SUPPLIER_STAGING_URL

Run the tests from the command-line typing:

    $./gradlew connectedDevDebugAndroidTest
    
To just build the project use:

    $./gradlew assembleDebug

To build a release .apk:

    $./gradlew assembleRelease
    
To see tasks:

    $./gradlew tasks

## User Stories

### Request (Salume)[https://github.com/rafaelfiume/Salume] Product Advice Based on Customer Profile (Parent Story)
* ~~Request advice happy path~~
* Request advice sad path
* Improve UI displaying product price
