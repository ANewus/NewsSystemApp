# NewsSystemApp
在线新闻浏览app


App的logo<br>
![image](https://github.com/ANewus/NewsSystemApp/blob/master/readme-img/logo.png)

整个程序基于android技术开发，出总体模块外主要分为“首页”、“优惠”、“更多”以及“我的”（包括一些其他功能）这四大部分。底部为一个菜单栏，可以进行四个模块的切换。<br>

“首页”部分主要有两个模块。第一个是FrameLayout是一个广告轮播模块，实现广告的自动轮播，从而赚取广告费。第二个是一个ListView，主要用来显示新闻。这新闻是对接聚合数据的实时新闻，实时更新。需要到 Volley库 进行网络请求和网络图片请求,Volley库相信很多人都都知道是谷歌官方承认的轻量级网络请求库。通过对聚合数据申请的API接口返回的Json数据进行解析，创建一个类来保存请求回来的新闻数据。再通过item的点击事件将数据传递到NewsInfoActivity中后在这个界面同样通过getIntent()的get()系列方法来获得传递过来的数据，实现点击新闻查看新闻详情。<br>
![image](https://github.com/ANewus/NewsSystemApp/blob/master/readme-img/main.png)
![image](https://github.com/ANewus/NewsSystemApp/blob/master/readme-img/ex1.png)

“优惠”部分通过一个简单的WebView，来实现淘宝优惠券的页面。<br>
![image](https://github.com/ANewus/NewsSystemApp/blob/master/readme-img/youhui.png)

“更多”部分也就是几个简单的模块。点击“扫一扫”，通过监听该模块，用户点击之后获取相机权限，从而进行扫一扫，扫描结果跳转到本地默认浏览器。<br>
![image](https://github.com/ANewus/NewsSystemApp/blob/master/readme-img/more.png)

“我的”部分，点击按钮他会监听该按钮，从而进行相对于的操作，和“更多”模块差不多。<br>
![image](https://github.com/ANewus/NewsSystemApp/blob/master/readme-img/my.png)
![image](https://github.com/ANewus/NewsSystemApp/blob/master/readme-img/about.png)<br>


登陆注册端与服务器端交互<br>
登陆界面：<br>
![image](https://github.com/ANewus/NewsSystemApp/blob/master/readme-img/login.png)

注册界面：<br>
![image](https://github.com/ANewus/NewsSystemApp/blob/master/readme-img/register.png)

