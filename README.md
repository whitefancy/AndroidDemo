 Android 应用的标准项目结构
在Android Studio（简称AS）中，一个Android项目的文件结构有许多种表现形式，我称之为视图。其中，Android视图是AS默认的视图，在新建一个项目之后，AS就会将项目的文件结构以Android视图表现出来；Project视图是程序猿们最喜欢的视图，几乎所有教科书、教程里都建议切换Project视图，因为Android视图中会缺少很多文件夹和文件，并且有些文件夹的名称会被AS替换显示，Project视图中的文件结构就是项目在硬盘上真实的文件结构。
app/manifests	app/src/main/AndroidManifest.xml	存放AndroidManifest.xml文件，整个项目的配置文件，包括程序版本、四大组件注册、权限声明等。
app/java	app/src/main/java	Java源文件夹，存放项目中所有的Java代码源文件。
app/cpp	app/src/main/jni	C/C++源文件夹，存放项目中所有的C和C++代码源文件。
app/aidl	app/src/main/aidl	AIDL源文件夹，存放项目中的Android接口定义语言代码源文件。
app/renderscript	app/src/main/rs	RenderScript源文件夹，存放项目中的RenderScript代码源文件。
app/assets	app/src/main/assets	存放程序员想使用的任何资源文件

生成文件
.gradle和.idea
这两个目录下放置的都是Android Studio自动生成的一些文件，我们无须关心，也不要去手动编辑。
build
这个目录你也不需要过多关心，它主要包含了一些在编译时自动生成的文件。
这个目录下包含了gradle wrapper的配置文件，使用gradle wrapper的方式不需要提前将gradle下载好，而是会自动根据本地的缓存情况决定是否需要联网下载gradle。Android Studio默认没有启用gradle wrapper的方式，如果需要打开，可以点击Android Studio导航栏→File→Settings→Build, Execution, Deployment→Gradle，进行配置更改。
.gitignore
这个文件是用来将指定的目录或文件排除在版本控制之外的，关于版本控制我们将在第5章中开始正式的学习。
资源文件
app
项目中的代码、资源等内容几乎都是放置在这个目录下的，我们后面的开发工作也基本都是在这个目录下进行的

gradle.properties
这个文件是全局的gradle配置文件，在这里配置的属性将会影响到项目中所有的gradle编译脚本。
gradlew和gradlew.bat
这两个文件是用来在命令行界面中执行gradle命令的，其中gradlew是在Linux或Mac系统中使用的，gradlew.bat是在Windows系统中使用的。
HelloWorld.iml
iml文件是所有IntelliJ IDEA项目都会自动生成的一个文件（Android Studio是基于IntelliJ IDEA开发的），用于标识这是一个IntelliJ IDEA项目，我们不需要修改这个文件中的任何内容。
local.properties
这个文件用于指定本机中的Android SDK路径，通常内容都是自动生成的，我们并不需要修改。除非你本机中的Android SDK位置发生了变化，那么就将这个文件中的路径改成新的位置即可。
local.properties
为构建系统配置本地环境属性，其中包括：
ndk.dir - NDK 的路径。此属性已被弃用。NDK 的所有下载版本都将安装在 Android SDK 目录下的 ndk 目录中。
sdk.dir - SDK 的路径。
cmake.dir - CMake 的路径。
ndk.symlinkdir - 在 Android Studio 3.5 及更高版本中，创建指向 NDK 的符号链接，该符号链接的路径可比 NDK 安装路径短。


app目录
build
这个目录和外层的build目录类似，主要也是包含了一些在编译时自动生成的文件，不过它里面的内容会更多更杂。
libs
如果你的项目中使用到了第三方jar包，就需要把这些jar包都放在libs目录下，放在这个目录下的jar包都会被自动添加到构建路径里去。
src
项目资源

