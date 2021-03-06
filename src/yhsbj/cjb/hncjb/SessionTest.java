package yhsbj.cjb.hncjb;

import org.junit.Test;

import yhsbj.cjb.hncjb.transaction.Grinfo;
import yhsbj.cjb.hncjb.transaction.GrinfoQuery;

public class SessionTest {

    @Test
    public void testSession() {
        try (var session = Session.user002()) {
            session.login();
            var query = session.dump(new GrinfoQuery("43031119591225052X"));
            System.out.format("query: %s\n", query);
            session.send(query);
            var rs = session.getResult(Grinfo.class);
            System.out.format("result: %s\n", rs);
            System.out.format("datas: %d|%s\n", rs.getDatas().size(), rs.getDatas());
            if (rs.getDatas().size() > 0) {
                var data = rs.getDatas().get(0);
                System.out.format("%s %s\n", data.getName(), data.getJbztCN());
            }
            session.logout();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void TestSession2() {
        Session.user002(session -> {
            var query = session.dump(new GrinfoQuery("130503193510300329"));
            System.out.format("query: %s\n", query);
            session.send(query);
            var rs = session.getResult(Grinfo.class);
            System.out.format("result: %s\n", rs);
            System.out.format("datas: %d|%s\n", rs.getDatas().size(), rs.getDatas());
            if (rs.getDatas().size() > 0) {
                var data = rs.getDatas().get(0);
                System.out.format("%s %s %s\n", data.getName(), data.getJbztCN(), data.getDwmc());
            }
        });
    }

}
