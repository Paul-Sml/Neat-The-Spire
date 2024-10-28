package neatTheSpire.cards.purple;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.MantraPower;
import neatTheSpire.cards.AbstractDynamicCard;
import neatTheSpire.neatTheSpireMod;

import static neatTheSpire.neatTheSpireMod.makeCardPath;

public class Ascended extends AbstractDynamicCard {

    public static final String ID = neatTheSpireMod.makeID(Ascended.class.getSimpleName());
    public static final String IMG = makeCardPath("Ascended.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = CardColor.PURPLE;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;

    public Ascended() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractPower ma = p.getPower(MantraPower.POWER_ID);
        if (ma != null) {
            int mantraGain = ma.amount;
            int drawAmount = (mantraGain * 2) - 10;
            if (drawAmount > 0)
                this.addToBot(new ApplyPowerAction(p, p, new MantraPower(p, mantraGain-drawAmount), mantraGain-drawAmount));
            else
                this.addToBot(new ApplyPowerAction(p, p, new MantraPower(p, mantraGain), mantraGain));
            if (drawAmount > 0)
                this.addToBot(new DrawCardAction(drawAmount));
        }
    }


    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBaseCost(UPGRADED_COST);
        }
    }
}
