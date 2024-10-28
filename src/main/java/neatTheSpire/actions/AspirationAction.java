//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package neatTheSpire.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.unique.DualWieldAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.TrueGrit;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.Iterator;

public class AspirationAction extends AbstractGameAction {
    private AbstractCard card;
    private AttackEffect effect;
    private AbstractPlayer p;
    private ArrayList<AbstractCard> cannotCopy = new ArrayList();


    public AspirationAction(AbstractCard card, AbstractMonster target, AttackEffect effect) {
        this.card = card;
        this.p = AbstractDungeon.player;
        this.effect = effect;
        this.target = target;
        this.duration = 0.25F;
    }

    public void update() {
        Iterator var1;
        AbstractCard c;
            if (this.target != null && this.duration == Settings.ACTION_DUR_FAST) {
//            this.card.calculateCardDamage((AbstractMonster)this.target);

            var1 = this.p.hand.group.iterator();

            while(var1.hasNext()) {
                c = (AbstractCard)var1.next();
                if (c.type != AbstractCard.CardType.ATTACK || c == this.card) {//is the card actually in play still in hand ?
                    this.cannotCopy.add(c);
                }
            }

            if (this.cannotCopy.size() == this.p.hand.group.size()) {
                this.isDone = true;
                return;
            }

            if (this.p.hand.group.size() - this.cannotCopy.size() == 1) {
                var1 = this.p.hand.group.iterator();

                while(var1.hasNext()) {
                    c = (AbstractCard)var1.next();
                    if (c.type == AbstractCard.CardType.ATTACK && c != this.card) {
//                        for (int i = 0; i < 2; i++) {
//                            c.calculateCardDamage((AbstractMonster) this.target);
                            this.addToTop(new DamageAction(this.target, new DamageInfo(AbstractDungeon.player, c.damage, DamageInfo.DamageType.THORNS), this.effect));
//                        }
                        this.isDone = true;
                        return;
                    }
                }
            }

            this.p.hand.group.removeAll(this.cannotCopy);
            if (this.p.hand.group.size() > 1) {
                AbstractDungeon.handCardSelectScreen.open("copy damage of", 1, false, false, false, false);
                this.tickDuration();
                return;
            }

            if (this.p.hand.group.size() == 1) {
                AbstractCard ca = this.p.hand.getTopCard();
//                for (int i = 0; i < 2; i++) {
//                    ca.calculateCardDamage((AbstractMonster)this.target);
                    this.addToTop(new DamageAction(this.target, new DamageInfo(AbstractDungeon.player, this.p.hand.getTopCard().damage, DamageInfo.DamageType.THORNS), this.effect));
//                }
                this.returnCards();
                this.isDone = true;
                return;
            }
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            var1 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();

            while(var1.hasNext()) {
                c = (AbstractCard)var1.next();
                p.hand.addToTop(c);

//                for (int i = 0; i < 2; i++) {
//                    c.calculateCardDamage((AbstractMonster)this.target);
                    this.addToTop(new DamageAction(this.target, new DamageInfo(AbstractDungeon.player, c.damage, DamageInfo.DamageType.THORNS), this.effect));
//                }

            }

            this.returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
            return;
        }

        this.tickDuration();


        this.isDone = true;

    }

    private void returnCards() {
        Iterator var1 = this.cannotCopy.iterator();

        while(var1.hasNext()) {
            AbstractCard c = (AbstractCard)var1.next();
            this.p.hand.addToTop(c);
        }

        this.p.hand.refreshHandLayout();
    }
}
