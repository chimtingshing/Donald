package Donald.Statistics;

import java.util.*;
import Donald.*;

public class Statistics {

    //Find min
    public static double min(String titletofind) {
        int indexoftitletofind = Statistics.returnindexoftitle(titletofind);
        double minInMethod = 0;
        ArrayList<Double> columnInNum = new ArrayList();
        //check null content
        Null.checknull(titletofind);
        if (Donald.test.get(1).get(indexoftitletofind).matches("(.*)\\p{Digit}(.*)")) {
            for (int i = 1; i < Donald.test.size(); i++) {
                columnInNum.add(Double.parseDouble(Donald.test.get(i).get(indexoftitletofind)));
            }
            minInMethod = Collections.min(columnInNum);
        } else {
            System.out.println("This column is not numeric.");
        }
        return minInMethod;
    }

    //method to calculate maximum value in the data
    public static double max(String titletofind) {
        int indexoftitletofind = Statistics.returnindexoftitle(titletofind);
        double maxInMethod = 0;
        ArrayList<Double> columnInNum = new ArrayList();
        Null.checknull(titletofind);
        if (Donald.test.get(1).get(indexoftitletofind).matches("(.*)\\p{Digit}(.*)")) {
            for (int i = 1; i < Donald.test.size(); i++) {
                columnInNum.add(Double.parseDouble(Donald.test.get(i).get(indexoftitletofind)));
            }
            maxInMethod = Collections.max(columnInNum);
        } else {
            System.out.println("This column is not numeric.");
        }
        return maxInMethod;
    }

    //method to calculate range of the data
    public static double range(String titletofind) {
        double range = Statistics.max(titletofind) - Statistics.min(titletofind);
        return range;
    }

    //method to calculate the mean of the data
    public static double mean(String titletofind) {
        int indexoftitletofind = Statistics.returnindexoftitle(titletofind);
        double meanInMethod = 0;
        double total = 0;
        ArrayList<Double> columnInNum = new ArrayList();
        Null.checknull(titletofind);
        if (Donald.test.get(1).get(indexoftitletofind).matches("(.*)\\p{Digit}(.*)")) {
            for (int i = 1; i < Donald.test.size(); i++) {
                columnInNum.add(Double.parseDouble(Donald.test.get(i).get(indexoftitletofind)));
            }
            for (int i = 0; i < columnInNum.size(); i++) {
                total += columnInNum.get(i);
            }
            meanInMethod = (double) total / columnInNum.size();
        } else {
            System.out.println("This column is not numeric.");
        }
        return meanInMethod;
    }

    //general method to return index of the give title in test.csv
    public static int returnindexoftitle(String titletofind) {
        int index = 0;
        for (int j = 0; j < Donald.test.get(0).size(); j++) {
            if (titletofind.equals(Donald.test.get(0).get(j))) {
                index = j;
            }
        }
        return index;
    }

    //method to calculate median of the data
    public static double median(String titletofind) {
        int indexoftitletofind = Statistics.returnindexoftitle(titletofind);
        double medianinmethod = 0;
        ArrayList<Double> columninnum = new ArrayList();
        Null.checknull(titletofind);
        if (Donald.test.get(1).get(indexoftitletofind).matches("(.*)\\p{Digit}(.*)")) {
            for (int i = 1; i < Donald.test.size(); i++) {
                columninnum.add(Double.parseDouble(Donald.test.get(i).get(indexoftitletofind)));
            }
            Collections.sort(columninnum);
            int middle = columninnum.size() / 2;
            medianinmethod = columninnum.get(middle);
        } else {
            System.out.println("This column is not numeric.");
        }
        return medianinmethod;
    }

    //method to calculate the variance of the data
    public static double variance(String titletofind) {
        int indexoftitletofind = Statistics.returnindexoftitle(titletofind);
        double varianceinmethod = 0;
        double meaninmethod = Statistics.mean(titletofind);
        double sumofxminusxs = 0;
        Null.checknull(titletofind);
        if (Donald.test.get(1).get(indexoftitletofind).matches("(.*)\\p{Digit}(.*)")) {
            for (int i = 1; i < Donald.test.size(); i++) {
                sumofxminusxs += Math.pow(Double.parseDouble(Donald.test.get(i).get(indexoftitletofind)) - meaninmethod, 2);
            }
            varianceinmethod = sumofxminusxs / (Donald.test.size() - 2);
        } else {
            System.out.println("This column is not numeric");
        }
        return varianceinmethod;
    }

    public static double sd(String titletofind) {
        return Math.pow(variance(titletofind), 0.5);
    }

