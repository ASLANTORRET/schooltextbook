package com.example.aslan.schooltextbook;

import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Created by sst on 6/24/16.
 */
public class Utils {
    static String []vowels = {"a","e","i","o","u","y","а","е","о","ы","у","и","э","ю","я","іәұүө"};
    static Set<String> v;
    static {
        v = new TreeSet<String>();
        for ( String s: vowels )
            for ( Character c: s.toCharArray() )
                v.add(""+c);
    };
    public static String preprocess( String s, int width ) {
        StringBuffer sb = new StringBuffer();
        String []w = (s.replace("\n"," ")).split(" ");
        int i,j,k,n = w.length,t,l;
        for ( i = 0; i < n; i = j, sb.append("\n") ) {
            for ( k = 0, j = i; j < n; ) {
                if ( k+(l=w[j].length()) >= width ) break;
                k += l; ++j;
            }
            for ( t = i; t < j; sb.append(w[t++]+" ") );
        }
        return sb.toString();
    };
}

