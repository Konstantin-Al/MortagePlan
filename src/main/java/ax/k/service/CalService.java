package ax.k.service;

import ax.k.controller.MainController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class CalService {


    static Logger logger = LoggerFactory.getLogger(MainController.class);


    public ArrayList<String[]> calculateArray(ArrayList<String[]> listOfLines) {
        logger.info("calculateArray");

        // column definition
        List<String> columns = Arrays.asList(listOfLines.get(0));

        //
        int listLength = listOfLines.size();
        for (int i = 1; i < listLength; i++) {
            String [] result = calculateLine(columns, listOfLines.get(i));
            logger.info(Arrays.toString(result));

            listOfLines.set(i, result);
        }
        return listOfLines;
    }

    public String [] calculateLine(List<String> columns, String [] values) {
        logger.info("calculateLine");

        // create a new array of bigger capacity
        String [] resultArray = resizeArray(values, 1);

        BigDecimal total = new BigDecimal(resultArray[columns.indexOf("Total loan")]);
        BigDecimal interest = new BigDecimal(resultArray[columns.indexOf("Interest")]);
        int years = Integer.parseInt(resultArray[columns.indexOf("Years")]);

        BigDecimal Result = calculMortgage(total, interest, years);

        resultArray[values.length] = String.valueOf(Result);

        return resultArray;
    }

    public String [] resizeArray( String [] oldArray, int sizeDif) {
        // create a new array
        String [] newArray = new String[oldArray.length + sizeDif];
        System.arraycopy(oldArray, 0, newArray, 0, oldArray.length);

        return newArray;
    }

    public static BigDecimal calculMortgage(BigDecimal total, BigDecimal interest, int years) {
        logger.info("calculMortgage");

        int numberOfPeriods = years * 12; // Months in a year
        BigDecimal numOne = new BigDecimal(1);
        BigDecimal interestPerc = interest.movePointLeft(2);

        /*
        Mortgage Formula
            E = U * ( b*(1 + b)^p) / ((1 + b)^p - 1)
        Where:
            E = Fixed monthly payment
            b = Interest on a monthly basis
            U = Total loan
            p = Number of payments
        */
        //  ( b*(1 + b)^p)
        BigDecimal result1 = interestPerc.multiply(myPow((interestPerc.add(numOne)), numberOfPeriods));
        //  ((1 + b)^p - 1)
        BigDecimal result2 = myPow((interestPerc.add(numOne)), numberOfPeriods).subtract(numOne);
        // U * result1 / result2
        BigDecimal result = total.multiply(result1.divide(result2, RoundingMode.HALF_UP )).setScale(2, RoundingMode.HALF_UP);

        return result;
    }


    // calculation power using recursion
    public static BigDecimal myPow(BigDecimal value, int powValue) {
        if (powValue == 1) {
            return value;
        } else {
            return value.multiply(myPow(value, powValue - 1));
        }
    }
}
