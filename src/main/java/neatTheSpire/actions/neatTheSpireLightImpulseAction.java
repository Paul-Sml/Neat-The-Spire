//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package neatTheSpire.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import neatTheSpire.orbs.NeatTheSpireLightOrb;

import java.util.Iterator;

public class neatTheSpireLightImpulseAction extends AbstractGameAction {
    public neatTheSpireLightImpulseAction() {
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST && !AbstractDungeon.player.orbs.isEmpty()) {
            Iterator var1 = AbstractDungeon.player.orbs.iterator();

            while(var1.hasNext()) {
                AbstractOrb o = (AbstractOrb)var1.next();
                if (o instanceof NeatTheSpireLightOrb) {
                    o.onStartOfTurn();
                    o.onEndOfTurn();
                }
            }

            if (AbstractDungeon.player.hasRelic("Cables") && !(AbstractDungeon.player.orbs.get(0) instanceof EmptyOrbSlot) && AbstractDungeon.player.orbs.get(0) instanceof NeatTheSpireLightOrb) {
                ((AbstractOrb)AbstractDungeon.player.orbs.get(0)).onStartOfTurn();
                ((AbstractOrb)AbstractDungeon.player.orbs.get(0)).onEndOfTurn();
            }
        }
        this.isDone = true;

        this.tickDuration();
    }
}
