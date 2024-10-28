package neatTheSpire.patches.relics;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.ObtainKeyEffect;
import javassist.CtBehavior;
import neatTheSpire.relics.Gilthead;

public class GiltheadPatch {
    @SpirePatch(
            clz = ObtainKeyEffect.class,
            method = "update"
    )
    public static class update {
        @SpireInsertPatch(
                locator = GiltheadPatch.update.Locator.class
        )
        public static void Insert() {
            if (AbstractDungeon.player.hasRelic(Gilthead.ID))
                AbstractDungeon.player.getRelic(Gilthead.ID).onTrigger();
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(SoundMaster.class, "playA");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
}
