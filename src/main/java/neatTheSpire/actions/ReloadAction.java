package neatTheSpire.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;

public class ReloadAction extends AbstractGameAction {
    public ReloadAction(AbstractCreature source) {
        this.source = source;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            int drawAmount = 0;
            Iterator var1 = AbstractDungeon.player.hand.group.iterator();

            while(var1.hasNext()) {
                AbstractCard c = (AbstractCard)var1.next();
                if (c.type != AbstractCard.CardType.SKILL) {
                    this.addToTop(new DiscardSpecificCardAction(c));
                    drawAmount++;
                }
            }

            this.addToBot(new DrawCardAction(AbstractDungeon.player, drawAmount));

            this.isDone = true;
        }

    }
}
