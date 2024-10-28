package neatTheSpire.cards.colorless;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.blue.Darkness;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Dark;
import com.megacrit.cardcrawl.relics.BottledFlame;
import com.megacrit.cardcrawl.relics.Courier;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import neatTheSpire.actions.LightSwitchAction;
import neatTheSpire.cards.AbstractDynamicCard;
import neatTheSpire.neatTheSpireMod;
import neatTheSpire.orbs.NeatTheSpireLightOrb;

import static neatTheSpire.neatTheSpireMod.makeCardPath;

public class TurnOff extends AbstractDynamicCard {

    public static final String ID = neatTheSpireMod.makeID(TurnOff.class.getSimpleName());
    public static final String IMG = makeCardPath("TurnOff.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.NONE;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = -2;

    public TurnOff() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.onChoseThisOption();
    }

    public void onChoseThisOption() {
        /*this.addToBot(new VFXAction(new BorderLongFlashEffect(Color.BLACK, true)));
        this.addToBot(new LightSwitchAction(false));
        this.addToBot(new ChannelAction(new Dark()));*/
        this.addToBot(new ChannelAction(new Dark()));
    }

    @Override
    public void upgrade() {
    }
}
