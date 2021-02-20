package redes;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Scanner;

public class Client {

	public static void main(String[] args) {
		Scanner command = new Scanner(System.in);
		Scanner sc = new Scanner(System.in);

		DatagramSocket aSocket = null;
		DatagramPacket request = null;
		DatagramPacket reply = null;
		int serverPort = 6787;

		InetAddress aHost = null;
		InetAddress multicastHost;

		String roomName = null;
		String roomIP = null;
		String message = null;
		byte[] m = null;
		byte[] buffer = null;

		try {

			aHost = InetAddress.getByName(args[0]);
			System.out.println("Digite o seu nome");
			String userName = sc.nextLine();

			aSocket = new DatagramSocket();
			aHost = InetAddress.getByName(args[0]);

			MulticastSocket mSocket = new MulticastSocket(6789);
			boolean cont = true;

			while (cont) {
				menu();
				switch (command.nextLine()) {
				case "EXIT":
					cont = false;
					System.out.println("Conexao encerrada com sucesso.");
					break;

				case "CREATE":
					System.out.println("Voce selecionou a opcao de criar uma sala multicast!");
					System.out.println("Digite o nome da sala");
					roomName = sc.nextLine();
					
					System.out.println(roomName);

					System.out.println("Digite o ip do servidor multicast");
					roomIP = sc.nextLine();

					// envia para o servidor a nova sala para ser armazenada.

					StringBuffer sb = new StringBuffer();
					sb.append("CREATE;");
					sb.append(roomName + ";");
					sb.append(roomIP + ";");
					sb.append(userName);

					message = sb.toString();
					m = message.getBytes();

					request = new DatagramPacket(m, m.length, aHost, serverPort);
					aSocket.send(request);

					// MULTICAST
					multicastHost = InetAddress.getByName(roomIP);

					mSocket.joinGroup(multicastHost);

					System.out.println("Bem vindo ao chat multicast!");
					System.out.println("Digite LEAVE ROOM para sair da sala");


					new Thread(() -> processMessages(mSocket, userName)).start();
					boolean i = true;
					String mess2 = "";
					while (i) {
						mess2 = userName + ": " + sc.nextLine();
						byte[] chat = mess2.getBytes();
						DatagramPacket messageOut = new DatagramPacket(chat, chat.length, multicastHost, 6789);
						mSocket.send(messageOut);
						if (mess2.equals((userName + ": " + "LEAVE ROOM").trim())) {
							mSocket.leaveGroup(multicastHost);
							i = false;
						}

					}

					// AVISA O SERVIDOR QUE USUARIO SAIU
					sb = new StringBuffer();
					sb.append("LEAVE ROOM;");
					sb.append(userName + ";");
					sb.append(roomName);
					message = sb.toString();
					m = message.getBytes();

					request = new DatagramPacket(m, m.length, aHost, serverPort);
					aSocket.send(request);
					
					System.out.println("");
					break;
				case "LIST ROOMS":
					System.out.println();
					message = "LIST ROOMS;";
					m = message.getBytes();

					request = new DatagramPacket(m, m.length, aHost, serverPort);
					aSocket.send(request);

					buffer = new byte[100000];
					reply = new DatagramPacket(buffer, buffer.length);
					aSocket.receive(reply);
					message = new String(reply.getData()).trim();

					System.out.println("Lista de salas disponiveis:");
					System.out.println(message);

					System.out.println("");

					break;
				case "JOIN ROOM":

					System.out.println("Digite o nome da sala que deseja ingressar.");
					String insertName = sc.nextLine();

					StringBuffer sb2 = new StringBuffer();
					sb2.append("JOIN ROOM;");
					sb2.append(insertName + ";");
					sb2.append(userName);

					message = sb2.toString();
					m = message.getBytes();

					request = new DatagramPacket(m, m.length, aHost, serverPort);
					aSocket.send(request);

					buffer = new byte[100000];
					reply = new DatagramPacket(buffer, buffer.length);
					aSocket.receive(reply);
					message = new String(reply.getData()).trim();
					String[] messageArray = message.split(";");

					// MULTICAST

					multicastHost = InetAddress.getByName(messageArray[1]);
					mSocket.joinGroup(multicastHost);
					if (messageArray[0].equals("!"))
						System.out.println("Sala inexistente usuario nao pode ser adicionado");
					else {

						System.out.println(messageArray[0]);
						System.out.println("Bem vindo a sala de chat multicast!");
						System.out.println("Digite LEAVE ROOM para sair da sala");
						new Thread(() -> processMessages(mSocket, userName)).start();
						boolean j = true;
						String mess = "";
						while (j) {
							mess = userName + ": " + sc.nextLine();
							byte[] chat = mess.getBytes();
							DatagramPacket messageOut = new DatagramPacket(chat, chat.length, multicastHost, 6789);
							mSocket.send(messageOut);
							if (mess.equals((userName + ": " + "LEAVE ROOM").trim())) {
								j = false;
								mSocket.leaveGroup(multicastHost);
							}

						}

						sb2 = new StringBuffer();
						sb2.append("LEAVE ROOM;");
						sb2.append(userName + ";");
						sb2.append(insertName);
						message = sb2.toString();
						m = message.getBytes();

						request = new DatagramPacket(m, m.length, aHost, serverPort);
						aSocket.send(request);

					}
					System.out.println("");

					break;
				}
			}

		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		} finally {
			if (aSocket != null)
				aSocket.close();
		}
	}

	public static void menu() {
		System.out.println(
				"DIGITE ABAIXO UMA DAS OPCOES \n 1- CREATE: para criar uma nova sala multicast \n 2- LIST ROOMS: para ver as salas disponiveis \n 3- JOIN ROOM para entrar em uma sala \n 4- EXIT: para encerrar a conexao");
	}

	public static void processMessages(MulticastSocket mSocket, String userName) {

		try {
			boolean contin = true;
			String message = "";
			while (contin) {
				byte[] buffer = new byte[100000];
				DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);
				mSocket.receive(messageIn);
				message = new String(messageIn.getData()).trim();
				System.out.println(new String(messageIn.getData()).trim());
				if (message.equals(userName + ": " + "LEAVE ROOM")) {
					System.out.println("Voce saiu da sala multicast");
					contin = false;
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
