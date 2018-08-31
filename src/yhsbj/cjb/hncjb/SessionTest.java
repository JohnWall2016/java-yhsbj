package yhsbj.cjb.hncjb;

import org.junit.jupiter.api.Test;

import yhsbj.cjb.hncjb.transaction.Grinfo;
import yhsbj.cjb.hncjb.transaction.GrinfoQuery;

class SessionTest {

	@Test
	void testSession() {
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
				System.out.format("%s\n", data.getName());
			}
			session.logout();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@Test
	void TestSession2() {
		Session.user002(session -> {
			var query = session.dump(new GrinfoQuery("43031119591225052X"));
			System.out.format("query: %s\n", query);
			session.send(query);
			var rs = session.getResult(Grinfo.class);
			System.out.format("result: %s\n", rs);
			System.out.format("datas: %d|%s\n", rs.getDatas().size(), rs.getDatas());
			if (rs.getDatas().size() > 0) {
				var data = rs.getDatas().get(0);
				System.out.format("%s\n", data.getName());
			}
		});
	}

}
