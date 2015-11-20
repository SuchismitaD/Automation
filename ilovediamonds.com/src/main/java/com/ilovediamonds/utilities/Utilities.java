package com.ilovediamonds.utilities;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;


import au.com.bytecode.opencsv.CSVReader;

/**
* This is an utility function used in our automation
* 
 * @author ILD LT 015
*
*/
public class Utilities {
      /**
      * This function is used to get Data from file name
      * 
       * @param fileName for CSV file
      * @return read all the data
      */
      public String[][] getDataFromCSV(String fileName, LinkedHashMap<String, Integer> map) {

            String dataSet[][] = null;// string type declaration is there so we created string type dataset.if its not null then automatically it will the memory size

            if (fileName.endsWith(".csv")) {

                  try {

                        java.io.Reader aa = new FileReader(fileName);//the object 'csvReader' store the parameter value of the csv filename

                        CSVReader cs = new CSVReader(aa);//data convert into csv format

                        String[] nextLine = cs.readNext();//reads the lines from csv i.e firstline
                        
                        // fill column name vs. index map
                        int numCols = nextLine.length;
                        for(int i=0;i<numCols;i++)
                        {
                                // get the column name and location and store in map
                                String colName = nextLine[i];
                                map.put(colName.toUpperCase(), i);
                        } 

                        String[] rowLine = null;

                        if (nextLine != null) {

                              List<String[]> content = cs.readAll();//content:string of lists present in one row

                              int row = content.size();//findout the row size from the list

                              int column = nextLine.length;//it reads the 1st row of the table

                              dataSet = new String[row][column];

                             int j = 0;

                              for (String[] object : content)//reads each string from the list
                              {

                                    rowLine = object;

                                    for (int i = 0; i < rowLine.length; i++)//loop continues the list of string data upto the total data
                                    {

                                          dataSet[j][i] = rowLine[i].trim();

                                    }

                                    j++;

                              }

                              content.clear();

                        }

                        cs.close();

                  } catch (Exception e) {

                  }

            }

            return dataSet;

      }
/**This function Get the random data without any repeatation and reads its corrossponding values from the csv
* 
 * @param String arrarylist value for the all rows & columns in csv
* @param count
* @return
*/
      public String[][] getRandomNoFromCSV(String[][] s, Integer count, LinkedHashMap<String, Integer> colNametoLocMap) {

            int rows = s.length;
            String[][] s2 = new String[count][21];
            if (count < rows) {
                  Random ran = new Random();
                  Set<Integer> generated = new LinkedHashSet<Integer>();
                  while (generated.size() < count) {
                        Integer next = ran.nextInt(rows) + 1;
                        generated.add(next);
                  }
                  
                  int i = 0;
                  for (Integer j : generated) {
                	  int colLoc = colNametoLocMap.get("SKU");
                      s2[i][0] = s[j][colLoc];
                      
                      int colLoc1 = colNametoLocMap.get("PRODUCT");
                      s2[i][1] = s[j][colLoc1]; 
                      
                      int colLoc2 = colNametoLocMap.get("DIA AMOUNT FOR SI GH");
                      s2[i][2] = s[j][colLoc2]; 
                      
                      int colLoc3 = colNametoLocMap.get("DIA AMOUNT FOR VVS EF");
                      s2[i][3] = s[j][colLoc3]; 
                      
                      int colLoc4 = colNametoLocMap.get("TOTAL GEMSTONE AMOUNT");
                      s2[i][4] = s[j][colLoc4];
                      
                      int colLoc5 = colNametoLocMap.get("METAL TYPE");
                      s2[i][5] = s[j][colLoc5]; 
                      
                      int colLoc6 = colNametoLocMap.get("HEIGHT");
                      s2[i][6] = s[j][colLoc6]; 
                      
                      int colLoc7 = colNametoLocMap.get("WIDTH");
                      s2[i][7] = s[j][colLoc7]; 
                      
                      int colLoc8 = colNametoLocMap.get("METAL WEIGHT");
                      s2[i][8] = s[j][colLoc8]; 
                      
                      int colLoc9 = colNametoLocMap.get("CATEGORY");
                      s2[i][9] = s[j][colLoc9]; 
                      
                      int colLoc10 = colNametoLocMap.get("TYPE OF PRODUCT");
                      s2[i][10] = s[j][colLoc10]; 
                      
                      int colLoc11 = colNametoLocMap.get("DIAMOND COLOR");
                      s2[i][11] = s[j][colLoc11]; 
                      
                      int colLoc12 = colNametoLocMap.get("DIAMOND CLARITY");
                      s2[i][12] = s[j][colLoc12];
                      
                      int colLoc13 = colNametoLocMap.get("DIAMOND SHAPE");
                      s2[i][13] = s[j][colLoc13];
                      
                      int colLoc14 = colNametoLocMap.get("NO. OF DIAMONDS");
                      s2[i][14] = s[j][colLoc14];
                      
                      int colLoc15 = colNametoLocMap.get("TOTAL DIAMOND WEIGHT");
                      s2[i][15] = s[j][colLoc15];
                      
                      int colLoc16 = colNametoLocMap.get("COLLECTION");
                      s2[i][16] = s[j][colLoc16];
                      
                      int colLoc17 = colNametoLocMap.get("GEM STONE TYPE");
                      s2[i][17] = s[j][colLoc17];
                      
                      int colLoc18 = colNametoLocMap.get("GEMSTONE SHAPE");
                      s2[i][18] = s[j][colLoc18];
                      
                      int colLoc19 = colNametoLocMap.get("NO. OF GEMSTONES");
                      s2[i][19] = s[j][colLoc19];
                      
                      int colLoc20 = colNametoLocMap.get("TOTAL GEMSTONE WEIGHT");
                      s2[i][20] = s[j][colLoc20];
                        
                        

                                               
                        i++;
                  }
            }
            else if (count == rows) {
                  int i = 0;
                  for (int j = 0; j < s.length; j++) {
                	  int colLoc = colNametoLocMap.get("SKU");
                      s2[i][0] = s[j][colLoc];
                      
                      int colLoc1 = colNametoLocMap.get("PRODUCT");
                      s2[i][1] = s[j][colLoc1]; 
                      
                      int colLoc2 = colNametoLocMap.get("DIA AMOUNT FOR SI GH");
                      s2[i][2] = s[j][colLoc2]; 
                      
                      int colLoc3 = colNametoLocMap.get("DIA AMOUNT FOR VVS EF");
                      s2[i][3] = s[j][colLoc3]; 
                      
                      int colLoc4 = colNametoLocMap.get("TOTAL GEMSTONE AMOUNT");
                      s2[i][4] = s[j][colLoc4];
                      
                      int colLoc5 = colNametoLocMap.get("METAL TYPE");
                      s2[i][5] = s[j][colLoc5]; 
                      
                      int colLoc6 = colNametoLocMap.get("HEIGHT");
                      s2[i][6] = s[j][colLoc6]; 
                      
                      int colLoc7 = colNametoLocMap.get("WIDTH");
                      s2[i][7] = s[j][colLoc7]; 
                      
                      int colLoc8 = colNametoLocMap.get("METAL WEIGHT");
                      s2[i][8] = s[j][colLoc8]; 
                      
                      int colLoc9 = colNametoLocMap.get("CATEGORY");
                      s2[i][9] = s[j][colLoc9]; 
                      
                      int colLoc10 = colNametoLocMap.get("TYPE OF PRODUCT");
                      s2[i][10] = s[j][colLoc10]; 
                      
                      int colLoc11 = colNametoLocMap.get("DIAMOND COLOR");
                      s2[i][11] = s[j][colLoc11]; 
                      
                      int colLoc12 = colNametoLocMap.get("DIAMOND CLARITY");
                      s2[i][12] = s[j][colLoc12];
                      
                      int colLoc13 = colNametoLocMap.get("DIAMOND SHAPE");
                      s2[i][13] = s[j][colLoc13];
                      
                      int colLoc14 = colNametoLocMap.get("NO. OF DIAMONDS");
                      s2[i][14] = s[j][colLoc14];
                      
                      int colLoc15 = colNametoLocMap.get("TOTAL DIAMOND WEIGHT");
                      s2[i][15] = s[j][colLoc15];
                      
                      int colLoc16 = colNametoLocMap.get("COLLECTION");
                      s2[i][16] = s[j][colLoc16];
                      
                      int colLoc17 = colNametoLocMap.get("GEM STONE TYPE");
                      s2[i][17] = s[j][colLoc17];
                      
                      int colLoc18 = colNametoLocMap.get("GEMSTONE SHAPE");
                      s2[i][18] = s[j][colLoc18];
                      
                      int colLoc19 = colNametoLocMap.get("NO. OF GEMSTONES");
                      s2[i][19] = s[j][colLoc19];
                      
                      int colLoc20 = colNametoLocMap.get("TOTAL GEMSTONE WEIGHT");
                      s2[i][20] = s[j][colLoc20];
                      
                      i++;
                  } 
            }

             else {
                  System.out.println("Please enter less number");
            }
            return s2;// it return the 17 coloum datas for the random searchable
            // products and print it
      }

