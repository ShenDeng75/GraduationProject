package shendeng.graduation.show;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.*;

/**
 * @author 肖斌武.
 * @describe 数据库访问层
 * @datetime 2020/4/25 22:12
 */
public class JDBCHelper {
    private static Connection conn;
    private static Statement statement;
    private static final String username = "root";
    private static final String password = "shendeng75";

    static {
        String url = String.format("jdbc:mysql://localhost:3306/%s?serverTimezone=GMT%%2b8", "graduation");
        try {
            conn = DriverManager.getConnection(url, username, password);
            statement = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取MySQL中的数据
     *
     * @param table 要查询的表
     * @return url与keys的键值对
     */
    public static Map<String, String> getData(String table) {
        Map<String, String> map = new HashMap<>();
        ResultSet resultSet;
        if ("大数据".equals(table)){
            table = "bigdata";
        }
        try {
            String sql = "select * from " + table + " limit 20";
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                map.put(resultSet.getString(1), resultSet.getString(2));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 插入数据
     */
    public static void insertData() throws IOException, SQLException {
        String sql = "insert into graduation.bigdata values (?, ?)";
        PreparedStatement prepared = conn.prepareStatement(sql);

        String dir = "D:\\Code\\GraduationProject\\files\\结果数据";
        String javaPath = dir + "\\大数据.txt";
        BufferedReader br = new BufferedReader(new FileReader(javaPath));
        String line = null;
        int row = 0;
        while ((line = br.readLine()) != null) {
            String[] url_keys = line.split("\t", 2);
            if (url_keys[1].length() < 4096) {
                System.out.println((++row) + "  " + url_keys[0]);
                prepared.setObject(1, url_keys[0]);
                prepared.setObject(2, url_keys[1]);
                prepared.execute();
            }
        }
    }

    /**
     * 找到最佳匹配
     * @param searchKeys 搜索的keys
     * @return 匹配度较高的urls
     */
    public static List<String> search(String searchKeys) {
        List<String> result = new ArrayList<>();
        List<String> list = new ArrayList<>(Arrays.asList(searchKeys.split(" ")));
        int len = (int) (list.size() * 0.5);
        String table = list.get(0);

        Map<String, String> map = getData(table);
        List<String> needs;
        for (String key : map.keySet()) {
            String need = map.get(key);
            needs = new ArrayList<>(Arrays.asList(need.split("\t")));
            needs.retainAll(list);
            if (needs.size() >= len) {
                result.add(needs.size() + "\t" + key);
            }
        }
        // 根据辅助数字(匹配个数),排序desc
        result.sort(Comparator.reverseOrder());
        // 去掉辅助数字
        List<String> ans = new ArrayList<>();
        for (String s : result) {
            String[] numURL = s.split("\t");
            ans.add(numURL[1]);
        }
        return ans;
    }

    public static void main(String[] args) throws IOException, SQLException {
        List<String> result = search("java ssh springmvc mybatis linq mvclinq axurerp angularjs");
        System.out.println(result);
    }
}
