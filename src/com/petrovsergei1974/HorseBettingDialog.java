package com.petrovsergei1974;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

final class HorseBettingDialog extends JDialog {
    private final HorseRacingFrame frame;
    private final List<Horse> horses;

    private final JPanel rootPanel = new JPanel();

    private final String[] columnNames = new String[]{"Выберите Лошадь", "Действующий коэффициент"};
    private final DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
    private final JTable table = new JTable(tableModel);
    private final JScrollPane tablePanel = new JScrollPane(table);

    private final JButton placeBetButton = new JButton("Сделать ставку");

    HorseBettingDialog(HorseRacingFrame frame, List<Horse> horses) {
        super(frame, "Делайте ваши ставки", true);
        this.frame = frame;
        this.horses = horses;
        initTable();
        initRootPanel();
        initDialog();
        initListeners();
        pack();
        setLocationRelativeTo(null);
        // должен вызываться для отображения в центре экрана
    }

    private void initTable() {
        tablePanel.setBorder(BorderFactory.createEmptyBorder());
        // установка редактора по умолчанию, как показано ниже, отключает редактирование ячейки
        table.setDefaultEditor(Object.class, null);
        // заполнить таблицу
        horses.forEach(horse -> tableModel.addRow(horse.getHorseAsTableRow()));
    }

    private void initDialog() {
        setPreferredSize(new Dimension(400, 200));
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        add(rootPanel, BorderLayout.CENTER);
    }

    private void initRootPanel() {
        rootPanel.setLayout(new BorderLayout());
        rootPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        rootPanel.add(tablePanel, BorderLayout.CENTER);
        rootPanel.add(placeBetButton, BorderLayout.SOUTH);
    }

    private void initListeners() {
        placeBetButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            // если при нажатии кнопки ни одна строка не выбрана, ничего не делать
            if (selectedRow < 0) return;
            // иначе сохранить выбранную лошадь и закройте это диалоговое окно
            Horse selectedHorse = horses.get(selectedRow);
            frame.setBetHorse(selectedHorse);
            this.setVisible(false);
        });
    }
}