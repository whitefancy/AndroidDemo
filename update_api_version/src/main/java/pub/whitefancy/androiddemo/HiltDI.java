package pub.whitefancy.androiddemo;

public class HiltDI {
}
//使用 Hilt 实现依赖项注入
//Hilt 是 Android 的依赖项注入库，可减少在项目中执行手动依赖项注入的样板代码。执行手动依赖项注入要求您手动构造每个类及其依赖项，并借助容器重复使用和管理依赖项。
//Hilt 通过为项目中的每个 Android 类提供容器并自动管理其生命周期，提供了一种在应用中使用 DI（依赖项注入）的标准方法。Hilt 在热门 DI 库 Dagger 的基础上构建而成，因而能够受益于 Dagger 的编译时正确性、运行时性能、可伸缩性和 Android Studio 支持。如需了解详情，请参阅 Hilt 和 Dagger。
//添加依赖项
//首先，将 hilt-android-gradle-plugin 插件添加到项目的根级 build.gradle 文件中：
//buildscript {
//    ...
//    dependencies {
//        ...
//        classpath 'com.google.dagger:hilt-android-gradle-plugin:2.28-alpha'
//    }
//}
//然后，应用 Gradle 插件并在 app/build.gradle 文件中添加以下依赖项：
//...
//apply plugin: 'kotlin-kapt'
//apply plugin: 'dagger.hilt.android.plugin'
//android {
//    ...
//}
//dependencies {
//    implementation "com.google.dagger:hilt-android:2.28-alpha"
//    kapt "com.google.dagger:hilt-android-compiler:2.28-alpha"
//}
//注意：同时使用 Hilt 和数据绑定的项目需要 Android Studio 4.0 或更高版本。
//Hilt 使用 Java 8 功能。如需在项目中启用 Java 8，请将以下代码添加到 app/build.gradle 文件中：
//android {
//  ...
//  compileOptions {
//    sourceCompatibility JavaVersion.VERSION_1_8
//    targetCompatibility JavaVersion.VERSION_1_8
//  }
//}
//本指南介绍了 Hilt 的基本概念及其生成的容器，，还演示了如何开始在现有应用中使用 Hilt。
//Hilt 应用类
//所有使用 Hilt 的应用都必须包含一个带有 @HiltAndroidApp 注释的 Application 类。
//@HiltAndroidApp 会触发 Hilt 的代码生成操作，生成的代码包括应用的一个基类，该基类充当应用级依赖项容器。
//KOTLIN
//JAVA
//@HiltAndroidApp
//public class ExampleApplication extends Application { ... }
//生成的这一 Hilt 组件会附加到 Application 对象的生命周期，并为其提供依赖项。此外，它也是应用的父组件，这意味着，其他组件可以访问它提供的依赖项。
//将依赖项注入 Android 类
//在 Application 类中设置了 Hilt 且有了应用级组件后，Hilt 可以为带有 @AndroidEntryPoint 注释的其他 Android 类提供依赖项：
//KOTLIN
//JAVA
//@AndroidEntryPoint
//public class ExampleActivity extends AppCompatActivity { ... }
//Hilt 目前支持以下 Android 类：
//Application（通过使用 @HiltAndroidApp）
//Activity
//Fragment
//View
//Service
//BroadcastReceiver
//如果您使用 @AndroidEntryPoint 为某个 Android 类添加注释，则还必须为依赖于该类的 Android 类添加注释。例如，如果您为某个 Fragment 添加注释，则还必须为使用该 Fragment 的所有 Activity 添加注释。
//注意：在 Hilt 对 Android 类的支持方面还要注意以下几点：
//Hilt 仅支持扩展 ComponentActivity 的 Activity，如 AppCompatActivity。
//Hilt 仅支持扩展 androidx.Fragment 的 Fragment。
//Hilt 不支持保留的 Fragment。
//@AndroidEntryPoint 会为项目中的每个 Android 类生成一个单独的 Hilt 组件。这些组件可以从它们各自的父类接收依赖项，如组件层次结构中所述。
//如需从组件获取依赖项，请使用 @Inject 注释执行字段注入：
//KOTLIN
//JAVA
//@AndroidEntryPoint
//public class ExampleActivity extends AppCompatActivity {
//  @Inject
//  AnalyticsAdapter analytics;
//  ...
//}
//注意：由 Hilt 注入的字段不能为私有字段。尝试使用 Hilt 注入私有字段会导致编译错误。
//Hilt 注入的类可以有同样使用注入的其他基类。如果这些类是抽象类，则它们不需要 @AndroidEntryPoint 注释。
//定义 Hilt 绑定
//为了执行字段注入，Hilt 需要知道如何从相应组件提供必要依赖项的实例。“绑定”包含将某个类型的实例作为依赖项提供所需的信息。
//向 Hilt 提供绑定信息的一种方法是构造函数注入。在某个类的构造函数中使用 @Inject 注释，以告知 Hilt 如何提供该类的实例：
//KOTLIN
//JAVA
//public class AnalyticsAdapter {
//  private final AnalyticsService service;
//  @Inject
//  AnalyticsAdapter(AnalyticsService service) {
//    this.service = service;
//  }
//  ...
//}
//在一个类的代码中，带有注释的构造函数的参数即是该类的依赖项。在本例中，AnalyticsService 是 AnalyticsAdapter 的一个依赖项。因此，Hilt 还必须知道如何提供 AnalyticsService 的实例。
//注意：在构建时，Hilt 会为 Android 类生成 Dagger 组件。然后，Dagger 会走查您的代码，并执行以下步骤：
//构建并验证依赖关系图，确保没有未满足的依赖关系且没有依赖循环。
//生成它在运行时用来创建实际对象及其依赖项的类。
//如需详细了解 Android 类被注入的是哪个生命周期回调，请参阅组件生命周期。