package pub.whitefancy.androiddemo;

public class JetpackUsage {
}
//Android Jetpack
//Jetpack 包含一系列 Android 库，它们都采用最佳做法并在 Android 应用中提供向后兼容性。
//Jetpack 应用架构指南概述了构建 Android 应用时要考虑的最佳做法和推荐架构。
//利用 Jetpack
//Jetpack 库可以单独使用，也可以组合使用，以满足应用的不同需求。
//WorkManager - 满足您的后台调度需求。
//Room - 实现数据存储持久性。
//Navigation - 管理应用导航流程。
//CameraX - 满足相机应用需求。
//请参阅所有 Jetpack 库的概览。
//Jetpack 库在 androidx 命名空间中发布。如果项目目前使用 Android 支持库，请阅读如何迁移到 androidx 命名空间。
//应用架构指南
//本指南包含一些最佳做法和推荐架构，有助于构建强大而优质的应用。
//如需详细了解如何使用 Jetpack，请查看以下页面：
//移动应用用户体验
//s在大多数情况下，桌面应用将桌面或程序启动器当做单个入口点，然后作为单个整体流程运行。Android 应用则不然，它们的结构要复杂得多。典型的 Android 应用包含多个应用组件，包括 Activity、Fragment、Service、内容提供程序和广播接收器。
//您需要在应用清单中声明其中的大多数应用组件。Android 操作系统随后会使用此文件来决定如何将您的应用集成到设备的整体用户体验中。鉴于正确编写的 Android 应用包含多个组件，并且用户经常会在短时间内与多个应用进行互动，因此应用需要适应不同类型的用户驱动型工作流和任务。
//例如，思考一下当您在自己喜欢的社交网络应用中分享照片时会发生什么：
//该应用将触发相机 intent。Android 操作系统随后会启动相机应用来处理请求。此时，用户已离开社交网络应用，但他们的体验仍然是无缝的。
//相机应用可能会触发其他 intent（如启动文件选择器），而这可能会再启动一个应用。
//最后，用户返回社交网络应用并分享照片。
//在此过程中，用户随时可能会被电话或通知打断。处理之后，用户希望能够返回并继续分享照片。这种应用跳跃行为在移动设备上很常见，因此您的应用必须正确处理这些流程。
//请注意，移动设备的资源也很有限，因此操作系统可能会随时终止某些应用进程，以便为新的进程腾出空间。
//鉴于这种环境条件，您的应用组件可以不按顺序地单独启动，并且操作系统或用户可以随时销毁它们。由于这些事件不受您的控制，因此您不应在应用组件中存储任何应用数据或状态，并且应用组件不应相互依赖。
//常见的架构原则
//a如果您不应使用应用组件存储应用数据和状态，那么您应该如何设计应用呢？
//分离关注点
//要遵循的最重要的原则是分离关注点。一种常见的错误是在一个 Activity 或 Fragment 中编写所有代码。这些基于界面的类应仅包含处理界面和操作系统交互的逻辑。您应使这些类尽可能保持精简，这样可以避免许多与生命周期相关的问题。
//请注意，您并非拥有 Activity 和 Fragment 的实现；它们只是表示 Android 操作系统与应用之间关系的粘合类。操作系统可能会根据用户互动或因内存不足等系统条件随时销毁它们。为了提供令人满意的用户体验和更易于管理的应用维护体验，您最好尽量减少对它们的依赖。
//通过模型驱动界面
//另一个重要原则是您应该通过模型驱动界面（最好是持久性模型）。模型是负责处理应用数据的组件。它们独立于应用中的 View 对象和应用组件，因此不受应用的生命周期以及相关的关注点的影响。
//持久性是理想之选，原因如下：
//如果 Android 操作系统销毁应用以释放资源，用户不会丢失数据。
//当网络连接不稳定或不可用时，应用会继续工作。
//应用所基于的模型类应明确定义数据管理职责，这样将使应用更可测试且更一致。

