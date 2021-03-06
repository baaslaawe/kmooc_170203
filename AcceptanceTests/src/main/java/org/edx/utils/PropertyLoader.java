package com.nile.utils;

import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

public class PropertyLoader {

	private static final String PROP_FILE = "/config.properties";

	public static Optional<String> loadProperty(String name) {
		Properties props = new Properties();
		try {
			props.load(PropertyLoader.class.getResourceAsStream(PROP_FILE));
		} catch (IOException e) {
			System.out.println("예외 발생");
		}

		return Optional.of(props.getProperty(name));
	}
}
