package neatTheSpire.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.*;
import neatTheSpire.neatTheSpireMod;
import neatTheSpire.util.TextureLoader;

import static neatTheSpire.neatTheSpireMod.makePowerPath;

//Gain 1 dex for the turn for each card played.

public class AlwaysLearnPower extends AbstractPower {
    public AbstractCreature source;

    public static final String POWER_ID = neatTheSpireMod.makeID("AlwaysLearnPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("AlwaysLearn84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("AlwaysLearn32.png"));

    public AlwaysLearnPower(final AbstractCreature owner, int AccAmount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = AccAmount;

        type = PowerType.BUFF;
        isTurnBased = true  ;

        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.cardID.equals(Shiv.ID)) {
            this.addToBot(new ApplyPowerAction(this.owner, this.owner, new AccuracyPower(this.owner, this.amount), this.amount));
        }
    }

    public void atEndOfTurn(boolean isPlayer) {
        this.flash();
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }
}

