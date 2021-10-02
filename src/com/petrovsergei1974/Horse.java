package com.petrovsergei1974;

import java.util.Objects;

final class Horse implements Comparable<Horse> {
    /**
     *мы будем использовать целочисленный тип переменной для сохранения шансов
     * - коэффициент 101 соответствует десятичному выражению коэффициента 1,01.
     * - если вы поставите 1 доллар на лошадь с десятичным коэффициентом 1.01, вы выиграете 1 цент
     */
    private final static int MIN_ODDS = 101;
    private final static int MAX_ODDS = 800;

    private final static int MIN_PERIOD = 1;    // минимальная длина трека
    private final static int MAX_PERIOD = 200;  // максимальная длина трека

    // каждый новый экземпляр будет иметь уникальный идентификатор
    private static int ID = 0;
    private final int id;

    private final String name;
    private final int odds;
    private int percentageOfRaceTrackTraveled;

    Horse(String name, int odds) {
        if (name == null) throw new HorseException("Null name.");
        if (name.trim().isEmpty()) throw new HorseException("Empty name.");
        if (odds < MIN_ODDS) throw new HorseException("Invalid odds.");
        if (odds > MAX_ODDS) throw new HorseException("Invalid odds.");

        this.name = name;
        this.odds = odds;
        this.id = ID++;
    }

    static Horse getRandomInstance(int index) {
        String name = "Лошадь " + index;
        int odds = (int)(Math.random() * (MAX_ODDS - MIN_ODDS) + MIN_ODDS) + 1;
        return new Horse(name, odds);
    }

    int race() {
        if (percentageOfRaceTrackTraveled < 0) throw new HorseException();
        if (percentageOfRaceTrackTraveled > 99) throw new HorseException();
        // with each method call the horse travels 1% of total distance
        percentageOfRaceTrackTraveled++;
        // возвращает, сколько времени лошадь потратила на прохождение 1% пути
        return (int)(Math.random() * (MAX_PERIOD - MIN_PERIOD) + MIN_PERIOD) + 1;
    }

    String[] getHorseAsTableRow() {
        String[] row = new String[2];
        row[0] = name;
        row[1] = String.valueOf(odds / 100.00);
        return row;
    }

    void moveToStartOfTrack() {
        percentageOfRaceTrackTraveled = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Horse)) return false;
        Horse horse = (Horse) o;
        return getId() == horse.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public synchronized int compareTo(Horse horse) {
        if (horse == null) throw new HorseException();
        int comparisonResult = horse.getPercentageOfRaceTrackTraveled() - this.percentageOfRaceTrackTraveled;
        return comparisonResult;
    }

    int getId() {
        return id;
    }

    String getName() {
        return name;
    }

    int getOdds() {
        return odds;
    }

    int getPercentageOfRaceTrackTraveled() {
        return percentageOfRaceTrackTraveled;
    }
}