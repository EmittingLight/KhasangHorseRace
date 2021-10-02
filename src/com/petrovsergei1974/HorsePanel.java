package com.petrovsergei1974;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

final class HorsePanel extends JPanel implements Runnable {
    private final List<HorseListener> horseListeners = new ArrayList<>();

    private final Horse horse;
    private final JLabel horseLabel = new JLabel();
    private final JProgressBar horseProgressBar = new JProgressBar(0, 100);

    Horse getHorse() {
        return horse;
    }

    JProgressBar getHorseProgressBar() {
        return horseProgressBar;
    }

    HorsePanel(Horse horse) {
        this.horse = horse;
        initComponents();
        initPanel();
    }

    private void initComponents() {
        horseLabel.setText(horse.getName());
        horseProgressBar.setStringPainted(true);
    }

    private void initPanel() {
        setName("Panel " + horse.getName());
        setLayout(new BorderLayout(10, 10));
        add(horseLabel, BorderLayout.WEST);
        add(horseProgressBar, BorderLayout.CENTER);
    }

    void addHorseListener(HorseListener hl) {
        horseListeners.add(hl);
    }

    void removeHorseListener(HorseListener hl) {
        horseListeners.remove(hl);
    }

    @Override
    public void run() {
        // уведомить что лошадь стартовала
        notifyHorseListeners(HorseEventType.СТАРТ_ЗАБЕГА);
        while (horseProgressBar.getValue() < horseProgressBar.getMaximum()) {
            try {
                Thread.sleep(horse.race());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // лошадь прошла 1% пути за этот период
            horseProgressBar.setValue(horse.getPercentageOfRaceTrackTraveled());
            // обновить турнирную таблицу
            notifyHorseListeners(HorseEventType.СМЕНА_ЛИДЕРА);
        }
        // уведомить что лошадь достигла финишной черты
        notifyHorseListeners(HorseEventType.БЕГА_ЗАКОНЧИЛИСЬ);
    }

    private synchronized void notifyHorseListeners(HorseEventType het) {
        System.out.println(Thread.currentThread().getName() + het.toString());
        switch (het) {
            case СТАРТ_ЗАБЕГА:
                horseListeners.forEach(hl -> hl.raceStarted(new HorseEvent(this)));
                break;
            case СМЕНА_ЛИДЕРА:
                horseListeners.forEach(hl -> hl.raceProgress(new HorseEvent(this)));
                break;
            case БЕГА_ЗАКОНЧИЛИСЬ:
                horseListeners.forEach(hl -> hl.raceFinished(new HorseEvent(this)));
                break;
        }
    }
}