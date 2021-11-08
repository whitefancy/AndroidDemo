package com.whitefancy.layoutdemo;

public class ViewNotes {
    //常用窗口小部件
    //TextView 文本框 ，子类 EditText文本输入框
    //有几十个属性，可以登录官网查看
    //在活动类的onCreate方法中，使用 setContentView方法加载活动界面后，所有的活动组件便处于活动状态。
    //可以将这些组件实例化。

    //EditText文本输入框
    //android:ems 设置显示多少个字符
    //android:hint 设置提示文本
    //android:textColorHint 提示文本的颜色
    //android:inputType设置用户输入字符的类型，系统会根据类型拉起对应的键盘

    //AutoCompleteTextView 自动完成文本框
    //android:completionHint 设置下拉菜单的提示标题
    //android:completionThreshold 设置用户至少输入几个字符才会显示提示

    //MultiAutoCompleteTextView 多个输入的自动完成文本框
    //setThreshold(1);//设置输入几个字符后提示，默认为2字符
    //setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());//设置分隔符为逗号分隔符
}
