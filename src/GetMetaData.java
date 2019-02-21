import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class GetMetaData {
	
	    private final String URL = "klienci.db";
	    private static Connection con;
	    private PrintWriter pw;

	    public GetMetaData(Connection con) {
	    	this.con=con;
	    	
	    }
	    
	    public static ArrayList<String> getMetaDate() {
	        ArrayList<String> list = new ArrayList<>();
	        DatabaseMetaData dbmd;

	        try (Statement st = con.createStatement()) {
	            dbmd = con.getMetaData();
	            ResultSet tables = dbmd.getTables(null, null, null, null);
	         
	            while(tables.next()) {
	                String table_name=tables.getString("TABLE_NAME");
	                list.add(table_name);

	                ResultSet res=st.executeQuery("select * from "+table_name);
	                ResultSetMetaData res_table=res.getMetaData();

	                int col_count=res_table.getColumnCount();
	                for(int i=1; i<=col_count; i++) {
	                    list.add(res_table.getColumnName(i)+" "+res_table.getColumnTypeName(i));
	                }

	                list.add("----------------------------------------------------------------");
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return list;
	    }
}
