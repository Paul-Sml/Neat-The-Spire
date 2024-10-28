//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package neatTheSpire.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import neatTheSpire.neatTheSpireMod;
import neatTheSpire.util.TextureLoader;

import static neatTheSpire.neatTheSpireMod.makeRelicOutlinePath;
import static neatTheSpire.neatTheSpireMod.makeRelicPath;

public class Geode extends CustomRelic {
    public static final String ID = neatTheSpireMod.makeID("Geode");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Geode.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Geode.png"));

    public Geode() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.HEAVY);
    }

    public void atBattleStart() {
        this.flash();
        this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        DamageInfo i = new DamageInfo(null, 5, DamageInfo.DamageType.THORNS);
        this.addToBot(new DamageRandomEnemyAction(i, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    public boolean canSpawn() {
        return Settings.isEndless || AbstractDungeon.actNum <= 2;
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
