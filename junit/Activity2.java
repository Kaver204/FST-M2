package activities;

import activities_pckg.BankAccount;
import activities_pckg.NotEnoughFundsException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Activity2 {

    @Test
    @Order(1)
    public void notEnoughFunds(){
        BankAccount account= new BankAccount(10);
        //to assert the exception thrown
        assertThrows(NotEnoughFundsException.class,() ->account.withdraw(11));


    }
    @Test
    @Order(2)
    public void enoughFunds(){
        BankAccount account=new BankAccount(100);
        //Assertions for no exceptions
        assertDoesNotThrow(()->account.withdraw(10));

    }
}
