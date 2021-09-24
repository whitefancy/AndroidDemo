package pub.whitefancy.androiddemo;

public class IntentUsage {
}
//Intent
//目的是要执行的操作的抽象描述。它可用于startActivity启动Activity， broadcastIntent将其发送到任何感兴趣的BroadcastReceiver组件，Context.startService(Intent)或 Context.bindService(Intent, ServiceConnection, int)与背景进行通信Service。
//Intent Structure
//The primary pieces of information in an intent are:
//action -- The general action to be performed, such as ACTION_VIEW, ACTION_EDIT, ACTION_MAIN, etc.
//data -- The data to operate on, such as a person record in the contacts database, expressed as a Uri.
//除了这些主要属性之外，您还可以通过意图包含许多辅助属性：
//类别-提供有关要执行的操作的其他信息。例如，CATEGORY_LAUNCHER意味着它应该在启动器中显示为顶级应用程序，而 CATEGORY_ALTERNATIVE意味着它应该包含在用户可以对数据执行的替代操作列表中。
//type-指定意图数据的显式类型（MIME类型）。通常，类型是从数据本身推断出来的。通过设置此属性，可以禁用该评估并强制使用显式类型。
//component-指定用于意图的组件类的显式名称。通常，这是通过查看意图中的其他信息（动作，数据/类型和类别）并将其与可以处理该意图的组件进行匹配来确定的。如果设置了此属性，则不执行任何评估，并且按原样使用此组件。通过指定此属性，所有其他Intent属性将变为可选。
//附加功能-这是Bundle任何其他信息的一部分。这可用于向组件提供扩展信息。例如，如果我们有发送电子邮件的动作，那么我们也可以在此处包括额外的数据，以提供主题，正文等。
//以下是一些其他操作的示例，您可以使用这些附加参数将它们指定为意图：
//ACTION_MAIN与类别CATEGORY_HOME-启动主屏幕。
//ACTION_GET_CONTENTMIME类型 vnd.android.cursor.item/phone -显示人员的电话号码列表，允许用户浏览并选择其中一个并将其返回到父活动。
//ACTION_GET_CONTENTMIME类型 * / *和category-CATEGORY_OPENABLE 显示可以使用打开的数据的所有选择器 ContentResolver.openInputStream()，允许用户选择其中一个，然后选择其中的一些数据，并将生成的URI返回给调用方。例如，可以在电子邮件应用程序中使用它来允许用户选择一些要包含为附件的数据。
//在Intent类中定义了各种标准的Intent操作和类别常量，但是应用程序也可以定义自己的常量。这些字符串使用Java风格的作用域，以确保它们是唯一的-例如，该标准ACTION_VIEW称为“ android.intent.action.VIEW”。
//放在一起，这组动作，数据类型，类别和其他数据定义了一种系统语言，允许表达诸如“呼叫约翰·史密斯细胞”之类的短语。在将应用程序添加到系统后，他们可以通过添加新的动作，类型和类别来扩展此语言，或者可以通过提供自己的活动来处理它们来修改现有短语的行为。
//意图解析
//您将使用两种主要形式的意图。
//显式意图已经指定了一个组件（通过setComponent(ComponentName)或setClass(Context, Class)），该组件 提供了要运行的确切类。通常，这些信息将不包含任何其他信息，仅是应用程序在用户与应用程序交互时启动其具有的各种内部活动的一种方式。
//隐式意图没有指定组件。相反，它们必须包括足够的信息以使系统确定为此目的最佳运行的可用组件。
//当使用隐式意图时，给定这样的任意意图，我们需要知道如何处理它。这是通过的过程中处理的意图的分辨率，其中意图映射到Activity， BroadcastReceiver或者Service（或有时两个或两个以上的活动/接收器），其可以处理它。
//意图解析机制主要围绕将意图与已安装的应用程序包中的所有<intent-filter>描述进行匹配。（此外，在广播的情况下，任何向BroadcastReceiver 显式注册的对象Context#registerReceiver。）有关此内容的更多详细信息，请参见IntentFilter该类的文档。
//Intent提供了一种在不同应用程序中的代码之间执行后期运行时绑定的功能。它最重要的用途是在启动活动时，可以将其视为活动之间的粘合剂。它基本上是一个被动数据结构，其中包含要执行的动作的抽象描述。
//Android 系统设计的独特之处在于，任何应用都可启动其他应用的组件。例如，当您想让用户使用设备相机拍摄照片时，另一个应用可能也可执行该操作，因而您的应用便可使用该应用，而非自行产生一个 Activity 来拍摄照片。您无需加入甚至链接到该相机应用的代码。只需启动拍摄照片的相机应用中的 Activity 即可。完成拍摄时，系统甚至会将照片返回您的应用，以便您使用。对用户而言，这就如同相机是您应用的一部分。
//当系统启动某个组件时，它会启动该应用的进程（如果尚未运行），并实例化该组件所需的类。例如，如果您的应用启动相机应用中拍摄照片的 Activity，则该 Activity 会在属于相机应用的进程（而非您的应用进程）中运行。因此，与大多数其他系统上的应用不同，Android 应用并没有单个入口点（即没有 main()函数）。
//由于系统在单独的进程中运行每个应用，且其文件权限会限制对其他应用的访问，因此您的应用无法直接启动其他应用中的组件，但 Android 系统可以。如要启动其他应用中的组件，请向系统传递一条消息，说明启动特定组件的 Intent。系统随后便会为您启动该组件。
//Intent是在相互独立的组件（如两个 Activity）之间提供运行时绑定功能的对象。
///** Called when the user taps the Send button */
//public void sendMessage(View view) {
//Intent intent = new Intent(this, DisplayMessageActivity.class);
//EditText editText = (EditText) findViewById(R.id.editText);
//String message = editText.getText().toString();
//intent.putExtra(EXTRA_MESSAGE, message);
//startActivity(intent);
//}
//表示应用执行某项操作的意图。您可以使用 Intent 执行多种任务，但在本课中，您的 Intent 将用于启动另一个 Activity。
//@Override
//protected void onCreate(Bundle savedInstanceState) {
//super.onCreate(savedInstanceState);
//setContentView(R.layout.activity_display_message);
//// Get the Intent that started this activity and extract the string
//Intent intent = getIntent();
//String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
//// Capture the layout's TextView and set the string as its text
//TextView textView = findViewById(R.id.textView);
//textView.setText(message);
//}
//每个 Android 应用都处于各自的安全沙盒中，并受以下 Android 安全功能的保护：
//● Android 操作系统是一种多用户 Linux 系统，其中的每个应用都是一个不同的用户；
//● 默认情况下，系统会为每个应用分配一个唯一的 Linux 用户 ID（该 ID 仅由系统使用，应用并不知晓）。系统会为应用中的所有文件设置权限，使得只有分配给该应用的用户 ID 才能访问这些文件；
//● 每个进程都拥有自己的虚拟机 (VM)，因此应用代码独立于其他应用而运行。
//● 默认情况下，每个应用都在其自己的 Linux 进程内运行。Android 系统会在需要执行任何应用组件时启动该进程，然后当不再需要该进程或系统必须为其他应用恢复内存时，其便会关闭该进程。
//启动组件
//在四种组件类型中，有三种（Activity、服务和广播接收器）均通过异步消息 Intent 进行启动。Intent 会在运行时对各个组件进行互相绑定。您可以将 Intent 视为从其他组件（无论该组件是属于您的应用还是其他应用）请求操作的信使。
//您需使用 Intent对象创建 Intent，该对象通过定义消息来启动特定组件（显式 Intent）或特定的组件类型（隐式 Intent）。
//对于 Activity 和服务，Intent 会定义要执行的操作（例如，查看或发送某内容），并且可指定待操作数据的 URI，以及正在启动的组件可能需要了解的信息。
//对于广播接收器，Intent 只会定义待广播的通知。例如，指示设备电池电量不足的广播只包含指示“电池电量不足”的已知操作字符串。
//与 Activity、服务和广播接收器不同，内容提供程序并非由 Intent 启动。相反，它们会在成为 ContentResolver的请求目标时启动。内容解析程序会通过内容提供程序处理所有直接事务，因此通过提供程序执行事务的组件便无需执行事务，而是改为在 ContentResolver对象上调用方法。这会在内容提供程序与请求信息的组件之间留出一个抽象层（以确保安全）。
//Activity 的Flags
//指定Activity的启动模式有两种，一种是在AndroidMenifest.xml中指定
//<activity
//android:name=".Patterm.SingleInstanceActivity"
//android:launchMode="singleInstance">
//另外一种是通过Intent来指定
//Intent intent = new Intent();
//intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
//intent.setClass(context, CycleActivity.class);
//context.startActivity(intent);
//注：intent指定的优先级大于xml中指定的优先级；如果两个方式都指定了启动方式，那么系统会以intent指定的启动方式为准。
//FLAG_ACTIVITY_NEW_TASK
//等同于在xml中配置了singleTask启动码模式
//FLAG_ACTIVITY_SINGLE_TOP
//等同于在xml中配置了singleTop启动码模式
//FLAG_ACTIVITY_CLEAR_TOP
//singTask自带该标记。这个标记会清除同一个任务栈中目标Activity之上的Activity。
//如果目标Activity采用了standard启动模式，但是任务栈中已经存在了Activity的实例。那么系统会清除任务中该实例以及它上面的Activity，并且会重新创建一个目标Activity实例放入栈顶。
//IntentFilter的匹配规则
//启动Activity有两种，一种为显示调用
//Intent intent = new Intent(MainActivity.this, TestActivity.class);
//startActivity(intent);
//另一种为隐式调用要配置清单文件
//<!--隐式调用-->
//<activity
//android:name=".Patterm.PattermActivity"
//android:theme="@style/AppTheme.NoActionBar">
//<intent-filter>
//<action android:name="com.hdd.androidreview.asdf" />
//<category android:name="com.hdd.123456" />
//<!--比就加上，否则会报错-->
//<category android:name="android.intent.category.DEFAULT" />
//</intent-filter>
//</activity>
//Activity跳转代码
//Intent intent = new Intent();
//intent.setAction("com.hdd.androidreview.asdf");
////category非必须指定。如果要指定，一点要和清单文件中填写的一至
//// intent.addCategory("com.hdd.123456");
//context.startActivity(intent);
//1. action
//可以再清单文件中配置多个
//intent指定的action值只要和清单文件中的其中一个字符串值一样，即可匹配成功。
//如果清单文件中配置了action，那么在intent跳转中必须至少指定且配对成功一个。
//<activity
//android:name=".Patterm.PattermActivity"
//android:theme="@style/AppTheme.NoActionBar">
//<intent-filter>
//<action android:name="com.hdd.androidreview.asdf" />
//<action android:name="com.hdd.androidreview.qwer" />
//<action android:name="12345678" />
//<!--必须加上，否则会报错-->
//<category android:name="android.intent.category.DEFAULT" />
//</intent-filter>
//</activity>
////java代码
//Intent intent = new Intent();
//intent.setAction("12345678");
//context.startActivity(intent);
//2. category
//category可以在清单文件中添加多个
//intent的addCategory()字符串有一个相同就可以匹配成功。
//他和action区别是，action是要在intent必须要指定的，切至少和一个匹配成功。category可以不用指定。如果指定也是至少和一个匹配成功。
//Intent intent = new Intent();
////必须指定一个并匹配成功
//intent.setAction("com.hdd.androidreview.asdf");
////category非必须指定。如果要指定，一点要和清单文件中填写的一至
//// intent.addCategory("com.hdd.123456");
//context.startActivity(intent);
//3. data
//data的匹配规则和action类似，如果过滤规则中定义了data，那么intent中必须要匹配data。
//data有mimeType和URL两部分组成
//mimeType为媒体类型: image/jpeg、audio/mpeg4-generic以及video/*等。
//URL数据结构为：
//<scheme>://<host>:<prot>/[<path>|<pathPrefix>|<pathPattern>]
//例如：
//content://com.example.project:200/folder/subfolder/etc
//http://www.baidu.com:80/search/info
//Scheme:URL的模式，比如http、file、content等；如果URL没有指定scheme，这个URL是无效的。
//Host:URL主机名,比如www.baidu.com，如果未指定，URL无效。
//Port:RUL端口号，比如80，只有scheme和host指定了port才有意义。
//Path：表示完整的路径信息。
//PathPattern:表示完整路径信息，里面可以包含通配符。
//PathPrefix:表示路径的前缀信息
//data在定义的时候有两种情况需要注意
//第一种情况
//非完整写法，及只指定了mimeType或者只指定了URL。
//注：如果只指定了URL，系统会默认设置mimeType的值为content和file
//<!-- 隐式调用,只配置URL -->
//<activity android:name=".RegulationActivity">
//<intent-filter>
//<action android:name="12345678" />
//<!-- 必须加上，否则会报错 -->
//<category android:name="android.intent.category.DEFAULT" />
//<data
//android:host="www.baidu.com"
//android:scheme="http" />
//</intent-filter>
//</activity>
//<!-- 隐式调用，只配置mimeType· -->
//<activity android:name=".RegulationActivity">
//<intent-filter>
//<action android:name="12345678" />
//<!-- 必须加上，否则会报错 -->
//<category android:name="android.intent.category.DEFAULT" />
//<data android:mimeType="audio/mpeg" />
//</intent-filter>
//</activity>
//java代码:
////intent指定只配置了URL的data
//intent.setAction("12345678");
//intent.setData(Uri.parse("http://www.baidu.com"));
//context.startActivity(intent);
////intent指定只配置了mimeType的data
//intent.setAction("12345678");
//intent.setType("audio/mpeg");
//context.startActivity(intent);
//第二种情况
//完整写法
//注:如果intent指定的为完整的data，必须要使用setDataAndType(),因为setData()和setType()会彼此清楚对方的值。
//<!-- 隐式调用 -->
//<activity android:name=".RegulationActivity">
//<intent-filter>
//<action android:name="12345678" />
//<!-- 必须加上，否则会报错 -->
//<category android:name="android.intent.category.DEFAULT" />
//<data
//android:mimeType="audio/mpeg"
//android:host="www.baidu.com"
//android:scheme="http" />
//</intent-filter>
//</activity>
//java代码:
////intent完整的data
//intent.setAction("12345678");
//intent.setDataAndType(Uri.parse("http://www.baidu.com"), "audio/mpeg");
//context.startActivity(intent);
//还有一种特殊写法：
////写法1
//<intent-filter>
//<data
//android:mimeType="audio/mpeg"
//android:host="www.baidu.com"
//android:scheme="http" />
//</intent-filter>
////写法2
//<intent-filter>
//<data android:mimeType="audio/mpeg" />
//<data android:scheme="http" />
//<data android:host="www.baidu.com" />
//</intent-filter>
//上面的两个写法上在使用效果上是一样的。
//如何判断隐式启动是否成功
//第一种，使用intent的resolveActivity
//ComponentName componentName = intent.resolveActivity(context.getPackageManager());
//if (componentName != null)
//context.startActivity(intent);
//else
//Toast.makeText(context, "匹配不成功", Toast.LENGTH_SHORT).show();
//第二种，使用PackageManager的resolveActivity
//PackageManager packageManager=context.getPackageManager();
//ResolveInfo resolveInfo = packageManager.resolveActivity(intent,PackageManager.MATCH_DEFAULT_ONLY);
////判断是否匹配成功
//if (resolveInfo != null)
//context.startActivity(intent);
//else
//Toast.makeText(context, "匹配不成功", Toast.LENGTH_SHORT).show();
//上面的代码中返回ResolveInfo不是最佳的Activity信息，而是所有匹配成功的Activity信息。resolveActivity()填写的第二个参数必须是MATCH_DEFAULT_ONLY。具体原因可以查看《android开发艺术探索》第一章34页，这里就不解释了。