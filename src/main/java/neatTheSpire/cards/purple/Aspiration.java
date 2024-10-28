package neatTheSpire.cards.purple;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import neatTheSpire.actions.AspirationAction;
import neatTheSpire.cards.AbstractDynamicCard;
import neatTheSpire.neatTheSpireMod;

import static neatTheSpire.neatTheSpireMod.makeCardPath;

public class  Aspiration extends AbstractDynamicCard {

    public static final String ID = neatTheSpireMod.makeID(Aspiration.class.getSimpleName());
    public static final String IMG = makeCardPath("Aspiration.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = CardColor.PURPLE;

    private static final int COST = 1;

//    private static final int DAMAGE = 0;

    public Aspiration() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
//        this.baseDamage = DAMAGE;
        this.selfRetain = false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new AspirationAction(this, m, AbstractGameAction.AttackEffect.LIGHTNING));
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);

        for (AbstractCard c : p.hand.group) {
            if (c != this && c.type == CardType.ATTACK) {
                return canUse;
            }
        }
        this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        return false;
    }

    /*@Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        if (this.upgraded) {
            this.flash();
            this.addToBot(new DrawCardAction(1));
        }
    }*/

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
            this.selfRetain = true;
        }
    }
}
