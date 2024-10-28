package neatTheSpire.cards.red;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import neatTheSpire.Intefaces.onGenerateCardMidcombatInterface;
import neatTheSpire.cards.AbstractDynamicCard;
import neatTheSpire.neatTheSpireMod;
import neatTheSpire.powers.DejaVuPower;

import static neatTheSpire.neatTheSpireMod.makeCardPath;

public class DejaVu extends AbstractDynamicCard implements onGenerateCardMidcombatInterface {

    public static final String ID = neatTheSpireMod.makeID(DejaVu.class.getSimpleName());
    public static final String IMG = makeCardPath("DejaVu.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.POWER;       //
    public static final CardColor COLOR = CardColor.PURPLE;

    private static final int COST = 2;
    private static final int UPGRADED_COST = 1;

    public DejaVu() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p, p, new DejaVuPower(p, this.magicNumber), this.magicNumber));
    }

    //Upgraded stats.
    @Override

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
        }
    }
}