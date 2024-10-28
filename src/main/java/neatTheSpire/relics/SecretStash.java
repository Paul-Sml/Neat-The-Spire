//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package neatTheSpire.relics;

import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import com.google.gson.reflect.TypeToken;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.Pantograph;
import neatTheSpire.neatTheSpireMod;
import neatTheSpire.util.TextureLoader;

import java.lang.reflect.Type;
import java.util.Iterator;

import static neatTheSpire.neatTheSpireMod.makeRelicOutlinePath;
import static neatTheSpire.neatTheSpireMod.makeRelicPath;

public class SecretStash extends CustomRelic implements CustomSavable<Integer> {
    public static final String ID = neatTheSpireMod.makeID("SecretStash");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("SecretStash.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("SecretStash.png"));

    private int GOLD_GAIN = 0;
    private boolean bossChestAlreadyOpened = false;


    public SecretStash() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.CLINK);
        this.counter = -1;
    }

    @Override
    public void updateDescription(AbstractPlayer.PlayerClass c) {
        this.description = this.getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }

    @Override
    public void onEquip() {
        this.counter = 3;
        CardCrawlGame.sound.play("GOLD_GAIN");
        AbstractPlayer p = AbstractDungeon.player;
        GOLD_GAIN = p.gold;
        p.loseGold(GOLD_GAIN);
        updateDescription(null);
    }

    public void atBattleStart() {
        Iterator var1 = AbstractDungeon.getMonsters().monsters.iterator();

        AbstractMonster m;
        do {
            if (!var1.hasNext()) {
                return;
            }

            m = (AbstractMonster)var1.next();
        } while(m.type != AbstractMonster.EnemyType.BOSS);

        this.bossChestAlreadyOpened = false;
    }

    @Override
    public void onChestOpen(boolean bossChest) {
        if (!bossChest || !bossChestAlreadyOpened) {
            if (this.counter > 0) {
                --this.counter;
                this.bossChestAlreadyOpened = bossChest;
                this.flash();
                CardCrawlGame.sound.play("GOLD_GAIN");
                AbstractDungeon.player.gainGold(GOLD_GAIN);
                    updateDescription(null);
            }
        }
    }

    @Override
    public void setCounter(int setCounter) {
        this.counter = setCounter;
        if (setCounter == -2) {
            this.usedUp();
            this.counter = -2;
        }

    }

    @Override
    public int getPrice() {
        return 15;
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.floorNum < 26;
    }

    @Override
    public String getUpdatedDescription() {
        if (this.counter == -1) {
            return DESCRIPTIONS[0];
        }
        else if (this.counter <= 0) {
            return DESCRIPTIONS[5];
        } else if (this.counter == 1) {
            return DESCRIPTIONS[1] + GOLD_GAIN + DESCRIPTIONS[4];
        } else {
            return DESCRIPTIONS[1] + GOLD_GAIN + DESCRIPTIONS[2] + this.counter + DESCRIPTIONS[3];
        }
    }


    @Override
    public Integer onSave() {
        return GOLD_GAIN;
    }

    @Override
    public void onLoad(Integer integer) {
        if (integer != null) {
            GOLD_GAIN = integer;
        } else
            GOLD_GAIN = 0;
        updateDescription(null);
    }
}