      public String[][] getRandomSku(String[][] s, Integer count, LinkedHashMap<String, Integer> colNametoLocMap) {

          int rows = s.length;
          String[][] s2 = new String[count][2];
          if (count < rows) {
                Random ran = new Random();
                Set<Integer> generated = new LinkedHashSet<Integer>();
                while (generated.size() < count) {
                      Integer next = ran.nextInt(rows) + 1;
                      generated.add(next);
                }
                
                int i = 0;
                for (Integer j : generated) {
                                int colLoc = colNametoLocMap.get("SKU");
                      s2[i][0] = s[j][colLoc];
                      
                      int colLoc1 = colNametoLocMap.get("NAME");
                      s2[i][1] = s[j][colLoc1]; 
                      
                     
                      i++;
                }
          }
          else if (count == rows) {
                int i = 0;
                for (int j = 0; j < s.length; j++) {
                                int colLoc = colNametoLocMap.get("SKU");
                      s2[i][0] = s[j][colLoc];
                      
                      int colLoc1 = colNametoLocMap.get("NAME");
                      s2[i][1] = s[j][colLoc1]; 
                      
                      
                } 
          }

           else {
                System.out.println("Please enter less number");
          }
          return s2;// it return the 17 coloum datas for the random searchable
          // products and print it
    }
      public String convertDecimal(String s) {
            float f = Float.parseFloat(s);
            String k = String.format("%.9f", f);
            return k;
      }

