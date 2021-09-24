package pub.whitefancy.androiddemo;

public class ScreenAdaption {
}
//由于Android系统的开放性，任何用户、开发者、OEM厂商、运营商都可以对Android进行定制，于是导致：
//Android系统碎片化：小米定制的MIUI、魅族定制的flyme、华为定制的EMUI等等
//当然都是基于Google原生系统定制的
//Android机型屏幕尺寸碎片化：5寸、5.5寸、6寸等等
//Android屏幕分辨率碎片化：320x480、480x800、720x1280、1080x1920
//据友盟指数显示，统计至2015年12月，支持Android的设备共有27796种
//当Android系统、屏幕尺寸、屏幕密度出现碎片化的时候，就很容易出现同一元素在不同手机上显示不同的问题。
//适应更长的屏幕
//现在的手机屏幕高宽比例越来越大，我们还需要额外做一下适配才能使应用在所有手机上都能全屏显示，具体方式有两种：
//方式一：在AndroidManifest.xml中配置支持最大高宽比
//<meta-data android:name="android.max_aspect"
//android:value="ratio_float" />
//或者
//android:maxAspectRatio="ratio_float" （API LEVEL 26）
//复制代码说明：以上两种接口可以二选一，ratio_float = 屏幕高 / 屏幕宽 （如oppo新机型屏幕分辨率为2280 x 1080， ratio_float = 2280 / 1080 = 2.11，建议设置 ratio_float为2.2或者更大）
//声明最大长宽比
//其中第一点是所有应用都需要适配的，对应下文的声明最大长宽比。
//以前的普通屏长宽比为16：9，全面屏手机的屏幕长宽比增大了很多，如果不适配的话就会类似下面这样：黑色区域为未利用的区域。
//适配方式有两种：
//1. 将targetSdkVersion版本设置到API 24及以上
//这个操作将会为<application> 标签隐式添加一个属性，android:resizeableActivity="true", 该属性的作用后面将详细说明。
//2. 在 <application> 标签中增加属性：android:resizeableActivity="false"
//同时在节点下增加一个meta-data标签：
//<!-- Render on full screen up to screen aspect ratio of 2.4 -->
//<!-- Use a letterbox on screens larger than 2.4 -->
//<meta-data android:name="android.max_aspect" android:value="2.4" />
//在 Android 7.0（API 级别 24）或更高版本的应用，android:resizeableActivity属性默认为true（对应适配方式1）。这个属性是控制多窗口显示的，决定当前的应用或者Activity是否支持多窗口。
//多窗口支持
//在清单的<activity>或 <application>节点中设置该属性，启用或禁用多窗口显示：
//android:resizeableActivity=["true" | "false"]
//如果该属性设置为 true，Activity 将能以分屏和自由形状模式启动。 如果此属性设置为 false，Activity 将不支持多窗口模式。 如果该值为 false，且用户尝试在多窗口模式下启动 Activity，该 Activity 将全屏显示。
//适配方式2即为设置屏幕的最大长宽比，这是官方提供的设置方式。
//如果设置了最大长宽比，必须android:resizeableActivity="false"。 否则最大长宽比没有任何作用。
//方式二：在AndroidManifest.xml中配置支持分屏，注意验证分屏下界面兼容性
//android:resizeableActivity="true"
//复制代码也可以通过设置targetSdkVersion>=24（即Android 7.0）,该属性的值会默认为true，就不需要在AndroidManifest.xml中配置了。
//运行之后，我们发现状态栏的部分留出了一条黑边，看上起很奇怪，这显然不是我们想要的效果。
//通过文档可以看出从Android7.0开始,应用的多窗口模式默认变为启动,在多窗口模式下,默认已经进行了全面屏适配,如果我们不想应用在多窗口模式下运行,可以修改以下属性:
//1
//android:resizeableActivity="false"
//此时,我们可以手动进行设置最大屏幕宽高比:
//android8.0及以上:
//1
//2
//3
//<activity android:maxAspectRatio="2.4">
//...
//</activity>
//android7.1及以下版本:
//1
//<meta-data android:name="android.max_aspect" android:value="2.4" />
//需要注意的是,如果手动进行设置了最大宽高比,一定要将多窗口模式设置为false,否则不生效.
//适配异形屏
//防止内容被刘海遮挡
//如果应用本身不需要全屏显示或使用沉浸式状态栏，是不需要适配的。需要获取刘海的位置和宽高，然后将显示内容避开即可。
//两种屏幕都可以统称为刘海屏，不过对于右侧较小的刘海，业界一般称为水滴屏或美人尖。为便于说明，后文提到的「刘海屏」「刘海区」都同时指代上图两种屏幕。

