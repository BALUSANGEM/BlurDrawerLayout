# BlurDrawerLayout
DrawerLayout with blur functionality like iOS
 
 [ ![Download](https://api.bintray.com/packages/balusangem/maven/blurdrawerlayout/images/download.svg) ](https://bintray.com/balusangem/maven/blurdrawerlayout/_latestVersion)
 [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-BlurDrawerLayout-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/7080)
 
![Alt Text](https://github.com/BALUSANGEM/BlurDrawerLayout/blob/master/blurleft.gif)
![alt text](https://github.com/BALUSANGEM/BlurDrawerLayout/blob/master/BlurScreenShot1.png)
![alt text](https://github.com/BALUSANGEM/BlurDrawerLayout/blob/master/BlurScreenshot2.png)

### Requirements
Kotlin, Android version >= 17

## Adding to Project


#### Add dependencies in gradle

```groovy
 repositories {
  jcenter()
 }
 
 //dependency
 implementation 'org.nosort.blurdrawerlayout:blurdrawerlayout:1.1.0'
 
```

#### Set up renderscript

```groovy
  defaultConfig {
    renderscriptTargetApi 19
    renderscriptSupportModeEnabled true
  }
```


#### Simple usage

```xml
<?xml version="1.0" encoding="utf-8"?>
<org.nosort.blurdrawerlayout.BlurDrawerLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/blurDrawerLayout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:fitsSystemWindows="true"
    tools:context=".MainActivity">

    <FrameLayout
        android:id="@+id/contentLayout"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical" />

    <LinearLayout
        android:id="@+id/menuLayout"
        android:layout_width="200dp"
        android:layout_height="match_parent"
        android:layout_gravity="start"
        android:gravity="center_horizontal"
        android:orientation="vertical">
    </LinearLayout>

</org.nosort.blurdrawerlayout.BlurDrawerLayout>
```



#### Works with DrawerLayout child Gravity RIGHT

![alt text](https://github.com/BALUSANGEM/BlurDrawerLayout/blob/master/blurscreenshot3.png)
![alt text](https://github.com/BALUSANGEM/BlurDrawerLayout/blob/master/blurscreenshot4.png)



