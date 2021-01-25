/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package routerrental_package;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author Andrew
 */
class date {

    final static int Currentyear = 2020; //#6th requirement final data member to not needing write a year every time
    public int day, month, year;

    date(int day, int month) {
        this.day = day;
        this.month = month;
        this.year = Currentyear;
    }

    date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public String GetDate() {
        return String.valueOf(day) + "/" + String.valueOf(month) + "/" + String.valueOf(year);
    }

    public final static String CurrentDate() { //#7th requirement final method
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date currentdate = new Date();
        return formatter.format(currentdate);
    }
}

class router {

    private int serialnumber;
    public String model;
    public int numberofports;
    public String speed;
    private double Daily, Weekly, Monthly;

    router(String model, int ports, String speed) {
        this.serialnumber = (int) (Math.random() * (999 - 111 + 1) + 111);
        this.model = model;
        numberofports = ports;
        this.speed = speed;
    }

    router(String model, int ports, String speed, double Daily, double Weekly, double Monthly) {
        this(model, ports, speed);
        this.Daily = Daily;
        this.Weekly = Weekly;
        this.Monthly = Monthly;
    }

    public int getSerialnumber() {
        return serialnumber;
    }

    public void setSerialnumber(int serialnumber) {
        this.serialnumber = serialnumber;
    }

    public void SetFees(double Daily, double Weekly, double Monthly) {
        this.Daily = Daily;
        this.Weekly = Weekly;
        this.Monthly = Monthly;
    }

    public double GetFees(String type) {
        if (type.equals("Daily")) {
            return Daily;
        } else if (type.equals("Weekly")) {
            return Weekly;
        } else {
            return Monthly;
        }

    }

    public void Display() {
        System.out.print("\nRouter's Model: " + model);
        System.out.print("\nRouter's Number of ports: " + numberofports);
        System.out.print("\nRouter's Speed: " + speed);
    }
}

class InvalidDataException extends Exception { //#10th requirement Exception class with handling in the code

    public InvalidDataException(String msg) {
        super(msg);
    }
}

class reservation {

    protected int ReservationNumber;
    public String ResDate;
    public date StartDate;
    public date DueDate;
    public String Type;
    public router Router;

    reservation(date startDate, String type, router Router) {
        StartDate = new date(0, 0, 0);
        this.StartDate = startDate;
        Type = type;
        ResDate = date.CurrentDate();
        this.Router = Router;
        ReservationNumber = (int) (Math.random() * (999 - 111 + 1) + 111);
        if (type.equals("Daily")) {
            if (StartDate.day < 30) {
                DueDate = new date(StartDate.day + 1, StartDate.month, StartDate.year);
            } else {
                DueDate = new date(1, StartDate.month + 1, StartDate.year);
            }
        } else if (type.equals("Weekly")) {
            if (StartDate.day <= 23) {
                DueDate = new date(StartDate.day + 7, StartDate.month, StartDate.year);
            } else {
                DueDate = new date(StartDate.day + 7 - 30, StartDate.month + 1, StartDate.year);
            }
        } else if (type.equals("Monthly")) {
            if (StartDate.month < 12) {
                DueDate = new date(StartDate.day, StartDate.month + 1, StartDate.year);
            } else {
                DueDate = new date(StartDate.day, 1, StartDate.year + 1);
            }
        }
    }

    public void ExtendDate(String tyype) {
        if (tyype.equals("Daily")) {
            if (StartDate.day < 30) {
                DueDate.day += 1;
            } else {
                DueDate.day = 1;
                DueDate.month += 1;
            }
        } else if (tyype.equals("Weekly")) {
            if (StartDate.day <= 23) {
                DueDate.day += 7;
            } else {
                DueDate.day += 7 - 30;
                DueDate.month += 1;
            }
        } else if (tyype.equals("Monthly")) {
            if (StartDate.month < 12) {
                DueDate.month += 1;
            } else {
                DueDate.month = 1;
                DueDate.year += 1;
            }
        }
    }

    public void Display() {
        System.out.print("\nReservation Date: " + ResDate);
        System.out.print("\nStart Date: " + StartDate.day + "/" + StartDate.month + "/" + StartDate.year);
        System.out.print("\nDue Date: " + DueDate.day + "/" + DueDate.month + "/" + DueDate.year);
        System.out.print("\nPackage Type: " + Type);
    }
}

class invoice {

    protected int serialnumber;
    protected int ReservationNumber;
    protected double Rentingfees;
    protected double totalfees; //#12th requirement Calculated data member

