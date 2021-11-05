package pub.whitefancy.androiddemo;

public class ActivityUsage {
    //        @Subscribe(event = SDKEventKey.ON_EXIT_SUCC)
    //        private void onExitSucc() {
    //            mActivity.();
    //            android.os.Process.killProcess(android.os.Process.myPid());
    //        }
    //Android中killProcess()、System.exit(0)及finish()的区别
    //一、finish()
    //
    //    我们 首先说一下finish()这个方法，我们在程序中经常见到该方法的调用，例如，从A_Activity跳转到B_Activity后，如果我们不需要再返回A_Activity，就可以再启动之后将其finish掉，还有就是在退出一个程序的时候，我们会经常会调用finish来解决。
    //
    //    但是，finish()这个方法，它是针对于Activity的，当我们对一个Activity执行该方法后，就是将当前栈顶的Activity移除，结合Activity的生命周期我们可以知道，调用finish()之后，Activity会相应的调用onPause()、onStop()方法，他只是将Activity推向了后台，并没有立即释放资源，剩下的就与Activity的生命周期有关了（至于onDestroy()方法是否执行，理论上是会执行的，而且我再测试的时候，他也是执行了，在这里要与直接按HOME键或BACK键区分开来）。
    //这里顺便提一下，以上案例代码中，当直接按下BACK键后，执行的生命周期方法与上述截图一样，但是在按下HOME键时，会执行onPause()和onStop()方法，并没有执行onDestroy()，当长按HOME键清楚掉进程之后，会执行onDestroy()，这里详细的解释可以参考Activity的生命周期。
    //
    //    二、System.exit(0)和killProcess()
    //
    //    这两个方法都会直接杀死当前进程，与finish()的区别在于，这两个方法执行时，是直接将整个进程杀死，并且释放内存资源，经过测试之后发现，当执行这两个方式时，他们会绕过Activity的生命周期方式，直接将进程kill掉。
    //
    //    当然，说起kill掉整个进程，我们这里就不得不提一下Service，Service是属于后台服务进程，与Activity不同的是，当程序退出时，Service会继续工作，但是，在执行这两个方法后，连同Service一起杀死，这样说起来大家是不是就能感觉出两个的区别呢？
    //
    //    可是，还有意外，就是这两个方法在杀死进程之后，很可能app会进行重新启动（很可能是android os会认为他是意外终止的程序，比如内存不足等，os底层有监听服务，当app意外终止会自动重启）；如果在Service中，也会与Service的onStartCommand()方法的返回值有关，我们都知道它可以返回粘性或者非粘性等，为了防止后台Service被意外终止，可以重新启动，当执行这两个方法后，android os很可能会认为这个进程是被意外终止的，所以根据Service的粘性来进行重启。
    // 其实 Process.killProcess 或 System.exit(0) 都不应该直接调用， 进程是由 os 底层进行管理的，android 系统会自己进行处理回收进程。退出应用你就直接 finish 掉 activity 就行了。

}
