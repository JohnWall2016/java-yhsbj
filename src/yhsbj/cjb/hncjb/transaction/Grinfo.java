package yhsbj.cjb.hncjb.transaction;

import com.google.gson.annotations.SerializedName;

public class Grinfo {
	// 个人编号
	@SerializedName("aac001")
	int grbh;

	// 身份证号码
	@SerializedName("aac002")
	String pid;

	@SerializedName("aac003")
	String name;

	@SerializedName("aac006")
	int birthday;

	// 参保状态: "1"-正常参保 "2"-暂停参保 "4"-终止参保 "0"-未参保
	@SerializedName("aac008")
	String cbzt;

	// 户口所在地
	@SerializedName("aac010")
	String hkszd;

	// 缴费状态: "1"-参保缴费 "2"-暂停缴费 "3"-终止缴费
	@SerializedName("aac031")
	String jfzt;

	@SerializedName("aae005")
	String phone;

	@SerializedName("aae006")
	String address;

	@SerializedName("aae010")
	String bankcard;

	// 行政区划编码
	@SerializedName("aaf101")
	String xzqh;

	// 村组名称
	@SerializedName("aaf102")
	String czmc;

	// 村社区名称
	@SerializedName("aaf103")
	String csmc;

	public int getGrbh() {
		return grbh;
	}

	public String getPid() {
		return pid;
	}

	public String getName() {
		return name;
	}

	public int getBirthday() {
		return birthday;
	}

	public String getCbzt() {
		return cbzt;
	}

	public String getHkszd() {
		return hkszd;
	}

	public String getJfzt() {
		return jfzt;
	}

	public String getPhone() {
		return phone;
	}

	public String getAddress() {
		return address;
	}

	public String getBankcard() {
		return bankcard;
	}

	public String getXzqh() {
		return xzqh;
	}

	public String getCzmc() {
		return czmc;
	}

	public String getCsmc() {
		return csmc;
	}
}
