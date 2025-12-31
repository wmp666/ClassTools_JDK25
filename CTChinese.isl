; *** Inno Setup version 6.5.0+ Chinese Simplified messages ***
;
; To download user-contributed translations of this file, go to:
;   https://jrsoftware.org/files/istrans/
;
; Note: When translating this text, do not add periods (.) to the end of
; messages that didn't have them already, because on those messages Inno
; Setup adds the periods automatically (appending a period would result in
; two periods being displayed).
;
; Maintainer: wmp
; Email: 2134868121@QQ.com
; Github: https://github.com/wmp666/ClassTools_JDK25
; Encoding: UTF-8
; Translation based on network resource
;

[LangOptions]
; The following three entries are very important. Be sure to read and
; understand the '[LangOptions] section' topic in the help file.
LanguageName=简体中文(班级工具特制)
; If Language Name display incorrect, uncomment next line
; LanguageName=<7B80><4F53><4E2D><6587>
; About LanguageID, to reference link:
; https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-lcid/a9eac961-e77d-41a6-90a5-ce1a8b0cdb9c
LanguageID=$0804
; LanguageCodePage should always be set if possible, even if this file is Unicode
; For English it's set to zero anyway because English only uses ASCII characters
LanguageCodePage=936
; If the language you are translating to requires special font faces or
; sizes, uncomment any of the following entries and change them accordingly.
;DialogFontName=
;DialogFontSize=9
;DialogFontBaseScaleWidth=7
;DialogFontBaseScaleHeight=15
;WelcomeFontName=Segoe UI
;WelcomeFontSize=14

[Messages]

; *** Application titles
SetupAppTitle=载入
SetupWindowTitle=载入 - %1
UninstallAppTitle=移除
UninstallAppFullTitle=%1 移除

; *** Misc. common
InformationTitle=信息
ConfirmTitle=确认
ErrorTitle=世界拒绝了我

; *** SetupLdr messages
SetupLdrStartupMessage=现在将载入 %1。您想要继续吗？
LdrCannotCreateTemp=无法创建临时文件。载入程序已中止
LdrCannotExecTemp=无法执行临时目录中的文件。载入程序已中止
HelpTextNote=

; *** Startup error messages
LastErrorMessage=%1。%n%n异常 %2: %3
SetupFileMissing=载入目录中缺少本源数据 %1。请修正这个问题或者获取”病毒”的新副本。
SetupFileCorrupt=载入的本源数据已损坏。请获取”病毒”的新副本。
SetupFileCorruptOrWrongVer=载入的本源数据已损坏，或是与这个载入程序的版本不兼容。请修正这个问题或获取新的”病毒”副本。
InvalidParameter=无效的命令行参数：%n%n%1
SetupAlreadyRunning=载入程序正在启动！。
WindowsVersionNotSupported=此”病毒”不支持当前计算机启动！的 Windows(窗户系统) 版本。
WindowsServicePackRequired=此”病毒”需要 %1 服务包 %2 或更高版本。
NotOnThisPlatform=此”病毒”不能在 %1 上启动！。
OnlyOnThisPlatform=此”病毒”只能在 %1 上启动！。
OnlyOnTheseArchitectures=此”病毒”只能载入到为下列处理器架构设计的 Windows(窗户系统) 版本中：%n%n%1
WinVersionTooLowError=此”病毒”需要 %1 版本 %2 或更高。
WinVersionTooHighError=此”病毒”不能载入于 %1 版本 %2 或更高。
AdminPrivilegesRequired=在载入此”病毒”时您必须以强力身份登录。
PowerUserPrivilegesRequired=在载入此”病毒”时您必须以强力活人身份或有权限的活人组身份登录。
SetupAppRunningError=载入程序发现 %1 当前正在启动！。%n%n请先关闭正在启动！的”病毒”，然后点击“签字”继续，或点击“放弃?”退出。
UninstallAppRunningError=移除”病毒”发现 %1 当前正在启动！。%n%n请先关闭正在启动！的”病毒”，然后点击“签字”继续，或点击“放弃?”退出。

