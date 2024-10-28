package neatTheSpire.cards.blue;

import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.DarkImpulseAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import neatTheSpire.actions.MagneticImpulseAction;
import neatTheSpire.cards.AbstractDynamicCard;
import neatTheSpire.neatTheSpireMod;
import neatTheSpire.orbs.NeatTheSpireMagneticOrb;

import static neatTheSpire.neatTheSpireMod.makeCardPath;

public class Polarization extends AbstractDynamicCard {

    public static final String ID = neatTheSpireMod.makeID(Polarization.class.getSimpleName());
    public static final String IMG = makeCardPath("Polarization.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = CardColor.BLUE;

    private static final int COST = 1;
//    private static final int UPGRADED_COST = 0;

    //private static final int BLOCK = 10;
    //private static final int UPGRADE_PLUS_BLOCK = 4;


    public Polarization() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        //baseBlock = BLOCK;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
//        this.defaultBaseSecondMagicNumber = 2;
//        this.defaultSecondMagicNumber = this.defaultBaseSecondMagicNumber;
        //this.cardsToPreview = new Slimed();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for(int i = 0; i < this.magicNumber; ++i) {
            AbstractOrb orb = new NeatTheSpireMagneticOrb();
            this.addToBot(new ChannelAction(orb));
        }

        if (this.upgraded) {
            this.addToBot(new MagneticImpulseAction());
        }
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
//            this.upgradeBaseCost(UPGRADED_COST);
            //upgradeBlock(UPGRADE_PLUS_BLOCK);
            //this.upgradeMagicNumber(1);
            //this.upgradeDefaultSecondMagicNumber(1);
        }
    }
}
