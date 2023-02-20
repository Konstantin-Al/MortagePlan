package ax.k.service;


import ax.k.controller.MainController;
import ax.k.domain.Customer;
import ax.k.domain.LoanCal;
import ax.k.domain.Role;
import ax.k.repos.CustomerRepo;
import ax.k.repos.LoanCalRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Service
public class MainService  {


    Logger logger = LoggerFactory.getLogger(MainController.class);
    @Autowired
    private ReadService readService;

    @Autowired
    private CalService calService;


    @Autowired
    private LoanCalRepo loanCalRepo;

    @Autowired
    private CustomerRepo customerRepo;


    public void importBtnClicked(MultipartFile importedFile)  {
        logger.info("importBtnClicked");

        readImportedFile(importedFile);
    }

    private void readImportedFile(MultipartFile importedFile)  {
        logger.info("readImportedFile");

        // read and clean
        ArrayList<String[]> listOfLines = null;
        try {
            listOfLines = readService.readFile(importedFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Send ArrayList to a method for calculation
        ArrayList<String[]> calListOfLines = calService.calculateArray(listOfLines);

        logger.info(Arrays.toString(calListOfLines.get(1)));
        logger.info(String.valueOf(calListOfLines.size()));

        List<String> columns = Arrays.asList(calListOfLines.get(0));


        for (int i = 1; i < calListOfLines.size(); i++) {

            String customerName = calListOfLines.get(i)[0];
            String total_loan= calListOfLines.get(i)[1];
            String interest = calListOfLines.get(i)[2];
            String years = calListOfLines.get(i)[3];

            // Check if customer exists in DB
            Customer  customer= customerRepo.findByName(customerName);

            // Create new customer if not exist

            if (customer == null) {

                customer = new Customer();

                customer.setUsername(customerName);
                customer.setActive(true);
                customer.setRoles(Collections.singleton(Role.USER));

                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                customer.setPassword(encoder.encode(customerName));
                customerRepo.save(customer);
            }
            saveOneCalToDB(columns, customer, total_loan, interest, years);
        }
    }

    private String prospect (String customerName, String total_loan, String years, String resultMortgage){
        return ("Prospect: " + customerName + " wants to borrow " + total_loan + " € for a period of " + years + "years and pay " + resultMortgage + " € each month");
    }


    public String calBtnClicked(Customer customer, String total_loan, String interest, String years){
        logger.info("addBtnClicked");

        List<String> columns = Arrays.asList("Customer", "Total loan", "Interest", "Years");

        String resultMortgage = saveOneCalToDB(columns, customer, total_loan, interest, years);

        return prospect(customer.getUsername(), total_loan, years, resultMortgage );
    }

    private String saveOneCalToDB(List<String> columns, Customer customer, String total_loan, String interest, String years){
        logger.info("saveOneCalToDB");


        String [] values = new String[]{"customer", total_loan, interest, years};

        String [] resultArray = calService.calculateLine(columns, values);

        LocalDateTime CD = LocalDateTime.now();
        int yearsInt  = Integer.parseInt(resultArray[columns.indexOf("Years")]);
        String resultMortgage = resultArray[values.length];                         // mortgageCal is the last added

        LoanCal loanCal = new LoanCal(CD, customer, total_loan, interest, yearsInt, resultMortgage);
        loanCalRepo.save(loanCal);

        return resultMortgage;
    }

    public List<LoanCal> mainLoadAllLoanCal(){
        return loanCalRepo
                .findAll(Sort.by(Sort.Direction.DESC, "CD")
                        .and(Sort.by(Sort.Direction.ASC, "customer")));
    }
    public List<LoanCal> mainLoadLoanCalByCustomer(Customer customer){
        return loanCalRepo.findLoanCalByCustomerOrderByCDDesc(customer);
    }

}