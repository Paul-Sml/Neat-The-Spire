package neatTheSpire.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import neatTheSpire.neatTheSpireMod;
import neatTheSpire.util.TextureLoader;

import static neatTheSpire.neatTheSpireMod.makePowerPath;

//Gain 1 dex for the turn for each card played.

public class SimpletonPower extends AbstractPower {
    public AbstractCreature source;

    public static final String POWER_ID = neatTheSpireMod.makeID("SimpletonPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("Simpleton84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("Simpleton32.png"));

    public SimpletonPower(final AbstractCreature owner) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;

        type = PowerType.BUFF;
        isTurnBased = true  ;

        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }
}

