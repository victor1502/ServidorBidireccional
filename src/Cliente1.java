import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Cliente1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Socket sock;
		Scanner sc = new Scanner(System.in);
		DataOutputStream salida;
		DataInputStream entrada;
		String mensaje, nombre, respuesta;
		InetAddress ip;
		int puerto;
		
		System.out.println("Introduzca su nick");
		nombre = sc.nextLine();
		
		/*System.out.println("Elije el puerto que vas a usar");
		puerto = sc.nextInt();
		sc.nextLine();*/
		Boolean parar = true;
		
		try {
			ip = InetAddress.getByName("localhost");
			sock = new Socket(ip,5555);
			salida = new DataOutputStream(sock.getOutputStream());
			entrada = new DataInputStream(sock.getInputStream());
			salida.writeUTF(nombre);
			
			System.out.println("Que mensaje quieres enviar?");
			mensaje = sc.nextLine();
			salida.writeUTF(mensaje);
			
			while(parar){
				respuesta = entrada.readUTF();
				System.out.println(respuesta);
				
				System.out.println("Que mensaje quieres enviar?");
				mensaje = sc.nextLine();
				salida.writeUTF(mensaje);			
				
				if(mensaje.toLowerCase().equals("parar")) {
					parar = false;
				}
									
			}
			sock.close();
			salida.close();
			entrada.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
