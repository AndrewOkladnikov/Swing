package ru.learning.swing.myLibrary;

import javax.swing.*;

public class MainClass {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            GridBagLayoutTest view = new GridBagLayoutTest();
            Controller controller = new Controller(new DAO(), view);
            view.setController(controller);
        });
            }
}