package pub.whitefancy.androiddemo;

public class ANRSolution {
}
//Android ANR：原理分析及解决办法
//全称：Application Not Responding，也就是应用程序无响应。
//ANR出现场景
//以下四个条件都可以造成ANR发生：
//InputDispatching Timeout：5秒内无法响应屏幕触摸事件或键盘输入事件
//BroadcastQueue Timeout ：在执行前台广播（BroadcastReceiver）的onReceive()函数时10秒没有处理完成，后台为60秒。
//Service Timeout ：前台服务20秒内，后台服务在200秒内没有执行完毕。
//ContentProvider Timeout ：ContentProvider的publish在10s内没进行完。
//原因
//Android系统中，ActivityManagerService(简称AMS)和WindowManagerService(简称WMS)会检测App的响应时间，如果App在特定时间无法相应屏幕触摸或键盘输入时间，或者特定事件没有处理完毕，就会出现ANR。
//上面例子只是由于简单的主线程耗时操作造成的ANR，造成ANR的原因还有很多：
//主线程阻塞或主线程数据读取
//解决办法：避免死锁的出现，使用子线程来处理耗时操作或阻塞任务。尽量避免在主线程query provider、不要滥用SharePreferenceS
//CPU满负荷，I/O阻塞
//解决办法：文件读写或数据库操作放在子线程异步操作。
//内存不足
//解决办法：AndroidManifest.xml文件<applicatiion>中可以设置 android:largeHeap="true"，以此增大App使用内存。不过不建议使用此法，从根本上防止内存泄漏，优化内存使用才是正道。
//各大组件ANR
//各大组件生命周期中也应避免耗时操作，注意BroadcastReciever的onRecieve()、后台Service和ContentProvider也不要执行太长时间的任务。
//首先，Android系统对于一些事件需要在一定的时间范围内完成，如果超过预定时间能未能得到有效响应或者响应时间过长，都会造成ANR。ANR由消息处理机制保证，Android在系统层实现了一套精密的机制来发现ANR，核心原理是消息调度和超时处理。
//其次，ANR机制主体实现在系统层。所有与ANR相关的消息，都会经过系统进程(system_server)调度，然后派发到应用进程完成对消息的实际处理，同时，系统进程设计了不同的超时限制来跟踪消息的处理。 一旦应用程序处理消息不当，超时限制就起作用了，它收集一些系统状态，譬如CPU/IO使用情况、进程函数调用栈，并且报告用户有进程无响应了(ANR对话框)。
//然后，ANR问题本质是一个性能问题。ANR机制实际上对应用程序主线程的限制，要求主线程在限定的时间内处理完一些最常见的操作(启动服务、处理广播、处理输入)， 如果处理超时，则认为主线程已经失去了响应其他操作的能力。主线程中的耗时操作，譬如密集CPU运算、大量IO、复杂界面布局等，都会降低应用程序的响应能力。
//避免
//尽量避免在主线程（UI线程）中作耗时操作。
//那么耗时操作就放在子线程中。
//ANR分析办法
//Log
//可以看到logcat清晰地记录了ANR发生的时间，以及线程的tid和一句话概括原因：WaitingInMainSignalCatcherLoop，大概意思为主线程等待异常。
//最后一句The application may be doing too much work on its main thread.告知可能在主线程做了太多的工作。
//ANR分析办法二：traces.txt
//刚才的log有第二句Wrote stack traces to '/data/anr/traces.txt'，说明ANR异常已经输出到traces.txt文件，使用adb命令把这个文件从手机里导出来：
//cd到adb.exe所在的目录，也就是Android SDK的platform-tools目录，例如：
//cd D:\Android\AndroidSdk\platform-tools
//此外，除了Windows的cmd以外，还可以使用AndroidStudio的Terminal来输入adb命令。
//到指定目录后执行以下adb命令导出traces.txt文件：
//adb pull /data/anr/traces.txt
//traces.txt默认会被导出到Android SDK的\platform-tools目录。一般来说traces.txt文件记录的东西会比较多，分析的时候需要有针对性地去找相关记录
//在文件中使用 ctrl + F 查找包名可以快速定位相关代码。
//通过上方log可以看出相关问题：
//进程id和包名：pid 23346 com.sky.myjavatest
//造成ANR的原因：Sleeping
//造成ANR的具体行数：ANRTestActivity.java:20类的第20行