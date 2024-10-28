//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package neatTheSpire.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class HauntedAfterimageAction extends AbstractGameAction {
    private AbstractCard card;
    private AttackEffect effect;

    public HauntedAfterimageAction(AbstractCard card, AttackEffect effect) {
        this.card = card;
        this.effect = effect;
    }

    public HauntedAfterimageAction(AbstractCard card) {
        this(card, AttackEffect.NONE);
    }

    public void update() {
        this.target = AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster)null, true, AbstractDungeon.cardRandomRng);
        if (this.target != null) {
            this.card.calculateCardDamage((AbstractMonster)this.target);
            //this.addToTop(new ApplyPowerAction(this.target, AbstractDungeon.player, new Resonance(this.target, AbstractDungeon.player, 1), 1));
            this.addToTop(new DamageAction(this.target, new DamageInfo(AbstractDungeon.player, this.card.damage, this.card.damageTypeForTurn), this.effect));
        }

        this.isDone = true;
    }
}
