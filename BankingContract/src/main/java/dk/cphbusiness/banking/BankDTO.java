package dk.cphbusiness.banking;

import java.io.Serializable;

public class BankDTO implements Serializable {
    private String cvr;
    private String name;

    public BankDTO(String cvr, String name) {
        this.cvr = cvr;
        this.name = name;
    }

    public String getCvr() {
        return cvr;
    }

    public String getName() {
        return name;
    }
}
