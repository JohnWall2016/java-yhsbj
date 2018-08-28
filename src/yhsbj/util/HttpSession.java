package yhsbj.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

public class HttpSession implements AutoCloseable {
	private String host;
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	private int port;
	
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	private String charsetName = "UTF-8";
	
	public String getCharsetName() {
		return charsetName;
	}

	public void setCharsetName(String charsetName) {
		this.charsetName = charsetName;
	}

	private Socket socket;
	private InputStream input;
	private OutputStream output; 

	public HttpSession(String host, int port) 
			throws UnknownHostException, IOException {
		this.host = host;
		this.port = port;
		socket = new Socket(host, port);
		input = socket.getInputStream();
		output = socket.getOutputStream();
	}
	
	@Override
	public void close() throws Exception {
		if (output != null) {
			output.close();
			output = null;
		}
		if (input != null) {
			input.close();
			input = null;
		}
		if (socket != null) {
			socket.close();
			socket = null;
		}
	}
	
	public void write(String content) throws IOException {
		if (output == null)
			throw new IOException("The output stream is closed");
		output.write(content.getBytes(charsetName));
	}

	public String readLine() throws IOException {
		if (input == null)
			throw new IOException("The input stream is closed");
		try (var data = new ByteArrayOutputStream(512)) {
			int c = 0, n = 0;
			while (true) {
				c = input.read();
				if (c == -1)
					return data.toString(charsetName);
				if (c == 0xd) {
					n = input.read();
					if (n == 0xa)
						return data.toString(charsetName);
					else if (n == -1) {
						data.write(c);
						return data.toString(charsetName);
					} else {
						data.write(c);
						data.write(n);
					}
				} else
					data.write(c);
			}
		}
	}
	
	public String readHeader() throws IOException {
		var result = new StringBuffer(512);
		while (true) {
			var line = readLine();
			if (line == null || line.equals("")) break;
			result.append(line + "\n");
		}
		return result.toString();
	}
	
	public String readBody(String header) throws IOException {
		try (var data = new ByteArrayOutputStream(512)) {
			if (header == null || header.equals(""))
				header = readHeader();
			if (Pattern.matches("Transfer-Encoding: chunked", header)) {
				while (true) {
					var len = Integer.parseInt(readLine(), 16);
					if (len <= 0) {
						readLine();
						break;
					}
					var b = new byte[len];
					var rlen = input.readNBytes(b, 0, len);
					if (rlen != len) 
						throw new IllegalStateException("Length of data is shorter than expected");
					data.write(b, 0, rlen);
					readLine();
				}
			} else {
				var pattern = Pattern.compile("Content-Length: (\\\\d+)");
				var matcher = pattern.matcher(header);
				if (matcher.matches()) {
					var len = Integer.parseInt(matcher.group(1), 10);
					if (len > 0) {
						var b = new byte[len];
						var rlen = input.readNBytes(b, 0, len);
						if (rlen != len) 
							throw new IllegalStateException("Length of data is shorter than expected");
						data.write(b, 0, rlen);
					}
				} else 
					throw new UnsupportedOperationException("unsupported transfer mode");
			}
			return data.toString(charsetName);
		}
	}
	
	public byte[] getBytes(String content) throws UnsupportedEncodingException {
		if (content == null || content.equals("")) return new byte[0]; 
		return content.getBytes(charsetName);
	}
}
