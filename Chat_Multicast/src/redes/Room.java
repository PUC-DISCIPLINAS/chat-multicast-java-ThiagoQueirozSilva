package redes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Room{

	private String name;
	private String ip;
	private List<String> participants;

	public Room(String name, String ip) {
		this.name = name;
		this.participants = new ArrayList<String>();
		this.ip = ip;

	}

	public void joinGroup(String name) {
		this.participants.add(name);
	}

	public void leaveGroup(String userName) {
		this.participants = this.participants.stream().filter(p -> !p.equals(userName)).collect(Collectors.toList());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public List<String> getParticipants() {
		return participants;
	}

	@Override
	public String toString() {
		return "Room [name=" + name + ", ip=" + ip + ", participants=" + participants + "]\n";
	}

}
