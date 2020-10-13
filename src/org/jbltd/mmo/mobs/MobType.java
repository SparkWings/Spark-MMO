package org.jbltd.mmo.mobs;

public enum MobType {

    ZOMBIE("zombie"), SKELETON("skeleton"), PIGLIN("piglin");


    private final String name;

    private MobType(String displayName) {
        name = displayName;
    }

    public boolean equalsName(String otherName) {
        return name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }
}
