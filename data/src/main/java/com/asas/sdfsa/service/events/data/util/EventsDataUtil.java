package com.geaviation.eds.service.events.data.util;


import org.springframework.stereotype.Component;

@Component
public class EventsDataUtil {
	
	private EventsDataUtil() {}
	
	public static String generateQsForIn(int numQs) {
		String items = "(";
		for (int i = 0; i < numQs; i++) {
			if (i != 0)
				items += ", ";
			items += "?";
		}
		return items+")";
	}
}