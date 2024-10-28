package neatTheSpire.cards.curse;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.SoulboundField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.random.Random;
import neatTheSpire.cards.AbstractDynamicCard;
import neatTheSpire.neatTheSpireMod;

import java.util.ArrayList;
import java.util.Iterator;

import static neatTheSpire.neatTheSpireMod.makeCardPath;

public class CurseOfTheAnvil extends AbstractDynamicCard {

    public static final String ID = neatTheSpireMod.makeID(CurseOfTheAnvil.class.getSimpleName());
    public static final String IMG = makeCardPath("CurseOfTheAnvil.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.CURSE;       //
    public static final CardColor COLOR = CardColor.CURSE;

    private static final int COST = 3;

    public CurseOfTheAnvil() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.timesUpgraded = 0;
        this.exhaust = true;
        FleetingField.fleeting.set(this, false);
        SoulboundField.soulbound.set(this, true);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {}

    public static <T> T getRandomItem(ArrayList<T> list, Random rng) {
        return list.isEmpty() ? null : list.get(rng.random(list.size() - 1));
    }

    public void triggerWhenDrawn() {
        AbstractCard cota = this;

        if (this.timesUpgraded == 0)
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    ArrayList<AbstractCard> cardsToExhaust = new ArrayList();

                    for (AbstractCard c : AbstractDungeon.player.hand.group) {
                        if (c.upgraded && c != cota) {
                            cardsToExhaust.add(c);
                        }
                    }

                    if (!cardsToExhaust.isEmpty())
                        this.addToTop(new ExhaustSpecificCardAction(getRandomItem(cardsToExhaust, AbstractDungeon.cardRandomRng), AbstractDungeon.player.hand));

                    this.isDone = true;
                }
            });
    }

    public void upgrade() {
        if (this.timesUpgraded == 1) {
            upgradeBaseCost(1);
            FleetingField.fleeting.set(this, true);
            this.exhaust = false;
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[2];
            initializeDescription();
        }
        if (this.timesUpgraded == 0) {
            upgradeBaseCost(2);
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
            initializeDescription();
        }
        /*if (this.timesUpgraded == 0) {
            upgradeBaseCost(2);
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
            initializeDescription();
        }*/

        ++this.timesUpgraded;
        this.upgraded = true;

        this.name = cardStrings.NAME + "+" + this.timesUpgraded;
        this.initializeTitle();
    }

    public boolean canUpgrade() {
        if (this.timesUpgraded < 2) {
            return true;
        } else {
            return false;
        }
    }

    public AbstractCard makeCopy() {
        return new CurseOfTheAnvil();
    }

}
