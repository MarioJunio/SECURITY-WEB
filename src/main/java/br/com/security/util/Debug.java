package br.com.security.util;

public interface Debug {

	public static void log(String tag, String message) {
		System.out.printf("[%s]: %s\n", tag, message);
	}

}
