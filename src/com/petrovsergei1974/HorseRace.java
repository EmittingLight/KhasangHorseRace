package com.petrovsergei1974;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

final class HorseRace {
    /**список лошадей перед началом гонки
     */
    private final List<Horse> horseRaceLineup;

    /*отсортированный список заездов лошадей во время гонки
      * - перед началом скачек состав лошадей и зачет лошадей идентичны
      * - во время забега лошадь, лидирующая в скачке, становится первым элементом в этом списке
      * - по окончании забега первым элементом в этом списке становится лошадь-победитель.
      */
    private final List<Horse> horseRaceStandings;

    HorseRace(int horseRaceSize) {
        horseRaceLineup = new ArrayList<>(horseRaceSize);
        horseRaceStandings = new ArrayList<>(horseRaceSize);
        // заполняем лист
        for(int i = 0; i < horseRaceSize; i++) {
            Horse horse = Horse.getRandomInstance(i + 1);
            horseRaceLineup.add(horse);
            horseRaceStandings.add(horse);
        }
    }

    synchronized void updateHorseRaceStandings() {
        System.out.println(Thread.currentThread().getName() + " сортировка результатов скачек");
        Collections.sort(horseRaceStandings);
        System.out.println(Thread.currentThread().getName() + " сортировка результатов скачек");
    }

    List<Horse> getHorseRaceLineup() {
        return horseRaceLineup;
    }

    List<Horse> getHorseRaceStandings() {
        return horseRaceStandings;
    }
}