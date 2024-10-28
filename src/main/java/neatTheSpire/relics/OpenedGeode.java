//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package neatTheSpire.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import neatTheSpire.cards.curse.CurseOfTheAnvil;
import neatTheSpire.neatTheSpireMod;
import neatTheSpire.util.TextureLoader;

import static neatTheSpire.neatTheSpireMod.makeRelicOutlinePath;
import static neatTheSpire.neatTheSpireMod.makeRelicPath;

public class OpenedGeode extends CustomRelic {
    public static final String ID = neatTheSpireMod.makeID("OpenedGeode");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("OpenedGeode.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Geode.png"));

    private boolean wasMaxEGained = false;

    public OpenedGeode() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    public void onEquip() {
        if (!wasMaxEGained) {
            ++AbstractDungeon.player.energy.energyMaster;
            wasMaxEGained = true;
        }
    }

    public void onUnequip() {
        --AbstractDungeon.player.energy.energyMaster;
    }

    @Override
    public void obtain() {
        AbstractPlayer p = AbstractDungeon.player;
        if (p.hasRelic(Geode.ID)) {
            for (int i=0; i<p.relics.size(); ++i) {
                if (p.relics.get(i).relicId.equals(Geode.ID)) {
                    instantObtain(p, i, true);
                    break;
                }
            }
        } else {
            super.obtain();
        }
    }

    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(Geode.ID);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
