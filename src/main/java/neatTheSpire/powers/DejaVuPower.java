package neatTheSpire.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import neatTheSpire.neatTheSpireMod;
import neatTheSpire.util.TextureLoader;

import static neatTheSpire.neatTheSpireMod.makePowerPath;

//Gain 1 dex for the turn for each card played.

public class DejaVuPower extends AbstractPower {
    public AbstractCreature source;

    public static final String POWER_ID = neatTheSpireMod.makeID("DejaVuPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("DejaVu84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("DejaVu32.png"));

    public DejaVuPower(final AbstractCreature owner, int powerAmount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = powerAmount;

        type = PowerType.BUFF;
        isTurnBased = true  ;

        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        boolean ignoreSelf = true;
        boolean triggered = false;

        for (AbstractCard q : AbstractDungeon.actionManager.cardsPlayedThisTurn) {
            if (q.cardID.equals(card.cardID)) {
                if (q.equals(card) && ignoreSelf) {
                    ignoreSelf = false; //just ignore itself *one* time. If it appears more than once, that means you've actually played it more than once this turn.
                }
                else if (!triggered) {
                    triggered = true;
                }
                else {
                    triggered = false;
                    break;
                }
            }
        }

        if (triggered)
            this.addToBot(new DrawCardAction(this.owner, this.amount));
    }

    @Override
    public void updateDescription() {
        if (this.amount > 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
        }

    }}