    //method to find mode integer
    public static String modeInteger(String titletofind) {
        int indexoftitletofind = Statistics.returnindexoftitle(titletofind);
        ArrayList<Double> columninnum = new ArrayList();
        Set<Double> modeSet = new HashSet<>();
        Null.checknull(titletofind);
        for (int i = 1; i < Donald.test.size(); i++) {
            columninnum.add(Double.parseDouble(Donald.test.get(i).get(indexoftitletofind)));
        }
        int maxFrequency = 0;
        int count = 0;
        boolean modeFound = false;
        Collections.sort(columninnum);
        for (int i = 0; i < columninnum.size(); i++) {
            count = Collections.frequency(columninnum, columninnum.get(i));
            if (maxFrequency != 0 && count != maxFrequency) {
                modeFound = true;
            }
            if (count > maxFrequency) {
                modeSet.clear();
                maxFrequency = count;
                modeSet.add(columninnum.get(i));
            } else if (count == maxFrequency) {
                modeSet.add(columninnum.get(i));
            }
        }

        if (!modeFound) {
            modeSet.clear();
        }
        return Arrays.toString(modeSet.toArray()).replace("[", "").replace("]", "");
    }

    //method to find mode for string
    public static String modeString(String titletofind) {
        int indexoftitletofind = Statistics.returnindexoftitle(titletofind);
        ArrayList<String> columninstring = new ArrayList();
        Set<String> modeSet = new HashSet<>();
        Null.checknullstring(titletofind);
        for (int i = 1; i < Donald.test.size(); i++) {
            columninstring.add(Donald.test.get(i).get(indexoftitletofind));
        }
        int maxFrequency = 0;
        int count = 0;
        boolean modeFound = false;
        Collections.sort(columninstring);
        for (int i = 0; i < columninstring.size(); i++) {
            count = Collections.frequency(columninstring, columninstring.get(i));
            if (maxFrequency != 0 && count != maxFrequency) {
                modeFound = true;
            }
            if (count > maxFrequency) {
                modeSet.clear();
                maxFrequency = count;
                modeSet.add(columninstring.get(i));
            } else if (count == maxFrequency) {
                modeSet.add(columninstring.get(i));
            }
        }

        if (!modeFound) {
            modeSet.clear();
        }

        return Arrays.toString(modeSet.toArray()).replace("[", "").replace("]", "");
    }

    //method to calculate the minmaxscaling
    public static ArrayList<Double> minmaxscaling(String titletofind) {
        int indexoftitletofind = Statistics.returnindexoftitle(titletofind);
        double min = Statistics.min(titletofind);
        double range = Statistics.range(titletofind);
        double temp = 0;
        ArrayList<Double> minmaxscaling = new ArrayList();
        
        for (int i = 1; i < Donald.test.size(); i++) {
            temp = ((Double.parseDouble(Donald.test.get(i).get(indexoftitletofind))) - min) / range;
            minmaxscaling.add(temp);
        }
        
        System.out.print("Min max scaling for " + Donald.test.get(0).get(indexoftitletofind) + ": \n");
        return minmaxscaling;
    }

    //method to calculate standard deviation scaling
    public static ArrayList<Double> sdscaling(String titletofind) {
        int indexoftitletofind = Statistics.returnindexoftitle(titletofind);
        double mean = Statistics.mean(titletofind);
        double sd = Statistics.sd(titletofind);
        double temp = 0;
        ArrayList<Double> sdscalinginmethod = new ArrayList();
        for (int i = 1; i < Donald.test.size(); i++) {
            temp = (Double.parseDouble(Donald.test.get(i).get(indexoftitletofind)) - mean) / sd;
            sdscalinginmethod.add(temp);
        }
        System.out.print("Standard scaling for " + Donald.test.get(0).get(indexoftitletofind) + ": \n");
        return sdscalinginmethod;
    }

    //return index of the title based on train.csv
    public static int returnindexofTraintitle(String titletofind) {
        int index = 0;
        for (int j = 0; j < Donald.train.get(0).size(); j++) {
            if (titletofind.equals(Donald.train.get(0).get(j))) {
                index = j;
            }
        }
        return index;
    }

    /*
    *
    *   ADDITIONAL CHALLENGE DIFFERENT SCALERS
    *   Robust Scaler
    */
    public static ArrayList<Double> robustScaler(String titletofind) {
        int indexoftitletofind = Statistics.returnindexoftitle(titletofind);

        ArrayList<Double> columninnum = new ArrayList();
        for (int i = 1; i < Donald.test.size(); i++) {
            columninnum.add(Double.parseDouble(Donald.test.get(i).get(indexoftitletofind)));
        }
        Collections.sort(columninnum);
        double q1 = columninnum.get(Donald.test.size() * 1 / 4);
        double q3 = columninnum.get(Donald.test.size() * 3 / 4);

        double temp = 0;
        ArrayList<Double> robustscaler = new ArrayList();

        for (int i = 1; i < Donald.test.size(); i++) {
            temp = ((Double.parseDouble(Donald.test.get(i).get(indexoftitletofind))) - q1) / (q3 - q1);
            robustscaler.add(temp);
        }

        return robustscaler;
    }
}