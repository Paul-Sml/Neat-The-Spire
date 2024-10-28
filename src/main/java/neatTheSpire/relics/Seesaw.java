//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package neatTheSpire.relics;

import basemod.BaseMod;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.Clash;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.Boot;
import com.megacrit.cardcrawl.relics.OddlySmoothStone;
import com.megacrit.cardcrawl.relics.PenNib;
import com.megacrit.cardcrawl.relics.Vajra;
import neatTheSpire.neatTheSpireMod;
import neatTheSpire.util.TextureLoader;

import static neatTheSpire.neatTheSpireMod.makeRelicOutlinePath;
import static neatTheSpire.neatTheSpireMod.makeRelicPath;

public class Seesaw extends CustomRelic {
    public static final String ID = neatTheSpireMod.makeID("Seesaw");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Seesaw.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Seesaw.png"));

    public final int DAMAGE_AND_BLOCK_PLUS_BONUS = 4;

    public Seesaw() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.HEAVY);
    }

    public float atDamageModify(float damage, AbstractCard c) {
        for (AbstractCard ca : AbstractDungeon.player.hand.group) {
            if (ca.type != AbstractCard.CardType.ATTACK) {
                return damage;
            }
        }
        return damage + DAMAGE_AND_BLOCK_PLUS_BONUS;
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

    public void onDrawOrDiscard(AbstractCard drawnCard) {
        super.onCardDraw(drawnCard);

        pulsing();
    }

    private boolean shouldPulse() {
        boolean fullOfAttacks = true;

        for (AbstractCard ca : AbstractDungeon.player.hand.group) {
            if (ca.type != AbstractCard.CardType.ATTACK) {
                fullOfAttacks = false;
                break;
            }
        }

        boolean fullOfSkills = true;

        for (AbstractCard ca : AbstractDungeon.player.hand.group) {
            if (ca.type != AbstractCard.CardType.SKILL) {
                fullOfSkills = false;
                break;
            }
        }

        return fullOfAttacks && fullOfSkills;
    }

    private void pulsing() {
        if (shouldPulse()) {
            this.flash();
            this.beginPulse();
            this.pulse = true;
        } else
            this.stopPulse();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + DAMAGE_AND_BLOCK_PLUS_BONUS + DESCRIPTIONS[1] + DAMAGE_AND_BLOCK_PLUS_BONUS + DESCRIPTIONS[2];
    }
}
