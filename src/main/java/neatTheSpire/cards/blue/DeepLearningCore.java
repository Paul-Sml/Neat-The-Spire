package neatTheSpire.cards.blue;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.LockOn;
import com.megacrit.cardcrawl.cards.curses.CurseOfTheBell;
import com.megacrit.cardcrawl.cards.red.HeavyBlade;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import neatTheSpire.actions.DeepLearningCoreAction;
import neatTheSpire.cards.AbstractDynamicCard;
import neatTheSpire.neatTheSpireMod;

import static neatTheSpire.neatTheSpireMod.makeCardPath;

public class DeepLearningCore extends AbstractDynamicCard {

    public static final String ID = neatTheSpireMod.makeID(DeepLearningCore.class.getSimpleName());
    public static final String IMG = makeCardPath("DeepLearningCore.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;  //
    public static final CardColor COLOR = CardColor.BLUE;

    private static final int COST = -1;
    //private static final int UPGRADED_COST = 1;

    //private static final int DAMAGE = 1;

    public DeepLearningCore() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        this.misc = 1;
        this.damage = this.baseDamage = this.misc;
        this.baseMagicNumber = 0;
        this.magicNumber = this.baseMagicNumber;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DeepLearningCoreAction(this.uuid, this.freeToPlayOnce, this.misc, (this.energyOnUse + this.magicNumber)));
        this.addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));

//        if (this.upgraded)
//            this.addToBot(new DrawCardAction(p, 1));
    }

    public void applyPowers() {
        this.damage = this.baseDamage = this.misc;
        this.initializeDescription();
        super.applyPowers();
    }

    public void calculateCardDamage(AbstractMonster m) {
        this.damage = this.baseDamage = this.misc;
        this.initializeDescription();
        super.calculateCardDamage(m);
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.upgradeMagicNumber(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}