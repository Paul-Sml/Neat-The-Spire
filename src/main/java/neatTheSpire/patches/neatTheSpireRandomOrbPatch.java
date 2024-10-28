package neatTheSpire.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import javassist.CtBehavior;
import neatTheSpire.orbs.NeatTheSpireLightOrb;
import neatTheSpire.orbs.NeatTheSpireMagneticOrb;

import java.util.ArrayList;

@SpirePatch(cls = "com.megacrit.cardcrawl.orbs.AbstractOrb", method = "getRandomOrb")
public class neatTheSpireRandomOrbPatch {

    @SpireInsertPatch(locator = Locator.class, localvars = {"orbs"})
    public static void addInfiniteSpireOrbs(boolean useCardRng, ArrayList<AbstractOrb> orbs) {
        orbs.add(new NeatTheSpireMagneticOrb());
        orbs.add(new NeatTheSpireLightOrb());
    }
    public static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher matcher = new Matcher.MethodCallMatcher(ArrayList.class, "add");
            return LineFinder.findInOrder(ctBehavior, matcher);
        }
    }

}