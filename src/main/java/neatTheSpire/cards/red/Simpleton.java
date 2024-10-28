package neatTheSpire.cards.red;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import neatTheSpire.Intefaces.onGenerateCardMidcombatInterface;
import neatTheSpire.cards.AbstractDynamicCard;
import neatTheSpire.neatTheSpireMod;
import neatTheSpire.powers.SimpletonPowerUpgraded;

import static neatTheSpire.neatTheSpireMod.makeCardPath;

public class Simpleton extends AbstractDynamicCard implements onGenerateCardMidcombatInterface {

    public static final String ID = neatTheSpireMod.makeID(Simpleton.class.getSimpleName());
    public static final String IMG = makeCardPath("Simpleton.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.POWER;       //
    public static final CardColor COLOR = CardColor.RED;

    private static final int COST = 2;

    public Simpleton() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.cardsToPreview = new Dazed();
    }

    private void effect(AbstractPlayer p) {
        this.addToBot(new ApplyPowerAction(p, p, new SimpletonPowerUpgraded(p)));

//        AbstractPower unupgradedPower = p.getPower(SimpletonPower.POWER_ID);
//        AbstractPower upgradedPower = p.getPower(SimpletonPowerUpgraded.POWER_ID);
//        if (upgradedPower == null) {//only enters if the player doesn't already have the upgraded power
//
//            if (this.upgraded) {//if this card is upgraded...
//                if (unupgradedPower != null)//...check if the player has the unupgraded power...
//                    this.addToBot(new RemoveSpecificPowerAction(p, p, unupgradedPower));//...and removes it
//                this.addToBot(new ApplyPowerAction(p, p, new SimpletonPowerUpgraded(p)));//then applies the upgraded power to the player
//            } else if (!this.upgraded && unupgradedPower == null) {//then if this card is not upgraded and the player doesn't already have the said power...
//                this.addToBot(new ApplyPowerAction(p, p, new SimpletonPower(p)));//applies the power to the player
//            }
//        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        effect(p);
    }

    @Override
    public void triggerOnExhaust() {
        if (this.upgraded)
            effect(AbstractDungeon.player);
    }

    //Upgraded stats.
    @Override

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            //this.cardsToPreview = new Dazed();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}