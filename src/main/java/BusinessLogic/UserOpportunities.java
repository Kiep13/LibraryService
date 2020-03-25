package BusinessLogic;

import Data.Library;
import Interface.CatalogPanel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class UserOpportunities {

    private static UserOpportunities opportunities;

    public DataBaseHelper dbHelper;

    public static String[] headers = new String [] {"Автор", "Название", "Издательство", "Год издания", "Количество страниц", "Жанр", "ISBN", "Библиотека", "Адрес", "Телефон"};
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

    CatalogPanel catalogPanel;

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

    public void setCatalogPanel(CatalogPanel panel) {
        this.catalogPanel = panel;
    }

    public ArrayList<Library> getLibrariesList() {

        ResultSet libraries = dbHelper.getShortInfoLibraries();
        librariesArrayList = new ArrayList<>();
        librariesArrayList.add(new Library(0, "Все", ""));
        try {
            while(libraries.next()) {
                librariesArrayList.add(new Library(libraries.getLong(1), libraries.getString(2), libraries.getString(3)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return librariesArrayList;
    }

    public void updateCatalogTable() {

        int index = catalogPanel.libraryList.getSelectedIndex();
        String id_library;
        if(index == 0){
            id_library = "%";
        } else {
            id_library = String.valueOf(librariesArrayList.get(index).getId());
        }

        String sortLabel = Objects.requireNonNull(catalogPanel.fieldsList.getSelectedItem()).toString();
        sortLabel = findColumnName(sortLabel);

        DataBaseHelper dbHelper = DataBaseHelper.getInstance();
        DatabaseTableModel model = new DatabaseTableModel();
        try {
            ResultSet bookfund = dbHelper.getCatalog(sortLabel, id_library, titleMack, genreMack, authorMack, publisherMask, isbnMack, pageMin, pageMax, yearMin, yearMax);
            model.setDataSource(bookfund, headers);
            catalogPanel.table.setModel(model);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public String findColumnName(String columnName) {
        switch (columnName) {
            case "Автор": return "author";
            case "Название": return "b.title";
            case "Издательство": return "p.publisher";
            case "Год издания": return "b.year";
            case "Количество страниц": return "b.amount_pages";
            case "Жанр": return "g.genre";
            case "ISBN": return "b.ISBN";
            case "Библиотека": return "l.name";
            case "Адрес": return "l.address";
            case "Телефон": return "l.telephone";
        }
        return "";
    }

}
