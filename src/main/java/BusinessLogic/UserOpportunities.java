package BusinessLogic;

import Data.Library;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class UserOpportunities {

    private static UserOpportunities opportunities;

    public DataBaseHelper dbHelper;

    public static String[] headers = new String [] {"Author", "Title", "Publisher", "Year", "Pages", "Genre", "ISBN", "Library", "Address", "Telephone", "Amount"};
    private ArrayList<Library> librariesArrayList;

    public String titleMack;
    public String genreMack;
    public String authorMack;
    public String publisherMask;
    public String isbnMack;
    public int pageMin;
    public int pageMax;
    public int yearMin;
    public int yearMax;

    public JTable table;
    public JComboBox<Object> libraryList;
    public JComboBox<Object> fieldsList;

    private UserOpportunities() {

        dbHelper = DataBaseHelper.getInstance();

        titleMack = "%";
        genreMack = "%";
        authorMack = "%";
        publisherMask = "%";
        isbnMack = "%";
        pageMin = 0;
        pageMax = dbHelper.getMaxPage();
        yearMin = 0;
        yearMax = dbHelper.getMaxYear();

    }

    public static UserOpportunities getInstance() {
        if(opportunities == null) {
            opportunities = new UserOpportunities();
        }
        return opportunities;
    }

    public ArrayList<Library> getLibrariesList() {

        ResultSet libraries = dbHelper.getLibraries("Library");
        librariesArrayList = new ArrayList<>();
        librariesArrayList.add(new Library(0, "Все", ""));
        try {
            while(libraries.next()) {
                librariesArrayList.add(new Library(libraries.getLong(1), libraries.getString(2), libraries.getString(3)));
            }
        } catch (SQLException e) {
            Buildier.showErrorMessage("Error", "Errors when getting the list of libraries");
        }
        return librariesArrayList;
    }

    public void viewListOfBooks() {

        int index = libraryList.getSelectedIndex();
        String id_library;
        if(index == 0){
            id_library = "%";
        } else {
            id_library = String.valueOf(librariesArrayList.get(index).getId());
        }

        String sortLabel = Objects.requireNonNull(fieldsList.getSelectedItem()).toString();

        DataBaseHelper dbHelper = DataBaseHelper.getInstance();
        DatabaseTableModel model = new DatabaseTableModel();
        try {
            ResultSet bookfund = dbHelper.getCatalog(sortLabel, id_library, titleMack, genreMack, authorMack, publisherMask, isbnMack, pageMin, pageMax, yearMin, yearMax);
            model.setDataSource(bookfund, headers);
            table.setModel(model);
        } catch (SQLException | ClassNotFoundException e) {
            Buildier.showErrorMessage("Error", "Errors when updating the table");
        }

    }

}
