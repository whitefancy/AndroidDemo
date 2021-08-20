package pub.whitefancy.androiddemo;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SingleInstanceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_instance);
        //App在后台运行，点击应用桌面图标重新启动
        //然而，当你把App放入后台时，这个时候点击了app桌面的启动图标，这个时候系统会默认你开启一个新的应用，但是因为一个软件只能在手机上面运行一个，所以，系统发现你之前的app还在后台，这个时候系统会把新创建的activity放到了之前activity栈的顶部，如上图所示的Activity1
        //        知道了原因之后，我们就好做处理了。
        //        第一步：查看Activity1的启动模式，如果Activity1的启动模式为singleTask
        //android:launchMode="singleTask"
        //           那么必须把他删除掉，或者改为“standard"。
        //         第二步：在你的app的AndroidManifest.xml文件的application标签下面设置
        //android:persistent="true"
        //         持久化为 true；防止你的app挂后台被回收。
        //         第三步：在activity1的onCreate方法中设置如下方法：
        //            用于判断这个Activity的启动标志，看它所在的应用是不是从后台跑到前台的。如果是，则直接把它finish（）掉，然后系统会去Activity启动历史栈查询上一个activity，然后再新建它，所以还原到了我们按home键出去的那个界面。
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
    }

}