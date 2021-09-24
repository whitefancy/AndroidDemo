package pub.whitefancy.androiddemo;

public class AndroidXUsage {
}
//androidX
//AndroidX概述
//androidx 命名空间中的工件包含 Android Jetpack 库。与支持库一样，androidx 命名空间中的库与 Android 平台分开提供，并向后兼容各个 Android 版本。
//AndroidX 对原始 Android 支持库进行了重大改进，后者现在已不再维护。androidx 软件包完全取代了支持库，不仅提供与支持库同等的功能，而且还提供了新的库。
//此外，AndroidX 还包括以下功能：
//AndroidX 中的所有软件包都使用一致的命名空间，以字符串 androidx 开头。支持库软件包已映射到对应的 androidx.* 软件包。如需获取所有旧类到新类以及旧构建工件到新构建工件的完整映射，请参阅软件包重构页面。
//与支持库不同，androidx 软件包会单独维护和更新。从版本 1.0.0 开始，androidx 软件包使用严格的语义版本控制。您可以单独更新项目中的各个 AndroidX 库。
//版本 28.0.0 是支持库的最后一个版本。我们将不再发布 android.support 库版本。 所有新功能都将在 androidx 命名空间中开发。
//AndroidX是Android团队用于在Jetpack中开发，测试，打包，发布和发布库的开源项目 。
//AndroidX是对原始Android 支持库的重大改进 。与支持库一样，AndroidX与Android操作系统分开提供，并提供跨Android版本的向后兼容性。AndroidX通过提供功能奇偶校验和新库完全取代了支持库。此外，AndroidX还包括以下功能：
//AndroidX中的所有软件包都以字符串开头，位于一致的命名空间androidx中。支持库包已映射到相应的androidx.*包中。有关所有旧类和构建工件的完整映射到新构件，请参阅“ 包重构”页面。
//与支持库不同，AndroidX软件包是单独维护和更新的。这些androidx包使用严格的语义版本控制， 从版本1.0.0开始。您可以单独更新项目中的AndroidX库。
//所有新的支持库开发都将在AndroidX库中进行。这包括维护原始支持库工件和引入新的Jetpack组件。
//其实就是对安卓过往v7,v4，design等系列包的一个整合，不用我们多次添加与核对版本号，大大方便了我们的开发，真的是早就该这样做了。
//使用AndroidX
//如果要在新项目中使用命名空间为 androidx 的库，就需要将编译 SDK 设置为 Android 9.0（API 级别 28）或更高版本，并在 gradle.properties 文件中将以下两个 Android Gradle 插件标志设置为 true。
//android.useAndroidX：该标志设置为 true 时，Android 插件会使用对应的 AndroidX 库，而非支持库。如果未指定，那么该标志默认为 false。
//android.enableJetifier：该标志设置为 true 时，Android 插件会通过重写其二进制文件来自动迁移现有的第三方库，以使用 AndroidX 依赖项。如果未指定，那么该标志默认为 false。
//如果要在新项目中使用AndroidX，则需要将compile SDK设置为Android 9.0（API级别28）或更高版本，并在gradle.properties文件中设置以下两个Android Gradle插件标志。
//android.useAndroidX：设置true为时,Android插件使用相应的AndroidX库而不是支持库。如果未指定,则默认情况下为false标志 。
//android.enableJetifier：设置true为时,Android插件会自动迁移现有的第三方库,通过重写其二进制文件来使用AndroidX。如果未指定，则默认情况下为false标志。
//x的最低实验条件
//AndroidStudio 3.2.0+
//gradle：gradle-4.6以上
//迁移到 AndroidX
//bookmark_border
//AndroidX 将原始支持库 API 替换为 androidx 命名空间中的软件包。只有软件包和 Maven 工件名称发生了变化；类名、方法名和字段名没有变化。
//注意：我们建议在单独的分支中执行迁移。此外，还应设法避免在执行迁移时重构代码。
//为什么要迁移？
//现在是时候从使用Android支持库迁移到AndroidX。这有四个原因：
//Android支持库已寿终正寝。28.0是Android支持名称空间的最新版本，并且不再维护该名称空间。因此，如果您需要以前在支持库中获得的错误修复或新功能，则需要迁移到AndroidX。
//更好的包装管理。使用AndroidX，您可以获得标准化和独立的版本控制，以及更好的标准化命名和更频繁的发行。
//其他库已迁移为使用AndroidX名称空间库，包括Google Play服务，Firebase，Butterknife，Mockito 2和SQLDelight等。
//所有新的Jetpack库都将在AndroidX名称空间中发布。因此，例如，要利用Jetpack Compose或CameraX，您需要迁移到AndroidX命名空间。
//前提条件
//执行迁移之前，请先将应用更新到最新版本。 我们建议您将项目更新为使用支持库的最终版本：版本 28.0.0。 这是因为，1.0.0 版本的 AndroidX 工件是与支持库 28.0.0 工件等效的二进制文件。
//使用 Android Studio 迁移现有项目
//使用 Android Studio 3.2 及更高版本，您只需从菜单栏中依次选择 Refactor > Migrate to AndroidX，即可将现有项目迁移到 AndroidX。
//重构命令使用两个标记。默认情况下，这两个标记在 gradle.properties 文件中都设为 true：
//android.useAndroidX=true
//Android 插件会使用对应的 AndroidX 库而非支持库。
//android.enableJetifier=true
//Android 插件会通过重写现有第三方库的二进制文件，自动将这些库迁移为使用 AndroidX。
//注意：内置的 Android Studio 迁移功能可能并不能完成所有操作。根据您的构建配置，您可能需要手动更新构建脚本和 Proguard 映射。例如，如果您在一个单独的构建文件中维护依赖项配置，请使用下述映射文件来检查依赖项并将其更新为对应的 AndroidX 软件包。
//改用类映射csv文件来实现查找和替换bash脚本
//如果您不使用Android Studio或采用“迁移到AndroidX”选项无法涵盖的更复杂的应用程序体系结构，则应改用类映射csv文件来实现查找和替换bash脚本。此脚本应找到android.support类的所有源代码实例，然后将其替换为同等的AndroidX。
//更具体地说，脚本应采用提供类映射的CSV文件，然后运行grep命令和sed命令以替换程序包名称。但是，由于这是一种蛮力的迁移方法，因此这种基本的查找和替换可能无法充分或正确地完成迁移。
//此外，更新也可以手动完成。
//如果您决定采用手动方法，请访问“迁移到AndroidX”页面，您可以在其中找到详细描述支持库软件包及其对应类映射为AndroidX的工件映射。您也可以从此页面下载CSV文件。
//就是这样，您现在应该拥有一个可编译并使用AndroidX的项目
//ProGuard和构建脚本
//迁移工具不会自动更新ProGuard和任何关联的内置脚本。因此，如果您正在使用这些文件并在其中包含程序包名称，则需要手动进行编辑。
//获取最新的稳定版本
//迁移工具引入了最新版本的AndroidX库。结果，您可能最终得到某些库的Alpha版本，而不是稳定版本。例如，迁移之前的支持库版本可能是：
//实施'com.android.support:appcompat-v7:28.0.0'
//迁移之后，您将使用相应AndroidX库的Alpha版本：
//实现'androidx.appcompat：appcompat：1.1.0-alpha01'
//因此，如果您希望使用该库的稳定版本，则需要手动更新该版本：
//实现'androidx.appcompat：appcompat：1.0.2'
//工件映射
//bookmark_border
//下表列出了从旧支持库工件到 androidx 的最新映射。您也可以下载包含这些映射信息的 CSV 格式文件。
//要了解 Jetpack 库的最新版本，请参阅版本页面。
//旧构建工件	AndroidX 构建工件
//android.arch.core:common	androidx.arch.core:core-common
//android.arch.core:core	androidx.arch.core:core
//android.arch.core:core-testing	androidx.arch.core:core-testing
//android.arch.core:runtime	androidx.arch.core:core-runtime
//android.arch.lifecycle:common	androidx.lifecycle:lifecycle-common
//android.arch.lifecycle:common-java8	androidx.lifecycle:lifecycle-common-java8
//android.arch.lifecycle:compiler	androidx.lifecycle:lifecycle-compiler
//android.arch.lifecycle:extensions	androidx.lifecycle:lifecycle-extensions
//android.arch.lifecycle:livedata	androidx.lifecycle:lifecycle-livedata
//android.arch.lifecycle:livedata-core	androidx.lifecycle:lifecycle-livedata-core
//android.arch.lifecycle:reactivestreams	androidx.lifecycle:lifecycle-reactivestreams
//android.arch.lifecycle:runtime	androidx.lifecycle:lifecycle-runtime
//android.arch.lifecycle:viewmodel	androidx.lifecycle:lifecycle-viewmodel
//android.arch.paging:common	androidx.paging:paging-common
//android.arch.paging:runtime	androidx.paging:paging-runtime
//android.arch.paging:rxjava2	androidx.paging:paging-rxjava2
//android.arch.persistence.room:common	androidx.room:room-common
//android.arch.persistence.room:compiler	androidx.room:room-compiler
//android.arch.persistence.room:guava	androidx.room:room-guava
//android.arch.persistence.room:migration	androidx.room:room-migration
//android.arch.persistence.room:runtime	androidx.room:room-runtime
//android.arch.persistence.room:rxjava2	androidx.room:room-rxjava2
//android.arch.persistence.room:testing	androidx.room:room-testing
//android.arch.persistence:db	androidx.sqlite:sqlite
//android.arch.persistence:db-framework	androidx.sqlite:sqlite-framework
//com.android.support.constraint:constraint-layout	androidx.constraintlayout:constraintlayout
//com.android.support.constraint:constraint-layout-solver	androidx.constraintlayout:constraintlayout-solver
//com.android.support.test.espresso.idling:idling-concurrent	androidx.test.espresso.idling:idling-concurrent
//com.android.support.test.espresso.idling:idling-net	androidx.test.espresso.idling:idling-net
//com.android.support.test.espresso:espresso-accessibility	androidx.test.espresso:espresso-accessibility
//com.android.support.test.espresso:espresso-contrib	androidx.test.espresso:espresso-contrib
//com.android.support.test.espresso:espresso-core	androidx.test.espresso:espresso-core
//com.android.support.test.espresso:espresso-idling-resource	androidx.test.espresso:espresso-idling-resource
//com.android.support.test.espresso:espresso-intents	androidx.test.espresso:espresso-intents
//com.android.support.test.espresso:espresso-remote	androidx.test.espresso:espresso-remote
//com.android.support.test.espresso:espresso-web	androidx.test.espresso:espresso-web
//com.android.support.test.janktesthelper:janktesthelper	androidx.test.jank:janktesthelper
//com.android.support.test.services:test-services	androidx.test:test-services
//com.android.support.test.uiautomator:uiautomator	androidx.test.uiautomator:uiautomator
//com.android.support.test:monitor	androidx.test:monitor
//com.android.support.test:orchestrator	androidx.test:orchestrator
//com.android.support.test:rules	androidx.test:rules
//com.android.support.test:runner	androidx.test:runner
//com.android.support:animated-vector-drawable	androidx.vectordrawable:vectordrawable-animated
//com.android.support:appcompat-v7	androidx.appcompat:appcompat
//com.android.support:asynclayoutinflater	androidx.asynclayoutinflater:asynclayoutinflater
//com.android.support:car	androidx.car:car-alpha5
//com.android.support:cardview-v7	androidx.cardview:cardview
//com.android.support:collections	androidx.collection:collection
//com.android.support:coordinatorlayout	androidx.coordinatorlayout:coordinatorlayout
//com.android.support:cursoradapter	androidx.cursoradapter:cursoradapter
//com.android.support:customtabs	androidx.browser:browser
//com.android.support:customview	androidx.customview:customview
//com.android.support:design	com.google.android.material:material-rc01
//com.android.support:documentfile	androidx.documentfile:documentfile
//com.android.support:drawerlayout	androidx.drawerlayout:drawerlayout
//com.android.support:exifinterface	androidx.exifinterface:exifinterface
//com.android.support:gridlayout-v7	androidx.gridlayout:gridlayout
//com.android.support:heifwriter	androidx.heifwriter:heifwriter
//com.android.support:interpolator	androidx.interpolator:interpolator
//com.android.support:leanback-v17	androidx.leanback:leanback
//com.android.support:loader	androidx.loader:loader
//com.android.support:localbroadcastmanager	androidx.localbroadcastmanager:localbroadcastmanager
//com.android.support:media2	androidx.media2:media2-alpha03
//com.android.support:media2-exoplayer	androidx.media2:media2-exoplayer-alpha01
//com.android.support:mediarouter-v7	androidx.mediarouter:mediarouter
//com.android.support:multidex	androidx.multidex:multidex
//com.android.support:multidex-instrumentation	androidx.multidex:multidex-instrumentation
//com.android.support:palette-v7	androidx.palette:palette
//com.android.support:percent	androidx.percentlayout:percentlayout
//com.android.support:preference-leanback-v17	androidx.leanback:leanback-preference
//com.android.support:preference-v14	androidx.legacy:legacy-preference-v14
//com.android.support:preference-v7	androidx.preference:preference
//com.android.support:print	androidx.print:print
//com.android.support:recommendation	androidx.recommendation:recommendation
//com.android.support:recyclerview-selection	androidx.recyclerview:recyclerview-selection
//com.android.support:recyclerview-v7	androidx.recyclerview:recyclerview
//com.android.support:slices-builders	androidx.slice:slice-builders
//com.android.support:slices-core	androidx.slice:slice-core
//com.android.support:slices-view	androidx.slice:slice-view
//com.android.support:slidingpanelayout	androidx.slidingpanelayout:slidingpanelayout
//com.android.support:support-annotations	androidx.annotation:annotation
//com.android.support:support-compat	androidx.core:core
//com.android.support:support-content	androidx.contentpager:contentpager
//com.android.support:support-core-ui	androidx.legacy:legacy-support-core-ui
//com.android.support:support-core-utils	androidx.legacy:legacy-support-core-utils
//com.android.support:support-dynamic-animation	androidx.dynamicanimation:dynamicanimation
//com.android.support:support-emoji	androidx.emoji:emoji
//com.android.support:support-emoji-appcompat	androidx.emoji:emoji-appcompat
//com.android.support:support-emoji-bundled	androidx.emoji:emoji-bundled
//com.android.support:support-fragment	androidx.fragment:fragment
//com.android.support:support-media-compat	androidx.media:media
//com.android.support:support-tv-provider	androidx.tvprovider:tvprovider
//com.android.support:support-v13	androidx.legacy:legacy-support-v13
//com.android.support:support-v4	androidx.legacy:legacy-support-v4
//com.android.support:support-vector-drawable	androidx.vectordrawable:vectordrawable
//com.android.support:swiperefreshlayout	androidx.swiperefreshlayout:swiperefreshlayout
//com.android.support:textclassifier	androidx.textclassifier:textclassifier
//com.android.support:transition	androidx.transition:transition
//com.android.support:versionedparcelable	androidx.versionedparcelable:versionedparcelable
//com.android.support:viewpager	androidx.viewpager:viewpager
//com.android.support:wear	androidx.wear:wear
//com.android.support:webkit	androidx.webkit:webkit
//类映射https://developer.android.com/jetpack/androidx/migrate/class-mappings

