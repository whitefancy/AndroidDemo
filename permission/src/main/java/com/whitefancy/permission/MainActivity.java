package com.whitefancy.permission;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.ads.identifier.AdvertisingIdClient;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;


public class MainActivity extends AppCompatActivity {

    private static final int INSTALL_APK_REQUESTCODE = 11;
    private static final String PROJECT_ID = "";
    // Register the permissions callback, which handles the user's response to the
// system permissions dialog. Save the return value, an instance of
// ActivityResultLauncher, as an instance variable.
//    private ActivityResultLauncher<String> requestPermissionLauncher =
//            registerForActivityResult(new RequestPermission(), isGranted -> {
//                if (isGranted) {
//                    // Permission is granted. Continue the action or workflow in your
//                    // app.
//                } else {
//                    // Explain to the user that the feature is unavailable because the
//                    // features requires a permission that the user has denied. At the
//                    // same time, respect the user's decision. Don't link to system
//                    // settings in an effort to convince the user to change their
//                    // decision.
//                }
//            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void storageGet(View view) {
//        确定应用是否已获得权限
//        如需检查用户是否已向您的应用授予特定权限，请将该权限传入 ContextCompat.checkSelfPermission() 方法。根据您的应用是否具有相应权限，此方法会返回 PERMISSION_GRANTED 或 PERMISSION_DENIED。
//说明您的应用为何需要获取权限
//如果 ContextCompat.checkSelfPermission() 方法返回 PERMISSION_DENIED，请调用 shouldShowRequestPermissionRationale()。如果此方法返回 true，请向用户显示指导界面。请在此界面中说明用户希望启用的功能为何需要特定权限。
//请求权限
//用户查看指导界面后或者 shouldShowRequestPermissionRationale() 的返回值表明您这次不需要显示指导界面后，您可以请求权限。用户会看到系统权限对话框，并可在其中选择是否向您的应用授予特定权限。
//按照历来的做法，您可以在权限请求过程中自行管理请求代码，并将此请求代码包含在您的权限回调逻辑中。另一种选择是使用 AndroidX 库中包含的 RequestPermission 协定类，您可在其中允许系统代为管理权限请求代码。由于使用 RequestPermission 协定类可简化逻辑，因此，建议您尽可能使用该方法。
//允许系统管理权限请求代码
//如需允许系统管理与权限请求相关联的请求代码，请在您模块的 build.gradle 文件中添加 androidx.activity 库的依赖项。请使用该库的 1.2.0 版或更高版本。
//注意：该库的 1.2.0 版目前处于 Alpha 版开发阶段。
//然后，您可以使用以下某个类：
//如需请求一项权限，请使用 RequestPermission。
//如需同时请求多项权限，请使用 RequestMultiplePermissions。
//以下步骤显示了如何使用 RequestPermission 协定类。使用 RequestMultiplePermissions 协定类的流程几乎与此相同。
//在 Activity 或 Fragment 的初始化逻辑中，将 ActivityResultCallback 的实现传入对 registerForActivityResult() 的调用。ActivityResultCallback 定义应用如何处理用户对权限请求的响应。
//保留对 registerForActivityResult()（类型为 ActivityResultLauncher）的返回值的引用。
//如需在必要时显示系统权限对话框，请对您在上一步中保存的 ActivityResultLauncher 实例调用 launch() 方法。
//调用 launch() 之后，系统会显示系统权限对话框。当用户做出选择后，系统会异步调用您在上一步中定义的 ActivityResultCallback 实现。
//调用 launch() 之后，系统会显示系统权限对话框。当用户做出选择后，系统会异步调用您在上一步中定义的 ActivityResultCallback 实现。
//注意：应用无法自定义调用 launch() 时显示的对话框。如需为用户提供更多信息或上下文，请更改应用的界面，以便让用户更容易了解应用中的功能为何需要特定权限。例如，您可以更改用于启用该功能的按钮中的文本。
//此外，系统权限对话框中的文本会提及与您请求的权限关联的权限组。此权限分组是为了让系统易于使用，您的应用不应依赖于特定权限组之内或之外的权限。
//        以下代码段演示了检查权限并根据需要向用户请求权限的建议流程：
//        Context CONTEXT=this;
//        if (ContextCompat.checkSelfPermission(
//                CONTEXT, Manifest.permission.REQUESTED_PERMISSION) ==
//                PackageManager.PERMISSION_GRANTED) {
//            // You can use the API that requires the permission.
//            performAction(...);
//        } else if (shouldShowRequestPermissionRationale(...)) {
//            // In an educational UI, explain to the user why your app requires this
//            // permission for a specific feature to behave as expected. In this UI,
//            // include a "cancel" or "no thanks" button that allows the user to
//            // continue using your app without granting the permission.
//            showInContextUI(...);
//        } else {
//            // You can directly ask for the permission.
//            // The registered ActivityResultCallback gets the result of this request.
//            requestPermissionLauncher.launch(
//                    Manifest.permission.REQUESTED_PERMISSION);
//        }

        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                1);
        String apkPath = "";
        if (Build.VERSION.SDK_INT >= 26) {
            //来判断应用是否有权限安装apk
            boolean installAllowed = getPackageManager().canRequestPackageInstalls();
            //有权限
            if (installAllowed) {
                //安装apk
                install(apkPath);
            } else {
                //无权限 申请权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.REQUEST_INSTALL_PACKAGES}, INSTALL_APK_REQUESTCODE);
            }
        } else {
            install(apkPath);
        }

    }

    private void install(String s) {
        Intent installApkIntent = null;
        Uri apkUri = null;
        File apkFile = null;
        if (Build.VERSION.SDK_INT >= 24) { //Android 7.0版本
            // 参数2 清单文件中provider节点里面的authorities ; 参数3 共享的文件,即apk包的file类
            //对目标应用临时授权该Uri所代表的文件
            installApkIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            installApkIntent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) { //Android6.0 版本
            installApkIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            installApkIntent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
            installApkIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } else { //Android 5.0 以下版本
            installApkIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            installApkIntent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        }
        this.startActivity(installApkIntent);

    }

    private String getId() {
        //使用广告 ID
        //广告 ID 是一种可由用户重置的标识符，适用于广告用例。但是，在使用此 ID 时，请注意以下要点：
        //在重置广告 ID 时始终尊重用户的意愿。在未经用户同意的情况下，请勿使用其他标识符或指纹将后续广告 ID 关联起来，对用户重置进行桥接。Google Play 开发者内容政策声明如下：
        //“…重置后，在未获得用户明确许可的情况下，新的广告标识符不得与先前的广告标识符或由先前的广告标识符所衍生的数据相关联。”
        //始终尊重关联的个性化广告标记。广告 ID 是可配置的，用户可以限制与 ID 关联的跟踪数量。请务必使用 AdvertisingIdClient.Info.isLimitAdTrackingEnabled() 方法，确保您没有忽视用户的意愿。Google Play 开发者内容政策声明如下：
        //“…您还必须遵循用户的‘选择停用针对用户兴趣投放广告’或‘选择停用广告个性化功能’设置。如果用户已启用此设置，您不得出于广告目的而使用广告标识符创建用户个人资料，也不得使用广告标识符向用户投放个性化广告。允许的活动包括：内容相关广告定位、频次上限、转化跟踪、生成报表以及安全性和欺诈检测。”
        //注意与您使用的 SDK 有关、涉及广告 ID 用途的任何隐私权或安全性政策。例如，如果您将 true 传递到 Google Analytics（分析）SDK 中的 enableAdvertisingIdCollection() 方法，请务必查看和遵守所有适用的 Google Analytics（分析）SDK 政策。
        //此外，还要注意，Google Play 开发者内容政策要求广告 ID“不得与个人身份信息或任何永久性设备标识符（例如：SSAID、MAC 地址、IMEI 等）相关联。”
        if (AdvertisingIdClient.isAdvertisingIdProviderAvailable(this)) {
//            生成令牌需要由Google Developers Console生成的项目ID 。
            String authorizedEntity = PROJECT_ID; // Project id from Google Developer Console
            String scope = "GCM"; // e.g. communicating using GCM, but you can use any
            // URL-safe characters up to a maximum of 1000, or
            // you can also leave it blank.
            Context context = this;
//            String token = InstanceID.getInstance(this).getToken(authorizedEntity, scope);
//            //设置Google Play服务
//            //要编写客户端应用程序，请使用Google Play服务SDK，如设置Google Play服务SDK中所述。播放服务库包括实例ID库。
//            //获取实例ID
//            //下面的代码行返回一个实例ID：
//            String iid = InstanceID.getInstance(this).getId();
////            删除令牌和实例ID
//            InstanceID.getInstance(context).deleteToken(authorizedEntity, scope);
////            您还可以删除实例ID本身，包括所有关联的令牌。下次致电时，getInstance()您将获得一个新的实例ID：
//            InstanceID.getInstance(context).deleteInstanceID();
//            String newIID = InstanceID.getInstance(context).getId();
//            注意：如果您的应用使用了已被删除的令牌，则deleteInstanceID您的应用将需要生成替换令牌。


        }
        String m_szDevIDShort = "35" + //we make this look like a valid IMEI
                Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +
                Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +
                Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +
                Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +
                Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +
                Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +
                Build.USER.length() % 10; //13 digits

        return null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        //自行管理权限请求代码
        //作为允许系统管理权限请求代码的替代方法，您可以自行管理权限请求代码。为此，请在对 requestPermissions() 的调用中添加请求代码。
        //注意：应用无法自定义调用 requestPermissions() 时显示的对话框。系统权限对话框中的文本会提及权限组，但此权限分组是为了让系统易于使用。您的应用不应依赖于特定权限组之内或之外的权限。
        //以下代码段演示了如何使用请求代码来请求权限：
        //if (ContextCompat.checkSelfPermission(
        // CONTEXT, Manifest.permission.REQUESTED_PERMISSION) ==
        // PackageManager.PERMISSION_GRANTED) {
        // // You can use the API that requires the permission.
        // performAction(...);
        //} else if (shouldShowRequestPermissionRationale(...)) {
        // // In an educational UI, explain to the user why your app requires this
        // // permission for a specific feature to behave as expected. In this UI,
        // // include a "cancel" or "no thanks" button that allows the user to
        // // continue using your app without granting the permission.
        // showInContextUI(...);
        //} else {
        // // You can directly ask for the permission.
        // requestPermissions(CONTEXT,
        // new String[] { Manifest.permission.REQUESTED_PERMISSION },
        // REQUEST_CODE);
        //}
        //当用户响应系统权限对话框后，系统就会调用应用的 onRequestPermissionsResult() 实现。系统会传入用户对权限对话框的响应以及您定义的请求代码，如以下代码段所示：
        //@Override
        //public void onRequestPermissionsResult(int requestCode, String[] permissions,
        // int[] grantResults) {
        // switch (requestCode) {
        // case PERMISSION_REQUEST_CODE:
        // // If request is cancelled, the result arrays are empty.
        // if (grantResults.length > 0 &&
        // grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        // // Permission is granted. Continue the action or workflow
        // // in your app.
        // } else {
        // // Explain to the user that the feature is unavailable because
        // // the features requires a permission that the user has denied.
        // // At the same time, respect the user's decision. Don't link to
        // // system settings in an effort to convince the user to change
        // // their decision.
        // }
        // return;
        // }
        // // Other 'case' lines to check for other
        // // permissions this app might request.
        // }
        //}
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                    Toast.makeText(MainActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            case 11: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                    Toast.makeText(MainActivity.this, "Permission denied to install packages", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private boolean isHardWareAvailable() {
//        确定硬件可用性
//        如果您将硬件声明为可选，您的应用在没有该硬件的设备上也可以运行。要检查设备是否具有特定的硬件，请使用 hasSystemFeature() 方法，如以下代码段所示。如果设备不具有该硬件，只需在您的应用中停用此功能即可。
// Check whether your app is running on a device that has a front-facing camera.
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA_FRONT)) {
            // Continue with the part of your app's workflow that requires a
            // front-facing camera.
        } else {
            // Gracefully degrade your app experience.
        }
        return false;
    }
}