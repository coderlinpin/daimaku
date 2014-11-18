原理:相对布局,左右两个view
SlidingMenu是一个库工程，使用时需要添加到当前的工程上，作为当前工程所依赖的工程来使用
为什么不达成jar？资源文件无法打成jar，R文件定义的id无法使用，所以需要做成库工程
SlidingMenu还需要ActionbarShelock作为依赖库

zip-->library--->导入工程--->菜单-->android-->isLibrary-->add-->apply

SlidingMenu和ViewPager兼容

private void initSlidingMenu() {
		//实例化一个slidingmenu
		sm = new SlidingMenu(this);
		sm.setMode(SlidingMenu.LEFT);//slidingmenu出现的屏幕左右方向
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);//设置滑出slidingmenu的地方
		sm.setShadowDrawable(R.drawable.shadow);//设置主页面和slidingmenu页面之间的阴影图片
		sm.setShadowWidthRes(R.dimen.shadow_width);//设置阴影的宽度
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);//设置主页面在slidingmenu上显示的宽度
//		sm.setBehindWidth(400);//直接设置slidingmenu的宽度
		sm.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);//将当前的slidingmenu作为activity的一部分显示
		
		sm.setMenu(R.layout.navigation_layout);//设置slidingmenu显示的layout
		new NavigationHandler(this,sm);//实现导航页的动画需要的代码量比较大，所以写在单独的类中。
	}
***********************************************	
	drawable/shadow.xml
	
	<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android" >

    <gradient
        android:endColor="#33000000"
        android:centerColor="#11000000"
        android:startColor="#00000000" />

</shape>
	************************************************
	
	shadow_width
	\res\values\dimens.xml
	
	<resources>

    <dimen name="slidingmenu_offset">60dp</dimen>
    <dimen name="list_padding">10dp</dimen>
    <dimen name="shadow_width">15dp</dimen>

    <integer name="num_cols">1</integer>
    <dimen name = "title_bar_height">50dp</dimen>
    
    <dimen name="item_img_width">100dp</dimen>
    <dimen name="padding_medium">8dp</dimen>
    <dimen name="padding_large">16dp</dimen>
    <dimen name="business_type_marging_right">6dp</dimen>
    <dimen name="mobile_line_img_margin_left">10dp</dimen>
    <dimen name="mobile_line_img_margin_right">5dp</dimen>
    <dimen name="province_list_margin_right">48.5dp</dimen>
    <dimen name="submenu_btn_bg_width">64.67dp</dimen>
    <dimen name="submenu_btn_bg_height">54.67dp</dimen>
    <dimen name="submenu_btn_img_width">25dp</dimen>
    <dimen name="submenu_btn_img_height">25dp</dimen>
    
    <dimen name="introduction_font_size">18sp</dimen>
    
    <dimen name="slidingmenu_offset">60dp</dimen>
    <dimen name="list_padding">10dp</dimen>
    <dimen name = "copy_right_width">100dp</dimen>

</resources>