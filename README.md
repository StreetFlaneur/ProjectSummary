# ProjectSummary

相关说明见https://github.com/chaozaiai/TechArticle

### android项目开发
整理框架和部分问题的解决方案，目的是为了简化开发，节约开发时间。新项目都可以在框架基础上进行。

#### 框架

##### UI实现
总结不同项目框架，目前主流的UI框架主要是一下实现方式：

1. 底部TabButton按钮   见app module

2. 侧滑菜单  见项目中leftdrawer module

   三种效果实现方式：

   1）android drawerLayout 实现方式

   2）右边内容滑动，菜单展示。见 唯品会

   https://github.com/JustKiddingBaby/SlideMenuLayout

   3）前两种实现方式的左右两边菜单

   drawerLayout 默认设置layout_gravity  start|end  实现左右菜单布局

   SlideMenuLayout 支持左右菜单

##### 应用框架

采用mvvm进行层级划分model  view viewmodel 三层，实际项目中分为四个module。
存在的问题：会有很多的重复代码，创建很多的activity fragment 继承RxBase  有很多类（实际直接使用HttpMethods 可以直接获取数据，由datadomain层有重新封装一次）。如何减少这些代码量，还没找到解决办法。

- data层（归属于m层）（retrofit rxjava okhttp gson）: 实际发出网络请求,对返回的对象进行泛型的封装。
- model层：实体模型；拼接要请求参数，获取不同逻辑需要返回的对象。
- ViewModel层: 处理视图层需要的逻辑
- View层：通过viewModel层返回的数据，进行数据展示

使用的网络请求库
- Retrofit+Okhttp+Rxjava2  见data module

- OkHttp  见data module OkHttpManager

- Volley  存在自动重试机制，弃用

  **该实现方式以前项目存在的问题：网络状况不好时，重试机制会造成接口调用两次的问题，设置重试机制不可用无效，目前没有好的解决办法。**

- 上传图片实现（单张、多张、附加文本信息）
  1)retrofit okhttp 多媒体提交 multi 没有附带多余文本信息
```
@Multipart
    @POST("showCircle/photoUpload")
    Observable<HttpResult<PhotoItem>> photoUpload(
            @Part MultipartBody.Part part
    );
Observable<PhotoItem> photoUpload(MultipartBody.Part part, ProgressListener listener) {
        initUploadService(listener);
        return uploadApiService.photoUpload(part)
                .compose(RxUtils.<HttpResult<PhotoItem>>rxSchedulerHelper())
                .map(new HttpResultFunctionObject<PhotoItem>())
                .onErrorResumeNext(new HttpExceptionFunc<PhotoItem>());
    }
```
2)使用base64 post方法提交，见BitmapUtils 类

#### 图片加载开源库
Glide ：图片加载主要在于两级缓存，内存和磁盘。
Picasso


#### 网络请求数据
Retrofit+Okhttp+Rxjava2
OkHttp
Volley

#### 常用其他开源库

- butterknife
  需要放在项目model里面，引用library中 View会报空指针异常

- 动态权限
  https://github.com/googlesamples/easypermissions

- 选择图片
  https://github.com/crazycodeboy/TakePhoto

- recyclerview侧滑删除
  https://github.com/yanzhenjie/SwipeRecyclerView

- tab滑动切换和点击切换
  https://github.com/H07000223/FlycoTabLayout
  类似微信底部Tab显示消息小红点，在源码基础上修改布局

- 下拉刷新和上拉加载
  https://github.com/scwang90/SmartRefreshLayout
  https://github.com/lcodecorex/TwinklingRefreshLayout

- banner轮播图
   https://github.com/youth5201314/banner
   该库存在问题，debug	模式下，页面切换会偶尔出现空白，然后恢复正常，应该是生命周期问题或者是Glide加载图片问题，没找到原因。但是测试没反馈，问qq群别人也没遇到过。
    https://github.com/saiwu-bigkoo/Android-ConvenientBanner


- 公告滚动（是否有走马灯效果）
  https://github.com/ronghao/AutoScrollTextView

- 扫码库
  直接zxinglibrary源码引入，因为需要修改页面样式。见源码

- 列表视频播放
   https://github.com/lipangit/JiaoZiVideoPlayer
   实现列表播放，并在同一个activity或者fragment切换全屏

- 文本走马灯
  https://github.com/dalong982242260/AndroidMarqueeView
  compile 'com.dalong:marqueeview:1.0.1'

- 图片加载
  Picassa   Glide

- 头像圆形图片
  尝试了fresco:fresco
   compile 'de.hdodenhof:circleimageview:2.2.0'
  项目中遇到剪裁后的图片，显示总有黑色边框，不能填充圆形。
  使用android原生剪裁图片，缺少参数会造成图片有黑色边框：
  解决方案：添加有黑边的两行代码

```

  public static Intent createClipImageIntent(Uri uri, String photoPath, int width, int height) {
      Intent intent = new Intent("com.android.camera.action.CROP");
      intent.setDataAndType(uri, "image/*");
      intent.putExtra("crop", "true");
      intent.putExtra("aspectX", 1);
      intent.putExtra("aspectY", 1);
      intent.putExtra("outputX", width);
      intent.putExtra("outputY", height);
      intent.putExtra("return-data", true);
      intent.putExtra("scale", true);//黑边
      intent.putExtra("scaleUpIfNeeded", true);//黑边
      uri = Uri.fromFile(new File(photoPath));
      intent.putExtra("output", uri);
      return intent;
  }
  
```


 - 富文本显示
    compile 'com.zzhoujay.richtext:richtext:2.5.4'
    有的图片显示不正常，直接使用webview加载富文本。

