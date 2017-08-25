package com.controlderuta.guardianroute.Model;

import java.util.ArrayList;

/**
 * Created by eduin on 25/08/2017.
 */

public class ArrayMarkets {

    private ArrayList<Markets> arraymarkets;

    public ArrayMarkets() {

        arraymarkets = new ArrayList<Markets>();

    }

    public ArrayList<Markets> getMarkets() {
        return (ArrayList<Markets>)arraymarkets.clone();
    }

    public Markets getMarkets(int posicion){
        return arraymarkets.get(posicion);
    }


    public void addMarkets(Markets markets){
        arraymarkets.add(markets);
    }
}


