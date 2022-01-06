package com.whitefancy.demo.home.items;

public class ActivityViewWindow {
}
//Android Activity、Window、View的关系
//想分析一下触摸事件的分发响应机制，却发现Activity、View、ViewGroup中都存在分发事件的方法
//Activity：是Android的四大组件之一。它是View对象的容器，也是我们界面的载体。它可用于显示界面。它有一个SetContentView()方法，可以将我们定义的布局设置到界面上。
//
//View：它是一个视图对象，它实现了 KeyEvent.Callback 和 Drawable.Callback。
//
//Window：是一个抽象类，是一个顶层窗口，它唯一的实例是PhoneWindow，它提供了标准的用户界面策略，如背景、标题、区域和默认按键处理。
//View包含很多，TextView、Imageview、Listview、Button..是一个将不同图形一一显示的对象。
// 我们可以通过xml来布局view，也可以通过newView()，然后通过addview方法或者动态或者静态的将其添加到Activity的布局中。
// 我们都知道我们已经定义了layoutlayout，可以通过SetContentView给Activity设置，
// Activity中的SetContentView()方法调用Window的SetContentView方法，也就是View最终通过活动。

//为什么源码中很多方法就一行throw new RuntimeException("Stub!")
// 在使用某些类的方法时，发现其内部就一行throw new RuntimeException("Stub!")，但是实际运行中并没有抛出该错误，该方法也并没有语法报错。
//
//因此可能是系统设计者故意隐藏此部分的实现源码。
//
//使用的Android Studio或者其他IDE看jar包的源码的时候，编译工具只让你看到方法签名，不让你看方法的实现；
//Android SDK自带的Source源码包很小，并没有包括所有的Android Framework的源码，仅仅提供给应用开发参考用，
// 一些比较少用的系统类的源码并没有给出，所以有时候你会看到throw new RuntimeException("Stub!")。
//此外，在IDE里看源码的时候，有时候一些方法或者类会出现报红（找不到）的情况：这是因为这些方法或者类是被Android SDK隐藏的，
// 出于安全或者某些原因，这些API不能暴露给应用层的开发者，所以编译完成的android.jar包里会把这些API隐藏掉，
// 而我们的Android项目是依赖android.jar的，查看源码的时候，IDE会自动去android.jar找对应的API，自然会找不到。
// 当然，这些API在ROM中是实际存在的，有些开发者发现了一些可以修改系统行为的隐藏API，在应用层通过反射的方式强行调用这些API执行系统功能，这种手段也是一种HACK。

//Android Runtime
//对于运行 Android 5.0（API 级别 21）或更高版本的设备，每个应用都在其自己的进程中运行，
// 并且有其自己的 Android Runtime (ART) 实例。ART 编写为通过执行 DEX 文件在低内存设备上运行多个虚拟机，
// DEX 文件是一种专为 Android 设计的字节码格式，经过优化，使用的内存很少。
// 编译工具链（例如 Jack）将 Java 源代码编译为 DEX 字节码，使其可在 Android 平台上运行。

//原生 C/C++ 库
//许多核心 Android 系统组件和服务（例如 ART 和 HAL）构建自原生代码，需要以 C 和 C++ 编写的原生库。
// Android 平台提供 Java 框架 API 以向应用显示其中部分原生库的功能。
// 例如，您可以通过 Android 框架的 Java OpenGL API 访问 OpenGL ES，以支持在应用中绘制和操作 2D 和 3D 图形。
//
//如果开发的是需要 C 或 C++ 代码的应用，可以使用 Android NDK 直接从原生代码访问某些原生平台库。

//Activity 类是 Android 应用的关键组件，而 Activity 的启动和组合方式则是该平台应用模型的基本组成部分。
// 在编程范式中，应用是通过 main() 方法启动的，而 Android 系统与此不同，它会调用与其生命周期特定阶段相对应的特定回调方法来启动 Activity 实例中的代码。