//Android9.0及以上适配
//Android P（9.0）开始，官方提供了适配异形屏的方式。
//通过全新的 DisplayCutout 类，可以确定非功能区域的位置和形状，这些区域不应显示内容。 要确定这些凹口屏幕区域是否存在及其位置，请使用 getDisplayCutout() 函数。
//1. 全新的窗口布局属性 layoutInDisplayCutoutMode 让您的应用可以为设备凹口屏幕周围的内容进行布局。 您可以将此属性设为下列值之一：
//* `LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT`
//* `LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES`
//* `LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER`
//2. 您可以按如下方法在任何运行 Android P 的设备或模拟器上模拟屏幕缺口：
//1. 启用开发者选项。
//3. 在 Developer options 屏幕中，向下滚动至 Drawing 部分并选择 Simulate a display with a cutout。
//3. 选择凹口屏幕的大小。
//异形屏
//全屏适配刘海屏页面
//通过查看WindowManager的源码可以看到共有3种显示模式,:
////默认情况,全屏页面不可用刘海区域,非全屏页面可以进行使用
//public static final int LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT = 0;
////不允许使用刘海区域
//public static final int LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER = 2;
////允许页面延伸到刘海区域
//public static final int LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES = 1;
//过查看上面的文档,则可以进行全屏界面的适配:
//var lp = window.attributes
//lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
//window.attributes = lp
//通过全新的 DisplayCutout 类，可以确定非功能区域的位置和形状，这些区域不应显示内容。 要确定这些凹口屏幕区域是否存在及其位置，请使用 getDisplayCutout() 函数。
//全新的窗口布局属性 layoutInDisplayCutoutMode 让您的应用可以为设备凹口屏幕周围的内容进行布局。 您可以将此属性设为下列值之一：
//LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT
//LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
//LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER
//默认值是LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT，刘海区域不会显示内容，需要将值设置为LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
//您可以按如下方法在任何运行 Android P 的设备或模拟器上模拟屏幕缺口：
//启用开发者选项。
//在 Developer options 屏幕中，向下滚动至 Drawing 部分并选择 Simulate a display with a cutout。
//选择凹口屏幕的大小。
//适配参考：
//// 延伸显示区域到刘海
//WindowManager.LayoutParams lp = window.getAttributes();
//lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
//window.setAttributes(lp);
//// 设置页面全屏显示
//final View decorView = window.getDecorView();
//decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);其中延伸显示区域到刘海的代码，也可以通过修改Activity或应用的style实现，例如：
//<?xml version="1.0" encoding="utf-8"?>
//<resources>
//<style name="AppTheme" parent="xxx">
//<item name="android:windowLayoutInDisplayCutoutMode">shortEdges</item>
//</style>
//</resources>
//Android O 适配
//沉浸式风格。
//需要将状态栏设置为透明，需要注意只有在Android 4.4（API Level 19）以上才支持设置透明状态栏。有两种设置方法：
//方法一：为Activity设置style，添加一个属性：
//<item name="android:windowTranslucentStatus">true</item>
//方法二：在Activity的onCreate()中为Window添加Flag
//public class ImmersiveActivity extends AppCompatActivity {
//@Override
//protected void onCreate(@Nullable Bundle savedInstanceState) {
//super.onCreate(savedInstanceState);
//setContentView(R.layout.activity_immersive);
//// 透明状态栏
//if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//getWindow().addFlags(
//WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//}
//}
//}
//全屏风格，状态栏不可见。
//同样有两种设置方法：
//方法一：为Activity设置style，添加属性：
//<item name="android:windowFullscreen">true</item>
//<!-- 这里为了简单，直接从style中指定一个背景 -->
//<item name="android:windowBackground">@mipmap/bg</item>
//方法二：在Activity的OnCreate()中添加代码：
//public class FullScreenActivity extends AppCompatActivity {
//@Override
//protected void onCreate(@Nullable Bundle savedInstanceState) {
//super.onCreate(savedInstanceState);
//// 全屏显示
//getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN |
//View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//}
//}
//为了手动适配异形屏，让状态栏剩余区域更为美观，想优化下显示。
//一开始先设置
//getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | SYSTEM_UI_FLAG_FULLSCREEN);
//WindowManager.LayoutParams lp = getWindow().getAttributes();
//lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
//getWindow().setAttributes(lp);
//但是没有作用。
//后来在布局文件中，
//<?xml version="1.0" encoding="utf-8"?>
//<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
//xmlns:app="http://schemas.android.com/apk/res-auto"
//xmlns:tools="http://schemas.android.com/tools"
//android:layout_width="match_parent"
//android:layout_height="match_parent"
//android:background="#00000000"
//android:fitsSystemWindows="true"
//tools:context=".FocusingActivity">
//<......>
//</androidx.constraintlayout.widget.ConstraintLayout>
//发现android:fitsSystemWindows="true"这句话很可疑
//后遂删除，改之为android:fitsSystemWindows="false"，过
//这是其他文章里对这个属性的介绍：
//fitsSystemWindows属性可以让view根据系统窗口来调整自己的布局；简单点说就是我们在设置应用布局时是否考虑系统窗口布局，这里系统窗口包括系统状态栏、导航栏、输入法等，包括一些手机系统带有的底部虚拟按键。
//也就是说，设置android:fitsSystemWindows="true"后，系统会给某些view自动加上padding，在状态栏下方的View则自动添加等同于状态栏高度的paddingTop，虚拟键上方的view自动添加等同于虚拟键高度的paddingBottom。既然我们要手动适配异形屏效果，自然就要关闭这一属性了。
//private void setScreen(){
//if (Build.VERSION.SDK_INT >= 28) {
//WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
//lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
//mActivity.getWindow().setAttributes(lp);
//}
//}
//“布局”匹配
//本质1：使得布局元素自适应屏幕尺寸
//做法
//使用相对布局（RelativeLayout），禁用绝对布局（AbsoluteLayout）
//开发中，我们使用的布局一般有：
//线性布局（Linearlayout）
//相对布局（RelativeLayout）
//帧布局（FrameLayout）
//绝对布局（AbsoluteLayout）
//由于绝对布局（AbsoluteLayout）适配性极差，所以极少使用。
//对于线性布局（Linearlayout）、相对布局（RelativeLayout）和帧布局（FrameLayout）需要根据需求进行选择，但要记住：
//RelativeLayout布局的子控件之间使用相对位置的方式排列，因为RelativeLayout讲究的是相对位置，即使屏幕的大小改变，视图之前的相对位置都不会变化，与屏幕大小无关，灵活性很强
//LinearLayout通过多层嵌套LinearLayout和组合使用"wrap_content"和"match_parent"已经可以构建出足够复杂的布局。但是LinearLayout无法准确地控制子视图之间的位置关系，只能简单的一个挨着一个地排列。
//所以，对于屏幕适配来说，使用相对布局（RelativeLayout）将会是更好的解决方案
//本质2：根据屏幕的配置来加载相应的UI布局
//应用场景：需要为不同屏幕尺寸的设备设计不同的布局
//做法：使用限定符
//作用：通过配置限定符使得程序在运行时根据当前设备的配置（屏幕尺寸）自动加载合适的布局资源
//限定符类型：
//尺寸（size）限定符
//最小宽度（Smallest-width）限定符
//布局别名
//屏幕方向（Orientation）限定符
//“布局组件”匹配
//本质：使得布局组件自适应屏幕尺寸
//做法
//使用"wrap_content"、"match_parent"和"weight“来控制视图组件的宽度和高度
//"wrap_content"
//相应视图的宽和高就会被设定成所需的最小尺寸以适应视图中的内容
//"match_parent"(在Android API 8之前叫作"fill_parent")
//视图的宽和高延伸至充满整个父布局
//"weight"
//1.定义：是线性布局（Linelayout）的一个独特比例分配属性
//2.作用：使用此属性设置权重，然后按照比例对界面进行空间的分配，公式计算是：控件宽度=控件设置宽度+剩余空间所占百分比宽幅
//具体可以参考这篇文章，讲解得非常详细
//通过使用"wrap_content"、"match_parent"和"weight"来替代硬编码的方式定义视图大小&位置，
// 你的视图要么仅仅使用了需要的那边一点空间，要么就会充满所有可用的空间，即按需占据空间大小，
// 能让你的布局元素充分适应你的屏幕尺寸支持各种屏幕尺寸

