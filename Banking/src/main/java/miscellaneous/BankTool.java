package miscellaneous;

import banking.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BankTool {
    public void readFromFile(Bank bank, String filename) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(filename));
        for (String line : lines) {
            if (line.startsWith("A:")) {
                String[] parts = line.split(":");
                if (parts.length != 3) throw new RuntimeException();
                Account account = new RealAccount(bank, bank.getCustomer(parts[2]), parts[1]);
                bank.registerAccount(account);
            }
            else if (line.startsWith("C:")){
                String[] customerParts = line.split(":");
                if (customerParts.length != 3) throw new RuntimeException();
                Customer customer = new BaseCustomer(customerParts[1], customerParts[2]);
                bank.registerCustomer(customer);
            }
            else if (line.startsWith("M:")) {
                String[] movementParts = line.split(":");
                if (movementParts.length != 5) throw new RuntimeException();
                String sourceNumber = movementParts[1];
                String targetNumber = movementParts[2];
                long amount = Integer.parseInt(movementParts[3]);
                long timestamp = Integer.parseInt(movementParts[4]);
                Movement movement = new RealMovement(bank.getAccount(sourceNumber), bank.getAccount(targetNumber), amount, timestamp);
            }
            else throw new RuntimeException();
        }


    }

}