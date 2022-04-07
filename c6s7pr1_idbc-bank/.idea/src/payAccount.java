import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Scanner;

public class payAccount {
    Connection connection;
    public payAccount()
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","******");
        }catch (Exception e)
        {
            System.out.println(e);
        }
    }
    public String generateAccountNumber()
    {
        String number="";
        try
        {
            PreparedStatement preparedStatementobj=connection.prepareStatement("select * from payaccount order by accountid desc ");
            ResultSet resultSetobj= preparedStatementobj.executeQuery();
            while (resultSetobj.next())
            {
               number= resultSetobj.getString(1);
                break;
            }
        }catch(Exception e)
        {
            System.out.println(e);
        }
        String first="PAIDBC";
       int  n=100001;
       n=Integer.parseInt(number.substring(6,11));
       n++;

        String accountnumber=first.concat(String.valueOf(n));
        System.out.println(accountnumber);
        return  accountnumber;
    }
    public boolean checkage(String dateOfBirth)
    { int year=0;
        try
        {
            SimpleDateFormat format=new SimpleDateFormat("dd-MM-yyyy");
            Date fulldate=format.parse(dateOfBirth);
            SimpleDateFormat yearformat=new SimpleDateFormat("yyyy");
            year=Integer.parseInt(yearformat.format(fulldate));
        }catch(Exception e)
        {
            System.out.println(e);
        }
        if((LocalDate.now().getYear()-year)>=18)
            return  true;

        return false;
    }
    public void openAccount(String dateOfBirth)
    {
        if(checkage(dateOfBirth))
        {
            try
            {
                Scanner scan=new Scanner(System.in);
                PreparedStatement preparedStatementobj= connection.prepareStatement("insert into payaccount values (?,?,?,?)");
                System.out.println("enter your full name");
                String name= scan.nextLine();
                preparedStatementobj.setString(1,generateAccountNumber());
                preparedStatementobj.setString(2,name);
                preparedStatementobj.setInt(3,0);
                preparedStatementobj.setDate(4, java.sql.Date.valueOf(LocalDate.now()));
                System.out.println(preparedStatementobj.executeUpdate());
                System.out.println("Account opened");
                System.out.println("account number "+generateAccountNumber());
                System.out.println("account name "+name);

            }
            catch (Exception e)
            {
                System.out.println(e);
            }
        }
        else
            System.out.println("age is less than 18 ");
    }
    public void deposit()
    {
        try
        {
            int depositamount=0;
            Scanner scanner=new Scanner(System.in);
            System.out.println("enter accountid");
            String accountid= scanner.next();
            System.out.println("enter amount to be depostied");
            depositamount= scanner.nextInt();
            PreparedStatement preparedStatementobj= connection.prepareStatement("update payaccount set amount=amount+? where accountid=?");
            preparedStatementobj.setInt(1,depositamount);
            preparedStatementobj.setString(2,accountid);
            int row=preparedStatementobj.executeUpdate();
            if(row>0)
            {
                System.out.println("amount deposited "+depositamount+" to accountid "+accountid);
                transcation("deposit",accountid,depositamount);

            }else {
                System.out.println("account not found");
            }

        }catch(Exception e)
        {
            System.out.println(e);
        }

    }
    public void transcation(String type,String fromorto,int amount)
    {
        int id = 0;
        try
        {
            if(type.equalsIgnoreCase("deposit")) {

                PreparedStatement preparedStatementobj = connection.prepareStatement("insert into transactions(transactionid,transactiondate,transtype,toaccount,amount) values(?,?,?,?,?)");
                PreparedStatement preparedStatementobj2 = connection.prepareStatement("select * from transactions order by transactionid desc");
                ResultSet resultSetobj = preparedStatementobj2.executeQuery();
                while (resultSetobj.next()) {
                    id = resultSetobj.getInt(1);
                    break;
                }
                //System.out.println(id);
                id++;
                preparedStatementobj.setInt(1, id);
                preparedStatementobj.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
                preparedStatementobj.setString(3, type);
                preparedStatementobj.setString(4,fromorto);
                preparedStatementobj.setInt(5,amount);
                preparedStatementobj.executeUpdate();
            }else if (type.equalsIgnoreCase("withdrawal"))
            {
                PreparedStatement preparedStatementobj = connection.prepareStatement("insert into transactions(transactionid,transactiondate,transtype,fromaccount,amount) values(?,?,?,?,?)");
                PreparedStatement preparedStatementobj2 = connection.prepareStatement("select * from transactions order by transactionid desc");
                ResultSet resultSetobj = preparedStatementobj2.executeQuery();
                while (resultSetobj.next()) {
                    id = resultSetobj.getInt(1);
                    break;
                }
                //System.out.println(id);
                id++;
                preparedStatementobj.setInt(1, id);
                preparedStatementobj.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
                preparedStatementobj.setString(3, type);
                preparedStatementobj.setString(4,fromorto);
                preparedStatementobj.setInt(5,amount);
                preparedStatementobj.executeUpdate();

            }

        }catch (Exception e)
        {
            System.out.println(e);
        }
    }
    public  void withdrawal()
    {
        int withdrawalamount=0;
        try {
            PreparedStatement preparedStatementobj= connection.prepareStatement("update payaccount set amount=amount-? where amount>? and accountid=?");
            Scanner scanner=new Scanner(System.in);
            System.out.println("enter accountid");
            String acccountid= scanner.next();
            System.out.println("enter amount to be withdrawal");
            withdrawalamount= scanner.nextInt();
            preparedStatementobj.setInt(1,withdrawalamount);
            preparedStatementobj.setInt(2,withdrawalamount);
            preparedStatementobj.setString(3,acccountid);
            int row= preparedStatementobj.executeUpdate();
            if(row>0)
            {
                System.out.println("collect your cash "+withdrawalamount);
                transcation("withdrawal",acccountid,withdrawalamount);

            }
            else
            {
                System.out.println("wrong accountid or low on balance");
            }

        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
    public  void checkbalance()
    {
        try {
            int amount=0,i=0;
            Scanner scanner=new Scanner(System.in);
            System.out.println("enter accountid");
            String accountid= scanner.next();
            PreparedStatement preparedStatementobj= connection.prepareStatement("select * from payaccount");
            ResultSet resultSetobj= preparedStatementobj.executeQuery();
            while (resultSetobj.next())
            {
                if (resultSetobj.getString(1).equalsIgnoreCase(accountid))
                {
                    amount= resultSetobj.getInt(3);
                    i=1;
                    break;

                }
            }
            if(i==1) {
                System.out.println("our current balance is "+amount);
            }else {
                System.out.println("wrong account id");
            }

        }catch (Exception e)
        {
            System.out.println(e);
        }
    }
    public void fundstransfer()
    {
        try {
            Scanner scanner=new Scanner(System.in);
            System.out.println("enter your account number");
            String accountid= scanner.next();
            System.out.println("enter amount to be transfered");
            int transferingAmount= scanner.nextInt();
            System.out.println("enter the account number to be transfered");
            String accountidToBeTransfered= scanner.next();
            PreparedStatement preparedStatementobj= connection.prepareStatement("update payaccount set amount=amount-? where amount>? and accountid=? ");
            preparedStatementobj.setInt(1,transferingAmount);
            preparedStatementobj.setInt(2,transferingAmount);
            preparedStatementobj.setString(3,accountid);
            int row=preparedStatementobj.executeUpdate();
            PreparedStatement preparedStatementobj2=connection.prepareStatement("update payaccount set amount=amount+? where accountid=?");
            preparedStatementobj2.setInt(1,transferingAmount);
            preparedStatementobj2.setString(2,accountidToBeTransfered);
            int  row2= preparedStatementobj2.executeUpdate();
            if(row>0 && row2>0)
            {
               int id=0;
                System.out.println("funds transfered");
                PreparedStatement preparedStatementobj3=connection.prepareStatement("insert into transactions values(?,?,?,?,?,?)");
                PreparedStatement preparedStatementobj4 = connection.prepareStatement("select * from transactions order by transactionid desc");
                ResultSet resultSetobj = preparedStatementobj4.executeQuery();
                while (resultSetobj.next()) {
                    id = resultSetobj.getInt(1);
                    break;
                }
                //System.out.println(id);
                id++;
                preparedStatementobj3.setInt(1,id);
                preparedStatementobj3.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
                preparedStatementobj3.setString(3,"fundstransfer");
                preparedStatementobj3.setString(4,accountid);
                preparedStatementobj3.setString(5,accountidToBeTransfered);
                preparedStatementobj3.setInt(6,transferingAmount);
                preparedStatementobj3.executeUpdate();
            }
            else
            {
                System.out.println("error occuered");
            }

        }catch (Exception e)
        {
            System.out.println(e);
        }
    }


}
