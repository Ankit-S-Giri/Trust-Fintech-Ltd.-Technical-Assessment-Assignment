package com.ankitgiri.bank_service_a.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

public class TrxIdGenerator {

	private static final AtomicLong COUNTER = new AtomicLong(0);

	public static String generate() {
		return "TRX-" + LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE).toString() + "-"
				+ String.format("%06d", COUNTER.incrementAndGet());
	}
}