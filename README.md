# ZoomLoadView

This widget can zoom image , don't make image distort.

这个控件可以保持图片的比例去填充view,如果按图片比例计算出的大小超出view的大小,会展示图片的中心对称的中心区域,再以控件中心慢慢放大.

**Usage**

```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".activity.ZoomViewTestActivity">

    <com.xf.mylab.widget.ZoomLoadView
        android:id="@+id/zoom_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:zoomDrawable="@drawable/test1" />
</RelativeLayout>

```
```
public class Test1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);
        ZoomLoadView zoomLoadView = (ZoomLoadView) findViewById(R.id.zoom_load_view);
        zoomLoadView.setEndListener(new ZoomLoadView.EndListener() {
            @Override
            public void onEndListener() {
                Log.d("ZoomLoadView", "ani end");
            }
        });
    }
}
```



**Attrs**

you can custom some params in xml
```
    <declare-styleable name="ZoomLoadView">
        <attr name="animatorDuration" format="integer" />
        <!--如果自定义建议该值不要超过0.5f-->
        <attr name="zoomScale" format="float" />
        <attr name="zoomDrawable" format="reference" />
    </declare-styleable>
```

**Gif**

Test1 origin image

![Alt Text](https://github.com/X-FAN/resource/blob/master/gif/test1.png)

Test2 origin image

![Alt Text](https://github.com/X-FAN/resource/blob/master/gif/test2.png)

![Alt Text](https://github.com/X-FAN/resource/blob/master/gif/zoomloadview.gif)
