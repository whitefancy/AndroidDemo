package pub.whitefancy.androiddemo;

public class AlarmManagerUsage {
}
//AlarmManager
//a此类提供对系统警报服务的访问。这些使您可以计划您的应用程序在将来的某个时间运行。警报响起时，Intent系统会广播已为其注册的警报，如果尚未运行目标应用程序，则会自动启动该应用程序。设备处于睡眠状态时会保留已注册的警报（如果警报在这段时间内关闭，可以选择将其唤醒），但是如果已关闭并重新启动，则警报将被清除。
//只要警报接收器的onReceive（）方法正在执行，警报管理器就会保持CPU唤醒锁。这样可以确保手机在完成广播处理之前不会进入睡眠状态。一旦onReceive（）返回，警报管理器将释放此唤醒锁。这意味着在某些情况下，一旦您的onReceive（）方法完成，电话就会进入休眠状态。如果您的警报接收器叫Context.startService()，则电话可能会在启动请求的服务之前进入睡眠状态。为避免这种情况，您的BroadcastReceiver和服务将需要实施单独的唤醒锁定策略，以确保电话继续运行，直到服务可用为止。
//注意：警报管理器适用于您希望在特定时间运行应用程序代码的情况，即使您的应用程序当前未运行。对于正常的计时操作（滴答声，超时等），它使用起来更容易且效率更高 Handler。
//注意：从API 19（Build.VERSION_CODES.KITKAT）开始，警报传递是不准确的：操作系统将转移警报，以最大程度地减少唤醒和电池消耗。有一些新的API支持需要严格交付保证的应用程序。看到 setWindow(int, long, long, android.app.PendingIntent)和 setExact(int, long, android.app.PendingIntent)。targetSdkVersion 早于API 19的应用程序将继续看到以前的行为，在该行为中，所有警报均在被请求时准确地传递。
//public void cancel (PendingIntent operation)
//删除所有与匹配的警报Intent。任何意图与该警报相匹配（由定义Intent#filterEquals）的任何类型的警报 都将被取消。
//设置重复闹铃时间
//s闹钟（基于 AlarmManager 类）为您提供了一种在应用生命周期之外定时执行操作的方式。例如，您可以使用闹钟启动长期运行的操作，例如每天启动一次某项服务以下载天气预报。
//闹钟具有以下特征：
//它们可让您按设定的时间和/或间隔触发 intent。
//您可以将它们与广播接收器结合使用，以启动服务以及执行其他操作。
//它们在应用外部运行，因此即使应用未运行，或设备本身处于休眠状态，您也可以使用它们来触发事件或操作。
//它们可以帮助您最大限度地降低应用的资源要求。您可以安排定期执行操作，而无需依赖定时器或持续运行后台服务。
//注意：对于肯定会在应用生命周期内发生的定时操作，请考虑将 Handler 类与 Timer 和 Thread 结合使用。该方法可让 Android 更好地控制系统资源。
//了解利弊
//重复闹钟是一种相对简单的机制，灵活性有限。对于您的应用而言，它可能并不是理想的选择，尤其是当您需要触发网络操作时。设计不合理的闹钟会导致耗电过度，并会使服务器负载显著增加。
//在应用生命周期之外触发操作的一种常见情形是与服务器同步数据。在这种情况下，您可能会想要使用重复闹钟。但是，如果您拥有托管应用数据的服务器，那么与 AlarmManager 相比，结合使用 Google 云消息传递 (GCM) 与同步适配器是更好的解决方案。同步适配器可为您提供与 AlarmManager 完全相同的时间安排选项，但灵活性要高得多。例如，同步操作可以基于来自服务器/设备的“新数据”消息（如需了解详情，请参阅运行同步适配器）、用户的活动状态（或非活动状态）、一天当中的时段等等。有关何时以及如何使用 GCM 和同步适配器的详细讨论，请参阅此页面顶部链接的视频。
//当设备在低电耗模式下处于空闲状态时，不会触发闹钟。所有已设置的闹钟都会推迟，直到设备退出低电耗模式。如果您需要确保即使设备处于空闲状态您的工作也会完成，可以通过多种选项实现这一目的。您可以使用 setAndAllowWhileIdle() 或 setExactAndAllowWhileIdle() 确保闹钟会执行。另一个选项是使用新的 WorkManager API，它可用于执行一次性或周期性的后台工作。如需了解详情，请参阅使用 WorkManager 安排任务。
//最佳做法
//您在设计重复闹钟时所做的每一个选择都会对应用使用（或滥用）系统资源的方式产生影响。例如，假设有一个热门应用会与服务器同步。如果同步操作基于时钟时间，并且应用的每个实例都会在晚上 11:00 进行同步，那么服务器上的负载可能会导致高延迟，甚至是“拒绝服务”。请遵循以下使用闹钟的最佳做法：
//为重复闹钟触发的网络请求加入一些随机性（抖动）：
//在闹钟触发时执行本地工作。“本地工作”是指无需连接至服务器或使用来自服务器的数据的任何工作。
//同时，将包含网络请求的闹钟设置为在某个随机时间段内触发。
//尽可能降低闹钟的触发频率。
//请勿在不必要的情况下唤醒设备（该行为取决于闹钟类型，如选择闹钟类型中所述）。
//请勿将闹钟的触发时间设置得过于精确。
//使用 setInexactRepeating() 代替 setRepeating()。当您使用 setInexactRepeating() 时，Android 会同步来自多个应用的重复闹钟，并同时触发它们。这可以减少系统必须唤醒设备的总次数，从而减少耗电量。从 Android 4.4（API 级别 19）开始，所有重复闹钟都是不精确的。请注意，尽管 setInexactRepeating() 是对 setRepeating() 的改进，但如果应用的每个实例都大约在同一时间连接至服务器，仍会使服务器不堪重负。因此，如上所述，对于网络请求，请为您的闹钟添加一些随机性。
//尽量避免基于时钟时间设置闹钟。.
//基于精确触发时间的重复闹钟无法很好地扩展。如果可以的话，请使用 ELAPSED_REALTIME。下一部分详细介绍了不同的闹钟类型。
//选择闹钟类型
//使用重复闹钟时的首要考虑因素之一是，它应该是哪种类型。
//闹钟有两种常规时钟类型：“经过的时间”和“实时时钟”(RTC)。前者使用“自系统启动以来的时间”作为参考，而后者则使用世界协调时间 (UTC)（挂钟时间）。这意味着“经过的时间”类型适合用于设置基于时间流逝情况的闹钟（例如，每 30 秒触发一次的闹钟），因为它不受时区/语言区域的影响。“实时时钟”类型更适合依赖当前语言区域的闹钟。
//两种类型都有一个“唤醒”版本，该版本会在屏幕处于关闭状态时唤醒设备的 CPU。这可以确保闹钟在预定的时间触发。如果您的应用具有时间依赖项（例如，如果它需要在限定时间内执行特定操作），这会非常有用。如果您未使用闹钟类型的唤醒版本，则在您的设备下次处于唤醒状态时，所有重复闹钟都会触发。
//如果您只需要让闹钟以特定的时间间隔（例如每半小时）触发，请使用某个“经过的时间”类型。一般而言，它是更好的选择。
//如果您需要让闹钟在一天中的某个特定时段触发，请选择基于时钟的实时时钟类型之一。但是请注意，这种方法有一些弊端，即应用可能无法很好地转换到其他语言区域，并且如果用户更改设备的时间设置，可能会导致应用出现意外行为。如上所述，使用实时时钟闹钟类型也无法很好地扩展。如果可以的话，我们建议您使用“经过的时间”闹钟。
//以下为类型列表：
//ELAPSED_REALTIME - 基于自设备启动以来所经过的时间触发待定 intent，但不会唤醒设备。经过的时间包括设备处于休眠状态期间的任何时间。
//ELAPSED_REALTIME_WAKEUP - 唤醒设备，并在自设备启动以来特定时间过去之后触发待定 Intent。
//RTC - 在指定的时间触发待定 Intent，但不会唤醒设备。
//RTC_WAKEUP - 唤醒设备以在指定的时间触发待定 Intent。
//“经过的时间”闹钟示例
//以下是使用 ELAPSED_REALTIME_WAKEUP 的一些示例。
//在 30 分钟后唤醒设备并触发闹钟，此后每 30 分钟触发一次：
//KOTLIN
//JAVA
//    // Hopefully your alarm will have a lower frequency than this!
//    alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
//            SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_HALF_HOUR,
//            AlarmManager.INTERVAL_HALF_HOUR, alarmIntent);
//在一分钟后唤醒设备并触发一个一次性（非重复）闹钟：
//KOTLIN
//JAVA
//    private AlarmManager alarmMgr;
//    private PendingIntent alarmIntent;
//    ...
//    alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
//    Intent intent = new Intent(context, AlarmReceiver.class);
//    alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
//    alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
//            SystemClock.elapsedRealtime() +
//            60 * 1000, alarmIntent);
//    “实时时钟”闹钟示例
//以下是使用 RTC_WAKEUP 的一些示例。
//在下午 2:00 左右唤醒设备并触发闹钟，并在每天的同一时间重复一次：
//KOTLIN
//JAVA
//    // Set the alarm to start at approximately 2:00 p.m.
//    Calendar calendar = Calendar.getInstance();
//    calendar.setTimeInMillis(System.currentTimeMillis());
//    calendar.set(Calendar.HOUR_OF_DAY, 14);
//    // With setInexactRepeating(), you have to use one of the AlarmManager interval
//    // constants--in this case, AlarmManager.INTERVAL_DAY.
//    alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//            AlarmManager.INTERVAL_DAY, alarmIntent);
//在上午 8:30 准时唤醒设备并触发闹钟，此后每 20 分钟触发一次：
//KOTLIN
//JAVA
//    private AlarmManager alarmMgr;
//    private PendingIntent alarmIntent;
//    ...
//    alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
//    Intent intent = new Intent(context, AlarmReceiver.class);
//    alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
//    // Set the alarm to start at 8:30 a.m.
//    Calendar calendar = Calendar.getInstance();
//    calendar.setTimeInMillis(System.currentTimeMillis());
//    calendar.set(Calendar.HOUR_OF_DAY, 8);
//    calendar.set(Calendar.MINUTE, 30);
//    // setRepeating() lets you specify a precise custom interval--in this case,
//    // 20 minutes.
//    alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//            1000 * 60 * 20, alarmIntent);
//RTC_WAKEUP
//在API级别1中添加
//public static final int RTC_WAKEUP
//闹铃时间输入System.currentTimeMillis() （以UTC表示的墙上时钟时间），它将在设备关闭时唤醒设备。
//常数值：0（0x00000000）
//workmanager vs jobscheduler vs alarmmanager
//s报警管理器
//使用AlarmManager在系统级别安排任务
//AlarmManager提供对系统级警报服务的访问。使用AlarmManager可使应用程序安排可能需要在其生命周期范围之外运行或重复的任务。这样，即使在应用程序进程或其所有Android组件已由系统清理之后，应用程序也可以执行某些功能。
//通常，AlarmManager用于触发PendingIntent，该PendingIntent将在将来启动服务。AlarmManager根据经过的时间间隔或特定的时钟时间触发服务。如果警报紧急，这两个选项还可以在设备处于睡眠状态时将其唤醒。
//使用不精确的时间间隔或时间来关闭服务时，AlarmManager的好处就会发挥作用。Android系统会尝试以相似的时间间隔或时间将警报分批处理，以延长电池寿命。通过批量处理来自多个应用程序的警报，系统可以避免频繁的设备唤醒和联网。
//使用AlarmManager时要考虑的一个问题是，重启设备后会清除掉警报。应用程序需要在其Android清单中注册RECEIVE_BOOT_COMPLETE权限，并在BroadcastReceiver中重新安排其警报。
//另一个担心是，设计不当的警报可能会导致电池电量耗尽。虽然AlarmManager确实具有唤醒设备并设置警报确切时间的能力，但文档中提到开发人员在执行联网时应警惕这些功能。除了通过避免批量警报来消耗设备的电池电量外，如果每个应用程序安装都尝试与服务器同步，那么为应用程序与服务器同步设置准确的时间可能会使服务器承受更大的压力！可以通过向警报间隔或时间添加一些随机性来避免这种情况。
//如果应用程序需要在准确的时间或不精确的时间间隔执行本地事件，AlarmManager是安排计划的理想选择。闹钟或提醒应用程序是使用AlarmManager的绝佳示例。但是，本文档不鼓励使用AlarmManager计划与网络相关的任务。让我们看一下一些更好的网络选项。
//作业调度器
//JobScheduler帮助以高效的方式执行后台工作，尤其是网络。JobServices计划根据JobInfo.Builder（）中声明的条件运行。这些条件包括仅在设备正在充电，空闲，连接到网络或连接到未计量的网络时才执行JobService。JobInfo还可以包括执行JobService的最小延迟和某些截止日期。如果不满足这些条件，则作业将在系统中排队等待稍后执行。系统还将尝试以与计划警报相同的方式将这些作业分批处理，以节省建立网络连接时的电池寿命。
//开发人员可能会担心调度程序经常延迟启动其JobServices的时间。如果作业经常被延迟并且结果是数据陈旧，那么很高兴知道这些事情。JobScheduler将返回有关JobService的信息，例如重新安排或失败的信息。JobScheduler具有用于处理这些方案的退避和重试逻辑，或者开发人员可以自行处理这些方案。
//子类化JobService需要重写其onStartJob（JobParams params）和onStopJob（JobParams params）方法。onStartJob（）是放置作业的回调逻辑的位置，它在主线程上运行。开发人员负责处理长期运行的作业。如果需要进行单独的线程处理，则将true返回到onStartJob（）；如果可以在主线程上进行处理，并且无需为此工作做更多的工作，则返回false。当作业完成时，开发人员还必须调用jobFinished（JobParameters params，boolean needsReschedule），并确定是否要重新安排更多的作业。当不再符合初始JobInfo参数时（例如，用户需要拔出其设备的电源时），onStopJob（）将被调用以停止或清理任务。
//实施JobService时可能要考虑很多事情，但是它比AlarmManager具有更大的灵活性。另一个方便的功能是，计划的作业会在系统重新引导后继续存在。
//使用JobScheduler至少有一个缺点。在撰写本文时，它仅与21级及更高版本的API兼容。在这里，您可以找到运行各种API级别的Android设备的分布。从技术上讲，JobScheduler没有后台支持，但是类似的工具是GCM Network Manager。
//现在，推荐的后台处理方法是Jetpack WorkManager API。由于以下原因，我将引用官方文档：
//WorkManager根据设备API级别和应用程序状态等因素选择合适的方式来运行任务。如果WorkManager在应用程序运行时执行您的任务之一，则WorkManager可以在应用程序进程中的新线程中运行任务。如果您的应用未运行，WorkManager将选择适当的方式来调度后台任务-根据设备API级别和所包含的依赖关系，WorkManager可能会使用JobScheduler，Firebase JobDispatcher或AlarmManager。您无需编写设备逻辑即可弄清楚设备具有哪些功能并选择合适的API。相反，您可以将任务交给WorkManager，然后让它选择最佳选项。
//此外，WorkManager还提供了一些高级功能。例如，您可以设置一系列任务；当一个任务完成时，WorkManager会将链中的下一个任务排入队列。您还可以通过观察其LiveData来检查任务的状态及其返回值。如果您要显示指示任务状态的UI，这将很有用。
//因此，您不必担心每次选择哪种后台处理（因为每个任务都有推荐和适当的方式），您只需使用WorkManager即可完成工作。
//这是在考虑以下陷阱：
//WorkManager适用于需要保证即使应用程序退出时系统也将运行它们的任务，例如将应用程序数据上传到服务器。它不适用于进行中的后台工作，如果应用程序进程消失，可以安全地终止该后台工作；对于这种情况，我们建议使用ThreadPools。
//PS由于WorkManager API在后台使用JobScheduler，Firebase JobDistpacher或AlarmManager，因此您必须考虑已使用功能的最低API级别。JobScheduler至少需要API 21，Firebase JobDispatcher至少需要API 14和Google Play服务。
//有关完整的文档，请检查：https : //developer.android.com/topic/libraries/architecture/workmanager
//关于第二个问题：据我所知，您将始终看到该通知，因为它通知用户您的应用正在消耗电池。用户可以通过Android Oreo 8.1中的设置禁用该通知。
//（最终）WorkManager可能是您正在寻找的解决方案。它充当一种抽象，决定是使用JobScheduler（如果可用），Firebase JobDispatcher（如果可用），还是使用默认实现。这样，您将获得世界上最好的。但是，它仍然处于Alpha状态，因此您可能至少要考虑其他选项。
//如果您选择不使用WorkManager，则JobScheduler和JobDispatcher的组合可能是合适的（请参阅此处）。
//但是，如果您定位的设备没有API 22以下的Google Play服务，则需要使用其他解决方案。在那种情况下，AlarmManager可能就是您想要的，因为您需要一个延迟的任务并保证执行。为此可以使用IntentService，但并非那么容易。它涉及引入某种延迟机制，其中有几种选择。
//请注意，如果您使用作业API或WorkManager之一，则使用批处理机制，因此在Oreo中不会看到通知。基于AlarmManager / IntentService的解决方案可能会显示通知，但可能不会显示很长时间，因为任务非常短。对于AlarmManager尤其如此。
//您还应该注意，它WorkManager是androidx的一部分，而是JobScheduler旧的支持库的一部分
//首先，WorkManager用于可延期并需要保证执行的工作。考虑到向后兼容性，JobScheduler仅适用于API 23+。为避免必须进行向后兼容，WorkManager会为您做到这一点：-
//工作经理的特征
//保证约束约束的执行
//尊重系统背景限制
//可确认，您可以检查状态，例如失败，成功等
//可链接，例如，工作-A取决于工作-B->工作图
//机会十足，请在遇到限制时尽力执行，而实际上不需要作业调度程序进行干预，即唤醒应用程序或等待JobSheduler进程启动并运行时分批处理您的工作
//向后兼容，带有或不带有Google Play服务
//WorkManager提供了回到API级别14的兼容性。WorkManager根据设备API级别选择适当的方式来调度后台任务。它可能使用JobScheduler（在API 23及更高版本上）或AlarmManager和BroadcastReceiver的组合
//16
//WorkManager使用JobScheduler服务来安排作业。如果JobScheduler设备不支持，则它将使用FirebaseJobDispatcher服务。如果FirebaseJobDispatcher在设备上不可用，它将使用AlarmManager和BroadcastReceiver。
//因此，有了WorkManager，您无需担心向后兼容性。除此之外，它还允许定义一些约束条件，例如，定义网络约束条件，电池电量，充电状态和存储电量，这些约束条件才能使作业运行。
//它允许任务链和参数传递给工作。
//Workmanager内部使用基于Android API级别的AlarmManger，Jobscheduler或Firebase Job Dispatcher，因此我认为Jobscheduler不会被弃用。
