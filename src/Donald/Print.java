package Donald;

import Donald.Knn.*;
import Donald.Statistics.Statistics;
import java.util.ArrayList;

public class Print {

    //print the knn classifier
    public static void printKnnClassifier(String[] title) {
        Donald.knnclass = Knn.knnclassifier(title);
        System.out.println("Knn Classifier for train set:");
        for (double x : Donald.knnclass) {
            System.out.println(x);
        }
    }

    //print the mode in integer
    public static void printmodeInteger(String title) {
        String modeofcolumn = Statistics.modeInteger(title);
        System.out.printf("The mode of %s is %s\n", title, modeofcolumn);
    }

    //print the knn regression
    public static void printKnnRegression(String[] title) {
        Donald.knnregress = Knn.knnRegression(title);
        System.out.println("Knn Regression for train set: ");
        for (double x : Donald.knnregress) {
            System.out.println(x);
        }
    }

    //print median
    public static void printMedian(String title) {
        double medianofcolumn = Statistics.median(title);
        System.out.printf("The median of %s is %.1f\n", title, medianofcolumn);
    }

    //print variance
    public static void printVariance(String title) {
        double variance = Statistics.variance(title);
        System.out.printf("The variance of %s is %.2f\n", title, variance);
    }

    //print standard deviation
    public static void printSD(String title) {
        double sd = Statistics.sd(title);
        System.out.printf("The standard deviation of %s is %.2f\n", title, sd);
    }

    //print min
    public static void printMin(String title) {
        double minofcolumn = Statistics.min(title);
        System.out.printf("The minimun of %s is %.1f\n", title, minofcolumn);
    }

    //print mean
    public static void printMean(String title) {
        double meanofcolumn = Statistics.mean(title);
        System.out.printf("The mean of %s is %.1f\n", title, meanofcolumn);
    }

    //print min max scaling
    public static void printMinMaxScaling(String title) {
        ArrayList<Double> minmaxscaling = new ArrayList();
        minmaxscaling = Statistics.minmaxscaling(title);
        for (int i = 0; i < minmaxscaling.size(); i++) {
            if (i != minmaxscaling.size() - 1) {
                System.out.print(minmaxscaling.get(i) + ", ");
            } else {
                System.out.println(minmaxscaling.get(i)+ "\n");
            }
        }
    }

    //print max
    public static void printMax(String title) {
        double maxofcolumn = Statistics.max(title);
        System.out.printf("The maximum of %s is %.1f\n", title, maxofcolumn);
    }

    //print range
    public static void printRange(String title) {
        double rangeofcolumn = Statistics.range(title);
        System.out.printf("The range of %s is %.1f\n", title, rangeofcolumn);
    }

    //print mode in String
    public static void printmodeString(String title) {
        String modeofcolumnS = Statistics.modeString(title);
        System.out.printf("The mode of %s is %s\n", title, modeofcolumnS);
    }

    //print standard scaling
    public static void printSS(String title) {
        ArrayList<Double> sdscaling = new ArrayList();
        sdscaling = Statistics.sdscaling(title);
        for (int i = 0; i < sdscaling.size(); i++) {
            if (i != sdscaling.size() - 1) {
                System.out.print(sdscaling.get(i) + ", ");
            } else {
                System.out.println(sdscaling.get(i) + "\n");
            }
        }
    }
    
    /*
    *
    *   ADDITIONAL CHALLENGE PRINT NORMALIZATION (SCALER)
    *
    */
    //print normalization
    public static void printRobustScaler(String title) {
        ArrayList<Double> robustscaler = new ArrayList();
        robustscaler = Statistics.robustScaler(title);
        System.out.println("The robust scaler of the data : ");
        for (int i = 0; i < robustscaler.size(); i++) {
            if (i != robustscaler.size() - 1) {
                System.out.print(robustscaler.get(i) + ", ");
            } else {
                System.out.print(robustscaler.get(i));
            }
        }
    }
}