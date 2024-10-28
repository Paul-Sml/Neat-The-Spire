package neatTheSpire.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.green.AllOutAttack;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.Colosseum;
import com.megacrit.cardcrawl.events.exordium.ScrapOoze;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.chests.BossChest;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.screens.select.BossRelicSelectScreen;
import javassist.CtBehavior;

import java.util.ArrayList;

import static neatTheSpire.neatTheSpireMod.FourthBossRelicStorage;
import static neatTheSpire.neatTheSpireMod.enableFourthBossRelic;

public class FourthBossRelic
    
{
    @SpirePatch(
            clz=BossRelicSelectScreen.class,
            method="open"
    )
    public static class Open
    {
        @SpireInsertPatch(
                locator=Locator.class
        )
        public static void Insert(BossRelicSelectScreen __instance, ArrayList<AbstractRelic> chosenRelics)
        {
            if (enableFourthBossRelic && FourthBossRelicStorage != null) {
                float SLOT_1_X = __instance.relics.get(0).currentX;//needed if 5th relic
                float SLOT_2_X = __instance.relics.get(1).currentX;
                float SLOT_3_X = __instance.relics.get(2).currentX;
                float SLOT_1_Y = __instance.relics.get(0).currentY;
                float SLOT_2_Y = __instance.relics.get(1).currentY;//needed if 5th relic

                // Spawn 4th relic
//                AbstractRelic relic = AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.BOSS);
//                System.out.println("Created 4th boss relic: " + relic.relicId);
                FourthBossRelicStorage.spawn(SLOT_3_X, SLOT_1_Y);
                FourthBossRelicStorage.hb.move(FourthBossRelicStorage.currentX, FourthBossRelicStorage.currentY);
                __instance.relics.add(FourthBossRelicStorage);

                // Move 1st relic
                AbstractRelic first = __instance.relics.get(0);
                first.currentX = SLOT_2_X;
                first.hb.move(first.currentX, first.currentY);

                //TEST 5th relic
                /*AbstractRelic r = AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.BOSS);
                r.spawn(SLOT_3_X, SLOT_1_Y);
                __instance.relics.add(r);*/

                if (__instance.relics.size() > 4) {
                    float newX = SLOT_1_X;
                    FourthBossRelicStorage.currentX = newX;
                    float newY = (SLOT_1_Y + SLOT_2_Y) / 2;
                    FourthBossRelicStorage.currentY = newY;
                    FourthBossRelicStorage.hb.move(FourthBossRelicStorage.currentX, FourthBossRelicStorage.currentY);
                }
            }
        }

        private static class Locator extends SpireInsertLocator
        {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
            {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(BossRelicSelectScreen.class, "relics");
                // Only the last match
                int[] found = LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
                return new int[]{found[found.length-1]};
            }
        }
    }
}
