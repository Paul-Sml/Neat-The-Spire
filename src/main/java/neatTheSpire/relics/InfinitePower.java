//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package neatTheSpire.relics;

import basemod.BaseMod;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.curses.Normality;
import com.megacrit.cardcrawl.cards.purple.LikeWater;
import com.megacrit.cardcrawl.cards.red.Clash;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.EntanglePower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.watcher.MantraPower;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.stances.CalmStance;
import neatTheSpire.cards.curse.CurseOfTheAnvil;
import neatTheSpire.neatTheSpireMod;
import neatTheSpire.util.TextureLoader;

import java.util.Iterator;

import static neatTheSpire.neatTheSpireMod.makeRelicOutlinePath;
import static neatTheSpire.neatTheSpireMod.makeRelicPath;

public class InfinitePower extends CustomRelic {
    public static final String ID = neatTheSpireMod.makeID("InfinitePower");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("InfinitePower.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("InfinitePower.png"));

    public InfinitePower() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    public void onEquip() {
        ++AbstractDungeon.player.energy.energyMaster;
    }

    public void onUnequip() {
        --AbstractDungeon.player.energy.energyMaster;
    }

    public boolean canPlay(AbstractCard c) {
        boolean checkP = false;
        Iterator var4 = AbstractDungeon.player.hand.group.iterator();

        while(var4.hasNext()) {
            AbstractCard ca = (AbstractCard)var4.next();
            if (ca.type == AbstractCard.CardType.POWER) {
                checkP = true;
            }
        }

        if (c.type != AbstractCard.CardType.POWER && checkP) {
            c.cantUseMessage = this.DESCRIPTIONS[1];
            return false;
        }

        return true;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
