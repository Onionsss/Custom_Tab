# Custom_Tab
自定义控件 tab 支持各种小图案

xml:
```Java
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    <onionsss.it.mytab.MyTab
        android:id="@+id/indicator"
        android:layout_width="match_parent"
        android:background="#000000"
        app:shape="none"
        app:textsize_d="@dimen/x7"
        app:textcolor_n="#ffffff"
        app:textcolor_l="@color/colorAccent"
        app:bottomColor="@color/colorAccent"
        app:visibleSize="4"
        android:layout_height="wrap_content"></onionsss.it.mytab.MyTab>
    <android.support.v4.view.ViewPager
        android:id="@+id/vp"
        android:layout_width="match_parent"
        android:layout_height="match_parent"></android.support.v4.view.ViewPager>
</LinearLayout>
```
Java:
```Java
  mVp.setAdapter(myAdapter);
  //设置标题
 mIndicator.setTitles(list);
 //关联viewPager
mIndicator.attachViewPager(mVp);
```
![image](https://github.com/Onionsss/Custom_Tab/blob/master/imgae/r.gif)
![image](https://github.com/Onionsss/Custom_Tab/blob/master/imgae/none.gif)
![image](https://github.com/Onionsss/Custom_Tab/blob/master/imgae/san.gif)
![image](https://github.com/Onionsss/Custom_Tab/blob/master/imgae/tab.gif)
