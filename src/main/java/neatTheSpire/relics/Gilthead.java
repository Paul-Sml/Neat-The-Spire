//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package neatTheSpire.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import neatTheSpire.neatTheSpireMod;
import neatTheSpire.util.TextureLoader;

import static neatTheSpire.neatTheSpireMod.*;

public class Gilthead extends CustomRelic {
    public static final String ID = neatTheSpireMod.makeID("Gilthead");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Gilthead.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Gilthead.png"));

    public static int INCREASE_MAX_HP_AMOUNT = 4;

    public Gilthead() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.HEAVY);
    }

    @Override
    public void onEquip() {
        AbstractPlayer p = AbstractDungeon.player;
        if (Settings.hasRubyKey)
            onTrigger();
        if (Settings.hasEmeraldKey)
            onTrigger();
        if (Settings.hasSapphireKey)
            onTrigger();
    }

    @Override
    public void onTrigger() {
        AbstractDungeon.player.increaseMaxHp(INCREASE_MAX_HP_AMOUNT, true);
    }

    @Override
    public boolean canSpawn() {
        return Settings.isFinalActAvailable;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + INCREASE_MAX_HP_AMOUNT + DESCRIPTIONS[1];
    }
}
