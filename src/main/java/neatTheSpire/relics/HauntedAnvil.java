//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package neatTheSpire.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.CallingBell;
import neatTheSpire.cards.curse.CurseOfTheAnvil;
import neatTheSpire.neatTheSpireMod;
import neatTheSpire.util.TextureLoader;

import static neatTheSpire.neatTheSpireMod.makeRelicOutlinePath;
import static neatTheSpire.neatTheSpireMod.makeRelicPath;

public class HauntedAnvil extends CustomRelic {
    public static final String ID = neatTheSpireMod.makeID("HauntedAnvil");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("HauntedAnvil.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("HauntedAnvil.png"));

    public HauntedAnvil() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.HEAVY);
    }

    public void onEquip() {
        ++AbstractDungeon.player.energy.energyMaster;

        CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        AbstractCard anvilCurse = new CurseOfTheAnvil();
        group.addToBottom(anvilCurse.makeCopy());
        AbstractDungeon.gridSelectScreen.openConfirmationGrid(group, this.DESCRIPTIONS[1]);
    }

    public void onUnequip() {
        --AbstractDungeon.player.energy.energyMaster;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
