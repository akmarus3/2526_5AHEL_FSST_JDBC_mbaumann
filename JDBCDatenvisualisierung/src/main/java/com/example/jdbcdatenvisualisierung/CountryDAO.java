package com.example.jdbcdatenvisualisierung;

import java.sql.*;
import java.util.*;

public class CountryDAO {

    private static final String URL = "jdbc:postgresql://xserv:5432/world2";
    private static final String USER = "reader";
    private static final String PASSWORD = "reader";

    public static Map<String, String> loadCountries() throws Exception {
        Map<String,String> map = new HashMap<>();

        String sql = "SELECT name, code FROM country ORDER BY name";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                map.put(rs.getString("name"), rs.getString("code"));
            }
        }
        return map;
    }

    public static List<LanguageStat> loadLanguages(List<String> codes) throws Exception {

        String placeholders = String.join(",", Collections.nCopies(codes.size(), "?"));

        String sql =
                "SELECT language, " +
                        "AVG(percentage) AS percentage, " +
                        "BOOL_OR(isofficial) AS isofficial " +
                        "FROM countrylanguage " +
                        "WHERE countrycode IN (" + placeholders + ") " +
                        "GROUP BY language " +
                        "ORDER BY percentage DESC";

        List<LanguageStat> list = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = con.prepareStatement(sql)) {

            for (int i = 0; i < codes.size(); i++) {
                ps.setString(i + 1, codes.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new LanguageStat(
                        rs.getString("language"),
                        rs.getDouble("percentage"),
                        rs.getBoolean("isofficial")
                ));
            }
        }
        return list;
    }
}
