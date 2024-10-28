//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package neatTheSpire.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Calipers;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import neatTheSpire.neatTheSpireMod;
import neatTheSpire.util.TextureLoader;

import static neatTheSpire.neatTheSpireMod.makeRelicOutlinePath;
import static neatTheSpire.neatTheSpireMod.makeRelicPath;

public class PrisonerBall extends CustomRelic {
    public static final String ID = neatTheSpireMod.makeID("PrisonerBall");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("PrisonerBall.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("PrisonerBall.png"));
    private final int blockAmount = 3;

    public PrisonerBall() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public void onCardDraw(AbstractCard drawnCard) {
        super.onCardDraw(drawnCard);
        if (drawnCard.cost == -2) {
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.addToBot(new GainBlockAction(AbstractDungeon.player, blockAmount));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + blockAmount + DESCRIPTIONS[1];
    }
}