- 百度地图定位+导航 见baiduditu  module
  使用最新sdk，遇到问题
  1) .so文件一直加载失败
  gralde.xml  配置如下代码，并把相关so文件复制到libs目录下。不要新建目录jinLibs.
```
repositories {
    flatDir {
        dirs 'libs'
    }
}
```
2) 地图定位图层一直不显示

- 扫码库 见zxingLibrary2
  https://github.com/fengchuiyeluo/Zxing.git

- 状态页面（网络加载成功或者失败，可点击重试）
  https://github.com/lufficc/StateLayout

- 星级评价 ratingBar 可设置星星宽高，以及星星之间边距的


#### UI切图
android单独一套图
mdpi
hdpi
xhdpi
xxhdpi
xxxhdp
m:h:xh:xxh:xxxh 比例为 1:1.5:2:3:4
如果只有IOS一倍图到三倍图的一套图，那对应分别为m/xh/xxh
举例：中等密度(medium)的屏幕你的图片像素大小为48×48，高(high)的为72×72

注意事项：
- 图片尽量不要有透明的边距,不然会造成前端设置的宽高显示不正确的情况。（因为透明边距会占去一部分宽高）
- 同样位置的图片，尽量保持一样的宽高。如下图
   ![](/images/bottom_nav.png)


#### 常见问题解决方案及常用页面效果
- 沉浸式状态栏 
1. statusbar 和toolbar 颜色保持一致
2. 页面布局头部是一张图片，statusbar和toolbar初始透明，随着页面滑动改变透明度
        ![](/images/toolbar_change.gif)
      ![](/images/white_image_head.gif)
      ![](/images/blue_image_head.gif)


- 标题随内容滑动，标题按钮和标题渐变  GradationScrollView  监听onScrollChange方法
- 页面滑动悬浮标题效果  
  见 leftdrawer module  HomeFragment
- 底部bottomsheet使用  
  1）  bottomsheet  分享菜单
  2）bottomsheetdialog  背景透明 先设置view后，再设置背景色
```
 val dialog = BottomSheetDialog(context)
        val view = View.inflate(context, R.layout.bottomsheet_dialog, null)
        dialog.setContentView(view)
        dialog.window.findViewById<View>(R.id.design_bottom_sheet)
                .setBackgroundColor(resources.getColor(R.color.transparent))
        mBehavior = BottomSheetBehavior.from(view.parent as View)
        return dialog
```
3) bottomsheetdialogfragment
onCreateDialog,设置透明背景同上

- 列表字母排序(联系人或者城市列表)  **字母标题悬浮效果还没实现**

- View 宽度高度变化动画

- 加入购物车动画

- scrollview嵌套recyclerview显示不全解决办法

- 类似淘宝头条上下滚动替换(一个或者两个同时滚动)

- popwindow 添加页面其他部分阴影

- 前端显示图片变形解决办法
  - 保持一定的宽高比例， 常使用fixXY。后台上传图片做限制。

- android6.0 以上，app切换到后台，修改权限，造成Application全局静态变量为空的问题
  解决办法，把全局变量存入文件，每次get，判断为空就从文件获取

- webview相关问题  WebViewUtils
   1） webview开启定位  LocationChromeClient
   2） webview清除缓存 
   3） 带加载进度条的webview
   添加设置缓存目录后，会导致下面链接头像图片不显示
   http://api.metro.com.cn/lpwebapptest/?openid=oJ9_hjrbjh4HosrSH0c5_Dde1t-M


- 多语言设置 7.0版本以上
  在7.0系统以后，系统语言选择已经不再是设置一种语言，而是可以设置一组语言。
  需要在BaseActivity里面设置语言，或者在每一个activity里面都设置语言。

- 图片缓存策略
  glide 虽然有两级缓存，但是应用进程被杀死后网络图片加载仍然很慢。实际图片已经缓存在本地，只是网路接口获取数据慢，造成应用打开后需要等接口返回数据后，glide才会去加载数据。有这样的延迟，所以会造成再次打开应用，图片加载很慢的现象。目前的解决办法：把图片链接缓存到本地，页面显示，直接加载缓存数据，等接口返回后，再刷新页面数据。


#### MVC MVP MVVM 比较
- MVC
  View：XML布局文件。
  Model：实体模型（数据的获取、存储、数据状态变化）。
  Controller：对应于Activity，处理数据、业务和UI。
- MVP
  View: 对应于Activity和XML，负责View的绘制以及与用户的交互。
  Model: 依然是实体模型。
  Presenter: 负责完成View与Model间的交互和业务逻辑。
- MVVM
  View: 对应于Activity和XML，负责View的绘制以及与用户交互。
  Model: 实体模型。
  ViewModel: 负责完成View与Model间的交互，负责业务逻辑。
  MVVM的目标和思想与MVP类似，利用数据绑定(Data Binding)、依赖属性(Dependency Property)、命令(Command)、路由事件(Routed Event)等新特性，打造了一个更加灵活高效的架构。

#### 常见流程
- app有游客情况，在app内部进行登录注册流程
  两种情况：

  1)重新打开MainActivity （singleTask）,并实现newIntent回调函数 

  2) 关闭登录注册页面，刷新app数据。手机猪八戒处理方法，应用首先进入首页（我的），判断没有登录，打开登录页面（包含注册按钮），注册或者登录成功后，当前页面关闭，原来页面保持不变，更新app数据。

###
其他常见问题：
- 应用ANR
  ANR的产生原因主要为以下三点：
  1.主线程View的点击事件或者触摸事件在特定的时间（5s）内无法得到响应。
  2.BroadcastReceiver的onReceive()函数运行在主线程中，在特定的时间（10s）内无法完成处理。
  3.Service的各个生命周期函数在特定时间（20s）内无法完成处理。

- app进程被杀死恢复机制







