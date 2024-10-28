package neatTheSpire.orbs;

import basemod.abstracts.CustomOrb;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.vfx.combat.FrostOrbActivateEffect;
import com.megacrit.cardcrawl.vfx.combat.FrostOrbPassiveEffect;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;
import neatTheSpire.neatTheSpireMod;

import static neatTheSpire.neatTheSpireMod.makeOrbPath;

public class NeatTheSpireMagneticOrb extends CustomOrb {

    // Standard ID/Description
    public static final String ORB_ID = neatTheSpireMod.makeID("neatTheSpireMagneticOrb");
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String[] DESCRIPTIONS = orbString.DESCRIPTION;

    private static final int PASSIVE_AMOUNT = 1;
    private static final int EVOKE_AMOUNT = 2;

    // Animation Rendering Numbers - You can leave these at default, or play around with them and see what they change.
    private float vfxTimer = 1.0f;
    private float vfxIntervalMin = 0.1f;
    private float vfxIntervalMax = 0.4f;
    private static final float ORB_WAVY_DIST = 0.04f;
    private static final float PI_4 = 12.566371f;

    private static final Texture img2 = ImageMaster.loadImage(makeOrbPath("MagneticOrbBlue.png"));
    private float rotationTimer = 0;

    public NeatTheSpireMagneticOrb() {
        super(ORB_ID, orbString.NAME, PASSIVE_AMOUNT, EVOKE_AMOUNT, DESCRIPTIONS[1], DESCRIPTIONS[3], makeOrbPath("MagneticOrbRed.png"));

        updateDescription();

        angle = MathUtils.random(360.0f); // More Animation-related Numbers
        channelAnimTimer = 0.5f;
    }

    @Override
    public void updateDescription() { // Set the on-hover description of the orb
        applyFocus(); // Apply Focus (Look at the next method)
        if (evokeAmount == 1) {
            description = DESCRIPTIONS[0] + passiveAmount + DESCRIPTIONS[1] + DESCRIPTIONS[2] + evokeAmount + DESCRIPTIONS[3] + DESCRIPTIONS[5];
        } else if (evokeAmount > 1) {
            description = DESCRIPTIONS[0] + passiveAmount + DESCRIPTIONS[1] + DESCRIPTIONS[2] + evokeAmount + DESCRIPTIONS[4] + DESCRIPTIONS[5];
        }
    }

    @Override
    public void applyFocus() {
        AbstractPower power = AbstractDungeon.player.getPower(FocusPower.POWER_ID);
        if (power != null) {
            this.evokeAmount = Math.max(0, this.baseEvokeAmount + power.amount);
        } else {
            this.evokeAmount = this.baseEvokeAmount;
        }
    }

    @Override
    public void onEvoke() { // 1.On Orb Evoke

        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, this.evokeAmount));

        AbstractDungeon.actionManager.addToBottom(new SFXAction("TINGSHA")); // 3.And play a Jingle Sound.
        // For a list of sound effects you can use, look under com.megacrit.cardcrawl.audio.SoundMaster - you can see the list of keys you can use there. As far as previewing what they sound like, open desktop-1.0.jar with something like 7-Zip and go to audio. Reference the file names provided. (Thanks fiiiiilth)

    }

    @Override
    public void onStartOfTurn() {// 1.At the start of your turn.
        AbstractDungeon.actionManager.addToBottom(// 2.This orb will have a flare effect
                new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.FROST), 0.1f));

        AbstractDungeon.actionManager.addToBottom(// 3. And draw you cards.
                new DrawCardAction(AbstractDungeon.player, 1));
    }

    public void updateAnimation() {
        super.updateAnimation();
        this.angle += Gdx.graphics.getDeltaTime() * 180.0F;
        this.vfxTimer -= Gdx.graphics.getDeltaTime();
        if (this.vfxTimer < 0.0F) {
            AbstractDungeon.effectList.add(new FrostOrbPassiveEffect(this.cX, this.cY));
            if (MathUtils.randomBoolean()) {
                AbstractDungeon.effectList.add(new FrostOrbPassiveEffect(this.cX, this.cY));
            }

            this.vfxTimer = MathUtils.random(this.vfxIntervalMin, this.vfxIntervalMax);
        }

    }

    public void render(SpriteBatch sb) {
        /*if (rotationTimer <= 0F) {
            rotationTimer = 45.0F;
        } else {
            rotationTimer -= Gdx.graphics.getDeltaTime();
        }*/
        sb.setColor(this.c);
        sb.draw(img, this.cX - 48.0F, this.cY - 48.0F, 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, -this.angle*0.4F, 0, 0, 96, 96, false, false);
        sb.draw(img2, this.cX - 48.0F, this.cY - 48.0F, 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, -this.angle*0.4F, 0, 0, 96, 96, false, false);
        this.renderText(sb);
        this.hb.render(sb);
    }

    protected void renderText(SpriteBatch sb) {
        FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.evokeAmount), this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET - 4.0F * Settings.scale, new Color(0.2F, 1.0F, 1.0F, this.c.a), this.fontScale);
    }

    @Override
    public void triggerEvokeAnimation() {
        CardCrawlGame.sound.play("ORB_FROST_EVOKE", 0.1F);
        AbstractDungeon.effectsQueue.add(new FrostOrbActivateEffect(this.cX, this.cY));
    }
    @Override
    public void playChannelSFX() { // When you channel this orb, the ATTACK_FIRE effect plays ("Fwoom").
        CardCrawlGame.sound.play("ATTACK_FIRE", 0.1f);
    }

    @Override
    public AbstractOrb makeCopy() {
        return new NeatTheSpireMagneticOrb();
    }
}