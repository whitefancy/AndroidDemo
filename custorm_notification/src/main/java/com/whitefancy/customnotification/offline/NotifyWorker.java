package com.whitefancy.customnotification.offline;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.whitefancy.customnotification.App;
import com.whitefancy.customnotification.MainActivity;
import com.whitefancy.customnotification.NotificationReceiver;
import com.whitefancy.customnotification.R;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;
import static com.whitefancy.customnotification.App.CHANNEL_ID;

//定义工作
//工作使用 Worker 类定义。doWork() 方法在 WorkManager 提供的后台线程上异步运行。
//如需为 WorkManager 创建一些要运行的工作，请扩展 Worker 类并替换 doWork() 方法。例如，如需创建上传图像的 Worker，您可以执行以下操作：
//KOTLIN
//JAVA
//public class UploadWorker extends Worker {
//   public UploadWorker(
//       @NonNull Context context,
//       @NonNull WorkerParameters params) {
//       super(context, params);
//   }
//   @Override
//   public Result doWork() {
//     // Do the work here--in this case, upload the images.
//     uploadImages();
//     // Indicate whether the work finished successfully with the Result
//     return Result.success();
//   }
//}
//从 doWork() 返回的 Result 会通知 WorkManager 服务工作是否成功，以及工作失败时是否应重试工作。
//Result.success()：工作成功完成。
//Result.failure()：工作失败。
//Result.retry()：工作失败，应根据其重试政策在其他时间尝试。
public class NotifyWorker extends Worker {
    public NotifyWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        // Method to trigger an instant notification
        triggerNotification();

