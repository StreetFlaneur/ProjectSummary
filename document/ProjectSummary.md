### android项目开发
整理框架和部分问题的解决方案，目的是为了简化开发，节约开发时间。新项目都可以在框架基础上进行。

#### 框架
总结不同项目框架，目前主流的框架主要是一下实现方式：
1. 底部TabButton按钮 类似麦德龙和上工申贝项目

2. 左面侧滑菜单，类似麦德龙一期、唯品会（侧滑又分为左边可滑动和右边滑动）

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
- 动态权限

- 选择图片
- 

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
 Glide Picasso trans,在图片小的时候，不会自动放大显示成圆形。
 
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





