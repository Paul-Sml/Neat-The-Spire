package neatTheSpire.cards.red;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import neatTheSpire.cards.AbstractDynamicCard;
import neatTheSpire.neatTheSpireMod;

import static neatTheSpire.neatTheSpireMod.makeCardPath;

public class OozingGloves extends AbstractDynamicCard {

    public static final String ID = neatTheSpireMod.makeID(OozingGloves.class.getSimpleName());
    public static final String IMG = makeCardPath("OozingGloves.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = CardColor.RED;

    private static final int COST = 1;

    private static final int BLOCK = 11;
    private static final int UPGRADE_PLUS_BLOCK = 4;


    public OozingGloves() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
        //this.baseMagicNumber = 1;
        //this.magicNumber = this.baseMagicNumber;
//        this.defaultBaseSecondMagicNumber = 2;
//        this.defaultSecondMagicNumber = this.defaultBaseSecondMagicNumber;
        this.cardsToPreview = new Slimed();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new GainBlockAction(p, p, this.block));
        this.addToBot(new MakeTempCardInDrawPileAction(new Slimed(), 1, true, true));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            //            this.upgradeMagicNumber(5);
            //this.upgradeDefaultSecondMagicNumber(1);
        }
    }
}
