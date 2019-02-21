import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class InsertData {

	private Connection con;
	private PrintWriter pw;
	private Scanner sc;
	
	public InsertData(Connection con,PrintWriter pw,Scanner sc) {
		this.con=con;
		this.pw=pw;
    	this.sc=sc;
	}
	
	public void insertToDB() throws FileNotFoundException, IOException {
		
		try(Statement st=con.createStatement()){
			
			DatabaseMetaData dbmd=con.getMetaData();
			ResultSet res=dbmd.getTables(null, null, null, null);
			
			while(res.next()) {
				//Wypisywanie nazw tabel
				pw.print(res.getString("TABLE_NAME")+" - ");
			}
			pw.println("Podaj nazwe tabeli do ktorej chesz dodac rekordy:");
			String table_name=sc.nextLine();
			
			pw.println("1. single- dodanie pojedynczego rekordu");
			pw.println("2. file- dodanie rekordow z pliku");
			
			String option=sc.nextLine();
			
			if(option.equals("single")) {
				ResultSet res_table=st.executeQuery("select * from "+table_name);
				ResultSetMetaData resmd=res_table.getMetaData();
				int columnCount=resmd.getColumnCount();
				
				String sqlQuery="insert into "+table_name;
				String sqlQuery_tables=" (";
				String sqlQuery_values=" (";
				
				for(int i=1;i<=columnCount;i++) {
					
					
					if(resmd.isAutoIncrement(i)) {
					}else {
						sqlQuery_tables+=resmd.getColumnName(i);
						pw.println("Podaj wartosc dla kolumny: "+resmd.getColumnName(i)+" (typ:"+resmd.getColumnTypeName(i)+")");
						String tmp=sc.nextLine();
						
						
						if(resmd.getColumnTypeName(i).trim().equals("VARCHAR") ||resmd.getColumnTypeName(i).equals("TEXT")) {
							sqlQuery_values+="\'"+tmp+"\'";
							
						}else {
							sqlQuery_values+=tmp;
						}
						if(i!=columnCount) {
							sqlQuery_values+=",";
							sqlQuery_tables+=",";
						}
					}
					
					
				}
				sqlQuery+=sqlQuery_tables+") values"+sqlQuery_values;
				sqlQuery+=")";
				System.out.println(sqlQuery);
				st.execute(sqlQuery);
				
				
			}
			else if(option.equals("file")) {
				
				ResultSet res_table=st.executeQuery("select * from "+table_name);
				ResultSetMetaData resmd=res_table.getMetaData();
				int columnCount=resmd.getColumnCount();
				
				pw.println("Podaj siciezke do pliku:");
				String path=sc.nextLine();
				
				
				
				BufferedReader r = new BufferedReader(new FileReader(path));
		        String as = "", line = null;
		        
		        int countNotAutoincrement=0;
		        for (int i=1; i <=columnCount;i++){
					if(resmd.isAutoIncrement(i)) {
					}
					else {
						countNotAutoincrement++;
					}
				}
		        pw.println("Liczba kolumn notAuto:"+ countNotAutoincrement);
				
		        ArrayList<String> queryList=new ArrayList<>();
				while((line = r.readLine()) != null) {
					StringTokenizer lineST=new StringTokenizer(line);
						
						System.out.println(lineST.toString());
						String sqlQuery="insert into "+table_name;
						String sqlQuery_tables=" (";
						String sqlQuery_values=" (";
						
						for(int i=1;i<=columnCount;i++) {

							if(resmd.isAutoIncrement(i)) {
							}
							else {
								sqlQuery_tables+=resmd.getColumnName(i);
								String tmp=lineST.nextToken();
								
								if(resmd.getColumnTypeName(i).trim().equals("VARCHAR") ||resmd.getColumnTypeName(i).trim().equals("TEXT")) {
									sqlQuery_values+="'"+tmp+"'";
									//System.out.println("jestem"+resmd.getColumnTypeName(i));
								}else {
									sqlQuery_values+=tmp;
								}
								
								if(i!=columnCount) {
									sqlQuery_values+=",";
									sqlQuery_tables+=",";
									
								}
							}
							
							
						}
						sqlQuery+=sqlQuery_tables+") values"+sqlQuery_values;
						sqlQuery+=")";
						System.out.println(sqlQuery);
						//st.execute(sqlQuery);
						queryList.add(sqlQuery);
								
							
				}
				
				for(String command: queryList) {
					st.execute(command);
				}
				
			}
			else {
				pw.print("Podales zla komende");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public int tableDetails(String name,PrintWriter pw, Statement st) throws SQLException {
    	
    	ResultSet resS=st.executeQuery("select * from "+name);
        ResultSetMetaData res_table=resS.getMetaData();

        pw.println();
        int col_count=res_table.getColumnCount();
        for(int i=1; i<=col_count; i++) {
            pw.println(res_table.getColumnName(i)+" "+res_table.getColumnTypeName(i));
        }
        return col_count;
    }
	
}
