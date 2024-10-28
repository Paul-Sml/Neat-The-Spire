package neatTheSpire.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import neatTheSpire.cards.purple.Elevation;

@SpirePatch(
        clz = ScryAction.class,
        method = "update"
)
public class OnScryWhileInHand {
    public static void Prefix(ScryAction __instance, float ___duration, float ___startingDuration) {
        if (___duration == ___startingDuration) {
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c.cardID.equals(Elevation.ID)) {
                    c.baseMagicNumber++;
                    c.magicNumber++;
                }
            }
        }
    }
}