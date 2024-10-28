package neatTheSpire.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.relics.GoldPlatedCables;
import neatTheSpire.orbs.NeatTheSpireMagneticOrb;

public class MagneticImpulseAction extends AbstractGameAction {
    public MagneticImpulseAction() {
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST && !AbstractDungeon.player.orbs.isEmpty()) {

            for (int i=0; i<AbstractDungeon.player.orbs.size(); i++) {
                if (AbstractDungeon.player.orbs.get(i) != null && AbstractDungeon.player.orbs.get(i).ID != null && AbstractDungeon.player.orbs.get(i).ID.equals(NeatTheSpireMagneticOrb.ORB_ID)) {

                    if (AbstractDungeon.player.hasRelic(GoldPlatedCables.ID) && i == 1 && !(AbstractDungeon.player.orbs.get(0) instanceof EmptyOrbSlot)) {
                        ((AbstractOrb)AbstractDungeon.player.orbs.get(0)).onStartOfTurn();
                        ((AbstractOrb)AbstractDungeon.player.orbs.get(0)).onEndOfTurn();
                    }

                    if (i != 0) {
                        if (AbstractDungeon.player.orbs.get(i - 1) != null && AbstractDungeon.player.orbs.get(i - 1).ID != null) {
                            AbstractDungeon.player.orbs.get(i - 1).onStartOfTurn();
                            AbstractDungeon.player.orbs.get(i - 1).onEndOfTurn();
                        }
                    }

                    if (i < AbstractDungeon.player.orbs.size() - 1) {
                        if (AbstractDungeon.player.orbs.get(i + 1) != null && AbstractDungeon.player.orbs.get(i + 1).ID != null) {
                            AbstractDungeon.player.orbs.get(i + 1).onStartOfTurn();
                            AbstractDungeon.player.orbs.get(i + 1).onEndOfTurn();
                        }
                    }

                }
            }
            /*if (AbstractDungeon.player.hasRelic(GoldPlatedCables.ID) && !(AbstractDungeon.player.orbs.get(0) instanceof EmptyOrbSlot) && AbstractDungeon.player.orbs.get(1) instanceof NeatTheSpireMagneticOrb) {
                ((AbstractOrb)AbstractDungeon.player.orbs.get(0)).onStartOfTurn();
                ((AbstractOrb)AbstractDungeon.player.orbs.get(0)).onEndOfTurn();
            }*/
        }

        this.tickDuration();
    }
}
