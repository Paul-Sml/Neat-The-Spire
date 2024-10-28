package neatTheSpire.cards.red;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.watcher.WallopAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.purple.Wallop;
import com.megacrit.cardcrawl.cards.red.Inflame;
import com.megacrit.cardcrawl.cards.red.Sentinel;
import com.megacrit.cardcrawl.cards.red.TrueGrit;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import neatTheSpire.actions.HauntedAfterimageAction;
import neatTheSpire.actions.InnerFireAction;
import neatTheSpire.cards.AbstractDynamicCard;
import neatTheSpire.neatTheSpireMod;

import static neatTheSpire.neatTheSpireMod.makeCardPath;

public class InnerFire extends AbstractDynamicCard {

    public static final String ID = neatTheSpireMod.makeID(InnerFire.class.getSimpleName());
    public static final String IMG = makeCardPath("TrueGuts.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = CardColor.RED;

    private static final int COST = 1;

    private static final int DAMAGE = 5;

    public InnerFire() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        this.magicNumber = this.baseMagicNumber = 3;
        this.defaultSecondMagicNumber = this.defaultBaseSecondMagicNumber = baseDiscard + baseMagicNumber;

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        boolean attackInHand = false;
        for (AbstractCard c : p.hand.group) {
            if (c.type != (AbstractCard.CardType.ATTACK)) {
                attackInHand = true;
                break;
            }
        }
        if (attackInHand) {
            this.addToBot(new InnerFireAction(p, m, damage, defaultSecondMagicNumber, damageTypeForTurn));
        } else
            this.addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
    }

    @Override
    public void applyPowers()
    {
        defaultBaseSecondMagicNumber = baseDamage + baseMagicNumber;

        int tmp = baseDamage;
        baseDamage = defaultBaseSecondMagicNumber;

        super.applyPowers();

        defaultSecondMagicNumber = damage;
        baseDamage = tmp;

        super.applyPowers();

        isDefaultSecondMagicNumberModified = (defaultSecondMagicNumber != defaultBaseSecondMagicNumber);
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo)
    {
        defaultBaseSecondMagicNumber = baseDamage + baseMagicNumber;

        int tmp = baseDamage;
        baseDamage = defaultBaseSecondMagicNumber;

        super.calculateCardDamage(mo);

        defaultSecondMagicNumber = damage;
        baseDamage = tmp;

        super.calculateCardDamage(mo);

        isDefaultSecondMagicNumberModified = (defaultSecondMagicNumber != defaultBaseSecondMagicNumber);
    }

    /*public void triggerOnExhaust() {
        AbstractPlayer p = AbstractDungeon.player;
        this.addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
    }*/

    /*@Override
    public void calculateCardDamage(AbstractMonster mo) {
        if (innerFirePrev) {
            boolean attackInHand = false;
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c.type != (AbstractCard.CardType.ATTACK)) {
                    attackInHand = true;
                    break;
                }
            }
            if (attackInHand) {
                int realBaseDamage = this.baseDamage;
                this.baseDamage += this.magicNumber;
                super.calculateCardDamage(mo);
                this.baseDamage = realBaseDamage;
                this.isDamageModified = this.isDamageModified || (baseDamage != damage);
            } else
                super.calculateCardDamage(mo);
        } else
            super.calculateCardDamage(mo);
    }

    @Override
    public void applyPowers() {
        if (innerFirePrev) {
            boolean attackInHand = false;
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c.type != (AbstractCard.CardType.ATTACK)){
                    attackInHand = true;
                    break;
                }
            }
            if (attackInHand) {
                int realBaseDamage = this.baseDamage;
                this.baseDamage += this.magicNumber;
                super.applyPowers();
                this.baseDamage = realBaseDamage;
                this.isDamageModified = this.isDamageModified || (baseDamage != damage);
            } else
                super.applyPowers();
        } else
            super.applyPowers();
    }*/

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(4);
            //upgradeDamage(3);
        }
    }
}
