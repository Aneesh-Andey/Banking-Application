import java.util.Scanner;

public class mainmethod {
    public static void main(String[] args) {
        System.out.println("***************  WELCOME TO IDBC BANK  ***********");
        System.out.println(" Choose Account Type");
        System.out.println("1) Savings Account\n2) Pay Account");
        Scanner scan = new Scanner(System.in);
        int choiceOfAccountType= scan.nextInt();
        if (choiceOfAccountType==1)
        {
            /*SAVINGS ACCOUNT OPERATIONS*/
            saveAccount savingaccountobj=new saveAccount();
            System.out.println("1) Open Account\n2) Withdraw\n3) Deposit\n4) Calculate Interest\n5) Funds Transfer\n6) check Balance");
            int choiceInSavingAccount= scan.nextInt();
            switch (choiceInSavingAccount)
            {
                case 1:
                    System.out.println("Enter your Date of Birth");
                    String dob= scan.next();
                    savingaccountobj.openAccount(dob);
                    break;
                case 2:
                    savingaccountobj.withdrawal();
                    break;
                case 3:
                    savingaccountobj.deposit();
                    break;
                case 4:
                    savingaccountobj.interestcal();
                    break;
                case 5:
                    savingaccountobj.fundstransfer();
                    break;
                case 6:savingaccountobj.checkbalance();
                break;
                default:
                    System.out.println("Wrong Option");
                    break;
            }
        }
        else if (choiceOfAccountType==2)
        {
           payAccount payAccountobj=new payAccount();
            System.out.println("1) Open Account\n2) Withdraw\n3) Deposit\n4) Funds Transfer\n5)check Balance");
            int choiceofPayAccount= scan.nextInt();
            switch(choiceofPayAccount)
            {
                case 1:
                    System.out.println("Enter your Date of Birth");
                    String dob= scan.next();
                    payAccountobj.openAccount(dob);
                    break;
                case 2:payAccountobj.withdrawal();
                break;
                case 3:payAccountobj.deposit();
                break;
                case 4:payAccountobj.fundstransfer();
                break;
                case 5:payAccountobj.checkbalance();
                break;
                default:
                    System.out.println("Wrong option");
                    break;
            }
        }
        else {
            System.out.println("Wrong Option");
        }






//saving account

            //obj.generateAccountNumber();
       //obj.openAccount("21-07-2001");

        //  obj.deposit();
       // obj.withdrawal();
       // obj.checkbalance();
       // obj.interestcal();
      //
      //obj.fundstransfer();
        //--------------------------------------------
        //payaccount

       // payAccountobj.generateAccountNumber();
       // payAccountobj.openAccount("21-07-2001");
        //payAccountobj.deposit();
       // payAccountobj.withdrawal();
     //   payAccountobj.checkbalance();
       // payAccountobj.fundstransfer();


    }
}
