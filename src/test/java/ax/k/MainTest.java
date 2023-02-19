package ax.k;
import ax.k.service.CalService;
import ax.k.service.ReadService;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Array;

public class MainTest {


    // ReadService
    @Test
    public void CleanLineTest (){

        String testStr = "\"Clarencé,Andersson\",2000,6,4";
        String [] resultArray = new String[]{"Clarencé Andersson", "2000", "6", "4"};

        Assert.assertArrayEquals(resultArray, ReadService.cleanLine(testStr));
    }

    @Test
    public void isValidTest (){

        String [] columnArray  = new String[]{"Customer", "Total loan", "Interest", "Years"};

        String [] valueArray = new String[]{"Clarencé Andersson", "2000", "6", "4"};
        Assert.assertTrue(ReadService.isValidArr(columnArray, valueArray));

        String [] newValueArray = new String[]{"Clarencé Andersson", "2000", "6"};
        Assert.assertFalse(ReadService.isValidArr(columnArray, newValueArray));
    }


    // CalService
    @Test
    public void calculMortgageTest (){

        BigDecimal total = BigDecimal.valueOf(1000);
        BigDecimal interest = BigDecimal.valueOf(5);
        int years = 2;

        BigDecimal resultFunc = CalService.calculMortgage(total, interest,years);

        BigDecimal answer = new BigDecimal("72.47");
        Assert.assertEquals(resultFunc, answer);
    }

    @Test
    public void myPowTest (){
        // 2^2 = 4
        Assert.assertEquals(BigDecimal.valueOf(4), CalService.myPow(BigDecimal.valueOf(2), 2));
        // 3^2 = 9
        Assert.assertEquals(BigDecimal.valueOf(9), CalService.myPow(BigDecimal.valueOf(3), 2));
        // 3^3 = 27
        Assert.assertEquals(BigDecimal.valueOf(27), CalService.myPow(BigDecimal.valueOf(3), 3));
    }

}
