package ax.k.service;

import ax.k.controller.MainController;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ReadService {
    static Logger logger = LoggerFactory.getLogger(MainController.class);

    boolean isFirstLine = true;
    String [] columns = null;


   public ArrayList<String[]> readFile(MultipartFile importedFile) throws IOException {
       logger.info("readFile");

       // To read char data
       InputStreamReader inputStreamReader = new InputStreamReader(importedFile.getInputStream());
       // To read line data
       BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

       // create an ArrayList of String Array type
       ArrayList<String[]> listOfLines = new ArrayList<String[]>();


       String line = bufferedReader.readLine();
       while (line != null) {
           readLineStr(line, listOfLines);
           line = bufferedReader.readLine();
       }
       bufferedReader.close();

       return listOfLines;
   }

   public void readLineStr (String line, ArrayList<String[]> listOfLines) {
       logger.info("readLineStr");

       // Clean up line
       String [] cleanedArr = cleanLine(line);
       logger.debug(String.valueOf(cleanedArr.length));

       // Collect columns name
       if (isFirstLine) {
           columns = cleanedArr;
           //logger.info(Arrays.toString(columns));
           isFirstLine = false;
           listOfLines.add(cleanedArr);
       } else {
           logger.info(String.valueOf(cleanedArr.length));

           // Add to listOfLines If length is the same
           if (isValidArr( columns, cleanedArr)){            // DRY TODO? check Data types
               //logger.info(cleanedArr.toString());
               listOfLines.add(cleanedArr);
           }
       }

   }

    public static String[] cleanLine(String line) {
        logger.info("cleanLine");
        logger.info(line);

        // remove ',' between quotes
        Matcher matcher = Pattern.compile("\"[^\"]*\"").matcher(line);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group().replaceAll(",", " "));
        }
        matcher.appendTail(sb);

        // remove extra white spaces between words
        String strWithProperSpacing = StringUtils.normalizeSpace(sb.toString());

        // remove whitespace ' ,' before a quote, remove ' " '  and split
        String arr [] = strWithProperSpacing
                .replaceAll(" ,", ",")
                .replaceAll("\"", "")
                .split(",");
        logger.info(Arrays.toString(arr));
        return arr;
    }


    // TODO check?
    public static Boolean isValidArr(String [] columns, String [] arr ) {
        logger.info("isValidArr");

        if (arr.length == columns.length) {
            logger.info("arr and columns have the same length");
            return true;
        }
        logger.info("unequal length");
       return false;
    }
}