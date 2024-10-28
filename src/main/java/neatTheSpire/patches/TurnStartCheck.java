package neatTheSpire.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import neatTheSpire.neatTheSpireMod;
import javassist.CtBehavior;

import static neatTheSpire.neatTheSpireMod.ResonanceCheck;

@SpirePatch(
        clz = GameActionManager.class,
        method = "getNextAction"
)
public class TurnStartCheck {
    @SpireInsertPatch(
            locator = Locator.class
    )
    public static void Insert(GameActionManager __instance) {
        neatTheSpireMod.gainedStrDexThisTurn = false;
        neatTheSpireMod.hpAtTurnStart = AbstractDungeon.player.currentHealth;
        ResonanceCheck = false;
        neatTheSpireMod.checkEnergy = 0;
        neatTheSpireMod.usedEnergy = 0;
    }
    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "applyStartOfTurnRelics");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}