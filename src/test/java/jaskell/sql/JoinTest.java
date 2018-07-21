package jaskell.sql;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.print.attribute.standard.PresentationDirection;
import javax.sql.RowSet;
import java.sql.*;

import static jaskell.sql.SQL.*;

public class JoinTest {
    private static final String url = "jdbc:sqlite::memory:";
    private static final String table = "test";
    static private Connection conn;
    static private Statement ist = insert().into("test", "pid, content")
            .values(p("pid", int.class),
                    p("content", String.class));

    @BeforeClass
    static public void init() {
        try {
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
            conn.prepareStatement(
                    "create table test(id integer primary key autoincrement, " +
                            "pid integer references test(id), " +
                            "content text)")
                    .execute();
            Statement ins = ist.cache();
            Query queryLastId = select(func("last_insert_rowid"));
            int last = 1;
            ins.setParameter("content", "one line.");
            for(int i = 0; i < 10; i++){
                ins.setParameter("pid", last);
                ins.execute(conn);
                last = queryLastId.scalar(conn, Integer.class).orElse(0);
                if(last == 0){
                    throw  new IllegalStateException("can't get new id which insert");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @AfterClass
    static public void close(){
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void initTest(){
        try {
            Query q = select(count()).from("test");
            var c = q.scalar(conn, Integer.class);
            Assert.assertTrue(c.isPresent());
            Assert.assertEquals("expect 10 lines of data", 10, c.get().intValue());
        } catch (SQLException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void joinSelfTest(){
        Query q = select("l.id, r.id, l.content, r.content")
                .from(n("test").as("l"))
                    .join(n("test").as("r")).on(l("l.id").eq(l("r.pid")))
                .where(l("l.id").ne(l("r.id")));
        try(PreparedStatement statement = q.prepare(conn);
            ResultSet rs = q.query(statement)){
            while (rs.next()){
                Assert.assertEquals(rs.getString(3), rs.getString(4));
                Assert.assertEquals(rs.getInt(1)+1, rs.getInt(2));
            }
        } catch (SQLException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void leftJoinTest(){
        Query q = select("l.id, r.id, l.content, r.content")
                .from(n("test").as("l")
                        .left().join(n("test").as("r")).on(l("l.id=r.pid")))
                .where(l("r.id").isNull());
        try(PreparedStatement statement = q.prepare(conn);
            ResultSet rs = q.query(statement)){
            while (rs.next()){
                Assert.assertNull(rs.getObject(2));
            }
        } catch (SQLException e) {
            Assert.fail(e.getMessage());
        }
    }

}
