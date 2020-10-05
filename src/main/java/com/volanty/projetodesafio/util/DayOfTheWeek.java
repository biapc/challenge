package com.volanty.projetodesafio.util;

import java.time.DateTimeException;

public enum DayOfTheWeek {
	
	domingo, segunda, terca, quarta, quinta, sexta, sabado;
	
	private static final DayOfTheWeek[] ENUMS = DayOfTheWeek.values();
	
	public static DayOfTheWeek of(int dayOfWeek) {
        if (dayOfWeek < 1 || dayOfWeek > 7) {
            throw new DateTimeException("Invalid value for DayOfTheWeek: " + dayOfWeek);
        }
        return ENUMS[dayOfWeek - 1];
    }

}
