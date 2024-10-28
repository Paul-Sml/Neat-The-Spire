//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package neatTheSpire.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class DesolationStrikeAction extends AbstractGameAction {
    private boolean exhaustCards;
    int d;

    public DesolationStrikeAction(int damage, AbstractMonster target, boolean exhausts) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.WAIT;
        this.source = AbstractDungeon.player;
        this.target = target;
        d = damage;
        this.exhaustCards = exhausts;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (AbstractDungeon.player.drawPile.size() + AbstractDungeon.player.discardPile.size() == 0) {
                this.isDone = true;
                return;
            }

            if (AbstractDungeon.player.drawPile.isEmpty()) {
                this.addToTop(new DesolationStrikeAction(d, (AbstractMonster)this.target, this.exhaustCards));
                this.addToTop(new EmptyDeckShuffleAction());
                this.isDone = true;
                return;
            }

            if (!AbstractDungeon.player.drawPile.isEmpty()) {
                AbstractCard card = AbstractDungeon.player.drawPile.getTopCard();
                /*AbstractDungeon.player.drawPile.group.remove(card);
                AbstractDungeon.getCurrRoom().souls.remove(card);
                card.exhaustOnUseOnce = this.exhaustCards;
                AbstractDungeon.player.limbo.group.add(card);
                card.current_y = -200.0F * Settings.scale;
                card.target_x = (float)Settings.WIDTH / 2.0F + 200.0F * Settings.xScale;
                card.target_y = (float)Settings.HEIGHT / 2.0F;
                card.targetAngle = 0.0F;
                card.lighten(false);
                card.drawScale = 0.12F;
                card.targetDrawScale = 0.75F;*/
                card.applyPowers();
                //this.addToTop(new ExhaustSpecificCardAction(card, AbstractDungeon.player.limbo, true));
                int loops = card.costForTurn;
                if (loops == -1) {
                    for (int i = 0; i < EnergyPanel.getCurrentEnergy(); i++)
                        this.addToTop(new DamageAction(this.target, new DamageInfo(AbstractDungeon.player, d, DamageInfo.DamageType.THORNS  ), AttackEffect.SLASH_HORIZONTAL));
                } else if (loops > 0) {
                    for (int i = 0; i < loops; i++)
                        this.addToTop(new DamageAction(this.target, new DamageInfo(AbstractDungeon.player, d, DamageInfo.DamageType.THORNS  ), AttackEffect.SLASH_HORIZONTAL));

                }
                if (!Settings.FAST_MODE) {
                    this.addToTop(new WaitAction(Settings.ACTION_DUR_MED));
                } else {
                    this.addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
                }
            }

            this.isDone = true;
        }

    }
}