        return Result.success();
        // (Returning RETRY tells WorkManager to try this task again
        // later; FAILURE says not to try again.)
    }

    //we set a tag to be able to cancel all work of this type if needed
    public static final String workTag = "notificationWork";

    private void triggerNotification() {
        TextNotification();

    }

    private void TextNotification() {
        Intent playIntent = new Intent(getApplicationContext(), MainActivity.class);

//put together the PendingIntent
        PendingIntent pendingIntent =
                PendingIntent.getActivity(getApplicationContext(), 1, playIntent, FLAG_UPDATE_CURRENT);

        String notificationTitle = getInputData().getString("title");
        String notificationText = getInputData().getString("content");
        //build the notification
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(getApplicationContext(), App.CHANNEL_ID)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(notificationTitle)
                        .setContentText(notificationText)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        //trigger the notification
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(getApplicationContext());

        //we give each notification the ID of the event it's describing,
        //to ensure they all show up and there are no duplicates
        notificationManager.notify(888, notificationBuilder.build());
    }

    private void PictureNotification() {
        String title = getInputData().getString("title");
        String contect = getInputData().getString("contect");
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().
                getSystemService(Context.NOTIFICATION_SERVICE);
        Context context = getApplicationContext();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            在Android 8.0中所有的通知都需要提供通知渠道，否则，所有通知在8.0系统上都不能正常显示。
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Example Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            //we can optionally add a description for the channel
            String description = "A channel which shows notifications about events at Manasia";
            channel.setDescription(description);

            //we can optionally set notification LED colour
            channel.setLightColor(Color.MAGENTA);
            // Register the channel with the system

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
        RemoteViews collapsedView = new RemoteViews(context.getPackageName(),
                R.layout.notification_collapsed);
        RemoteViews expandedView = new RemoteViews(context.getPackageName(),
                R.layout.notification_expanded);
        Intent clickIntent = new Intent(context, NotificationReceiver.class);
        PendingIntent clickPendingIntent = PendingIntent.getBroadcast(context,
                0, clickIntent, 0);
        collapsedView.setTextViewText(R.id.text_view_collapsed_1, "Hello World!");
        expandedView.setImageViewResource(R.id.image_view_expanded, R.mipmap.ic_launcher);
        expandedView.setOnClickPendingIntent(R.id.image_view_expanded, clickPendingIntent);
        Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setCustomContentView(collapsedView)
                .setCustomBigContentView(expandedView)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                //.setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .build();
        notificationManager.notify(888, notification);

//        String id = "my_channel_01";
//        CharSequence name = getString(R.string.channel_name);
//        String description = getString(R.string.channel_description);
//        以下示例代码说明了如何针对某个通知渠道隐藏标志：
//        int importance = NotificationManager.IMPORTANCE_LOW;
//        NotificationChannel mChannel = new NotificationChannel(id, name, importance);
//        mChannel.setDescription(description);
//        mChannel.setShowBadge(false);
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.createNotificationChannel(mChannel);

//        设置自定义通知计数
//        默认情况下，长按菜单上显示的数字会随着通知数量递增（如图 1 所示），但您可以针对自己的应用替换此数字。例如，如果您仅使用一个通知表示多条新信息，但希望此处的计数代表新信息总数，这种情况下，替换该数字可能会很有用。
//        如需设置自定义数字，请对通知调用 setNumber()，如下所示：
//        Notification notification = new NotificationCompat.Builder(MainActivity.this, CHANNEL_ID)
//                .setContentTitle("New Messages")
//                .setContentText("You've received 3 new messages.")
//                .setSmallIcon(R.drawable.ic_notify_status)
//                .setNumber(messageCount)
//                .build();
//        修改通知的长按菜单图标
//        长按菜单会显示与通知关联的大图标或小图标（如果有）。默认情况下，系统会显示大图标，但是您可以调用 Notification.Builder.setBadgeIconType() 并传入 BADGE_ICON_SMALL 常量以显示小图标。
//        KOTLIN
//                JAVA
//        Notification notification = new NotificationCompat.Builder(MainActivity.this, CHANNEL_ID)
//                .setContentTitle("New Messages")
//                .setContentText("You've received 3 new messages.")
//                .setSmallIcon(R.drawable.ic_notify_status)
//                .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
//                .build();
//使用 MessagingStyle
//从 Android 7.0（API 级别 24）起，Android 提供了专用于消息内容的通知样式模板。使用 NotificationCompat.MessagingStyle 类，您可以更改在通知中显示的多个标签，包括会话标题、其他消息和通知的内容视图。
//以下代码段展示了如何使用 MessagingStyle 类自定义通知的样式。
//    Notification notification = new Notification.Builder(this, CHANNEL_ID)
//            .setStyle(new NotificationCompat.MessagingStyle("Me")
//                    .setConversationTitle("Team lunch")
//                    .addMessage("Hi", timestamp1, null) // Pass in null for user.
//                    .addMessage("What's up?", timestamp2, "Coworker")
//                    .addMessage("Not much", timestamp3, null)
//                    .addMessage("How about lunch?", timestamp4, "Coworker"))
//            .build();
//从 Android 8.0（API 级别 26）起，使用 NotificationCompat.MessagingStyle 类的通知会在采用折叠形式时显示更多内容。您还可以使用 addHistoricMessage() 方法，通过向与消息相关的通知添加历史消息为会话提供上下文。
//使用 NotificationCompat.MessagingStyle 时：
//调用 MessagingStyle.setConversationTitle()，为超过两个用户的群聊设置标题。一个好的会话标题可能是群组名称，也可能是会话参与者的列表（如果没有具体名称）。否则，消息可能会被误以为属于与会话中最近消息发送者的一对一会话。
//使用 MessagingStyle.setData() 方法包含媒体消息，如图片等。目前支持 image/* 格式的 MIME 类型。
//使用直接回复
//直接回复允许用户以内嵌方式回复消息。
//在用户通过内嵌回复操作回复后，使用 MessagingStyle.addMessage() 更新 MessagingStyle 通知，并且不要撤消或取消通知。不取消通知可以让用户从通知发送多个回复。
//如需让内嵌回复操作与 Wear OS 兼容，请调用 Action.WearableExtender.setHintDisplayInlineAction(true)。
//通过向通知添加历史消息，使用 addHistoricMessage() 方法向直接回复会话提供上下文。
//启用智能回复
//如需启用智能回复，请对回复操作调用 setAllowGeneratedResponses(true)。这样，在通知桥接到 Wear OS 设备后，用户就能使用智能回复响应。智能回复响应由一个完全在手表上的机器学习模型使用 NotificationCompat.MessagingStyle 通知提供的上下文生成，不会将数据上传到互联网上来生成回复。
//添加通知元数据
//分配通知元数据以告知系统如何在设备处于勿扰模式时处理应用通知。例如，使用 addPerson() 或 setCategory(Notification.CATEGORY_MESSAGE) 方法替换勿扰模式。
//更新通知
//如需在发出此通知后对其进行更新，请再次调用 NotificationManagerCompat.notify()，并将之前使用的具有同一 ID 的通知传递给该方法。如果之前的通知已被关闭，则系统会创建一个新通知。
//您可以选择性调用 setOnlyAlertOnce()，这样通知只会在通知首次出现时打断用户（通过声音、振动或视觉提示），而之后更新则不会再打断用户。
//注意：Android 会在更新通知时应用速率限制。如果您过于频繁地发布对某条通知的更新（不到一秒内发布多条通知），系统可能会丢弃部分更新。
//移除通知
//除非发生以下情况之一，否则通知仍然可见：
//用户关闭通知。
//用户点击通知，且您在创建通知时调用了 setAutoCancel()。
//您针对特定的通知 ID 调用了 cancel()。此方法还会删除当前通知。
//您调用了 cancelAll() 方法，该方法将移除之前发出的所有通知。
//如果您在创建通知时使用 setTimeoutAfter() 设置了超时，系统会在指定持续时间过后取消通知。如果需要，您可以在指定的超时持续时间过去之前取消通知。
    }
}
//创建通知渠道
//如需创建通知渠道，请按以下步骤操作：
//构建一个具有唯一渠道 ID、用户可见名称和重要性级别的 NotificationChannel 对象。
//（可选）使用 setDescription() 指定用户在系统设置中看到的说明。
//注册通知渠道，方法是将该渠道传递给 createNotificationChannel()。
//    private void createNotificationChannel() {
//        // Create the NotificationChannel, but only on API 26+ because
//        // the NotificationChannel class is new and not in the support library
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = getString(R.string.channel_name);
//            String description = getString(R.string.channel_description);
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
//            channel.setDescription(description);
//            // Register the channel with the system; you can't change the importance
//            // or other notification behaviors after this
//            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//        }
//    }
//创建采用其原始值的现有通知渠道不会执行任何操作，因此可以放心地在启动应用时调用此代码。
//默认情况下，发布到此渠道的所有通知都使用由 NotificationManagerCompat 类中的重要性级别（如 IMPORTANCE_DEFAULT 和 IMPORTANCE_HIGH）定义的视觉和听觉行为。请参阅下文，详细了解重要性级别）。
//如果您希望进一步自定义渠道的默认通知行为，可以在 NotificationChannel 上调用 enableLights()、setLightColor() 和 setVibrationPattern() 等方法。但请注意，创建渠道后，您将无法更改这些设置，而且对于是否启用相应行为，用户拥有最终控制权。
//您还可以通过调用 createNotificationChannels() 在一次操作中创建多个通知渠道。