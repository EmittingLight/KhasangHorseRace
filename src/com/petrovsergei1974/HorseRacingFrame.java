package com.petrovsergei1974;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;

final class HorseRacingFrame extends JFrame implements HorseListener {
    private final int horseRaceSize;
    private final HorseRace horseRace;
    private Horse betHorse;

    private final JPanel rootPanel = new JPanel();
    private final HorseRacePanel horseRacePanel;
    private final HorseRaceStandingsPanel horseRaceStandingsPanel;

    HorseRacingFrame(int horseRaceSize) {
        super("Лошадиные Бега");
        this.horseRaceSize = horseRaceSize;
        this.horseRace = new HorseRace(horseRaceSize);
        this.horseRacePanel = new HorseRacePanel(horseRace, this);
        this.horseRaceStandingsPanel = new HorseRaceStandingsPanel(horseRace.getHorseRaceStandings());

        initComponents();
        initFrame();
        pack();
        setLocationRelativeTo(null);
    }

    private void initFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(900, 350));
        setLayout(new BorderLayout());
        add(rootPanel, BorderLayout.CENTER);
    }

    private void initComponents() {
        rootPanel.setLayout(new GridLayout(1,2));
        rootPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        rootPanel.add(horseRacePanel);
        rootPanel.add(horseRaceStandingsPanel);
    }

    void raceReset() {
        horseRaceStandingsPanel.setBetResult("Вы выбрали " + betHorse.getName());
    }

    @Override
    public synchronized void raceStarted(HorseEvent he) {
        // пока лошадь бежит, индикатор выполнения красный
        he.getSource().getHorseProgressBar().setForeground(Color.RED);
    }

    @Override
    public synchronized void raceProgress(HorseEvent he) {
        System.out.println(Thread.currentThread().getName() + " обновление прогресса гонки");
        horseRace.updateHorseRaceStandings();
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    horseRaceStandingsPanel.refreshTableModel();
                }
            });
        } catch (InterruptedException | InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " обновление прогресса гонки");
    }

    @Override
    public synchronized void raceFinished(HorseEvent he) {
        System.out.println(Thread.currentThread().getName() + " достиг финишной черты");
        // когда лошадь достигнет финишной черты, индикатор станет синим
        he.getSource().getHorseProgressBar().setForeground(Color.BLUE);
        horseRacePanel.incrementClosedThreadsCount();

        // проверить, все ли лошади дошли до финиша
        // следующие строки кода после этого, если выполняются, только если все лошади достигли финишной черты
        if (horseRacePanel.getClosedThreadsCount() != horseRaceSize) return;

        // проверить, выиграла ли лошадь, на которую была сделана ставка
        if (horseRace.getHorseRaceStandings().get(0).equals(betHorse)) {
            horseRaceStandingsPanel.setBetResult("Вы Победили!");
        } else {
            horseRaceStandingsPanel.setBetResult("Вы Проиграли!");
        }

        // Кнопка "Начать гонку" снова активирована
        horseRacePanel.getStartRaceButton().setEnabled(true);
    }

    void showHorseBettingDialog() {
        HorseBettingDialog hbd = new HorseBettingDialog(this, horseRace.getHorseRaceLineup());
        hbd.setVisible(true);
    }

    Horse getBetHorse() {
        return betHorse;
    }

    void setBetHorse(Horse betHorse) {
        this.betHorse = betHorse;
    }
}