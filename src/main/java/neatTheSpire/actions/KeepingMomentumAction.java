//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package neatTheSpire.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class KeepingMomentumAction extends AbstractGameAction {

    public AbstractCard c;

    public KeepingMomentumAction(AbstractCard card) {
        this.c = card;
    }

    public void update() {
        if (AbstractDungeon.player.exhaustPile.group.contains(this.c))
            for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
                c.stopGlowing();
                c.unhover();
                c.unfadeOut();
            }
            AbstractDungeon.player.exhaustPile.moveToDeck(c, false);

        this.tickDuration();
    }
}
