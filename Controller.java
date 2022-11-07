package ru.learning.swing.myLibrary;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.util.Objects;

public class Controller {
    DAO dao;
    GridBagLayoutTest view;
    DefaultTableModel model;

    public Controller(DAO dao, GridBagLayoutTest view) {
        this.dao = dao;
        this.view = view;
    }

    public void getTakenBooks() {
        model = dao.getTakenBooks();
        view.setTableModel(model);
        view.getTable().getColumnModel().getColumn(0).setMaxWidth(20);
    }

    public void getBooksInStock() {
        model = dao.getAllBooks();
        view.setTableModel(model);
        view.getTable().getColumnModel().getColumn(0).setMaxWidth(20);
    }

    public void getReaders() {
        model = dao.getAllReaders();
        view.setTableModel(model);
        view.getTable().getColumnModel().getColumn(0).setMaxWidth(20);
    }

    public void addBook() {
        dao.setTableName("books");
        model = dao.getBooksDtm();
        if (model.getRowCount() == 0) model.addRow(new String[model.getColumnCount()]);
        view.setTableModel(model);
        view.getTable().getColumnModel().getColumn(0).setMaxWidth(20);
    }

    public void updateRecord() {
        JTable table = view.getTable();
        dao.updateRow(table);
    }

    public void addReader() {
        dao.setTableName("readers");
        model = dao.getReadersDtm();
        if (model.getRowCount() == 0) model.addRow(new String[model.getColumnCount()]);
        view.setTableModel(model);
        view.getTable().getColumnModel().getColumn(0).setMaxWidth(20);
    }

    public void addTakenBook() {
        model = dao.getTakenBooksDtm();
        dao.setTableName("taken_books");
        if (model.getRowCount() == 0) model.addRow(new String[model.getColumnCount()]);
        fillDatesForTable(model);

        view.setTableModel(model);
        view.getTable().getColumnModel().getColumn(0).setMaxWidth(20);
    }

    private void fillDatesForTable(DefaultTableModel model) {
        LocalDate dateNow = LocalDate.now();
        LocalDate plusMonths = dateNow.plusMonths(1);
        Object valueAt = model.getValueAt(0, 3);
        try {
            LocalDate.parse((String) valueAt);
        } catch (Exception e) {
            model.setValueAt(dateNow, 0, 3);
        }
        valueAt = model.getValueAt(0, 4);
        try {
            LocalDate.parse((String) valueAt);
        } catch (Exception e) {
            model.setValueAt(plusMonths, 0, 4);
        }
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

    public void findReaderByTemplate(JTextField clientName) {
        model = dao.getUser(clientName);
        view.setTableModel(model);
        view.getTable().getColumnModel().getColumn(0).setMaxWidth(20);
    }

    public void findBookByTemplates(JTextField bookName) {
        model = dao.getBook(bookName);
        view.setTableModel(model);
        view.getTable().getColumnModel().getColumn(0).setMaxWidth(20);
    }

    public void addRecord() {
        JTable table = view.getTable();
        dao.addRecord(table);
    }

    public void setReferencesToTakenBooks() {
        int rowIndex = view.getTable().getSelectedRow();
        Long number = (Long) view.getTable().getValueAt(rowIndex, 0);
        if (Objects.equals(dao.getCurrentTableName(), "taken_books")) return;
        System.out.println(number);
        if (dao.getTakenBooksDtm().getRowCount() == 0)
            dao.getTakenBooksDtm().addRow(new String[dao.getTakenBooksDtm().getColumnCount()]);
        if (Objects.equals(dao.getCurrentTableName(), "books"))
            dao.getTakenBooksDtm().setValueAt(number, 0, 1);
        if (Objects.equals(dao.getCurrentTableName(), "readers"))
            dao.getTakenBooksDtm().setValueAt(number, 0, 2);
    }
}