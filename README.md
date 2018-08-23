# BlurDrawerLayout
DrawerLayout with blur functionality like iOS

 [ ![Download](https://api.bintray.com/packages/balusangem/BlurDrawerLayout/BlurDrawerLayout/images/download.svg) ](https://bintray.com/balusangem/BlurDrawerLayout/BlurDrawerLayout/_latestVersion)
 
![alt text](https://github.com/BALUSANGEM/BlurDrawerLayout/blob/master/BlurScreenShot1.png)
![alt text](https://github.com/BALUSANGEM/BlurDrawerLayout/blob/master/BlurScreenshot2.png)
### Requirements
Kotlin, Android version >= 17

### Adding to Project

Set up renderscript
```
  defaultConfig {
    renderscriptTargetApi 19
    renderscriptSupportModeEnabled true
  }
```

BlurDrawerLayout usage
```
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
