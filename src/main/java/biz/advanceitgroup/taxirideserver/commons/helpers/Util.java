package biz.advanceitgroup.taxirideserver.commons.helpers;

import java.util.UUID;

public class Util {

	public static Boolean isTrue(Boolean value) {
		return true == value;
	}

	public static Boolean isFalse(Boolean value) {
		return false == value;
	}

	public static String generateRandomUuid() {
		return UUID.randomUUID().toString();
	}
}
