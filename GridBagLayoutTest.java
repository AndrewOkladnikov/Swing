package ru.learning.swing.myLibrary;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class GridBagLayoutTest extends JFrame {
    private Controller controller;
    private final GridBagLayout gridbag = new GridBagLayout();
    private final JTable table = new JTable();
    private JLabel bookLabel,clientNameLabel;
    private JScrollPane scrollPane;
    private JTextField bookName,clientName;
    private JButton changeBut,addBut,booksBut,readersBut,renderedBooksBut,addBookBut,addReaderBut,addTakenBookBut,deleteBut,findUser,findBook;

    public GridBagLayoutTest() {
        initComponents();
        buildView();
        initListeners();
    }

    private void initListeners() {
        booksBut.addActionListener((ActionEvent e) -> {controller.getBooks();});
        readersBut.addActionListener((ActionEvent e) -> {controller.getReaders();});
        renderedBooksBut.addActionListener((ActionEvent e) -> {controller.getTakenBooks();});
        addBookBut.addActionListener((ActionEvent e) -> {controller.addBook();});
        addReaderBut.addActionListener((ActionEvent e) -> {controller.addReader();});
        addTakenBookBut.addActionListener((ActionEvent e) -> {controller.addTakenBook();});
        changeBut.addActionListener((ActionEvent e) -> {controller.updateRecord();});
        addBut.addActionListener((ActionEvent e) -> {controller.addRecord();});
        deleteBut.addActionListener((ActionEvent e) -> {controller.deleteRecord();});
        findUser.addActionListener((ActionEvent e) -> {controller.findReader(clientName);});
        findBook.addActionListener((ActionEvent e) -> {controller.findBook(bookName);});
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.setReferencedIndex();
            }
        });
    }

    private void buildView() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 0.0;
        constraints.weighty = 1.0;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        addComponent(scrollPane, constraints);
        constraints.weightx = 1.0;
        constraints.weighty = 0.0;
        constraints.gridwidth = 1;
        addComponent(booksBut, constraints);
        addComponent(addBookBut, constraints);
        addComponent(bookLabel, constraints);
        addComponent(bookName, constraints);
        constraints.gridwidth = 0;
        addComponent(findBook, constraints);
        constraints.gridwidth = 1;
        addComponent(readersBut, constraints);
        addComponent(addReaderBut, constraints);
        addComponent(clientNameLabel, constraints);
        addComponent(clientName, constraints);
        constraints.gridwidth = 0;
        addComponent(findUser, constraints);
        constraints.gridwidth = 1;
        addComponent(renderedBooksBut, constraints);
        addComponent(addTakenBookBut, constraints);
        addComponent(changeBut, constraints);
        addComponent(deleteBut, constraints);
        addComponent(addBut, constraints);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setBounds(100, 100, 1200, 600);
    }

    private void initComponents(){
        setTitle("Библиотека");
        setLayout(gridbag);
        table.setAutoCreateRowSorter(true);
        scrollPane = new JScrollPane(table);
        bookLabel = new JLabel("Название книги -->", SwingConstants.CENTER);
        clientNameLabel = new JLabel("Фамилие читателя -->", SwingConstants.CENTER);
        bookName = new JTextField(10);
        clientName = new JTextField(10);
        changeBut = new JButton("Изменить");
        changeBut.setToolTipText("Обновление записей для книг и читателей.Сделайте изменения в списке выборки.Строка должна быть выделена");
        changeBut.setBackground(Color.PINK);
        addBut = new JButton("Добавить запись");
        addBut.setBackground(Color.PINK);
        addBut.setToolTipText("Добавить новую запись");
        booksBut = new JButton("Книги");
        readersBut = new JButton("Пользователи");
        renderedBooksBut = new JButton("Выданные книги");
        addBookBut = new JButton("Добавить книгу");
        addReaderBut = new JButton("Добавить пользователя");
        addTakenBookBut = new JButton("Зарегистрировать взятую книгу");
        deleteBut = new JButton("Удалить запись");
        deleteBut.setToolTipText("Выделите строку из списка и нажмите эту кнопку.");
        deleteBut.setBackground(Color.PINK);
        findUser = new JButton("Найти читателя");
        findBook = new JButton("Найти книгу");
    }

    public void setController(Controller controller) {this.controller = controller;}

    public void setTableModel(DefaultTableModel model) { table.setModel(model);}

    public JTable getTable() { return table;}

    protected void addComponent(Component component, GridBagConstraints c) {
        gridbag.setConstraints(component, c);
        add(component);
    }

}