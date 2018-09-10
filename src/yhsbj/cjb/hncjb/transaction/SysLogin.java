package yhsbj.cjb.hncjb.transaction;

import yhsbj.cjb.hncjb.service.CustomService;

public class SysLogin extends CustomService {
    String username, passwd;

    public SysLogin setUserName(String username) {
        this.username = username;
        return this;
    }

    public SysLogin setPassword(String passwd) {
        this.passwd = passwd;
        return this;
    }

    public SysLogin() {
        super("syslogin");
    }
}
