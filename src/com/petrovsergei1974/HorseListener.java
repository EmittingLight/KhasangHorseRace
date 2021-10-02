package com.petrovsergei1974;

import java.util.EventListener;

interface HorseListener extends EventListener {
    void raceStarted(HorseEvent he);
    void raceProgress(HorseEvent he);
    void raceFinished(HorseEvent he);
}
