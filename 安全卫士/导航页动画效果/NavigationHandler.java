package com.itheima24.know;

import java.util.ArrayList;
import java.util.List;

import com.itheima24.know.anim.ExpandAnimation;
import com.itheima24.know.domain.ViewBean;
import com.itheima24.know.utils.BroadcastHelper;
import com.itheima24.know.utils.Constants;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
/**
 * 
 * 前提：将所有的title和对应的layout封装ViewBean中，然后将所有ViewBean放到List中
 * 
 * 1.点击任意一个title的时候，将该title对应的隐藏的layout显示出来，并将该title置为选中状态
 * 2.在点击其他title的时候，需要判断之前有没有打开过动画
 * 3.如果打开过，需要将之前打开的动画关闭，并且将之前的按下效果恢复成默认
 * 
 */
public class NavigationHandler {

	private static final String TAG = "NavigationHandler";
	private Activity activity;
	private List<ViewBean> viewList;
	private boolean allowAnim = true;
	private View lastView;
	private SlidingMenu sm;
	
	public NavigationHandler(Activity activity,SlidingMenu sm) {
		this.activity = activity;
		this.sm = sm;
		initViews();
	}

	private void initViews() {
		viewList = new ArrayList<ViewBean>();
		//1.将所有的viewTitle和viewLayout封装成ViewBean，并且用list保存
		ViewBean bean = new ViewBean();
		View view = activity.findViewById(R.id.navigation_root_view);
		//初始化营业厅
		RelativeLayout businessHallTitle = (RelativeLayout) view.findViewById(R.id.find_business_hall_title);
		final View businessHallLayout = view.findViewById(R.id.business_hall_layout);
		bean.setViewTitle(businessHallTitle);
		bean.setViewLayout(businessHallLayout);
		viewList.add(bean);
		
		//初始化国际漫游
		RelativeLayout internationalRoamingTitle = (RelativeLayout) view.findViewById(R.id.international_roaming_title);
		final View internationalRoamingLayout = view.findViewById(R.id.international_roaming_layout);
		bean = new ViewBean();
		bean.setViewTitle(internationalRoamingTitle);
		bean.setViewLayout(internationalRoamingLayout);
		viewList.add(bean);
		
		//初始化查找手机
		RelativeLayout cellPhoneTitle = (RelativeLayout) view.findViewById(R.id.find_cellphone_title);
		final View cellPhoneLayout = view.findViewById(R.id.find_cellphone_layout);
		bean = new ViewBean();
		bean.setViewTitle(cellPhoneTitle);
		bean.setViewLayout(cellPhoneLayout);
		viewList.add(bean);
		
		//初始化套餐
		RelativeLayout packageTitle = (RelativeLayout) view.findViewById(R.id.package_title);
		final View packageLayout = view.findViewById(R.id.package_layout);
		bean = new ViewBean();
		bean.setViewTitle(packageTitle);
		bean.setViewLayout(packageLayout);
		viewList.add(bean);
		
		//初始化实时话费
		RelativeLayout telephoneChargeTitle = (RelativeLayout) view.findViewById(R.id.telephone_charge_title);
		final View telephoneChargeLayout = view.findViewById(R.id.telephone_charge_layout);
		bean = new ViewBean();
		bean.setViewTitle(telephoneChargeTitle);
		bean.setViewLayout(telephoneChargeLayout);
		viewList.add(bean);
		
		//初始化拨打客服电话
		RelativeLayout customerServicePhoneTitle = (RelativeLayout) view.findViewById(R.id.call_customer_service_phone_title);
		final View customerServicePhoneLayout = view.findViewById(R.id.call_customer_service_phone_layout);
		bean = new ViewBean();
		bean.setViewTitle(customerServicePhoneTitle);
		bean.setViewLayout(customerServicePhoneLayout);
		viewList.add(bean);
		
		//初始化唐诗
		RelativeLayout tangTitle = (RelativeLayout) view.findViewById(R.id.listen_tang_poetry_title);
		final View tangPoetryLayout = view.findViewById(R.id.tang_poetry_layout);
		bean = new ViewBean();
		bean.setViewTitle(tangTitle);
		bean.setViewLayout(tangPoetryLayout);
		viewList.add(bean);
		
		//初始化笑话
		RelativeLayout jokeTitle = (RelativeLayout) view.findViewById(R.id.say_joke_title);
		final View jokeLayout = view.findViewById(R.id.jokey_layout);
		bean = new ViewBean();
		bean.setViewTitle(jokeTitle);
		bean.setViewLayout(jokeLayout);
		viewList.add(bean);
		
		//初始化美女
		RelativeLayout beautyTitle = (RelativeLayout) view.findViewById(R.id.see_beauty_title);
		final View beautyLayout = view.findViewById(R.id.beauty_layout);
		bean = new ViewBean();
		bean.setViewTitle(beautyTitle);
		bean.setViewLayout(beautyLayout);
		viewList.add(bean);
		
		//初始化应用
		RelativeLayout appTitle = (RelativeLayout) view.findViewById(R.id.find_app_title);
		final View appLayout = view.findViewById(R.id.app_layout);
		bean = new ViewBean();
		bean.setViewTitle(appTitle);
		bean.setViewLayout(appLayout);
		viewList.add(bean);
		
		setAnimation(viewList);
	}

