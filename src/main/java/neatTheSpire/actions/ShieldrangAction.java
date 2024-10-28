package neatTheSpire.actions;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

//import com.megacrit.cardcrawl.actions.ActionType;
//import com.megacrit.cardcrawl.actions.AttackEffect;
public class ShieldrangAction extends AbstractGameAction
{
    private final DamageInfo info;

    public ShieldrangAction(final AbstractCreature target, final DamageInfo info) {
        this.setValues(target, this.info = info);
        this.actionType = ActionType.DAMAGE;
        this.duration = 0.1f;
    }

    @Override
    public void update() {
        if (this.duration == 0.1f && this.target != null) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.SLASH_VERTICAL));
            int startHp = this.target.currentHealth;
            this.target.damage(this.info);
            int damageDealt = startHp - this.target.currentHealth;

            AbstractDungeon.actionManager.addToTop(new GainBlockAction(AbstractDungeon.player, damageDealt));

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }
        this.tickDuration();
        this.isDone = true;
    }
}