.gitignore
这个文件用于将app模块内的指定的目录或文件排除在版本控制之外，作用和外层的.gitignore文件类似。
app.iml
IntelliJ IDEA项目自动生成的文件，我们不需要关心或修改这个文件中的内容。
模块级构建文件app/build.gradle
这是app模块的gradle构建脚本，这个文件中会指定很多项目构建相关的配置。
proguard-rules.pro
这个文件用于指定项目代码的混淆规则，当代码开发完成后打成安装包文件。
混淆处理的目的是通过缩短应用的类、方法和字段的名称来缩减应用的大小。
虽然混淆处理不会从应用中移除代码，但如果应用的 DEX 文件将许多类、方法和字段编入索引，那么混淆处理将可以显著缩减应用的大小。不过，由于混淆处理会对代码的不同部分进行重命名，因此在执行某些任务（如检查堆栈轨迹）时需要使用额外的工具。
解码经过混淆处理的堆栈轨迹
R8 对代码进行混淆处理后，理解堆栈轨迹的难度将会极大增加，因为类和方法的名称可能有变化。除了重命名之外，R8 还可能会更改出现在堆栈轨迹中的行号，以便在写入 DEX 文件时进一步缩减大小。幸运的是，R8 在每次运行时都会创建一个 mapping.txt 文件，其中列出了经过混淆处理的类、方法和字段的名称与原始名称的映射关系。此映射文件还包含用于将行号映射回原始源文件行号的信息。R8 会将此文件保存在 <module-name>/build/outputs/mapping/<build-type>/ 目录中。
在 Google Play 上发布应用时，您可以上传每个 APK 版本对应的 mapping.txt 文件。然后，Google Play 会根据用户报告的问题对传入的堆栈轨迹进行去混淆处理，以便您可以在 Play 管理中心查看这些堆栈轨迹。


