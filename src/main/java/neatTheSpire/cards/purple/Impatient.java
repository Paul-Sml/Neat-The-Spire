package neatTheSpire.cards.purple;

import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.CalmStance;
import com.megacrit.cardcrawl.stances.NeutralStance;
import com.megacrit.cardcrawl.stances.WrathStance;
import neatTheSpire.cards.AbstractDynamicCard;
import neatTheSpire.neatTheSpireMod;

import static neatTheSpire.neatTheSpireMod.makeCardPath;

public class Impatient extends AbstractDynamicCard {

    public static final String ID = neatTheSpireMod.makeID(Impatient.class.getSimpleName());
    public static final String IMG = makeCardPath("Impatient.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = CardColor.PURPLE;

    private static final int COST = 1;
//    private static final int UPGRADED_COST = 0;

    public Impatient() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.selfRetain = false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (AbstractDungeon.player.stance.ID.equals(NeutralStance.STANCE_ID)) {
            this.addToTop(new ChangeStanceAction(CalmStance.STANCE_ID));
        } else {
            this.addToTop(new ChangeStanceAction(WrathStance.STANCE_ID));
        }
    }


    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
//            this.upgradeBaseCost(UPGRADED_COST);
            this.selfRetain = true;
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