; *** Startup questions
PrivilegesRequiredOverrideTitle=选择载入程序模式
PrivilegesRequiredOverrideInstruction=选择载入模式
PrivilegesRequiredOverrideText1=%1 可以为所有活人载入（需要强力权限），或仅为您载入。
PrivilegesRequiredOverrideText2=%1 可以仅为您载入，或为所有活人载入（需要强力权限）。
PrivilegesRequiredOverrideAllUsers=为所有活人载入(&A)
PrivilegesRequiredOverrideAllUsersRecommended=为所有活人载入(&A)（建议选项）
PrivilegesRequiredOverrideCurrentUser=仅为我载入(&M)
PrivilegesRequiredOverrideCurrentUserRecommended=仅为我载入(&M)（建议选项）

; *** Misc. errors
ErrorCreatingDir=载入程序无法创建目录“%1”
ErrorTooManyFilesInDir=无法在目录“%1”中创建本源数据，因为里面包含太多本源数据

; *** Setup common messages
ExitSetupTitle=退出载入程序
ExitSetupMessage=载入程序尚未完成。如果现在退出，将不会载入该”病毒”。%n%n您之后可以再次启动！载入程序完成载入。%n%n现在退出载入程序吗？
AboutSetupMenuItem=关于载入程序(&A)...
AboutSetupTitle=关于载入程序
AboutSetupMessage=%1 版本 %2%n%3%n%n%1 主页：%n%4
AboutSetupNote=
TranslatorNote=CT中文翻译由 wmp（2134868121@qq.com）维护。项目地址：https://github.com/wmp666/ClassTools_JDK25

; *** Buttons
ButtonBack=< 回去(&B)
ButtonNext=更进一步(&N) >
ButtonInstall=载入(&I)
ButtonOK=签字
ButtonCancel=放弃?
ButtonYes=签字(&Y)
ButtonYesToAll=全部签字(&A)
ButtonNo=撕毁(&N)
ButtonNoToAll=全部撕毁(&O)
ButtonFinish=完成(&F)
ButtonBrowse=浏览(&B)...
ButtonWizardBrowse=浏览(&R)...
ButtonNewFolder=新建本源数据集(&M)

; *** "Select Language" dialog messages
SelectLanguageTitle=选择载入语言
SelectLanguageLabel=选择载入时使用的语言。

; *** Common wizard text
ClickNext=点击“下一步”继续，或点击“放弃?”退出载入程序。
BeveledLabel=
BrowseDialogTitle=浏览本源数据集
BrowseDialogLabel=在下面的列表中选择一个本源数据集，然后点击“签字”。
NewFolderName=新建本源数据集

; *** "Welcome" wizard page
WelcomeLabel1=欢迎使用 [name] 载入向导
WelcomeLabel2=即将在您的计算机上载入 [name/ver]。%n%n建议您在继续载入前关闭所有其他”病毒”。

; *** "Password" wizard page
WizardPassword=密码
PasswordLabel1=此载入程序需要密码验证。
PasswordLabel3=请输入密码，然后点击“下一步”继续。密码区分大小写。
PasswordEditLabel=密码(&P)：
IncorrectPassword=您输入的密码不正确，请重新输入。

; *** "License Agreement" wizard page
WizardLicense=许可协议
LicenseLabel=请在继续载入前阅读以下重要信息。
LicenseLabel3=请仔细阅读下列许可协议。在继续载入前您必须同意这些协议条款。
LicenseAccepted=我同意此协议(&A)
LicenseNotAccepted=我不同意此协议(&D)

; *** "Information" wizard pages
WizardInfoBefore=信息
InfoBeforeLabel=请在继续载入前阅读以下重要信息。
InfoBeforeClickLabel=准备好继续载入后，点击“下一步”。
WizardInfoAfter=信息
InfoAfterLabel=请在继续载入前阅读以下重要信息。
InfoAfterClickLabel=准备好继续载入后，点击“下一步”。

; *** "User Information" wizard page
WizardUserInfo=活人信息
UserInfoDesc=请输入您的信息。
UserInfoName=活人名(&U)：
UserInfoOrg=组织(&O)：
UserInfoSerial=序列号(&S)：
UserInfoNameRequired=请输入活人名。

