//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package neatTheSpire.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.TrueGrit;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import java.util.ArrayList;
import java.util.Iterator;

import static neatTheSpire.neatTheSpireMod.makeID;

public class InnerFireAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private static final float DURATION_PER_CARD = 0.25F;
    private AbstractPlayer p;
    private ArrayList<AbstractCard> cannotDuplicate = new ArrayList();
    private AbstractCard card;
    private int dmg;
    private int dmgB;


    public InnerFireAction(AbstractCreature source,AbstractCreature target ,int damage, int damageBoosted, DamageInfo.DamageType damageType) {
        this.actionType = ActionType.DRAW;
        this.duration = 0.25F;
        this.p = AbstractDungeon.player;
        dmg = damage;
        dmgB = damageBoosted;
        this.damageType = damageType;
        this.target = target;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST && !isDone) {
            Iterator var1;
            AbstractCard card;

            var1 = this.p.hand.group.iterator();

            while(var1.hasNext()) {
                card = (AbstractCard)var1.next();
                if (!this.isDualWieldable(card)) {
                    this.cannotDuplicate.add(card);
                }
            }

            if (this.cannotDuplicate.size() == this.p.hand.group.size()) {
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect, false));
                this.target.tint.color.set(Color.RED);
                this.target.tint.changeColor(Color.WHITE.cpy());this.target.damage(new DamageInfo(p, dmg, damageType));
                this.isDone = true;
                return;
            }

            /*if (this.cannotDuplicate.size() == this.p.hand.group.size() - 1) {
                for (AbstractCard c : this.p.hand.group) {
                    if (this.isDualWieldable(c)) {
                        this.p.hand.moveToExhaustPile(c);
                        break;
                    }
                }

                AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect, false));
                this.target.tint.color.set(Color.RED);
                this.target.tint.changeColor(Color.WHITE.cpy());this.target.damage(new DamageInfo(p, dmgB, damageType));
                this.isDone = true;
                return;
            }*/

            this.p.hand.group.removeAll(this.cannotDuplicate);

            AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, true, false, false, true);
            this.addToBot(new WaitAction(0.25F));
            this.tickDuration();

        } else {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                AbstractCard c;
                if (AbstractDungeon.handCardSelectScreen.selectedCards.group.size() > 0) {
                    this.p.hand.moveToExhaustPile(AbstractDungeon.handCardSelectScreen.selectedCards.group.get(0));
                    AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect, false));
                    this.target.tint.color.set(Color.RED);
                    this.target.tint.changeColor(Color.WHITE.cpy());
                    this.target.damage(new DamageInfo(p, dmgB, damageType));
                    //AbstractDungeon.player.exhaustPile.addToTop(AbstractDungeon.handCardSelectScreen.selectedCards.group.get(0));
                    //this.p.hand.moveToExhaustPile(c);
                } else {
                    AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect, false));
                    this.target.tint.color.set(Color.RED);
                    this.target.tint.changeColor(Color.WHITE.cpy());
                    this.target.damage(new DamageInfo(p, dmg, damageType));
                }
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            }
            if (this.cannotDuplicate.size() > 0) {
                this.returnCards();
            }
            this.tickDuration();
            this.isDone = true;
        }
    }

    private void returnCards() {
        Iterator var1 = this.cannotDuplicate.iterator();

        while(var1.hasNext()) {
            AbstractCard c = (AbstractCard)var1.next();
            this.p.hand.addToTop(c);
        }

        this.p.hand.refreshHandLayout();
    }

    private boolean isDualWieldable(AbstractCard card) {
        return card.type != (AbstractCard.CardType.ATTACK);
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(makeID("InnerFireAction"));
        TEXT = uiStrings.TEXT;
    }
}
