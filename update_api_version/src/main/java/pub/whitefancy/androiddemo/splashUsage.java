package pub.whitefancy.androiddemo;

public class splashUsage {
}
//闪屏覆盖在主Activity上遮盖初始化的时长
//2.1 显示 Logo
//首先说显示 Logo，如果 App 的 Logo 与开始的图片上的流程是一样的，那么 Logo 是可以有两种方式来显示的。
//通过 windowBackground 来设置：可以在屏幕背景里放一个非全屏显示的图片，作为打开的留白的间隙的替代，当正常内容加载完就会被覆盖掉。Logo 的显示时间取决于正常内容的加载时间，如果加载很快，Logo 就会一闪而过。但为什么是非全屏显示呢，对于 windowBackgroung，很难设置图片的拉伸模式，那么屏幕适配就非常麻烦。所以放一个非全屏图片，设置个背景色或许是个不错的选择。这种方式也是 Google Photos 在用的。至于一闪而过对于用户的体验是否良好就仁者见仁了。另外值得一提的是网易云应该也是使用这种方式，因为 Logo 的显示时间并不总是相同而且没有丝毫的留白间隙，但是它的 Logo 是全屏的，
//public class SplashActivity extends Activity {
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
//            finish();
//            return;
//        }
//        try {
//            VideoView videoHolder = new VideoView(this);
//            setContentView(videoHolder);
//            Uri video = Uri.parse("android.resource://" + this.getPackageName() + "/" + R.raw.cbg_sdk_video);
//            videoHolder.setVideoURI(video);
//            videoHolder.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                public void onCompletion(MediaPlayer mp) {
//                    jump();
//                }
//            });
//            videoHolder.start();
//        } catch (Exception ex) {
//            Log.e("SplashActivity", ex.toString());
//            jump();
//        }
//    }
//    private void jump() {
//        if (isFinishing())
//            return;
//        Intent intent = new Intent();
//        intent.setAction(this.getPackageName() + ".aftersplash");
//        startActivity(intent);
//        finish();
//    }
//}