    public invoice(int serialnumber, int ReservationNumber, double Rentingfees) {
        this.serialnumber = serialnumber;
        this.ReservationNumber = ReservationNumber;
        this.Rentingfees = Rentingfees;
    }

    private double CalculateDiscount(double Renting, double disc) {
        return Renting - (Renting * disc);
    }

    public String PrintInvoice(double discount) {
        double totalfees = CalculateDiscount(Rentingfees, discount);
        String s = "\nRouter's Serial Number: " + String.valueOf(serialnumber) + "\nReservation Number: " + String.valueOf(ReservationNumber)
                + "\nRenting fees: " + String.valueOf(Rentingfees) + "$\nCustomer's Dicount " + String.valueOf(discount)
                + "%\nTotal fees: " + String.valueOf(totalfees) + "$";

        return s;
    }
}
//#4th requirement abstract class //#2nd requirement uses Polymorphism 
//#2nd requirement uses Polymorphism //#11th requirement uses difference access modifiers

abstract class Customer {

    private static int Size = 0; //#8th requirement Static data member
    public String Name;
    private int ID;
    private String Address;
    public String PhoneNumber;
    protected double discount;
    ArrayList<invoice> Invoices;

    Customer(String Name, int ID, String Address, String PhoneNumber) {
        this.Name = Name;
        this.ID = ID;
        this.Address = Address;
        this.PhoneNumber = PhoneNumber;
        Invoices = new ArrayList<invoice>();
        Size++;
    }

    public int getID() {
        return ID;
    }

    public ArrayList<invoice> getInvoices() {
        return Invoices;
    }

    public void setInvoices(invoice Inv) {
        this.Invoices.add(Inv);
    }

    abstract double getDiscount();

    final void Display() {

    }

    public static int getSize() {
        return Size;
    }

}

class Resident extends Customer {

    private final double ResidentRate = 0.25;

    Resident(String Name, int ID, String Address, String PhoneNumber) {
        super(Name, ID, Address, PhoneNumber);
        getDiscount();
    }

    @Override //#3rd Requirement Overriding
    double getDiscount() {
        discount = ResidentRate;
        return discount;
    }

}

class Foreigner extends Customer {

    private final double ForeignerRate = 0;

    Foreigner(String Name, int ID, String Address, String PhoneNumber) {
        super(Name, ID, Address, PhoneNumber);
        getDiscount();
    }

    @Override
    double getDiscount() {
        discount = ForeignerRate;
        return discount;
    }
}

class GetCustomerFactory {  //#13th requirement FACTORY DESIGN PATTERN

    //use getPlan method to get object of type Plan   
    public Customer getInfo(String type, String name, int ID, String Address, String PhoneNumber) {
        if (type == null) {
            return null;
        }
        if (type.equalsIgnoreCase("RESIDENT")) {
            return new Resident(name, ID, Address, PhoneNumber);
        } else if (type.equalsIgnoreCase("FOREIGNER")) {
            return new Foreigner(name, ID, Address, PhoneNumber);
        }
        return null;
    }
}//end of GetPlanFactory class.  

interface ErrorHandlers { //#5th requirement Interface class

    void OutOfGivenRange();

    void WrongRentType();

    void WrongDateFormat();

    void ShortReportLength();

    void WrongNationality();
}

class ShowError implements ErrorHandlers {

    @Override
    public void OutOfGivenRange() {
        System.out.println("Exception: You should Choose from the given numbers only.");
    }

    @Override
    public void WrongRentType() {
        System.out.println("Exception: You should Write Exactly one of the given Rent Types.");

    }

    @Override
    public void WrongDateFormat() {
        System.out.println("Exception: Wrong Date, Enter a real date in the current 5 years only.");

    }

    @Override
    public void ShortReportLength() {
        System.out.println("Exception: Your Report should not be less than 50 letters.");

    }

    @Override
    public void WrongNationality() {
        System.out.println("Exception: You should write Foreigner or Resident only there is no other choices.");

    }
}

public class RouterRental {

