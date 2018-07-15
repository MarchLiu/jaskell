package jaskell.sql;

import jaskell.parsec.State;
import jaskell.util.Result;
import org.junit.*;
import org.junit.runners.MethodSorters;

import java.sql.*;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

import static jaskell.sql.Func.*;
import static jaskell.sql.SQL.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WriteTest {
    static final String url = "jdbc:sqlite::memory:";
    static final String table = "test";

    static private Connection conn;

    @BeforeClass
    static public void connect() {
        try {
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
            conn.prepareStatement("create table test(id integer primary key autoincrement, content text)")
                .execute();
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
    public void updateTest() throws SQLException {
        AtomicLong id = new AtomicLong();
        Query findIdQuery = select(max(n("id")).as("id")).from(table);
        try{
            findIdQuery.scalar(conn, Integer.class).ifPresentOrElse(
                    id::set,
                    () -> System.out.println("data not found"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Statement statement = update(table).set("content", p("data"))
                .where(l("id").eq(p("id", Integer.class)));
        statement.setParameter("id", id.get());
        statement.setParameter("data", "rewritten");
        statement.execute(conn);

        Query query = select("content").from(table).where(l("id").eq(id.get()));
        query.scalar(conn, String.class).ifPresentOrElse(
                v->Assert.assertEquals("rewritten",v),
                ()-> Assert.fail("data updated not found"));
    }

    @Test
    public void zooCleanTest(){
        Statement statement = delete().from(table);
        try{
            statement.execute(conn);
            Assert.assertTrue(true);
        } catch (SQLException|IllegalStateException e) {
            Assert.fail(e.getMessage());
        }
    }
}
