package com.itheim28.submitdata;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.itheim28.submitdata.utils.NetUtils;
import com.itheim28.submitdata.utils.NetUtils2;

public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";
	private EditText etUserName;
	private EditText etPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		etUserName = (EditText) findViewById(R.id.et_username);
		etPassword = (EditText) findViewById(R.id.et_password);
	}
	
	/**
	 * ʹ��httpClient��ʽ�ύget����
	 * @param v
	 */
	public void doHttpClientOfGet(View v) {
		Log.i(TAG, "doHttpClientOfGet");
		final String userName = etUserName.getText().toString();
		final String password = etPassword.getText().toString();
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				// ��������
				final String state = NetUtils2.loginOfGet(userName, password);
				// ִ�����������߳���
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						// ���������߳��в���
						Toast.makeText(MainActivity.this, state, 0).show();
					}
				});
			}}).start();
	}
	

	/**
	 * ʹ��httpClient��ʽ�ύpost����
	 * @param v
	 */
	public void doHttpClientOfPost(View v) {
		Log.i(TAG, "doHttpClientOfPost");
		final String userName = etUserName.getText().toString();
		final String password = etPassword.getText().toString();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				final String state = NetUtils2.loginOfPost(userName, password);
				// ִ�����������߳���
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						// ���������߳��в���
						Toast.makeText(MainActivity.this, state, 0).show();
					}
				});
			}
		}).start();
	}

	public void doGet(View v) {
		final String userName = etUserName.getText().toString();
		final String password = etPassword.getText().toString();
		
		new Thread(
				new Runnable() {
					
					@Override
					public void run() {
						// ʹ��get��ʽץȥ����
						final String state = NetUtils.loginOfGet(userName, password);
						
						// ִ�����������߳���
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								// ���������߳��в���
								Toast.makeText(MainActivity.this, state, 0).show();
							}
						});
					}
				}).start();
	}
	
	public void doPost(View v) {
		final String userName = etUserName.getText().toString();
		final String password = etPassword.getText().toString();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				final String state = NetUtils.loginOfPost(userName, password);
				// ִ�����������߳���
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						// ���������߳��в���
						Toast.makeText(MainActivity.this, state, 0).show();
					}
				});
			}
		}).start();
	}
}
