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
        //int columncount = rsmd.getColumnCount();
        ArrayList<String> rest = new ArrayList<>();
        rest = handlingresult(rs,rsmd);
        return rest;
    }

    public boolean dodelite(String sqlReq, int id){
        try {
            PreparedStatement pstmt = dbCon.prepareStatement(sqlReq);
            pstmt.setInt(1, id);
            //System.out.println(pstmt.toString());
            pstmt.execute();
        }catch (SQLException e){
            System.out.println("Error Dbconnector " +e.getMessage());
            return false;
        }
        return true;
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

    public ArrayList<String> handlingresult(ResultSet rs, ResultSetMetaData rsmd) throws SQLException {
        ArrayList<String> columnresult = new ArrayList<>();

        int countcolumn = rsmd.getColumnCount();
        columnresult.add(String.valueOf(countcolumn)); //Первый элемент в результате - количество столбцов в таблице
        //название столбцов
        for(int i = 1; i<=countcolumn; i++){
            columnresult.add(rsmd.getColumnName(i));
        }
        while (rs.next()){
            for(int i = 1; i<=countcolumn; i++) {
                columnresult.add(rs.getString(i));
            }
        }
        //System.out.println(columnresult);

        return columnresult;
    }

}
