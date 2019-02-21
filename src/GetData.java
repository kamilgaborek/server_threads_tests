import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

import org.w3c.dom.ranges.RangeException;

public class GetData {
    private final String URL = "klienci.db";
    private Connection con;
    private PrintWriter pw;

    public GetData(Connection con) {
    	this.con=con;
    }

    public boolean connect() {
        try {
            con = DriverManager.getConnection("jdbc:sqlite:" + URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        

        return con != null ? true : false;
    }

    public ArrayList<String> selectQuery(Scanner sc,PrintWriter pw) throws SQLException {
        
    	ArrayList<String> list = new ArrayList<>();
        try (Statement st = con.createStatement()) {
        	
        	DatabaseMetaData dbmd=con.getMetaData();
			ResultSet res=dbmd.getTables(null, null, null, null);
			
			while(res.next()) {
				//Wypisywanie nazw tabel
				pw.print(res.getString("TABLE_NAME")+" - ");
			}
			pw.println();
			pw.println("Podaj nazwe tabeli z której chcesz obrac rekordy:");
			String name=sc.nextLine();
			pw.println("1. all -Wypis wszystkich rekordów");
			pw.println("2. where -Wypisanie rekordów z u¿yciem warunku where");
			String option=sc.nextLine();
			
			if(option.equals("all")) {
				String query="select * from "+name;
				
				list=executeUserQuery(query, st);
	            
	            return list;
			}
			else if(option.equals("where")){
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
				
				String query="select * from "+name+" where "+colName+condition+value;
				
				list=executeUserQuery(query, st);
				return list;
				
				
			}
			else {
				pw.println("Podales zle wyra¿enie");
			}
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
        
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
    
    public static  ArrayList<String> executeUserQuery(String query,Statement st) throws SQLException{
    	ArrayList<String> tmpList=new ArrayList<>();
    	ResultSet res1 = st.executeQuery(query);
        
        ResultSetMetaData metaDate=res1.getMetaData();
        int colCount=metaDate.getColumnCount();
        
        
        while(res1.next()) {
        	String tmp="";
        	for(int j=0; j<colCount;j++) {
        		tmp=tmp+res1.getString(j+1)+" ";
        	}
        	System.out.println(tmp);
        	tmpList.add(tmp);
        }
        
        return tmpList;
    }
}