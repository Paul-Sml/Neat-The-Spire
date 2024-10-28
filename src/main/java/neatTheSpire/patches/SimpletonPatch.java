package neatTheSpire.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import neatTheSpire.cards.purple.UseTheHead;
import neatTheSpire.powers.SimpletonPower;
import neatTheSpire.powers.SimpletonPowerUpgraded;

public class SimpletonPatch {

    @SpirePatch(clz = MakeTempCardInDiscardAction.class, method = "update")
    @SpirePatch(clz = MakeTempCardInHandAction.class, method = "update")
    public static class HandOrDiscard
    {
        public static void Prefix(AbstractGameAction __instance, float ___duration, float ___startDuration, @ByRef AbstractCard[] ___c)
        {
            if (___duration == ___startDuration)
            {
//                for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
//                    if (c.cardID.equals(UseTheHead.ID))
//                        AbstractDungeon.actionManager.addToBottom(new DiscardToHandAction(c));
//                }

                AbstractCreature p = AbstractDungeon.player;
                AbstractPower unupgradedPower = p.getPower(SimpletonPower.POWER_ID);
                AbstractPower upgradedPower = p.getPower(SimpletonPowerUpgraded.POWER_ID);
                if (unupgradedPower != null || upgradedPower != null) {
                if (___c[0].type == AbstractCard.CardType.STATUS) {
                    //if it's a status, replace it
                    //also might need another test here to do a dazed instead of a slimed for your upgrade
                    if (upgradedPower != null)
                        ___c[0] = new Dazed();
                    else if (unupgradedPower != null)
                        ___c[0] = new Slimed();
                }
            }
            }
        }
    }

    @SpirePatch(clz = MakeTempCardInDrawPileAction.class, method = "update")
    public static class Draw
    {
        public static void Prefix(AbstractGameAction __instance, float ___duration, float ___startDuration, @ByRef AbstractCard[] ___cardToMake)
        {
            if (___duration == ___startDuration)
            {
//                for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
//                    if (c.cardID.equals(UseTheHead.ID))
//                        AbstractDungeon.actionManager.addToBottom(new DiscardToHandAction(c));
//                }

                AbstractCreature p = AbstractDungeon.player;
                AbstractPower unupgradedPower = p.getPower(SimpletonPower.POWER_ID);
                AbstractPower upgradedPower = p.getPower(SimpletonPowerUpgraded.POWER_ID);
                if (unupgradedPower != null || upgradedPower != null) {
                if (___cardToMake[0].type == AbstractCard.CardType.STATUS) {
                    //if it's a status, replace it
                    //also might need another test here to do a dazed instead of a slimed for your upgrade
                    if (upgradedPower != null)
                        ___cardToMake[0] = new Dazed();
                    else if (unupgradedPower != null)
                        ___cardToMake[0] = new Slimed();
                }
            }
            }
        }
    }
}