	private void setAnimation(List<ViewBean> viewList) {
		//给title设置点击事件，但是执行动画的是title对应的layout
		for (ViewBean bean : viewList) {
			final View viewTitle = bean.getViewTitle();
			final View viewLayout = bean.getViewLayout();
			viewTitle.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//给title对应的layout执行动画的操作
					executeAnimation(viewTitle,viewLayout);
				}
			});
		}
		for (ViewBean bean : viewList) {
			LinearLayout viewLayout = (LinearLayout) bean.getViewLayout();
			int count = viewLayout.getChildCount();
			for (int i = 0; i < count; i ++) {
				viewLayout.getChildAt(i).setOnClickListener(viewLayoutClickListener);
			}
		}
	}
	private OnClickListener viewLayoutClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			TextView tv = (TextView) v;
			String text = tv.getText().toString();
			String action = Constants.NAVIGATION_STRING_ACTION;
			String key = Constants.NAVIGATION_STRING_ACTION_KEY;
			BroadcastHelper.sendBroadCast(activity, action, key, text);
			sm.toggle();//关闭slidingmenu
		}
	};
	
	/**
	 * 执行动画的view如果是隐藏状态，自动展开，展开后将其设置为可见状态
	 * 执行动画的view如果是可见状态，自动隐藏，隐藏后将其设置为不可见状态。
	 */
	private void executeAnimation(View viewTitle, View viewLayout) {
		if (allowAnim) {
			//初始化展开动画
			ExpandAnimation animation = new ExpandAnimation(viewLayout, 300);
			boolean toggle = animation.toggle();//当前动画的状态
			if (toggle) {//当前动画时打开的
				RelativeLayout title = (RelativeLayout) viewTitle;//将viewTitle强转成RelativeLayout
				ImageView img = (ImageView) title.getChildAt(0);//得到title的第0个孩子imageview
				LinearLayout ll = (LinearLayout) title.getChildAt(1);//得到第1个还是LinearLayout
				TextView tv = (TextView) ll.getChildAt(0);//拿到当前title的layout中的文本值
				ImageView arrow = (ImageView) title.getChildAt(2);//得到title的第2个孩子imageview
				setPress(img,tv,arrow);
				viewTitle.setBackgroundResource(R.drawable.business_hall_selector_pressed);//设置title背景的按下效果
				if (lastView == null) {//说明之前没有打开过动画(现在这个是打开的,看看是否有其它的也是打开的)
					lastView = viewLayout;
				} else {//说明之前执行过动画
					if(lastView == viewLayout) {//上一个执行的动画是不是自己
						lastView = null;
					} else {//上一个执行的动画不是自己
						executeAnimation(viewTitle, lastView);//将lastView执行动画
						lastView = viewLayout;//将当前执行动画的view赋给给lastView
					}
				}
			} else {//当前动画已经关闭
				for (ViewBean bean : viewList) {
					if (viewLayout == bean.getViewLayout()) {//恢复原来未点击时的状态
						RelativeLayout title = (RelativeLayout) bean.getViewTitle();
						ImageView img = (ImageView) title.getChildAt(0);//得到title的第0个孩子imageview
						LinearLayout ll = (LinearLayout) title.getChildAt(1);//得到第1个还是LinearLayout
						TextView tv = (TextView) ll.getChildAt(0);//拿到当前title的layout中的文本值
						ImageView arrow = (ImageView) title.getChildAt(2);//得到title的第2个孩子imageview
						setNormal(img,tv,arrow);
						title.setBackgroundResource(R.drawable.business_hall_selector_normall);//设置title背景的按下效果
					}
					if (lastView == viewLayout) {
						lastView = null;
					}
				}
				
			}
				//设置动画的监听
				animation.setAnimationListener(animationListener);
				//开始播放动画
				viewLayout.startAnimation(animation);
				
			} else {
				Log.d(TAG, "不能播放动画");
			}
		
		
	}
	private void setNormal(ImageView img, TextView text) {
		String str = text.getText().toString();
		if (!TextUtils.isEmpty(str)) {
			if (str.contains(activity.getString(R.string.business_hall))) {
				img.setImageResource(R.drawable.find_businesshall);
			} else if (str.contains(activity.getString(R.string.international_roaming))) {
				img.setImageResource(R.drawable.internation_roaming);
			} else if (str.contains(activity.getString(R.string.mobile_mobile))) {
				img.setImageResource(R.drawable.find_cellphone);
			} else if (str.contains(activity.getString(R.string.packge_name))) {
				img.setImageResource(R.drawable.mobile_package);
			} else if (str.contains(activity.getString(R.string.telephone_charge))) {
				img.setImageResource(R.drawable.query_fee);
			} else if (str.contains(activity.getString(R.string.service_phone_number))) {
				img.setImageResource(R.drawable.call_customer_service_telephone);
			} else if (str.contains(activity.getString(R.string.tang_poetry))) {
				img.setImageResource(R.drawable.tang_poetry);
			} else if (str.contains(activity.getString(R.string.joke))) {
				img.setImageResource(R.drawable.joke);
			} else if (str.contains(activity.getString(R.string.myapp))) {
				img.setImageResource(R.drawable.app_btn_normal);
			} else if (str.contains(activity.getString(R.string.mm))) {
				img.setImageResource(R.drawable.online_service_normal);
			}
			
		}
	}

	private void setPress(ImageView img, TextView tv) {
		String str = tv.getText().toString();
		if (!TextUtils.isEmpty(str)) {
			if (str.contains(activity.getString(R.string.business_hall))) {//设置营业厅
				img.setImageResource(R.drawable.find_businesshall_press);
			} else if (str.contains(activity.getString(R.string.international_roaming))) {//国际漫游
				img.setImageResource(R.drawable.internation_roaming_press);
			} else if (str.contains(activity.getString(R.string.mobile_mobile))) {//手机
				img.setImageResource(R.drawable.find_cellphone_press);
			} else if (str.contains(activity.getString(R.string.packge_name))) {//套餐
				img.setImageResource(R.drawable.mobile_package_press);
			} else if (str.contains(activity.getString(R.string.telephone_charge))) {//查询余额
				img.setImageResource(R.drawable.query_fee_press);
			} else if (str.contains(activity.getString(R.string.service_phone_number))) {//客户电话
				img.setImageResource(R.drawable.call_customer_service_telephone_press);
			} else if (str.contains(activity.getString(R.string.tang_poetry))) {//背唐诗
				img.setImageResource(R.drawable.tang_poetry_press);
			} else if (str.contains(activity.getString(R.string.joke))) {//讲笑话
				img.setImageResource(R.drawable.joke_press);
			} else if (str.contains(activity.getString(R.string.myapp))) {//找应用
				img.setImageResource(R.drawable.app_btn_pressed);
			} else if (str.contains(activity.getString(R.string.mm))) {//找美女
				img.setImageResource(R.drawable.online_service_pressed);
			}
		}
	}
	Animation.AnimationListener animationListener = new AnimationListener() {
		
		@Override
		public void onAnimationEnd(Animation animation) {
			allowAnim = true;
		}
		@Override
		public void onAnimationStart(Animation animation) {
			allowAnim = false;
		}
		
		@Override
		public void onAnimationRepeat(Animation animation) {
		}
	};
	
}
