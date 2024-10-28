//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package neatTheSpire.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rooms.ShopRoom;
import neatTheSpire.neatTheSpireMod;
import neatTheSpire.util.TextureLoader;

import static neatTheSpire.neatTheSpireMod.makeRelicOutlinePath;
import static neatTheSpire.neatTheSpireMod.makeRelicPath;

public class SmoothedVajra extends CustomRelic {
    public static final String ID = neatTheSpireMod.makeID("SmoothedVajra");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("SmoothedVajra.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("SmoothedVajra.png"));
    private static boolean doneCheck;

    public SmoothedVajra() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.SOLID);
        doneCheck = true;
    }

    @Override
    public void onEquip() {
        doneCheck = false;
    }

    public static void getRelics() {
        if (!doneCheck) {
            if (!AbstractDungeon.player.hasRelic(Vajra.ID))
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(500, 500, RelicLibrary.getRelic(Vajra.ID).makeCopy());
            if (!AbstractDungeon.player.hasRelic(OddlySmoothStone.ID))
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(500, 500, RelicLibrary.getRelic(OddlySmoothStone.ID).makeCopy());
            doneCheck = true;
        }
    }

    @Override
    public boolean canSpawn() {
        return (!AbstractDungeon.player.hasRelic(OddlySmoothStone.ID) && !AbstractDungeon.player.hasRelic(Vajra.ID) && !(AbstractDungeon.getCurrRoom() instanceof ShopRoom));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
