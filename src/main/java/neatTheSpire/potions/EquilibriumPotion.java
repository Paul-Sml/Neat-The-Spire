package neatTheSpire.potions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import neatTheSpire.neatTheSpireMod;

public class EquilibriumPotion extends AbstractPotion {

    public static final String POTION_ID = neatTheSpireMod.makeID("EquilibriumPotion");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public EquilibriumPotion() {
        // The bottle shape and inside is determined by potion size and color. The actual colors are the main DefaultMod.java
        super(NAME, POTION_ID, PotionRarity.RARE, PotionSize.S, PotionColor.STRENGTH);
        this.labOutlineColor = Color.RED;

        // Potency is the damage/magic number equivalent of potions.
        potency = getPotency();

        // Initialize the Description
        description = DESCRIPTIONS[0];

        // Do you throw this potion at an enemy or do you just consume it.
        isThrown = false;

        // Initialize the on-hover name + description
        tips.add(new PowerTip(name, description));

    }

    @Override
    public void use(AbstractCreature target) {
        target = AbstractDungeon.player;
        AbstractCreature p = AbstractDungeon.player;

        if (AbstractDungeon.player.hasPower(StrengthPower.POWER_ID) && AbstractDungeon.player.hasPower(DexterityPower.POWER_ID)) {
            int str = AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount;
            int dex = AbstractDungeon.player.getPower(DexterityPower.POWER_ID).amount;

            if (str > dex)
                this.addToBot(new ApplyPowerAction(target, p, new DexterityPower(target, (str - dex) ), (str - dex) ));
            if (dex > str)
                this.addToBot(new ApplyPowerAction(target, p, new StrengthPower(target, (str - dex) ), (str - dex) ));

        }

        if (AbstractDungeon.player.hasPower(StrengthPower.POWER_ID) && !AbstractDungeon.player.hasPower(DexterityPower.POWER_ID)) {
            int str = AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount;
            this.addToBot(new ApplyPowerAction(target, p, new DexterityPower(target, str ), str ));
        }

        if (!AbstractDungeon.player.hasPower(StrengthPower.POWER_ID) && AbstractDungeon.player.hasPower(DexterityPower.POWER_ID)) {
            int dex = AbstractDungeon.player.getPower(DexterityPower.POWER_ID).amount;
            this.addToBot(new ApplyPowerAction(target, p, new StrengthPower(target, dex ), dex ));
        }

        /*if (AbstractDungeon.player.hasPower(StrengthPower.POWER_ID) && AbstractDungeon.player.hasPower(DexterityPower.POWER_ID)) {
            if (AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount > AbstractDungeon.player.getPower(DexterityPower.POWER_ID).amount) {
                this.addToTop(new ApplyPowerAction(target, AbstractDungeon.player, new DexterityPower(target,
                        AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount - AbstractDungeon.player.getPower(DexterityPower.POWER_ID).amount), AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount - AbstractDungeon.player.getPower(DexterityPower.POWER_ID).amount));

            } else if (AbstractDungeon.player.getPower(DexterityPower.POWER_ID).amount > AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount) {
                this.addToTop(new ApplyPowerAction(target, AbstractDungeon.player, new StrengthPower(target,
                        AbstractDungeon.player.getPower(DexterityPower.POWER_ID).amount - AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount), AbstractDungeon.player.getPower(DexterityPower.POWER_ID).amount - AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount));

            }
        }

        if (target.hasPower(StrengthPower.POWER_ID) && !target.hasPower(DexterityPower.POWER_ID)) {
            this.addToTop(new ApplyPowerAction(target, AbstractDungeon.player, new DexterityPower(target, AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount), AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount));
        }

        if (!target.hasPower(StrengthPower.POWER_ID) && target.hasPower(DexterityPower.POWER_ID)) {
            this.addToTop(new ApplyPowerAction(target, AbstractDungeon.player, new StrengthPower(target, AbstractDungeon.player.getPower(DexterityPower.POWER_ID).amount), AbstractDungeon.player.getPower(DexterityPower.POWER_ID).amount));
        }*/
    }

    @Override
    public AbstractPotion makeCopy() {
        return new EquilibriumPotion();
    }

    // This is your potency.
    @Override
    public int getPotency(final int potency) {
        return 0;
    }

    public void upgradePotion()
    {
        //potency += 0;
        tips.clear();
        tips.add(new PowerTip(name, description));
    }
}