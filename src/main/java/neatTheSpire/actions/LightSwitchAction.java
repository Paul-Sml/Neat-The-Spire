//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package neatTheSpire.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Dark;
import neatTheSpire.orbs.NeatTheSpireLightOrb;

import java.util.ListIterator;

public class LightSwitchAction extends AbstractGameAction {
    private AbstractPlayer p = AbstractDungeon.player;
    boolean turnOn;

    public LightSwitchAction(boolean on) {
        this.duration = Settings.ACTION_DUR_FAST;
        turnOn = on;
        }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST && !p.orbs.isEmpty()) {
            ListIterator<AbstractOrb> iter = p.orbs.listIterator();
            int index = 0;
            while (iter.hasNext()) {
                AbstractOrb o = iter.next();
                AbstractOrb orbToSet;
                if (o.ID != null) {
                    if ((o.ID.equals(Dark.ORB_ID) && turnOn == true) || (o.ID.equals(NeatTheSpireLightOrb.ORB_ID) && turnOn == false)) {

                        if (turnOn)
                            orbToSet = new NeatTheSpireLightOrb();
                        else
                            orbToSet = new Dark();

                        orbToSet.evokeAmount = o.evokeAmount;
//                        orbToSet.cX = o.cX;
//                        orbToSet.cY = o.cY;
                        orbToSet.updateDescription();
                        orbToSet.updateAnimation();
                        iter.set(orbToSet);
                        orbToSet.setSlot(index, AbstractDungeon.player.maxOrbs);

                    }
                }
                ++index;
            }
            /*for (int i=0; i<AbstractDungeon.player.orbs.size(); i++) {
                if (AbstractDungeon.player.orbs.get(i).ID.equals(Dark.ORB_ID)) {
                    AbstractOrb orb = new neatTheSpireLightOrb();
                    orb.cX = AbstractDungeon.player.orbs.get(i).cX;
                    orb.cY = AbstractDungeon.player.orbs.get(i).cY;
                    AbstractDungeon.player.orbs.set(i, orb);
                }
            }*/
        }
        this.isDone = true;

        this.tickDuration();
    }
}
