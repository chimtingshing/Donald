package Donald;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class CSV {
    //read csv //the int variable is to set which arraylist to save the file content
    public static ArrayList<ArrayList<String>> readcsv(String file, int count) {
        //try to catch the csv file from the folder
        try (final BufferedReader bg = new BufferedReader(new FileReader(file))) {
            ArrayList<String> readline = new ArrayList();
            String line1;
            String[] oneline1 = {" "};
            bg.mark(9999999);  //mark to reset the bufferedreader to the top of the file
            if (count == 0) {
                while ((line1 = bg.readLine()) != null) {
                    oneline1 = line1.split(",");
                    Donald.totalcol = oneline1.length;
                    Donald.totalrow++;
                }
                bg.reset();
                for (int i = 0; i < Donald.totalrow; i++) {
                    line1 = bg.readLine();
                    oneline1 = line1.split(",");
                    for (String x : oneline1) {
                        readline.add(x);
                    }
                    
                    while(readline.size() != Donald.totalcol){
                        readline.add("");
                    }
                    
                    ArrayList readlinecopy = new ArrayList();
                    readlinecopy = (ArrayList) readline.clone();
                    readline.clear();
                    Donald.test.add(readlinecopy);
                }
            } else if (count == 1) {
                while ((line1 = bg.readLine()) != null) {
                    oneline1 = line1.split(",");
                    Donald.totalcol1 = oneline1.length;
                    Donald.totalrow1++;
                }
                bg.reset();
                for (int i = 0; i < Donald.totalrow1; i++) {
                    line1 = bg.readLine();
                    oneline1 = line1.split(",");
                    for (String x : oneline1) {
                        readline.add(x);
                    }
                    ArrayList readlinecopy = new ArrayList();
                    readlinecopy = (ArrayList) readline.clone();
                    readline.clear();
                    Donald.train.add(readlinecopy);
                }
            } else {
                while ((line1 = bg.readLine()) != null) {
                    oneline1 = line1.split(",");
                    Donald.totalcol2 = oneline1.length;
                    Donald.totalrow2++;
                }
                bg.reset();
                for (int i = 0; i < Donald.totalrow2; i++) {
                    line1 = bg.readLine();
                    oneline1 = line1.split(",");
                    for (String x : oneline1) {
                        readline.add(x);
                    }
                    ArrayList readlinecopy = new ArrayList();
                    readlinecopy = (ArrayList) readline.clone();
                    readline.clear();
                    Donald.predict.add(readlinecopy);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (count == 0) {
            return Donald.test;
        } else if (count == 1) {
            return Donald.train;
        } else {
            return Donald.predict;
        }
    }

    //printthedata inside the arraylist
    public static void printdataframe(ArrayList<ArrayList<String>> file) {
        for (ArrayList<String> value : file) {
            for (String y : value) {
                System.out.print(y + "\t\t");
            }
            System.out.println();
        }
    }

    //save dataframe in csv file
    public static void writeCSV(String filepath) {
        try {
            FileWriter fw = new FileWriter(filepath);
            PrintWriter pw = new PrintWriter(fw);
            for (int i=0; i < Donald.test.size(); i++){
                for (int j=0; j < Donald.test.get(0).size(); j++){
                    pw.print(Donald.test.get(i).get(j) + ",");
                }
                pw.println();
            }
            pw.flush();
            pw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}