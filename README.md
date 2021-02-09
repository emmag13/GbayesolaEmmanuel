# Gbayesola Emmanuel
A Simple list filter and the filtered list of car owners.

[![Kotlin Version](https://img.shields.io/badge/Kotlin-1.4.30-blue.svg)](https://kotlinlang.org)
[![AGP](https://img.shields.io/badge/AGP-4.1.2-blue?style=flat)](https://developer.android.com/studio/releases/gradle-plugin)
[![Gradle](https://img.shields.io/badge/Gradle-6.5.1-blue?style=flat)](https://gradle.org)


##The application project is made of two (2) modules. They are:
* `app`
* `restapi`

## Tech-stack
Min API level is set to [`21`](https://android-arsenal.com/api?level=21), so the presented approach is suitable for over
[85% of devices](https://developer.android.com/about/dashboards) running Android. This project takes advantage of many
popular libraries and tools of the Android ecosystem. Most of the libraries are in the stable version unless there is a
good reason to use non-stable dependency.

## Caching and Offline

## Getting started

There are a few ways to open this project.

### Android Studio

1. `Android Studio` -> `File` -> `New` -> `From Version control` -> `Git`
2. Enter `https://github.com/emmag13/GbayesolaEmmanuel.git` into URL field
3. Provide `productionApiBaseUrl="<value goes here>"` in the `gradle.properties` for Release
4. Provide `dev.api.base.url="<value goes here>"` in the `local.properties` for Debug

### Command-line + Android Studio
1. Run `git clone https://github.com/emmag13/GbayesolaEmmanuel.git` to clone project
2. Go to `Android Studio` -> `File` -> `Open` and select cloned directory
3. Provide `productionApiBaseUrl="<value goes here>"` in the `gradle.properties` for Release
4. Provide `dev.api.base.url="<value goes here>"` in the `local.properties` for Debug
