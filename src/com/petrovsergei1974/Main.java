package com.petrovsergei1974;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        HorseRacingFrame hrf = new HorseRacingFrame(4);
        hrf.showHorseBettingDialog();
        hrf.setVisible(true);
       /* SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // диалоговое окно, в котором пользователь может выбрать лошадь для ставки
                hrf.showHorseBettingDialog();
                // если пользователь не выбрал лошадь для ставки, то не войдет в приложения
                if (hrf.getBetHorse() == null) System.exit(2);
                // отобразить кадр, где будут проходить скачки
                hrf.setVisible(true);
            }
        });

         */
    }
}
