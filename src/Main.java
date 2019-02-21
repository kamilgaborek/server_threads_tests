import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

	public static void main(String[] args) throws IOException {
		
		try(ServerSocket ss= new ServerSocket(8189)){
			while(true) {
				Socket s=ss.accept();
				Thread t=new Thread(new ServerFunctionClass(s));
				t.start();
				
			}
		}

	}

}
