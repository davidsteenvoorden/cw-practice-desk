#!/bin/sh
# Builds the Android app and copies it to ./CWPracticeDesk.apk
set -e
cd "$(dirname "$0")/android"
JAVA_HOME="${JAVA_HOME:-/opt/android-studio/jbr}" ANDROID_HOME="${ANDROID_HOME:-$HOME/Android/Sdk}" ./gradlew -q :app:assembleRelease
cp app/build/outputs/apk/release/app-release.apk ../CWPracticeDesk.apk
echo "Built $(pwd)/../CWPracticeDesk.apk"
