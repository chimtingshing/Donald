package Donald.SQL;

import Donald.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.*;

public class Driver {

    private static String columnName[], columnType[];
    private static Scanner input = new Scanner(System.in);
    private static String driver, url, username, password;

    public Driver(String driver, String url, String username, String password) {
        this.driver = driver;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    //gets connection to the mysql server
    public Connection getConnection() throws Exception {
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, username, password);
            return conn;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    //create table from Donald.test
    //then fill the table straight away through fillEmptyTable method
    public void createTable(String tableName) throws Exception {
        try {
            //get connection
            Connection con = getConnection();

            //get number of columns, the name for column and type
            int columnLength = Donald.test.get(0).size();
            columnName = new String[columnLength];
            columnType = new String[columnLength];
            String sql = "CREATE TABLE " + tableName + " (";

            //fill in column type as empty so that it is not null
            for (int i = 0; i < columnType.length; i++) {
                columnType[i] = "";
            }

            //insert column names from first row into column name
            for (int i = 0; i < columnName.length; i++) {
                columnName[i] = Donald.test.get(0).get(i);
            }

            //insert column type into array
            for (int i = 0; i < columnType.length; i++) {
                if (Donald.test.get(1).get(i).matches("(.*)\\p{Digit}(.*)")) {
                    columnType[i] = "int";
                } else {
                    columnType[i] = "varchar";
                }
            }

            //create sql statement through loops
            //check for type through columnType
            //Primary key is first column (auto)
            //"CREATE TABLE tableName (id INTEGER NOT NULL, name VARCHAR(255), PRIMARY KEY (id))"
            for (int i = 0; i < columnName.length; i++) {
                sql += columnName[i] + " ";

                if (columnType[i].equals("int")) {
                    sql += "INTEGER";
                } else {
                    sql += "VARCHAR(255)";
                }

                if (i == 0) {
                    sql += " not NULL, ";
                } else if (i > 0 && i <= columnName.length - 2) {
                    sql += ", ";
                } else {
                    sql += ")";
                }
            }

            //make a prepared statement and execute it, then fill the empty table with the data 
            PreparedStatement create = con.prepareStatement(sql);
            create.executeUpdate();
            System.out.println("Table is created");
            fillEmptyTable(tableName);
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //fill an empty table with Dataframe from Donald.test
    public void fillEmptyTable(String tableName) throws Exception {
        try {
            //get connection
            Connection con = getConnection();

            //start creating the sql statement through loops
            //String str for values (?,?)
            //E.g. String query = "insert into tableName (variable1, variable2) values (?, ?)" 
            String sql = "INSERT INTO " + tableName + " (";
            String str = "";

            //enter variable into the sql statement
            for (int i = 0; i < columnName.length; i++) {
                if (i == columnName.length - 1) {
                    sql += columnName[i];
                } else {
                    sql += columnName[i] + ", ";
                }
            }

            //Enter values into str string
            for (int i = 0; i < columnName.length; i++) {
                if (i == columnName.length - 1) {
                    str += "?)";
                } else {
                    str += "?,";
                }
            }

            //get full sql statement
            sql += ") values (" + str;

            //make a prepared statement named fill
            PreparedStatement fill = con.prepareStatement(sql);

            //j is row
            //i is column
            //if statement to know the type of value in the column
            //either fill.setInt or fill.setString based on numbers or words
            //execute the prepared statement for each row
            for (int j = 1; j < Donald.test.size(); j++) {
                for (int i = 0; i < columnType.length; i++) {
                    if (columnType[i].equalsIgnoreCase("int")) {
                        fill.setInt(i + 1, Integer.parseInt(Donald.test.get(j).get(i)));

                    } else {
                        fill.setString(i + 1, Donald.test.get(j).get(i));
                    }
                }
                fill.execute();
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            System.out.println("Table is filled");
        }
    }

    //read existing table into dataframe into Donald.test
    public void readTable(String tableName) throws Exception {
        try {
            //create 2 array list, readData to store the data into test
            //temp as temporary array list to store readData
            ArrayList<String> readData = new ArrayList();
            ArrayList<String> temp = new ArrayList();
            Donald.test.clear();

            //get connection
            Connection con = getConnection();

            //create a statement
            Statement statement = con.createStatement();

            //select to select all columns
            //ResultSet is to get columns
            //ResultSetMetaData is to get the information about columns from ResultSet
            String select = "SELECT * FROM " + tableName;
            ResultSet results = statement.executeQuery(select);
            ResultSetMetaData metadata = results.getMetaData();
            int columnCount = metadata.getColumnCount();

            //set columnName and columnType
            columnName = new String[columnCount];
            columnType = new String[columnCount];

            //get column name and type
            //read data adds the column namae
            for (int i = 1; i <= columnCount; i++) {
                columnName[i - 1] = metadata.getColumnName(i);
                columnType[i - 1] = metadata.getColumnTypeName(i);
                readData.add(columnName[i - 1]);
            }

            //clone read data and insert the clone into test
            temp = (ArrayList) readData.clone();
            readData.clear();
            Donald.test.add(temp);

            //i is to control index of column
            //add the value of the column into arraylist readData, then clone and add into test
            int i = 0;
            while (results.next()) {
                while (i < columnCount) {
                    if (columnType[i].equalsIgnoreCase("int")) {
                        readData.add(results.getString(columnName[i].toString()));
                        i++;
                    } else {
                        readData.add(results.getString(columnName[i]));
                        i++;
                    }
                }
                i = 0;
                temp = (ArrayList) readData.clone();
                readData.clear();
                Donald.test.add(temp);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //Insert data into the table in database, N is number of records to be added
    public void insertData(String tableName, int N) throws Exception {
        try {
            //get connection
            Connection con = getConnection();

            //start creating the sql statement through loops
            //String str for values (?,?)
            //E.g. String query = "insert into tableName (variable1, variable2) values (?, ?)" 
            String sql = "insert into " + tableName + " (";
            String str = "";

            //get the column name, type and count for both dataframe
            Statement statement = con.createStatement();
            String select = "SELECT * FROM " + tableName;
            ResultSet results = statement.executeQuery(select);
            ResultSetMetaData metadata = results.getMetaData();
            int columnCount = metadata.getColumnCount();

            //initialize the column name and type for both dataframe
            columnName = new String[columnCount];
            columnType = new String[columnCount];

            //get the column name and type through metadata
            for (int i = 1; i <= columnCount; i++) {
                columnName[i - 1] = metadata.getColumnName(i);
                columnType[i - 1] = metadata.getColumnTypeName(i);
            }

            //enter variable into the sql statement
            for (int i = 0; i < columnName.length; i++) {
                if (i == columnName.length - 1) {
                    sql += columnName[i];
                } else {
                    sql += columnName[i] + ", ";
                }
            }

            //Enter values into str string
            for (int i = 0; i < columnName.length; i++) {
                if (i == columnName.length - 1) {
                    str += "?)";
                } else {
                    str += "?,";
                }
            }

            //get full sql statement
            sql += ") values (" + str;

            //make a prepared statement named fill
            PreparedStatement fill = con.prepareStatement(sql);

            //insert data loop
            //count is the set which column, noOfRecord to control how many times
            int count = 1;
            int noOfRecord = 0;
            while (noOfRecord < N) {
                for (int i = 0; i < columnCount; i++) {
                    if (columnType[i].equalsIgnoreCase("int")) {
                        System.out.print("Enter data for " + columnName[i] + ": ");
                        int data = input.nextInt();
                        input.nextLine();
                        fill.setInt(count, data);
                    } else {
                        System.out.print("Enter data for " + columnName[i] + ": ");
                        String data2 = input.nextLine();
                        fill.setString(count, data2);
                    }
                    count++;
                }
                count = 1;
                fill.execute();
                System.out.println("Data inserted");
                noOfRecord++;
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //update existing data in the table in the database, N is number of records to be updated
    public void updateData(String tableName, int N) throws Exception {
        try {
            Connection con = getConnection();

            //get the column name, type and count for both dataframe
            Statement statement = con.createStatement();
            String select = "SELECT * FROM " + tableName;
            ResultSet results = statement.executeQuery(select);
            ResultSetMetaData metadata = results.getMetaData();
            int columnCount = metadata.getColumnCount();

            //initialize the column name and type for both dataframe
            columnName = new String[columnCount];
            columnType = new String[columnCount];

            //get the column name and type through metadata
            for (int i = 1; i <= columnCount; i++) {
                columnName[i - 1] = metadata.getColumnName(i);
                columnType[i - 1] = metadata.getColumnTypeName(i);
            }

            int count = 0;
            while (count < N) {
                //initialize boolean to check is find integer or string
                //also check is update integer or string
                //String bool to check whether record exist
                boolean integerFind = false;
                boolean integerUpdate = false;
                String bool = "SELECT * FROM " + tableName;

                //enter column to find
                //add statement to bool
                System.out.print("Enter column which acts as condition: ");
                String columnFind = input.nextLine();
                bool = bool + " where " + columnFind + " = ";

                //data for int column, data2 for string column
                //enter data for column and check
                //add statement to bool
                int data = 0;
                String data2 = "";
                System.out.print("Enter data for that column: ");
                for (int i = 0; i < columnName.length; i++) {
                    if (columnName[i].equalsIgnoreCase(columnFind)) {
                        if (columnType[i].equalsIgnoreCase("int")) {
                            data = input.nextInt();
                            input.nextLine();
                            integerFind = true;
                            bool = bool + data;
                        } else {
                            data2 = input.nextLine();
                            bool = bool + "'" + data2 + "'";
                        }
                    }
                }

                ResultSet rs = statement.executeQuery(bool);
                if (rs.next()) {
                    //enter column to update and enter updated data
                    System.out.print("Enter column to update data: ");
                    String columnUpdate = input.nextLine();
                    System.out.print("Enter updated data for " + columnUpdate + ": ");

                    //updateData for int, updateData2 for String
                    int updateData = 0;
                    String updateData2 = "";

                    //check is int or String
                    for (int i = 0; i < columnName.length; i++) {
                        if (columnName[i].equalsIgnoreCase(columnUpdate)) {
                            if (columnType[i].equalsIgnoreCase("int")) {
                                updateData = input.nextInt();
                                input.nextLine();
                                integerUpdate = true;
                            } else {
                                updateData2 = input.nextLine();
                            }
                        }
                    }

                    //loop to update sql string
                    String sql = "update " + tableName + " ";
                    if (integerUpdate) {
                        sql = sql + "set " + columnUpdate + " = " + updateData + " ";
                    } else {
                        sql = sql + "set " + columnUpdate + " = '" + updateData2 + "' ";
                    }

                    if (integerFind) {
                        sql = sql + "where " + columnFind + " = " + data;
                    } else {
                        sql = sql + "where " + columnFind + " = '" + data2 + "'";
                    }

                    statement.executeUpdate(sql);
                    System.out.println("Data updated!");
                    count++;
                }else{
                    System.out.println("Cannot find the record in the database");
                }
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //delete existing data from the table in the database
    public void deleteData(String tableName) throws Exception {
        try {
            Connection con = getConnection();

            //get the column name, type and count for both dataframe
            //initialize String bool to check whether record exists
            Statement statement = con.createStatement();
            String select = "SELECT * FROM " + tableName;
            String bool = "SELECT * FROM " + tableName;
            ResultSet results = statement.executeQuery(select);
            ResultSetMetaData metadata = results.getMetaData();
            int columnCount = metadata.getColumnCount();

            //initialize the column name and type for both dataframe
            columnName = new String[columnCount];
            columnType = new String[columnCount];

            //get the column name and type through metadata
            for (int i = 1; i <= columnCount; i++) {
                columnName[i - 1] = metadata.getColumnName(i);
                columnType[i - 1] = metadata.getColumnTypeName(i);
            }

            //Enter the column
            System.out.print("Enter column which act as condition: ");
            String columnDelete = input.nextLine();
            System.out.print("Enter data for that column: ");

            //find whether is delete integer or varchar
            //data for int //data2 for varchar
            boolean integerDelete = false;
            int data = 0;
            String data2 = "";

            for (int i = 0; i < columnName.length; i++) {
                if (columnName[i].equalsIgnoreCase(columnDelete)) {
                    if (columnType[i].equalsIgnoreCase("int")) {
                        data = input.nextInt();
                        input.nextLine();
                        integerDelete = true;
                    } else {
                        data2 = input.nextLine();
                    }
                }
            }

            //form sql statement and bool statement to check wheter record exist
            String sql = "delete from " + tableName + " ";
            if (integerDelete) {
                sql = sql + "where " + columnDelete + " = " + data;
                bool = bool + " where " + columnDelete + " = " + data;
            } else {
                sql = sql + "where " + columnDelete + " = '" + data2 + "'";
                bool = bool + " where " + columnDelete + " = '" + data2 + "'";
            }

            //check whether records exist or not
            ResultSet rs = statement.executeQuery(bool);
            if (rs.next()) {
                statement.executeUpdate(sql);
                System.out.println("Record deleted!");
            } else {
                System.out.println("The record cannot be found in the database");
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
