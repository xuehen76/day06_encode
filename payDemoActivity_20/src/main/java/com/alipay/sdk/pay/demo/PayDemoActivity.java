package com.alipay.sdk.pay.demo;

import java.util.Map;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.PayTask;
import com.alipay.sdk.pay.demo.util.OrderInfoUtil2_0;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 *  重要说明:
 *  
 *  这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
 *  真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
 *  防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险； 
 */
public class PayDemoActivity extends FragmentActivity {
	
	/** 支付宝支付业务：入参app_id */
	public static final String APPID = "2016090501850689";
	
	/** 支付宝账户登录授权业务：入参pid值   商户ID*/
	public static final String PID = "2088102881552561";

	/** 支付宝账户登录授权业务：入参target_id值 */
	public static final String TARGET_ID = "";

/*	*//** 商户私钥，pkcs8格式 *//*
	public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALsUjUtpiOU7Z9jX" +
			"qfmscs9Zg/uTtZ+uGSH4TXIilQkzyWpMeCqTMzC1wLP4CCKcFHqacxvQ4ECEF269" +
			"gUfOmFrbYEeWAdUBvmc0p3UI0KU8Ib2MGy8EfrujVbCPC3nwgmHl/h1O5tx5Q0/T" +
			"fMSqpxpYFulpOyR5bF1SJmwe3F8FAgMBAAECgYBLr6riQZYCt5/x2H6J5jykqqB8" +
			"3WtzaKn+VkhuzpLhIDRybvGGt/rT0BRgOw5/ggm++FFthXnUYBxizwRImsqD9mzq" +
			"F0rbwXo3U24dloIR7T96wbAkBvmQ1iJiSn8NvVhycSIa+NdUFkvdbQ70j8+wE0Wk" +
			"yKdr3HszJRL8T+1zAQJBAODW2t7/Xg0kvy855GZPtM0X3wPCvN8Krems317pPZlu" +
			"BuygibBXyTRlLspJvgcwE8LTNG7filxv9Enu7wnVMGECQQDVAgiMjk0z63em1Ls/" +
			"RSFVq5NnPRD2/v6vab6pI9YRTwXbcr1v2ACUfE3xgGgwI9FQtviZw7NBqnDK08Zb" +
			"XAElAkA8cibHetn7KJzpgmPgCZ+u37MKHMmQIRveTaaDqqrv27+q0AIUUZV7K5Sn" +
			"t6mJ3ygA4a0f/aMB9Os1ETJcb18hAkAnkIof+WqTo0AFR+xD00Ze0FC/01LsXe7W" +
			"jDCy4AoqCuCOo2BWfuuUqQYdPnmbv3ton6PLg9Mu9kafFfj5DTzVAkEAgLGQGMhr" +
			"g6nyYkmYRexsxNFpS8pOGBgP5fAEDTM1mrwueeh0ylW4UBrOPs8FT44jyiJfvfaz" +
			"CP0/Rm6x84slxQ==";	*/
	/** 商户私钥，pkcs8格式 */
	public static final String RSA_PRIVATE ="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALH6mpvZbNaptOFg" +
			"DysvqW0s+XFeTkv4bNRN4vN+cMqIIuvTYX1ziyLrCjuUAxuWJQiZciTXrNE0+IAz" +
			"g2zKs+Zipm7mM5vizGmmn0aTHpQo9reEjwUDwiGx+U9DSMQXWwC7DsX9HA5U2B+g" +
			"qC3l3H1xyvvomJVbmxiWoja5/NDtAgMBAAECgYAl1n56Nb5S4N+IrrBq4QYnJNGf" +
			"TYpd0mHovOpMFAyMsmaiNl1am27wLIIPaodCnSggbOb8eDHQhG90af896NZYAGcv" +
			"CcZIbRWG6SWZvzQBWRPlYzBHv32jLQo3zZ02yt3x2NQAEH2GXCVlYNzHRU/LgMPW" +
			"jGMmI6SEQ4AKNvbgAQJBAOpR0wXFVs7hRsXrCwCllr5oixreVWOEtzBj5Fb4MGze" +
			"b7WGXd9h1xgiMp6PmRx98xqXSx6+aRvD2W9Bx6OLCAECQQDCckYzffnB6vPdlukK" +
			"le/IDeq5u1y/IyXZjKgEwFrHmGOysFpdFlgO+65QBAl52NIGORYwQQ47DTY5Unmn" +
			"BmjtAkBZwZ4jx8PI22JQzggY51Um+sTONfxfcqb5UkpVRhzGly4TSrUKrF/ITtE1" +
			"C6yKzXQh63JLBIUerZbMl1Xxv4ABAkEAi0XIbIdVxgSgqV23C4L7XSJCtsexlaH6" +
			"MUli6wxPuVi4SDuybaxcZFb2so9BrkmTnSt0bhmatMcwyHv/cJfRvQJBALC0j8Ep" +
			"0F7xRpuRuJxHbgkDOw29a+NHcfdbqCWp2skaneErPqJ5hSYP85jeNrgIqh/lA4bG" +
			"E7tCUvK471ZlZcI=";
	private static final int SDK_PAY_FLAG = 1;
	private static final int SDK_AUTH_FLAG = 2;

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@SuppressWarnings("unused")
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				@SuppressWarnings("unchecked")
				PayResult payResult = new PayResult((Map<String, String>) msg.obj);
				/**
				 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
				 */
				String resultInfo = payResult.getResult();// 同步返回需要验证的信息
				String resultStatus = payResult.getResultStatus();
				// 判断resultStatus 为9000则代表支付成功
				if (TextUtils.equals(resultStatus, "9000")) {
					// 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
					Toast.makeText(PayDemoActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
				} else {
					// 该笔订单真实的支付结果，需要依赖服务端的异步通知。
					Toast.makeText(PayDemoActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
				}
				break;
			}
			case SDK_AUTH_FLAG: {
				@SuppressWarnings("unchecked")
				AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
				String resultStatus = authResult.getResultStatus();

				// 判断resultStatus 为“9000”且result_code
				// 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
				if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
					// 获取alipay_open_id，调支付时作为参数extern_token 的value
					// 传入，则支付账户为该授权账户
					Toast.makeText(PayDemoActivity.this,
							"授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
							.show();
				} else {
					// 其他状态值则为授权失败
					Toast.makeText(PayDemoActivity.this,
							"授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

				}
				break;
			}
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay_main);
	}
	
