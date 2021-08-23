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
//灵活的重试政策
//有时工作会失败。WorkManager 提供了灵活的重试政策，包括可配置的指数退避政策。
//工作链
//对于复杂的相关工作，您可以使用流畅自然的接口将各个工作任务串联起来，这样您便可以控制哪些部分依序运行，哪些部分并行运行。
//KOTLIN
//JAVA
//WorkManager.getInstance(...)
//    .beginWith(Arrays.asList(workA, workB))
//    .then(workC)
//    .enqueue();
//对于每项工作任务，您可以定义工作的输入和输出数据。将工作串联在一起时，WorkManager 会自动将输出数据从一个工作任务传递给下一个工作任务。
//内置线程互操作性
//WorkManager 无缝集成 RxJava 和 协程，并可灵活地插入您自己的异步 API。
//使用 WorkManager 实现可延期、可靠的工作
//WorkManager 适用于可延期工作，即不需要立即运行但需要可靠运行的工作，即使退出应用或重启设备也不影响工作的执行。例如：
//向后端服务发送日志或分析数据
//定期将应用数据与服务器同步
//WorkManager 不适用于那些可在应用进程结束时安全终止的进程内后台工作，也不适用于需要立即执行的工作。请查看后台处理指南，了解哪种解决方案符合您的需求。
//创建 WorkRequest
//定义工作后，必须使用 WorkManager 服务进行调度该工作才能运行。对于如何调度工作，WorkManager 提供了很大的灵活性。您可以将其安排为在某段时间内定期运行，也可以将其安排为仅运行一次。
//不论您选择以何种方式调度工作，请始终使用 WorkRequest。Worker 定义工作单元，WorkRequest（及其子类）则定义工作运行方式和时间。在最简单的情况下，您可以使用 OneTimeWorkRequest，如以下示例所示。
//WorkRequest uploadWorkRequest =
//   new OneTimeWorkRequest.Builder(UploadWorker.class)
//       .build();
//将 WorkRequest 提交给系统
//最后，您需要使用 enqueue() 方法将 WorkRequest 提交到 WorkManager。
//KOTLIN
//JAVA
//WorkManager
//    .getInstance(myContext)
//    .enqueue(uploadWorkRequest);
//执行工作器的确切时间取决于 WorkRequest 中使用的约束和系统优化方式。WorkManager 经过设计，能够在满足这些约束的情况下提供最佳行为。
//定义工作请求
//入门指南介绍了如何创建简单的 WorkRequest 并将其加入队列。
//在本指南中，您将了解如何定义和自定义 WorkRequest 对象来处理常见用例，例如：
//调度一次性工作和重复性工作
//设置工作约束条件，例如要求连接到 Wi-Fi 网络或正在充电
//确保至少延迟一定时间再执行工作
//设置重试和退避策略
//将输入数据传递给工作
//使用标记将相关工作分组在一起
//概览
//工作通过 WorkRequest 在 WorkManager 中进行定义。为了使用 WorkManager 调度任何工作，您必须先创建一个 WorkRequest 对象，然后将其加入队列。
//WorkRequest myWorkRequest = ...
//WorkManager.getInstance(myContext).enqueue(myWorkRequest);
//WorkRequest 对象包含 WorkManager 调度和运行工作所需的所有信息。其中包括运行工作必须满足的约束、调度信息（例如延迟或重复间隔）、重试配置，并且可能包含输入数据（如果工作需要）。
//WorkRequest 本身是抽象基类。该类有两个派生实现，可用于创建 OneTimeWorkRequest 和 PeriodicWorkRequest 请求。顾名思义，OneTimeWorkRequest 适用于调度非重复性工作，而 PeriodicWorkRequest 则更适合调度以一定间隔重复执行的工作。
//调度一次性工作
//对于无需额外配置的简单工作，请使用静态方法 from：
//KOTLIN
//JAVA
//WorkRequest myWorkRequest = OneTimeWorkRequest.from(MyWork.class);
//对于更复杂的工作，可以使用构建器。
//KOTLIN
//JAVA
//WorkRequest uploadWorkRequest =
//   new OneTimeWorkRequest.Builder(MyWork.class)
//       // Additional configuration
//       .build();
//调度定期工作
//您的应用有时可能需要定期运行某些工作。例如，您可能要定期备份数据、定期下载应用中的新鲜内容或者定期上传日志到服务器。
//使用 PeriodicWorkRequest 创建定期执行的 WorkRequest 对象的方法如下：
//KOTLIN
//JAVA
//PeriodicWorkRequest saveRequest =
//       new PeriodicWorkRequest.Builder(SaveImageToFileWorker.class, 1, TimeUnit.HOURS)
//           // Constraints
//           .build();
//在此示例中，工作的运行时间间隔定为一小时。
//时间间隔定义为两次重复执行之间的最短时间。工作器的确切执行时间取决于您在 WorkRequest 对象中设置的约束以及系统执行的优化。
//注意：可以定义的最短重复间隔是 15 分钟（与 JobScheduler API 相同）。
//灵活的运行间隔
//如果您的工作的性质致使其对运行时间敏感，您可以将 PeriodicWorkRequest 配置为在每个时间间隔的灵活时间段内运行，如图 1 所示。
//调度一次性工作
//对于无需额外配置的简单工作，请使用静态方法 from：
//KOTLIN
//JAVA
//WorkRequest myWorkRequest = OneTimeWorkRequest.from(MyWork.class);
//对于更复杂的工作，可以使用构建器。
//KOTLIN
//JAVA
//WorkRequest uploadWorkRequest =
//   new OneTimeWorkRequest.Builder(MyWork.class)
//       // Additional configuration
//       .build();
//调度定期工作
//您的应用有时可能需要定期运行某些工作。例如，您可能要定期备份数据、定期下载应用中的新鲜内容或者定期上传日志到服务器。
//使用 PeriodicWorkRequest 创建定期执行的 WorkRequest 对象的方法如下：
//KOTLIN
//JAVA
//PeriodicWorkRequest saveRequest =
//       new PeriodicWorkRequest.Builder(SaveImageToFileWorker.class, 1, TimeUnit.HOURS)
//           // Constraints
//           .build();
//在此示例中，工作的运行时间间隔定为一小时。
//时间间隔定义为两次重复执行之间的最短时间。工作器的确切执行时间取决于您在 WorkRequest 对象中设置的约束以及系统执行的优化。
//注意：可以定义的最短重复间隔是 15 分钟（与 JobScheduler API 相同）。
//灵活的运行间隔
//如果您的工作的性质致使其对运行时间敏感，您可以将 PeriodicWorkRequest 配置为在每个时间间隔的灵活时间段内运行，如图 1 所示。
//工作状态
//工作在其整个生命周期内经历了一系列 State 更改。
//一次性工作的状态
//对于 one-time 工作请求，工作的初始状态为 ENQUEUED。
//在 ENQUEUED 状态下，您的工作会在满足其 Constraints 和初始延迟计时要求后立即运行。接下来，该工作会转为 RUNNING 状态，然后可能会根据工作的结果转为 SUCCEEDED、FAILED 状态；或者，如果结果是 retry，它可能会回到 ENQUEUED 状态。在此过程中，随时都可以取消工作，取消后工作将进入 CANCELLED 状态。
//图 1 展示了一次性工作的生命周期，事件可能会进入另一个状态。
//图 1. 一次性工作的状态图。
//SUCCEEDED、FAILED 和 CANCELLED 均表示此工作的终止状态。如果您的工作处于上述任何状态，WorkInfo.State.isFinished() 都将返回 true。
//定期工作的状态
//成功和失败状态仅适用于一次性工作和链式工作。定期工作只有一个终止状态 CANCELLED。这是因为定期工作永远不会结束。每次运行后，无论结果如何，系统都会重新对其进行调度。图 2 描述了定期工作的精简状态图。
//BLOCKED 状态
//还有一种我们尚未提到的最终状态，那就是 BLOCKED。此状态适用于一系列已编排的工作，或者说工作链。链接工作中介绍了工作链及其状态图。
//管理工作
//定义 Worker 和 WorkRequest 后，最后一步是将工作加入队列。将工作加入队列的最简单方法是调用 WorkManager enqueue() 方法，然后传递要运行的 WorkRequest。
//KOTLIN
//JAVA
//WorkRequest myWork = // ... OneTime or PeriodicWork
//WorkManager.getInstance(requireContext()).enqueue(myWork);
//在将工作加入队列时请小心谨慎，以避免重复。例如，应用可能会每 24 小时尝试将其日志上传到后端服务。如果不谨慎，即使作业只需运行一次，您最终也可能会多次将同一作业加入队列。为了实现此目标，您可以将工作调度为唯一工作。
//唯一工作
//唯一工作是一个很实用的概念，可确保同一时刻只有一个具有特定名称的工作实例。与 ID 不同的是，唯一名称是人类可读的，由开发者指定，而不是由 WorkManager 自动生成。与标记不同，唯一名称仅与一个工作实例相关联。
//唯一工作既可用于一次性工作，也可用于定期工作。您可以通过调用以下方法之一创建唯一工作序列，具体取决于您是调度重复工作还是一次性工作。
//WorkManager.enqueueUniqueWork()（用于一次性工作）
//WorkManager.enqueueUniquePeriodicWork()（用于定期工作）
//这两种方法都接受 3 个参数：
//uniqueWorkName - 用于唯一标识工作请求的 String。
//existingWorkPolicy - 此 enum 可告知 WorkManager：如果已有使用该名称且尚未完成的唯一工作链，应执行什么操作。如需了解详情，请参阅冲突解决政策。
//work - 要调度的 WorkRequest。
//借助唯一工作，我们可以解决前面提到的重复调度问题。
//KOTLIN
//JAVA
//PeriodicWorkRequest sendLogsWorkRequest = new
//      PeriodicWorkRequest.Builder(SendLogsWorker.class, 24, TimeUnit.HOURS)
//              .setConstraints(new Constraints.Builder()
//              .setRequiresCharging(true)
//          .build()
//      )
//     .build();
//WorkManager.getInstance(this).enqueueUniquePeriodicWork(
//     "sendLogs",
//     ExistingPeriodicWorkPolicy.KEEP,
//     sendLogsWorkRequest);
//现在，如果上述代码在 sendLogs 作业已处于队列中的情况下运行，系统会保留现有的作业，并且不会添加新的作业。
//当您需要逐步构建一个长任务链时，也可以利用唯一工作序列。例如，照片编辑应用可能允许用户撤消一长串操作。其中的每一项撤消操作可能都需要一些时间来完成，但必须按正确的顺序执行。在这种情况下，应用可以创建一个“撤消”链，并根据需要将每个撤消操作附加到该链上。如需了解详情，请参阅链接工作。
//冲突解决政策
//调度唯一工作时，您必须告知 WorkManager 在发生冲突时要执行的操作。您可以通过在将工作加入队列时传递一个枚举来实现此目的。
//对于一次性工作，您需要提供一个 ExistingWorkPolicy，它支持用于处理冲突的 4 个选项。
//REPLACE：用新工作替换现有工作。此选项将取消现有工作。
//KEEP：保留现有工作，并忽略新工作。
//APPEND：将新工作附加到现有工作的末尾。此政策将导致您的新工作链接到现有工作，在现有工作完成后运行。
//现有工作将成为新工作的先决条件。如果现有工作变为 CANCELLED 或 FAILED 状态，新工作也会变为 CANCELLED 或 FAILED。如果您希望无论现有工作的状态如何都运行新工作，请改用 APPEND_OR_REPLACE。
//APPEND_OR_REPLACE 函数类似于 APPEND，不过它并不依赖于先决条件工作状态。即使现有工作变为 CANCELLED 或 FAILED 状态，新工作仍会运行。
//对于定期工作，您需要提供一个 ExistingPeriodicWorkPolicy，它支持 REPLACE 和 KEEP 这两个选项。这些选项的功能与其对应的 ExistingWorkPolicy 功能相同。
//观察您的工作
//在将工作加入队列后，您可以随时按其 name、id 或与其关联的 tag 在 WorkManager 中进行查询，以检查其状态。
//KOTLIN
//JAVA
//// by id
//workManager.getWorkInfoById(syncWorker.id); // ListenableFuture<WorkInfo>
//// by name
//workManager.getWorkInfosForUniqueWork("sync"); // ListenableFuture<List<WorkInfo>>
//// by tag
//workManager.getWorkInfosByTag("syncTag"); // ListenableFuture<List<WorkInfo>>
//该查询会返回 WorkInfo 对象的 ListenableFuture，该值包含工作的 id、其标记、其当前的 State 以及通过 Result.success(outputData) 设置的任何输出数据。
//利用每个方法的 LiveData 变种，您可以通过注册监听器来观察 WorkInfo 的变化。例如，如果您想要在某项工作成功完成后向用户显示消息，您可以进行如下设置：
//KOTLIN
//JAVA
//workManager.getWorkInfoByIdLiveData(syncWorker.id)
//        .observe(getViewLifecycleOwner(), workInfo -> {
//    if (workInfo.getState() != null &&
//            workInfo.getState() == WorkInfo.State.SUCCEEDED) {
//        Snackbar.make(requireView(),
//                    R.string.work_completed, Snackbar.LENGTH_SHORT)
//                .show();
//   }
//});
//复杂的工作查询
//WorkManager 2.4.0 及更高版本支持使用 WorkQuery 对象对已加入队列的作业进行复杂查询。WorkQuery 支持按工作的标记、状态和唯一工作名称的组合进行查询。
//以下示例说明了如何查找带有“syncTag”标记、处于 FAILED 或 CANCELLED 状态，且唯一工作名称为“preProcess”或“sync”的所有工作。
//KOTLIN
//JAVA
//WorkQuery workQuery = WorkQuery.Builder
//       .fromTags(Arrays.asList("syncTag"))
//       .addStates(Arrays.asList(WorkInfo.State.FAILED, WorkInfo.State.CANCELLED))
//       .addUniqueWorkNames(Arrays.asList("preProcess", "sync")
//     )
//    .build();
//ListenableFuture<List<WorkInfo>> workInfos = workManager.getWorkInfos(workQuery);
//WorkQuery 中的每个组件（标记、状态或名称）与其他组件都是 AND 逻辑关系。组件中的每个值都是 OR 逻辑关系。例如：<em>(name1 OR name2 OR ...) AND (tag1 OR tag2 OR ...) AND (state1 OR state2 OR ...)</em>
//WorkQuery 也适用于等效的 LiveData 方法 getWorkInfosLiveData()。
//取消和停止工作
//如果您不再需要运行先前加入队列的工作，则可以要求将其取消。您可以按工作的 name、id 或与其关联的 tag 取消工作。
//KOTLIN
//JAVA
//// by id
//workManager.cancelWorkById(syncWorker.id);
//// by name
//workManager.cancelUniqueWork("sync");
//// by tag
//workManager.cancelAllWorkByTag("syncTag");
//WorkManager 会在后台检查工作的 State。如果工作已经完成，系统不会执行任何操作。否则，工作的状态会更改为 CANCELLED，之后就不会运行这个工作。任何依赖于此工作的 WorkRequest 作业也将变为 CANCELLED。
//目前，RUNNING 可收到对 ListenableWorker.onStopped() 的调用。如需执行任何清理操作，请替换此方法。如需了解详情，请参阅停止正在运行的工作器。
//注意：cancelAllWorkByTag(String) 会取消具有给定标记的所有工作。
//停止正在运行的工作器
//正在运行的 Worker 可能会由于以下几种原因而停止运行：
//您明确要求取消它（例如，通过调用 WorkManager.cancelWorkById(UUID) 取消）。
//如果是唯一工作，您明确地将 ExistingWorkPolicy 为 REPLACE 的新 WorkRequest 加入到了队列中。旧的 WorkRequest 会立即被视为已取消。
//您的工作约束条件已不再满足。
//系统出于某种原因指示您的应用停止工作。如果超过 10 分钟的执行期限，可能会发生这种情况。该工作会调度为在稍后重试。
//在这些情况下，您的工作器会停止。
//您应该合作地取消正在进行的任何工作，并释放您的工作器保留的所有资源。例如，此时应该关闭所打开的数据库和文件句柄。有两种机制可让您了解工作器何时停止。
//onStopped() 回调
//在您的工作器停止后，WorkManager 会立即调用 ListenableWorker.onStopped()。替换此方法可关闭您可能保留的所有资源。
//isStopped() 属性
//您可以调用 ListenableWorker.isStopped() 方法以检查工作器是否已停止。如果您在工作器执行长时间运行的操作或重复操作，您应经常检查此属性，并尽快将其用作停止工作的信号。
//注意：WorkManager 会忽略已收到 onStop 信号的工作器所设置的 Result，因为工作器已被视为停止。
//观察工作器的中间进度
//WorkManager 2.3.0-alpha01 为设置和观察工作器的中间进度添加了一流的支持。如果应用在前台运行时，工作器保持运行状态，那么也可以使用返回 WorkInfo 的 LiveData 的 API 向用户显示此信息。
//ListenableWorker 现在支持 setProgressAsync() API，此类 API 允许保留中间进度。借助这些 API，开发者能够设置可通过界面观察到的中间进度。进度由 Data 类型表示，这是一个可序列化的属性容器（类似于 input 和 output，并且受到相同的限制）。
//只有在 ListenableWorker 运行时才能观察到和更新进度信息。如果尝试在 ListenableWorker 完成执行后在其中设置进度，则将会被忽略。您还可以使用 getWorkInfoBy…() 或 getWorkInfoBy…LiveData() 方法来观察进度信息。这两个方法会返回 WorkInfo 的实例，后者有一个返回 Data 的新 getProgress() 方法。
//更新进度
//对于使用 ListenableWorker 或 Worker 的 Java 开发者，setProgressAsync() API 会返回 ListenableFuture<Void>；更新进度是异步过程，因为更新过程涉及将进度信息存储在数据库中。在 Kotlin 中，您可以使用 CoroutineWorker 对象的 setProgress() 扩展函数来更新进度信息。
//此示例展示了一个简单的 ProgressWorker。Worker 在启动时将进度设置为 0，在完成后将进度值更新为 100。
//KOTLIN
//JAVA
//import android.content.Context;
//import androidx.annotation.NonNull;
//import androidx.work.Data;
//import androidx.work.Worker;
//import androidx.work.WorkerParameters;
//public class ProgressWorker extends Worker {
//    private static final String PROGRESS = "PROGRESS";
//    private static final long DELAY = 1000L;
//    public ProgressWorker(
//        @NonNull Context context,
//        @NonNull WorkerParameters parameters) {
//        super(context, parameters);
//        // Set initial progress to 0
//        setProgressAsync(new Data.Builder().putInt(PROGRESS, 0).build());
//    }
//    @NonNull
//    @Override
//    public Result doWork() {
//        try {
//            // Doing work.
//            Thread.sleep(DELAY);
//        } catch (InterruptedException exception) {
//            // ... handle exception
//        }
//        // Set progress to 100 after you are done doing your work.
//        setProgressAsync(new Data.Builder().putInt(PROGRESS, 100).build());
//        return Result.success();
//    }
//}
//观察进度
//观察进度信息也很简单。您可以使用 getWorkInfoBy…() 或 getWorkInfoBy…LiveData() 方法，并引用 WorkInfo。
//以下是使用 getWorkInfoByIdLiveData API 的示例。
//KOTLIN
//JAVA
//WorkManager.getInstance(getApplicationContext())
//     // requestId is the WorkRequest id
//     .getWorkInfoByIdLiveData(requestId)
//     .observe(lifecycleOwner, new Observer<WorkInfo>() {
//             @Override
//             public void onChanged(@Nullable WorkInfo workInfo) {
//                 if (workInfo != null) {
//                     Data progress = workInfo.getProgress();
//                     int value = progress.getInt(PROGRESS, 0)
//                     // Do something with progress
//             }
//      }
//});
//链接工作
//您可以使用 WorkManager 创建工作链并将其加入队列。工作链用于指定多个依存任务并定义这些任务的运行顺序。当您需要以特定顺序运行多个任务时，此功能尤其有用。
//如需创建工作链，您可以使用 WorkManager.beginWith(OneTimeWorkRequest) 或 WorkManager.beginWith(List<OneTimeWorkRequest>)，这会返回 WorkContinuation 实例。
//然后，可以使用 WorkContinuation 通过 then(OneTimeWorkRequest) 或 then(List<OneTimeWorkRequest>) 添加 OneTimeWorkRequest 依赖实例。 .
//每次调用 WorkContinuation.then(...) 都会返回一个新的 WorkContinuation 实例。如果添加了 OneTimeWorkRequest 实例的 List，这些请求可能会并行运行。
//最后，您可以使用 WorkContinuation.enqueue() 方法对 WorkContinuation 工作链执行 enqueue() 操作。
//下面我们来看一个示例。在本例中，有 3 个不同的工作器作业配置为运行（可能并行运行）。然后这些工作器的结果将联接起来，并传递给正在缓存的工作器作业。最后，该作业的输出将传递到上传工作器，由上传工作器将结果上传到远程服务器。
//KOTLIN
//JAVA
//WorkManager.getInstance(myContext)
//   // Candidates to run in parallel
//   .beginWith(Arrays.asList(plantName1, plantName2, plantName3))
//   // Dependent work (only runs after all previous work in chain)
//   .then(cache)
//   .then(upload)
//   // Call enqueue to kick things off
//   .enqueue();
//输入合并器
//当您链接 OneTimeWorkRequest 实例时，父级工作请求的输出将作为子级的输入传入。因此，在上面的示例中，plantName1、plantName2 和 plantName3 的输出将作为 cache 请求的输入传入。
//为了管理来自多个父级工作请求的输入，WorkManager 使用 InputMerger。
//WorkManager 提供两种不同类型的 InputMerger：
//OverwritingInputMerger 会尝试将所有输入中的所有键添加到输出中。如果发生冲突，它会覆盖先前设置的键。
//ArrayCreatingInputMerger 会尝试合并输入，并在必要时创建数组。
//如果您有更具体的用例，则可以创建 InputMerger 的子类来编写自己的用例。
//OverwritingInputMerger
//OverwritingInputMerger 是默认的合并方法。如果合并过程中存在键冲突，键的最新值将覆盖生成的输出数据中的所有先前版本。
//例如，如果每种植物的输入都有一个与其各自变量名称（"plantName1"、"plantName2" 和 "plantName3"）匹配的键，传递给 cache 工作器的数据将具有三个键值对。
//由于工作请求是并行运行的，因此无法保证其运行顺序。在上面的示例中，plantName1 可以保留值 "tulip" 或 "elm"，具体取决于最后写入的是哪个值。如果有可能存在键冲突，并且您需要在合并器中保留所有输出数据，那么 ArrayCreatingInputMerger 可能是更好的选择。
//ArrayCreatingInputMerger
//对于上面的示例，假设我们要保留所有植物名称工作器的输出，则应使用 ArrayCreatingInputMerger。
//KOTLIN
//JAVA
//OneTimeWorkRequest cache = new OneTimeWorkRequest.Builder(PlantWorker.class)
//       .setInputMerger(ArrayCreatingInputMerger.class)
//       .setConstraints(constraints)
//       .build();
//ArrayCreatingInputMerger 将每个键与数组配对。如果每个键都是唯一的，您会得到一系列一元数组。
//链接和工作状态
//只要工作成功完成（即，返回 Result.success()），OneTimeWorkRequest 链便会按顺序执行。运行时，工作请求可能会失败或被取消，这会对依存工作请求产生下游影响。
//当第一个 OneTimeWorkRequest 被加入工作请求链队列时，所有后续工作请求会被屏蔽，直到第一个工作请求的工作完成为止。
//在加入队列且满足所有工作约束后，第一个工作请求开始运行。如果工作在根 OneTimeWorkRequest 或 List<OneTimeWorkRequest> 中成功完成（即返回 Result.success()），系统会将下一组依存工作请求加入队列。
//如果该重试政策未定义或已用尽，或者您以其他方式已达到 OneTimeWorkRequest 返回 Result.failure() 的某种状态，该工作请求和所有依存工作请求都会被标记为 FAILED.
//请注意，如果要向已失败或已取消工作请求的链附加更多工作请求，新附加的工作请求也会分别标记为 FAILED 或 CANCELLED。如果您想扩展现有链的工作，请参阅 ExistingWorkPolicy 中的 APPEND_OR_REPLACE。
//创建工作请求链时，依存工作请求应定义重试政策，以确保始终及时完成工作。失败的工作请求可能会导致链不完整和/或出现意外状态。
//测试 Worker 实现
//WorkManager 提供了用于测试 Worker、ListenableWorker 和 ListenableWorker 变体（CoroutineWorker 和 RxWorker）的 API。
//自定义 WorkManager 配置和初始化
//默认情况下，当您的应用启动时，WorkManager 使用适合大多数应用的合理选项自动进行配置。如果您需要进一步控制 WorkManager 管理和调度工作的方式，可以通过自行初始化 WorkManager 来自定义 WorkManager 配置。
//为 WorkManager 提供自定义初始化的最灵活方式是使用 WorkManager 2.1.0 及更高版本中提供的按需初始化。其他选项稍后讨论。
//按需初始化
//通过按需初始化，您可以仅在需要 WorkManager 时创建该组件，而不必每次应用启动时都创建。这样做可将 WorkManager 从关键启动路径中移出，从而提高应用启动性能。如需使用按需初始化，请执行以下操作：
//移除默认初始化程序
//如需提供自己的配置，必须先移除默认初始化程序。为此，请使用合并规则 tools:node="remove" 更新 AndroidManifest.xml：
//<provider
//    android:name="androidx.work.impl.WorkManagerInitializer"
//    android:authorities="${applicationId}.workmanager-init"
//    tools:node="remove" />
//如需详细了解如何在清单中使用合并规则，请参阅有关合并多个清单文件的文档。
//实现 Configuration.Provider
//让您的 Application 类实现 Configuration.Provider 接口，并提供您自己的 Configuration.Provider.getWorkManagerConfiguration() 实现。当您需要使用 WorkManager 时，请务必调用方法 WorkManager.getInstance(Context)。WorkManager 会调用应用的自定义 getWorkManagerConfiguration() 方法来发现其 Configuration。（您无需自行调用 WorkManager.initialize()。）
//注意：如果您在初始化 WorkManager 之前调用已弃用的无参数 WorkManager.getInstance() 方法，该方法将抛出异常。即使您不自定义 WorkManager，您也应始终使用 WorkManager.getInstance(Context) 方法。
//以下示例展示了自定义 getWorkManagerConfiguration() 实现：
//KOTLIN
//JAVA
//class MyApplication extends Application implements Configuration.Provider {
//    @Override
//    public Configuration getWorkManagerConfiguration() {
//        return Configuration.Builder()
//                .setMinimumLoggingLevel(android.util.Log.INFO)
//                .build();
//    }
//}
//注意： 您必须移除默认的初始化程序，自定义的 getWorkManagerConfiguration() 实现才能生效。
//WorkManager 2.1.0 之前版本的自定义初始化
//对于 WorkManager 2.1.0 之前的版本，有两个初始化选项。在大多数情况下，默认初始化就足够了。如需更精确地控制 WorkManager，可以指定您自己的配置。
//默认初始化
//在您的应用启动时，WorkManager 会使用自定义 ContentProvider 自行初始化。此代码位于内部类 androidx.work.impl.WorkManagerInitializer 中，并使用默认 Configuration。自动使用默认初始化程序（除非明确停用它）。默认初始化程序适合大多数应用。
//自定义初始化
//如果您想控制初始化过程，就必须停用默认初始化程序，然后定义您自己的自定义配置。
//移除默认初始化程序后，可以手动初始化 WorkManager：
//KOTLIN
//JAVA
//// provide custom configuration
//Configuration myConfig = new Configuration.Builder()
//    .setMinimumLoggingLevel(android.util.Log.INFO)
//    .build();
////initialize WorkManager
//WorkManager.initialize(this, myConfig);
//确保 WorkManager 单例的初始化是在 Application.onCreate() 或 ContentProvider.onCreate() 中运行。
//如需查看可用自定义的完整列表，请参阅 Configuration.Builder() 参考文档。
//WorkManager 中的线程处理
//在 WorkManager 使用入门中，我们提到 WorkManager 可以代表您异步执行后台工作。该基本实现可满足大多数应用的需求。关于更高级的用例（例如正确处理正在停止的工作），您应了解 WorkManager 中的线程处理和并发机制。
//WorkManager 提供了四种不同类型的工作基元：
//Worker 是最简单的实现，我们已在前面几节进行了介绍。WorkManager 会在后台线程中自动运行该基元（您可以将它替换掉）。请参阅工作器中的线程处理，详细了解 Worker 实例中的线程处理。
//CoroutineWorker 是为 Kotlin 用户建议的实现。CoroutineWorker 实例公开了后台工作的一个挂起函数。默认情况下，这些实例运行默认的 Dispatcher，但您可以进行自定义。请参阅 CoroutineWorker 中的线程处理，详细了解 CoroutineWorker 实例中的线程处理。
//RxWorker 是为 RxJava 用户建议的实现。如果您有很多现有异步代码是用 RxJava 建模的，则应使用 RxWorker。与所有 RxJava 概念一样，您可以自由选择所需的线程处理策略。请参阅 RxWorker 中的线程处理，详细了解 RxWorker 实例中的线程处理。
//ListenableWorker 是 Worker、CoroutineWorker 和 RxWorker 的基类。这个类专为需要与基于回调的异步 API（例如 FusedLocationProviderClient）进行交互并且不使用 RxJava 的 Java 开发者而设计。请参阅 ListenableWorker 中的线程处理，详细了解 ListenableWorker 实例中的线程处理。
//工作器中的线程处理
//当您使用 Worker 时，WorkManager 会自动在后台线程中调用 Worker.doWork()。该后台线程来自于 WorkManager 的 Configuration 中指定的 Executor。默认情况下，WorkManager 会为您设置 Executor，但您也可以自己进行自定义。例如，您可以在应用中共享现有的后台 Executor，也可以创建单线程 Executor 以确保所有后台工作都按顺序执行，甚至可以指定一个自定义 Executor。如需自定义 Executor，请确保手动初始化 WorkManager。
//在手动配置 WorkManager 时，您可以按以下方式指定 Executor：
//KOTLIN
//JAVA
//WorkManager.initialize(
//    context,
//    new Configuration.Builder()
//        .setExecutor(Executors.newFixedThreadPool(8))
//        .build());
//下面是一个简单的 Worker 示例，其会下载网页内容 100 次：
//KOTLIN
//JAVA
//public class DownloadWorker extends Worker {
//    public DownloadWorker(Context context, WorkerParameters params) {
//        super(context, params);
//    }
//    @NonNull
//    @Override
//    public Result doWork() {
//        for (int i = 0; i < 100; i++) {
//            try {
//                downloadSynchronously("https://www.google.com");
//            } catch (IOException e) {
//                return Result.failure();
//            }
//        }
//        return Result.success();
//    }
//}
//请注意，Worker.doWork() 是同步调用 - 您应以阻塞方式完成整个后台工作，并在方法退出时完成工作。如果您在 doWork() 中调用异步 API 并返回 Result，那么回调可能无法正常运行。如果您遇到这种情况，请考虑使用 ListenableWorker（请参阅在 ListenableWorker 中进行线程处理）。
//如果当前正在运行的 Worker 因任何原因而停止，它就会收到对 Worker.onStopped() 的调用。在必要的情况下，只需替换此方法或调用 Worker.isStopped()，即可对代码进行检查点处理并释放资源。当上述示例中的 Worker 被停止时，内容的下载可能才完成了一半；但即使该工作器被停止，下载也会继续。如需优化此行为，您可以执行以下操作：
//KOTLIN
//JAVA
//public class DownloadWorker extends Worker {
//    public DownloadWorker(Context context, WorkerParameters params) {
//        super(context, params);
//    }
//    @NonNull
//    @Override
//    public Result doWork() {
//        for (int i = 0; i < 100; ++i) {
//            if (isStopped()) {
//                break;
//            }
//            try {
//                downloadSynchronously("https://www.google.com");
//            } catch (IOException e) {
//                return Result.failure();
//            }
//        }
//        return Result.success();
//    }
//}
//Worker 停止后，从 Worker.doWork() 返回什么已不重要；Result 将被忽略。
//CoroutineWorker 中的线程处理
//对于 Kotlin 用户，WorkManager 为协程提供了一流的支持。如要开始使用，请将 work-runtime-ktx 包含到您的 gradle 文件中。不要扩展 Worker，而应扩展 CoroutineWorker，后者包含 doWork() 的挂起版本。例如，如果要构建简单的 CoroutineWorker 来执行某些网络操作，您需要执行以下操作：
//class CoroutineDownloadWorker(
//    context: Context,
//    params: WorkerParameters
//) : CoroutineWorker(context, params) {
//    override suspend fun doWork(): Result = {
//        val data = downloadSynchronously("https://www.google.com")
//        saveData(data)
//        Result.success()
//    }
//}
//请注意，CoroutineWorker.doWork() 是一个“挂起”函数。此代码不同于 Worker，不会在 Configuration 中指定的 Executor 中运行，而是默认为 Dispatchers.Default。您可以提供自己的 CoroutineContext 来自定义这个行为。在上面的示例中，您可能希望在 Dispatchers.IO 上完成此操作，如下所示：
//class CoroutineDownloadWorker(
//    context: Context,
//    params: WorkerParameters
//) : CoroutineWorker(context, params) {
//    override suspend fun doWork(): Result {
//        withContext(Dispatchers.IO) {
//            val data = downloadSynchronously("https://www.google.com")
//            saveData(data)
//            return Result.success()
//        }
//    }
//}
//CoroutineWorker 通过取消协程并传播取消信号来自动处理停工情况。您无需执行任何特殊操作来处理停工情况。
//RxWorker 中的线程处理
//我们在 WorkManager 与 RxJava 之间提供互操作性。如需开始使用这种互操作性，除了在您的 gradle 文件中包含 work-runtime 之外，还应包含 work-rxjava3 依赖项。而且还有一个支持 rxjava2 的 work-rxjava2 依赖项，您可以根据情况使用。
//然后，您应该扩展 RxWorker，而不是扩展 Worker。最后替换 RxWorker.createWork() 方法以返回 Single<Result>，用于表示代码执行的 Result，如下所示：
//KOTLIN
//JAVA
//public class RxDownloadWorker extends RxWorker {
//    public RxDownloadWorker(Context context, WorkerParameters params) {
//        super(context, params);
//    }
//    @NonNull
//    @Override
//    public Single<Result> createWork() {
//        return Observable.range(0, 100)
//            .flatMap { download("https://www.example.com") }
//            .toList()
//            .map { Result.success() };
//    }
//}
//请注意，RxWorker.createWork() 在主线程上调用，但默认情况下会在后台线程上订阅返回值。您可以替换 RxWorker.getBackgroundScheduler() 来更改订阅线程。
//当 RxWorker 为 onStopped() 时，系统会处理订阅，因此您无需以任何特殊方式处理停工情况。
//ListenableWorker 中的线程处理
//在某些情况下，您可能需要提供自定义线程处理策略。例如，您可能需要处理基于回调的异步操作。在这种情况下，不能只依靠 Worker 来完成操作，因为它无法以阻塞方式完成这项工作。WorkManager 通过 ListenableWorker 支持该用例。ListenableWorker 是最基本的工作器 API；Worker、CoroutineWorker 和 RxWorker 都是从这个类衍生而来的。ListenableWorker 只会发出信号以表明应该开始和停止工作，而线程处理则完全交您决定。开始工作信号在主线程上调用，因此请务必手动转到您选择的后台线程。
//抽象方法 ListenableWorker.startWork() 会返回一个将使用操作的 Result 设置的 ListenableFuture。ListenableFuture 是一个轻量级接口：它是一个 Future，用于提供附加监听器和传播异常的功能。在 startWork 方法中，应该返回 ListenableFuture，完成操作后，您需要使用操作的 Result 设置这个返回结果。您可以通过以下两种方式之一创建 ListenableFuture 实例：
//如果您使用的是 Guava，请使用 ListeningExecutorService。
//否则，请将 councurrent-futures 包含到您的 gradle 文件中并使用 CallbackToFutureAdapter。
//如果您希望基于异步回调执行某些工作，则应以类似如下的方式执行：
//KOTLIN
//JAVA
//public class CallbackWorker extends ListenableWorker {
//    public CallbackWorker(Context context, WorkerParameters params) {
//        super(context, params);
//    }
//    @NonNull
//    @Override
//    public ListenableFuture<Result> startWork() {
//        return CallbackToFutureAdapter.getFuture(completer -> {
//            Callback callback = new Callback() {
//                int successes = 0;
//                @Override
//                public void onFailure(Call call, IOException e) {
//                    completer.setException(e);
//                }
//                @Override
//                public void onResponse(Call call, Response response) {
//                    successes++;
//                    if (successes == 100) {
//                        completer.set(Result.success());
//                    }
//                }
//            };
//            for (int i = 0; i < 100; i++) {
//                downloadAsynchronously("https://www.example.com", callback);
//            }
//            return callback;
//        });
//    }
//}

