//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package neatTheSpire.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.CreativeAIPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import neatTheSpire.neatTheSpireMod;
import neatTheSpire.orbs.NeatTheSpireLightOrb;
import neatTheSpire.util.TextureLoader;

import static neatTheSpire.neatTheSpireMod.makeRelicOutlinePath;
import static neatTheSpire.neatTheSpireMod.makeRelicPath;

public class SolarPanel extends CustomRelic {
    public static final String ID = neatTheSpireMod.makeID("SolarPanel");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("SolarPanel.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("SolarPanel.png"));

    public SolarPanel() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    /*@Override
    public void onEvokeOrb(AbstractOrb ammo) {
        super.onEvokeOrb(ammo);
        if (ammo.ID.equals(NeatTheSpireLightOrb.ORB_ID)){
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractCard card = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.POWER).makeCopy();
            this.addToBot(new MakeTempCardInHandAction(card));
        }
    }*/

    public void atTurnStart() {
        if (AbstractDungeon.player.orbs.size() > 0) {
            boolean hasLightOrb = false;
            for (AbstractOrb o : AbstractDungeon.player.orbs) {
                if (o != null && o.ID != null && o.ID.equals(NeatTheSpireLightOrb.ORB_ID)) {
                    hasLightOrb = true;
                    break;
                }
            }
            if (hasLightOrb) {
                this.flash();
                this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                this.addToBot(new GainEnergyAction(1));
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
