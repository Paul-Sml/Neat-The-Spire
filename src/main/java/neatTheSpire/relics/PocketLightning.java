//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package neatTheSpire.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.MantraPower;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.DivinityStance;
import neatTheSpire.neatTheSpireMod;
import neatTheSpire.util.TextureLoader;

import static neatTheSpire.neatTheSpireMod.makeRelicOutlinePath;
import static neatTheSpire.neatTheSpireMod.makeRelicPath;

public class PocketLightning extends CustomRelic {
    public static final String ID = neatTheSpireMod.makeID("PocketLightning");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("PocketLightning.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("PocketLightning.png"));
//    private final int drawAmount = 3;

    public PocketLightning() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.MAGICAL);
    }

    /*@Override
    public void onChangeStance(AbstractStance prevStance, AbstractStance newStance) {
        super.onChangeStance(prevStance, newStance);
        if (prevStance.ID.equals(DivinityStance.STANCE_ID)) {
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.addToBot(new DrawCardAction(drawAmount));
        }
    }*/

    @Override
    public void onVictory() {
        AbstractPower pow = AbstractDungeon.player.getPower(MantraPower.POWER_ID);
        if (pow != null) {
            this.counter = pow.amount;
        }
        super.onVictory();
    }

    @Override
    public void atBattleStart() {
        if (this.counter > 0)
        this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new MantraPower(AbstractDungeon.player, this.counter), this.counter));
        this.counter = -1;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
