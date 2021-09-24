package pub.whitefancy.androiddemo;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class LifestyleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifestyle);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
//Android应用的生命周期
//Android 源码分析Application的生命周期及共享数据详解
//基本作用
//Activity的生命周期共七个指示器：onCreate、onStart、onResume、onPause、onStop、onDestroy、onRestart。
//如下图所示。
//onCreate() ，不可见状态
//在Activity被创建时回调，第一个生命周期。我们一般在创建Activity时需要重写该方法做一些初始化的操作，如通过setContentView设置界面布局的资源，初始化所需要的组件信息等。
//onStart() ，可见状态
//该方法回调表示Activity正在启动，此时Activity处于可见状态，只是还没有在前台显示，因此用户也无法交互。可以简单理解为Activity已显示却无法被用户看见。
//onResume() ，可见状态
//此方法回调时，Activity已在在屏幕上显示UI并允许用户操作了。从流程图可见，当Activity停止后（onPause、onStop方法被调用），重新回到前台时也会调用onResume方法。可以在onResume方法中初始化一些资源，比如打开相机或开启动画。
//onPause() ，可见状态
//此方法回调时，Activity正在停止（Paused形态），通常接下来 onStop() 会被回调 。但通过流程图可见，另一种情况是onPause() 执行后直接执行了onResume方法，这可能是用户点击Home键，让程序退回到主界面，程序在后台运行时又迅速地再回到到当前的Activity，此时onResume方法就会被回调。我们可以在onPause方法中做一些数据存储、动画停止、资源回收等操作。另外，onPause方法执行完成后，新Activity的onResume方法才会被执行。所以onPause不能太耗时，因为这可能会影响到新的Activity的显示。
//onStop() ，不可见状态
//此方法回调时，Activity即将停止或者完全被覆盖（Stopped形态），此时Activity不可见，仅在后台运行。同样地，在onStop方法可以做一些资源释放的操作，不能太耗时。
//onRestart(），可见状态
//此方法回调时，表示Activity正在重新启动，由不可见状态变为可见状态。这种情况，一般发生在用户打开了一个新的Activity时，之前的Activity就会被onStop，接着又回到之前Activity页面时，之前的Activity的 onRestart方法就会被回调。
//onDestroy() ，不可见状态
//此方法回调时，表示Activity正在被销毁，也是生命周期最后一个执行的方法，一般我们可以在此方法中做一些回收工作和最终的资源释放。
//初始化和资源回收
//1、如果所有的初始化都在onCreate()中实现，会有什么问题？
//首先，Activity的onCreate()被调用时，Activity还不可见，如果要做一些动画，既然视图还不存在，在onCreate中来启动动画，明显有问题；
//其次，AActivity 切换到 BActivity，再切换到 AActivity，由于实例已经存在，所以onCreate不会再被调用，那问题就在于AActivity从后台切换至前台时，有可能需要一些初始化，就没法被调用到了。
//2、如果所有的初始化都在onStart()中实现，会有什么问题？
//首先，虽然 在onStart()中用 setContentView()、findViewById() 功能也是正常的，但是onCreate()注释中，明确建议 setContentView()、findViewById() 要在 onCreate() 中被调用。
//其次， onResume()的注释中都明确地说了这不是 Activity 对用户是可见的最好的指示器，如果在 onStart() 中做全部初始化，很有可能初始化还没完成影响到用户的交互体验。
//3、如果所有资源回收都在onStop()中实现，会有什么问题？
//首先，在 onResume() 的注释中，建议是在onResume()中打开独占设备（比如相机），与onResume()对应的是onPause()，关闭相机的操作也应该在此方法中被调用；否则，考虑一下如下场景：如果AActivity打开了相机，我们点击某按钮要跳转到BActivity中，BActivity也想打开相机；假设AActivity的onPause() 在 BActivity启动后再被调用，那BActivity根本就无法再正常启动相机。
//在onPause() 的注释中明确表示，应该在这个方法中执行停止动画等比较耗CPU的操作，如果不先执行这些操作，就先启动新应用，然后再来执行此操作，确实是不合逻辑；其次，onStop() 的注释中也明确地写了，在内存不足而导致系统自动回收进程情况下，onStop() 可能都不会被执行。
//4、Activity间跳转时，为什么AActivity的onPause()被调用后，BActivity的初始化流程（onCreate() -> onStart() -> onResume()），然后AActivity的onStop()被调用？
//从AActivity切换到BActivity的日志如下：
//10-17 20:54:46.997: I/com.example.servicetest.AActivity(5817): onPause()
//10-17 20:54:47.021: I/com.example.servicetest.BActivity(5817): onCreate()
//10-17 20:54:47.028: I/com.example.servicetest.BActivity(5817): onStart()
//10-17 20:54:47.028: I/com.example.servicetest.BActivity(5817): onResume()
//10-17 20:54:47.099: I/com.example.servicetest.AActivity(5817): onStop()
//当用户点击打开新的Activity，肯定是想尽快进入新的视图进行操作。而且上面的问题已经解释了，在onResume()一般会打开独占设备，开启动画等，当需要从AActivity切换到BActivity时，先执行AActivity中的onPause()进行关闭独占设备，关闭动画等，以防止BActivity也需要使用这些资源，因为AActivity的资源回收，也有利于BActivity运行的流畅。
//当AActivity中比较消耗资源的部分在onPause()中关闭后，再执行BActivity的初始化，显示视图与用户交互。然后，系统在后台默默执行AActivity的onStop()操作，去回收AActivity占用的其余资源。即使onStop()中会有些比较耗时的操作，也没有关系，这是在后台执行也不会影响到用户的体验。（设计的非常好!good！！！）
//onSavedInstanceState()和onRestoreInstanceState()理解
//大家在开发过程中可能遇到，当在页面的EditText中输入值，接着翻转屏幕时，我输入的内容清空了，但是当我给EditText定义了id属性，再执行上诉操作时，EditText的内容仍然存在。另外，当我们点击Home键退回到主页面，许久之后再次打开程序进行操作，可能会崩溃。
//下面解释为什么会出现这样的情况:
//（1）onSavedInstanceState()和onRestoreInstanceState()并不是activity生命周期的方法。
//onSaveInstanceState()会在onPause()或onStop()之前执行；
//onRestoreInstanceState()会在onStart()和onResume()之间执行。
//当应用遇到意外情况（内存不足，用户直接按home键）由系统直接销毁一个Activity时，onSaveInstanceState()就会调用，但是当用户主动销毁activity，如按back键，或直接执行finish(),这种情况下onSaveInstanceState()就不会执行，因为这种情况下，用户的行为决定了不需要保存Activity的状态。
//那么onRestoreInstanceState()会跟onSaveInstanceState()成对出现吗？
//答案是不会成对出现，onSaveInstanceState()需要调用的时，activity可能销毁，也可能没有销毁，只有在activity销毁重建的时候onRestoreInstanceState()才会调用。
//在onSaveInstanceState()中默认情况下具体干些什么？
//默认情况下默认会自动保存Activity中的某些状态，比如activity中各种UI的状态，因此在activity被“系统”销毁和重建的时候，这些Ui的状态会默认保存，但是前提条件是Ui控件必须制定id,如果没有指定id的话，UI的状态是无法保存 的。
//总结下Activity数据的保存和恢复：
//activity中保存数据有两种方式onPause()，onSaveInstance(bundle), 恢复数据也有两种途径onCreate(Bundle), onRestoreInstanceState(budle)，默认情况下onSaveInstanceSate()和onRestoreInstanceState()会对UI状态进行保存和恢复，如果需要保存其他数据可以在onSaveInstanceState()、onPause()保存。但是如果是持久化的数据，google推荐的是通过onPause()保存。
//android update project --path D:\stories
//ewSdkProj\client\oversea2\luobo\src\main --target 10 --library C:\Users\Administrator\Downloads\AndroidSDK\AndroidSDK\sdk\tools/extras/google/market_licensing --library C:\Users\Administrator\Downloads\AndroidSDK\AndroidSDK\sdk\extras\google\market_apk_expansion\downloader_library