package neatTheSpire.patches;


import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Dark;
import neatTheSpire.neatTheSpireMod;
import neatTheSpire.orbs.NeatTheSpireLightOrb;

import java.util.ListIterator;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "channelOrb"
)
public class neatTheSpireLightOrbChannelPatch {
    @SpirePostfixPatch
    public static void postfix(AbstractPlayer __instance, AbstractOrb channeled) {
        if (AbstractDungeon.player.maxOrbs > 1) {
            for (AbstractOrb o : AbstractDungeon.player.orbs) {
                if (o instanceof NeatTheSpireLightOrb && !o.equals(channeled)) {
                    //For something where you have to cast, I prefer an instanceof check to ensure crashing is impossible
                    //You can make this an entire separate class if you want, I just didn't feel like it since it'd be annoying to paste in here
                    final NeatTheSpireLightOrb lightOrb = (NeatTheSpireLightOrb) o;
                    final String channeledID = channeled.ID;
                    AbstractDungeon.actionManager.addToTop(
                            new AbstractGameAction() {
                                @Override
                                public void update() {
                                    lightOrb.lightOrbTrigger(channeledID);
                                    isDone = true;
                                }
                            });
                }
            }
        }
    }
}

