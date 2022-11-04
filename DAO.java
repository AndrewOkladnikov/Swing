package ru.learning.swing.myLibrary;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Objects;
import java.util.Vector;

public class DAO {
    private DefaultTableModel booksDtm, readersDtm, takenBooksDtm;
    private String lastTableName;

    public DAO() {
        initDao();
    }

    public void setLastTableName(String lastTableName) {
        this.lastTableName = lastTableName;
    }

    public String getLastTableName() {
        return lastTableName;
    }

    public DefaultTableModel getBooksDtm() {
        return booksDtm;
    }

    public DefaultTableModel getReadersDtm() {
        return readersDtm;
    }

    public DefaultTableModel getTakenBooksDtm() {
        return takenBooksDtm;
    }

    public DefaultTableModel getAllBooks() {
        lastTableName = "books";
        String GET_BOOKS = "Select * FROM books WHERE id NOT IN " +
                "( Select taken_books.book_id FROM taken_books WHERE id IS NOT NULL)";
        return getTableModel(GET_BOOKS);
    }

    public DefaultTableModel getAllReaders() {
        lastTableName = "readers";
        String GET_READERS = "Select * from readers";
        return getTableModel(GET_READERS);
    }

    public DefaultTableModel getTakenBooks() {
        lastTableName = "taken_books";
        String GET_TAKEN_BOOKS = "Select taken_books.id,books.name \"Название книги\", books.author Автор, " +
                "readers.last_name " +
                "as Фамилия, " +
                "readers.name  \"Имя читателя\"," +
                "taken_books.issue_date Выдана, " +
                "taken_books.planned_return_date \"Взята до\" " +
                "from taken_books " +
                "JOIN books " +
                "ON taken_books.book_id = books.id " +
                "JOIN readers " +
                "ON taken_books.reader_id = readers.id";
        return getTableModel(GET_TAKEN_BOOKS);
    }

    private DefaultTableModel getTableModel(String str) {
        try (Connection con = DataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(str)) {
                if (str.contains("Select")) {
                    ResultSet rs = stmt.executeQuery();
                    return getModelFromResultSet(rs);
                }
                stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private DefaultTableModel getModelFromResultSet(ResultSet rs) {
        Vector<Object> columnNames = new Vector<>();
        Vector<Vector<Object>> data = new Vector<>();
        try {
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();
            for (int i = 1; i <= columns; i++) {
                columnNames.addElement(md.getColumnName(i));
            }
            while (rs.next()) {
                Vector<Object> row = new Vector<Object>(columns);
                for (int i = 1; i <= columns; i++) {
                    row.addElement(rs.getObject(i));
                }
                data.addElement(row);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace(System.out);
        }
        return new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column > 0;
            }
        };
    }

    private void initDao() {
        String GET_BOOKS_0 = "Select * from books Limit 0";
        booksDtm = getTableModel(GET_BOOKS_0);
        String GET_READERS_0 = "Select * from readers Limit 0";
        readersDtm = getTableModel(GET_READERS_0);
        String GET_TAKEN_BOOKS_0 = "Select * from taken_books Limit 0";
        takenBooksDtm = getTableModel(GET_TAKEN_BOOKS_0);
    }

    public void deleteRow(String id) {
        String DELETE = "Delete from " + lastTableName + " where id=" + id;
        getTableModel(DELETE);
    }

    public void updateRow(JTable table) {
        if (Objects.equals(lastTableName, "taken_books") || table.getSelectedRow() == -1) return;
        int selectedRow = table.getSelectedRow();
        int colNum = table.getColumnCount();
        for (int i = 1; i < colNum; i++) {
            String UPDATE = "Update " + lastTableName + " Set " + table.getColumnName(i) + "= '" + table.getValueAt(selectedRow, i) +
                    "' Where " + table.getColumnName(0) + " = " + table.getValueAt(selectedRow, 0);
            getTableModel(UPDATE);
        }
    }

    public DefaultTableModel getUser(JTextField clientName) {
        lastTableName = "readers";
        String GET_READER = "Select * from readers Where upper(last_name) like upper('%" + clientName.getText() + "%') ";
        return getTableModel(GET_READER);
    }

    public DefaultTableModel getBook(JTextField bookName) {
        lastTableName = "books";
        String GET_BOOK = "Select * from books Where Upper(name) like  Upper('%" + bookName.getText() + "%')";
        return getTableModel(GET_BOOK);
    }

    public void addRecord(JTable table) {
        StringBuilder colNames = new StringBuilder();
        if (lastTableName == null) return;
        StringBuilder row = new StringBuilder();
        int colCount = table.getColumnCount();
        for (int i = 1; i < colCount; i++) {
            colNames.append(table.getColumnName(i));
            if (i != colCount - 1) colNames.append(",");
            Object valueAt = table.getValueAt(0, i);
            row.append("'");
            row.append(table.getValueAt(0, i));
            row.append("'");
            if (i != colCount - 1) row.append(",");
        }
        String INSERT = "Insert into " + lastTableName + "(" + colNames + ") values(" + row + ")";
        getTableModel(INSERT);
    }
}