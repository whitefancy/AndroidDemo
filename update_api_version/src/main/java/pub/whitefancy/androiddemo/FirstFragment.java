package pub.whitefancy.androiddemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class FirstFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
    }
}
//Activity如何指定需要的任务栈
//Activity指定需要启动的任务栈可以用过在配置文件中添加taskAffinity属性来实现。
//TaskAffinity特点:
//默认情况下，Activity启动的任务栈名称为应用包名。
//如果自己指定该属性值，不能与包名相同，否则相当于没指定。
//该属于一般配合singleTask或者allowTaskReparenting属性结合使用，在其他情况下没有实际意义。
//<activity android:name=".TestActivity"
//android:launchMode="singleTask"
//android:taskAffinity="com.test.singleTask.affinity"/>
//<activity android:name=".Test2ActivityC"
//android:exported="true"
//android:allowTaskReparenting="true"/>
//TaskAffinity使用场景
//下面这段内容摘自Activity启动模式与任务栈(Task)全面深入记录（下）这篇文章。
//TaskAffinity与singleTask应用场景
//假如现在有这么一个需求,我们的客户端app正处于后台运行，此时我们因为某些需要，让微信调用自己客户端app的某个页面，用户完成相关操作后，我们不做任何处理，按下回退或者当前Activity.finish()，页面都会停留在自己的客户端（此时我们的app回退栈不为空），这显然不符合逻辑的，用户体验也是相当出问题的。我们要求是，回退必须回到微信客户端,而且要保证不杀死自己的app.这时候我们的处理方案就是，设置当前被调起Activity的属性为：
//LaunchMode=""SingleTask" taskAffinity="com.tencent.mm"
//其中com.tencent.mm是借助于工具找到的微信包名，就是把自己的Activity放到微信默认的Task栈里面，这样回退时就会遵循“Task只要有Activity一定从本Task剩余Activity回退”的原则，不会回到自己的客户端；而且也不会影响自己客户端本来的Activity和Task逻辑。
//TaskAffinity与allowTaskReparenting应用场景
//一个e-mail应用消息包含一个网页链接，点击这个链接将出发一个activity来显示这个页面，虽然这个activity是浏览器应用定义的，但是activity由于e-mail应用程序加载的，所以在这个时候该activity也属于e-mail这个task。如果e-mail应用切换到后台，浏览器在下次打开时由于allowTaskReparenting值为true，此时浏览器就会显示该activity而不显示浏览器主界面，同时actvity也将从e-mail的任务栈迁移到浏览器的任务栈，下次打开e-买了时并不会再显示该activity。
//Taskffinity与singleTask实例:
//注: 如果使用我在GitHub上建立的项目测这个功能的时候，请将TestTaskffinityOrAllowTaskRep.zip这个压缩包解压，并导入AS中。这个压缩包是我在测试的时候写的用于跳转androidreview应用的testTask应用。
//testTask应用(简称T应用)MainActivity中有一个按钮A，点击按钮会调用androidreview应用(简称A应用)的SingleTaskActivity。下面是两个应用的主要代码。
//testTask应用代码:
//switch (v.getId()) {
//case R.id.mBnt_ForSingleTask:
////跳转androidreview应用SingleToak页面的按钮方法
//ComponentName cnForSingleTask = new ComponentName(
//"com.hdd.androidreview",
//"com.hdd.androidreview.Patterm.SingleTaskActivity");
//intent.setComponent(cnForSingleTask);
//startActivity(intent);
//break;
//}
//androidreview应用代码:
//<activity
//android:name=".Patterm.SingleTaskActivity"
//android:exported="true"
//android:launchMode="singleTask"
//android:taskAffinity="cmom.han.testbt.testTask">
//</activity>
//从上面的A应用代码配置信息中可以看到taskAffinity属性配置的是T应用的包名。因此SingleTaskActivity会在T的任务中被创建。假如MainActivity的按钮A点击事件中启动了SingleTaskActivity。那么cmom.han.testbt.testTask任务栈中会存在SingleTaskActivity和MainActivity两个Activity。下面是Activity在栈中的信息。
//这时如果按了hone键返回桌面SingleTaskActivity进入后台，然后点击A应用的图标启动的却是A应用的MainActivity。出现这种情况是因为SingleTaskActivity的taskAffinity属性指定的是T应用包名。在T应用的MainActivity启动的SingleTaskActivy是在T应用的任务栈中。
//TaskAffinity与allowTaskReparenting实例
//T应用的B按钮点击事件代码：
//case R.id.mBnt_ForAllowTaskRep:
////allowTaskReparenting模式跳转PattermActivity
//intent.setAction("com.hdd.androidreview.PattermActivity");
//ComponentName cnForAllowTaskRep = new ComponentName(
//"com.hdd.androidreview",
//"com.hdd.androidreview.Patterm.PattermActivity");
//intent.setComponent(cnForAllowTaskRep);
//break;
//A应用的PattermActivity(简称PActivity)的配置信息:
//<activity
//android:name=".Patterm.PattermActivity"
//android:allowTaskReparenting="true"
//android:theme="@style/AppTheme.NoActionBar" >
//<intent-filter>
//<action android:name="com.hdd.androidreview.PattermActivity"/>
//</intent-filter>
//</activity>
//点击T用于的B按钮，会启动A用的PActivity。该Activity的allowTaskReparenting属性为true，那么Activity会被移动到T应用的任务中站创建。当T应用按home返回桌面，再点击A应用；PActivity会被移回A的任务栈中。
//T应用调用A应用的PActivity栈内信息
//image
//T应用按home返回桌面，在启动A应用栈内信息:
//image