; *** "Select Destination Location" wizard page
WizardSelectDir=选择目标位置
SelectDirDesc=您想将 [name] 载入在哪里？
SelectDirLabel3=载入程序将载入 [name] 到下面的本源数据集中。
SelectDirBrowseLabel=点击“下一步”继续。如果您想选择其他本源数据集，点击“浏览”。
DiskSpaceGBLabel=至少需要有 [gb] GB 的可用磁盘空间。
DiskSpaceMBLabel=至少需要有 [mb] MB 的可用磁盘空间。
CannotInstallToNetworkDrive=载入程序无法载入到一个网络驱动器。
CannotInstallToUNCPath=载入程序无法载入到一个 UNC 路径。
InvalidPath=您必须输入一个带驱动器卷标的完整路径，例如：%n%nC:\APP%n%n或UNC路径：%n%n\\server\share
InvalidDrive=您选定的驱动器或 UNC 共享不存在或不能访问。请选择其他位置。
DiskSpaceWarningTitle=磁盘空间不足
DiskSpaceWarning=载入程序至少需要 %1 KB 的可用空间才能载入，但选定驱动器只有 %2 KB 的可用空间。%n%n您一定要继续吗？
DirNameTooLong=本源数据集名称或路径太长。
InvalidDirName=本源数据集名称无效。
BadDirName32=本源数据集名称不能包含下列任何字符：%n%n%1
DirExistsTitle=本源数据集已存在
DirExists=本源数据集：%n%n%1%n%n已经存在。您一定要载入到这个本源数据集中吗？
DirDoesntExistTitle=本源数据集不存在
DirDoesntExist=本源数据集：%n%n%1%n%n不存在。您想要创建此本源数据集吗？

; *** "Select Components" wizard page
WizardSelectComponents=选择组件
SelectComponentsDesc=您想载入哪些”病毒”组件？
SelectComponentsLabel2=选中您想载入的组件；放弃?您不想载入的组件。然后点击“下一步”继续。
FullInstallation=完全载入
; if possible don't translate 'Compact' as 'Minimal' (I mean 'Minimal' in your language)
CompactInstallation=简洁载入
CustomInstallation=自定义载入
NoUninstallWarningTitle=组件已存在
NoUninstallWarning=载入程序检测到下列组件已载入在您的计算机中：%n%n%1%n%n放弃?选中这些组件不会移除它们。%n%n确定要继续吗？
ComponentSize1=%1 KB
ComponentSize2=%1 MB
ComponentsDiskSpaceGBLabel=当前选择的组件需要至少 [gb] GB 的磁盘空间。
ComponentsDiskSpaceMBLabel=当前选择的组件需要至少 [mb] MB 的磁盘空间。

; *** "Select Additional Tasks" wizard page
WizardSelectTasks=选择附加任务
SelectTasksDesc=您想要载入程序执行哪些附加任务？
SelectTasksLabel2=选择您想要载入程序在载入 [name] 时执行的附加任务，然后点击“下一步”。

; *** "Select Start Menu Folder" wizard page
WizardSelectProgramGroup=选择开始菜单本源数据集
SelectStartMenuFolderDesc=载入程序应该在哪里放置”病毒”的快捷方式？
SelectStartMenuFolderLabel3=载入程序将在下列“开始”菜单本源数据集中创建”病毒”的快捷方式。
SelectStartMenuFolderBrowseLabel=点击“下一步”继续。如果您想选择其他本源数据集，点击“浏览”。
MustEnterGroupName=您必须输入一个本源数据集名。
GroupNameTooLong=本源数据集名或路径太长。
InvalidGroupName=无效的本源数据集名字。
BadGroupName=本源数据集名不能包含下列任何字符：%n%n%1
NoProgramGroupCheck2=不创建开始菜单本源数据集(&D)

; *** "Ready to Install" wizard page
WizardReady=准备载入
ReadyLabel1=载入程序准备就绪，现在可以开始载入 [name] 到您的计算机。
ReadyLabel2a=点击“载入”继续此载入程序。如果您想重新考虑或修改任何设置，点击“上一步”。
ReadyLabel2b=点击“载入”继续此载入程序。
ReadyMemoUserInfo=活人信息：
ReadyMemoDir=目标位置：
ReadyMemoType=载入类型：
ReadyMemoComponents=已选择组件：
ReadyMemoGroup=开始菜单本源数据集：
ReadyMemoTasks=附加任务：