//使用wrap_content、match_parent、weight要确保布局的灵活性并适应各种尺寸的屏幕，应使用 “wrap_content” 和 “match_parent” 控制某些视图组件的宽度和高度。
//使用 “wrap_content”，系统就会将视图的宽度或高度设置成所需的最小尺寸以适应视图中的内容，而 “match_parent”（在低于 API 级别 8 的级别中称为 “fill_parent”）则会展开组件以匹配其父视图的尺寸。
//如果使用 “wrap_content” 和 “match_parent” 尺寸值而不是硬编码的尺寸，视图就会相应地仅使用自身所需的空间或展开以填满可用空间。此方法可让布局正确适应各种屏幕尺寸和屏幕方向。
//第三方库适配
//AndroidAutoSize
//  AndroidAutoSize 是基于今日头条适配方案，该开源库已经很大程度上解决了今日头条适配方案的两个缺点，可以对activity，fragment进行取消适配。也是目前我的项目中所使用的适配方案。
//使用也非常简单只需两步：
//（1）引入：
//implementation 'me.jessyan:autosize:1.1.2'
//（2）在 AndroidManifest 中填写全局设计图尺寸 (单位 dp)，如果使用副单位，则可以直接填写像素尺寸，不需要再将像素转化为 dp，详情请查看 demo-subunits
//<manifest>
//<application>
//<meta-data
//android:name="design_width_in_dp"
//android:value="360"/>
//<meta-data
//android:name="design_height_in_dp"
//android:value="640"/>
//</application>
//</manifest>
//今日头条适配（修改手机的设备密度 density）
//这套方案对老项目是不太友好的，因为修改了系统的density值之后，整个布局的实际尺寸都会发生改变，如果想要在老项目文件中使用，恐怕整个布局文件中的尺寸都可能要重新按照设计稿修改一遍才行。因此，如果你是在维护或者改造老项目，使用这套方案就要三思了。
//原理
//通过修改density值，强行把所有不同尺寸分辨率的手机的宽度dp值改成一个统一的值，这样就解决了所有的适配问题。
//比如，设计稿宽度是360px，那么开发这边就会把目标dp值设为360dp，在不同的设备中，动态修改density值，从而保证(手机像素宽度)px/density这个值始终是360dp,这样的话，就能保证UI在不同的设备上表现一致了。
//UI设计图是按屏幕宽度为360dp来设计的，那么在上述设备上，屏幕宽度其实为1080/(440/160)=392.7dp，也就是屏幕是比设计图要宽的。这种情况下， 即使使用dp也是无法在不同设备上显示为同样效果的。 同时还存在部分设备屏幕宽度不足360dp，这时就会导致按360dp宽度来开发实际显示不全。加上16:9、4:3甚至其他宽高比层出不穷，宽高比不同，显示完全一致就不可能了。
//通常下，我们只需要以宽或高一个维度去适配，比如我们Feed是上下滑动的，只需要保证在所有设备中宽的维度上显示一致即可，再比如一个不支持上下滑动的页面，那么需要保证在高这个维度上都显示一致，尤其不能存在某些设备上显示不全的情况。
//支持以宽或者高一个维度去适配，保持该维度上和设计图一致；
//支持dp和sp单位，控制迁移成本到最小。
//今日头条的适配方式，今日头条适配方案默认项目中只能以高或宽中的一个作为基准，进行适配。
//px = dp * density（dp是360dp），想要px正好是屏幕宽度的话，只能修改density。/**
//* 适配：修改设备密度
//*/
//private static float sNoncompatDensity;
//private static float sNoncompatScaledDensity;
//public static void setCustomDensity(@NonNull Activity activity, @NonNull final Application application) {
//DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();
//if (sNoncompatDensity == 0) {
//sNoncompatDensity = appDisplayMetrics.density;
//sNoncompatScaledDensity = appDisplayMetrics.scaledDensity;
//// 防止系统切换后不起作用
//application.registerComponentCallbacks(new ComponentCallbacks() {
//@Override
//public void onConfigurationChanged(Configuration newConfig) {
//if (newConfig != null && newConfig.fontScale > 0) {
//sNoncompatScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
//}
//}
//@Override
//public void onLowMemory() {
//}
//});
//}
//float targetDensity = appDisplayMetrics.widthPixels / 360;
//// 防止字体变小
//float targetScaleDensity = targetDensity * (sNoncompatScaledDensity / sNoncompatDensity);
//int targetDensityDpi = (int) (160 * targetDensity);
//appDisplayMetrics.density = targetDensity;
//appDisplayMetrics.scaledDensity = targetScaleDensity;
//appDisplayMetrics.densityDpi = targetDensityDpi;
//final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
//activityDisplayMetrics.density = targetDensity;
//activityDisplayMetrics.scaledDensity = targetScaleDensity;
//activityDisplayMetrics.densityDpi = targetDensityDpi;
//}只需要在baseActivity中添加一句话即可。适配就是这么简单。
//DisplayUtil.setCustomDensity(this, getApplication());
//缺点
//只需要修改一次 density，项目中的所有地方都会自动适配，这个看似解放了双手，减少了很多操作，但是实际上反应了一个缺点，那就是只能一刀切的将整个项目进行适配，但适配范围是不可控的。这样不是很好吗？这样本来是很好的，但是应用到这个方案是就不好了，因为我上面的原理也分析了，这个方案依赖于设计图尺寸，但是项目中的系统控件、三方库控件、等非我们项目自身设计的控件，它们的设计图尺寸并不会和我们项目自身的设计图尺寸一样。当这个适配方案不分类型，将所有控件都强行使用我们项目自身的设计图尺寸进行适配时，这时就会出现问题，当某个系统控件或三方库控件的设计图尺寸和和我们项目自身的设计图尺寸差距非常大时，这个问题就越严重。
//所以你只需要你的美工在切引导页UI的时候，做一张1440X2960的分辨率，你把他放到这个标红圈的目录下，然后就是XML里面的代码了：
//<ImageView
//android:layout_width="match_parent"
//android:layout_height="match_parent"
//android:scaleType="centerCrop"
//android:src="@mipmap/logo"/>
//各手机厂商适配方案
//部分厂商并未使用此方案,而是使用自家API进行全面屏适配,详情可以查看各手机厂商官方文档.
//OPPO:https://open.oppomobile.com/wiki/doc#id=10159
//VIVO:https://dev.vivo.com.cn/doc/document/info?id=103
//HUAWEI:https://mini.eastday.com/bdmip/180411011257629.html
//适配华为Android O设备
//方案一：
//具体方式如下所示：
//<meta-data android:name="android.notch_support" android:value="true"/>对Application生效，意味着该应用的所有页面，系统都不会做竖屏场景的特殊下移或者是横屏场景的右移特殊处理：
//<application
//android:allowBackup="true"
//android:icon="@mipmap/ic_launcher"
//android:label="@string/app_name"
//android:roundIcon="@mipmap/ic_launcher_round"
//android:testOnly="false"
//android:supportsRtl="true"
//android:theme="@style/AppTheme">
//<meta-data android:name="android.notch_support" android:value="true"/>
//<activity android:name=".MainActivity">
//<intent-filter>
//<action android:name="android.intent.action.MAIN"/>
//<category android:name="android.intent.category.LAUNCHER"/>
//</intent-filter>
//</activity>对Activity生效，意味着可以针对单个页面进行刘海屏适配，设置了该属性的Activity系统将不会做特殊处理：
//<application
//android:allowBackup="true"
//android:icon="@mipmap/ic_launcher"
//android:label="@string/app_name"
//android:roundIcon="@mipmap/ic_launcher_round"
//android:testOnly="false"
//android:supportsRtl="true"
//android:theme="@style/AppTheme">
//<activity android:name=".MainActivity">
//<intent-filter>
//<action android:name="android.intent.action.MAIN"/>
//<category android:name="android.intent.category.LAUNCHER"/>
//</intent-filter>
//</activity>
//<activity android:name=".LandscapeFullScreenActivity" android:screenOrientation="sensor">
//</activity>
//<activity android:name=".FullScreenActivity">
//<meta-data android:name="android.notch_support" android:value="true"/>
//</activity>
//</application>方案二
//对Application生效，意味着该应用的所有页面，系统都不会做竖屏场景的特殊下移或者是横屏场景的右移特殊处理
//我的NotchScreenTool中使用的就是方案二，如果需要针对Activity，建议自行修改。
//设置应用窗口在华为刘海屏手机使用刘海区
///*刘海屏全屏显示FLAG*/
//public static final int FLAG_NOTCH_SUPPORT=0x00010000;
///**
//* 设置应用窗口在华为刘海屏手机使用刘海区
//* @param window 应用页面window对象
//*/
//public static void setFullScreenWindowLayoutInDisplayCutout(Window window) {
//if (window == null) {
//return;
//}
//WindowManager.LayoutParams layoutParams = window.getAttributes();
//try {
//Class layoutParamsExCls = Class.forName("com.huawei.android.view.LayoutParamsEx");
//Constructor con=layoutParamsExCls.getConstructor(LayoutParams.class);
//Object layoutParamsExObj=con.newInstance(layoutParams);
//Method method=layoutParamsExCls.getMethod("addHwFlags", int.class);
//method.invoke(layoutParamsExObj, FLAG_NOTCH_SUPPORT);
//} catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException |InstantiationException
//| InvocationTargetException e) {
//Log.e("test", "hw add notch screen flag api error");
//} catch (Exception e) {
//Log.e("test", "other Exception");
//}
//}清除添加的华为刘海屏Flag，恢复应用不使用刘海区显示
///**
//* 设置应用窗口在华为刘海屏手机使用刘海区
//* @param window 应用页面window对象
//*/
//public static void setNotFullScreenWindowLayoutInDisplayCutout (Window window) {
//if (window == null) {
//return;
//}
//WindowManager.LayoutParams layoutParams = window.getAttributes();
//try {
//Class layoutParamsExCls = Class.forName("com.huawei.android.view.LayoutParamsEx");
//Constructor con=layoutParamsExCls.getConstructor(LayoutParams.class);
//Object layoutParamsExObj=con.newInstance(layoutParams);
//Method method=layoutParamsExCls.getMethod("clearHwFlags", int.class);
//method.invoke(layoutParamsExObj, FLAG_NOTCH_SUPPORT);
//} catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException |InstantiationException
//| InvocationTargetException e) {
//Log.e("test", "hw clear notch screen flag api error");
//} catch (Exception e) {
//Log.e("test", "other Exception");
//}}
//适配小米Android O设备
//判断是否是刘海屏
//private static boolean isNotch() {
//try {
//Method getInt = Class.forName("android.os.SystemProperties").getMethod("getInt", String.class, int.class);
//int notch = (int) getInt.invoke(null, "ro.miui.notch", 0);
//return notch == 1;
//} catch (Throwable ignore) {
//}
//return false;
//}设置显示到刘海区域
//@Override
//public void setDisplayInNotch(Activity activity) {
//int flag = 0x00000100 | 0x00000200 | 0x00000400;
//try {
//Method method = Window.class.getMethod("addExtraFlags",
//int.class);
//method.invoke(activity.getWindow(), flag);
//} catch (Exception ignore) {
//}
//}获取刘海宽高
//public static int getNotchHeight(Context context) {
//int resourceId = context.getResources().getIdentifier("notch_height", "dimen", "android");
//if (resourceId > 0) {
//return context.getResources().getDimensionPixelSize(resourceId);
//}
//return 0;
//}
//public static int getNotchWidth(Context context) {
//int resourceId = context.getResources().getIdentifier("notch_width", "dimen", "android");
//if (resourceId > 0) {
//return context.getResources().getDimensionPixelSize(resourceId);
//}
//return 0;}
//适配oppoAndroid O设备
//判断是否是刘海屏
//@Override
//public boolean hasNotch(Activity activity) {
//boolean ret = false;
//try {
//ret = activity.getPackageManager().hasSystemFeature("com.oppo.feature.screen.heteromorphism");
//} catch (Throwable ignore) {
//}
//return ret;
//}获取刘海的左上角和右下角的坐标
///**
//* 获取刘海的坐标
//* <p>
//* 属性形如：[ro.oppo.screen.heteromorphism]: [378,0:702,80]
//* <p>
//* 获取到的值为378,0:702,80
//* <p>
//* <p>
//* (378,0)是刘海区域左上角的坐标
//* <p>
//* (702,80)是刘海区域右下角的坐标
//*/
//private static String getScreenValue() {
//String value = "";
//Class<?> cls;
//try {
//cls = Class.forName("android.os.SystemProperties");
//Method get = cls.getMethod("get", String.class);
//Object object = cls.newInstance();
//value = (String) get.invoke(object, "ro.oppo.screen.heteromorphism");
//} catch (Throwable ignore) {
//}
//return value;
//}
//Oppo Android O机型不需要设置显示到刘海区域，只要设置了应用全屏就会默认显示。
//因此Oppo机型必须适配。
//控制系统界面可见度
//s系统栏是专用于显示通知、传达设备状态和进行设备导航的屏幕区域。通常，系统栏（由状态栏和导航栏组成，如图 1 所示）与您的应用同时显示。如果应用显示沉浸式内容（例如电影或图片），则可以暂时调暗系统栏图标以减少对用户的干扰，也可以暂时隐藏系统栏以让用户畅享完全沉浸式体验。
//如果您熟悉 Android 设计指南，您会明白在设计应用时遵循标准的 Android 界面准则和使用模式是非常重要的。在修改系统栏之前，您应该仔细考虑用户的需求和期望，因为系统栏为用户提供了浏览设备和查看状态的标准方法。
//调暗系统栏
//本课介绍如何在 Android 4.0（API 级别 14）及更高版本中调暗系统栏（即状态和导航栏）。在更早期的版本中，Android 不提供调暗系统栏的内置方式。
//使用此方法时，内容大小不会调整，但系统栏中的图标会消失。用户只要轻触屏幕的状态栏或导航栏区域，这两个栏就会全面显示出来。这种方法的优点在于，这些栏仍然存在，但是它们的详细信息被遮盖，不仅可以在打造沉浸式体验，而且不影响用户轻松访问这些栏。
//您可以使用 SYSTEM_UI_FLAG_LOW_PROFILE 标记调暗状态栏和通知栏，如下所示：
//KOTLIN
//JAVA
//    // This example uses decor view, but you can use any visible view.
//    View decorView = getActivity().getWindow().getDecorView();
//    int uiOptions = View.SYSTEM_UI_FLAG_LOW_PROFILE;
//    decorView.setSystemUiVisibility(uiOptions);
//用户只要轻触状态栏或导航栏，此标记即会被清除，从而使这些栏显示出来。标记被清除后，如果您希望再次调暗这些栏，应用需要重置该标记。
//图 1 显示了一张导航栏已变暗的图库图片（请注意，“图库”应用会完全隐藏状态栏，而不会将其调暗）。请注意，导航栏（图片右侧）上有微弱的白点，表示导航控件：
//显示状态栏和导航栏
//如果您希望以编程方式清除通过 setSystemUiVisibility() 设置的标记，可按下方所示执行该操作：
//KOTLIN
//JAVA
//    View decorView = getActivity().getWindow().getDecorView();
//    // Calling setSystemUiVisibility() with a value of 0 clears
//    // all flags.
//    decorView.setSystemUiVisibility(0);
//隐藏导航栏
//s您可以使用 SYSTEM_UI_FLAG_HIDE_NAVIGATION 标记隐藏导航栏。以下代码段用于隐藏导航栏和状态栏：
//KOTLIN
//JAVA
//    View decorView = getWindow().getDecorView();
//    // Hide both the navigation bar and the status bar.
//    // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
//    // a general rule, you should design your app to hide the status bar whenever you
//    // hide the navigation bar.
//    int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                  | View.SYSTEM_UI_FLAG_FULLSCREEN;
//    decorView.setSystemUiVisibility(uiOptions);
//请注意以下几点：
//使用这种方法，轻触屏幕上的任意位置都会使导航栏（和状态栏）重新出现并保持可见状态。用户互动会导致这些标记被清除。
//标记被清除后，如果您希望再次隐藏这些栏，应用需要重置这些标记。请参阅响应界面可见性更改，探讨如何监听界面可见性变化，以便您的应用相应地做出响应。
//在不同的位置设置界面标记会产生不同的效果。如果您在 Activity 的 onCreate() 方法中隐藏系统栏，那么当用户按“主屏幕”按钮时，系统栏会重新出现。当用户重新打开 Activity 后，系统不会调用 onCreate()，因此系统栏仍保持可见。如果您希望在用户进入和退出 Activity 时继续保留系统界面更改，请在 onResume() 或 onWindowFocusChanged() 中设置界面标记。
//方法 setSystemUiVisibility() 生效的前提是您调用它时所在的视图必须可见。
//离开该视图会导致系统清除使用 setSystemUiVisibility() 设置的标记。
//让内容显示在导航栏后面
//在 Android 4.1 及更高版本中，您可以将应用的内容设置为显示在导航栏的后面，这样内容就不会随着导航栏的隐藏和显示调整大小。可使用 SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION 执行此操作。 您可能还需要使用 SYSTEM_UI_FLAG_LAYOUT_STABLE 来帮助您的应用保持稳定布局。
//当您使用此方法时，您要负责确保应用界面的关键部分不会被系统栏覆盖。有关此主题的更多讨论内容，请参阅隐藏状态栏一课。
//启用全屏模式
//有些内容在全屏模式下会让用户获得最佳体验，例如视频、游戏、图片库、图书和演示文稿中的幻灯片。本页面介绍了如何采用全屏模式吸引用户更深入地了解相关内容，并防止用户意外退出应用。您想要启用全屏模式可能只是为了让应用最大限度地利用屏幕空间。但需要注意用户来回切换应用查看通知、进行临时搜索等操作的频率。使用全屏模式会导致用户无法轻松访问系统导航，因此使用全屏模式的前提是：对用户体验来讲，带来的益处不仅仅只是获得一点额外的空间（会带来更多益处，例如：能够避免在游戏过程中意外退出，或针对图片、视频和图书提供宝贵的沉浸式体验）。
//全屏选项
//Android 提供了三个用于将应用设为全屏模式选项：向后倾斜模式、沉浸模式和粘性沉浸模式。在所有三种方法中，系统栏都是隐藏的，您的 Activity 会持续收到所有轻触事件。 它们之间的区别在于用户让系统栏重新显示出来的方式。
//以下是每个不同选项的说明。有关示例代码，请参阅下面的启用全屏模式。
//向后倾斜
//向后倾斜模式适用于用户不会与屏幕进行大量互动的全屏体验，例如在观看视频时。
//当用户希望调出系统栏时，只需点按屏幕上的任意位置即可。
//如需启用向后倾斜模式，请调用 setSystemUiVisibility() 并传递 SYSTEM_UI_FLAG_FULLSCREEN 和 SYSTEM_UI_FLAG_HIDE_NAVIGATION。
//当系统栏再次显示时，您可以接收回调以对界面进行其他适当的更新。请参阅响应界面可见性更改。
//沉浸模式
//沉浸模式适用于用户将与屏幕进行大量互动的应用。示例包括游戏、查看图库中的图片或者阅读分页内容，如图书或演示文稿中的幻灯片。
//当用户需要调出系统栏时，他们可从隐藏系统栏的任一边滑动。要求使用这种这种意图更强的手势是为了确保用户与您应用的互动不会因意外轻触和滑动而中断。
//要启用沉浸模式，请调用 setSystemUiVisibility() 并将 SYSTEM_UI_FLAG_IMMERSIVE 标志与 SYSTEM_UI_FLAG_FULLSCREEN 和 SYSTEM_UI_FLAG_HIDE_NAVIGATION 一起传递。
//如果您的应用拥有自己的控件，而这些控件在用户以沉浸模式观看内容时用不上，可让这些控件随系统栏同步消失及重新显示。 如果您有用于隐藏和显示应用控件的任何应用专用手势，上述建议同样适用。例如，如果轻触屏幕上的任意位置会显示或隐藏工具栏或调色板，也应相应地显示或隐藏系统栏。
//当系统栏再次显示时，您可以接收回调以对界面进行其他适当的更新。请参阅响应界面可见性更改。
//粘性沉浸模式
//在普通的沉浸模式中，只要用户从边缘滑动，系统就会负责显示系统栏，您的应用甚至不会知道发生了该手势。因此，如果用户实际上可能是出于主要的应用体验而需要从屏幕边缘滑动，例如在玩需要大量滑动的游戏或使用绘图应用时，您应改为启用“粘性”沉浸模式。
//在粘性沉浸模式下，如果用户从隐藏了系统栏的边缘滑动，系统栏会显示出来，但它们是半透明的，并且轻触手势会传递给应用，因此应用也会响应该手势。
//例如，在使用这种方法的绘图应用中，如果用户想绘制从屏幕最边缘开始的线条，则从这个边缘滑动会显示系统栏，同时还会开始绘制从最边缘开始的线条。无互动几秒钟后，或者用户在系统栏之外的任何位置轻触或做手势时，系统栏会自动消失。
//要启用粘性沉浸模式，请调用 setSystemUiVisibility() 并将 SYSTEM_UI_FLAG_IMMERSIVE_STICKY 标志与 SYSTEM_UI_FLAG_FULLSCREEN 和 SYSTEM_UI_FLAG_HIDE_NAVIGATION 一起传递。
//在粘性沉浸模式下，如果系统界面可见性发生变化，您无法收到回调。因此，如果您需要粘性沉浸模式的自动隐藏行为，但仍想要知道系统界面何时重新显示以便把您自己的界面控件也显示出来，请使用普通的 IMMERSIVE 标志并使用 Handler.postDelayed() 或类似方法，以在几秒后重新进入沉浸模式。
//启用全屏模式
//无论您想要使用哪种全屏模式，都必须调用 setSystemUiVisibility() 并将其传递给 SYSTEM_UI_FLAG_HIDE_NAVIGATION 和/或 SYSTEM_UI_FLAG_FULLSCREEN。您可以包含 SYSTEM_UI_FLAG_IMMERSIVE（用于普通沉浸模式）或 SYSTEM_UI_FLAG_IMMERSIVE_STICKY（用于粘性沉浸模式），也可以同时不含这两种标记以启用向后倾斜模式。
//最好包含其他系统界面标志（例如 SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION 和 SYSTEM_UI_FLAG_LAYOUT_STABLE），防止布局随着系统栏的隐藏和显示调整大小。您还应确保操作栏和其他界面控件同时处于隐藏状态。
//以下代码说明了如何隐藏和显示 Activity 中的状态栏和导航栏，同时无需为了响应屏幕空间变化而调整布局大小：
//KOTLIN
//JAVA
//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus) {
//            hideSystemUI();
//        }
//    }
//    private void hideSystemUI() {
//        // Enables regular immersive mode.
//        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
//        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//        View decorView = getWindow().getDecorView();
//        decorView.setSystemUiVisibility(
//                View.SYSTEM_UI_FLAG_IMMERSIVE
//                // Set the content to appear under the system bars so that the
//                // content doesn't resize when the system bars hide and show.
//                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                // Hide the nav bar and status bar
//                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_FULLSCREEN);
//    }
//    // Shows the system bars by removing all the flags
//    // except for the ones that make the content appear under the system bars.
//    private void showSystemUI() {
//        View decorView = getWindow().getDecorView();
//        decorView.setSystemUiVisibility(
//                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//    }
//为了提供更好的用户体验，您可能还需要实现以下功能：
//如需实现状态之间的无缝切换，请将所有界面控件的可见性与系统栏保持同步。当应用进入沉浸模式后，任何界面控件也应随系统栏一起隐藏，然后在系统界面重新出现时也再次显示出来。为此，请实现 View.OnSystemUiVisibilityChangeListener 以接收回调，如响应界面可见性更改中所述。
//实现 onWindowFocusChanged()。 如果您获得窗口焦点，则可能需要再次隐藏系统栏。 如果您失去了窗口焦点，例如由于在应用界面上出现对话框或弹出式菜单，您可能需要使用 Handler.postDelayed() 或类似方法取消之前调度的所有待处理“隐藏”操作。
//实现用于检测 onSingleTapUp(MotionEvent) 的 GestureDetector，以允许用户通过轻触您的内容手动切换系统栏的可见性。 简单的点击监听器并不是这种情况的最佳解决方案，因为即使用户用手指在屏幕上拖动也会触发此类监听器（假设点击目标会占用整个屏幕）。
//注意：当您使用 SYSTEM_UI_FLAG_IMMERSIVE_STICKY 标记时，滑动操作会使系统界面暂时以半透明状态显示，但不会清除任何标记，并且不会触发系统界面可见性更改监听器。
//响应界面可见度变更
//本课介绍如何注册监听器，以便让应用获得系统界面可见性变更通知。如果您希望将界面的其他部分与系统栏的隐藏/显示同步，这会非常有用。