app/src目录
src/test
此处是用来编写Unit Test测试用例的，是对项目进行自动化测试的另一种方式。
src/androidTest
此处是用来编写Android Test测试用例的，可以对项目进行一些自动化测试。
src/main/java
app/src/main/java：项目的源代码
毫无疑问，java目录是放置我们所有Java代码的地方，展开该目录，你将看到我们刚才创建的HelloWorldActivity文件就在里面。
Android 中的资源文件
src/main/assets
Assets
app/src/main/assets：放置原生文件，里面的文件会保留原有格式，文件的读取需要通过流
assets
assets目录下存放原生资源文件，可以存放一些图片，html，js, css等文件。
因为系统在编译的时候不会编译 assets 下的资源文件，所以不能通过 R.XXX.ID 的方式访问它们。那我么能不能通过该资源的绝对路径去访问它们呢？因为apk安装之后会放在/data/app/**.apk目录下，以apk形式存在，asset/res/raw被绑定在apk里，并不会解压到/data/data/YourApp目录下去，所以无法直接获取到 assets 的绝对路径，因为它们根本就没有。
assets 文件夹资源的访问
Android 系统提供了 AssetManager 类来访问 assets 文件里的资源。
assets 文件里的文件都是保持原始的文件格式，需要使用 AssetManager 以字节流的形式读取文件。
先在 Activity 里面调用 getAssets() 来获取 AssetManager 引用。
再用 AssetManager 的 open(String fileName, int accessMode) 方法，指定读取的文件以及访问模式，就能得到输入流 InputStream。
然后用已经 open file 的 inputStream 读取文件，读取完成后记得 inputStream.close() 。
调用 AssetManager.close() 关闭 AssetManager。
src/main/res
图片放在drawable目录下，
布局放在layout目录下，
字符串放在values目录下，
所以你不用担心会把整个res目录弄得乱糟糟的。
app/src/main/res：项目的资源
app/src/main/res/anim：存放动画的XML文件
app/src/main/res/drawable：存放各种位图文件(.png，.jpg，.9png，.gif等)和drawable类型的XML文件
app/src/main/res/drawable-v24：存放自定义Drawables类（Android API 24开始，可在XML中使用）
app/src/main/res/layout：存放布局文件
app/src/main/res/menu：存放菜单文件
app/src/main/res/mipmap-hdpi：存放高分辨率图片资源
app/src/main/res/mipmap-mdpi：存放中等分辨率图片资源
app/src/main/res/mipmap-xdpi：存放超高分辨率图片资源
app/src/main/res/mipmap-xxdpi：存放超超分辨率图片资源
app/src/main/res/mipmap-xxxdpi：存放超超超高分辨率图片资源
app/src/main/res/raw：存放各种原生资源(音频，视频，一些XML文件等)
app/src/main/res/values： 存放各种配置资源（颜色，尺寸，样式，字符串等）
app/src/main/res/values/attrs.xml：自定义控件时用的较多，自定义控件的属性
app/src/main/res/values/arrays.xml：定义数组资源
app/src/main/res/values/colors.xml：定义颜色资源
app/src/main/res/values/dimens.xml：定义尺寸资源
app/src/main/res/values/string.xml：定义字符串资源
app/src/main/res/values/styles.xml：定义样式资源
app/src/main/res/values-v11：在API 11+的设备上调用
app/src/main/res/values-v14：在API 14+的设备上调用
app/src/main/res/values-v21：在API 21+的设备上调用
Android 资源文件大致可以分为两种：res/raw 和 assets
res/raw
res/raw 目录下存放可编译的资源文件
这种资源文件系统会在 R.Java 里面自动生成该资源文件的 ID，所以访问这种资源文件比较简单，通过 R.XXX.ID 即可。
res/raw 和 assets 对比
res/raw和assets的相同点：
两者目录下的文件在打包后会原封不动的保存在apk中，不会被变成二进制。
res/raw和assets的不同点：
1、res/raw 中的文件会被映射到 R.Java 文件中，访问的时候直接使用资源 ID 即 R.XXX.ID；
assets 文件夹下的文件不会被映射到 R.Java 中，访问的时候需要 AssetManager 类。
2、res/raw 不可以有目录结构；
而 assets 则可以有目录结构，也就是 assets 目录下可以再建立文件夹。
3、读取文件资源方式不同：
读取 res/raw 下的文件资源：
InputStream is =getResources().openRawResource(R.id.filename);
读取assets下的文件资源：
AssetManager am = getAssets();
InputStream is = am.open(“filename”);
注意1：来自 Resources 和 Assets 中的文件只可以读取而不能进行写的操作
注意2：Google 的 Android 系统处理 Assert 有个 bug，在 AssertManager 中不能处理单个超过1MB的文件，不然会报异常，raw 没这个限制，可以放个4MB的Mp3文件没问题。
注意3：assets 文件夹是存放不进行编译加工的原生文件，即该文件夹里面的文件不会像 xml， java 文件被预编译，可以存放一些图片，html，js, css 等文件。
res/raw 和 assets 使用场景
由于 res/raw 是Resources(res)的子目录，Android会自动的为这目录中的所有资源文件生成一个ID，这个ID会被存储在R类当中，作为一个文件的引用。这意味着这个资源文件可以很容易的被Android的类和方法访问到，甚至在Android XML文件中你也可以@raw/的形式引用到它。在Android中，使用ID是访问一个文件最快捷的方式。MP3和Ogg文件放在这个目录下是比较合适的。
assets 目录更像一个附录类型的目录，Android不会为这个目录中的文件生成ID并保存在R类当中，因此它与Android中的一些类和方法兼容度更低。同时，由于你需要一个字符串路径来获取这个目录下的文件描述符，访问的速度会更慢。但是把一些文件放在这个目录下会使一些操作更加方便，比方说拷贝一个数据库文件到系统内存中。要注意的是，你无法在Android XML文件中引用到assets目录下的文件，只能通过AssetManager来访问这些文件。数据库文件和游戏数据等放在这个目录下是比较合适的。
追加的语言限定符
Android 系统会根据向资源目录名称追加的语言限定符（如为法语字符串值追加 res/values-fr/）和用户的语言设置，对您的界面应用相应的语言字符串。
Android 支持许多不同的备用资源限定符。限定符是资源目录名称中加入的短字符串，用于定义这些资源适用的设备配置。例如，您应根据设备的屏幕方向和尺寸为 Activity 创建不同的布局。当设备屏幕为纵向（长型）时，您可能想要一种垂直排列按钮的布局；但当屏幕为横向（宽型）时，可以按水平方向排列按钮。如要根据方向更改布局，您可以定义两种不同的布局，然后对每个布局的目录名称应用相应的限定符。然后，系统会根据当前设备方向自动应用相应的布局。
res/
目录包含所有资源（在子目录中）：一个图像资源、两个布局资源、启动器图标的
mipmap/
目录以及一个字符串资源文件。资源目录名称非常重要，具体说明请见表 1。
raw/
需以原始形式保存的任意文件。如要使用原始 InputStream 打开这些资源，请使用资源 ID（即 R.raw.filename）调用 Resources.openRawResource()。
但是，如需访问原始文件名和文件层次结构，则可以考虑将某些资源保存在 assets/ 目录（而非 res/raw/）下。assets/ 中的文件没有资源 ID，因此您只能使用 AssetManager 读取这些文件。
为一组资源指定配置特定的备用资源：
1. 在 res/ 中创建以 <resources_name>-<config_qualifier> 形式命名的新目录。<resources_name> 是相应默认资源的目录名称（如表 1 中所定义）。
a. <qualifier> 是指定要使用这些资源的各个配置的名称（如表 2 中所定义）。
您可以追加多个 <qualifier>。以短划线将其分隔。
● 限定符命名规则
● 以下是一些关于使用配置限定符名称的规则：
您可以为单组资源指定多个限定符，并使用短划线分隔。例如，
drawable-en-rUS-land
适用于屏幕方向为横向的美国英语设备。
○ 这些限定符必须遵循表 2 中列出的顺序。例如：错误：
drawable-hdpi-port/
● 正确：
drawable-port-hdpi/
不能嵌套备用资源目录。例如，您的目录不能为
res/drawable/drawable-en/
。
● 值不区分大小写。在处理之前，资源编译器会将目录名称转换为小写，以免不区分大小写的文件系统出现问题。名称中使用的所有大写字母只是为了便于认读。
每种限定符类型仅支持一个值。例如，若要对西班牙语和法语使用相同的可绘制对象文件，则您不能拥有名为
drawable-rES-rFR/
的目录，而是需要两个包含相应文件的资源目录，如
drawable-rES/
和
drawable-rFR/
。然而，您实际无需在两处复制相同的文件。相反，您可以创建指向资源的别名。请参阅下面的创建别名资源。



Apk目录结构
assets
原生资源文件，不会被压缩或者处理
classes.dex
java 代码通过 javac 转化成 class 文件，再通过 dx 文件转化成 dex 文件。如果有多个 dex 文件，其命名会是这样的：
classes.dex classes2.dex classes3.dex ...
在其中保存了类信息。
lib/
保存了 native 库 .so 文件，其中会根据 cpu 型号划分不同的目录，比如 ARM，x86 等等。
res/
保存了处理后的二进制资源文件。
resources.arsc
保存了资源 id 名称以及资源对应的值／路径的映射。
META-INF
META-INF的文件夹里边存储的是关于签名的一些信息。
META-INF/
其中将apk包解压出来，进入META-INF目录，发现会有3个文件：CERT.RSA，CERT.SF，MANIFEST.MF。
用来验证 APK 签名，其中有三个重要的文件 MANIFEST.MT，CERT.SF，CERT.RSA。
在android系统中，不同App之间是依靠包名、数字签名共同来进行区分的。虽然Google建议我们用自己的域名的反写作为包名的前缀来定义包名（例如com.google.），但是这并不能做到万无一失，我们不能单单利用包名来区分apk，所以提出了签名的概念。顾名思义，就是在apk上打上作者的烙印。
Metadata is “data about data”，又称元数据、中介数据、中继数据，为描述数据的数据，主要是描述数据属性的信息，用来支持如指示存储位置、历史数据、资源查找、文件纪录等功能。而这个Manifest中包含了的都是name:value这种的类似hashMap的键值对。这个manifest被命名为MANIFEST.MF。
用记事本打开MANIFEST.MF文件，这是个可读的文本文件， 看到跟manifest.mf中的很类似，也是BASE64编码的哈希值，这个是对每个文件的头3行进行SHA1 hash。这两个文件中的内容仅仅是个摘要，也就是仅仅对文件做个hash。
最后一个文件是CERT.RSA，这个文件中放的是apk包的签名，同时还有证书的公钥。
一般来说，证书的内容是：开发者信息+开发者公钥 +CA的签名（ CA对前两部分做hash值然后私钥加密之后的密文）
验证过程
验证过程是：用CA公钥对括号里的内容解密，然后对前两部分hash，跟CA的签名进行对比，看是否相同。
解密过程应该是：首先提取自己公钥，然后对自己加密的那部分解密，然后对前2部分做hash进行比对，模拟跟验证证书。然后再用公钥依次验证每一个文件的摘要看是否正确。
给一个APK文件的后缀名从.apk改为.zip或者.rar，然后利用解压工具进行解压，我们会在META-INF目录下看到四个文件：MANIFEST.MF、CERT.SF、INDEX.LIST、CERT.RSA
MANIFEST.MF（摘要文件）：程序遍历APK包中的所有文件，对非文件夹非签名文件的文件，逐个用SHA1生成摘要信息，再用Base64进行编码。如果APK包的文件被修改，在APK安装校验时，被修改的文件与MANIFEST.MF的校验信息不同，程序将无法正常安装。
CERT.SF（对摘要文件的签名文件）：对于生成的MANIFEST.MF文件利用SHA1-RSA算法对开发者的私钥进行签名。在安装时只有公共密钥才能对其解密。解密之后将其与未加密的摘要信息进行比对，如果相符则文件没有被修改。
INDEX.LIST APK索引文件目录
CERT.RSA 保存公钥、加密算法等信息。
在APK进行安装时，可以通过MANIFEST.MF文件开始的环环相扣来保证APK的安全性。但这些文件或者密钥如果被攻击者得到或者被攻击者通过某些技术手段攻破，则Android操作系统无法验证其安全性。


快速生成渠道包 META-INF目录
如果能直接修改apk的渠道号，而不需要再重新签名能节省不少打包的时间。幸运的是我们找到了这种方法。直接解压apk，解压后的根目录会有一个META-INF目录，如下图所示：
META-INF目录
如果在META-INF目录内添加空文件，可以不用重新签名应用。因此，通过为不同渠道的应用添加不同的空文件，可以唯一标识一个渠道。
采用这种方式，每打一个渠道包只需复制一个apk，在META-INF中添加一个使用渠道号命名的空文件即可。
这种打包方式速度非常快，900多个渠道不到一分钟就能打完。


apk 签名
上一步打包好的 APK 还不能直接安装，因为没有签名，我们这里用 debug.keystore 给 final.apk 签名。执行下面的命令。
/Users/zy/android-sdk-mac_x86/build-tools/28.0.2/apksigner sign --ks ~/.android/debug.keystore final.apk
这里需要输入 debug.keystore 的密码，是 android。
这样，最后的 final.apk 就是我们手动生成的 apk 了。可以安装尝试一下了～
V1 签名
V1 签名的机制主要就在 META-INF 目录下的三个文件，MANIFEST.MF，CERT.SF，CERT.RSA，他们都是 V1 签名的产物。
V2 签名
本来使用上述方案，一切都是很美好的，然而在 Android 7.0 的时候，引入新的签名方式，APK Signature Scheme v2，导致 V1 上的多渠道签名方案失效。我们先看看 V2 签名的原理。
按照官方文档来看，V2 签名是在 数据区和核心目录区之间，新增了一个 APK Signing Block 区块，用来记录签名信息。
首先检查是否包含 V2 签名块，如果包含 V2 签名块，就采用 V2 签名进行验证，如果没有包含 V2 签名块，就采用 V1 签名验证。
因此，采用 V2 签名进行验证时，V1 方案中添加 EOCD 注释和 META-INF 空文件的方式就都失效了。
看到这里，我们会发现，V2 签名验证了其他三个模块数据，但是没有对 APK Signing Block 本身进行验证，而其中的 ID-value 是一组数据，所以可以在这里添加包含渠道信息的 ID-value。这就是 V2 签名下生成多渠道包的原理。
V3 签名
在 Android 9.0 中引入了新的签名方式 V3。为了解决签名签名过期的问题。V3 签名在 V2 的 APK Signing Block 中新增了一个签名块，保存了 supported SDK 版本以及密钥流转结构。由于对 V2 结构没有进行大的更改，所以不会对多渠道打包方案造成影响。
