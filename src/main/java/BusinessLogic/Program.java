package BusinessLogic;

import Interface.LibraryService;

import javax.swing.*;

public class Program {

    JFrame frame;
    Buildier buildier;
    Checking checking;
    UserOpportunities opportunities;

    public static void main(String [] args) {

        new Program();

    }

    public Program() {

        frame = new LibraryService();


    }

}
