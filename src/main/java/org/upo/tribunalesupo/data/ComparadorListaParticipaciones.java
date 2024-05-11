package org.upo.tribunalesupo.data;

import java.util.Comparator;

public class ComparadorListaParticipaciones implements Comparator<Object[]> {

    @Override
    public int compare(Object[] o1, Object[] o2) {
        Long num1 = (Long) o1[1];
        Long num2 = (Long) o2[1];
        return num1.compareTo(num2);
    }
}
