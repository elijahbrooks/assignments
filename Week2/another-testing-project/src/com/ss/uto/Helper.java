package com.ss.uto;

public abstract class Helper {
    public String[] pushToArray(String element, String[] array){
        String[] newArray = new String[array.length];

        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i];
        }
        newArray[array.length - 1] = element;

        return newArray;
    }
}
