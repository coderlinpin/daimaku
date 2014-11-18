1.pager
<android.support.v4.view.ViewPager
        android:id="@+id/pager"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_marginTop="50dp"
        >
    </android.support.v4.view.ViewPager>
    
	pager = (ViewPager) findViewById(R.id.pager);
	pager.setAdapter(adapter);
	
2.adapter  下有全部代码
	
	ViewPagerAdapter extends PagerAdapter
	
	private List<View> list;
	
	public ViewPagerAdapter(List<View> list) {
		this.list = list;
	}
	
	@Override
	public int getCount()
	
	public boolean isViewFromObject(View arg0, Object arg1) 
	
	public void destroyItem(ViewGroup container, int position, Object object)
	ViewPager vp = (ViewPager) container;
		View view = list.get(position);
		vp.removeView(view);
		
	public Object instantiateItem(ViewGroup container, int position)
	vp.addView(view);
		return view;
		
3.List<View> list

view :用LocalActivityManager lam将Activty转化为view

		//将滑动的Activity转成view
		lam = new LocalActivityManager(this, true);
		lam.dispatchCreate(savedInstanceState);//必须被调用
		
		
		Intent intent = new Intent(this,MainActivity.class);
		View mainView = lam.startActivity("MainActivity", intent).getDecorView();
		viewList.add(mainView);
		
		//将viewList放到ViewPager的适配器中去显示
		ViewPagerAdapter adapter = new ViewPagerAdapter(viewList);
		pager.setAdapter(adapter);
		
	2.adapter	

	package com.itheima24.know.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

public class ViewPagerAdapter extends PagerAdapter {

	private List<View> list;
	
	public ViewPagerAdapter(List<View> list) {
		this.list = list;
	}
	
	/**
	 * 一共有多少个数量
	 */
	@Override
	public int getCount() {
		return list.size();
	}

	/**
	 * 判断当前view是不是来自object
	 */
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	/**
	 * item被销毁的时候
	 */
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		super.destroyItem(container, position, object);
		ViewPager vp = (ViewPager) container;
		View view = list.get(position);
		vp.removeView(view);
	}
	/**
	 * item被生成的时候
	 */
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		ViewPager vp = (ViewPager) container;
		View view = list.get(position);
		vp.addView(view);
		return view;
	}

}
