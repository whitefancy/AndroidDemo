package pub.whitefancy.androiddemo;

public class StorageUsage {
}
//将文件保存到外部存储
//对于您要与其他应用共享或允许用户使用计算机访问的文件，将其存储在外部存储上是很好的选择。
//外部存储通常是通过可移动设备（例如 SD 卡）来提供的。Android 使用路径（例如 /sdcard）来表示这些设备。
//在您请求存储权限并确认存储可用后，可以保存以下类型的文件：
//公开文件：应可供其他应用和用户自由访问的文件。在用户卸载您的应用后，这些文件应该仍然可供用户使用。例如，您的应用拍摄的照片应保存为公开文件。
//私有文件：存储在特定于应用的目录中的文件（使用 Context.getExternalFilesDir() 来访问）。这些文件在用户卸载您的应用时会被清除。尽管这些文件在技术上可被用户和其他应用访问（因为它们存储在外部存储上），但它们不能为应用之外的用户提供价值。可以使用此目录来存储您不想与其他应用共享的文件。
//注意：如果用户移除了外部存储设备（例如 SD 卡）或断开其连接，则存储在外部存储上的文件可能会变得不可用。如果您的应用功能依赖于这些文件，则应将文件写入内部存储。
//设置虚拟外部存储设备
//在没有可移动外部存储的设备上，可使用以下命令启用虚拟磁盘进行测试：
//adb shell sm set-virtual-disk true
//请求外部存储权限
//Android 包含以下访问外部存储中的文件的权限：
//READ_EXTERNAL_STORAGE
//允许应用访问外部存储设备中的文件。
//WRITE_EXTERNAL_STORAGE
//允许应用在外部存储设备中写入和修改文件。拥有此权限的应用也会自动获得 READ_EXTERNAL_STORAGE 权限。
//从 Android 4.4（API 级别 19）开始，在特定于应用的目录中读取或写入文件不再需要任何与存储相关的权限。因此，如果您的应用支持 Android 4.3（API 级别 18）及更低版本，并且您只想访问特定于应用的目录，则应添加 maxSdkVersion 属性，声明仅在较低版本的 Android 上请求权限：
//<manifest ...>
//<!-- If you need to modify files in external storage, request
//WRITE_EXTERNAL_STORAGE instead. -->
//<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"
//android:maxSdkVersion="18" />
//</manifest>
//注意：如果您的应用使用分区存储，则除非您的应用需要访问其他应用创建的文件，否则您将无需声明任何与存储相关的权限。
//验证外部存储是否可用
//由于外部存储可能会不可用（比如，当用户将存储安装到另一台机器或移除了提供外部存储的 SD 卡时），因此在访问之前，您应该始终先确认相应的卷可用。您可以通过调用 getExternalStorageState() 来查询外部存储的状态。如果返回的状态为 MEDIA_MOUNTED，则可以读取和写入文件。如果返回的是 MEDIA_MOUNTED_READ_ONLY，则只能读取文件。
//例如，以下方法可用于确定存储的可用性：
//KOTLIN
//JAVA
///* Checks if external storage is available for read and write */
//public boolean isExternalStorageWritable() {
//String state = Environment.getExternalStorageState();
//if (Environment.MEDIA_MOUNTED.equals(state)) {
//return true;
//}
//return false;
//}
///* Checks if external storage is available to at least read */
//public boolean isExternalStorageReadable() {
//String state = Environment.getExternalStorageState();
//if (Environment.MEDIA_MOUNTED.equals(state) ||
//Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
//return true;
//}
//return false;
//}
//保存到公开目录
//如果要将文件保存到其他应用可以访问的外部存储上，请使用以下 API 之一：
//如果要保存照片、音频文件或视频剪辑，请使用 MediaStore API。
//如果要保存任何其他文件（如 PDF 文档），请使用 ACTION_CREATE_DOCUMENT intent，这是存储访问框架的一部分。
//如果您不希望媒体扫描程序发现您的文件，请在特定于应用的目录中添加名为 .nomedia 的空文件（请注意文件名中的句点前缀）。这可以防止媒体扫描程序读取您的媒体文件并通过 MediaStore API 将它们提供给其他应用。
//保存到私有目录
//如果您想将应用专用文件保存在外部存储上，可以通过调用 getExternalFilesDir() 并传入指明您想要的目录类型的名称来获取特定于应用的目录。通过这种方式创建的每个目录都会被添加到一个父目录中，该目录包含了应用的所有外部存储文件，当用户卸载应用时，系统会清除这些文件。
//以下代码段展示了如何为单个相册创建目录：
//KOTLIN
//JAVA
//public File getPrivateAlbumStorageDir(Context context, String albumName) {
//// Get the directory for the app's private pictures directory.
//File file = new File(context.getExternalFilesDir(
//Environment.DIRECTORY_PICTURES), albumName);
//if (!file.mkdirs()) {
//Log.e(LOG_TAG, "Directory not created");
//}
//return file;
//}
//请务必使用由 DIRECTORY_PICTURES 这类 API 常量提供的目录名称。这些目录名称可确保系统正确地处理这些文件。例如，保存在 DIRECTORY_RINGTONES 中的文件会被系统媒体扫描程序归类为铃声，而不是音乐。
//如果没有适合您文件的预定义子目录名称，您可以调用 getExternalFilesDir() 并传入 null。这会返回应用在外部存储上的专用目录的根目录。
//注意：getExternalFilesDir() 创建的目录在用户卸载您的应用时会被清除。如果您保存的文件需在用户卸载您的应用后继续保持可用（例如，当用户需要保留您的应用拍摄的照片时），则应将文件保存到公开目录中。
//在多个存储位置之间选择
//有时，分配内部存储分区作为外部存储的设备也会提供 SD 卡插槽。这意味着该设备会有两个不同的外部存储目录，因此在将“私有”文件写入外部存储时，需要选择使用哪个目录。
//从 Android 4.4（API 级别 19）开始，您可以通过调用 getExternalFilesDirs() 来访问这两个位置，这会返回一个 File 数组，其中包含了每个存储位置的条目。数组中的第一个条目被视为主要外部存储，除非该位置已满或不可用，否则应该一律使用该位置。
//如果您的应用支持 Android 4.3 及更低版本，则应使用支持库的静态方法 ContextCompat.getExternalFilesDirs()。这始终会返回一个 File 数组，但如果设备搭载的是 Android 4.3 及更低版本，数组中将仅包含主要外部存储的条目。（如果有第二个存储位置，您将无法在 Android 4.3 及更低版本上访问它。）
//唯一卷名称
//面向 Android 10（API 级别 29）或更高版本的应用可以访问系统分配给每个外部存储设备的唯一名称。此命名系统可帮助您高效地整理内容并将内容编入索引，还可让您控制新内容的存储位置。
//主要共享存储设备始终名为 VOLUME_EXTERNAL_PRIMARY。您可以通过调用 MediaStore.getExternalVolumeNames() 来发现其他卷。
//要查询、插入、更新或删除特定卷，请将卷名称传入 MediaStore API 中提供的任何 getContentUri() 方法，如以下代码段中所示：
//// Assumes that the storage device of interest is the 2nd one
//// that your app recognizes.
//val volumeNames = MediaStore.getExternalVolumeNames(context)
//val selectedVolumeName = volumeNames[1]
//val collection = MediaStore.Audio.Media.getContentUri(selectedVolumeName)
//// ... Use a ContentResolver to add items to the returned media collection.
//注意：从 Android 10 开始，StorageVolume 类中的 createAccessIntent() 方法已被弃用，因此您不应再使用此方法浏览外部存储设备。如果您这样做，在搭载 Android 10 或更高版本的设备上访问您的应用的用户，
// 将无法在您的应用中查看保存在外部存储中的文件。

