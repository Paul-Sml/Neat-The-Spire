package neatTheSpire.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.blue.Claw;
import com.megacrit.cardcrawl.cards.blue.RipAndTear;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static neatTheSpire.neatTheSpireMod.enableNtsClaw;

@SpirePatch(clz = RipAndTear.class, method = SpirePatch.CONSTRUCTOR)

public class RipAndTearPatch {
    public static void Postfix(RipAndTear __instance) {
        if (enableNtsClaw) {
            __instance.rawDescription = __instance.rawDescription + " NL Damage increased by Claw.";
            __instance.initializeDescription();
        }
    }
}
