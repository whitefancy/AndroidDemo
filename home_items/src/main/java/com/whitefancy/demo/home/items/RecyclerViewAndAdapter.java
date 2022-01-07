package com.whitefancy.demo.home.items;

public class RecyclerViewAndAdapter {
}
//通知任何已注册的观察者数据集已更改。
//
//有两种不同类别的数据更改事件，项目更改和结构更改。项目更改是指单个项目的数据已更新但未发生位置更改。结构变化是指在数据集中插入、删除或移动项目。
//
//此事件未指定数据集发生了什么变化，迫使任何观察者假设所有现有项目和结构可能不再有效。LayoutManagers 将被迫完全重新绑定和重新布局所有可见视图。
//
//RecyclerView将尝试为stable IDs在使用此方法时报告它们具有的适配器合成可见的结构更改事件。这有助于实现动画和视觉对象持久性，但仍需要重新分配和重新布局单个项目视图。
//
//如果您正在编写适配器，如果可以的话，使用更具体的更改事件总是更有效。依靠notifyDataSetChanged() 作为最后的手段。
//观察者模式
//适配器的基类
//
//适配器提供从特定于应用程序的数据集到显示在RecyclerView.
//RecyclerView 可以让您轻松高效地显示大量数据。您提供数据并定义每个列表项的外观，而 RecyclerView 库会根据需要动态创建元素。
//RecyclerView 会回收这几个元素。当列表项滚动出屏幕能力时，RecyclerView 不会模糊其视图。
//是一个聪明的观察者，会自己设计显示
// 相反，RecyclerView 项可用于屏幕上滚动的新列表重用该视图。这种重显提高性能，改进应用响应可以并降低效率。
//关键类
//将多个不同的类搭配使用，可构建动态列表。
//
//RecyclerView是包含与您的数据对应的视图的ViewGroup。它的视图就是视图，因此，将RecyclerView添加到组件中的方式与添加任何其他界面元素相同。
//
//列表中的每个独立元素都由一个 ViewHolder 对象进行定义。创建 ViewHold 时，它并没有任何关联的数据。创建 ViewHolder 后，RecyclerView绑定其绑定到其数据。您可以通过扩展RecyclerView.ViewHolder来定义 ViewHolder。
//
//RecyclerView 会请求这些视图，并通过 Adapter 中调用方法，将视图绑定到其数据。您可以通过扩展RecyclerView.Adapter来定义适配器。
//
//布局管理器重点排列列表中的各个元素。您可以使用 RecyclerView 库提供的某个布局管理器，也可以定义自己的布局管理器。布局管理器均基于库的LayoutManager抽象类。
//注意：RecyclerView 除了是类的名称，也是库的名称。在本页中，采用code font字体的RecyclerView总是表示 RecyclerView 库中的类。
//将多个不同的类搭配使用，可构建动态列表。
//
//RecyclerView是包含与您的数据对应的视图的ViewGroup。它的视图就是视图，因此，将RecyclerView添加到组件中的方式与添加任何其他界面元素相同。
//
//列表中的每个独立元素都由一个 ViewHolder 对象进行定义。创建 ViewHold 时，它并没有任何关联的数据。创建 ViewHolder 后，RecyclerView绑定其绑定到其数据。您可以通过扩展RecyclerView.ViewHolder来定义 ViewHolder。
//
//RecyclerView 会请求这些视图，并通过 Adapter 中调用方法，将视图绑定到其数据。您可以通过扩展RecyclerView.Adapter来定义适配器。
//
//布局管理器重点排列列表中的各个元素。您可以使用 RecyclerView 库提供的某个布局管理器，也可以定义自己的布局管理器。布局管理器均基于库的LayoutManager抽象类。
//实现 RecyclerView 的步骤
//如果您能使用 RecyclerView，那么您需要完成几项工作。下面几部分对这些工作进行了详细介绍。
//
//首先，确定列表或网格的外观。一般，您可以使用Recycler查看库的某一标准布局管理器。
//
//设计列表中每个元素的外观和行为。根据此设计，扩展ViewHolder类。您的ViewHolder版本提供了列表项的所有功能。您的ViewHolder的英文View的封装容器，该御姐视图由RecyclerView管理。
//
//定义用于将您的数据与ViewHolder视图相关联的Adapter。
//
//此外，还您可以使用高级自定义选项对话根据自己的具体需求定制RecyclerView。
//规划布局
//RecyclerView 中的列表由LayoutManager类项负责安排。RecyclerView 库提供了三种布局管理器，用于处理最常见的布局情况：
//
//LinearLayoutManager 将各个项排列在维列表中。
//GridLayoutManager 将所有项排列在二维条中：
//如果垂直排列，GridLayoutManager会尽量使每行中所有元素的宽度和高度相同，但不同的行可以有不同的高度。
//如果水平排列，GridLayoutManager会尽量使每列中所有元素的宽度和高度相同，但不同的可以有不同的宽度。
//StaggeredGridLayoutManager与GridLayoutManager类似，但不要求同一行中的列表项具有相同的（垂直网格有此要求）或同一列中的列表项具有相同的宽度（水平网格有此要求）。或相同列中的列表项可能会错落不
