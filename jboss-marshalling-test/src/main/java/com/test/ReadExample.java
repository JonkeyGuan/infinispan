package com.test;

import java.io.FileInputStream;
import java.io.IOException;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;
import org.jboss.marshalling.Unmarshaller;

public final class ReadExample {

	public static void main(String[] args) {
		// Get the factory for the "river" marshalling protocol
		final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("river");

		// Create a configuration
		final MarshallingConfiguration configuration = new MarshallingConfiguration();
		try {
			final Unmarshaller unmarshaller = marshallerFactory.createUnmarshaller(configuration);
			final FileInputStream is = new FileInputStream("external.txt");
			try {
				unmarshaller.start(Marshalling.createByteInput(is));
				Object obj = unmarshaller.readObject();
				System.out.println("Read object: " + obj);
				unmarshaller.finish();
				is.close();
			} finally {
				// clean up stream resource
				try {
					is.close();
				} catch (IOException e) {
					System.err.print("Stream close failed: ");
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			System.err.print("Marshalling failed: ");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.err.print("Marshalling failed: ");
			e.printStackTrace();
		}
	}
}