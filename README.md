#软件名称：机房能耗监测
-------
###软件功能介绍：
- 机房简介：简要介绍机房的一些配置参数；
- 机房供电：介绍机房供电系统；
- 机房节能：介绍机房使用的节能技术；
- 机房PUE：实时展示机房实际的能耗比，及机房能耗的历史数据;
- 技术支持：显示厂家信息，售后信息等。
###用到的技术：
1. 布局主要用到了PercentRelativeLayout ，LinearLayout，及FrameLayout等。
2. 使用了ListView及其优化。
3. 使用了广播（BroadcastReceiver），完成开机自启。
4. 使用了SQLite数据库，并使用GreenDao管理数据库。
5. 项目中用到了自定义控件。
6. 使用了转场动画与属性动画等。
7. 使用了后台服务（Service）。
8. 使用了定时任务Timer。

###附件：
---
<li>软件主界面:
![Alt text](https://github.com/Qiyeah/PowerMonitor/blob/master/Screenshot_20170628-124920.png)
<li>机房PUE界面:
![Alt text](https://github.com/Qiyeah/PowerMonitor/blob/master/Screenshot_20170628-124858.png)
