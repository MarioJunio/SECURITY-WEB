package br.com.security.util;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public interface AppUtils {

	public static Long checkId(Long id) {
		return id == null ? 0 : id;
	}

	public static String md5(Long id) throws NoSuchAlgorithmException {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(StandardCharsets.UTF_8.encode(String.valueOf(id)));
		return String.format("%032x", new BigInteger(1, md5.digest()));
	}

	public static String onlyNumbers(String str) {
		return str.replaceAll("[^0-9]+", "");
	}
}
