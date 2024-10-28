//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package neatTheSpire.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.SpotlightPlayerEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class GrandeFinaleAction extends AbstractGameAction {
    private final int increaseGold;
    private final int heal;
    private final DamageInfo info;
    private static final float DURATION = 0.1F;

    public GrandeFinaleAction(AbstractCreature target, DamageInfo info, int goldAmount, int healAmount) {
        this.info = info;
        this.setValues(target, info);
        this.increaseGold = goldAmount;
        this.heal = healAmount;
        this.actionType = ActionType.DAMAGE;
        this.duration = 0.1F;
    }

    public void update() {
        if (this.duration == 0.1F && this.target != null) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.BLUNT_HEAVY));
            this.target.damage(this.info);
            if ((((AbstractMonster)this.target).isDying || this.target.currentHealth <= 0) && !this.target.halfDead && !this.target.hasPower("Minion")) {
                AbstractDungeon.player.gainGold(this.increaseGold);
                AbstractDungeon.player.heal(this.heal);

                AbstractDungeon.effectList.add(new RainingGoldEffect(this.increaseGold, true));
                AbstractDungeon.effectsQueue.add(new SpotlightPlayerEffect());
            }

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }

        this.tickDuration();
    }
}
