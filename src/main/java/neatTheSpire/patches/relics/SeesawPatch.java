package neatTheSpire.patches.relics;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import neatTheSpire.relics.Seesaw;

@SpirePatch(
        clz = AbstractCard.class,
        method = "applyPowersToBlock"
)

public class SeesawPatch {
    public static void Postfix(final AbstractCard __instance) {

        AbstractRelic r = AbstractDungeon.player.getRelic(Seesaw.ID);
        if (__instance.block > 0 && r != null) {

            boolean modifyBlock = true;
            for (AbstractCard ca : AbstractDungeon.player.hand.group) {
                if (ca.type != AbstractCard.CardType.SKILL) {
                    modifyBlock = false;
                }
            }

            if (modifyBlock) {
                __instance.block += ((Seesaw)r).DAMAGE_AND_BLOCK_PLUS_BONUS;
                __instance.isBlockModified = true;
            }
        }
    }
}