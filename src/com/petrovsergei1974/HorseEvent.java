package com.petrovsergei1974;

import java.util.EventObject;

final class HorseEvent extends EventObject {
    HorseEvent(HorsePanel source) {
        super(source);
    }

    @Override
    public HorsePanel getSource() {
        return (HorsePanel) super.getSource();
    }
}