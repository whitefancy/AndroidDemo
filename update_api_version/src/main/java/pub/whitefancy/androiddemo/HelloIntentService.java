package pub.whitefancy.androiddemo;

import android.app.IntentService;
import android.content.Intent;

//Service
//这是适用于所有服务的基类。扩展此类时，您必须创建用于执行所有服务工作的新线程，因为服务默认使用应用的主线程，这会降低应用正在运行的任何 Activity 的性能。
//IntentService
//这是 Service 的子类，其使用工作线程逐一处理所有启动请求。如果您不要求服务同时处理多个请求，此类为最佳选择。实现 onHandleIntent()，该方法会接收每个启动请求的 Intent，以便您执行后台工作。
//下文介绍如何使用其中任一类来实现服务。
//扩展 IntentService 类
//由于大多数启动服务无需同时处理多个请求（实际上，这种多线程情况可能很危险），因此最佳选择是利用 IntentService 类实现服务。
//IntentService 类会执行以下操作：
//创建默认的工作线程，用于在应用的主线程外执行传递给 onStartCommand() 的所有 Intent。
//创建工作队列，用于将 Intent 逐一传递给 onHandleIntent() 实现，这样您就永远不必担心多线程问题。
//在处理完所有启动请求后停止服务，因此您永远不必调用 stopSelf()。
//提供 onBind() 的默认实现（返回 null）。
//提供 onStartCommand() 的默认实现，可将 Intent 依次发送到工作队列和 onHandleIntent() 实现。
//如要完成客户端提供的工作，请实现 onHandleIntent()。不过，您还需为服务提供小型构造函数。
//以下是 IntentService 的实现示例：
public class HelloIntentService extends IntentService {
    /**
     * A constructor is required, and must call the super <code><a href="/reference/android/app/IntentService.html#IntentService(java.lang.String)">IntentService(String)</a></code>
     * constructor with a name for the worker thread.
     */
    public HelloIntentService() {
        super("HelloIntentService");
    }

    /**
     * The IntentService calls this method from the default worker thread with
     * the intent that started the service. When this method returns, IntentService
     * stops the service, as appropriate.
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        // Normally we would do some work here, like download a file.
        // For our sample, we just sleep for 5 seconds.
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // Restore interrupt status.
            Thread.currentThread().interrupt();
        }
    }
}
