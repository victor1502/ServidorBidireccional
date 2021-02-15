import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Hilo extends Thread{
	
	 static HashMap<String,Socket> datosCliente = new HashMap<String,Socket>();
	 String nombre;
	 Socket sock;
	 DataInputStream entrada;
	 DataOutputStream salida;

	public Hilo(Socket sock) {
		this.sock = sock;
	}
	
	public void run() {
		String mensaje;
		String respuesta;
		Boolean parar = false;
		
		try {
			entrada = new DataInputStream(sock.getInputStream());
			nombre = entrada.readUTF();
			crearCliente(nombre, sock);
			System.out.println("El nick del cliente es "+nombre);
			respuesta = "El servidor dice: El nick del cliente es "+nombre;
			
			do {
				mensaje = entrada.readUTF();
				
				if(mensaje.toLowerCase().equals("parar")) {
					
					respuesta = "El servidor dice: El usuario "+nombre+" se va a borrar";
					System.out.println("El usuario "+nombre+" se va a borrar");
					parar = true;
					//Eliminar cliente del HashMap
					enviarMensaje(respuesta);
					borrarCliente(nombre);
				}
				else {
					respuesta = "El servidor dice: El cliente "+nombre+" dice: "+mensaje;
					System.out.println("El cliente "+nombre+" dice: "+mensaje);
					enviarMensaje(respuesta);
				}
				
			}while(!parar);
			System.out.println("salimos");
			salida.close();
			entrada.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	synchronized public void enviarMensaje(String respuesta) {
		for(String name : datosCliente.keySet()) {
			try {
				System.out.println(name);
				salida = new DataOutputStream(datosCliente.get(name).getOutputStream());
				salida.writeUTF(respuesta);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	synchronized public void borrarCliente(String nombre) {
		datosCliente.remove(nombre);
	}
	
	synchronized public void crearCliente(String nombre,Socket sock) {
		datosCliente.put(nombre, sock);
		System.out.println(sock);
	}
}
