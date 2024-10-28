package neatTheSpire.cards.blue;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import neatTheSpire.Intefaces.onGenerateCardMidcombatInterface;
import neatTheSpire.cards.AbstractDynamicCard;
import neatTheSpire.neatTheSpireMod;
import neatTheSpire.powers.ClosedCircuitPower;
import neatTheSpire.powers.ClosedCircuitUpgradedPower;

import static neatTheSpire.neatTheSpireMod.makeCardPath;

public class ClosedCircuit extends AbstractDynamicCard implements onGenerateCardMidcombatInterface {

    public static final String ID = neatTheSpireMod.makeID(ClosedCircuit.class.getSimpleName());
    public static final String IMG = makeCardPath("ClosedCircuit.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.POWER;       //
    public static final CardColor COLOR = CardColor.BLUE;

    private static final int COST = 2;

    public ClosedCircuit() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractPower unupgradedPower = p.getPower(ClosedCircuitPower.POWER_ID);
        AbstractPower upgradedPower = p.getPower(ClosedCircuitUpgradedPower.POWER_ID);
        if (upgradedPower == null) {//only enters if the player doesn't already have the upgraded power

            if (this.upgraded) {//if this card is upgraded...
                if (unupgradedPower != null)//...check if the player has the unupgraded power...
                    this.addToBot(new RemoveSpecificPowerAction(p, p, unupgradedPower));//...and removes it
                this.addToBot(new ApplyPowerAction(p, p, new ClosedCircuitUpgradedPower(p)));//then applies the upgraded power to the player
            } else if (!this.upgraded) {//then if this card is not upgraded and the player doesn't already have the said power...
                this.addToBot(new ApplyPowerAction(p, p, new ClosedCircuitPower(p, p, this.magicNumber), magicNumber));//applies the power to the player
            }
        }
    }

    //Upgraded stats.
    @Override

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}