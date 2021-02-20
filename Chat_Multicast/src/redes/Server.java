package redes;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class Server {

	public static void main(String[] args) {
		Map<String, Room> rooms = new TreeMap<String, Room>();

		DatagramSocket aSocket = null;
		String code;
		
		String roomName = "";
		String roomIP = "";
		String userName = "";

		try {
			aSocket = new DatagramSocket(6787);
			System.out.println("Servidor: ouvindo porta UDP/6787");
			String message = null;
			DatagramPacket reply = null;

			while (true) {

				byte[] buffer = new byte[100000];
				DatagramPacket request = new DatagramPacket(buffer, buffer.length);

				aSocket.receive(request);

				String[] m = new String(request.getData()).trim().split(";");


				code = m[0];
				System.out.println(code);

				switch (code) {
				case "CREATE":
					roomName = new String(m[1]);
					roomIP = new String(m[2]);
					userName =  new String(m[3]);
					Room room = new Room(roomName, roomIP);
					room.joinGroup(userName);
					rooms.put(roomName, room);

					break;
				case "LIST ROOMS":
					StringBuffer sb = new StringBuffer();

					for (Map.Entry<String, Room> entry : rooms.entrySet()) {
						sb.append(entry.getValue().toString());
					}
					message = sb.toString();

					reply = new DatagramPacket(message.getBytes(), message.length(), request.getAddress(),
							request.getPort());
					aSocket.send(reply);

					break;
				case "JOIN ROOM":
					StringBuffer sb2 = new StringBuffer();
					Room aux = rooms.get(m[1]);
					
					
					System.out.println(m[0]);
					System.out.println(m[1]);
					System.out.println(m[2]);

					
					if (aux == null)
						message = "!";
					else {
						sb2.append("Usario adicionado com sucesso.;");
						sb2.append(aux.getIp());
						rooms.get(m[1]).joinGroup(m[2]);
					}
					
					message = sb2.toString();
					
					reply = new DatagramPacket(message.getBytes(), message.length(), request.getAddress(),
							request.getPort());
					aSocket.send(reply);

					System.out.println("Usario adicionado");
					break;
				case "LEAVE ROOM":
					System.out.println("alo to removendo");
					userName = m[1];
					roomName = m[2];
					System.out.println("m[1] "+ m[1]);
					System.out.println("m[2] "+ m[2]);

					rooms.get(roomName).leaveGroup(userName);
				}
			}

		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());

		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());

		} finally {
			if (aSocket != null)
				aSocket.close();
		}
	}

}