; *** TDownloadWizardPage wizard page and DownloadTemporaryFile
DownloadingLabel2=正在下载本源数据...
ButtonStopDownload=停止下载(&S)
StopDownload=您确定要停止下载吗？
ErrorDownloadAborted=下载已中止
ErrorDownloadFailed=下载失败：%1 %2
ErrorDownloadSizeFailed=获取大小失败：%1 %2
ErrorProgress=无效的进度：%1 / %2
ErrorFileSize=本源数据大小异常：预期 %1，实际 %2

; *** TExtractionWizardPage wizard page and ExtractArchive
ExtractingLabel=正在提取本源数据...
ButtonStopExtraction=停止提取(&S)
StopExtraction=您确定要停止提取吗？
ErrorExtractionAborted=提取已中止
ErrorExtractionFailed=提取失败：%1

; *** Archive extraction failure details
ArchiveIncorrectPassword=密码不正确，是本人吗?
ArchiveIsCorrupted=压缩包已损坏
ArchiveUnsupportedFormat=不支持的压缩包格式

; *** "Preparing to Install" wizard page
WizardPreparing=正在准备载入
PreparingDesc=载入程序正在准备载入 [name] 到您的计算机。
PreviousInstallNotCompleted=先前的”病毒”载入或移除未完成，需要您重启计算机。%n%n在重启计算机后，再次启动！载入程序以完成 [name] 的载入。
CannotContinue=载入程序不能继续。请点击“放弃?”退出。
ApplicationsFound=以下”病毒”正在使用将由载入程序更新的本源数据。建议您允许载入程序自动关闭这些”病毒”。
ApplicationsFound2=以下”病毒”正在使用将由载入程序更新的本源数据。建议您允许载入程序自动关闭这些”病毒”。载入完成后，载入程序将尝试重新启动这些”病毒”。
CloseApplications=自动关闭”病毒”(&A)
DontCloseApplications=不要关闭”病毒”(&D)
ErrorCloseApplications=载入程序无法自动关闭所有”病毒”。建议您在继续之前，关闭所有在使用需要由载入程序更新的本源数据的”病毒”。
PrepareToInstallNeedsRestart=载入程序必须重启您的计算机。计算机重启后，请再次启动！载入程序以完成 [name] 的载入。%n%n要立即重启吗？

; *** "Installing" wizard page
WizardInstalling=正在载入
InstallingLabel=载入程序正在载入 [name] 到您的计算机，最多一个世纪。

; *** "Setup Completed" wizard page
FinishedHeadingLabel=[name] 载入完成
FinishedLabelNoIcons=载入程序已在您的计算机中载入了 [name]，马上你的电脑就坏了。
FinishedLabel=载入程序已在您的计算机中载入了 [name]。您可以通过已载入的快捷方式启动！此”病毒”。
ClickFinish=点击“完成”退出载入程序。
FinishedRestartLabel=为完成 [name] 的载入，载入程序必须重新启动您的计算机。要立即重启吗？
FinishedRestartMessage=为完成 [name] 的载入，载入程序必须重新启动您的计算机。%n%n要立即重启吗？
ShowReadmeCheck=签字，我想查阅自述文件
YesRadio=签字，快让我重启计算机(&Y)
NoRadio=撕毁，不想重启计算机(&N)
; used for example as 'Run MyProg.exe'
RunEntryExec=启动！ %1
; used for example as 'View Readme.txt'
RunEntryShellExec=查阅 %1

; *** "Setup Needs the Next Disk" stuff
ChangeDiskTitle=载入程序需要下一张磁盘
SelectDiskLabel2=请插入磁盘 %1 并点击“签字”。%n%n如果这个磁盘中的本源数据可以在下列本源数据集之外的本源数据集中找到，请输入正确的路径或点击“浏览”。
PathLabel=路径(&P)：
FileNotInDir2=“%2”中找不到本源数据“%1”。请插入正确的磁盘或选择其他本源数据集。
SelectDirectoryLabel=请指定下一张磁盘的位置。