    public static void main(String[] args) {
        GetCustomerFactory c = new GetCustomerFactory();
        ArrayList<String> ReportList = new ArrayList<>();
        ArrayList<Customer> CustomerList = new ArrayList<Customer>();
        ArrayList<reservation> Reservations = new ArrayList<reservation>();
        reservation restemp;
        ArrayList<router> Routers = new ArrayList<router>();
        invoice Inv;
        router routemp = new router("S-302", 4, "64MBps", 2, 5, 17);
        Routers.add(routemp);
        routemp = new router("Z-909", 6, "128MBps", 5, 8, 30);
        Routers.add(routemp);
        routemp = new router("B-101", 8, "256MBps", 9, 16, 50);
        Routers.add(routemp);
        routemp = new router("A-301", 3, "64MBps", 4, 7, 25);
        Routers.add(routemp);
        routemp = new router("C-521", 2, "32MBps", 3, 6, 20);
        Routers.add(routemp);
        routemp = new router("J-403", 1, "16MBps", 2, 5, 15);
        Routers.add(routemp);
        routemp = new router("K-909", 12, "512MBps", 18, 30, 100);
        Routers.add(routemp);
        routemp = new router("H-609", 10, "80MBps", 5, 10, 31);
        Routers.add(routemp);
        routemp = new router("L-713", 7, "160MBps", 7, 14, 48);
        Routers.add(routemp);
        String Nationality;
        date startdate;
        int checkID;
        boolean IDnotExist = true;
        Customer custemp;
        String[] curdate = date.CurrentDate().split("/", 3);
        int resnum;
        String EnterName;
        String EnterAddress;
        String EnterPhoneNumber;
        String EnterType = null;
        int EnterDay = 0;
        int EnterMonth = 0;
        int EnterYear = 0;
        String ExtendType;
        int resindex = 0;
        String s;
        String ReportString;
        Scanner in = new Scanner(System.in);

        //adding 2 customers one of them with 2 invoices
        Nationality = "Resident";
        EnterName = "Customer 1";
        EnterAddress = "Cairo";
        EnterPhoneNumber = "+201111111111";
        checkID = 123;
        custemp = c.getInfo(Nationality, EnterName, checkID, EnterAddress, EnterPhoneNumber);
        CustomerList.add(custemp);

        EnterDay = 5;
        EnterMonth = 6;
        EnterYear = 2020;
        startdate = new date(EnterDay, EnterMonth, EnterYear);
        EnterType = "Daily";
        restemp = new reservation(startdate, EnterType, Routers.get(0));
        Routers.remove(0);
        Reservations.add(restemp);
        Inv = new invoice(Reservations.get(0).Router.getSerialnumber(),
                Reservations.get(0).ReservationNumber, Reservations.get(0).Router.GetFees(Reservations.get(0).Type));
        CustomerList.get(0).setInvoices(Inv);

        EnterDay = 14;
        EnterMonth = 6;
        EnterYear = 2020;
        startdate = new date(EnterDay, EnterMonth, EnterYear);
        EnterType = "Monthly";
        restemp = new reservation(startdate, EnterType, Routers.get(1));
        Routers.remove(1);
        Reservations.add(restemp);
        Inv = new invoice(Reservations.get(1).Router.getSerialnumber(), Reservations.get(1).ReservationNumber,
                Reservations.get(1).Router.GetFees(Reservations.get(1).Type));
        CustomerList.get(0).setInvoices(Inv);

        Nationality = "Foreigner";
        EnterName = "Cutomer 2";
        EnterAddress = "Alex";
        EnterPhoneNumber = "+202222222222";
        checkID = 999;
        custemp = c.getInfo(Nationality, EnterName, checkID, EnterAddress, EnterPhoneNumber);
        CustomerList.add(custemp);
        EnterDay = 21;
        EnterMonth = 6;
        EnterYear = 2020;
        startdate = new date(EnterDay, EnterMonth, EnterYear);
        EnterType = "Weekly";
        restemp = new reservation(startdate, EnterType, Routers.get(0));
        Routers.remove(0);
        Reservations.add(restemp);
        Inv = new invoice(Reservations.get(2).Router.getSerialnumber(),
                Reservations.get(2).ReservationNumber, Reservations.get(2).Router.GetFees(Reservations.get(2).Type));
        CustomerList.get(1).setInvoices(Inv);

        for (int index = 0; index < Customer.getSize(); index++) {
            System.out.print("\n\nCustomer Name: " + CustomerList.get(index).Name + "\nCustomer ID: " + CustomerList.get(index).getID());
            for (int i = 0; i < CustomerList.get(index).Invoices.size(); i++) {
                System.out.print("\n\nInvoice " + String.valueOf(i + 1) + ": ");
                s = CustomerList.get(index).Invoices.get(i).PrintInvoice(CustomerList.get(index).discount);
                System.out.print(s);
                System.out.print("\nReservation Details: ");
                for (int j = 0; j < Reservations.size(); j++) {
                    if (Reservations.get(j).ReservationNumber == CustomerList.get(index).Invoices.get(i).ReservationNumber) {
                        Reservations.get(j).Display();
                        System.out.print("\nRouter's Details: ");
                        Reservations.get(j).Router.Display();
                        break;
                    }
                }
            }
        }

        //Input System
        int innerchoice;
        int routerchoice = 0;
        boolean ff;
        ShowError e = new ShowError();
        while (true) {
            System.out.print("\n\n\n\nTo Rent A router Enter \"1\"\nTo Cancel A reservation Enter \"2\"\nTo Extend a reservation Enter \"3\""
                    + "\nTo Change A router Enter \"4\"\nTo Send a Report Enter \"5\"\nTo Diaplay All Customers details Enter \"6\"\n"
                    + "To Display All Available Routers Details Enter \"7\"\n");
            try {
                innerchoice = Integer.valueOf(in.nextLine());
            } catch (NumberFormatException ii) {
                throw new NumberFormatException("The input should be in numbers only");
            }

            //RENT
            if (innerchoice == 1) {
                //RENT checking all ended date routers
                for (int i = 0; i < Reservations.size(); i++) {
                    if (Reservations.get(i).DueDate.day <= Integer.valueOf(curdate[0])
                            && Reservations.get(i).DueDate.month <= Integer.valueOf(curdate[1])
                            && Reservations.get(i).DueDate.year <= Integer.valueOf(curdate[2])) {
                        Routers.add(Reservations.get(i).Router);
                    }
                }
                System.out.print("Enter your Personal ID: ");
                try { //Exception handle built in
                    checkID = Integer.valueOf(in.nextLine());
                } catch (NumberFormatException ii) {
                    throw new NumberFormatException("The input should be in numbers only");
                }
                System.out.print("Choose the router you want to rent(enter the number of the router): \n");
                for (int i = 0; i < Routers.size(); i++) {
                    System.out.print(i + "- " + Routers.get(i).model + "\n");
                }
                try { //using Exception Handle class
                    routerchoice = Integer.valueOf(in.nextLine());
                    if (routerchoice > Routers.size() - 1 || routerchoice < 0) {
                        throw new InvalidDataException("");
                    }
                } catch (NumberFormatException ii) {
                    throw new NumberFormatException("The input should be in numbers only");
                } catch (InvalidDataException ii) {
                    e.OutOfGivenRange();
                    return;
                }
                System.out.print("Reservation Date is: " + date.CurrentDate());
                System.out.print("\nEnter The Start Date: \n");

                try {
                    System.out.print("Day: ");
                    EnterDay = Integer.valueOf(in.nextLine());
                    System.out.print("Month: ");
                    EnterMonth = Integer.valueOf(in.nextLine());
                    System.out.print("Year: ");
                    EnterYear = Integer.valueOf(in.nextLine());
                    if (EnterMonth > 12 || EnterMonth < 0 || EnterDay > 30 || EnterDay < 0 || EnterYear < 2020 || EnterYear > 2025) {
                        throw new InvalidDataException("");
                    }
                } catch (NumberFormatException ii) {
                    throw new NumberFormatException("The input should be in numbers only");
                } catch (InvalidDataException ii) {
                    e.WrongDateFormat();
                    return;
                }
                System.out.print("Write The rent type (Daily/Weekly/Monthly): ");
                try {
                    EnterType = in.nextLine();

                    if (!(EnterType.equals("Daily") || EnterType.equals("Weekly") || EnterType.equals("Monthly"))) {
                        throw new InvalidDataException("");
                    }
                } catch (InvalidDataException ii) {
                    e.WrongRentType();
                    return;
                }
                for (int i = 0; i < Customer.getSize(); i++) {
                    if (CustomerList.get(i).getID() == checkID) {
                        startdate = new date(EnterDay, EnterMonth, EnterYear);
                        restemp = new reservation(startdate, EnterType, Routers.get(routerchoice));
                        Routers.remove(routerchoice);
                        Reservations.add(restemp);
                        Inv = new invoice(Reservations.get(Reservations.size() - 1).Router.getSerialnumber(),
                                Reservations.get(Reservations.size() - 1).ReservationNumber,
                                Reservations.get(Reservations.size() - 1).Router.GetFees(Reservations.get(Reservations.size() - 1).Type));
                        CustomerList.get(i).setInvoices(Inv);
                        IDnotExist = false;
                        System.out.print("\nInvoice Details:\n");
                        System.out.print(Inv.PrintInvoice(CustomerList.get(i).getDiscount()));
                        break;
                    }
                }
                if (IDnotExist) {
                    System.out.print("Looks like you are a new customer, please fill the next few things\n");
                    System.out.print("Enter Nationality(Resident/Foreigner): ");
                    try {
                        Nationality = in.nextLine();
                        if (!(Nationality.equals("Resident") || Nationality.equals("Foreigner"))) {
                            throw new InvalidDataException("");
                        }
                    } catch (InvalidDataException ii) {
                        e.WrongNationality();
                        return;
                    }
                    System.out.print("Enter Name: ");
                    EnterName = in.nextLine();
                    System.out.print("Enter Address: ");
                    EnterAddress = in.nextLine();
                    System.out.print("Enter Phone Number: ");
                    EnterPhoneNumber = in.nextLine();
                    custemp = c.getInfo(Nationality, EnterName, checkID, EnterAddress, EnterPhoneNumber);
                    CustomerList.add(custemp);

                    //RENT
                    startdate = new date(EnterDay, EnterMonth, EnterYear);
                    restemp = new reservation(startdate, EnterType, Routers.get(0));
                    Routers.remove(0);
                    Reservations.add(restemp);
                    Inv = new invoice(Reservations.get(Reservations.size() - 1).Router.getSerialnumber(),
                            Reservations.get(Reservations.size() - 1).ReservationNumber,
                            Reservations.get(Reservations.size() - 1).Router.GetFees(Reservations.get(Reservations.size() - 1).Type));
                    CustomerList.get(CustomerList.size() - 1).setInvoices(Inv);
                    System.out.print("\nInvoice Details:\n");
                    System.out.print(Inv.PrintInvoice(CustomerList.get(CustomerList.size() - 1).getDiscount()));
                }

                IDnotExist = true;

            } else if (innerchoice == 2) {
                System.out.print("Enter Your Reservation Number: ");
                try {
                    resnum = Integer.valueOf(in.nextLine());
                } catch (NumberFormatException ii) {
                    throw new NumberFormatException("The input should be in numbers only");
                }
                boolean Cancell = false;
                ff = true;
                for (int i = 0; i < Reservations.size(); i++) {
                    if (Reservations.get(i).ReservationNumber == resnum) {
                        ff = false;
                        if ((Reservations.get(i).StartDate.day - Integer.valueOf(curdate[0]) >= 2
                                && Reservations.get(i).StartDate.month - Integer.valueOf(curdate[1]) == 0)
                                || (Reservations.get(i).StartDate.day - Integer.valueOf(curdate[0]) + 30 >= 2
                                && Reservations.get(i).StartDate.month - Integer.valueOf(curdate[1]) == 1)) {
                            Cancell = true;
                            Routers.add(Reservations.get(i).Router);
                            Reservations.remove(i);
                            break;
                        }
                    }
                }
                if (Cancell) {
                    outerloop:
                    for (int index = 0; index < Customer.getSize(); index++) {
                        for (int j = 0; j < CustomerList.get(index).Invoices.size(); j++) {
                            if (CustomerList.get(index).Invoices.get(j).ReservationNumber == resnum) {
                                CustomerList.get(index).Invoices.remove(j);
                                System.out.print("Reservation Cancelled Successfully\n");
                                break outerloop;
                            }
                        }
                    }
                    Cancell = false;
                } else if (ff) {
                    System.out.print("Reservation Number Doesn't exist.");
                } else {
                    System.out.print("Sorry you can't cancel if your start date is in less than 3 days\n");
                }

            } else if (innerchoice == 3) {
                System.out.print("Enter Your Reservation Number: ");
                try {
                    resnum = Integer.valueOf(in.nextLine());
                } catch (NumberFormatException ii) {
                    throw new NumberFormatException("The input should be in numbers only");
                }
                System.out.print("Write The extend rent type (Daily/Weekly/Monthly): ");
                try {
                    ExtendType = in.nextLine();
                    if (!(ExtendType.equals("Daily") || ExtendType.equals("Weekly") || ExtendType.equals("Monthly"))) {
                        throw new InvalidDataException("");
                    }

                } catch (InvalidDataException ii) {
                    e.WrongRentType();
                    return;
                }
                ff = true;
                for (int i = 0; i < Reservations.size(); i++) {
                    if (Reservations.get(i).ReservationNumber == resnum) {
                        ff = false;
                        Reservations.get(i).ExtendDate(ExtendType);
                        System.out.print("Time Extended to " + String.valueOf(Reservations.get(i).DueDate.day) + "/"
                                + String.valueOf(Reservations.get(i).DueDate.month) + "/" + String.valueOf(Reservations.get(i).DueDate.year) + ".\n");
                        break;
                    }
                }
                if (ff) {
                    System.out.print("Reservation Number Doesn't exist.");
                }

            } else if (innerchoice == 4) {
                for (int i = 0; i < Reservations.size(); i++) {
                    if (Reservations.get(i).DueDate.day <= Integer.valueOf(curdate[0])
                            && Reservations.get(i).DueDate.month <= Integer.valueOf(curdate[1])
                            && Reservations.get(i).DueDate.year <= Integer.valueOf(curdate[2])) {
                        Routers.add(Reservations.get(i).Router);
                    }
                }
                System.out.print("Enter Your Reservation Number: ");
                try {
                    resnum = Integer.valueOf(in.nextLine());
                } catch (NumberFormatException ii) {
                    throw new NumberFormatException("The input should be in numbers only");
                }
                System.out.print("Choose the router you want to rent(enter the number of the router): ");
                for (int i = 0; i < Routers.size(); i++) {
                    System.out.print(i + " " + Routers.get(i).model + "\n");
                }

                try {
                    routerchoice = Integer.valueOf(in.nextLine());
                    if (routerchoice > Routers.size() - 1 || routerchoice < 0) {
                        throw new InvalidDataException("");
                    }
                } catch (NumberFormatException ii) {
                    throw new NumberFormatException("The input should be in numbers only");
                } catch (InvalidDataException ii) {
                    e.OutOfGivenRange();
                    return;
                }
                resindex = 0;
                ff = true;
                for (int i = 0; i < Reservations.size(); i++) {
                    if (Reservations.get(i).ReservationNumber == resnum) {
                        ff = false;
                        Routers.add(Reservations.get(i).Router);
                        Reservations.get(i).Router = Routers.get(routerchoice);
                        resindex = i;
                        break;
                    }
                }
                if (ff) {
                    System.out.print("Reservation Number Doesn't exist.");
                    break;
                }
                Inv = new invoice(Reservations.get(resindex).Router.getSerialnumber(), Reservations.get(resindex).ReservationNumber,
                        Reservations.get(resindex).Router.GetFees(Reservations.get(resindex).Type));
                outerloop2:
                for (int index = 0; index < Customer.getSize(); index++) {
                    for (int j = 0; j < CustomerList.get(index).Invoices.size(); j++) {
                        if (CustomerList.get(index).Invoices.get(j).ReservationNumber == resnum) {
                            CustomerList.get(index).Invoices.remove(j);
                            CustomerList.get(index).Invoices.add(Inv);
                            break outerloop2;
                        }
                    }
                }

            } else if (innerchoice == 5) {
                System.out.print("Enter Your Report:\n");
                try {
                    ReportString = in.nextLine();
                    if (ReportString.length() < 50) {
                        throw new InvalidDataException("");
                    }
                } catch (InvalidDataException ii) {
                    e.ShortReportLength();
                    return;
                }
                ReportList.add(ReportString);
                System.out.print("Thank you we will review the issue directly.\n");
            } //Display All
            else if (innerchoice == 6) {
                for (int index = 0; index < Customer.getSize(); index++) {
                    System.out.print("\n\nCustomer Name: " + CustomerList.get(index).Name + "\nCustomer ID: " + CustomerList.get(index).getID());
                    for (int i = 0; i < CustomerList.get(index).Invoices.size(); i++) {
                        System.out.print("\n\nInvoice " + String.valueOf(i + 1) + ": ");
                        s = CustomerList.get(index).Invoices.get(i).PrintInvoice(CustomerList.get(index).discount);
                        System.out.print(s);
                        System.out.print("\nReservation Details: ");
                        for (int j = 0; j < Reservations.size(); j++) {
                            if (Reservations.get(j).ReservationNumber == CustomerList.get(index).Invoices.get(i).ReservationNumber) {
                                Reservations.get(j).Display();
                                System.out.print("\nRouter's Details: ");
                                Reservations.get(j).Router.Display();
                                break;
                            }
                        }
                    }
                }
            } else if (innerchoice == 7) {
                for (int j = 0; j < Routers.size(); j++) {

                    System.out.print("\n\nRouters Details: ");
                    Routers.get(j).Display();
                }
            }
        }
    }

}
