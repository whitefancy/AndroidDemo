package pub.whitefancy.androiddemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class First2Fragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first2, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(First2Fragment.this)
                        .navigate(R.id.action_First2Fragment_to_Second2Fragment);
            }
        });
    }
}
//Fragment
//Android上的界面展示都是通过Activity实现的，Activity实在是太常用了。但是Activity也有它的局限性，同样的界面在手机上显示可能很好看，在平板上就未必了，因为平板的屏幕非常大，手机的界面放在平板上可能会有过分被拉长、控件间距过大等情况。这个时候更好的体验效果是在Activity中嵌入"小Activity"，然后每个"小Activity"又可以拥有自己的布局。因此，我们今天的主角Fragment登场了。
//Fragment初探：
//Fragment是activity的界面中的一部分或一种行为。你可以把多个Fragment们组合到一个activity中来创建一个多面界面，并且你可以在多个activity中重用一个Fragment。你可以把Fragment认为模块化的一段activity，它具有自己的生命周期，接收它自己的事件，并可以在activity运行时被添加或删除。
//Fragment不能独立存在，它必须嵌入到activity中，而且Fragment的生命周期直接受所在的activity的影响。例如：当activity暂停时，它拥有的所有的Fragment们都暂停了，当activity销毁时，它拥有的所有Fragment们都被销毁。然而，当activity运行时（在onResume()之后，onPause()之前），你可以单独地操作每个Fragment，比如添加或删除它们。当你在执行上述针对Fragment的事务时，你可以将事务添加到一个栈中，这个栈被activity管理，栈中的每一条都是一个Fragment的一次事务。有了这个栈，就可以反向执行Fragment的事务，这样就可以在Fragment级支持“返回”键（向后导航）。
//当向activity中添加一个Fragment时，它须置于ViewGroup控件中，并且需定义Fragment自己的界面。你可以在layoutxml文件中声明Fragment，元素为：<fragment>；也可以在代码中创建Fragment，然后把它加入到ViewGroup控件中。然而，Fragment不一定非要放在activity的界面中，它可以隐藏在后台为actvitiy工作。
//Fragment的生命周期：
//因为Fragment必须嵌入在Acitivity中使用，所以Fragment的生命周期和它所在的Activity是密切相关的。
//如果Activity是暂停状态，其中所有的Fragment都是暂停状态；如果Activity是stopped状态，这个Activity中所有的Fragment都不能被启动；如果Activity被销毁，那么它其中的所有Fragment都会被销毁。
//但是，当Activity在活动状态，可以独立控制Fragment的状态，比如加上或者移除Fragment。
//当这样进行fragment transaction（转换）的时候，可以把fragment放入Activity的back stack中，这样用户就可以进行返回操作。
//使用Fragment时，需要继承Fragment或者Fragment的子类（DialogFragment, ListFragment, PreferenceFragment, WebViewFragment），所以Fragment的代码看起来和Activity的类似。
//每当创建一个Fragment时，首先添加以下三个回调方法：
//onCreate()：系统在创建Fragment的时候调用这个方法，这里应该初始化相关的组件，一些即便是被暂停或者被停止时依然需要保留的东西。
//onCreateView()：当第一次绘制Fragment的UI时系统调用这个方法，该方法将返回一个View，如果Fragment不提供UI也可以返回null。注意，如果继承自ListFragment，onCreateView()默认的实现会返回一个ListView，所以不用自己实现。
//onPause()：当用户离开Fragment时第一个调用这个方法，需要提交一些变化，因为用户很可能不再返回来。
//将Fragment加载到Activity当中有两种方式：
//方式一：添加Fragment到Activity的布局文件当中
//方式二：在Activity的代码中动态添加Fragment
//第一种方式虽然简单但灵活性不够。添加Fragment到Activity的布局文件当中,就等同于将Fragment及其视图与activity的视图绑定在一起，且在activity的生命周期过程中，无法切换fragment视图。
//第二种方式比较复杂，但也是唯一一种可以在运行时控制fragment的方式（加载、移除、替换）。
//Fragment的使用
//Fragment是可以被包裹在多个不同Activity内的，同时一个Activity内可以包裹多个Fragment，Activity就如一个大的容器，它可以管理多个Fragment。所有Activity与Fragment之间存在依赖关系。
//Activity与Fragment通信方案
//上文提到Activity与Fragment之间是存在依赖关系的，因此它们之间必然会涉及到通信问题，解决通信问题必然会涉及到对象之间的引用。因为Fragment的出现有一个重要的使命就是：模块化，从而提高复用性。若达到此效果，Fragment必须做到高内聚，低耦合。
//它们之间通信的方案有：handler，广播，EvnetBus，接口等
//Handler方案:
//先上代码
//public class MainActivity extends FragmentActivity{
////声明一个Handler
//public Handler mHandler = new Handler(){
//@Override
//public void handleMessage(Message msg) {
//super.handleMessage(msg);
//...相应的处理代码
//}
//}
//...相应的处理代码
//}
//public class MainFragment extends Fragment{
////保存Activity传递的handler
//private Handler mHandler;
//@Override
//public void onAttach(Activity activity) {
//super.onAttach(activity);
////这个地方已经产生了耦合，若还有其他的activity，这个地方就得修改
//if(activity instance MainActivity){
//mHandler = ((MainActivity)activity).mHandler;
//}
//}
//...相应的处理代码
//}
//该方案存在的缺点：
//Fragment对具体的Activity存在耦合，不利于Fragment复用
//不利于维护，若想删除相应的Activity，Fragment也得改动
//没法获取Activity的返回数据
//handler的使用个人感觉就很不爽（不知大家是否有同感)
//广播方案：
//具体的代码就不写了，说下该方案的缺点：
//用广播解决此问题有点大材小用了，个人感觉广播的意图是用在一对多，接收广播者是未知的情况
//广播性能肯定会差（不要和我说性能不是问题，对于手机来说性能是大问题）
//传播数据有限制（必须得实现序列化接口才可以）
//暂时就想到这些缺点，其他的缺点请大家集思广益下吧。
//EventBus方案：
//具体的EventBus的使用可以自己搜索下，个人对该方案的看法：
//EventBus是用反射机制实现的，性能上会有问题（不要和我说性能不是问题，对于手机来说性能是大问题）
//EventBus难于维护代码
//没法获取Activity的返回数据
//接口方案
//我想这种方案是大家最易想到，使用最多的一种方案吧，具体上代码：
////MainActivity实现MainFragment开放的接口
//public class MainActivity extends FragmentActivity implements FragmentListener{
//@override
//public void toH5Page(){ }
//...其他处理代码省略
//}
//public class MainFragment extends Fragment{
//public FragmentListener mListener;
////MainFragment开放的接口
//public static interface FragmentListener{
////跳到h5页面
//void toH5Page();
//}
//@Override
//public void onAttach(Activity activity) {
//super.onAttach(activity);
////对传递进来的Activity进行接口转换
//if(activity instance FragmentListener){
//mListener = ((FragmentListener)activity);
//}
//}
//...其他处理代码省略
//}
//这种方案应该是既能达到复用，又能达到很好的可维护性，并且性能也是杠杠的。但是唯一的一个遗憾是假如项目很大了，Activity与Fragment的数量也会增加，这时候为每对Activity与Fragment交互定义交互接口就是一个很头疼的问题（包括为接口的命名，新定义的接口相应的Activity还得实现，相应的Fragment还得进行强制转换）。
//Fragment和Activity的交互：
//java回调机制
//Class A实现接口CallBack callback——背景1
//class A中包含一个class B的引用b ——背景2
//class B有一个参数为callback的方法f(CallBack callback) ——背景3
//A的对象a调用B的方法 f(CallBack callback) ——A类调用B类的某个方法 C
//然后b就可以在f(CallBack callback)方法中调用A的方法 ——B类调用A类的某个方法D
//1、在Fragment中调用Activity中的方法：
//Fragment可以通过getActivity()方法来获得Activity的实例，然后就可以调用一些例如findViewById()之类的方法。例如：
//View listView = getActivity().findViewById(R.id.list);
//但是注意调用getActivity()时，fragment必须和activity关联（attached to an activity），否则将会返回一个null。
//另外，当碎片中需要使用Context对象时，也可以使用getActivity()方法，因此获取到的活动本身就是一个Context对象。
//【实例】在Activity的EditText中输入一段文本，这个时候，点击Fragment中的按钮，让它弹出吐司，显示出对应的文本。
//其实就是让Activity中的文本显示在Fragment中，Fragment的核心代码如下：
//public View onCreateView(LayoutInflater inflater, ViewGroup container,
//Bundle savedInstanceState) {
//View view = inflater.inflate(R.layout.fragment_left, null);
//button = (Button) view.findViewById(R.id.button1);
//button.setOnClickListener(new OnClickListener() {
//@Override
//public void onClick(View v) {
//// TODO Auto-generated method stub
//EditText editText = (EditText) getActivity().findViewById(R.id.editText);
// Toast.makeText(getActivity(), editText.getText().toString(), 1).show();
// }
// });
// return view;
// }
//第09行代码是核心，通过getActivity()方法来获得Activity的实例，然后就可以调用findViewById()的方法得到其中的EditText控件。
//2、在Activity中调用Fragment中的方法：（要用到接口回调）
//activity也可以获得一个fragment的引用，从而调用fragment中的方法。获得fragment的引用要用FragmentManager，之后可以调用findFragmentById() 或者 findFragmentByTag()。例如：
//ExampleFragment fragment = (ExampleFragment) getFragmentManager().findFragmentById(R.id.example_fragment);
//You should always use FragmentActivity and android.support.v4.app.Fragment instead of the platform default Activity and android.app.Fragment classes. Using the platform defaults mean that you are relying on whatever implementation of fragments is used in the device you are running on. These are often multiple years old, and contain bugs that have since been fixed in the support library.