package neatTheSpire.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import neatTheSpire.neatTheSpireMod;
import neatTheSpire.util.TextureLoader;

import static neatTheSpire.neatTheSpireMod.makePowerPath;

//Gain 1 dex for the turn for each card played.

public class neatTheSpireLoseFocusPower extends AbstractPower {
    public AbstractCreature source;

    public static final String POWER_ID = neatTheSpireMod.makeID("neatTheSpireLoseFocusPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("Flex84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("Flex32.png"));

    public neatTheSpireLoseFocusPower(final AbstractCreature owner, int newAmount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = newAmount;

        type = PowerType.DEBUFF;
        isTurnBased = true  ;

        this.loadRegion("flex");

        // We load those textures here.
//        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
//        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    public void atEndOfTurn(boolean isPlayer) {
        this.flash();
        this.addToBot(new ApplyPowerAction(this.owner, this.owner, new FocusPower(this.owner, -this.amount), -this.amount));
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }
}

