package Donald;

import Donald.Statistics.Statistics;
import java.util.*;

public class Null {

    //method to remove data which contain null value
    public static void dropNull(String[] todropNull) {
        int numberofnulltitletodrop = todropNull.length;
        int[] indexofnulltitletodrop = new int[numberofnulltitletodrop];
        for (int i = 0; i < numberofnulltitletodrop; i++) {
            for (int j = 0; j < Donald.test.get(0).size(); j++) {
                if (todropNull[i].equals(Donald.test.get(0).get(j))) {
                    indexofnulltitletodrop[i] = j;
                }
            }
        }
        
        if (numberofnulltitletodrop == 1) {
            for (int i = 1; i < Donald.test.size(); i++) {
                if (Donald.test.get(i).get(indexofnulltitletodrop[0]).equals("")) {
                    Donald.test.remove(i);
                }
            }
        } else {
            for (int j = 1; j < Donald.test.size(); j++) {
                if (Donald.test.get(j).get(indexofnulltitletodrop[0]).equals("")) {
                    for (int i = 1; i < indexofnulltitletodrop.length; i++) {
                        if (Donald.test.get(j).get(indexofnulltitletodrop[i]).equals("")) {
                            Donald.test.remove(j);
                        }
                    }
                }
            }
        }
        CSV.printdataframe(Donald.test);
    }

    //method to remove duplicate value in the data
    public static void dropDuplicates(String[] todroptitle, String tokeep) {
        int numberoftitletodrop = todroptitle.length;
        int[] indexoftitletodrop = new int[numberoftitletodrop];
        for (int i = 0; i < numberoftitletodrop; i++) {
            for (int j = 0; j < Donald.test.get(0).size(); j++) {
                if (todroptitle[i].equals(Donald.test.get(0).get(j))) {
                    indexoftitletodrop[i] = j;
                }
            }
        }

        if (tokeep.equalsIgnoreCase("first")) {
            for (int i = 0; i < numberoftitletodrop; i++) {
                for (int j = 1; j < Donald.test.size(); j++) {
                    for (int k = 1; k < Donald.test.size(); k++) {
                        if (j == k) {
                            if (k + 1 < Donald.test.size()) {
                                k++;
                            } else {
                                continue;
                            }
                        }
                        if (Donald.test.get(j).get(indexoftitletodrop[i]).equals(Donald.test.get(k).get(indexoftitletodrop[i]))) {
                            Donald.test.remove(k);
                            j = 1;
                            k = 1;
                        }
                    }
                }
            }
        } else if (tokeep.equalsIgnoreCase("last")) {
            for (int i = 0; i < numberoftitletodrop; i++) {
                for (int j = 1; j < Donald.test.size(); j++) {
                    for (int k = 1; k < Donald.test.size(); k++) {
                        if (j == k) {
                            if (k + 1 < Donald.test.size()) {
                                k++;
                            } else {
                                continue;
                            }
                        }
                        if (Donald.test.get(j).get(indexoftitletodrop[i]).equals(Donald.test.get(k).get(indexoftitletodrop[i]))) {
                            Donald.test.remove(j);
                            j = 1;
                            k = 1;
                        }
                    }
                }
            }
        } else if (tokeep.equalsIgnoreCase("none")) {
            ArrayList keep = new ArrayList();
            for (int i = 0; i < numberoftitletodrop; i++) {
                for (int j = 1; j < Donald.test.size(); j++) {
                    for (int k = 1; k < Donald.test.size(); k++) {
                        if (j == k) {
                            if (k + 1 < Donald.test.size()) {
                                k++;
                            } else {
                                continue;
                            }
                        }
                        if (Donald.test.get(j).get(indexoftitletodrop[i]).equals(Donald.test.get(k).get(indexoftitletodrop[i]))) {
                            keep.add(Donald.test.get(j).get(indexoftitletodrop[i]));
                        }
                    }
                }
            }
            for (int i = 0; i < numberoftitletodrop; i++) {
                for (int j = 1; j < Donald.test.size(); j++) {
                    for (int m = 0; m < keep.size(); m++) {
                        if (Donald.test.get(j).get(indexoftitletodrop[i]).equals(keep.get(m))) {
                            Donald.test.remove(j);
                            j--;
                            break;
                        }
                    }
                }
            }
        } else {
            HashSet<String> keep = new HashSet<String>();
            int numToKeep = Integer.parseInt(tokeep);
            for (int i = 0; i < numberoftitletodrop; i++) {
                for (int j = 1; j < Donald.test.size(); j++) {
                    for (int k = 1; k < Donald.test.size(); k++) {
                        if (j == k) {
                            if (k + 1 < Donald.test.size()) {
                                k++;
                            } else {
                                continue;
                            }
                        }
                        if (Donald.test.get(j).get(indexoftitletodrop[i]).equals(Donald.test.get(k).get(indexoftitletodrop[i]))) {
                            keep.add(Donald.test.get(j).get(indexoftitletodrop[i]));
                        }
                    }
                }
            }

            String str = keep.toString();
            str = str.replace("[", "").replace("]", "").replaceAll("\\s", "");
            String[] keep2 = str.split(",");
            int count = 0;

            for (int i = 0; i < numberoftitletodrop; i++) {
                for (int m = 0; m < keep2.length; m++) {
                    for (int j = 1; j < Donald.test.size(); j++) {
                        if (Donald.test.get(j).get(indexoftitletodrop[i]).equals(keep2[m])) {
                            count++;
                            if (count == numToKeep + 2) {
                                count -= 2;
                            }
                            if (count != numToKeep) {
                                Donald.test.remove(j);
                                j = 0;
                            }
                        }
                    }
                    count = 0;
                }
            }
        }
        CSV.printdataframe(Donald.test);
    }

