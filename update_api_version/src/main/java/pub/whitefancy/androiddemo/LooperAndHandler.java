package pub.whitefancy.androiddemo;

public class LooperAndHandler {
}
//Looper
//static Handler handler = new Handler(Looper.getMainLooper());
//Looper是android为线程间异步消息通信提供的一种机制，利用Looper机制可以方便我们实现多线程编程时线程间的相互沟通。当然，如果不用Looper而采用其它的线程间通信方式（像管道，信号量，共享内存，消息队列等）也是一样的。Looper的实现是利用消息队列的方式，为用户封装了一层API，用户不需要考虑互斥加锁的问题，方便用户的使用。
//Looper类使用
//Android的Looper类使用的5个要点
//Looper类用来为一个线程开启一个消息循环。
//默认情况下android中新诞生的线程是没有开启消息循环的。（主线程除外，主线程系统会自动为其创建Looper对象，开启消息循环。）
//Looper对象通过MessageQueue来存放消息和事件。一个线程只能有一个Looper，对应一个MessageQueue。
//通常是通过Handler对象来与Looper进行交互的。
//Handler可看做是Looper的一个接口，用来向指定的Looper发送消息及定义处理方法。
//默认情况下Handler会与其被定义时所在线程的Looper绑定，比如，Handler在主线程中定义，那么它是与主线程的Looper绑定。
//mainHandler = new Handler() 等价于new Handler（Looper.myLooper()）.
//Looper.myLooper()：获取当前进程的looper对象，类似的 Looper.getMainLooper() 用于获取主线程的Looper对象。
//在非主线程中直接new Handler() 会报如下的错误:
//E/AndroidRuntime( 6173): Uncaught handler: thread Thread-8 exiting due to uncaught exception
//E/AndroidRuntime( 6173): java.lang.RuntimeException: Can't create handler inside thread that has not called Looper.prepare()
//原因是非主线程中默认没有创建Looper对象，需要先调用Looper.prepare()启用Looper。
//Looper.loop();
//让Looper开始工作，从消息队列里取消息，处理消息。
//注意：写在Looper.loop()之后的代码不会被执行，这个函数内部应该是一个循环，当调用mHandler.getLooper().quit()后，loop才会中止，其后的代码才能得以运行。
//基于以上知识，可实现主线程给子线程（非主线程）发送消息。
//Android Looper和Handler
//Message：消息，其中包含了消息ID，消息处理对象以及处理的数据等，由MessageQueue统一列队，终由Handler处理。
//Handler：处理者，负责Message的发送及处理。使用Handler时，需要实现handleMessage(Message msg)方法来对特定的Message进行处理，例如更新UI等。
//MessageQueue：消息队列，用来存放Handler发送过来的消息，并按照FIFO规则执行。当然，存放Message并非实际意义的保存，而是将Message以链表的方式串联起来的，等待Looper的抽取。
//Looper：消息泵，不断地从MessageQueue中抽取Message执行。因此，一个MessageQueue需要一个Looper。
//Thread：线程，负责调度整个消息循环，即消息循环的执行场所。
//Android系统的消息队列和消息循环都是针对具体线程的，一个线程可以存在（当然也可以不存在）一个消息队列和一个消 息循环（Looper），特定线程的消息只能分发给本线程，不能进行跨线程，跨进程通讯。但是创建的工作线程默认是没有消息循环和消息队列的，如果想让该 线程具有消息队列和消息循环，需要在线程中首先调用Looper.prepare()来创建消息队列，然后调用Looper.loop()进入消息循环。 如下例所示：
//class LooperThread extends Thread {
//public Handler mHandler;
//public void run() {
//Looper.prepare();
//mHandler = new Handler() {
//public void handleMessage(Message msg) {
//// process incoming messages here
//}
//};
//Looper.loop();
//}
//}
//这样你的线程就具有了消息处理机制了，在Handler中进行消息处理。
//Activity是一个UI线程，运行于主线程中，Android系统在启动的时候会为Activity创建一个消息队列和消息循环（Looper）。详细实现请参考ActivityThread.java文件
//Android应用程序进程在启动的时候，会在进程中加载ActivityThread类，并且执行这个类的main函数，应用程序的消息循环过程就是在这个main函数里面实现的
//public final class ActivityThread {
//......
//public static final void main(String[] args) {
//......
//Looper.prepareMainLooper();
//......
//ActivityThread thread = new ActivityThread();
//thread.attach(false);
//......
//Looper.loop();
//......
//thread.detach();
//......
//}
//}
//这个函数做了两件事情，一是在主线程中创建了一个ActivityThread实例，二是通过Looper类使主线程进入消息循环中，这里我们只关注后者。

//子线程更新UI的四种方法
//1. handle.post(Runnable r)
//2. handle.handleMessage(Message msg)
//3. runOnUiThread(Runnable r)
//4. View.post(Runnable r)
//用于线程间通讯的类
//Handler :在android里负责发送和处理消息，通过它可以实现其他线程与Main线程之间的消息通讯。
//Looper:负责管理线程的消息队列和消息循环 。
//Message :线程间通讯的消息载体。两个码头之间运输货物，Message充当集装箱的功能，里面可以存放任何你想要传递的消息。
//MessageQueue:消息队列，先进先出，它的作用是保存有待线程处理的消息