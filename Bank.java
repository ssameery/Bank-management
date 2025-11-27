import java.util.*;

class Bank {

    static class Customer {
        String name, email;
        Customer(String n, String e) { name = n; email = e; }
    }

    static class Transaction {
        String type;
        double amount;
        Date time = new Date();
        Transaction(String t, double a) { type = t; amount = a; }
        public String toString() { return time + " | " + type + " | " + amount; }
    }

    static class Account {
        String accNo;
        Customer customer;
        double balance;
        List<Transaction> history = new ArrayList<>();

        Account(String accNo, Customer c, double initial) {
            this.accNo = accNo; this.customer = c;
            this.balance = initial;
            history.add(new Transaction("INITIAL DEPOSIT", initial));
        }

        void deposit(double amt) {
            balance += amt;
            history.add(new Transaction("DEPOSIT", amt));
        }

        void withdraw(double amt) {
            balance -= amt;
            history.add(new Transaction("WITHDRAW", amt));
        }
    }

    static Map<String, Account> accounts = new HashMap<>();

    static String generateAccNo() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    static Account getAcc(String accNo) {
        if (!accounts.containsKey(accNo)) throw new RuntimeException("Account Not Found!");
        return accounts.get(accNo);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== BANKING APP ===");
            System.out.println("1. Open Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. View Statement");
            System.out.println("6. List Accounts");
            System.out.println("0. Exit");
            System.out.print("Choose: ");

            int ch = sc.nextInt(); sc.nextLine();

            try {
                switch (ch) {

                    case 1:  // OPEN ACCOUNT
                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter Email: ");
                        String email = sc.nextLine();
                        System.out.print("Initial Deposit: ");
                        double ini = sc.nextDouble();

                        String accNo = generateAccNo();
                        accounts.put(accNo, new Account(accNo, new Customer(name, email), ini));
                        System.out.println("Account Created! Acc No: " + accNo);
                        break;

                    case 2: // DEPOSIT
                        System.out.print("Acc No: ");
                        Account a1 = getAcc(sc.next());
                        System.out.print("Amount: ");
                        double d = sc.nextDouble();
                        a1.deposit(d);
                        System.out.println("Deposited Successfully.");
                        break;

                    case 3: // WITHDRAW
                        System.out.print("Acc No: ");
                        Account a2 = getAcc(sc.next());
                        System.out.print("Amount: ");
                        double w = sc.nextDouble();
                        if (a2.balance < w) throw new RuntimeException("Insufficient Balance!");
                        a2.withdraw(w);
                        System.out.println("Withdraw Successful.");
                        break;

                    case 4: // TRANSFER
                        System.out.print("From Acc: ");
                        Account f = getAcc(sc.next());
                        System.out.print("To Acc: ");
                        Account t = getAcc(sc.next());
                        System.out.print("Amount: ");
                        double amt = sc.nextDouble();
                        if (f.balance < amt) throw new RuntimeException("Insufficient Balance!");

                        f.withdraw(amt);
                        t.deposit(amt);
                        System.out.println("Transfer Successful.");
                        break;

                    case 5: // STATEMENT
                        System.out.print("Acc No: ");
                        Account st = getAcc(sc.next());
                        System.out.println("Statement for " + st.accNo);
                        for (Transaction tx : st.history) System.out.println(tx);
                        break;

                    case 6: // LIST ACCOUNTS
                        for (Account ac : accounts.values())
                            System.out.println(ac.accNo + " | " + ac.customer.name + " | Balance: " + ac.balance);
                        break;

                    case 0:
                        System.out.println("Goodbye!");
                        return;

                    default:
                        System.out.println("Invalid Choice!");

                }
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }
}
