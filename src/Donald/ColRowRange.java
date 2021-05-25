package Donald;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ColRowRange {
    //method to print out specific row to specific row
    public static void rowRange(int firstrow, int secondrow) {
        ArrayList<ArrayList<String>> temp = new ArrayList();

        for (int i = firstrow - 1; i < secondrow; i++) {
            temp.add(Donald.test.get(i));
        }

        Donald.test.clear();
        Donald.test = (ArrayList) temp.clone();
        CSV.printdataframe(Donald.test);
    }

    //method to print out specific column (chim)
    public static void colRange(String[] columnName) {
        int columnLength = columnName.length;
        ArrayList<Integer> save = new ArrayList();
        ArrayList<ArrayList<String>> temp = new ArrayList();
        ArrayList<String> temp2 = new ArrayList();
        ArrayList<String> temp3 = new ArrayList();

        for (int i = 0; i < columnLength; i++) {
            for (int j = 0; j < Donald.test.get(0).size(); j++) {
                if (columnName[i].equals(Donald.test.get(0).get(j))) {
                    save.add(j);
                }
            }
        }

        for (int i = 0; i < Donald.test.size(); i++) {
            temp.add(null);
        }

        for (int i = 0; i < Donald.test.size(); i++) {
            for (int j = 0; j < Donald.test.get(0).size(); j++) {
                for (int k = 0; k < save.size(); k++) {
                    if (j == save.get(k)) {
                        temp2.add(Donald.test.get(i).get(j));
                    }
                }
            }
            temp3 = (ArrayList) temp2.clone();
            if (temp2.isEmpty()) {
                continue;
            } else {
                temp.set(i, temp3);
            }
            temp2.clear();
        }
        
        Donald.test.clear();
        Donald.test = (ArrayList) temp.clone();
        
        CSV.printdataframe(Donald.test);
    }

    //method to sort the data
    public static void sort(String titletosort) {
        int indexoftitle = 0;
        ArrayList<ArrayList<String>> hold = new ArrayList();
        for (int i = 0; i < Donald.test.get(0).size(); i++) {
            if (titletosort.equals(Donald.test.get(0).get(i))) {
                indexoftitle = i;
            }
        }
        for (int pass = 1; pass < Donald.test.size() - 1; pass++) {
            for (int i = 0; i < Donald.test.size() - 1 - pass; i++) {
                int n = i + 1;
                if (Donald.test.get(1).get(indexoftitle).matches("(.*)\\p{Digit}(.*)")) {
                    if (Integer.parseInt(Donald.test.get(n).get(indexoftitle)) > Integer.parseInt(Donald.test.get(n + 1).get(indexoftitle))) {
                        hold.add(Donald.test.get(n));
                        Donald.test.set(n, Donald.test.get(n + 1));
                        Donald.test.set(n + 1, hold.get(0));
                        hold.clear();
                    }
                } else {
                    if (Donald.test.get(n).get(indexoftitle).compareToIgnoreCase(Donald.test.get(n + 1).get(indexoftitle)) > 0) {
                        hold.add(Donald.test.get(n));
                        Donald.test.set(n, Donald.test.get(n + 1));
                        Donald.test.set(n + 1, hold.get(0));
                        hold.clear();
                    }
                }
            }
        }
        CSV.printdataframe(Donald.test);
    }

    //method to concate 2 csv column which has satisfied the requirement
    public static void columnConcatenation(String file) {
        try (final BufferedReader bf = new BufferedReader(new FileReader(file))) {
            ArrayList<ArrayList<String>> test3 = new ArrayList();
            ArrayList<String> test4 = new ArrayList();
            String line1;
            String[] oneline1 = {" "};
            int totalrow1 = 0;
            bf.mark(123125123);
            while ((line1 = bf.readLine()) != null) {
                oneline1 = line1.split(",");
                totalrow1++;
            }
            bf.reset();
            bf.mark(999999);
            for (int i = 0; i < totalrow1; i++) {
                line1 = bf.readLine();
                oneline1 = line1.split(",");
                for (String x : oneline1) {
                    test4.add(x);
                }
                ArrayList test5 = new ArrayList();
                test5 = (ArrayList) test4.clone();
                test4.clear();
                test3.add(test5);
            }

            int n = 0;
            if (totalrow1 == Donald.totalrow) {
                for (int i = 0; i < test3.get(0).size(); i++) {
                    for (int j = 0; j < Donald.totalrow; j++) {
                        Donald.test.get(j).add(test3.get(j).get(n));
                    }
                    n++;
                }
                CSV.printdataframe(Donald.test);
            } else {
                System.out.println("Error, axis in columns do not match");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void rowConcatenation(String file) {
        try (final BufferedReader bf = new BufferedReader(new FileReader(file))) {
            ArrayList<ArrayList<String>> test6 = new ArrayList();
            ArrayList<String> test7 = new ArrayList();
            String line1;
            String[] oneline1 = {" "};
            int totalrow1 = 0;
            int totalcol1 = 0;
            bf.mark(123125123);
            while ((line1 = bf.readLine()) != null) {
                oneline1 = line1.split(",");
                if (oneline1.length > totalcol1) {
                    totalcol1 = oneline1.length;
                }
                totalrow1++;
            }
            bf.reset();
            for (int i = 0; i < totalrow1; i++) {
                line1 = bf.readLine();
                oneline1 = line1.split(",");
                for (String x : oneline1) {
                    test7.add(x);
                }
                ArrayList test8 = new ArrayList();
                test8 = (ArrayList) test7.clone();
                test7.clear();
                test6.add(test8);
            }

            int j = 0;
            if (totalcol1 == Donald.totalcol) {
                for (int i = 1; i < test6.size(); i++, j++) {
                    if (test6.get(0).get(j).equals(Donald.test.get(0).get(j))) {
                        Donald.test.add(test6.get(i));
                    }
                }
                CSV.printdataframe(Donald.test);
            } else {
                System.out.println("Error in match axis");
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}