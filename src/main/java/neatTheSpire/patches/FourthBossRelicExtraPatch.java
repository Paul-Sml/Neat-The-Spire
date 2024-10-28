package neatTheSpire.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.green.AllOutAttack;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.Colosseum;
import com.megacrit.cardcrawl.events.exordium.ScrapOoze;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.chests.BossChest;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.screens.select.BossRelicSelectScreen;
import javassist.CtBehavior;
import neatTheSpire.neatTheSpireMod;

import java.util.ArrayList;

import static neatTheSpire.neatTheSpireMod.FourthBossRelicStorage;
import static neatTheSpire.neatTheSpireMod.enableFourthBossRelic;

    @SpirePatch(
            clz = BossChest.class,
            method = SpirePatch.CONSTRUCTOR
    )

    public class FourthBossRelicExtraPatch {
        @SpirePostfixPatch
        public static void postfix(BossChest __instance) {
            if (enableFourthBossRelic && __instance.relics.size() < 4) {
                FourthBossRelicStorage = (AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.BOSS));
                //relics.add(FourthBossRelicStorage);
            } else
                FourthBossRelicStorage = null;
        }
    }

