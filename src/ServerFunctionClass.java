import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class ServerFunctionClass implements Runnable {

    private Socket userSocket;
    private static int userCount = 0;
    private boolean isQuit = false;
    private static Connection con;
    private String URL="klienci.db";

    public ServerFunctionClass(Socket s) {
        super();
        this.userSocket = s;
        userCount++;
        
    }

    @Override
    public void run() {
        try (InputStream is = userSocket.getInputStream();
             OutputStream os = userSocket.getOutputStream();
             Scanner sc = new Scanner(is);
             PrintWriter pw = new PrintWriter(os, true)) {
            pw.println("Witamy na serwerze!");
            pw.println("Twoj numer uzytkownika to: " + userCount);

            
            boolean connected =connectToDB(URL);
            if (connected) pw.println("Polaczono z baza");
            
            
            showCommandList(pw);

            while (sc.hasNextLine() && !isQuit && connected) {
                String request = sc.nextLine();
                if (request.equals("metadata")) {
                	GetMetaData data=new GetMetaData(con);
                    ArrayList<String> metaDate = data.getMetaDate();
                    for(String tmp : metaDate) {
                        pw.println(tmp);
                    }

                }
                else if (request.equals("select")) {
                	GetData data=new GetData(con);
                    ArrayList<String> dataS = data.selectQuery(sc,pw);
                    
                    if(dataS.isEmpty()) {
                    	pw.println("Lista pusta");
                    }else {
                    	for(String tmp : dataS) {
                            pw.println(tmp);
                        }
                  
                    }
                    
                    

                }
                else if(request.equals("insert")) {
                	InsertData ins=new InsertData(con,pw,sc);
                	ins.insertToDB();
                }
                else if(request.equals("delete")) {
                	DeleteData del=new DeleteData(con);
                	del.deleteQuery(sc, pw);
                	
                }
                else if(request.equals("spis")) {
                	showCommandList(pw);
                }
                else if(request.equals("close")) {
                	userSocket.close();
                }
                else {
                    pw.println("Niepoprawna komenda");
                }
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private void showCommandList(PrintWriter pw) {
    	ArrayList<String> list=new ArrayList<>();
        list.add("SPIS KOMEND");
        list.add("  1.metadata -wypisuje budowe bazy (tabele i ich pola)");
        list.add("  2.select- wprowadza do operacji wypisawania zawartosci tabeli");
        list.add("  3.insert- dodaje rekordy do bazy");
        list.add("  4.delete- usowa rekordy z bazy");
        list.add("  5.komendy- Spis komend");
        
        
        for(String tmp : list) {
            pw.println(tmp);
        }
    }
    
    public static boolean connectToDB(String baseName) {
    	try {
            con = DriverManager.getConnection("jdbc:sqlite:" + baseName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    	
        return con != null ? true : false;
    }
    
    public static int getUserCount() {
    	return userCount;
    }

}
