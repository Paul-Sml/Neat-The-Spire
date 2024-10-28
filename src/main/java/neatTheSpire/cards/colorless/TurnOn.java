package neatTheSpire.cards.colorless;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Dark;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import neatTheSpire.actions.LightSwitchAction;
import neatTheSpire.cards.AbstractDynamicCard;
import neatTheSpire.neatTheSpireMod;
import neatTheSpire.orbs.NeatTheSpireLightOrb;
import neatTheSpire.orbs.NeatTheSpireMagneticOrb;

import static neatTheSpire.neatTheSpireMod.makeCardPath;

public class TurnOn extends AbstractDynamicCard {

    public static final String ID = neatTheSpireMod.makeID(TurnOn.class.getSimpleName());
    public static final String IMG = makeCardPath("TurnOn.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.NONE;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = -2;

    public TurnOn() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.onChoseThisOption();
    }

    public void onChoseThisOption() {
        /*this.addToBot(new VFXAction(new BorderLongFlashEffect(Color.WHITE, true)));
        this.addToBot(new LightSwitchAction(true));
        AbstractOrb orb = new NeatTheSpireLightOrb();
        this.addToBot(new ChannelAction(orb));*/
        this.addToBot(new ChannelAction(new NeatTheSpireMagneticOrb()));

    }

    @Override
    public void upgrade() {
    }
}
