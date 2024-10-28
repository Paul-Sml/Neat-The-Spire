package neatTheSpire.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;

import java.util.ArrayList;
import java.util.Map;


public class RandomTaggedCardAction extends AbstractGameAction {
    public boolean upgradeCard;
    public AbstractCard.CardTags tag;
    public boolean free;
    public boolean oneless;

    public RandomTaggedCardAction(boolean upgraded, AbstractCard.CardTags tagToSearch) {
        this.upgradeCard = upgraded;
        this.tag = tagToSearch;
        this.free = true;
        this.oneless = false;
    }

    public void update() {

        ArrayList<String> tmp = new ArrayList<>();

        for (Map.Entry<String, AbstractCard> stringAbstractCardEntry : CardLibrary.cards.entrySet()) {
            Map.Entry<String, AbstractCard> c = (Map.Entry) stringAbstractCardEntry;
            if (c.getValue().hasTag(this.tag)) {
                tmp.add(c.getKey());
            }
        }


        AbstractCard cStudy = CardLibrary.cards.get(tmp.get(AbstractDungeon.cardRng.random(0, tmp.size() - 1))).makeStatEquivalentCopy();
        if (this.upgradeCard) {
            cStudy.upgrade();
        }
        if (this.free) {
            cStudy.freeToPlayOnce = true;
        }
        if (this.oneless) {
            cStudy.modifyCostForCombat(-1);
        }

        cStudy.costForTurn = 0;
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(cStudy));

        this.isDone = true;
    }

}


