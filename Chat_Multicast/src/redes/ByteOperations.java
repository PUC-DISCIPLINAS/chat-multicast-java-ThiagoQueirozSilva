package redes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class ByteOperations {
	

	
	public static byte[] concat (byte[] a, byte[] b) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bos = new ByteArrayOutputStream();
		bos.write(a);
		bos.write(b);
		byte[] m = bos.toByteArray();
		
		return m;
	}
	
	public static byte[] roomToByteArray (Room room) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(room);
		oos.flush();
		
		return bos.toByteArray();
	}
	
	public static Room byteArrayToRoom (byte[] m) throws IOException, ClassNotFoundException {
		ByteArrayInputStream bis = new ByteArrayInputStream(m);
		ObjectInput in = new ObjectInputStream(bis);
		Object o = in.readObject();
		
		Room room = (Room)o;
		System.out.println(room.getName());
		
		return room;
		
	}
	
}
