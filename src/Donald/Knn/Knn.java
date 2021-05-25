package Donald.Knn;

import Donald.Donald;
import Donald.Statistics.Statistics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Knn {

    //method to determine k value for knn
    public static double determineK(ArrayList<Double> DataSet) {
        double sizeDouble = DataSet.size();
        double root = Math.sqrt(sizeDouble);
        double rawK = root / 2;
        double num = Math.round(rawK);
        if (num % 2 != 0) {
            return num; //num
        } else {
            return num-1; //num-1
        }
    }

    //method to calculate the Knn-Regresssion
    public static ArrayList<Double> knnRegression(String[] title) {
        ArrayList<Double> returntrainset = new ArrayList();
        
        ArrayList<Double> printAgedata = new ArrayList();
        printAgedata = getColumnData(title[0]);
        ArrayList<Double> printMarkdata = new ArrayList();
        printMarkdata = getColumnData(title[1]);
        ArrayList<Double> printGradedata = new ArrayList();
        printGradedata = getColumnData("Grade");
        
        ArrayList<ArrayList<Double>> DataSet = new ArrayList();
        DataSet.add(printAgedata);
        DataSet.add(printMarkdata);
        DataSet.add(printGradedata);

        ArrayList<Double> printTrainMarkdata = new ArrayList();
        printTrainMarkdata = getTrainData(title[0]);
        ArrayList<Double> printTrainAgedata = new ArrayList();
        printTrainAgedata = getTrainData(title[1]);
        ArrayList<ArrayList<Double>> TrainSet = new ArrayList();
        TrainSet.add(printTrainAgedata);
        TrainSet.add(printTrainMarkdata);

        int numoftrain = TrainSet.get(0).size();
        for (int l = 0; l < numoftrain; l++) {
            ArrayList<Double> distances = new ArrayList();
            ArrayList<Double> distanceclone = new ArrayList();
            for (int i = 0; i < DataSet.get(0).size(); i++) {
                double distance = getDistance(DataSet, TrainSet, l, i);
                distances.add(distance);
                distanceclone.add(distance);
            }
            Collections.sort(distances);
            ArrayList<Double> class1 = new ArrayList();
            ArrayList<Double> class2 = new ArrayList();
            int k = (int) determineK(DataSet.get(0));
            List<Double> ShortestDistance = distances.subList(0, k);
            double sum = 0;
            for (double element : ShortestDistance) {
                int indexonclone = distanceclone.indexOf(element);
                sum += DataSet.get(2).get(indexonclone);
            }
            returntrainset.add((double) sum / k);
            
        }
        return returntrainset;
    }

    //method to do Knn-Classifier
    public static ArrayList<Double> knnclassifier(String[] title) {
        ArrayList<Double> returntrainset = new ArrayList();
        
        ArrayList<Double> printAgedata = new ArrayList();
        printAgedata = getColumnData(title[0]);
        ArrayList<Double> printMarkdata = new ArrayList();
        printMarkdata = getColumnData(title[1]);
        ArrayList<Double> printGradedata = new ArrayList();
        printGradedata = getColumnData("Grade");
        
        ArrayList<ArrayList<Double>> DataSet = new ArrayList();
        DataSet.add(printAgedata);
        DataSet.add(printMarkdata);
        DataSet.add(printGradedata);
        
        ArrayList<Double> printTrainMarkdata = new ArrayList();
        printTrainMarkdata = getTrainData(title[0]);
        ArrayList<Double> printTrainAgedata = new ArrayList();
        printTrainAgedata = getTrainData(title[1]);
        ArrayList<ArrayList<Double>> TrainSet = new ArrayList();
        TrainSet.add(printTrainAgedata);
        TrainSet.add(printTrainMarkdata);

        int numoftrain = TrainSet.get(0).size();
        for (int l = 0; l < numoftrain; l++) {
            ArrayList<Double> distances = new ArrayList();
            ArrayList<Double> distanceclone = new ArrayList();
            for (int i = 0; i < DataSet.get(0).size(); i++) {
                double distance = getDistance(DataSet, TrainSet, l, i);
                distances.add(distance);
                distanceclone.add(distance);
            }

            Collections.sort(distances);
            ArrayList<Double> class1 = new ArrayList();
            ArrayList<Double> class2 = new ArrayList();

            int k = (int) determineK(DataSet.get(0));
            List<Double> ShortestDistance = distances.subList(0, k);
            for (double element : ShortestDistance) {
                int count = 0;
                int indexonclone = distanceclone.indexOf(element);
                double[] nearestneighbour = new double[k];
                nearestneighbour[count] = DataSet.get(2).get(indexonclone);
                if (nearestneighbour[count] == 1) {
                    class1.add(nearestneighbour[count]);
                } else if (nearestneighbour[count] == 5) {
                    class2.add(nearestneighbour[count]);
                }
            }
            if (class1.size() > class2.size()) {
                returntrainset.add(1.0);
            } else {
                returntrainset.add(5.0);
            }
        }
        
        return returntrainset;
        
    }

    /*
     *
     *   error metrics
     *
     */
    //Classification accuracy
    public static void classificationerror() {
        //Donald.predict = CSV.readcsv(file, 2);
        int countofcorrectpredict = 0;
        
        ArrayList<Double> predictdata = getPredictData("Grade");
        for (int i = 0; i < Donald.knnclass.size(); i++) {
            if (Donald.knnclass.get(i).equals(predictdata.get(i))) {
                countofcorrectpredict++;
            }
        }
        double accuracy;
        accuracy = (double) countofcorrectpredict / Donald.knnclass.size();
        System.out.printf("The classification accuracy is: %.3f\n", accuracy);
        
        //F1 score
        double precision;
        precision = (double) countofcorrectpredict / Donald.knnclass.size();
        double recall;
        recall = (double) countofcorrectpredict / ((double) countofcorrectpredict + (predictdata.size() - Donald.knnclass.size()) );
        double f1 = 2 * (1 / ((1 / precision) + (1 / recall)) );
        System.out.printf("The F1 score is: %.3f\n", f1);
    }

    //Regresssion error matrics
    public static void regressionerror() {
        double sum = 0;
        ArrayList<Double> predictdata = getPredictData("Grade");
        //mean absoulte error
        for (int i = 0; i < Donald.knnregress.size(); i++) {
            sum += Math.abs(predictdata.get(i) - Donald.knnregress.get(i));
        }
        double meanabserror = sum / Donald.knnregress.size();
        //mean squared error
        sum = 0;
        for (int i = 0; i < Donald.knnregress.size(); i++) {
            sum += Math.pow(predictdata.get(i) - Donald.knnregress.get(i), 2);
        }
        double meansquarederror = sum / Donald.knnregress.size();
        System.out.printf("The mean absolute error of the K-nn regression : %.2f\n", meanabserror);
        System.out.printf("The mean squared error of the K-nn regression : %.2f\n", meansquarederror);
    }

    //method to get distance between the train point and data point
    public static double getDistance(ArrayList<ArrayList<Double>> data, ArrayList<ArrayList<Double>> train, int trainset, int row) {
        int length = data.size() - 1;
        double totalsum = 0;
        double[] sum = new double[length];
        double distance = 0;
        for (int i = 0; i < length; i++) {
            sum[i] = Math.pow((data.get(i).get(row) - train.get(i).get(trainset)), 2);
        }
        for (int i = 0; i < sum.length; i++) {
            totalsum += sum[i];
        }
        distance = Math.sqrt(totalsum);
        return distance;
    }

    //method to convert predict.csv to dataframe in column
    public static ArrayList<Double> getPredictData(String titletofind) {
        ArrayList<Double> Columninmethod = new ArrayList();
        int indexofcolumn = returnindexofPredicttitle(titletofind);
        for (int i = 1; i < Donald.predict.size(); i++) {
            Columninmethod.add(Double.parseDouble(Donald.predict.get(i).get(indexofcolumn)));
        }
        return Columninmethod;
    }
    
    //method to convert data.csv to dataframe in column
    public static ArrayList<Double> getColumnData(String titletofind) {
        ArrayList<Double> Columninmethod = new ArrayList();
        int indexofcolumn = Statistics.returnindexoftitle(titletofind);
        for (int i = 1; i < Donald.test.size(); i++) {
            Columninmethod.add(Double.parseDouble(Donald.test.get(i).get(indexofcolumn)));
        }
        return Columninmethod;
    }

    //method to convert train.csv to dataframe in column
    public static ArrayList<Double> getTrainData(String titletofind) {
        ArrayList<Double> Columninmethod = new ArrayList();
        int indexofcolumn = Statistics.returnindexofTraintitle(titletofind);
        for (int i = 1; i < Donald.train.size(); i++) {
            Columninmethod.add(Double.parseDouble(Donald.train.get(i).get(indexofcolumn)));
        }
        return Columninmethod;
    }

    //method to return the index of the column of given title in predict.csv
    public static int returnindexofPredicttitle(String titletofind) {
        int index = 0;
        for (int j = 0; j < Donald.predict.get(0).size(); j++) {
            if (titletofind.equals(Donald.predict.get(0).get(j))) {
                index = j;
            }
        }
        return index;
    }
    
}