package ivanov.leonid.botdbdatareader;

import java.sql.*;
import java.util.ArrayList;

public class Dbconnector {
    private String dbAddress;
    private Statement stmt = null;
    private Connection dbCon;



    public Dbconnector(String dbAddress){
        this.dbAddress = dbAddress;

    }

    public ArrayList<String> doselect(String sqlReq) throws SQLException {
        stmt = dbCon.createStatement();
        ResultSet rs = stmt.executeQuery(sqlReq);
        ResultSetMetaData rsmd = rs.getMetaData();
        int columncount = rsmd.getColumnCount();
        ArrayList<String> rest = new ArrayList<>();
        while(rs.next()) {
            String element = "";
            for(int i = 1; i<=columncount; i++)
                element = element + " " + rs.getString(i);
            rest.add(element);
        }
        return rest;
    }

    public boolean initConnection() {
        //jdbc:mysql://localhost:3306/myDBforbot
        try {
            dbCon = DriverManager.getConnection("jdbc:mysql://" + dbAddress + "/myDBforbot", "BotApplication", "IamBottelegramm");
        } catch (SQLException e) {
            return false;
        }
        if(dbCon!= null)
            return true;
        else
            return false;
    }

    public void closeConnection() throws SQLException {
        if (dbCon!=null)
            dbCon.close();
    }

}
