package ru.learning.swing.myLibrary;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Objects;

public class Controller {
    DAO dao;
    GridBagLayoutTest view;

    public Controller(DAO dao, GridBagLayoutTest view) {
        this.dao = dao;
        this.view = view;
    }

    public void getTakenBooks() {
        DefaultTableModel model = dao.getTakenBooks();
        //view.getTable().getColumnModel().getColumn(0).setMaxWidth(15);
        view.setTableModel(model);
        view.getTable().getColumnModel().getColumn(0).setMaxWidth(20);
    }

    public void getBooks() {
        DefaultTableModel model = dao.getAllBooks();
        view.setTableModel(model);
        view.getTable().getColumnModel().getColumn(0).setMaxWidth(20);
    }

    public void getReaders() {
        DefaultTableModel model = dao.getAllReaders();
        view.setTableModel(model);
        view.getTable().getColumnModel().getColumn(0).setMaxWidth(20);
    }

    public void addBook() {
        dao.setLastTableName("books");
        DefaultTableModel model = dao.getBooksDtm();
        if (model.getRowCount() == 0) model.addRow(new String[model.getColumnCount()]);
        view.setTableModel(model);
        view.getTable().getColumnModel().getColumn(0).setMaxWidth(20);
    }

    public void updateRecord() {
        JTable table = view.getTable();
        dao.updateRow(table);
    }

    public void addReader() {
        dao.setLastTableName("readers");
        DefaultTableModel model = dao.getReadersDtm();
        if (model.getRowCount() == 0) model.addRow(new String[model.getColumnCount()]);
        view.setTableModel(model);
        view.getTable().getColumnModel().getColumn(0).setMaxWidth(20);
    }

    public void addTakenBook() {
        dao.setLastTableName("taken_books");
        DefaultTableModel model = dao.getTakenBooksDtm();
        if (model.getRowCount() == 0) model.addRow(new String[model.getColumnCount()]);
        view.setTableModel(model);
        view.getTable().getColumnModel().getColumn(0).setMaxWidth(20);
    }

    public void deleteRecord() {
        JTable table = view.getTable();
        int[] selectedRows = table.getSelectedRows();
        if (table.getColumnCount() > 0) {
            for (int i : selectedRows) {
                String id = Long.toString((Long) table.getValueAt(i, 0));
                dao.deleteRow(id);
            }
        }
    }

    public void findReader(JTextField clientName) {
        DefaultTableModel model = dao.getUser(clientName);
        view.setTableModel(model);
        view.getTable().getColumnModel().getColumn(0).setMaxWidth(20);
    }

    public void findBook(JTextField bookName) {
        DefaultTableModel model = dao.getBook(bookName);
        view.setTableModel(model);
        view.getTable().getColumnModel().getColumn(0).setMaxWidth(20);
    }

    public void addRecord() {
        JTable table = view.getTable();
        dao.addRecord(table);
    }

    public void setReferencedIndex() {
        int rowIndex = view.getTable().getSelectedRow();
        Long number = (Long) view.getTable().getValueAt(rowIndex, 0);
        if(Objects.equals(dao.getLastTableName(), "taken_books"))return;
        System.out.println(number);
        if (dao.getTakenBooksDtm().getRowCount() == 0) dao.getTakenBooksDtm().addRow(new String[dao.getTakenBooksDtm().getColumnCount()]);
        if (Objects.equals(dao.getLastTableName(), "books"))
            dao.getTakenBooksDtm().setValueAt(number, 0, 1);
        if (Objects.equals(dao.getLastTableName(), "readers"))
            dao.getTakenBooksDtm().setValueAt(number, 0, 2);
    }
}