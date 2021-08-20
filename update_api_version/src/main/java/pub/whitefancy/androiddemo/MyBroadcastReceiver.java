package pub.whitefancy.androiddemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

//系统软件包管理器会在应用安装时注册接收器。然后，该接收器会成为应用的一个独立入口点，这意味着如果应用当前未运行，系统可以启动应用并发送广播。
//系统会创建新的 BroadcastReceiver 组件对象来处理它接收到的每个广播。此对象仅在调用 onReceive(Context, Intent) 期间有效。一旦从此方法返回代码，系统便会认为该组件不再活跃。
public class MyBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "MyBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        //此外，以 Android 7.0 及更高版本为目标平台的应用必须使用 registerReceiver(BroadcastReceiver, IntentFilter) 注册 CONNECTIVITY_ACTION 广播。无法在清单中声明接收器。
        //Intent 过滤器指定您的接收器所订阅的广播操作。
        //创建 BroadcastReceiver 子类并实现 onReceive(Context, Intent)。以下示例中的广播接收器会记录并显示广播的内容：
        StringBuilder sb = new StringBuilder();
        sb.append("Action: " + intent.getAction() + "\n");
        sb.append("URI: " + intent.toUri(Intent.URI_INTENT_SCHEME).toString() + "\n");
        String log = sb.toString();
        Log.d(TAG, log);
        Toast.makeText(context, log, Toast.LENGTH_LONG).show();

        final PendingResult pendingResult = goAsync();
        Task asyncTask = new Task(pendingResult, intent);
        asyncTask.execute();
    }

    private static class Task extends AsyncTask<String, Integer, String> {
        private final PendingResult pendingResult;
        private final Intent intent;

        private Task(PendingResult pendingResult, Intent intent) {
            this.pendingResult = pendingResult;
            this.intent = intent;
        }

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder sb = new StringBuilder();
            sb.append("Action: " + intent.getAction() + "\n");
            sb.append("URI: " + intent.toUri(Intent.URI_INTENT_SCHEME).toString() + "\n");
            String log = sb.toString();
            Log.d(TAG, log);
            return log;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            // Must call finish() so the BroadcastReceiver can be recycled.
            pendingResult.finish();
        }
    }
}
//上下文注册的接收器
//要使用上下文注册接收器，请执行以下步骤：
//创建 BroadcastReceiver 的实例。
//KOTLIN
//JAVA
//    BroadcastReceiver br = new MyBroadcastReceiver();
//创建 IntentFilter 并调用 registerReceiver(BroadcastReceiver, IntentFilter) 来注册接收器：
//KOTLIN
//JAVA
//    IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
//        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
//        this.registerReceiver(br, filter);
//注意：要注册本地广播，请调用 LocalBroadcastManager.registerReceiver(BroadcastReceiver, IntentFilter)。
//只要注册上下文有效，上下文注册的接收器就会接收广播。例如，如果您在 Activity 上下文中注册，只要 Activity 没有被销毁，您就会收到广播。如果您在应用上下文中注册，只要应用在运行，您就会收到广播。
//要停止接收广播，请调用 unregisterReceiver(android.content.BroadcastReceiver)。当您不再需要接收器或上下文不再有效时，请务必注销接收器。
//请注意注册和注销接收器的位置，比方说，如果您使用 Activity 上下文在 onCreate(Bundle) 中注册接收器，则应在 onDestroy() 中注销，以防接收器从 Activity 上下文中泄露出去。如果您在 onResume() 中注册接收器，则应在 onPause() 中注销，以防多次注册接收器（如果您不想在暂停时接收广播，这样可以减少不必要的系统开销）。请勿在 onSaveInstanceState(Bundle) 中注销，因为如果用户在历史记录堆栈中后退，则不会调用此方法。
//对进程状态的影响
//BroadcastReceiver 的状态（无论它是否在运行）会影响其所在进程的状态，而其所在进程的状态又会影响它被系统终结的可能性。例如，当进程执行接收器（即当前在运行其 onReceive() 方法中的代码）时，它被认为是前台进程。除非遇到极大的内存压力，否则系统会保持该进程运行。
//但是，一旦从 onReceive() 返回代码，BroadcastReceiver 就不再活跃。接收器的宿主进程变得与在其中运行的其他应用组件一样重要。如果该进程仅托管清单声明的接收器（这对于用户从未与之互动或最近没有与之互动的应用很常见），则从 onReceive() 返回时，系统会将其进程视为低优先级进程，并可能会将其终止，以便将资源提供给其他更重要的进程使用。
//因此，您不应从广播接收器启动长时间运行的后台线程。onReceive() 完成后，系统可以随时终止进程来回收内存，在此过程中，也会终止进程中运行的派生线程。要避免这种情况，您应该调用 goAsync()（如果您希望在后台线程中多花一点时间来处理广播）或者使用 JobScheduler 从接收器调度 JobService，这样系统就会知道该进程将继续活跃地工作。如需了解详情，请参阅进程和应用生命周期。
//以下代码段展示了一个 BroadcastReceiver，它使用 goAsync() 来标记它在 onReceive() 完成后需要更多时间才能完成。如果您希望在 onReceive() 中完成的工作很长，足以导致界面线程丢帧 (>16ms)，则这种做法非常有用，这使它尤其适用于后台线程。
//发送广播
//Android 为应用提供三种方式来发送广播：
//sendOrderedBroadcast(Intent, String) 方法一次向一个接收器发送广播。当接收器逐个顺序执行时，接收器可以向下传递结果，也可以完全中止广播，使其不再传递给其他接收器。接收器的运行顺序可以通过匹配的 intent-filter 的 android:priority 属性来控制；具有相同优先级的接收器将按随机顺序运行。
//sendBroadcast(Intent) 方法会按随机的顺序向所有接收器发送广播。这称为常规广播。这种方法效率更高，但也意味着接收器无法从其他接收器读取结果，无法传递从广播中收到的数据，也无法中止广播。
//LocalBroadcastManager.sendBroadcast 方法会将广播发送给与发送器位于同一应用中的接收器。如果您不需要跨应用发送广播，请使用本地广播。这种实现方法的效率更高（无需进行进程间通信），而且您无需担心其他应用在收发您的广播时带来的任何安全问题。
//以下代码段展示了如何通过创建 Intent 并调用 sendBroadcast(Intent) 来发送广播。
//KOTLIN
//JAVA
//    Intent intent = new Intent();
//    intent.setAction("com.example.broadcast.MY_NOTIFICATION");
//    intent.putExtra("data","Notice me senpai!");
//    sendBroadcast(intent);
//广播消息封装在 Intent 对象中。Intent 的操作字符串必须提供应用的 Java 软件包名称语法，并唯一标识广播事件。您可以使用 putExtra(String, Bundle) 向 intent 附加其他信息。您也可以对 intent 调用 setPackage(String)，将广播限定到同一组织中的一组应用。
//注意：虽然 intent 既用于发送广播，也用于通过 startActivity(Intent) 启动 Activity，但这两种操作是完全无关的。广播接收器无法查看或捕获用于启动 Activity 的 intent；同样，当您广播 intent 时，也无法找到或启动 Activity。
//通过权限限制广播
//您可以通过权限将广播限定到拥有特定权限的一组应用。您可以对广播的发送器或接收器施加限制。
//带权限的发送
//当您调用 sendBroadcast(Intent, String) 或 sendOrderedBroadcast(Intent, String, BroadcastReceiver, Handler, int, String, Bundle) 时，可以指定权限参数。接收器若要接收此广播，则必须通过其清单中的 标记请求该权限（如果存在危险，则会被授予该权限）。例如，以下代码会发送广播：
//KOTLIN
//JAVA
//    sendBroadcast(new Intent("com.example.NOTIFY"),
//                  Manifest.permission.SEND_SMS);
//要接收此广播，接收方应用必须请求如下权限：
//<uses-permission android:name="android.permission.SEND_SMS"/>
//您可以指定现有的系统权限（如 SEND_SMS），也可以使用 <permission> 元素定义自定义权限。有关权限和安全性的一般信息，请参阅系统权限。
//注意：自定义权限将在安装应用时注册。定义自定义权限的应用必须在使用自定义权限的应用之前安装。
//带权限的接收
//如果您在注册广播接收器时指定了权限参数（通过 registerReceiver(BroadcastReceiver, IntentFilter, String, Handler) 或清单中的 <receiver> 标记指定），则广播方必须通过其清单中的 <uses-permission> 标记请求该权限（如果存在危险，则会被授予该权限），才能向该接收器发送 Intent。
//例如，假设您的接收方应用具有如下所示的清单声明的接收器：
//<receiver android:name=".MyBroadcastReceiver"
//              android:permission="android.permission.SEND_SMS">
//        <intent-filter>
//            <action android:name="android.intent.action.AIRPLANE_MODE"/>
//        </intent-filter>
//    </receiver>
//或者您的接收方应用具有如下所示的上下文注册的接收器：
//KOTLIN
//JAVA
//    IntentFilter filter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
//    registerReceiver(receiver, filter, Manifest.permission.SEND_SMS, null );
//那么，发送方应用必须请求如下权限，才能向这些接收器发送广播：
//<uses-permission android:name="android.permission.SEND_SMS"/>
//以下是有关收发广播的一些安全注意事项和最佳做法：
//如果您不需要向应用以外的组件发送广播，则可以使用支持库中提供的 LocalBroadcastManager 来收发本地广播。LocalBroadcastManager 效率更高（无需进行进程间通信），并且您无需考虑其他应用在收发您的广播时带来的任何安全问题。本地广播可在您的应用中作为通用的发布/订阅事件总线，而不会产生任何系统级广播开销。
//如果有许多应用在其清单中注册接收相同的广播，可能会导致系统启动大量应用，从而对设备性能和用户体验造成严重影响。为避免发生这种情况，请优先使用上下文注册而不是清单声明。有时，Android 系统本身会强制使用上下文注册的接收器。例如，CONNECTIVITY_ACTION 广播只会传送给上下文注册的接收器。
//请勿使用隐式 intent 广播敏感信息。任何注册接收广播的应用都可以读取这些信息。您可以通过以
//下三种方式控制哪些应用可以接收您的广播：
//您可以在发送广播时指定权限。
//在 Android 4.0 及更高版本中，您可以在发送广播时使用 setPackage(String) 指定软件包。系统会将广播限定到与该软件包匹配的一组应用。
//您可以使用 LocalBroadcastManager 发送本地广播。
//当您注册接收器时，任何应用都可以向您应用的接收器发送潜在的恶意广播。您可以通过以下三种方式限制您的应用可以接收的广播：
//您可以在注册广播接收器时指定权限。
//对于清单声明的接收器，您可以在清单中将 android:exported 属性设置为“false”。这样一来，接收器就不会接收来自应用外部的广播。
//您可以使用 LocalBroadcastManager 限制您的应用只接收本地广播。
//广播操作的命名空间是全局性的。请确保在您自己的命名空间中编写操作名称和其他字符串，否则可能会无意中与其他应用发生冲突。
//由于接收器的 onReceive(Context, Intent) 方法在主线程上运行，因此它会快速执行并返回。如果您需要执行长时间运行的工作，请谨慎生成线程或启动后台服务，因为系统可能会在 onReceive() 返回后终止整个进程。如需了解详情，请参阅对进程状态的影响。要执行长时间运行的工作，我们建议：
//在接收器的 onReceive() 方法中调用 goAsync()，并将 BroadcastReceiver.PendingResult 传递给后台线程。这样，在从 onReceive() 返回后，广播仍可保持活跃状态。不过，即使采用这种方法，系统仍希望您非常快速地完成广播（在 10 秒以内）。为避免影响主线程，它允许您将工作移到另一个线程。
//使用 JobScheduler 调度作业。如需了解详情，请参阅智能作业调度。
//请勿从广播接收器启动 Activity，否则会影响用户体验，尤其是有多个接收器时。相反，可以考虑显示通知.