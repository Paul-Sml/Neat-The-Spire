//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package neatTheSpire.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ParallelConvergenceAction extends AbstractGameAction {

    public AbstractCreature own;

    public ParallelConvergenceAction(AbstractCreature owner) {
        this.own = owner;
    }

    public void update() {
        if (((AbstractMonster)this.own).getIntentBaseDmg() >= 0) {
            this.addToBot(new StunMonsterAction((AbstractMonster)this.own, this.own));
        }

        this.isDone = true;
    }
}
