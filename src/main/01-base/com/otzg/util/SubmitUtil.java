package com.otzg.util;

import com.otzg.log.util.LogUtil;
import net.sf.json.JSONObject;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class SubmitUtil {

	/**
	 * 发送以map为参数的post请求
	 */
	public final static String postMap(String url, Map<String, String> params) {
		String result = "";
		try {
			// 创建默认的httpClient实例.
			CloseableHttpClient httpClient = HttpClients.createDefault();
			// 创建httppost
			HttpPost httpPost = new HttpPost(url);
			// 创建参数队列
			List formparams = new ArrayList();
			for (String key : params.keySet()) {
				formparams.add(new BasicNameValuePair(key, params.get(key)));
			}
			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
			httpPost.setEntity(uefEntity);
			CloseableHttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
			response.close();
			httpClient.close();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 发送以json为参数的post请求
	 */
	public final static String postJson(String url, JSONObject params) {
		Map maps = JSONObject.fromObject(params);
		return postMap(url, maps);
	}

//	/**
//	 * 发送以json为参数的post请求
//	 */
//	public final static String postJson(String url,JSONObject params,String secret) {
//		Map maps = JSONObject.fromObject(params);
//		maps = SignUtil.(maps,secret);
//		return postMap(url,maps);
//	}


	/**
	 * 发送 get请求
	 */
	public final static String get(String url) {
		String result = "";
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			// 创建httpget.
			HttpGet httpGet = new HttpGet(url);
//			System.out.println("executing request " + httpGet.getURI());
			// 执行get请求.
			CloseableHttpResponse response = httpClient.execute(httpGet);
			// 获取响应实体
			HttpEntity entity = response.getEntity();
//			System.out.println("--------------------------------------");
			// 打印响应状态
//			System.out.println(response.getStatusLine());
			if (entity != null) {
				// 打印响应内容长度
//				System.out.println("Response content length: " + com.otzg.advert.entity.getContentLength());
				// 打印响应内容
//					System.out.println("Response content: " + EntityUtils.toString(com.otzg.advert.entity));
				result = EntityUtils.toString(entity);
			}
			response.close();
			httpClient.close();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}


	/**
	 * 使用spring RestTemplate 创建连接
	 *
	 * @param
	 * @author G/2018/3/13 11:40
	 */
	public static String sendRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
		String body = responseEntity.getBody();
		HttpStatus statusCode = responseEntity.getStatusCode();
		int statusCodeValue = responseEntity.getStatusCodeValue();
		HttpHeaders headers = responseEntity.getHeaders();
		StringBuffer result = new StringBuffer();
		result.append("responseEntity.getBody()：").append(body).append("<hr>")
				.append("responseEntity.getStatusCode()：").append(statusCode).append("<hr>")
				.append("responseEntity.getStatusCodeValue()：").append(statusCodeValue).append("<hr>")
				.append("responseEntity.getHeaders()：").append(headers).append("<hr>");
//		LogUtil.print(result.toString());
		LogUtil.print(responseEntity.getBody());
		return responseEntity.getBody();
	}


	/**
	 * 发送一个http请求
	 * java原生URLConnection
	 *
	 * @author G/2018/3/13 10:25
	 * @param url
	 */

	private static String sessionId = "FDE017F285B222185F211AE9FB1E52CF";

	public static final String URLSubmit(String url) {
		String result = "";
		BufferedReader in = null;
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0(compatible; MSIE 6.0; Windows NT 5.1;SV1)");

			connection.setRequestMethod("POST");

			if (sessionId == null) {
				String cookieValue = connection.getHeaderField("Set-Cookie");
				sessionId = cookieValue.substring(0, cookieValue.indexOf(";"));
			} else {
				connection.setRequestProperty("Cookie", sessionId);
			}

			// 建立实际的连接
			connection.connect();
//			// 获取所有响应头字段
//			Map<String,List<String>> map = connection.getHeaderFields();
//			// 遍历所有的响应头字段
//			for(String key : map.keySet()) {
//				System.out.println(key+ "--->" + map.get(key));
//			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return result;
	}

	public static void HttpSubmit(String memberId, String nickName, String filePath) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpPost httppost = new HttpPost(url);

			FileBody bin = new FileBody(new File(filePath));

			StringBody comment = new StringBody("A binary file of some kind", ContentType.create("image/jpeg", Consts.UTF_8));

			HttpEntity reqEntity = MultipartEntityBuilder.create()
					.addPart("file", bin)
					.addPart("memberId", new StringBody(memberId, ContentType.create("text/plain", Consts.UTF_8)))
					.addPart("nickName", new StringBody(nickName, ContentType.create("text/plain", Consts.UTF_8)))
					.addPart("comment", comment).build();

			httppost.setEntity(reqEntity);


//			System.out.println("executing request " + httppost.getRequestLine());
			CloseableHttpResponse response = httpClient.execute(httppost);
			try {
//				System.out.println("----------------------------------------");
//				System.out.println(response.getStatusLine());
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
//					System.out.println("Response content length: " + resEntity.getContentLength());
				}
				EntityUtils.consume(resEntity);
			} finally {
				response.close();
			}
			httpClient.close();
		} catch (ClientProtocolException e) {
//			e.printStackTrace();
		} catch (IOException e) {
//			e.printStackTrace();
		}
	}


	/**
	 * 并发测试方法
	 *
	 * @author G/2018/3/13 10:38
	 */
	private static CountDownLatch cdl;
	//测试象过河用户注册链接