      public String converPrice1(String s) {
            String s1[] = s.substring(3).split(",");
            String s2 = "";
            for (int i = 0; i < s1.length; i++) {
                  s2 = s2 + s1[i].toString();
            }
            return s2;
      }
      
      public void creteHTMLFile(String htmlPath, String content){
                  String filepath = System.getProperty("user.dir");
                  File f = new File(filepath + "/TestResult/"+ htmlPath+".html");
                  try {
                   f.createNewFile();
                   if(f.exists())
                    f.delete();
                   f.createNewFile();
                   BufferedWriter bw = new BufferedWriter(new FileWriter(f));
                   bw.write(content);
                   bw.close();
                  } catch (IOException e) {
                   // TODO Auto-generated catch block
                   e.printStackTrace();
                  }
                  
                 }
                public String[][] getRandomLink(String[][] s, int count, LinkedHashMap<String, Integer> colNameToLocation) {
                                // TODO Auto-generated method stub
                    int rows = s.length;
        String[][] s2 = new String[count][2];
        if (count < rows) {
              Random ran = new Random();
              Set<Integer> generated = new LinkedHashSet<Integer>();
              while (generated.size() < count) {
                    Integer next = ran.nextInt(rows) + 1;
                    generated.add(next);
              }
              
              int i = 0;
              for (Integer j : generated) {
                                int colLoc = colNameToLocation.get("LINK");
                    s2[i][0] = s[j][colLoc];
                    
                    int colLoc1 = colNameToLocation.get("EXPECTEDTIME");
                    s2[i][1] = s[j][colLoc1]; 
                    
                   
                    i++;
              }
        }
        else if (count == rows) {
              int i = 0;
              for (int j = 0; j < s.length; j++) {
                                int colLoc = colNameToLocation.get("Link");
                    s2[i][0] = s[j][colLoc];
                    
                    int colLoc1 = colNameToLocation.get("ExpectedTime");
                    s2[i][1] = s[j][colLoc1]; 
                    
                    
              } 
        }

         else {
              System.out.println("Please enter less number");
        }
        return s2;
                                
                }
                public String converPrice(String s) {
        String s1[] = s.substring(3).split(",");
        String s2 = "";
        for (int i = 0; i < s1.length; i++) {
              s2 = s2 + s1[i].toString();
        }
        return s2;
  }
/**
 * Function to match the two string values and store each string in string of arraylist and split the strings
 * using , : etc.calculate the no of strings present in list if it matches then return true
 * @param s is product detail page value
 * @param s1 is the csv page value 
 * @return boolean value of string matching result
 */
  public boolean matchTwoString(String s, String s1) {
        boolean isMatch = true;
        

        if (s.trim().equalsIgnoreCase(s1.trim())) {
              return isMatch;
        } 
        else {
              String[] actualString = null;
              String[] expetedString = null;
              
                    actualString = s.split(",");
                                      
             
              if(s1.contains(":"))
                    expetedString = s1.split(":");
              if(s1.contains(","))
            	  expetedString = s1.split(",");
              
                    
             
              if (actualString.length != expetedString.length) {
                    return false;
              } else {
                    for (int i = 0; i < actualString.length; i++) {
                          boolean temp = false;
                          for (int j = 0; j < expetedString.length; j++) {
                                if (actualString[i].trim().equalsIgnoreCase(expetedString[j].trim())) {
                                      temp = true;
                                      break;
                                }
                          }
                          isMatch = isMatch & temp;
                    }
                    
              }
              return isMatch;
        }
  }
}
