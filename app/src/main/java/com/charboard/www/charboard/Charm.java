package com.charboard.www.charboard;

/**
 * Created by Steve on 12/17/2014.
 */

import java.util.Random;

public class Charm {

    private int[] colour;
    private Random rand;

    public Charm(){
        colour = new int[7];

        rand = new Random();

        for(int i=0; i < colour.length; i++)
            colour[i]= rand.nextInt(4)+2;
    }

    public int getCharm( int position){
        return colour[position];
    }

    public int pop(){
        int temp;
        temp = colour[0];
        for(int i = 0; i < (colour.length - 1);i++)
            colour[i]= colour[i+1];
        colour[colour.length - 1]= rand.nextInt(4)+2;
        return temp;
    }

}
