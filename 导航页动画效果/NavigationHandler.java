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
 * ǰ�᣺�����е�title�Ͷ�Ӧ��layout��װViewBean�У�Ȼ������ViewBean�ŵ�List��
 * 
 * 1.�������һ��title��ʱ�򣬽���title��Ӧ�����ص�layout��ʾ������������title��Ϊѡ��״̬
 * 2.�ڵ������title��ʱ����Ҫ�ж�֮ǰ��û�д򿪹�����
 * 3.����򿪹�����Ҫ��֮ǰ�򿪵Ķ����رգ����ҽ�֮ǰ�İ���Ч���ָ���Ĭ��
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
		//1.�����е�viewTitle��viewLayout��װ��ViewBean��������list����
		ViewBean bean = new ViewBean();
		View view = activity.findViewById(R.id.navigation_root_view);
		//��ʼ��Ӫҵ��
		RelativeLayout businessHallTitle = (RelativeLayout) view.findViewById(R.id.find_business_hall_title);
		final View businessHallLayout = view.findViewById(R.id.business_hall_layout);
		bean.setViewTitle(businessHallTitle);
		bean.setViewLayout(businessHallLayout);
		viewList.add(bean);
		
		//��ʼ����������
		RelativeLayout internationalRoamingTitle = (RelativeLayout) view.findViewById(R.id.international_roaming_title);
		final View internationalRoamingLayout = view.findViewById(R.id.international_roaming_layout);
		bean = new ViewBean();
		bean.setViewTitle(internationalRoamingTitle);
		bean.setViewLayout(internationalRoamingLayout);
		viewList.add(bean);
		
		//��ʼ�������ֻ�
		RelativeLayout cellPhoneTitle = (RelativeLayout) view.findViewById(R.id.find_cellphone_title);
		final View cellPhoneLayout = view.findViewById(R.id.find_cellphone_layout);
		bean = new ViewBean();
		bean.setViewTitle(cellPhoneTitle);
		bean.setViewLayout(cellPhoneLayout);
		viewList.add(bean);
		
		//��ʼ���ײ�
		RelativeLayout packageTitle = (RelativeLayout) view.findViewById(R.id.package_title);
		final View packageLayout = view.findViewById(R.id.package_layout);
		bean = new ViewBean();
		bean.setViewTitle(packageTitle);
		bean.setViewLayout(packageLayout);
		viewList.add(bean);
		
		//��ʼ��ʵʱ����
		RelativeLayout telephoneChargeTitle = (RelativeLayout) view.findViewById(R.id.telephone_charge_title);
		final View telephoneChargeLayout = view.findViewById(R.id.telephone_charge_layout);
		bean = new ViewBean();
		bean.setViewTitle(telephoneChargeTitle);
		bean.setViewLayout(telephoneChargeLayout);
		viewList.add(bean);
		
		//��ʼ������ͷ��绰
		RelativeLayout customerServicePhoneTitle = (RelativeLayout) view.findViewById(R.id.call_customer_service_phone_title);
		final View customerServicePhoneLayout = view.findViewById(R.id.call_customer_service_phone_layout);
		bean = new ViewBean();
		bean.setViewTitle(customerServicePhoneTitle);
		bean.setViewLayout(customerServicePhoneLayout);
		viewList.add(bean);
		
		//��ʼ����ʫ
		RelativeLayout tangTitle = (RelativeLayout) view.findViewById(R.id.listen_tang_poetry_title);
		final View tangPoetryLayout = view.findViewById(R.id.tang_poetry_layout);
		bean = new ViewBean();
		bean.setViewTitle(tangTitle);
		bean.setViewLayout(tangPoetryLayout);
		viewList.add(bean);
		
		//��ʼ��Ц��
		RelativeLayout jokeTitle = (RelativeLayout) view.findViewById(R.id.say_joke_title);
		final View jokeLayout = view.findViewById(R.id.jokey_layout);
		bean = new ViewBean();
		bean.setViewTitle(jokeTitle);
		bean.setViewLayout(jokeLayout);
		viewList.add(bean);
		
		//��ʼ����Ů
		RelativeLayout beautyTitle = (RelativeLayout) view.findViewById(R.id.see_beauty_title);
		final View beautyLayout = view.findViewById(R.id.beauty_layout);
		bean = new ViewBean();
		bean.setViewTitle(beautyTitle);
		bean.setViewLayout(beautyLayout);
		viewList.add(bean);
		
		//��ʼ��Ӧ��
		RelativeLayout appTitle = (RelativeLayout) view.findViewById(R.id.find_app_title);
		final View appLayout = view.findViewById(R.id.app_layout);
		bean = new ViewBean();
		bean.setViewTitle(appTitle);
		bean.setViewLayout(appLayout);
		viewList.add(bean);
		
		setAnimation(viewList);
	}

	private void setAnimation(List<ViewBean> viewList) {
		//��title���õ���¼�������ִ�ж�������title��Ӧ��layout
		for (ViewBean bean : viewList) {
			final View viewTitle = bean.getViewTitle();
			final View viewLayout = bean.getViewLayout();
			viewTitle.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//��title��Ӧ��layoutִ�ж����Ĳ���
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
			sm.toggle();//�ر�slidingmenu
		}
	};
	
	/**
	 * ִ�ж�����view���������״̬���Զ�չ����չ����������Ϊ�ɼ�״̬
	 * ִ�ж�����view����ǿɼ�״̬���Զ����أ����غ�������Ϊ���ɼ�״̬��
	 */
	private void executeAnimation(View viewTitle, View viewLayout) {
		if (allowAnim) {
			//��ʼ��չ������
			ExpandAnimation animation = new ExpandAnimation(viewLayout, 300);
			boolean toggle = animation.toggle();//��ǰ������״̬
			if (toggle) {//��ǰ����ʱ�򿪵�
				RelativeLayout title = (RelativeLayout) viewTitle;//��viewTitleǿת��RelativeLayout
				ImageView img = (ImageView) title.getChildAt(0);//�õ�title�ĵ�0������imageview
				LinearLayout ll = (LinearLayout) title.getChildAt(1);//�õ���1������LinearLayout
				TextView tv = (TextView) ll.getChildAt(0);//�õ���ǰtitle��layout�е��ı�ֵ
				ImageView arrow = (ImageView) title.getChildAt(2);//�õ�title�ĵ�2������imageview
				setPress(img,tv,arrow);
				viewTitle.setBackgroundResource(R.drawable.business_hall_selector_pressed);//����title�����İ���Ч��
				if (lastView == null) {//˵��֮ǰû�д򿪹�����(��������Ǵ򿪵�,�����Ƿ���������Ҳ�Ǵ򿪵�)
					lastView = viewLayout;
				} else {//˵��֮ǰִ�й�����
					if(lastView == viewLayout) {//��һ��ִ�еĶ����ǲ����Լ�
						lastView = null;
					} else {//��һ��ִ�еĶ��������Լ�
						executeAnimation(viewTitle, lastView);//��lastViewִ�ж���
						lastView = viewLayout;//����ǰִ�ж�����view������lastView
					}
				}
			} else {//��ǰ�����Ѿ��ر�
				for (ViewBean bean : viewList) {
					if (viewLayout == bean.getViewLayout()) {//�ָ�ԭ��δ���ʱ��״̬
						RelativeLayout title = (RelativeLayout) bean.getViewTitle();
						ImageView img = (ImageView) title.getChildAt(0);//�õ�title�ĵ�0������imageview
						LinearLayout ll = (LinearLayout) title.getChildAt(1);//�õ���1������LinearLayout
						TextView tv = (TextView) ll.getChildAt(0);//�õ���ǰtitle��layout�е��ı�ֵ
						ImageView arrow = (ImageView) title.getChildAt(2);//�õ�title�ĵ�2������imageview
						setNormal(img,tv,arrow);
						title.setBackgroundResource(R.drawable.business_hall_selector_normall);//����title�����İ���Ч��
					}
					if (lastView == viewLayout) {
						lastView = null;
					}
				}
				
			}
				//���ö����ļ���
				animation.setAnimationListener(animationListener);
				//��ʼ���Ŷ���
				viewLayout.startAnimation(animation);
				
			} else {
				Log.d(TAG, "���ܲ��Ŷ���");
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
			if (str.contains(activity.getString(R.string.business_hall))) {//����Ӫҵ��
				img.setImageResource(R.drawable.find_businesshall_press);
			} else if (str.contains(activity.getString(R.string.international_roaming))) {//��������
				img.setImageResource(R.drawable.internation_roaming_press);
			} else if (str.contains(activity.getString(R.string.mobile_mobile))) {//�ֻ�
				img.setImageResource(R.drawable.find_cellphone_press);
			} else if (str.contains(activity.getString(R.string.packge_name))) {//�ײ�
				img.setImageResource(R.drawable.mobile_package_press);
			} else if (str.contains(activity.getString(R.string.telephone_charge))) {//��ѯ���
				img.setImageResource(R.drawable.query_fee_press);
			} else if (str.contains(activity.getString(R.string.service_phone_number))) {//�ͻ��绰
				img.setImageResource(R.drawable.call_customer_service_telephone_press);
			} else if (str.contains(activity.getString(R.string.tang_poetry))) {//����ʫ
				img.setImageResource(R.drawable.tang_poetry_press);
			} else if (str.contains(activity.getString(R.string.joke))) {//��Ц��
				img.setImageResource(R.drawable.joke_press);
			} else if (str.contains(activity.getString(R.string.myapp))) {//��Ӧ��
				img.setImageResource(R.drawable.app_btn_pressed);
			} else if (str.contains(activity.getString(R.string.mm))) {//����Ů
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