//处理媒体文件
//本部分介绍了处理媒体文件（视频、图片和音频文件）的一些常见用例，并概要说明了应用可以使用的方法。下表对其中每个用例进行了总结，并列出了包含更多详细介绍的各个部分的链接。
//用例 摘要
//显示所有图片或视频文件 在所有 Android 版本中均使用相同的方法。
//显示特定文件夹中的图片或视频 在所有 Android 版本中均使用相同的方法。
//访问照片中的位置信息 如果应用使用分区存储，请使用一种方法。如果应用停用分区存储，请使用不同方法。
//在一次操作中修改或删除多个媒体文件 在 Android 11 中，请使用一种方法。在 Android 10 中，请停用分区存储并改用适用于 Android 9 及更低版本的方法。
//导入已经存在的单张图片 在所有 Android 版本中均使用相同的方法。
//拍摄单张图片 在所有 Android 版本中均使用相同的方法。
//与其他应用共享媒体文件 在所有 Android 版本中均使用相同的方法。
//与特定应用共享媒体文件 在所有 Android 版本中均使用相同的方法。
//使用直接文件路径访问代码或库中的文件 在 Android 11 中，请使用一种方法。在 Android 10 中，请停用分区存储并改用适用于 Android 9 及更低版本的方法。
//显示多个文件夹中的图片或视频文件
//使用 query() API 查询媒体集合。如需对媒体文件进行过滤或排序，请调整 projection、selection、selectionArgs 和 sortOrder 参数。
//显示特定文件夹中的图片或视频
//请使用以下方法：
//按照请求应用权限中所述的最佳做法，请求 READ_EXTERNAL_STORAGE 权限。
//根据 MediaColumns.DATA 的值检索媒体文件，该值包含磁盘上的媒体项的绝对文件系统路径。
//访问照片中的位置信息
//如果应用使用分区存储，请按照媒体存储指南的照片中的位置信息部分的步骤操作。
//注意：即使停用分区存储，您也需要 ACCESS_MEDIA_LOCATION 权限才能读取使用 MediaStore API 访问的图片中的未编辑位置信息。
//在一次操作中修改或删除多个媒体文件
//根据应用在哪个 Android 版本上运行来纳入逻辑。
//在 Android 11 上运行
//请使用以下方法：
//使用 MediaStore.createWriteRequest() 或 MediaStore.createTrashRequest() 为应用的写入或删除请求创建待定 intent，然后通过调用该 intent 提示用户授予修改一组文件的权限。
//评估用户的响应：
//如果授予了权限，请继续修改或删除操作。
//如果未授予权限，请向用户说明您的应用中的功能为何需要该权限。
//详细了解如何使用 Android 11 中提供的这些方法执行批量操作。
//在 Android 10 上运行
//如果您的应用以 Android 10（API 级别 29）为目标平台，请停用分区存储，继续使用适用于 Android 9 及更低版本的方法来执行此操作。
//在 Android 9 或更低版本上运行
//请使用以下方法：
//按照请求应用权限中所述的最佳做法，请求 WRITE_EXTERNAL_STORAGE 权限。
//使用 MediaStore API 修改或删除媒体文件。
//导入已经存在的单张图片
//当您要导入已经存在的单张图片（例如，用作用户个人资料的照片）时，应用可以将自己的界面用于此操作，也可以使用系统选择器。
//提供您自己的界面
//请使用以下方法：
//按照请求应用权限中所述的最佳做法，请求 READ_EXTERNAL_STORAGE 权限。
//使用 query() API 查询媒体集合。
//在应用的自定义界面中显示结果。
//使用系统选择器
//使用 ACTION_GET_CONTENT intent，它会要求用户选择要导入的图片。
//如果您想过滤系统选择器提供给用户选择的图片类型，您可以使用 setType() 或 EXTRA_MIME_TYPES。
//拍摄单张图片
//当您想拍摄单张图片在应用中使用（例如，用作用户个人资料的照片）时，请使用 ACTION_IMAGE_CAPTURE intent 要求用户使用设备的摄像头拍照。系统会将拍摄的照片存储在 MediaStore.Images 表中。
//与其他应用共享媒体文件
//使用 insert() 方法将记录直接添加到 MediaStore 中。如需了解详情，请参阅媒体存储指南的添加项目部分。
//与特定应用共享媒体文件
//按照设置文件共享指南中所述，使用 Android FileProvider 组件。
//从代码或依赖库中使用直接文件路径访问文件
//根据应用在哪个 Android 版本上运行来纳入逻辑。
//在 Android 11 上运行
//请使用以下方法：
//按照请求应用权限中所述的最佳做法，请求 READ_EXTERNAL_STORAGE 权限。
//使用直接文件路径访问文件。
//如需了解详情，请参阅使用原始路径访问文件。
//在 Android 10 上运行
//如果您的应用以 Android 10（API 级别 29）为目标平台，请停用分区存储，继续使用适用于 Android 9 及更低版本的方法来执行此操作。
//在 Android 9 或更低版本上运行
//请使用以下方法：
//按照请求应用权限中所述的最佳做法，请求 WRITE_EXTERNAL_STORAGE 权限。
//使用直接文件路径访问文件。
//处理非媒体文件
//本部分介绍了处理非媒体文件的一些常见用例，并概要说明了应用可以使用的方法。下表对其中每个用例进行了总结，并列出了包含更多详细介绍的各个部分的链接。
//用例 摘要
//打开文档文件 在所有 Android 版本中均使用相同的方法。
//从旧版存储位置迁移现有文件 请尽可能将文件迁移到分区存储。在 Android 10 中，请根据需要停用分区存储。
//与其他应用共享内容 在所有 Android 版本中均使用相同的方法。
//缓存非媒体文件 在所有 Android 版本中均使用相同的方法。
//打开文档文件
//使用 ACTION_OPEN_DOCUMENT intent 要求用户使用系统选择器选择要打开的文件。如果您想过滤系统选择器提供给用户选择的文件类型，您可以使用 setType() 或 EXTRA_MIME_TYPES。
//例如，您可以使用以下代码查找所有 PDF、ODT 和 TXT 文件：
//KOTLIN
//JAVA
//Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//intent.addCategory(Intent.CATEGORY_OPENABLE);
//intent.setType("*/*");
//intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[] {
//"application/pdf", // .pdf
//"application/vnd.oasis.opendocument.text", // .odt
//"text/plain" // .txt
//});
//startActivityForResult(intent, REQUEST_CODE);
//从旧版存储位置迁移现有文件
//如果目录不是应用专属目录或公开共享目录，则被视为旧版存储位置。如果您的应用要在旧版存储位置中创建文件或使用其中的文件，我们建议您将应用的文件迁移到可通过分区存储进行访问的位置，并对应用进行必要的更改以使用分区存储中的文件。
//保留对旧版存储位置的访问权限以进行数据迁移
//您的应用需要保留对旧版存储位置的访问权限，才能将任何应用文件迁移到可通过分区存储进行访问的位置。您应该使用的方法取决于应用的目标 API 级别。
//如果应用以 Android 11 为目标平台
//使用 preserveLegacyExternalStorage 标记保留旧版存储模型，以便在用户升级到以 Android 11 为目标平台的新版应用时，应用可以迁移用户的数据。
//注意：如果您使用 preserveLegacyExternalStorage，旧版存储模型只在用户卸载您的应用之前保持有效。如果用户在搭载 Android 11 的设备上安装或重新安装您的应用，那么无论 preserveLegacyExternalStorage 的值是什么，您的应用都无法停用分区存储模型。
//继续停用分区存储，以便您的应用可以继续在搭载 Android 10 的设备上访问旧版存储位置中的文件。
//如果应用以 Android 10 为目标平台
//停用分区存储，更轻松地在不同 Android 版本之间保持应用行为不变。
//迁移应用数据
//当应用准备就绪，可以迁移时，请使用以下方法：
//检查应用的工作文件是否位于 /sdcard/ 目录或其任何子目录中。
//将任何私有应用文件从 /sdcard/ 下的当前位置移至 getExternalFilesDir() 方法所返回的目录。
//将任何共享的非媒体文件从 /sdcard/ 下的当前位置移至 Downloads/ 目录的应用专用子目录。
//从 /sdcard/ 目录中移除应用的旧存储目录。
//与其他应用共享内容
//如需与一个其他应用共享应用的文件，请使用 FileProvider。对于全部需要在彼此之间共享文件的应用，我们建议您对每个应用使用内容提供程序，然后在将应用添加到集合中时同步数据。
//缓存非媒体文件
//您应该使用的方法取决于您需要缓存的文件类型。
//小文件或包含敏感信息的文件：请使用 Context#getCacheDir()。
//大型文件或不含敏感信息的文件：请使用 Context#getExternalCacheDir()。
//暂时停用分区存储
//在您的应用与分区存储完全兼容之前，您可以使用以下方法之一暂时停用分区存储：
//以 Android 9（API 级别 28）或更低版本为目标平台。
//如果您以 Android 10（API 级别 29）或更高版本为目标平台，请在应用的清单文件中将 requestLegacyExternalStorage 的值设置为 true：
//<manifest ... >
//<!-- This attribute is "false" by default on apps targeting
//Android 10 or higher. -->
//<application android:requestLegacyExternalStorage="true" ... >
//...
//</application>
//</manifest>
//注意：当您将应用更新为以 Android 11（API 级别 30）为目标平台后，如果应用在搭载 Android 11 的设备上运行，系统会忽略 requestLegacyExternalStorage 属性，因此您的应用必须做好支持分区存储并为这些设备上的用户迁移应用数据的准备。
//如需测试以 Android 9 或更低版本为目标平台的应用在使用分区存储时的行为，您可以通过将 requestLegacyExternalStorage 的值设置为 false，选择启用该行为。如果在搭载 Android 11 的设备上进行测试，您还可以使用应用兼容性标记来测试应用在使用和不使用分区存储时的行为。
//将文件保存到内部存储空间中
//应用的内部存储目录由位于 Android 文件系统的特殊位置（可使用以下 API 访问）的应用软件包名称指定。
//注意：与外部存储目录不同，您的应用不需要任何系统权限即可读取和写入这些方法返回的内部目录。
//查询可用空间
//如果您事先知道要保存多少数据，则可以通过调用 getFreeSpace() 或 getTotalSpace() 确定是否有足够的可用空间，而不引发 IOException。这两个方法会分别提供存储卷中的当前可用空间和总空间。这些信息还有助于避免填入存储卷的数据量超过特定阈值。
//不过，系统并不能保证您可以写入的字节数能够达到 getFreeSpace() 所指示的量。如果返回的可用空间量比您要保存的数据量多几 MB，或者已占用的文件系统容量不到 90%，则可以继续保存数据。否则，您可能不应该写入存储空间。
//注意：您无需检查可用空间就可以保存文件。您可以尝试立即写入文件，然后在出现异常时捕获 IOException。如果您不知道确切需要多少空间，则可能需要执行此操作。例如，如果您在保存文件前更改了文件的编码（将 PNG 图片转换为 JPEG 格式），则不会事先得知文件的大小。
//写入文件
//将文件保存到内部存储空间时，您可以通过调用以下方法之一，获取相应目录作为 File 对象：
//getFilesDir()
//返回表示应用内部目录的 File。
//getCacheDir()
//返回表示应用临时缓存文件的内部目录的 File。请务必删除各个不再需要的文件，并对在任何给定时间使用的内存量设定合理的大小限制（例如 1 MB）。
//注意：如果系统的存储空间不足，则可能会在不发出警告的情况下删除您的缓存文件。
//要在其中一个目录中创建新文件，您可以使用 File() 构造函数，并传入上述方法之一提供的 File（指定了内部存储目录）。例如：
//KOTLIN
//JAVA
//File file = new File(context.getFilesDir(), filename);
//如需更安全的解决方案，请使用 Jetpack Security 库，如以下代码段所示：
//KOTLIN
//JAVA
//// Although you can define your own key generation parameter specification, it's
//// recommended that you use the value specified here.
//KeyGenParameterSpec keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC;
//String masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec);
//// Creates a file with this name, or replaces an existing file
//// that has the same name. Note that the file name cannot contain
//// path separators.
//String fileToWrite = "my_sensitive_data.txt";
//try {
//EncryptedFile encryptedFile = new EncryptedFile.Builder(
//new File(directory, fileToWrite),
//context,
//masterKeyAlias,
//EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
//).build();
//// Write to a file.
//BufferedWriter writer = new BufferedWriter(new FileWriter(encryptedFile));
//writer.write("MY SUPER-SECRET INFORMATION");
//} catch (GeneralSecurityException gse) {
//// Error occurred getting or creating keyset.
//} catch (IOException ex) {
//// Error occurred opening file for writing.
//}
//或者，您可以调用 openFileOutput() 以获取会写入内部目录中的文件的 FileOutputStream。例如，以下代码展示了如何将一些文本写入文件：
//KOTLIN
//JAVA
//String filename = "myfile";
//String fileContents = "Hello world!";
//FileOutputStream outputStream;
//try {
//outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
//outputStream.write(fileContents.getBytes());
//outputStream.close();
//} catch (Exception e) {
//e.printStackTrace();
//}
//请注意，openFileOutput() 方法需要文件模式参数。传入 MODE_PRIVATE 会使该文件成为应用的私有文件。自 API 级别 17 起，其他模式选项 MODE_WORLD_READABLE 和 MODE_WORLD_WRITEABLE 已弃用。从 Android 7.0（API 级别 24）开始，如果您使用这些选项，Android 会抛出 SecurityException。如果您的应用需要与其他应用共享私有文件，您应改用具有 FLAG_GRANT_READ_URI_PERMISSION 属性的 FileProvider。如需了解详情，请参阅共享文件。
//在 Android 6.0（API 级别 23）或更低版本中，如果您将内部文件模式设置为全局可读，则其他应用可以读取您的内部文件。不过，其他应用必须知道您的应用软件包名称和文件名。其他应用无法浏览您的内部目录，也不具有读取或写入权限，除非您将这些文件明确设置为可读或可写文件。因此，只要您对内部存储空间中的文件使用了 MODE_PRIVATE，其他应用就一定无法访问这些文件。
//写入缓存文件
//如果您需要缓存某些文件，则应使用 createTempFile()。例如，以下方法从 URL 对象中提取文件名称，并使用该名称在应用的内部缓存目录下创建一个文件：
//KOTLIN
//JAVA
//private File getTempFile(Context context, String url) {
//// For a more secure solution, use EncryptedFile from the Security library
//// instead of File.
//File file;
//try {
//String fileName = Uri.parse(url).getLastPathSegment();
//file = File.createTempFile(fileName, null, context.getCacheDir());
//} catch (IOException e) {
//// Error while creating file
//}
//return file;
//}
//使用 createTempFile() 创建的文件位于应用的私有缓存目录下。您应定期删除不再需要的文件。
//注意：如果系统的存储空间不足，则可能会在不发出警告的情况下删除您的缓存文件，因此请务必在读取之前检查缓存文件是否存在。
//打开现有文件
//您可以使用 Security 库以更安全的方式读取文件，如以下代码段所示：
//KOTLIN
//JAVA
//// Although you can define your own key generation parameter specification, it's
//// recommended that you use the value specified here.
//KeyGenParameterSpec keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC;
//String masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec);
//String fileToRead = "my_sensitive_data.txt";
//ByteArrayOutputStream byteStream;
//EncryptedFile encryptedFile = new EncryptedFile.Builder(
//File(directory, fileToRead),
//context,
//masterKeyAlias,
//EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
//).build();
//StringBuffer stringBuffer = new StringBuffer();
//try (BufferedReader reader =
//new BufferedReader(new FileReader(encryptedFile))) {
//String line = reader.readLine();
//while (line != null) {
//stringBuffer.append(line).append('
//');
//line = reader.readLine();
//}
//} catch (IOException e) {
//// Error occurred opening raw file for reading.
//} finally {
//String contents = stringBuffer.toString();
//}
//或者，您可以调用 openFileInput(name) 并传入文件名。
//您可以通过调用 fileList() 获取应用的所有文件名的数组。
//注意：如果您需要在应用中打包可在安装时访问的文件，请将该文件保存到项目的 /res/raw 目录下。您可以使用 openRawResource() 打开这些文件，并传入带有 R.raw 前缀的文件名作为资源 ID。此方法会返回可用于读取文件的 InputStream。您无法写入原始文件。
//打开目录
//您可以使用以下方法打开内部文件系统中的目录：
//getFilesDir()
//返回一个 File 对象，它表示文件系统中与您的应用唯一关联的目录。
//getDir(name, mode)
//在应用的唯一文件系统目录中创建新目录（或打开现有目录）。这个新目录显示在 getFilesDir() 提供的目录内。
//getCacheDir()
//返回一个 File 对象，它表示文件系统中与您的应用唯一关联的缓存目录。此目录存放的是临时文件，您应定期清理此目录。如果磁盘空间不足，系统可能会删除其中的文件，因此请务必在读取之前检查缓存文件是否存在。
//要在其中一个目录中创建新文件，您可以使用 File() 构造函数，并传入上述方法之一提供的 File（指定了内部存储目录）。例如：
//KOTLIN
//JAVA
//File directory = context.getFilesDir();
//File file = new File(directory, filename);
//删除文件
//您应始终删除您的应用不再需要的文件。删除文件最直接的方法是对 File 对象调用 delete()：
//KOTLIN
//JAVA
//myFile.delete();
//如果该文件保存在内部存储空间中，您还可以通过调用 deleteFile() 让 Context 找到并删除该文件：
//KOTLIN
//JAVA
//myContext.deleteFile(fileName);
//注意：如果用户卸载您的应用，Android 系统会删除以下文件：
//您在内部存储空间中保存的所有文件。
//您使用 getExternalFilesDir() 在外部存储空间中保存的所有文件。
//不过，您应定期使用 getCacheDir() 手动删除创建的所有缓存文件，并定期删除不再需要的其他文件。
//访问媒体内容时的注意事项
//访问媒体内容时，请注意以下几部分中介绍的注意事项。
//存储卷
//以 Android 10 或更高版本为目标平台的应用可以访问系统为每个外部存储卷分配的唯一名称。此命名系统可帮助您高效地整理内容并将内容编入索引，还可让您控制新媒体文件的存储位置。
//主要共享存储卷始终名为 VOLUME_EXTERNAL_PRIMARY。您可以通过调用 MediaStore.getExternalVolumeNames() 查看其他存储卷：
//KOTLIN
//JAVA
//Set<String> volumeNames = MediaStore.getExternalVolumeNames(context);
//String firstVolumeName = volumeNames.iterator().next();
//照片中的位置信息
//一些照片在其 Exif 元数据中包含位置信息，以便用户查看照片的拍摄地点。但是，由于此位置信息属于敏感信息，如果应用使用了分区存储，默认情况下 Android 10 会对应用隐藏此信息。
//如果您的应用需要访问照片的位置信息，请完成以下步骤：
//在应用的清单中请求 ACCESS_MEDIA_LOCATION 权限。
//通过调用 setRequireOriginal()，从 MediaStore 对象获取照片的确切字节，并传入照片的 URI，如以下代码段所示：
//KOTLIN
//JAVA
//Uri photoUri = Uri.withAppendedPath(
//MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//cursor.getString(idColumnIndex));
//final double[] latLong;
//// Get location data using the Exifinterface library.
//// Exception occurs if ACCESS_MEDIA_LOCATION permission isn't granted.
//photoUri = MediaStore.setRequireOriginal(photoUri);
//InputStream stream = getContentResolver().openInputStream(photoUri);
//if (stream != null) {
//ExifInterface exifInterface = new ExifInterface(stream);
//double[] returnedLatLong = exifInterface.getLatLong();
//// If lat/long is null, fall back to the coordinates (0, 0).
//latLong = returnedLatLong != null ? returnedLatLong : new double[2];
//// Don't reuse the stream associated with
//// the instance of "ExifInterface".
//stream.close();
//} else {
//// Failed to load the stream, so return the coordinates (0, 0).
//latLong = new double[2];
//}
//媒体共享
//某些应用允许用户彼此分享媒体文件。例如，用户可以通过社交媒体应用与朋友分享照片和视频。
//如需共享媒体文件，请按照内容提供程序创建指南中的建议使用 content:// URI。
//使用原始文件路径的内容访问
//如果您没有任何与存储空间相关的权限，您可以访问应用专属目录中的文件，并可使用 File API 访问归因于您的应用的媒体文件。
//如果您的应用尝试使用 File API 访问文件但没有必要的权限，就会发生 FileNotFoundException。
//如需在搭载 Android 10 的设备上访问共享存储空间中的其他文件，建议您在应用的清单文件中将 requestLegacyExternalStorage 设置为 true 以停用分区存储。
//从原生代码访问内容
//您可能会遇到您的应用需要在原生代码中使用特定媒体文件的情况，例如其他应用与您的应用共享的文件，或用户的媒体合集中的媒体文件。
//您必须先执行以下操作，然后应用才能使用 fopen() 等原生文件方法读取媒体文件：
//在应用的清单文件中将 requestLegacyExternalStorage 设置为 true。
//请求 READ_EXTERNAL_STORAGE 权限。
//如果您需要写入这些媒体文件，请将与文件相关联的文件描述符从基于 Java 或基于 Koltin 的代码传入原生代码。以下代码段演示了如何将媒体对象的文件描述符传入应用的原生代码：
//KOTLIN
//JAVA
//Uri contentUri = ContentUris.withAppendedId(
//MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
//cursor.getLong(Integer.parseInt(BaseColumns._ID)));
//String fileOpenMode = "r";
//ParcelFileDescriptor parcelFd =
//resolver.openFileDescriptor(contentUri, fileOpenMode);
//if (parcelFd != null) {
//int fd = parcelFd.detachFd();
//// Pass the integer value "fd" into your native code. Remember to call
//// close(2) on the file descriptor when you're done using it.
//}
//如需详细了解如何在原生代码中访问文件，请参阅 2018 年 Android 开发者峰会中的 Files for Miles 演讲（从 15:20 开始）。
//媒体文件的应用归因
//当以 Android 10 或更高版本为目标平台的应用启用了分区存储时，系统会将每个媒体文件归因于一个应用，这决定了应用在未请求任何存储权限时可以访问的文件。每个文件只能归因于一个应用。因此，如果您的应用创建的媒体文件存储在照片、视频或音频文件媒体集合中，应用便可以访问该文件。
//但是，如果用户卸载并重新安装您的应用，您必须请求 READ_EXTERNAL_STORAGE 才能访问应用最初创建的文件。此权限请求是必需的，因为系统认为文件归因于以前安装的应用版本，而不是新安装的版本。
//处理外部存储中的媒体文件
//MediaStore API 提供访问以下类型的媒体文件的接口：
//照片：存储在 MediaStore.Images 中。
//视频：存储在 MediaStore.Video 中。
//音频文件：存储在 MediaStore.Audio 中。
//MediaStore 还包含一个名为 MediaStore.Files 的集合，该集合提供访问所有类型的媒体文件的接口。
//注意：如果您的应用使用分区存储，MediaStore.Files 集合将仅显示照片、视频和音频文件。
//本指南介绍如何访问和共享通常存储在外部存储设备上的媒体文件。
//访问文件
//若要加载媒体文件，请从 ContentResolver 调用以下方法之一：
//对于单个媒体文件，请使用 openFileDescriptor()。
//对于单个媒体文件的缩略图，请使用 loadThumbnail()，并传入要加载的缩略图的大小。
//对于媒体文件的集合，请使用 query()。Android 11 中的存储机制更新
//Android 11 中的存储机制更新
//Android 11 进一步增强了平台功能，为外部存储设备上的应用和用户数据提供了更好的保护。预览版引入了多项去年在 Android 开发者峰会上宣布的增强功能，例如可主动选择启用的媒体原始文件路径访问机制、面向媒体的批量修改操作，以及存储访问框架的界面更新。
//为了帮助开发者轻松过渡到使用分区存储，该平台为开发者引入了进一步的改进。如需详细了解如何根据应用的用例迁移应用以使用分区存储，请参阅本页的分区存储部分、Android 存储用例和最佳做法指南，以及标题为 Android 存储常见问题解答的媒体文章。
//我们一如既往地诚邀您提供反馈，帮助我们完善下一版 Android。请使用问题跟踪器向我们发送反馈意见。
//强制执行分区存储
//为了给开发者更多时间进行测试，以 Android 10（API 级别 29）为目标平台的应用仍可请求 requestLegacyExternalStorage 属性。应用可以利用此标记暂时停用与分区存储相关的变更，例如授予对不同目录和不同类型的媒体文件的访问权限。当您将应用更新为以 Android 11 为目标平台后，系统会忽略 requestLegacyExternalStorage 标记。
//保持与 Android 10 的兼容性
//如果应用在 Android 10 设备上运行时选择退出分区存储，建议您继续在应用的清单文件中将 requestLegacyExternalStorage 设为 true。这样，应用就可以在运行 Android 10 的设备上继续按预期运行。
//将数据迁移到使用分区存储时可见的目录
//如果您的应用使用旧版存储模型且之前以 Android 10 或更低版本为目标平台，您可能会将数据存储到启用分区存储模型后您的应用无法访问的目录中。在以 Android 11 为目标平台之前，请将数据迁移到与分区存储兼容的目录。在大多数情况下，您可以将数据迁移到您的应用专用目录。
//如果您有需要迁移的数据，当用户升级到以 Android 11 为目标平台的新版应用时，可以保留旧版存储模型。这样，用户就可以保留对您的应用之前用于保存数据的目录中存储的应用数据的访问权限。如需启用旧版存储模型以进行升级，请在应用的清单中将 preserveLegacyExternalStorage 属性设为 true。
//注意：大多数应用都不需要使用 preserveLegacyExternalStorage。此标记仅适用于这样一种情况：您将应用数据迁移到了与分区存储兼容的位置，并且希望用户在更新您的应用时保留对数据的访问权限。使用此标记会导致更难以测试分区存储对您应用的用户有何影响，因为当用户更新您的应用时，它会继续使用旧版存储模型。
//如果您使用 preserveLegacyExternalStorage，旧版存储模型只在用户卸载您的应用之前保持有效。如果用户在搭载 Android 11 的设备上安装或重新安装您的应用，那么无论 preserveLegacyExternalStorage 的值是什么，您的应用都无法停用分区存储模型。
//测试分区存储
//如需在您的应用中启用分区存储，而不考虑应用的目标 SDK 版本和清单标记值，请启用以下应用兼容性标记：
//DEFAULT_SCOPED_STORAGE（默认情况下，对所有应用处于启用状态）
//FORCE_ENABLE_SCOPED_STORAGE（默认情况下，对所有应用处于停用状态）
//如需停用分区存储而改用旧版存储模型，请取消设置这两个标记。
//管理设备存储空间
//在 Android 11 上，使用分区存储模型的应用只能访问自身的应用专用缓存文件。如果您的应用需要管理设备存储空间，请执行以下操作：
//通过调用 ACTION_MANAGE_STORAGE intent 操作检查可用空间。
//如果设备上的可用空间不足，请提示用户同意让您的应用清除所有缓存。为此，请调用 ACTION_CLEAR_APP_CACHE intent 操作。
//注意：ACTION_CLEAR_APP_CACHE intent 操作会严重影响设备的电池续航时间，并且可能会从设备上移除大量的文件。
//外部存储设备上的应用专用目录
//在 Android 11 上，应用无法在外部存储设备上创建自己的应用专用目录。如需访问系统为您的应用提供的目录，请调用 getExternalFilesDirs()。
//媒体文件访问权限
//为了在保证用户隐私的同时可以更轻松地访问媒体，Android 11 增加了以下功能。
//执行批量操作
//为实现各种设备之间的一致性并增加用户便利性，Android 11 向 MediaStore API 中添加了多种方法。对于希望简化特定媒体文件更改流程（例如在原位置编辑照片）的应用而言，这些方法尤为有用。
//添加的方法如下：
//createWriteRequest()
//用户向应用授予对指定媒体文件组的写入访问权限的请求。
//createFavoriteRequest()
//用户将设备上指定的媒体文件标记为“收藏”的请求。对该文件具有读取访问权限的任何应用都可以看到用户已将该文件标记为“收藏”。
//createTrashRequest()
//用户将指定的媒体文件放入设备垃圾箱的请求。垃圾箱中的内容会在系统定义的时间段后被永久删除。
//注意：如果您的应用是设备 OEM 的预安装图库应用，您可以将文件放入垃圾箱而不显示对话框。如需执行该操作，请直接将 IS_TRASHED 设置为 1。
//createDeleteRequest()
//用户立即永久删除指定的媒体文件（而不是先将其放入垃圾箱）的请求。
//系统在调用以上任何一个方法后，会构建一个 PendingIntent 对象。应用调用此 intent 后，用户会看到一个对话框，请求用户同意应用更新或删除指定的媒体文件。
//例如，以下是构建 createWriteRequest() 调用的方法：
//KOTLIN
//JAVA
//List<Uri> urisToModify = /* A collection of content URIs to modify. */
//PendingIntent editPendingIntent = MediaStore.createWriteRequest(contentResolver,
//urisToModify);
//// Launch a system prompt requesting user permission for the operation.
//startIntentSenderForResult(editPendingIntent.getIntentSender(),
//EDIT_REQUEST_CODE, null, 0, 0, 0);
//评估用户的响应，然后继续操作，或者在用户不同意时向用户说明您的应用为何需要获取权限：
//KOTLIN
//JAVA
//@Override
//protected void onActivityResult(int requestCode, int resultCode,
//@Nullable Intent data) {
//...
//if (requestCode == EDIT_REQUEST_CODE) {
//if (resultCode == Activity.RESULT_OK) {
///* Edit request granted; proceed. */
//} else {
///* Edit request not granted; explain to the user. */
//}
//}
//}
//您可以对 createFavoriteRequest()、createTrashRequest() 和 createDeleteRequest() 使用相同的通用模式。
//使用直接文件路径和原生库访问文件
//为了帮助您的应用更顺畅地使用第三方媒体库，Android 11 允许您使用除 MediaStore API 之外的 API 访问共享存储空间中的媒体文件。不过，您也可以转而选择使用以下任一 API 直接访问媒体文件：
//File API。
//原生库，例如 fopen()。
//如果您的应用没有任何存储权限，您可以使用直接文件路径访问归因于您的应用的媒体文件。如果您的应用具有 READ_EXTERNAL_STORAGE 权限，则可以使用直接文件路径访问所有媒体文件，无论这些文件是否归因于您的应用。
//如果您直接访问媒体文件，建议您在应用的清单文件中将 requestLegacyExternalStorage 设置为 true 以停用分区存储。这样，您的应用就可以在搭载 Android 10 的设备上正常工作。
//性能
//当您使用直接文件路径依序读取媒体文件时，其性能与 MediaStore API 相当。
//但是，当您使用直接文件路径随机读取和写入媒体文件时，进程的速度可能最多会慢一倍。在此类情况下，我们建议您改为使用 MediaStore API。
//媒体库中的可用值
//当您访问现有媒体文件时，您可以使用您的逻辑中 DATA 列的值。这是因为，此值包含有效的文件路径。但是，不要假设文件始终可用。请准备好处理可能发生的任何基于文件的 I/O 错误。
//另一方面，如需创建或更新媒体文件，请勿使用 DATA 列的值。请改用 DISPLAY_NAME 和 RELATIVE_PATH 列的值。
//访问其他应用中的数据
//为了保护用户隐私，Android 11 进一步限制您的应用访问其他应用的私有目录。
//访问内部存储设备上的数据目录
//变更详情
//如何切换
//变更名称：APP_DATA_DIRECTORY_ISOLATION
//变更 ID：143937733
//Android 9（API 级别 28）开始限制哪些应用可使其内部存储设备上数据目录中的文件可由其他应用进行全局访问。以 Android 9 或更高版本为目标平台的应用不能使其数据目录中的文件全局可访问。
//Android 11 在此限制的基础上进行了扩展。如果您的应用以 Android 11 为目标平台，则不能访问其他任何应用的数据目录中的文件，即使其他应用以 Android 8.1（API 级别 27）或更低版本为目标平台且已使其数据目录中的文件全局可读也是如此。
//访问外部存储设备上的应用专用目录
//在 Android 11 上，应用无法再访问外部存储设备中的任何其他应用的专用于特定应用的目录中的文件。
//文档访问限制
//为让开发者有时间进行测试，以下与存储访问框架 (SAF) 相关的变更只有在应用以 Android 11 为目标平台时才会生效。
//访问目录
//您无法再使用 ACTION_OPEN_DOCUMENT_TREE intent 操作请求访问以下目录：
//内部存储卷的根目录。
//设备制造商认为可靠的各个 SD 卡卷的根目录，无论该卡是模拟卡还是可移除的卡。可靠的卷是指应用在大多数情况下可以成功访问的卷。
//Download 目录。
//访问文件
//您无法再使用 ACTION_OPEN_DOCUMENT_TREE 或 ACTION_OPEN_DOCUMENT intent 操作请求用户从以下目录中选择单独的文件：
//Android/data/ 目录及其所有子目录。
//Android/obb/ 目录及其所有子目录。
//测试变更
//如需测试此行为更改，请执行以下操作：
//通过 ACTION_OPEN_DOCUMENT 操作调用 intent。检查 Android/data/ 和 Android/obb/ 目录是否均不显示。
//执行以下某项操作：
//启用 RESTRICT_STORAGE_ACCESS_FRAMEWORK 应用兼容性标记。
//以 Android 11 为目标平台。
//通过 ACTION_OPEN_DOCUMENT_TREE 操作调用 intent。检查 Download 目录是否已显示，以及与目录关联的操作按钮是否呈灰显状态。
//权限
//Android 11 引入了与存储权限相关的以下变更。
//以任何版本为目标平台
//第一个对话框中显示了一个名为“在‘设置’中允许”的链接
//图 1. 应用使用分区存储并请求 READ_EXTERNAL_STORAGE 权限时显示的对话框。
//不管应用的目标 SDK 版本是什么，以下变更均会在 Android 11 中生效：
//存储运行时权限已重命名为文件和媒体。
//如果您的应用未停用分区存储并且请求 READ_EXTERNAL_STORAGE 权限，用户会看到不同于 Android 10 的对话框。该对话框表明您的应用正在请求访问照片和媒体，如图 1 所示。
//用户可以在系统设置中查看哪些应用具有 READ_EXTERNAL_STORAGE 权限。在设置 > 隐私 > 权限管理器 > 文件和媒体页面上，具有该权限的每个应用都列在允许存储所有文件下。
//注意：如果您的应用以 Android 11 为目标平台，请记住，对“所有文件”的这种访问权限是只读访问权限。如需使用此应用读取和写入共享的存储空间中的所有文件，需要具有所有文件访问权限。
//以 Android 11 为目标平台
//如果应用以 Android 11 为目标平台，那么 WRITE_EXTERNAL_STORAGE 权限和 WRITE_MEDIA_STORAGE 特许权限将不再提供任何其他访问权限。
//请注意，在搭载 Android 10（API 级别 29）或更高版本的设备上，您的应用可以提供明确定义的媒体集合，例如 MediaStore.Downloads，而无需请求任何存储相关权限。详细了解如何在处理应用中的媒体文件时仅请求必要的权限。
//所有文件访问权限
//绝大多数需要共享存储空间访问权限的应用都可以遵循分区存储最佳做法，例如存储访问框架或 MediaStore API。但是，某些应用的核心用例需要广泛访问设备上的文件，但无法采用注重隐私保护的存储最佳做法高效地完成这些操作。
//例如，防病毒应用的主要用例可能需要定期扫描不同目录中的许多文件。如果此扫描需要反复的用户交互，让其使用系统文件选择器选择目录，可能就会带来糟糕的用户体验。其他用例（如文件管理器应用、备份和恢复应用以及文档管理应用）可能也需要考虑类似情况。
//应用可通过执行以下操作，向用户请求名为“所有文件访问权限”的特殊应用访问权限：
//在清单中声明 MANAGE_EXTERNAL_STORAGE 权限。
//使用 ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION intent 操作将用户引导至一个系统设置页面，在该页面上，用户可以为您的应用启用以下选项：授予所有文件的管理权限。
//如需确定您的应用是否已获得 MANAGE_EXTERNAL_STORAGE 权限，请调用 Environment.isExternalStorageManager()。
//MANAGE_EXTERNAL_STORAGE 权限会授予以下权限：
//对共享存储空间中的所有文件的读写访问权限。
//注意：/sdcard/Android/media⁠ 目录是共享存储空间的一部分。
//对 MediaStore.Files 表的内容的访问权限。
//对 USB On-The-Go (OTG) 驱动器和 SD 卡的根目录的访问权限。
//除 /Android/data/、/sdcard/Android 和 /sdcard/Android 的大多数子目录外，对所有内部存储目录⁠的写入权限。此写入权限包括文件路径访问权限。
//获得此权限的应用仍然无法访问属于其他应用的应用专用目录，因为这些目录在存储卷上显示为 Android/data/ 的子目录。
//当应用具有 MANAGE_EXTERNAL_STORAGE 权限时，它可以使用 MediaStore API 或文件路径访问这些额外的文件和目录。但是，当您使用存储访问框架时，只有在您不具备 MANAGE_EXTERNAL_STORAGE 权限也能访问文件或目录的情况下才能访问文件或目录。
//为测试启用
//如需了解“所有文件访问权限”这项权限对您的应用有何影响，您可以为了测试目的启用该权限。为此，请在连接到测试设备的计算机上运行以下命令：
//adb shell appops set --uid PACKAGE_NAME MANAGE_EXTERNAL_STORAGE allow
//为了限制对共享存储的广泛访问，Google Play 商店已更新其政策，用来评估以 Android 11 为目标平台且通过 MANAGE_EXTERNAL_STORAGE 权限请求“所有文件访问权限”的应用。
//仅当您的应用无法有效利用更有利于保护隐私的 API（如存储访问框架或 Media Store API）时，您才能请求 MANAGE_EXTERNAL_STORAGE 权限。此外，应用对此权限的使用必须在允许的使用情形范围内，并且必须与应用的核心功能直接相关。如果您的应用包含与以下示例类似的用例，很可能允许您的应用请求 MANAGE_EXTERNAL_STORAGE 权限：
//在 2021 年初之前，以 Android 11 为目标平台且需要 MANAGE_EXTERNAL_STORAGE 权限的应用无法上传到 Google Play。这包括新应用以及现有应用的更新。如需了解详情，请阅读政策帮助中心内更新后的政策。
//注意：只有在您的应用以 Android 11 为目标平台且请求 MANAGE_EXTERNAL_STORAGE 权限时，此上传限制才会对其产生影响。
//目前，如果您认为自己的应用需要管理外部存储权限，建议您暂时不要将目标 SDK 更新为 Android 11。如果您的应用以 Android 10 为目标平台，不妨考虑使用 requestLegacyExternalStorage 标记。

