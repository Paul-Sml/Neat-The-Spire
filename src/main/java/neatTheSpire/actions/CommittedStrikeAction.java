//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package neatTheSpire.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class CommittedStrikeAction extends AbstractGameAction {

    public AbstractCard c;

    public CommittedStrikeAction(AbstractCard card) {
        this.c = card;
    }

    public void update() {
        if (AbstractDungeon.player.discardPile.group.contains(this.c))
            AbstractDungeon.player.discardPile.moveToHand(c);

        if (AbstractDungeon.player.drawPile.group.contains(this.c))
            AbstractDungeon.player.drawPile.moveToHand(c);

        this.tickDuration();
    }
}
