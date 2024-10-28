//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package neatTheSpire.relics;

import basemod.BaseMod;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import com.megacrit.cardcrawl.powers.watcher.MantraPower;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.stances.CalmStance;
import neatTheSpire.neatTheSpireMod;
import neatTheSpire.util.TextureLoader;

import static neatTheSpire.neatTheSpireMod.makeRelicOutlinePath;
import static neatTheSpire.neatTheSpireMod.makeRelicPath;

public class BloodyClaws extends CustomRelic {
    public static final String ID = neatTheSpireMod.makeID("BloodyClaws");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("BloodyClaws.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("BloodyClaws.png"));
    //private int healAmount = 0;

    public BloodyClaws() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.CLINK);
    }

    public void onVictory() {
        this.flash();
        AbstractDungeon.player.heal(this.counter);
        this.counter = -1;
    }

    /*public void wasHPLost(int damageAmount) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && damageAmount > 0) {
            healAmount += damageAmount;
        }
    }*/

    public int onAttacked(DamageInfo info, int damageAmount) {
        if (damageAmount > 0 && info.owner == AbstractDungeon.player)
            this.counter += damageAmount*2;
        return damageAmount;
    }

    public void atTurnStart() {
        this.counter = 0;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
