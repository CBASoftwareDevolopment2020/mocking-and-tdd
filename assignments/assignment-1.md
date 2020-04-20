# Assignment 1 - Mocking and TDD

## Content

-   [Utilities](#Utilities)
-   [Account Test](#Account-Test)
-   [Bank Test](#Bank-Test)
-   [Customer Test](#Customer-Test)
-   [Movement Test](#Movement-Test)

## Utilities

### JMock

In the test cases we are using JMock to mock the classes needed to the given test. Theese mockings helps the dataflow to correspond with the testing scenarios.

`JUnitRuleMockery` is a `JUnit` rule, that manages JMock expectations, allowances and asserts that expectations have been met after each test has finished.

```java
@Rule
public JUnitRuleMockery context = new JUnitRuleMockery();
```

By using the `context.checking` method from `JUnitRuleMockery`, we can create a expectation the test should fulfill to complete. We are using it to determine if the method is called, what it returns and how many times its called.

#### JMock Example

The arrangement for most of our test cases are following this structure. In this we are creating three sections in the test **_Arrange_**, **_Act_** and **_Assert_**.

**_Arrange_** Is to create the objects and mocks og the needed objects, setup `Expection`'s and defining the invocations that tells the system how many time the given method must be called and return values.

**_Act_** Is to called the methods needed to fulfill the test and `Expectation`'s.

**_Assert_** Is to evaluate the final results and see if they fulfill the assertions.

```java
@Test
public void testMocking() {
    // Arange
    Object mockObject = context.mock(Object.class, "instance name");

    context.checking(new Expectations() {{
        oneOf(mockObject).myMethod();
        will(returnValue("Hello Mock!"));
    }});

    // Act
    String expected = "Hello Mock!";
    String actual = mockObject.myMethod();

    // Assert
    assertEquals(expected, actual);
}
```

#### Expectation Invocations

| Invocation           | Description                                                                                                  |
| -------------------- | ------------------------------------------------------------------------------------------------------------ |
| oneOf                | The invocation is expected once and once only.                                                               |
| exactly(n).of        | The invocation is expected exactly n times. Note: one is a convenient shorthand for exactly(1).              |
| atLeast(n).of        | The invocation is expected at least n.                                                                       |
| atMost(n).of         | The invocation is expected at most n times.                                                                  |
| between(min, max).of | The invocation is expected at least min times and at most max times.                                         |
| allowing             | The invocation is allowed any number of times but does not have to happen.                                   |
| ignoring             | The same as allowing. Allowing or ignoring should be chosen to make the test code clearly express intent.    |
| never                | The invocation is not expected at all. This is used to make tests more explicit and so easier to understand. |

### Code Coverage

We used Intellijs code coverage to see how comprehensive the test cases is.

| Element      | Class, %   | Method, %    | Line, %      |
| ------------ | ---------- | ------------ | ------------ |
| RealAccount  | 100% (1/1) | 100% (11/11) | 100% (34/34) |
| RealBank     | 100% (1/1) | 100% (10/10) | 100% (23/23) |
| RealCustomer | 100% (1/1) | 100% (8/8)   | 100% (16/16) |
| RealMovement | 100% (1/1) | 100% (5/5)   | 100% (10/10) |

## Account Test

_[AccountTest.java, reference](https://github.com/SoftwareDevolopmentSem1/mocking-and-tdd/blob/master/Banking/src/test/java/banking/AccountTest.java)_

### Selected test case

To show how the `Expectaion` works, we selected this test case. In this test, we are creating two `Expectation`'s, one for each `Movement` object. Because the two objects are the same type the `context.mock` method needs an identifier to differ from them.

The first `Expectation` contains a invocation of the `deposit.getAmount()` method, the `oneOf` notation is a contraint for the amount of calls for this method, in this case meaning **_only one_**. After defining the invocation, the methods return value is set to `100_00L`. This helps us controlling the data flow in the test without creating any complexed objects.

The second `Expectation` is almost the same as the first, but instead of calling `deposit.getAmount()` it calls `withdrawal.getAmount()` with a return value of `50_00L`.

```java
@Test
public void testGetBalancePositive() {
    final Bank bank = context.mock(Bank.class);
    final Customer customer = context.mock(Customer.class);
    final Movement deposit = context.mock(Movement.class, "deposit");
    final Movement withdrawal = context.mock(Movement.class, "withdrawal");

    Account account = new RealAccount(bank, customer, "1234");

    context.checking(new Expectations() {{
        oneOf(deposit).getAmount();
        will(returnValue(100_00L));
    }});

    context.checking(new Expectations() {{
        oneOf(withdrawal).getAmount();
        will(returnValue(50_00L));
    }});

    account.addDeposit(deposit);
    account.addWithdrawal(withdrawal);
    long res = account.getBalance();

    assertEquals(50_00, res);
}
```

### Test cases

#### Method `Account.getBalance`

| Method                   | Preconditions                     | Expectation         |
| ------------------------ | --------------------------------- | ------------------- |
| `testGetBalancePositive` | Deposit `100L` <br>Withdraw `50L` | A balance of `50L`  |
| `testGetBalanceZero`     | Deposit `50L` <br>Withdraw `50L`  | A balance of `0L`   |
| `testGetBalanceNegative` | Deposit `50L` <br>Withdraw `100L` | A balance of `-50L` |

#### Method `Account.transfer`

| Method                               | Preconditions                                        | Expectation                                                              |
| ------------------------------------ | ---------------------------------------------------- | ------------------------------------------------------------------------ |
| `testTransferAccount`                | Transfer `100L` <br>from `A` to `B`                  | A balance of `-100L` at `A` <br> A balance of `100L` at `B`              |
| `testTransferAccountNegativeAmount`  | Transfer `-100L` <br>from `A` to `B`                 | `IllegalArgumentException`                                               |
| `testTransferAccountDoesNotExists`   | Tranfer to unknown account                           | `IllegalArgumentException`                                               |
| `testTransferCustomer`               | Transfer `100L` <br>from `A` to `B`'s main account   | A balance of `-100L` at `A` <br> A balance of `100L` at `B` main account |
| `testTransferCustomerNegativeAmount` | Transfer `-100L` <br>from `A` to `B`'s main account. | `IllegalArgumentException`                                               |
| `testTransferCustomerDoesNotExists`  | Tranfer to unknown customer                          | `NullPointerException`                                                    |

#### Methods `Account.getters`

| Method        | Preconditions                 | Expectation                                  |
| ------------- | ----------------------------- | -------------------------------------------- |
| `testGetters` | Fields are defined with value | Getters return value equals the class fields |

## Bank Test

_[BankTest.java, reference](https://github.com/SoftwareDevolopmentSem1/mocking-and-tdd/blob/master/Banking/src/test/java/banking/BankTest.java)_

### Selected test case

In this case we are mocking a class like the previous example. This example contains a different feature, the invocation is `allowing` instaed of `oneOf`. The differents between theese two are the amount is a fixed value in `oneOf` where `allowing` can be any amount from 0 to whatever you want, meaning the test does'nt have to call it, or the test can call it multiple times and still reach the fulfillment.

```java
@Test
public void testGetAccounts() {
    Bank bank = new RealBank("a","BoB");
    Customer customer = context.mock(Customer.class);
    List<Account> accounts = new ArrayList<>();
    Account actualAccount = new RealAccount(bank, customer, "1234");
    accounts.add(actualAccount);

    context.checking(new Expectations() {{
        allowing(customer).getAccounts();
        will(returnValue(accounts));
    }});

    List<Account> actualAccounts = bank.getAccounts(customer);

    assertEquals(accounts, actualAccounts);
}
```

### Test cases

#### Methods `Bank.getAccount`

| Method                        | Preconditions                  | Expectation                               |
| ----------------------------- | ------------------------------ | ----------------------------------------- |
| `testGetAccount`              | Account `A` is registret       | The returned `Account`<br>is equal to `A` |
| `testGetAccountDoesNotExists` | No account is added<br>to bank | Account is `NULL`                         |

#### Methods `Bank.getCustomer`

| Method                         | Preconditions                   | Expectation                                |
| ------------------------------ | ------------------------------- | ------------------------------------------ |
| `testGetCustomer`              | Customer `C` is registret       | The returned `Customer`<br>is equal to `C` |
| `testGetCustomerDoesNotExists` | No customer is added<br>to bank | Customer is `NULL`                         |

#### Methods `Bank.getAccounts`

| Method                                 | Preconditions                                                    | Expectation                             |
| -------------------------------------- | ---------------------------------------------------------------- | --------------------------------------- |
| `testGetAccounts`                      | A customer is registret<br>and a list `L` of Accounts is created | The returned `Account`s is equal to `L` |
| `testGetAccountsCustomerDoesNotExists` | A cutomer object as `NULL`<br>with accounts                      | `NullPointerException`                  |

#### Methods `Bank.getters`

| Method        | Preconditions                 | Expectation                                  |
| ------------- | ----------------------------- | -------------------------------------------- |
| `testGetters` | Fields are defined with value | Getters return value equals the class fields |

## Customer Test

_[CustomerTest.java, reference](https://github.com/SoftwareDevolopmentSem1/mocking-and-tdd/blob/master/Banking/src/test/java/banking/CustomerTest.java)_

### Selected test case

In this example we will show how we test an `Exception` when we throw one. The test below will try to transfer a negative amount to another account. This would be an easy way to stell money from others accounts, so an `IllegalArgumentException` is thrown to avoid it from happening. The example does'nt contain any `asserts` but has an extended `anotation` where the `IllegalArgumentException` is expected.

```java
@Test(expected = IllegalArgumentException.class)
public void testTransferNegativeAmount() {
    final Bank bank = context.mock(Bank.class);
    final Customer targetCustomer = context.mock(Customer.class);

    Customer sourceCustomer = new RealCustomer(bank, "abcd", "stephan");
    Account sourceAccount = new RealAccount(bank, sourceCustomer, "1234");
    Account targetAccount = new RealAccount(bank, targetCustomer, "5678");

    context.checking(new Expectations() {{
        oneOf(targetCustomer).getFirstAccount();
        will(returnValue(targetAccount));
    }});

    sourceCustomer.transfer(-100_00L, sourceAccount, targetCustomer);
}
```

### Test cases

#### Methods `Customer.transfer`

| Method                              | Preconditions                                          | Expectation                                                 |
| ----------------------------------- | ------------------------------------------------------ | ----------------------------------------------------------- |
| `testTransfer`                      | Two Accounts are created<br>`A` tranfers `100L` to `B` | A balance of `-100L` at `A` <br> A balance of `100L` at `B` |
| `testTransferNegativeAmount`        | Transfer `-100L` <br>from `A` to `B`                   | `IllegalArgumentException`                                  |
| `testTransferCustomerDoesNotExists` | Tranfer to unknown customer                            | `NullPointerException`                                      |
| `testTransferAccountDoesNotExists`  | Tranfer to unknown account                             | `NullPointerException`                                      |

#### Methods `Customer.getters`

| Method        | Preconditions                 | Expectation                                  |
| ------------- | ----------------------------- | -------------------------------------------- |
| `testGetters` | Fields are defined with value | Getters return value equals the class fields |

## Movement Test

_[MovementTest.java, reference](https://github.com/SoftwareDevolopmentSem1/mocking-and-tdd/blob/master/Banking/src/test/java/banking/MovementTest.java)_

### Selected test case

This last test, is a test for the getters. Its a simple test, that `asserts` the values given to the `RealMovement` contructor and the values from the `get` methods of that object. This is made by an `assertEquals`, and passes like the rest of the tests.

```java
@Test
public void testGetters() {
    Account source = context.mock(Account.class, "source");
    Account target = context.mock(Account.class, "target");
    long amount = 100_00;
    long time = new Date().getTime();
    Movement movement = new RealMovement(source, target, amount, time);

    assertEquals(source, movement.getSource());
    assertEquals(target, movement.getTarget());
    assertEquals(amount, movement.getAmount());
    assertEquals(time, movement.getTime());
}
```

### Test cases

#### Methods `Customer.getters`

| Method        | Preconditions                 | Expectation                                  |
| ------------- | ----------------------------- | -------------------------------------------- |
| `testGetters` | Fields are defined with value | Getters return value equals the class fields |
