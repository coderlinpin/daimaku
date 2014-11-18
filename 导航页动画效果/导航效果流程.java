	//���ز໬�˵�sm.menu�Ĳ���
		
		sm.setMenu(R.layout.navigation_layout);//����slidingmenu��ʾ��layout
		new NavigationHandler(this,sm);//ʵ�ֵ���ҳ�Ķ�����Ҫ�Ĵ������Ƚϴ�����д�ڵ��������С�


public class NavigationHandler 

public NavigationHandler(Activity activity,SlidingMenu sm) {
		this.activity = activity;
		this.sm = sm;
	//1.�����е�viewTitle��viewLayout��װ��ViewBean��������list����
		initViews();
	}
	//2.��title���õ���¼�������ִ�ж�������title��Ӧ��layout
	setAnimation(viewList);
	//3.��title��Ӧ��layoutִ�ж����Ĳ���
	executeAnimation(viewTitle,viewLayout);
	
	viewLayoutClickListener
	****************************************
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
		
		........
		
				
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