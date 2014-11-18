	//加载侧滑菜单sm.menu的布局
		
		sm.setMenu(R.layout.navigation_layout);//设置slidingmenu显示的layout
		new NavigationHandler(this,sm);//实现导航页的动画需要的代码量比较大，所以写在单独的类中。


public class NavigationHandler 

public NavigationHandler(Activity activity,SlidingMenu sm) {
		this.activity = activity;
		this.sm = sm;
	//1.将所有的viewTitle和viewLayout封装成ViewBean，并且用list保存
		initViews();
	}
	//2.给title设置点击事件，但是执行动画的是title对应的layout
	setAnimation(viewList);
	//3.给title对应的layout执行动画的操作
	executeAnimation(viewTitle,viewLayout);
	
	viewLayoutClickListener
	****************************************
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
		
		........
		
				
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