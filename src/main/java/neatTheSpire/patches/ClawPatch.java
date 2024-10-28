package neatTheSpire.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Claw;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import neatTheSpire.actions.ClawRipAndTearAction;
import neatTheSpire.neatTheSpireMod;

import static neatTheSpire.neatTheSpireMod.enableNtsClaw;

@SpirePatch(clz = Claw.class, method = "use")

public class ClawPatch {

    public static void Postfix(Claw __instance, AbstractPlayer p, AbstractMonster m) {
        if (enableNtsClaw)
            AbstractDungeon.actionManager.addToBottom(new ClawRipAndTearAction(__instance, __instance.magicNumber));
    }
}
