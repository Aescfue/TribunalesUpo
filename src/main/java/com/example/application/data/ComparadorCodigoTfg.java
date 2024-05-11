package com.example.application.data;

import java.util.Comparator;

public class ComparadorCodigoTfg implements Comparator<Tfg> {

    @Override
    public int compare(Tfg tfg1, Tfg tfg2) {
        
        Integer anno1 = Integer.parseInt(tfg1.getCodigo().substring(0,tfg1.getCodigo().indexOf("-")));
        Integer anno2 = Integer.parseInt(tfg2.getCodigo().substring(0,tfg2.getCodigo().indexOf("-")));

        String letra1 = String.valueOf(tfg1.getCodigo().charAt(tfg1.getCodigo().lastIndexOf("-")+1));
        String letra2 = String.valueOf(tfg2.getCodigo().charAt(tfg2.getCodigo().lastIndexOf("-")+1));

        Integer num1 = Integer.parseInt(tfg1.getCodigo().substring(tfg1.getCodigo().lastIndexOf("-")+2));
        Integer num2 = Integer.parseInt(tfg2.getCodigo().substring(tfg2.getCodigo().lastIndexOf("-")+2));

        if(!num1.equals(num2)){
            return num1.compareTo(num2);
        } else if (!anno1.equals(anno2)) {
            return anno1.compareTo(anno2);
        } else{
            return letra1.compareTo(letra2);
        }

    }
    
}
