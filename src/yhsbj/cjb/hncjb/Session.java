package yhsbj.cjb.hncjb;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

import yhsbj.cjb.hncjb.service.IService;
import yhsbj.cjb.hncjb.service.JsonService;
import yhsbj.cjb.hncjb.service.Result;
import yhsbj.cjb.hncjb.transaction.SysLogin;
import yhsbj.util.*;

public class Session extends HttpSession {
	private String userId;
	private String password;
	private String sessionId;
	private String cxCookie;

	public Session(String host, int port, String userId, String password) throws UnknownHostException, IOException {
		super(host, port);
		this.userId = userId;
		this.password = password;
	}

	private String buildSendContent(String content) {
		var url = getHost() + ":" + getPort();
		var result = "POST /hncjb/reports/crud HTTP/1.1\n" + "Host: " + url + "\n" + "Connection: keep-alive\n"
				+ "Content-Length: " + getBytes(content).length + "\n"
				+ "Accept: application/json, text/javascript, */*; q=0.01\n" + "Origin: http://" + url + "\n"
				+ "X-Requested-With: XMLHttpRequest\n" + "User-Agent: Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 "
				+ "(KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36\n"
				+ "Content-Type: multipart/form-data;charset=UTF-8\n" + "Referer: http://" + url
				+ "/hncjb/pages/html/index.html\n" + "Accept-Encoding: gzip, deflate\n"
				+ "Accept-Language: zh-CN,zh;q=0.8\n";
		if (sessionId != null && !sessionId.equals("")) {
			result += "Cookie: jsessionid_ylzcbp=" + sessionId + "; cxcookie=" + cxCookie + "\n";
		}
		result += "\n" + content;
		return result;
	}

	void send(String seriveContent) throws IOException {
		var content = buildSendContent(seriveContent);
		// System.out.println("send: " + content);
		write(content);
	}

	public <T> void send(JsonService<T> service) throws IOException {
		send(service.toString());
	}

	public <T> void send(String serviceId, T params) throws IOException {
		send(new JsonService<>(serviceId, params).setLoginName(userId).setPassword(password));
	}

	public void send(IService service) throws IOException {
		send(service.Id(), service);
	}

	public String dump(IService service) throws IOException {
		return new JsonService<>(service.Id(), service).setLoginName(userId).setPassword(password).toString();
	}

	public String get() throws IOException {
		var ret = readBody();
		// System.out.println("get: " + ret);
		return ret;
	}

	public <T> Result<T> getResult(Class<T> datasType) throws IOException {
		var ret = get();
		return Result.<T>fromJson(ret, datasType);
	}

	public String login() throws IOException {
		send(JsonService.withoutParams("loadCurrentUser"));
		var header = readHeader();
		var pattern = Pattern.compile("Set-Cookie: jsessionid_ylzcbp=(.+?);");
		var matcher = pattern.matcher(header);
		if (matcher.find())
			sessionId = matcher.group(1);
		pattern = Pattern.compile("Set-Cookie: cxcookie=(.+?);");
		matcher = pattern.matcher(header);
		if (matcher.find())
			cxCookie = matcher.group(1);
		// System.out.println(header);
		readBody(header);

		send(new SysLogin().setUserName(userId).setPassword(password));
		return get();
	}

	public String logout() throws IOException {
		send(JsonService.withoutParams("syslogout"));
		return get();
	}

	public static Session user002() throws UnknownHostException, IOException {
		return new Session("10.136.6.99", 7010, Config.getValue("user002_id"), Config.getValue("user002_pwd"));
	}

	public interface Callable {
		public void call(Session session) throws Exception;
	}

	public static void user002(Callable call) {
		try (var session = Session.user002()) {
			session.login();
			call.call(session);
			session.logout();
		} catch (Exception ex) {
			new RuntimeException(ex);
		}
	}
}
