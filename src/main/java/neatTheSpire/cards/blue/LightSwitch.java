package neatTheSpire.cards.blue;

import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.DarkImpulseAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Dark;
import neatTheSpire.actions.neatTheSpireLightImpulseAction;
import neatTheSpire.cards.AbstractDynamicCard;
import neatTheSpire.cards.colorless.TurnOff;
import neatTheSpire.cards.colorless.TurnOn;
import neatTheSpire.neatTheSpireMod;
import neatTheSpire.orbs.NeatTheSpireLightOrb;

import java.util.ArrayList;

import static neatTheSpire.neatTheSpireMod.makeCardPath;

public class LightSwitch extends AbstractDynamicCard {

    public static final String ID = neatTheSpireMod.makeID(LightSwitch.class.getSimpleName());
    public static final String IMG = makeCardPath("LightSwitch.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = CardColor.BLUE;

    private static final int COST = 1;
    //private static final int UPGRADED_COST = 0;

    public LightSwitch() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ChannelAction(new NeatTheSpireLightOrb()));
        ArrayList<AbstractCard> stanceChoices = new ArrayList();
        stanceChoices.add(new TurnOn());
        stanceChoices.add(new TurnOff());
        this.addToBot(new ChooseOneAction(stanceChoices));
        /*if (this.upgraded) {
            this.addToBot(new DarkImpulseAction());
            this.addToBot(new neatTheSpireLightImpulseAction());
        }*/
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            //this.rawDescription = UPGRADE_DESCRIPTION;
            //initializeDescription();
            //upgradeBlock(UPGRADE_PLUS_BLOCK);
            //            this.upgradeMagicNumber(5);
            //this.upgradeDefaultSecondMagicNumber(1);
            //this.upgradeBaseCost(UPGRADED_COST);
            this.exhaust = false;
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