    //method to check if the data contain null element in integer variable
    public static void checknull(String titletofind) {
        Scanner s = new Scanner(System.in);
        int indexoftitletofind = Statistics.returnindexoftitle(titletofind);
        ArrayList<Integer> indexofnull = new ArrayList();
        for (int i = 1; i < Donald.test.size(); i++) {
            if (Donald.test.get(i).get(indexoftitletofind).equals("")) {
                indexofnull.add(i);
            }
        }

        String choose = "";
        boolean bool = true;

        if (indexofnull.size() != 0) {
            do {
                System.out.print("Enter decimal or integer value to fill for empty column: ");
                choose = s.nextLine();
                if (choose.equalsIgnoreCase("decimal") || choose.equalsIgnoreCase("integer")) {
                    bool = false;
                }
            } while (bool);
        }

        for (int k = 0; k < indexofnull.size(); k++) {
            if (choose.equalsIgnoreCase("integer")) {
                System.out.printf("Enter value to fill in the missing value in row %d column %d: ", (indexofnull.get(k) + 1), (indexoftitletofind + 1));
                int numtofill = s.nextInt();
                Donald.test.get(indexofnull.get(k)).set(indexoftitletofind, Integer.toString(numtofill));
            } else if (choose.equalsIgnoreCase("decimal")) {
                System.out.printf("Enter value to fill in the missing value in row %d column %d: ", (indexofnull.get(k) + 1), (indexoftitletofind + 1));
                double numtofill = s.nextDouble();
                Donald.test.get(indexofnull.get(k)).set(indexoftitletofind, Double.toString(numtofill));
            }
        }
    }

    //method to check if the data contain null element in string variable
    public static void checknullstring(String titletofind) {
        Scanner s = new Scanner(System.in);
        int indexoftitletofind = Statistics.returnindexoftitle(titletofind);
        ArrayList<Integer> indexofnull = new ArrayList();
        for (int i = 1; i < Donald.test.size(); i++) {
            if (Donald.test.get(i).get(indexoftitletofind).equals("")) {
                indexofnull.add(i);
            }
        }
        for (int k = 0; k < indexofnull.size(); k++) {
            System.out.printf("Enter value to fill in the missing String in row %d column %d: ", (indexofnull.get(k) + 1), (indexoftitletofind + 1));
            String numtofill = s.nextLine();
            Donald.test.get(indexofnull.get(k)).set(indexoftitletofind, numtofill);
        }
    }
}