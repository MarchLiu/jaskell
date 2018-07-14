package jaskell.sql;

import jaskell.parsec.State;
import org.junit.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.stream.IntStream;

import static jaskell.sql.SQL.*;

public class WriteTest {
    // static final String url = "jdbc:sqlite::memory:";
    static final String url = "jdbc:sqlite:/Users/mars/tmp/test.db";
    static final String table = "test";

    static private Connection conn;

    @BeforeClass
    static public void connect() {
        try {
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
//            conn.prepareStatement("create table test(id integer primary key autoincrement, content text)")
//                .execute();
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
    public void insertTest() {
        Statement query = insert().into(table, "content").values(p("data"));
        try(PreparedStatement statement = query.prepare(conn)){
            IntStream.range(0, 10).mapToObj(x->String.format("write %dth log", x)).forEach(log->{
                try {
                    query.setParameter("data", log);
                    query.syncParameters(statement);
                    statement.execute();
                    Assert.assertEquals("insert one should get count 1 but {}",
                            statement.getUpdateCount(),
                            1);
                } catch (SQLException e) {
                    Assert.fail(Arrays.toString(e.getStackTrace()));            }
            });
        } catch (SQLException e) {
            Assert.fail(Arrays.toString(e.getStackTrace()));
        }
    }

    @Test
    public void updateTest() {
        Statement statement = update("test").set("content", p("data")).where(l("id").eq(p("id")));
        statement.setParameter("id", 5);
        statement.setParameter("data", "rewritten");
        try {
            statement.execute(conn);
            Assert.assertTrue(true);
        } catch (SQLException e) {
            Assert.fail(Arrays.toString(e.getStackTrace()));
        }
    }

    @Test
    public void cleanTest(){
        Statement statement = delete().from(table);
        try{
            statement.execute(conn);
            Assert.assertTrue(true);
        } catch (SQLException|IllegalStateException e) {
            Assert.fail(Arrays.toString(e.getStackTrace()));
        }
    }
}
