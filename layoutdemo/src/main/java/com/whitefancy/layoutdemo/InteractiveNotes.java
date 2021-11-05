package com.whitefancy.layoutdemo;

import android.content.Context;
import android.view.View;

public class InteractiveNotes extends View {
    public InteractiveNotes(Context context) {
        super(context);
    }
    //界面交互方式：点击 长按 拖动
    //android系统给出了两种处理方式：基于回调机制的处理方法，基于监听接口的处理方法
    //基于回调：重写特定组件的回调方法。Android为绝大部分界面组件提供了事件相应的回调方法
    //右键任何View的子类，generate ，overrideMethod ，就能够添加键盘点击等事件
    //这里主要讲的是界面上的回调事件。
    //1.boolean onKeyDown（int keyCode KeyEvent event）
    //当用户在该组件上按下某个键时触发该方法。
    //2.– onKeyLongPress（- -）长按某个按钮时触发
    //3.– onKeyShortCut（- -）按下键盘快捷键时触发
    //4.– onKeyUp（- -）松开某个按钮时触发
    //5.– onTouchEvent（- -）触屏时触发
    //6.– onTrackballEvent（- -）图形轨迹出现轨迹球轨迹时触发
    //每个回调里都有细分的事件的分类，根据event的内容，做不同的处理。
    // 使用ctrl+q 可以快速查看方法的提示文档


    //基于监听接口的交互
    //监听接口是一个接口类，用来监听用户对布局、窗口组件的操作，被监听的对象称为事件源，接口被称为监听器
    // 接口类中的抽象方法被开发人员完成为预定的动作。
    //使用一系列 setOnXxxListener（new OnXxxListener(){}) 为事件源注册监听，将事件源与事件监听器联系到一起
    //View内部定义好的事件监听器和调用时机
    //1. setOnClickListener( View.OnClickListener l)
    //
    //注册要在单击此视图时调用的回调。
    //
    //2. setOnCreateContextMenuListener（查看。OnCreateContextMenuListener升）
    //
    //注册一个回调以在构建此视图的上下文菜单时调用。
    //
    //3.setOnDragListener( View.OnDragListener l)
    //
    //为此 View 注册一个拖动事件侦听器回调对象。
    //
    //4. setOnFocusChangeListener( View.OnFocusChangeListener l)
    //
    //注册要在此视图的焦点更改时调用的回调。
    //
    //5.setOnGenericMotionListener( View.OnGenericMotionListener l)
    //
    //注册一个回调，当一个通用的运动事件被发送到这个视图时被调用。
    //
    //6. setOnHoverListener( View.OnHoverListener l)
    //
    //注册一个回调以在悬停事件发送到此视图时调用。
    //7. setOnKeyListener( View.OnKeyListener l)
    //
    //注册在此视图中按下硬件键时要调用的回调。
    //
    //8. setOnLongClickListener( View.OnLongClickListener l)
    //
    //注册一个回调以在单击并保持此视图时调用。
    //
    //9.setOnSystemUiVisibilityChangeListener( View.OnSystemUiVisibilityChangeListener l)
    //
    //设置一个监听器，当系统栏的可见性改变时接收回调。
    //
    //10.setOnTouchListener( View.OnTouchListener l)
    //
    //注册一个回调，当一个触摸事件被发送到这个视图时被调用。

    //三、其他类型的监听事件和接口方法
    //1.动画变化监听事件
    //
    //setAnimationListener (new Animation.AnimationListener(){})
    //
    //①onAnimationStart(Animation animation) – 动画开始时调用
    //
    //②onAnimationEnd(Animation animation) – 动画结束时调用
    //
    //③onAnimationRepeat(Animation animation) – 动画重复时调用
    //
    //监听动画的某一次执行结果（不必每次都覆写多个方法）
    //
    //addListener (new AnimatorListenerAdapter(){}
    //
    //onAnimationEnd(Animator animation) -  只在动画执行完成时调用
    //
    //
    //
    //2.ViewPager的滑动监听事件
    //
    //setOnPageChangeListener (OnPageChangeListener onPageChangeListener)
    //
    //①onPageScrolled(int position, float positionOffset, int positionOffsetPixels)  - 当页面在滑动的时候会调用此方法，在滑动被停止之前，此方法回一直得到调用；第一个参数：当前页面，第二个参数：当前页面偏移百分比（以0.6为界，标记是否滑动到下一个还是回到原位），第三个参数：当前页面偏移的像素位置；
    //
    //②onPageSelected(int position) - 此方法是页面跳转完后得到调用；参数position为当前所在位置；
    //
    //③onPageScrollStateChanged(int state) - 在状态改变的时候调用；参数state有三个值：0-do nothing，1-正在滑动，2-滑动结束；

    //各控件的事件监听器和监听方法
    //1.Button（按钮）的监听事件：OnClickListener 接口 onClick(View v)接口方法；
    //2.SeekBar(进度条)的监听事件：OnSeekBarChangedListener接口，
    //
    //①onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)；
    //
    //②onStartTrackingTouch(SeekBar seekBar)；//滑动开始
    //
    //③onStopTrackingTouch(SeekBar seekBar)；//滑动结束
    //
    //3. EditText（编辑器）的监听事件：OnKeyListener接口
    //
    //onKey(View v, int keyCode, KeyEvent event)//接口方法 监听键盘事件
    //4. RadioGroup（单选按钮）的监听事件：OnCheckedChangeListener接口
    //
    //onCheckedChanged(RadioGroup group, int checkedId)//接口方法；
    //
    //5. Spinner（下拉列表）的监听事件：OnItemSelectedListener接口
    //
    //①onItemSelected(AdapterView<?> parent, View view, int position, long id)；
    //
    //②onNothingSelected(AdapterView<?> parent)
    //
    //6. Menu（菜单）的监听事件：Activity内部方法 不同菜单调用不同选择方法
    //
    //①public boolean onMenuItemSelected (int featureId, MenuItem item)；
    //
    //②public boolean onOptionsItemSelected (MenuItem item)；
    //
    //③public boolean onContextItemSelected (MenuItem item)；
    //
    //7. Dialog（对话框）的监听事件：实现了多个总类型接口，每个总类型接口中有若干个接口，根据不同种类Dialog，会实现不同的接口方法。implements DialogInterface KeyEvent.Callback  View.OnCreateContextMenuListener  Window.Callback
    //
    //如按钮类型的Dialog会实现总接口下的子接口DialogInterface.OnClickListener；
    //
    //8. DatePicker(日期)日期改变的监听事件： OnDateChangedListener接口
    //
    //onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth);
    //
    //9. TimePicker(时间)一天中事件改变的监听事件：OnTimeChangedListener接口
    //
    //onTimeChanged(TimePicker view, int hourOfDay, int minute);
    //
    //10. SlidingDrawer（滑动式抽屉）的监听事件：OnDrawerOpenListener接口
    //
    //onDrawerOpened();Invoked when the drawer becomes fully open.
    //
    //11. RatingBar（星级等级评价）的监听事件：OnRatingBarChangeListener
    //
    //onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser);
    //12. Chronometer（计数器）的监听事件：OnChronometerTickListener接口
    //
    //onChronometerTick(Chronometer chronometer); //提示计数器数字改变

}