//Activity 的概念
//移动应用体验与桌面体验的不同之处在于，用户与应用的互动并不总是在同一位置开始，而是经常以不确定的方式开始。
// 例如，如果您从主屏幕打开电子邮件应用，可能会看到电子邮件列表，如果您通过社交媒体应用启动电子邮件应用，则可能会直接进入电子邮件应用的邮件撰写界面。
//
//Activity 类的目的就是促进这种范式的实现。当一个应用调用另一个应用时，调用方应用会调用另一个应用中的 Activity，而不是整个应用。
// 通过这种方式，Activity 充当了应用与用户互动的入口点。您可以将 Activity 实现为 Activity 类的子类。
//
//Activity 提供窗口供应用在其中绘制界面。此窗口通常会填满屏幕，但也可能比屏幕小，并浮动在其他窗口上面。
// 通常，一个 Activity 实现应用中的一个屏幕。例如，应用中的一个 Activity 实现“偏好设置”屏幕，而另一个 Activity 实现“选择照片”屏幕。

//虽然应用中的各个 Activity 协同工作形成统一的用户体验，但每个 Activity 与其他 Activity 之间只存在松散的关联，
// 应用内不同 Activity 之间的依赖关系通常很小。事实上，Activity 经常会启动属于其他应用的 Activity。例如，浏览器应用可能会启动社交媒体应用的“分享”Activity。
//
//要在应用中使用 Activity，您必须在应用的清单中注册关于 Activity 的信息，并且必须适当地管理 Activity 的生命周期。

//View 对象通常称为“微件”，可以是多个子类之一，例如 Button 或 TextView。
// ViewGroup 对象通常称为“布局”，可以是提供不同布局结构的众多类型之一，例如 LinearLayout 或 ConstraintLayout。
//您可通过两种方式声明布局：
//
//在 XML 中声明界面元素。Android 提供对应 View 类及其子类的简明 XML 词汇，如用于微件和布局的词汇。
//您也可使用 Android Studio 的 Layout Editor，并采用拖放界面来构建 XML 布局。
//
//在运行时实例化布局元素。您的应用能以程序化方式创建 View 对象和 ViewGroup 对象（并操纵其属性）。

//您可以利用 Android 的 XML 词汇，按照在 HTML 中创建包含一系列嵌套元素的网页的相同方式快速设计界面布局及其包含的屏幕元素。
//
//每个布局文件都必须只包含一个根元素，并且该元素必须是视图对象或 ViewGroup 对象。
// 定义根元素后，您可以子元素的形式添加其他布局对象或微件，从而逐步构建定义布局的视图层次结构。
// 例如，以下 XML 布局使用垂直 LinearLayout 来储存 TextView 和 Button：
//在 XML 中声明布局后，请以 .xml 扩展名将文件保存在您 Android 项目的 res/layout/ 目录中，以便该文件能正确编译。

//加载 XML 资源
//当您编译应用时，系统会将每个 XML 布局文件编译成 View 资源。
// 您应在 Activity.onCreate() 回调实现内加载应用代码中的布局资源。
// 通过调用 setContentView()，并以 R.layout.layout_file_name 形式向应用代码传递对布局资源的引用，您即可执行此操作。
// 例如，如果您的 XML 布局保存为 main_layout.xml，

//ID
//任何 View 对象均可拥有与之关联的整型 ID，用于在结构树中对 View 对象进行唯一标识。
// 编译应用后，系统会以整型形式引用此 ID，但在布局 XML 文件中，系统通常会以字符串的形式在 id 属性中指定该 ID。
// 这是所有 View 对象共有的 XML 属性（由 View 类定义），并且您会经常使用该属性。XML 标记内部的 ID 语法是：
//ID 无需在整个结构树中具有唯一性，但其在您要搜索的结构树部分中应具有唯一性（要搜索的部分往往是整个结构树，因此最好尽可能具有全局唯一性）。
