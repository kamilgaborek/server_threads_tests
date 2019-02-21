import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class DeleteData {

	private final static String URL = "klienci.db";
    private static Connection con;
    private PrintWriter pw;

    public DeleteData(Connection con) {
    	this.con=con;
    }

    public static boolean connect() {
        try {
            con = DriverManager.getConnection("jdbc:sqlite:" + URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        

        return con != null ? true : false;
    }

    public void deleteQuery(Scanner sc,PrintWriter pw) throws SQLException {
        
    	ArrayList<String> list = new ArrayList<>();
        try (Statement st = con.createStatement()) {
        	
        	DatabaseMetaData dbmd=con.getMetaData();
			ResultSet res=dbmd.getTables(null, null, null, null);
			
			while(res.next()) {
				//Wypisywanie nazw tabel
				pw.print(res.getString("TABLE_NAME")+" - ");
			}
			pw.println();
			pw.println("Podaj nazwe tabeli z której chcesz usunac rekordy:");
			String name=sc.nextLine();
			
			
			
				pw.println("Pola tabeli ktora wybrales:");
				tableDetails(name,pw,st);
				pw.println("------------------------------");
				pw.println("Podaj kolumne dla warunku 'where':");
				String colName=sc.nextLine();
				pw.println("Podaj warunek logiczny dla 'where' <,>,=:");
				String condition=sc.nextLine();
				pw.println("Podaj wartosc dla warunku 'where':");
				pw.println("Pamietaj, ze wartosci string otocz znacznikiem:'TRESC'");
				String value=sc.nextLine();
				
				String query="delete from "+name+" where "+colName+condition+value;
				
				executeUserQuery(query, st);
			
				
				
			
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        
    }

    
    
    public void tableDetails(String name,PrintWriter pw, Statement st) throws SQLException {
    	
    	ResultSet resS=st.executeQuery("select * from "+name);
        ResultSetMetaData res_table=resS.getMetaData();

        pw.println();
        int col_count=res_table.getColumnCount();
        for(int i=1; i<=col_count; i++) {
            pw.println(res_table.getColumnName(i)+" "+res_table.getColumnTypeName(i));
        }
    }
    
    public static void executeUserQuery(String query,Statement st) throws SQLException{
    	
    	st.executeQuery(query);
      
        
        //return tmpList;
    }

}
