//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package neatTheSpire.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import neatTheSpire.neatTheSpireMod;
import neatTheSpire.util.TextureLoader;

import static neatTheSpire.neatTheSpireMod.makeRelicOutlinePath;
import static neatTheSpire.neatTheSpireMod.makeRelicPath;

public class PaintRoller extends CustomRelic {
    public static final String ID = neatTheSpireMod.makeID("PaintRoller");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("PaintRoller.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("PaintRoller.png"));

    public PaintRoller() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.HEAVY);
    }

    public float atDamageModify(float damage, AbstractCard c) {
        if ( AbstractDungeon.player.hand.size() >= 9) {
            return damage * 2.0F;
        } else
            return damage;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        super.onUseCard(card, action);

        this.addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                pulsing();
                isDone = true;
            }
        });

    }

    @Override
    public void onDrawOrDiscard() {
        super.onDrawOrDiscard();
        pulsing();

    }

    private void pulsing() {
        if (AbstractDungeon.player.hand.size() >= 9) {
            this.flash();
            this.beginPulse();
            this.pulse = true;
        } else
            this.stopPulse();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