	/**
	 * 支付宝支付业务
	 * 
	 * @param v
	 */
	public void payV2(View v) {
		if (TextUtils.isEmpty(APPID) || TextUtils.isEmpty(RSA_PRIVATE)) {
			new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialoginterface, int i) {
							//
							finish();
						}
					}).show();
			return;
		}
	
		/**
		 * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
		 * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
		 * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险； 
		 * 
		 * orderInfo的获取必须来自服务端；
		 */
		Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID);
		String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
		String sign = OrderInfoUtil2_0.getSign(params, RSA_PRIVATE);
		final String orderInfo = orderParam + "&" + sign;
		
		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				PayTask alipay = new PayTask(PayDemoActivity.this);
				Map<String, String> result = alipay.payV2(orderInfo, true);
				Log.i("msp", result.toString());
				
				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

	/**
	 * 支付宝账户授权业务
	 * 
	 * @param v
	 */
	public void authV2(View v) {
		if (TextUtils.isEmpty(PID) || TextUtils.isEmpty(APPID) || TextUtils.isEmpty(RSA_PRIVATE)
				|| TextUtils.isEmpty(TARGET_ID)) {
			new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置PARTNER |APP_ID| RSA_PRIVATE| TARGET_ID")
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialoginterface, int i) {
						}
					}).show();
			return;
		}

		/**
		 * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
		 * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
		 * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险； 
		 * 
		 * authInfo的获取必须来自服务端；
		 */
		Map<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(PID, APPID, TARGET_ID);
		String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);
		String sign = OrderInfoUtil2_0.getSign(authInfoMap, RSA_PRIVATE);
		final String authInfo = info + "&" + sign;
		Runnable authRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造AuthTask 对象
				AuthTask authTask = new AuthTask(PayDemoActivity.this);
				// 调用授权接口，获取授权结果
				Map<String, String> result = authTask.authV2(authInfo, true);

				Message msg = new Message();
				msg.what = SDK_AUTH_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		// 必须异步调用
		Thread authThread = new Thread(authRunnable);
		authThread.start();
	}
	
	/**
	 * get the sdk version. 获取SDK版本号
	 * 
	 */
	public void getSDKVersion() {
		PayTask payTask = new PayTask(this);
		String version = payTask.getVersion();
		Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 原生的H5（手机网页版支付切natvie支付） 【对应页面网页支付按钮】
	 * 
	 * @param v
	 */
	public void h5Pay(View v) {
		Intent intent = new Intent(this, H5PayDemoActivity.class);
		Bundle extras = new Bundle();
		/**
		 * url是测试的网站，在app内部打开页面是基于webview打开的，demo中的webview是H5PayDemoActivity，
		 * demo中拦截url进行支付的逻辑是在H5PayDemoActivity中shouldOverrideUrlLoading方法实现，
		 * 商户可以根据自己的需求来实现
		 */
		String url = "http://m.taobao.com";
		// url可以是一号店或者淘宝等第三方的购物wap站点，在该网站的支付过程中，支付宝sdk完成拦截支付
		extras.putString("url", url);
		intent.putExtras(extras);
		startActivity(intent);
	}

}