//	private final static String url="http://zibu.fangshangqu.com/member/register.htm?account=18038718888&password=123456";
//	private final static String url = "http://yundong.fangshangqu.com/portal/register.htm?account=18038718888&password=123456&validationCode=alg3&type=1";
//	private final static String url = "http://localhost:8088/pay/get_cash?token=68C2FDEF32A9BA4750A2854351169041&subject=xx&details=ffff&payerUnitId=5&amount=1&payChannel=alipay&APP_ID=7186b1e96b7847d5";


	//测试招聘登录链接
//	private final static String url = "http://zhenhuo.fangshangqu.cn/shoppingCart/shoppingCart.htm";
//	private final static String url = "http://cs-zhaopincmbs.fangshangqu.com/recruitcmbs/memberUser/isLogin.htm?token=1A396F0DD13ED2572A2956085E97F99DE81B42A59D7E5C8C6A605D65B0BCC001";

	//测试修改用户上传图像
//	private final static String url="http://zibucmbs.fangshangqu.com/mall/member/getUpdatePersonal.htm";
//	private final static String url="http://zibu.fangshangqu.com/shoppingCart/delectShopCart.htm?shopCarId=100491";


	//	private final static String url = "http://cs-yuedanping.fangshangqu.cn/answer/record/findListOne?token=E9E9DAE3DBC1E573CB738110F3C792EDFBE992BC735785FDEB06D01C7E25D990&testPaperId=1&recordId=73";
//	private final static String url = "http://cs-yuedanping.fangshangqu.cn/user/login?account=13703957387&password=123456";
//	private final static String url = "https://yonghu.fangshangqu.cn/login/wechat?appid=7648eb564c58c07a&unionId=&openId=oHZB8wYWjc0cqmMVO3cNhhKplptI&promotionId=";
//	private final static String url = "http://127.0.0.1:8030/test2";
//	private final static String url = "http://127.0.0.1:8070/order/goods/buy?token=EB0318BF7A016AACF1E71707EECF598B7E40547D7692F1573BE9284D10778CF8FBE992BC735785FDEB06D01C7E25D990&goodsId=1&quantity=2&addressId=1&memo=%E7%AB%8B%E9%A9%AC%E5%8F%91%E8%B4%A7&couponCode=";
//	private final static String url = "http://127.0.0.1:8050/unit/register?token=EB0318BF7A016AACF1E71707EECF598B35EBCAFFECCC3F1105328A9C6D3EAE65FBE992BC735785FDEB06D01C7E25D990&mobile=13703957317&areaCode=4101&areaName=%E6%B2%B3%E5%8D%97%E9%83%91%E5%B7%9E&name=%E7%8E%8B%E5%A4%A7%E9%99%86&street=%E5%B7%A5%E4%BA%BA%E8%B7%AF100%E5%8F%B7";
//	private final static String url = "http://192.168.3.112:8050/member/update?token=0CECBC9B5FB67F54DBCFFB89AADC5E493A703F3686720F5BA979A5CAEFA66859FBE992BC735785FDEB06D01C7E25D990&nickName=%E8%B6%85%E4%BA%BA&name=&headImg=&gender=&birthday=&taxCompany=&dutyParagraph=";
	private final static String url = "http://192.168.3.112:8050/folder/save?name=h12316&sort=1";

	public static class sendMultiHttp implements Runnable {
		private int i;

		public sendMultiHttp(int i) {
			this.i = i;
		}

		@Override
		public void run() {
			try {
				cdl.await();
				LogUtil.print(i + "=>" + URLSubmit(url));
			} catch (InterruptedException e) {
				LogUtil.print("异常：" + e);
			}
		}
	}

	//测试并发入口
	public static void testConcurrent(int num) {
		//设置同一个session
//		submitHttp("http://zibucmbs.fangshangqu.com");
		//并发数量
		cdl = new CountDownLatch(num);
		// 依次创建并启动5个worker线程
		for (int i = 0; i < num; ++i) {
			new Thread(new sendMultiHttp(i)).start();
			cdl.countDown();
			LogUtil.print("阻塞：" + i);
		}
	}

	//测试for循环
	public static void testForeach() {
		//执行次数
		int num = 1000;
		// 依次创建并启动5个worker线程
		for (int i = 0; i < num; ++i) {
			LogUtil.print(i + "=>" + URLSubmit(url));
		}
	}

}