//推荐应用架构
//请注意，每个组件仅依赖于其下一级的组件。例如，Activity 和 Fragment 仅依赖于视图模型。存储区是唯一依赖于其他多个类的类；在本例中，存储区依赖于持久性数据模型和远程后端数据源。
//这种设计打造了一致且愉快的用户体验。无论用户上次使用应用是在几分钟前还是几天之前，现在回到应用时都会立即看到应用在本地保留的用户信息。如果此数据已过时，则应用的存储区模块将开始在后台更新数据。
//构建界面
//界面由 Fragment UserProfileFragment 及其对应的布局文件 user_profile_layout.xml 组成。
//如需驱动该界面，数据模型需要存储以下数据元素：
//用户 ID：用户的标识符。最好使用 Fragment 参数将此类信息传递到相应 Fragment 中。如果 Android 操作系统销毁我们的进程，此类信息将保留，以便下次重启应用时 ID 可用。
//用户对象：用于存储用户详细信息的数据类。
//我们将使用 UserProfileViewModel（基于 ViewModel 架构组件）存储这些信息。
//ViewModel 对象为特定的界面组件（如 Fragment 或 Activity）提供数据，并包含数据处理业务逻辑，以与模型进行通信。例如，ViewModel 可以调用其他组件来加载数据，还可以转发用户请求来修改数据。ViewModel 不了解界面组件，因此不受配置更改（如在旋转设备时重新创建 Activity）的影响。
//我们现在定义了以下文件：
//user_profile.xml：屏幕的界面布局定义。
//UserProfileFragment：显示数据的界面控制器。
//UserProfileViewModel：准备数据以便在 UserProfileFragment 中查看并对用户互动做出响应的类。
//以下代码段显示了这些文件的起始内容（为简单起见，省略了布局文件）。
//获取用户对象后，我们需要通知 Fragment。这就是 LiveData 架构组件的用武之地。
//LiveData 是一种可观察的数据存储器。应用中的其他组件可以使用此存储器监控对象的更改，而无需在它们之间创建明确且严格的依赖路径。LiveData 组件还遵循应用组件（如 Activity、Fragment 和 Service）的生命周期状态，并包括清理逻辑以防止对象泄漏和过多的内存消耗。
//注意：如果您已在使用 RxJava 之类的库，则可以继续使用它们，而不是 LiveData。不过，在使用此类库和方法时，请确保正确处理应用的生命周期。特别是，确保在相关的 LifecycleOwner 停止时暂停数据流，并在相关的 LifecycleOwner 销毁时销毁这些数据流。您还可以添加 android.arch.lifecycle:reactivestreams 工件，以将 LiveData 与其他响应流库（如 RxJava2）一起使用。
//为了将 LiveData 组件纳入应用，我们将 UserProfileViewModel 中的字段类型更改为 LiveData<User>。现在，更新数据时，系统会通知 UserProfileFragment。此外，由于此 LiveData 字段具有生命周期感知能力，因此当不再需要引用时，会自动清理它们。
//现在，我们修改 UserProfileFragment 以观察数据并更新界面：
//UserProfileFragment
//override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//   super.onViewCreated(view, savedInstanceState)
//   viewModel.user.observe(viewLifecycleOwner) {
//       // update UI
//   }
//}
//每次更新用户个人资料数据时，系统都会调用 onChanged() 回调并刷新界面。
//如果您熟悉使用可观察回调的其他库，那么您可能已经意识到，我们并未替换 Fragment 的 onStop() 方法以停止观察数据。使用 LiveData 时没必要执行此步骤，因为它具有生命周期感知能力。这意味着，除非 Fragment 处于活跃状态（即，已接收 onStart() 但尚未接收 onStop()），否则它不会调用 onChanged() 回调。当调用 Fragment 的 onDestroy() 方法时，LiveData 还会自动移除观察者。
//此外，我们也没有添加任何逻辑来处理配置更改（例如，用户旋转设备的屏幕）。UserProfileViewModel 会在配置更改后自动恢复，所以一旦创建新的 Fragment，它就会接收相同的 ViewModel 实例，并且会立即使用当前的数据调用回调。鉴于 ViewModel 对象应该比它们更新的相应 View 对象存在的时间更长，因此 ViewModel 实现中不得包含对 View 对象的直接引用。如需详细了解与界面组件生命周期对应的 ViewModel 生命周期，请参阅 ViewModel 的生命周期。
//获取数据
//现在，我们已使用 LiveData 将 UserProfileViewModel 连接到 UserProfileFragment，那么如何获取用户个人资料数据？
//在本例中，我们假定后端提供 REST API。我们使用 Retrofit 库访问后端，不过您可以随意使用起着相同作用的其他库。
//下面是与后端进行通信的 Webservice 的定义：
//Webservice
//interface Webservice {
//   /**
//    * @GET declares an HTTP GET request
//    * @Path("user") annotation on the userId parameter marks it as a
//    * replacement for the {user} placeholder in the @GET path
//    */
//   @GET("/users/{user}")
//   suspend fun getUser(@Path("user") userId: String): User
//}
//实现 ViewModel 的第一个想法可能是直接调用 Webservice 来获取数据，然后将该数据分配给 LiveData 对象。这种设计行得通，但如果采用这种设计，随着应用的扩大，应用会变得越来越难以维护。这样会使 UserProfileViewModel 类承担太多的责任，这就违背了分离关注点原则。此外，ViewModel 的时间范围与 Activity 或 Fragment 生命周期相关联，这意味着，当关联界面对象的生命周期结束时，会丢失 Webservice 的数据，进而影响用户体验。
//ViewModel 会将数据获取过程委派给一个新的模块，即存储区。
//存储区模块会处理数据操作。它们会提供一个干净的 API，以便应用的其余部分可以轻松检索该数据。数据更新时，它们知道从何处获取数据以及进行哪些 API 调用。您可以将存储区视为不同数据源（如持久性模型、网络服务和缓存）之间的媒介。
//UserRepository 类（如以下代码段中所示）使用 WebService 实例来获取用户的数据：
//UserRepository
//class UserRepository {
//   private val webservice: Webservice = TODO()
//   // ...
//   suspend fun getUser(userId: String) =
//       // This isn't an optimal implementation because it doesn't take into
//       // account caching. We'll look at how to improve upon this in the next
//       // sections.
//       webservice.getUser(userId)
//}
//注意：对于 Java，请参阅有关在后台线程中运行 Android 任务的指南。
//虽然存储区模块看起来不必要，但它起着一项重要的作用：它会从应用的其余部分中提取数据源。现在，UserProfileViewModel 不知道如何获取数据，因此我们可以为视图模型提供从几个不同的数据获取实现获得的数据。
//管理组件之间的依赖关系
//上方的 UserRepository 类需要一个 Webservice 实例才能获取用户的数据。它可以直接创建该实例，但要做到这一点，它还需要知道 Webservice 类的依赖项。此外，UserRepository 或许不是唯一一个需要 Webservice 的类。这种情况需要我们复制代码，因为每个需要引用 Webservice 的类都需要知道如何构造该实例及其依赖项。如果每个类都创建一个新的 WebService，应用会变得非常耗费资源。
//您可以使用以下设计模式来解决此问题：
//依赖注入 (DI)：依赖注入使类能够定义其依赖项而不构造它们。在运行时，另一个类负责提供这些依赖项。
//服务定位器：服务定位器模式提供了一个注册表，类可以从中获取其依赖项而不构造它们。
//您可以借助这些模式来扩展代码，因为它们可提供清晰的依赖项管理模式（无需复制代码，也不会增添复杂性）。此外，您还可以借助这些模式在测试和生产数据获取实现之间快速切换。
//我们建议在 Android 应用中采用依赖项注入模式并使用 Hilt 库。Hilt 通过遍历依赖项树自动构造对象，为依赖项提供编译时保证，并为 Android 框架类创建依赖项容器。
//我们的示例应用使用 Hilt 管理 Webservice 对象的依赖项。
//连接 ViewModel 与存储区
//现在，我们修改 UserProfileViewModel 以使用 UserRepository 对象：
//UserProfileViewModel
//@HiltViewModel
//class UserProfileViewModel @Inject constructor(
//   savedStateHandle: SavedStateHandle,
//   userRepository: UserRepository
//) : ViewModel() {
//   val userId : String = savedStateHandle["uid"] ?:
//          throw IllegalArgumentException("missing user id")
//   private val _user = MutableLiveData<User>()
//   val user = LiveData<User> = _user
//   init {
//       viewModelScope.launch {
//           _user.value = userRepository.getUser(userId)
//       }
//   }
//}
//缓存数据
//UserRepository 实现会抽象化对 Webservice 对象的调用，但由于它只依赖于一个数据源，因此不是很灵活。
//UserRepository 实现的关键问题是，它从后端获取数据后，不会将该数据存储在任何位置。因此，如果用户在离开 UserProfileFragment 后再返回该类，则应用必须重新获取数据，即使数据未发生更改也是如此。
//这种设计不够理想，原因如下：
//浪费了宝贵的网络带宽。
//迫使用户等待新的查询完成。
//为了弥补这些缺点，我们向 UserRepository 添加了一个新的数据源，用于将 User 对象缓存在内存中：
//UserRepository
//// @Inject tells Hilt how to create instances of this type
//// and the dependencies it has.
//class UserRepository @Inject constructor(
//   private val webservice: Webservice,
//   // Simple in-memory cache. Details omitted for brevity.
//   private val userCache: UserCache
//) {
//   suspend fun getUser(userId: String): User {
//       val cached: User = userCache.get(userId)
//       if (cached != null) {
//           return cached
//       }
//       // This implementation is still suboptimal but better than before.
//       // A complete implementation also handles error cases.
//       val freshUser = webservice.getUser(userId)
//       userCache.put(userId, freshUser)
//       return freshUser
//   }
//}
//保留数据
//使用我们当前的实现时，如果用户旋转设备或离开应用后立即返回应用，则现有界面将立即变为可见，因为存储区将从内存中的缓存检索数据。
//不过，如果用户离开应用，数小时后当 Android 操作系统已终止进程后再回来，会发生什么？在这种情况下，如果依赖我们当前的实现，则需要再次从网络中获取数据。这一重新获取的过程不仅是一种糟糕的用户体验，而且很浪费资源，因为它会消耗宝贵的移动数据。
//您可以通过缓存网络请求来解决此问题，但这样做会带来一个值得关注的新问题：如果相同的用户数据因另一种类型的请求（如获取好友列表）而显示出来，会发生什么？应用将会显示不一致的数据，这样比较容易让用户感到困惑。例如，如果用户在不同的时间发出好友列表请求和单一用户请求，应用可能会显示同一用户的数据的两个不同版本。应用将需要弄清楚如何合并这些不一致的数据。
//处理这种情况的正确方法是使用持久性模型。这就是 Room 持久性库的用武之地。
//Room 是一个对象映射库，可利用最少的样板代码实现本地数据持久性。在编译时，它会根据数据架构验证每个查询，这样损坏的 SQL 查询会导致编译时错误而不是运行时失败。Room 可以抽象化处理原始 SQL 表格和查询的一些底层实现细节。它还允许您观察对数据库数据（包括集合和连接查询）的更改，并使用 LiveData 对象公开这类更改。它甚至明确定义了解决一些常见线程问题（如访问主线程上的存储空间）的执行约束。
//注意：如果您的应用已使用 SQLite 对象关系映射 (ORM) 等其他持久性解决方案，那么您无需将现有解决方案替换为 Room。不过，如果您正在编写新应用或重构现有应用，那么我们建议您使用 Room 保留应用数据。这样一来，您便可以利用该库的抽象和查询验证功能。
//要使用 Room，我们需要定义本地架构。首先，我们向 User 数据模型类添加 @Entity 注解，并向该类的 id 字段添加 @PrimaryKey 注解。这些注解会将 User 标记为数据库中的表格，并将 id 标记为该表格的主键：
//用户
//@Entity
//data class User(
//   @PrimaryKey private val id: String,
//   private val name: String,
//   private val lastName: String
//)
//然后，我们通过为应用实现 RoomDatabase 来创建一个数据库类：
//UserDatabase
//@Database(entities = [User::class], version = 1)
//abstract class UserDatabase : RoomDatabase()
//请注意，UserDatabase 是抽象类。Room 将自动提供它的实现。详情请参阅 Room 文档。
//现在，我们需要一种将用户数据插入数据库的方法。为了完成此任务，我们创建一个数据访问对象 (DAO)。
//UserDao
//@Dao
//interface UserDao {
//   @Insert(onConflict = REPLACE)
//   fun save(user: User)
//   @Query("SELECT * FROM user WHERE id = :userId")
//   fun load(userId: String): Flow<User>
//}
//请注意，load 方法将返回一个 Flow<User> 类型的对象。借助 Flow with Room，您可以获取实时更新。这意味着，每当 user 数据表发生更改时，系统都会发出一个新的 User。
//注意：Room 将根据表格的修改情况检查是否失效，这意味着它可能会发出误报通知。
//定义 UserDao 类后，从我们的数据库类引用该 DAO：
//UserDatabase
//@Database(entities = [User::class], version = 1)
//abstract class UserDatabase : RoomDatabase() {
//   abstract fun userDao(): UserDao
//}
//现在，我们可以修改 UserRepository 以纳入 Room 数据源：
//class UserRepository @Inject constructor(
//   private val webservice: Webservice,
//   // Simple in-memory cache. Details omitted for brevity.
//   private val executor: Executor,
//   private val userDao: UserDao
//) {
//   fun getUser(userId: String): Flow<User> {
//       refreshUser(userId)
//       // Returns a Flow object directly from the database.
//       return userDao.load(userId)
//   }
//   private suspend fun refreshUser(userId: String) {
//       // Check if user data was fetched recently.
//       val userExists = userDao.hasUser(FRESH_TIMEOUT)
//       if (!userExists) {
//           // Refreshes the data.
//           val response = webservice.getUser(userId)
//           // Check for errors here.
//           // Updates the database. Since `userDao.load()` returns an object of
//           // `Flow<User>`, a new `User` object is emitted every time there's a
//           // change in the `User`  table.
//           userDao.save(response.body()!!)
//       }
//   }
//   companion object {
//       val FRESH_TIMEOUT = TimeUnit.DAYS.toMillis(1)
//   }
//}
//由于 getUser 会返回 Flow<User> 的对象，因此您需要更新 UserProfileViewModel 以处理 Flow<User> 的新返回值类型：
//@HiltViewModel
//class UserProfileViewModel @Inject constructor(
//   savedStateHandle: SavedStateHandle,
//   userRepository: UserRepository
//) : ViewModel() {
//   val userId : String = savedStateHandle["uid"] ?:
//          throw IllegalArgumentException("missing user id")
//   // asLiveData() is part of lifecycle-livedata-ktx
//   // https://developer.android.com/kotlin/ktx#livedata
//   val user = userRepository.getUser(userId).asLiveData()
//}
//请注意，虽然您在 UserRepository 中更改了数据的来源，但不需要更改 UserProfileFragment。这种小范围的更新展示了此应用架构所具有的灵活性。这也很适合测试，因为您可以同时提供 UserRepository 的模拟实例和测试您的正式版 UserProfileViewModel。
//如果用户等待几天后再返回使用此架构的应用，他们很可能会看到过时的信息，直到存储区可以获取更新的信息。根据您的用例，您可能不希望显示这些过时的信息。您可以改为显示占位符数据，此类数据将显示示例值并指示您的应用当前正在获取并加载最新信息。
//单一可信来源
//不同的 REST API 端点返回相同的数据是一种很常见的现象。例如，如果我们的后端有其他端点返回好友列表，则同一个用户对象可能来自两个不同的 API 端点，甚至可能使用不同的粒度级别。如果 UserRepository 按原样从 Webservice 请求返回响应，而不检查一致性，则界面可能会显示混乱的信息，因为来自存储区的数据的版本和格式将取决于最近调用的端点。
//因此，我们的 UserRepository 实现会将网络服务响应保存在数据库中。这样一来，对数据库的更改将触发对活跃 LiveData 对象的回调。使用此模型时，数据库会充当单一可信来源，应用的其他部分则使用 UserRepository 对其进行访问。无论您是否使用磁盘缓存，我们都建议您的存储区将某个数据源指定为应用其余部分的单一可信来源。
//显示正在执行的操作
//在某些用例（如下拉刷新）中，界面务必要向用户显示当前正在执行某项网络操作。将界面操作与实际数据分离开来是一种很好的做法，因为数据可能会因各种原因而更新。例如，如果我们获取了好友列表，可能会程序化地再次获取相同的用户，从而触发 LiveData<User> 更新。从界面的角度来看，传输中的请求只是另一个数据点，类似于 User 对象本身中的其他任何数据。
//我们可以使用以下某个策略，在界面中显示一致的数据更新状态（无论更新数据的请求来自何处）：
//更改 getUser() 以返回一个 LiveData 类型的对象。此对象将包含网络操作的状态。
//有关示例，请参阅 Android 架构组件 GitHub 项目中的 NetworkBoundResource 实现。
//在 UserRepository 类中再提供一个可以返回 User 刷新状态的公共函数。如果您只想在数据获取过程源自于显式用户操作（如下拉刷新）时在界面中显示网络状态，使用此策略效果会更好。
//测试每个组件
//在分离关注点部分中，我们已经提到，遵循此原则的一个主要好处是可测试性。
//以下列表显示了如何测试扩展示例中的每个代码模块：
//界面和交互：使用 Android 界面插桩测试。创建此测试的最佳方法是使用 Espresso 库。您可以创建 Fragment 并为其提供模拟 UserProfileViewModel。由于 Fragment 只与 UserProfileViewModel 通信，因此模拟这一个类就足以完整测试应用的界面。
//ViewModel：您可以使用 JUnit 测试来测试 UserProfileViewModel 类。您只需模拟一个类，即 UserRepository。
//UserRepository：您也可以使用 JUnit 测试来测试 UserRepository。您需要模拟 Webservice 和 UserDao。在这些测试中，请验证以下行为：
//存储区是否进行了正确的网络服务调用。
//存储区是否将结果保存到数据库中。
//在数据已缓存且保持最新状态时，存储区是否不会发出不必要的请求。
//由于 Webservice 和 UserDao 都是接口，因此您可以模拟它们或者为更复杂的测试用例创建虚假实现。
//UserDao：使用插桩测试来测试 DAO 类。由于这些插桩测试不需要任何界面组件，因此它们会快速运行。对于每个测试，都请创建内存中数据库以确保测试没有任何副作用（例如更改磁盘上的数据库文件）。
//注意：Room 允许指定数据库实现，因此可以通过提供 SupportSQLiteOpenHelper 的 JUnit 实现来测试 DAO。不过，不建议您使用这种方法，因为设备上运行的 SQLite 版本可能与开发设备上的 SQLite 版本不同。
//Webservice：在这些测试中，请避免对后端进行网络调用。所有测试（尤其是基于网络的测试）都务必独立于外界。有几个库（包括 MockWebServer）可帮助您为这些测试创建虚假的本地服务器。
//测试工件：架构组件提供了一个可控制其后台线程的 maven 工件。androidx.arch.core:core-testing 工件包含以下 JUnit 规则：
//InstantTaskExecutorRule：使用此规则可立即在调用线程上执行任何后台操作。
//CountingTaskExecutorRule：使用此规则可等待架构组件的后台操作。您还可以将此规则作为空闲资源与 Espresso 关联。
//最佳做法
//编程是一个创造性的领域，构建 Android 应用也不例外。无论是在多个 Activity 或 Fragment 之间传递数据，检索远程数据并将其保留在本地以在离线模式下使用，还是复杂应用遇到的任何其他常见情况，解决问题的方法都会有很多种。
//虽然以下建议不是强制性的，但根据我们的经验，从长远来看，遵循这些建议会使您的代码库更强大、可测试性更高且更易维护：
//避免将应用的入口点（如 Activity、Service 和广播接收器）指定为数据源。
//相反，您应只将其与其他组件协调，以检索与该入口点相关的数据子集。每个应用组件存在的时间都很短暂，具体取决于用户与其设备的交互情况以及系统当前的整体运行状况。
//在应用的各个模块之间设定明确定义的职责界限。
//例如，请勿在代码库中将从网络加载数据的代码散布到多个类或软件包中。同样，也不要将不相关的职责（如数据缓存和数据绑定）定义到同一个类中。
//尽量少公开每个模块中的代码。
//请勿试图创建“就是那一个”快捷方式来呈现一个模块的内部实现细节。短期内，您可能会省点时间，但随着代码库的不断发展，您可能会反复陷入技术上的麻烦。
//考虑如何使每个模块可独立测试。
//例如，如果使用明确定义的 API 从网络获取数据，将会更容易测试在本地数据库中保留该数据的模块。如果您将这两个模块的逻辑混放在一处，或将网络代码分散在整个代码库中，那么即便能够进行测试，难度也会大很多。
//专注于应用的独特核心，以使其从其他应用中脱颖而出。
//不要一次又一次地编写相同的样板代码，这是在做无用功。相反，您应将时间和精力集中放在能让应用与众不同的方面上，并让 Android 架构组件以及建议的其他库处理重复的样板。
//保留尽可能多的相关数据和最新数据。
//这样，即使用户的设备处于离线模式，他们也可以使用您应用的功能。请注意，并非所有用户都能享受到稳定的高速连接。
//将一个数据源指定为单一可信来源。
//每当应用需要访问这部分数据时，这部分数据都应一律源于此单一可信来源。
//公开网络状态
//在上文的推荐应用架构部分中，我们省略了网络错误和加载状态以简化代码段。
//本部分将演示如何使用可封装数据及其状态的 Resource 类来公开网络状态。
//以下代码段提供了 Resource 的实现示例：
//Resource
//// A generic class that contains data and status about loading this data.
//sealed class Resource<T>(
//   val data: T? = null,
//   val message: String? = null
//) {
//   class Success<T>(data: T) : Resource<T>(data)
//   class Loading<T>(data: T? = null) : Resource<T>(data)
//   class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
//}
//有一个很常见的情况是，一边从网络加载数据，一边显示这些数据在磁盘上的副本，因此建议您创建一个可在多个地方重复使用的辅助程序类。在本例中，我们创建一个名为 NetworkBoundResource 的类。
//下图显示了 NetworkBoundResource 的决策树：
//它首先观察资源的数据库。首次从数据库中加载条目时，NetworkBoundResource 会检查结果是好到足以分派，还是应从网络中重新获取。请注意，考虑到您可能会希望在通过网络更新数据的同时显示缓存的数据，这两种情况可能会同时发生。
//如果网络调用成功完成，它会将响应保存到数据库中并重新初始化数据流。如果网络请求失败，NetworkBoundResource 会直接分派失败消息。
//注意：在将新数据保存到磁盘后，我们会重新初始化来自数据库的数据流。不过，通常我们不需要这样做，因为数据库本身正好会分派更改。
//请注意，依赖于数据库来分派更改将产生相关副作用，这样不太好，原因是，如果由于数据未更改而使得数据库最终未分派更改，就会出现这些副作用的未定义行为。
//此外，不要分派来自网络的结果，因为这样将违背单一可信来源原则。毕竟，数据库可能包含在“保存”操作期间更改数据值的触发器。同样，不要在没有新数据的情况下分派 `SUCCESS`，因为如果这样做，客户端会接收错误版本的数据。
//以下代码段显示了 NetworkBoundResource 类为其子类提供的公共 API：
//NetworkBoundResource.kt
//// ResultType: Type for the Resource data.
//// RequestType: Type for the API response.
//abstract class NetworkBoundResource<ResultType, RequestType> {
//   // Called to save the result of the API response into the database
//   @WorkerThread
//   protected abstract suspend fun saveCallResult(item: RequestType)
//   // Called with the data in the database to decide whether to fetch
//   // potentially updated data from the network.
//   @MainThread
//   protected abstract fun shouldFetch(data: ResultType?): Boolean
//   // Called to get the cached data from the database.
//   @MainThread
//   protected abstract suspend fun loadFromDb(): Flow<ResultType>
//   // Called to create the API call.
//   @MainThread
//   protected abstract fun createCall(): Flow<ApiResponse<RequestType>>
//   // Called when the fetch fails. The child class may want to reset components
//   // like rate limiter.
//   protected open fun onFetchFailed() {}
//}
//请注意有关该类定义的以下重要细节：
//它定义了两个类型参数（ResultType 和 RequestType），因为从 API 返回的数据类型可能与本地使用的数据类型不匹配。
//它对网络请求使用了一个名为 ApiResponse 的类。ApiResponse 是 Retrofit2.Call 类的一个简单封装容器，可将响应转换为 LiveData 实例。
//NetworkBoundResource 类的完整实现作为 Android 架构组件 GitHub 项目的一部分出现。
//创建 NetworkBoundResource 后，我们可以使用它在 UserRepository 类中编写磁盘和网络绑定的 User 实现：
//UserRepository
//class UserRepository @Inject constructor(
//   private val webservice: Webservice,
//   private val userDao: UserDao
//) {
//   fun getUser(userId: String) =
//       object : NetworkBoundResource<User, User>() {
//           override suspend fun saveCallResult(item: User) {
//               userDao.save(item)
//           }
//           override fun shouldFetch(data: User?): Boolean {
//               return rateLimiter.canFetch(userId) && (data == null || !isFresh(data))
//           }
//           override suspend fun loadFromDb(): Flow<User> {
//               return userDao.load(userId)
//           }
//           override fun createCall(): Flow<ApiResponse<User>> {
//               return webservice.getUser(userId)
//           }
//       }
//}