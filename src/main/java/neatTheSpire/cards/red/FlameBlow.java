package neatTheSpire.cards.red;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.SearingBlowEffect;
import neatTheSpire.cards.AbstractDynamicCard;
import neatTheSpire.neatTheSpireMod;

import static neatTheSpire.neatTheSpireMod.makeCardPath;

public class FlameBlow extends AbstractDynamicCard {

    public static final String ID = neatTheSpireMod.makeID(FlameBlow.class.getSimpleName());
    public static final String IMG = makeCardPath("FlameBlow.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = CardColor.RED;

    private static final int COST = 1;

//    private static final int BLOCK = 10;
//    private static final int UPGRADE_PLUS_BLOCK = 4;

    private static final int DAMAGE = 6;
    private static final int UPGRADE_PLUS_DAMAGE = 2;

    public FlameBlow() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.defaultBaseSecondMagicNumber = 1;
        this.defaultSecondMagicNumber = this.defaultBaseSecondMagicNumber;
        this.timesUpgraded = 0;
        this.cardsToPreview = new Burn();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            this.addToBot(new VFXAction(new SearingBlowEffect(m.hb.cX, m.hb.cY, this.timesUpgraded), 0.2F));
        }

        for (int i = 0; i < this.magicNumber ; i ++)
            this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));

        this.addToBot(new DrawCardAction(p, this.defaultSecondMagicNumber));

        if (this.timesUpgraded == 0)
            this.addToBot(new MakeTempCardInHandAction(new Burn(), 1));
        if (this.timesUpgraded == 1)
            this.addToBot(new MakeTempCardInDrawPileAction(new Wound(), 1, true ,true));
        if (this.timesUpgraded == 2)
            this.addToBot(new MakeTempCardInDiscardAction(new Dazed(), 1));
    }

    public void upgrade() {
        if (this.timesUpgraded == 2) {
            this.upgradeMagicNumber(1);
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[2];
            this.cardsToPreview = null;
            initializeDescription();
        }
        if (this.timesUpgraded == 1) {
            this.upgradeDamage(UPGRADE_PLUS_DAMAGE);
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
            this.cardsToPreview = new Dazed();
            initializeDescription();
        }
        if (this.timesUpgraded == 0) {
            this.upgradeDefaultSecondMagicNumber(1);
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
            this.cardsToPreview = new Wound();
            initializeDescription();
        }

        ++this.timesUpgraded;
        this.upgraded = true;

        this.name = cardStrings.NAME + "+" + this.timesUpgraded;
        this.initializeTitle();
    }

    public boolean canUpgrade() {
        if (this.timesUpgraded < 3) {
            return true;
        } else {
            return false;
        }
    }

    public AbstractCard makeCopy() {
        return new FlameBlow();
    }

}
