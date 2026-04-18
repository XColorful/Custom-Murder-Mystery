package xiao.murdermystery.api.game.process.murdermystery;

public enum MurderMysteryRole {
    SURVIVOR,
    DETECTIVE,
    MURDER,
    NONE;

    public boolean hasRole() {
        return this != NONE;
    }

    public boolean isSurvivor() {
        return this == SURVIVOR;
    }
    public boolean isDetective() {
        return this == DETECTIVE;
    }
    public boolean isSurvivorOrDetective() {
        return this == SURVIVOR || this == DETECTIVE;
    }
    public boolean isMurder() {
        return this == MURDER;
    }
}