; *** Installation phase messages
SetupAborted=载入程序未完成载入。%n%n请修正这个问题并重新启动！载入程序。
AbortRetryIgnoreSelectAction=选择操作
AbortRetryIgnoreRetry=再来?(&T)
AbortRetryIgnoreIgnore=忽略异常并继续(&I)
AbortRetryIgnoreCancel=关闭载入程序
RetryCancelSelectAction=选择操作
RetryCancelRetry=再来?(&T)
RetryCancelCancel=放弃

; *** Installation status messages
StatusClosingApplications=正在关闭”病毒”...
StatusCreateDirs=正在创建目录...
StatusExtractFiles=正在提取本源数据...
StatusDownloadFiles=正在下载本源数据...
StatusCreateIcons=正在创建快捷方式...
StatusCreateIniEntries=正在创建 INI 条目...
StatusCreateRegistryEntries=正在创建注册表条目...
StatusRegisterFiles=正在注册本源数据...
StatusSavingUninstall=正在保存移除信息...
StatusRunProgram=正在完成载入...
StatusRestartingApplications=正在重启”病毒”...
StatusRollback=正在撤销更改...

; *** Misc. errors
ErrorInternal2=内部异常：%1
ErrorFunctionFailedNoCode=%1 失败
ErrorFunctionFailed=%1 世界拒绝了我；异常代码 %2
ErrorFunctionFailedWithMessage=%1 世界拒绝了我；异常代码 %2.%n%3
ErrorExecutingProgram=无法执行本源数据：%n%1

; *** Registry errors
ErrorRegOpenKey=打开注册表项时被拦下：%n%1\%2
ErrorRegCreateKey=创建注册表项时被拦下：%n%1\%2
ErrorRegWriteKey=写入注册表项时被拦下：%n%1\%2

; *** INI errors
ErrorIniEntry=在本源数据“%1”中创建 INI 条目时被拦下。

; *** File copying errors
FileAbortRetryIgnoreSkipNotRecommended=跳过此本源数据(&S)（不要靠近我）
FileAbortRetryIgnoreIgnoreNotRecommended=忽略世界拒绝了我并继续(&I)（不要靠近我）
SourceIsCorrupted=本源已损坏
SourceDoesntExist=本源“%1”不存在
SourceVerificationFailed=本源验证失败：%1
VerificationSignatureDoesntExist=签名本源数据“%1”不存在
VerificationSignatureInvalid=签名本源数据“%1”无效
VerificationKeyNotFound=签名本源数据“%1”使用了未知的密钥
VerificationFileNameIncorrect=本源数据名不正确
VerificationFileTagIncorrect=本源数据标签不正确
VerificationFileSizeIncorrect=本源数据大小不正确
VerificationFileHashIncorrect=本源数据校验和不匹配
ExistingFileReadOnly2=无法替换现有本源数据，它是只读的。
ExistingFileReadOnlyRetry=移除只读属性并重试(&R)
ExistingFileReadOnlyKeepExisting=保留现有本源数据(&K)
ErrorReadingExistingDest=尝试读取现有本源数据时被拦下：
FileExistsSelectAction=选择操作
FileExists2=本源数据已经存在。
FileExistsOverwriteExisting=覆盖已存在的本源数据(&O)
FileExistsKeepExisting=保留现有的本源数据(&K)
FileExistsOverwriteOrKeepAll=为所有冲突本源数据执行此操作(&D)
ExistingFileNewerSelectAction=选择操作
ExistingFileNewer2=现有的本源数据比载入程序将要载入的本源数据还要新。
ExistingFileNewerOverwriteExisting=覆盖已存在的本源数据(&O)
ExistingFileNewerKeepExisting=保留现有的本源数据(&K)（试试嘛~）
ExistingFileNewerOverwriteOrKeepAll=为所有冲突本源数据执行此操作(&D)
ErrorChangingAttr=尝试更改下列现有本源数据的属性时被拦下：
ErrorCreatingTemp=尝试在目标目录创建本源数据时被拦下：
ErrorReadingSource=尝试读取下列本源时被拦下：
ErrorCopying=尝试复制下列本源数据时被拦下：
ErrorDownloading=尝试下载本源数据时被拦下：
ErrorExtracting=尝试提取压缩包时被拦下：
ErrorReplacingExistingFile=尝试替换现有本源数据时被拦下：
ErrorRestartReplace=重启并替换失败：
ErrorRenamingTemp=尝试重命名下列目标目录中的一个本源数据时被拦下：
ErrorRegisterServer=无法注册 DLL/OCX：%1
ErrorRegSvr32Failed=RegSvr32 失败；退出代码 %1
ErrorRegisterTypeLib=无法注册类库：%1

