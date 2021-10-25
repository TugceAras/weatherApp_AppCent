# <p align="center"> :sunny: **WeatherApp** :cloud:
## :dart: **The subject of the project**
#### An application that displays the weather in that region on a daily and weekly basis according to the users location information using the metaWeather API.

- metaWeather api link : <https://www.metaweather.com>

## :point_right: **If we look at the application in general**
- Use of google maps
- Getting weather related data from metaWeather api
- Operations such as internet control were carried out.
- Language : Kotlin

## :open_file_folder: **Manifest file**

``` xml
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
```

## :open_file_folder: **Build.gradle**

```xml 

 dataBinding {
        enabled = true
    }
    
 buildFeatures {
        viewBinding true
    }

```

```xml

 implementation 'com.google.android.gms:play-services-maps:17.0.1'
 implementation 'com.google.android.gms:play-services-location:18.0.0'
 implementation 'com.squareup.retrofit2:retrofit:2.9.0'
 implementation 'com.squareup.retrofit2:converter-gson:2.9.0'

```
