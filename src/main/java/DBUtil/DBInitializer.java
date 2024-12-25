package DBUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class DBInitializer implements ServletContextListener {
    private static final String driver = "org.sqlite.JDBC";
    public static String Path = null;

    private static Connection conn = null;

    static {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Path = sce.getServletContext().getRealPath("/") + "pj568-web-store.db";
        try {
            InitDB.main();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        if (conn == null || isConnectionClosed(conn)) {
            if (Path == null) {
                System.out.println("数据库初始化失败");
                return null;
            }
            try {
                conn = DriverManager.getConnection("jdbc:sqlite:" + Path);
                System.out.println("数据库初始化成功");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("数据库初始化失败");
            }
        }
        return conn;
    }

    private static boolean isConnectionClosed(Connection conn) {
        try {
            return conn != null && conn.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("数据库连接已关闭");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("关闭数据库连接时出错");
            }
        }
    }
}