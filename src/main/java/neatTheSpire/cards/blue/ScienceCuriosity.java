package neatTheSpire.cards.blue;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.blue.Equilibrium;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Plasma;
import com.megacrit.cardcrawl.powers.FocusPower;
import neatTheSpire.cards.AbstractDynamicCard;
import neatTheSpire.neatTheSpireMod;
import neatTheSpire.orbs.NeatTheSpireMagneticOrb;

import static neatTheSpire.neatTheSpireMod.makeCardPath;

public class ScienceCuriosity extends AbstractDynamicCard {

    public static final String ID = neatTheSpireMod.makeID(ScienceCuriosity.class.getSimpleName());
    public static final String IMG = makeCardPath("ScienceCuriosity.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = CardColor.BLUE;

    private static final int COST = 2;
    private static final int UPGRADE_COST = 1;

    //private static final int BLOCK = 10;
    //private static final int UPGRADE_PLUS_BLOCK = 4;


    public ScienceCuriosity() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        //baseBlock = BLOCK;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for(int i = 0; i < this.magicNumber; ++i) {
            this.addToBot(new ApplyPowerAction(p, p, new FocusPower(p, -1), -1));
            this.addToBot(new ChannelAction(new NeatTheSpireMagneticOrb()));
            this.addToBot(new ChannelAction(new Plasma()));
        }
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.upgradeBaseCost(UPGRADE_COST);
            //upgradeBlock(UPGRADE_PLUS_BLOCK);
            //this.upgradeMagicNumber(1);
            //this.upgradeDefaultSecondMagicNumber(1);
        }
    }
}
