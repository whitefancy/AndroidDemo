package com.whitefancy.layoutdemo;

public class LayoutNotes {
    //不同的布局具有不同的组件控制方式，同样的组件在不同的布局中，看到的效果不太一样。
    //线性布局是一个视图组，用于使所有子视图在单个方向保持对齐
    //使用 android:orientation 指定布局方向

    //android:layout_height android:layout_width用来设置布局的宽度，默认有两个选择， match_parent 和 wrap_content
    //match_parent 匹配父布局，当最外层的布局的宽度设置为该值时，该布局将铺满整个屏幕
    //wrap_content 指包裹内容，当控件宽度设置为该值时，以能够包裹住内容为准。

    //android:gravity用于设置布局子控件或控件自身内容的位置，从而使该按钮按照规定的位置显示
    //android:layout_gravity 设置自己在父布局的排版位置
    // 如果android:gravity与android:layout_gravity都设置了，并且两者的值有冲突，以父布局的设置为准。

    //android:layout_weight 设置子控件的权重，用于子控件的间距和内容的比例，受到子控件的实际宽度影响

    //android:background 用于设置布局或控件的背景，可以用十六进制表示颜色或图片

    //androidx.constraintlayout.widget.ConstraintLayout  约束布局是允许使用灵活的方式定位和调整小部件的布局
    // 方式包括 相对定位 边距margin 居中定位和偏向 环形定位
    //相对定位 在水平轴和垂直轴上约束小部件
    // 水平轴： 左右起点终点 垂直轴：顶部、底部、文本基线
    //app:layout_constraintLeft_toRightOf="@+id/button4" 将给定小部件约束到其窗口小部件的一侧。取值可以是小部件的id或父布局（parent）
    //边距margin 将边距强制为两者之间的空间。这些值一般是正数或者0，且已经在strings.xml中提前定义好。
    //居中定位和偏向
    //环形定位 app:layout_constraintCircle 设置锚点 半径和角度
    //可见性 View.GONE 当组件属性设置为GONE时，组件不会显示，计算时，尺寸被视为0，相关的限制也会等于0
    //链 在单个轴（水平或垂直方向）提供类似行的行为。轴可以独立约束。
    // 组件双向链接在一起，被视为链。第一个元素为链头，链由链头上设置的属性控制。链头位于最左侧或顶部。

    //FrameLayout 帧布局是最简单的布局形式，子视图以堆栈形式绘制，最近添加的子项位于顶部。
    // 与线性布局属性类似。可以通过layout_gravity属性控制子控件显示在指定位置

    //TableLayout 表格布局 将子项排列为行或列的布局
    //TableLayout由许多TableRow对象组成，每个对象定义一行（也可以有其他子对象）
    //TableLayout容器不显示边框线，每个单元格可以容纳一个View对象
    //每个对象可以有多个单元格（列），如果没有指定列号，会自动从0增加，跳过的列号被认为是空单元格
    //表格的列数与拥有最多列的行相同。一个单元格可以跨越多列。
    //TableLayout中的其他View子类作为直接子项，视图将显示为跨越所有列的单行。
    //TableLayout的子项宽度总是 match_parent 高度默认是wrap_content
    //TableLayout的属性
    //android:collapseColumns 隐藏指定列
    //android:stretchColumns 设置指定列为可伸展列 ="*"将每一列拉伸至最大
    //android:shrinkColumns 设置指定列为可收缩列
    //列元素属性
    //android:layout_column 设置该控件在TableRow的列号
    //android:layout_span 设置改控件跨越的列数

    //RecyclerView 循环视图 android 5.0推出的，在support-v7包
    // 如果应用需要显示基于大型数据集（经常更改的数据）的滚动元素列表，则应使用RecyclerView
    //实现列早期的 ListView,ScrollView,GridLayout的功能。使用需要绑定Adapter控件

}
