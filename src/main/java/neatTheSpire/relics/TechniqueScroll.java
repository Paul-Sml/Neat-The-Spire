//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package neatTheSpire.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.WellLaidPlans;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.Shuriken;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import neatTheSpire.neatTheSpireMod;
import neatTheSpire.util.TextureLoader;

import static neatTheSpire.neatTheSpireMod.makeRelicOutlinePath;
import static neatTheSpire.neatTheSpireMod.makeRelicPath;

public class TechniqueScroll extends CustomRelic {
    public static final String ID = neatTheSpireMod.makeID("TechniqueScroll");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("TechniqueScroll.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("TechniqueScroll.png"));
    private final int blockAmount = 5;

    public TechniqueScroll() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    public void atTurnStart() {
        this.counter = 0;
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.hasTag(AbstractCard.CardTags.STRIKE)) {
            ++this.counter;
            if (this.counter % 2 == 0) {
                this.counter = 0;
                this.flash();
                this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                this.addToBot(new GainBlockAction(AbstractDungeon.player, blockAmount));
            }
        }

    }

    public void onVictory() {
        this.counter = -1;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + blockAmount + DESCRIPTIONS[1];
    }
}
