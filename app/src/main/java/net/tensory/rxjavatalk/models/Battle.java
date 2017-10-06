package net.tensory.rxjavatalk.models;

import java.util.List;

public class Battle {
    private List<Combatant> combatants;

    public Battle(List<Combatant> combatants) {
        this.combatants = combatants;
    }

    public List<Combatant> getCombatants() {
        return combatants;
    }
}
