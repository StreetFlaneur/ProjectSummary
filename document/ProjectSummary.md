### android项目开发
整理框架和部分问题的解决方案，目的是为了简化开发，节约开发时间。新项目都可以在框架基础上进行。

#### 框架

##### UI实现
总结不同项目框架，目前主流的UI框架主要是一下实现方式：

1. 底部TabButton按钮 类似麦德龙和上工申贝项目

2. 侧滑菜单  见项目中leftdrawer module

   三种实现方式：

   1）android drawerLayout 实现方式

   2）右边内容滑动，菜单展示。见 唯品会

   https://github.com/JustKiddingBaby/SlideMenuLayout

   3）前两种实现方式的左右两边菜单

   drawerLayout 默认设置layout_gravity  start|end  实现左右菜单布局

   SlideMenuLayout 支持左右菜单

##### 应用框架

采用mvvm进行层级划分model  view viewmodel 三层，实际项目中分为四个module

- data层（归属于m层）（retrofit rxjava okhttp gson）: 实际发出网络请求,对返回的对象进行泛型的封装。
- model层：实体模型；拼接要请求参数，获取不同逻辑需要返回的对象。
- ViewModel层: 处理视图层需要的逻辑
- View层：通过viewModel层返回的数据，进行数据展示

使用的网络请求库
- Retrofit+Okhttp+Rxjava2  上工申贝项目
- OkHttp 实现
- Volley实现
  该实现方式以前项目存在的问题：网络状况不好时，重试机制会造成接口调用两次的问题，设置重试机制不可用无效，目前没有好的解决办法。
- 上传图片实现（单张、多张、附加文本信息）

#### 图片加载开源库
Glide ：图片加载主要在于两级缓存，内存和磁盘。
Picasso


#### 网络请求数据
Retrofit+Okhttp+Rxjava2
OkHttp
Volley

#### 页面常用效果
- 沉浸式状态栏
- 标题随内容滑动，标题按钮和标题渐变
- 页面状态

#### 常用其他开源库

- butterknife

  需要放在项目里面，引用library中 View会报空指针异常


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

- 文本走马灯
  https://github.com/dalong982242260/AndroidMarqueeView
  compile 'com.dalong:marqueeview:1.0.1'

- 图片加载

- Picassa   Glide

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


#### 常见问题解决方案
- scrollview嵌套recyclerview显示不全解决办法

- 沉浸式状态栏

- 类似淘宝头条上下滚动替换

- popwindow 添加页面其他部分阴影

- 前端显示图片变形解决办法
  - 保持一定的宽高比里 常使用fixXY

- android6.0 以上，app切换到后台，修改权限，造成Application全局静态变量为空的问题

- webview添加缓存目录后，有的页面很难加载出来
  示例：

- 应用游客身份，在应用内注册登录后，重新更新页面数据

  两种情况：

  1)重新打开MainActivity （singleTask）,并实现newIntent回调函数 

  2) 关闭登录注册页面，刷新app数据

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






