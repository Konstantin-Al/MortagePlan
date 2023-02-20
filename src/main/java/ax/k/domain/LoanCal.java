package ax.k.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity                         // This tells Hibernate to make a table out of this class
public class LoanCal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    private LocalDateTime CD;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;
    private String totalLoan;
    private String interest;
    private int years;

// ---------------------------------------------  constructors
    public LoanCal() {                                        // needs for entity creation
    }

    public LoanCal(LocalDateTime CD, Customer customer, String totalLoan, String interest, int years) {
        this.CD = CD;
        this.customer = customer;
        this.totalLoan = totalLoan;
        this.interest = interest;
        this.years = years;
    }

// --------------------------                   Getters and Setters

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    // my getter
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public String getCustomer() {
        return customer.getUsername();
    }

    public void setCD(LocalDateTime CD) {
        this.CD = CD;
    }
    public String getCD() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return CD.format(format);
    }

    public void setTotalLoan(String totalLoan) {
        this.totalLoan = totalLoan;
    }
    public String getTotalLoan() {
        return totalLoan;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }
    public String getInterest() {
        return interest;
    }

    public void setYears(int years) {
        this.years = years;
    }
    public int getYears() {
        return years;
    }



}
