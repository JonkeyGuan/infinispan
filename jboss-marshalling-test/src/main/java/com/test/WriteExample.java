package com.test;

import java.io.FileOutputStream;
import java.io.IOException;
import org.jboss.marshalling.Marshaller;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

public final class WriteExample {

	public static void main(String[] args) {
		// Get the factory for the "river" marshalling protocol
		final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("river");

		// Create a configuration
		final MarshallingConfiguration configuration = new MarshallingConfiguration();
		// Use version 3
		configuration.setVersion(3);
		try {
			final Marshaller marshaller = marshallerFactory.createMarshaller(configuration);
			final FileOutputStream os = new FileOutputStream("external.txt");
			try {
				marshaller.start(Marshalling.createByteOutput(os));
				User[] array = new User[1024];
				for (int i = 0; i< array.length; i++) {
					array[i] = User.randomUser(i);
				}
				marshaller.writeObject(array);
				marshaller.finish();
				os.close();
			} finally {
				// clean up stream resource
				try {
					os.close();
				} catch (IOException e) {
					System.err.print("Stream close failed: ");
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			System.err.print("Marshalling failed: ");
			e.printStackTrace();
		}
	}
}
