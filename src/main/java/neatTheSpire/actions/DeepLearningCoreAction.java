//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package neatTheSpire.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.Iterator;
import java.util.UUID;

public class DeepLearningCoreAction extends AbstractGameAction {
    public int[] multiDamage;
    private boolean freeToPlayOnce = false;
    private AbstractPlayer p = AbstractDungeon.player;
    private int energyOnUse = -1;
    private UUID uuid;

    public DeepLearningCoreAction(UUID targetUUID, boolean freeToPlayOnce, int miscValue, int energyOnUse) {
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.uuid = targetUUID;
        this.energyOnUse = energyOnUse;
    }

    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }

        AbstractRelic chemical = p.getRelic(ChemicalX.ID);
        if (chemical != null) {
            effect += 2;
            chemical.flash();
        }

        if (effect > 0) {
            Iterator var1 = AbstractDungeon.player.masterDeck.group.iterator();

            AbstractCard c;
            while(var1.hasNext()) {
                c = (AbstractCard)var1.next();
                if (c.uuid.equals(this.uuid)) {
                    c.misc += effect;
                    c.applyPowers();
                    c.baseDamage = c.misc;
//                    c.damage = c.baseDamage;
                    c.isDamageModified = false;
                }
            }

            for(var1 = GetAllInBattleInstances.get(this.uuid).iterator(); var1.hasNext(); c.baseDamage = c.misc) {
                c = (AbstractCard)var1.next();
                c.misc += effect;
                c.applyPowers();
            }

            this.isDone = true;

            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }

        this.isDone = true;
    }
}
