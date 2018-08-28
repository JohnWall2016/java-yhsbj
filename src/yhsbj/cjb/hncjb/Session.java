package yhsbj.cjb.hncjb;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;

import yhsbj.util.*;

public class Session extends HttpSession {
	private String userId;
	private String password;
	private String sessionId;
	private String cxCookie;

	public Session(String host, int port, String userId, String password) 
			throws UnknownHostException, IOException {
		super(host, port);
		this.userId = userId;
		this.password = password;
	}
	
	private String buildSendContent(String content) throws UnsupportedEncodingException {
		var url = getHost() + ":" + getPort();
		var result = "POST /hncjb/reports/crud HTTP/1.1\n" +
                "Host: " + url + "\n" +
                "Connection: keep-alive\n" +
                "Content-Length: " + getBytes(content).length + "\n" +
                "Accept: application/json, text/javascript, */*; q=0.01\n" +
                "Origin: http://" + url + "\n" +
                "X-Requested-With: XMLHttpRequest\n"+
                "User-Agent: Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 " +
                "(KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36\n" +
                "Content-Type: multipart/form-data;charset=UTF-8\n"+
                "Referer: http://" + url + "/hncjb/pages/html/index.html\n"+
                "Accept-Encoding: gzip, deflate\n"+
                "Accept-Language: zh-CN,zh;q=0.8\n";
		if (sessionId != null && !sessionId.equals("")) {
			result += "Cookie: jsessionid_ylzcbp=" + sessionId +
					"; cxcookie=" + cxCookie + "\n";
		}
		result += "\n" + content;
		return result;
	}
	
	private void send(String seriveContent) 
			throws UnsupportedEncodingException, IOException {
		write(buildSendContent(seriveContent));
	}
	
	public void send() {
		
	}
}
