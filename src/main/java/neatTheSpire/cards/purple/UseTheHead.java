package neatTheSpire.cards.purple;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import neatTheSpire.cards.AbstractDynamicCard;
import neatTheSpire.neatTheSpireMod;
import neatTheSpire.Intefaces.onGenerateCardMidcombatInterface;

import static neatTheSpire.neatTheSpireMod.makeCardPath;

public class UseTheHead extends AbstractDynamicCard implements onGenerateCardMidcombatInterface {

    public static final String ID = neatTheSpireMod.makeID(UseTheHead.class.getSimpleName());
    public static final String IMG = makeCardPath("UseTheHead.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = CardColor.PURPLE;

    private static final int COST = 0;

    private static final int DAMAGE = 4;
    private static final int UPGRADE_PLUS_DAMAGE = 2;

    public UseTheHead() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    @Override
    public void onCreateCardCard(AbstractCard card) {
        if (AbstractDungeon.player.discardPile.group.contains(this))
            AbstractDungeon.actionManager.addToBottom(new DiscardToHandAction(this));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeDamage(UPGRADE_PLUS_DAMAGE);
        }
    }
}
