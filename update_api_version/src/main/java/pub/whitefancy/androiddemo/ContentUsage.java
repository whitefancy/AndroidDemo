package pub.whitefancy.androiddemo;

public class ContentUsage {
}
//Context
//定义
//在官方的Android文档中，它们是如何定义上下文的。
//与有关应用程序环境的全局信息的接口。这是一个抽象类，其实现由Android系统提供。它允许访问特定于应用程序的资源和类，以及对应用程序级操作（如启动活动，广播和接收意图等）的调用。
//检索上下文的函数
//让我们看一下最常用的3种检索上下文的函数：
//getContext（） — 返回链接到被调用的Activity的Context，
//getApplicationContext（） —返回链接到Application的Context，该Application包含在其中运行的所有活动，
//getBaseContext（） —与ContextWrapper有关，后者围绕现有Context创建，并让我们更改其行为。使用getBaseContext（），我们可以在ContextWrapper类中获取现有的Context。
//getContext（）
//在getContext（）中，上下文与Activity及其生命周期相关联。我们可以将上下文想象为位于Activity后面的一层，并且只要Activity存活，它将一直存在。活动死亡的那一刻，上下文也将消失。
//Activity的Context有其自己的功能，我们可以将其用于Android框架支持的各种功能。这是Activity的Context为我们提供的功能列表：
//加载资源值，
//布局膨胀，
//启动活动，
//显示对话框，
//启动服务，
//绑定到服务，
//发送广播，
//注册BroadcastReceiver。
//getApplicationContext（）
//在getApplicationContext（）中，我们的上下文与Application及其生命周期相关联。我们可以将其视为整个应用程序的底层。只要用户不终止应用程序，它就仍然有效。
//您可能现在想知道，getContext（）和getApplicationContext（）有什么区别。不同之处在于Application的Context与UI无关。这意味着我们不应该使用它来增加布局，启动活动或显示对话框。关于活动上下文中的其余功能，也可以从应用程序上下文中使用。因此，应用程序上下文的功能列表如下所示：
//加载资源值，
//启动服务，
//绑定到服务，
//发送广播，
//注册BroadcastReceiver。
//上下文是位于其组件（活动性，应用程序…）和组件生命周期后面的一个层（接口），它提供对应用程序环境和Android框架支持的各种功能的访问。
//Context提供的最常用的功能是加载资源（例如字符串，资产，主题等），启动Activity和Services以及增加布局。
//显式启动组件时，需要两条信息：
//软件包名称，标识包含该组件的应用程序。
//组件的标准Java类名称。
//如果启动内部组件，则可以传入上下文，因为可以通过提取当前应用程序的包名称
//使用上下文
//context.getPackageName()。
//明确启动组件
//Intent intent = new Intent(context, MyActivity.class);
//startActivity(intent);
//显式启动组件时，需要两条信息：
//软件包名称，标识包含该组件的应用程序。
//组件的标准Java类名称。
//如果启动内部组件，则可以传入上下文，因为可以通过提取当前应用程序的包名称context.getPackageName()。
//TextView textView = new TextView(context);
//上下文包含视图所需的以下信息：
//设备屏幕尺寸和尺寸，用于将dp，sp转换为像素
//样式化的属性
//onClick属性的活动参考
//扩展XML布局文件
//我们使用上下文获取LayoutInflater，以将XML布局膨胀到内存中：
//爪哇
//科特林
//// A context is required when creating views.
//LayoutInflater inflater = LayoutInflater.from(context);
//inflater.inflate(R.layout.my_layout, parent);
//发送本地广播
//LocalBroadcastManager当发送或注册广播接收者时，我们使用上下文来获取：
//爪哇
//科特林
//// The context contains a reference to the main Looper,
//// which manages the queue for the application's main thread.
//Intent broadcastIntent = new Intent("custom-action");
//LocalBroadcastManager.getInstance(context).sendBroadcast(broadcastIntent);
//检索系统服务
//要从应用程序发送通知，NotificationManager需要系统服务：
//爪哇
//科特林
//// Context objects are able to fetch or start system services.
//NotificationManager notificationManager =
//(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//int notificationId = 1;
//// Context is required to construct RemoteViews
//Notification.Builder builder =
//new Notification.Builder(context).setContentTitle("custom title");
//notificationManager.notify(notificationId, builder.build());
//请参阅可以通过上下文检索的所有可用系统服务的列表。
//应用程序与活动上下文
//尽管主题和样式通常在应用程序级别应用，但是它们也可以在活动级别指定。这样，活动可以具有与应用程序其余部分不同的主题或样式集（例如，如果需要为某些页面禁用ActionBar）。您会在AndroidManifest.xml文件中注意到android:theme该应用程序通常有一个。我们还可以为活动指定其他名称：
//<application
//android:allowBackup="true"
//android:icon="@mipmap/ic_launcher"
//android:label="@string/app_name"
//android:theme="@style/AppTheme" >
//<activity
//android:name=".MainActivity"
//android:label="@string/app_name"
//android:theme="@style/MyCustomTheme">
//因此，重要的是要知道存在一个应用程序上下文和一个活动上下文，它们在各自的生命周期中持续存在。大多数视图都应传递一个活动上下文，以获取对应应用哪些主题，样式和尺寸的访问。如果没有为活动明确指定主题，则默认为使用为应用程序指定的主题。
//在大多数情况下，您应该使用活动上下文。通常，thisJava中的关键字引用该类的实例，并且可以在Activity中需要上下文时使用。下面的示例显示如何使用此方法显示Toast消息：
//爪哇
//科特林
//public class MainActivity extends AppCompatActivity {
//@Override
//protected void onCreate(Bundle savedInstanceState) {
//super.onCreate(savedInstanceState);
//setContentView(R.layout.activity_main);
//Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
//}
//}
//匿名功能
//在实现侦听器时使用匿名函数时this，Java中的关键字适用于所声明的最直接的类。在这些情况下，MainActivity必须指定外部类来引用Activity实例。
//爪哇
//科特林
//public class MainActivity extends AppCompatActivity {
//@Override
//protected void onCreate(Bundle savedInstanceState) {
//TextView tvTest = (TextView) findViewById(R.id.abc);
//tvTest.setOnClickListener(new View.OnClickListener() {
//@Override
//public void onClick(View view) {
//Toast.makeText(MainActivity.this, "hello", Toast.LENGTH_SHORT).show();
//}
//});
//}
//}
//}
//转接器
//阵列适配器
//为ListView构造适配器时，通常getContext()在布局膨胀过程中通常调用它。此方法通常使用实例化ArrayAdapter的上下文：
//if (convertView == null) {
//convertView =
//LayoutInflater
//.from(getContext())
//.inflate(R.layout.item_user, parent, false);
//}
//但是，如果使用应用程序上下文实例化ArrayAdapter，您可能会注意到没有应用主题/样式。因此，请确保在这些情况下传递“活动”上下文。
//RecyclerView适配器
//爪哇
//科特林
//public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {
//@Override
//public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//View v =
//LayoutInflater
//.from(parent.getContext())
//.inflate(R.layout.itemLayout, parent, false);
//return new ViewHolder(v);
//}
//@Override
//public void onBindViewHolder(ViewHolder viewHolder, int position) {
//// If a context is needed, it can be retrieved
//// from the ViewHolder's root view.
//Context context = viewHolder.itemView.getContext();
//// Dynamically add a view using the context provided.
//if(position == 0) {
//TextView tvMessage = new TextView(context);
//tvMessage.setText("Only displayed for the first item.")
//viewHolder.customViewGroup.addView(tvMessage);
//}
//}
//public static class ViewHolder extends RecyclerView.ViewHolder {
//public FrameLayout customViewGroup;
//public ViewHolder(View imageView) {
//// Very important to call the parent constructor
//// as this ensures that the imageView field is populated.
//super(imageView);
//// Perform other view lookups.
//customViewGroup = (FrameLayout) imageView.findById(R.id.customViewGroup);
//}
//}
//}
//而a ArrayAdapter需要将上下文传递到其构造函数中，而a RecyclerView.Adapter则不需要。相反，当需要进行通货膨胀时，可以从父视图中推断出正确的上下文。
//关联RecyclerView始终将自身作为父视图传递给RecyclerView.Adapter.onCreateViewHolder()调用。
//如果上下文是的需要外界的onCreateViewHolder()方法，只要有一个ViewHolder实例可用，上下文可以通过检索：viewHolder.itemView.getContext()。 itemView是final基类ViewHolder中的public，non-null和field。
//避免内存泄漏
//当需要创建单例实例时，通常会使用应用程序上下文，例如需要上下文信息来访问系统服务但在多个活动之间重用的自定义管理器类。由于保留对Activity上下文的引用将导致不再运行的内存不再被回收，因此使用Application Context至关重要。
//在下面的示例中，如果要存储的上下文是Activity或Service，并且已被Android系统破坏，则将无法对其进行垃圾回收，因为CustomManager类持有对该上下文的静态引用。
//爪哇
//科特林
//public class CustomManager {
//private static CustomManager sInstance;
//public static CustomManager getInstance(Context context) {
//if (sInstance == null) {
//// This class will hold a reference to the context
//// until it's unloaded. The context could be an Activity or Service.
//sInstance = new CustomManager(context);
//}
//return sInstance;
//}
//private Context mContext;
//private CustomManager(Context context) {
//mContext = context;
//}
//}
//正确存储上下文：使用应用程序上下文
//为避免内存泄漏，切勿在生命周期之外保留对上下文的引用。检查您的任何后台线程，挂起的处理程序或可能还会保留上下文对象的内部类。
//正确的方法是将应用程序上下文存储在中CustomManager.getInstance()。应用程序上下文是一个单例，并与应用程序进程的生命周期相关联，从而可以安全地存储对其的引用。
//当在组件的生存期之外需要上下文引用时，或者如果它应独立于传入上下文的生命周期，则使用应用程序上下文。
//爪哇
//科特林
//public static CustomManager getInstance(Context context) {
//if (sInstance == null) {
//// When storing a reference to a context, use the application context.
//// Never store the context itself, which could be a component.
//sInstance = new CustomManager(context.getApplicationContext());
//}
//return sInstance;
//}