package neatTheSpire.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import neatTheSpire.neatTheSpireMod;

@SpirePatch(
        clz = ApplyPowerAction.class,
        method = "update"
)
public class PowerActionPatch {
    public static void Postfix(ApplyPowerAction __instance) {
        AbstractPower p = (AbstractPower) ReflectionHacks.getPrivate(__instance, ApplyPowerAction.class, "powerToApply");
        if ((p instanceof StrengthPower || p instanceof DexterityPower) && __instance.target == AbstractDungeon.player) {
            neatTheSpireMod.gainedStrDexThisTurn = true;
        }
    }
}