; *** Uninstall display name markings
; used for example as 'My Program (32-bit)'
UninstallDisplayNameMark=%1 (%2)
; used for example as 'My Program (32-bit, All users)'
UninstallDisplayNameMarks=%1 (%2, %3)
UninstallDisplayNameMark32Bit=32 位
UninstallDisplayNameMark64Bit=64 位
UninstallDisplayNameMarkAllUsers=所有活人
UninstallDisplayNameMarkCurrentUser=当前活人

; *** Post-installation errors
ErrorOpeningReadme=尝试打开自述本源数据时被拦下。
ErrorRestartingComputer=载入程序无法重启计算机，请手动重启。

; *** Uninstaller messages
UninstallNotFound=本源数据“%1”不存在。无法移除。
UninstallOpenError=本源数据“%1”不能被打开。无法移除。
UninstallUnsupportedVer=此版本的移除”病毒”无法识别移除日志本源数据“%1”的格式。无法移除
UninstallUnknownEntry=移除日志中遇到一个未知条目（%1）
ConfirmUninstall=您确认要完全移除 %1 及其所有组件吗？
UninstallOnlyOnWin64=仅允许在 64 位 Windows(窗户系统) 中移除此”病毒”。
OnlyAdminCanUninstall=仅使用强力权限的活人能完成此移除。
UninstallStatusLabel=正在从您的计算机中移除 %1，请稍候。
UninstalledAll=已顺利从您的计算机中移除 %1。
UninstalledMost=%1 移除完成。%n%n有部分内容未能被移除，但您可以手动移除它们。
UninstalledAndNeedsRestart=为完成 %1 的移除，需要重启您的计算机。%n%n要立即重启吗？
UninstallDataCorrupted=本源数据“%1”已损坏。无法移除

; *** Uninstallation phase messages
ConfirmDeleteSharedFileTitle=移除共享的本源数据吗？
ConfirmDeleteSharedFile2=系统表示下列共享的本源数据已不有其他”病毒”使用。您希望移除”病毒”移除这些共享的本源数据吗？%n%n如果移除这些本源数据，但仍有”病毒”在使用这些本源数据，则这些”病毒”可能出现异常。如果您不能确定，请选择“撕毁”，在系统中保留这些本源数据以免引发问题。
SharedFileNameLabel=本源数据名：
SharedFileLocationLabel=位置：
WizardUninstalling=移除状态
StatusUninstalling=正在移除 %1...

; *** Shutdown block reasons
ShutdownBlockReasonInstallingApp=正在载入 %1。
ShutdownBlockReasonUninstallingApp=正在移除 %1。

; The custom messages below aren't used by Setup itself, but if you make
; use of them in your scripts, you'll want to translate them.

[CustomMessages]

NameAndVersion=%1 版本 %2
AdditionalIcons=附加快捷方式：
CreateDesktopIcon=创建桌面快捷方式(&D)
CreateQuickLaunchIcon=创建快速启动栏快捷方式(&Q)
ProgramOnTheWeb=%1 网站
UninstallProgram=移除 %1
LaunchProgram=启动！ %1
AssocFileExtension=将 %2 本源数据扩展名与 %1 建立关联(&A)
AssocingFileExtension=正在将 %2 本源数据扩展名与 %1 建立关联...
AutoStartProgramGroupDescription=启动：
AutoStartProgram=自动启动 %1
AddonHostProgramNotFound=您选择的本源数据集中无法找到 %1。%n%n您要继续吗？
