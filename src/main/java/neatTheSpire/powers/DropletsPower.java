package neatTheSpire.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.HelloPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import neatTheSpire.neatTheSpireMod;
import neatTheSpire.util.TextureLoader;

import java.util.Iterator;

import static neatTheSpire.neatTheSpireMod.makePowerPath;

//Gain 1 dex for the turn for each card played.

public class DropletsPower extends AbstractPower {
    public AbstractCreature source;

    public static final String POWER_ID = neatTheSpireMod.makeID("DropletsPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("Droplets84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("Droplets32.png"));

    public DropletsPower(final AbstractCreature owner, int poisonAmount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = poisonAmount;

        type = PowerType.BUFF;
        isTurnBased = true  ;

        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.costForTurn == 0) {
            Iterator var3 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();

            while(var3.hasNext()) {
                AbstractMonster mo = (AbstractMonster)var3.next();
                this.addToBot(new ApplyPowerAction(mo, this.owner, new PoisonPower(mo, this.owner, this.amount), this.amount));
            }
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}

