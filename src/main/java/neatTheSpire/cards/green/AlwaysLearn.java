package neatTheSpire.cards.green;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import neatTheSpire.Intefaces.onGenerateCardMidcombatInterface;
import neatTheSpire.cards.AbstractDynamicCard;
import neatTheSpire.neatTheSpireMod;
import neatTheSpire.powers.AlwaysLearnPower;

import static neatTheSpire.neatTheSpireMod.makeCardPath;

public class AlwaysLearn extends AbstractDynamicCard implements onGenerateCardMidcombatInterface {

    public static final String ID = neatTheSpireMod.makeID(AlwaysLearn.class.getSimpleName());
    public static final String IMG = makeCardPath("AlwaysLearn.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = CardColor.GREEN;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;

    public AlwaysLearn() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new MakeTempCardInHandAction(new Shiv(), this.magicNumber));
        this.addToBot(new ApplyPowerAction(p, p, new AlwaysLearnPower(p, 1), 1));
    }

    //Upgraded stats.
    @Override

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            //upgradeBaseCost(UPGRADED_COST);
        }
    }
}