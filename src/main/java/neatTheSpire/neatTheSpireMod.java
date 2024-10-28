package neatTheSpire;

import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.helpers.RelicType;
import basemod.helpers.TooltipInfo;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import neatTheSpire.Intefaces.onGenerateCardMidcombatInterface;
import neatTheSpire.cards.blue.*;
import neatTheSpire.cards.colorless.TurnOff;
import neatTheSpire.cards.colorless.TurnOn;
import neatTheSpire.cards.curse.CurseOfTheAnvil;
import neatTheSpire.cards.green.*;
import neatTheSpire.cards.purple.*;
import neatTheSpire.cards.red.*;
import neatTheSpire.cards.red.DejaVu;
import neatTheSpire.relics.*;
import neatTheSpire.util.IDCheckDontTouchPls;
import neatTheSpire.util.TextureLoader;
import neatTheSpire.variables.DefaultCustomVariable;
import neatTheSpire.variables.DefaultSecondMagicNumber;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Properties;

//TODO: DON'T MASS RENAME/REFACTOR
//TODO: DON'T MASS RENAME/REFACTOR
//TODO: DON'T MASS RENAME/REFACTOR
//TODO: DON'T MASS RENAME/REFACTOR
// Please don't just mass replace "theDefault" with "yourMod" everywhere.
// It'll be a bigger pain for you. You only need to replace it in 3 places.
// I comment those places below, under the place where you set your ID.

//TODO: FIRST THINGS FIRST: RENAME YOUR PACKAGE AND ID NAMES FIRST-THING!!!
// Right click the package (Open the project pane on the left. Folder with black dot on it. The name's at the very top) -> Refactor -> Rename, and name it whatever you wanna call your mod.
// Scroll down in this file. Change the ID from "theDefault:" to "yourModName:" or whatever your heart desires (don't use spaces). Dw, you'll see it.
// In the JSON strings (resources>localization>eng>[all them files] make sure they all go "yourModName:" rather than "theDefault". You can ctrl+R to replace in 1 file, or ctrl+shift+r to mass replace in specific files/directories (Be careful.).
// Start with the DefaultCommon cards - they are the most commented cards since I don't feel it's necessary to put identical comments on every card.
// After you sorta get the hang of how to make cards, check out the card template which will make your life easier

/*
 * With that out of the way:
 * Welcome to this super over-commented Slay the Spire modding base.
 * Use it to make your own mod of any type. - If you want to add any standard in-game content (character,
 * cards, relics), this is a good starting point.
 * It features 1 character with a minimal set of things: 1 card of each type, 1 debuff, couple of relics, etc.
 * If you're new to modding, you basically *need* the BaseMod wiki for whatever you wish to add
 * https://github.com/daviscook477/BaseMod/wiki - work your way through with this base.
 * Feel free to use this in any way you like, of course. MIT licence applies. Happy modding!
 *
 * And pls. Read the comments.
 */

@SpireInitializer
public class neatTheSpireMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        PostUpdateSubscriber,
        OnStartBattleSubscriber,
        PostInitializeSubscriber {
    // Make sure to implement the subscribers *you* are using (read basemod wiki). Editing cards? EditCardsSubscriber.
    // Making relics? EditRelicsSubscriber. etc., etc., for a full list and how to make your own, visit the basemod wiki.
    public static final Logger logger = LogManager.getLogger(neatTheSpireMod.class.getName());
    public static boolean lastTurnAttacked;
    public static Object hpAtTurnStart;
    public static boolean gainedStrDexThisTurn;
    private static String modID;
    private static SpireConfig ntsConfig;
    public static boolean ResonanceCheck;
    public static int checkEnergy = 0;
    public static int usedEnergy = 0;
    public static AbstractRelic FourthBossRelicStorage = null;
    public void receiveOnBattleStart (AbstractRoom room) {
        neatTheSpireMod.lastTurnAttacked = false;
        neatTheSpireMod.gainedStrDexThisTurn = false;
        neatTheSpireMod.hpAtTurnStart = AbstractDungeon.player.currentHealth;
        neatTheSpireMod.ResonanceCheck = false;
        neatTheSpireMod.checkEnergy = 0;
        neatTheSpireMod.usedEnergy = 0;
    }

    // Mod-settings settings. This is if you want an on/off savable button
    public static Properties ntsDefaultSettings = new Properties();
    public static final String ENABLE_FOURTHBOSSRELIC_SETTINGS = "enableFourthBossRelic";
    public static boolean enableFourthBossRelic = false; // The boolean we'll be setting on/off (true/false)
    public static Properties ntsClawSettings = new Properties();
    public static final String NTS_CLAW = "ntsClaw";
    public static boolean enableNtsClaw = false; // The boolean we'll be setting on/off (true/false)
//    public static Properties ntsGiltHeadSettings = new Properties();
//    public static final String NTS_GILTHEAD = "ntsGiltHead";
//    public static boolean enableNtsGiltHead = false; // The boolean we'll be setting on/off (true/false)





//    public static Properties innerFireSettings = new Properties();
//    public static final String INNER_FIRE_DAMAGE_PREVIEW = "innerFirePrev";
//    public static boolean innerFirePrev = false; // The boolean we'll be setting on/off (true/false)

    //This is for the in-game mod settings panel.
    private static final String MODNAME = "Neat The Spire";
    private static final String AUTHOR = "Diamsword"; // And pretty soon - You!
    private static final String DESCRIPTION = "Few additions to the base game to have it feel renewed and funnier. No overwhelms";

    // =============== INPUT TEXTURE LOCATION =================

    public static final String BADGE_IMAGE = "neatTheSpireResources/images/Badge.png";

    // =============== MAKE IMAGE PATHS =================

    public static String makeCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/" + resourcePath;
    }

    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }

    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }

    public static String makeOrbPath(String resourcePath) {
        return getModID() + "Resources/images/orbs/" + resourcePath;
    }

    public static String makePowerPath(String resourcePath) {
        return getModID() + "Resources/images/powers/" + resourcePath;
    }

    public static String makeEventPath(String resourcePath) {
        return getModID() + "Resources/images/events/" + resourcePath;
    }

    // =============== /MAKE IMAGE PATHS/ =================

    // =============== SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE =================

    public neatTheSpireMod() {
        BaseMod.subscribe(this);

        setModID("neatTheSpire");

        ntsDefaultSettings.setProperty(ENABLE_FOURTHBOSSRELIC_SETTINGS, "FALSE");
        try {
            ntsConfig = new SpireConfig("Neat The Spire", "neatTheSpireConfig", ntsDefaultSettings); // ...right here
            // the "fileName" parameter is the name of the file MTS will create where it will save our setting.
            ntsConfig.load(); // Load the setting and set the boolean to equal it
            enableFourthBossRelic = ntsConfig.getBool(ENABLE_FOURTHBOSSRELIC_SETTINGS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ntsClawSettings.setProperty(NTS_CLAW, "FALSE");
        try {
            ntsConfig = new SpireConfig("Neat The Spire", "neatTheSpireConfig", ntsClawSettings); // ...right here
            // the "fileName" parameter is the name of the file MTS will create where it will save our setting.
            ntsConfig.load(); // Load the setting and set the boolean to equal it
            enableNtsClaw = ntsConfig.getBool(NTS_CLAW);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        ntsGiltHeadSettings.setProperty(NTS_GILTHEAD, "FALSE");
//        try {
//            ntsConfig = new SpireConfig("Neat The Spire", "neatTheSpireConfig", ntsGiltHeadSettings); // ...right here
//            // the "fileName" parameter is the name of the file MTS will create where it will save our setting.
//            ntsConfig.load(); // Load the setting and set the boolean to equal it
//            enableNtsGiltHead = ntsConfig.getBool(NTS_GILTHEAD);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        innerFireSettings.setProperty(INNER_FIRE_DAMAGE_PREVIEW, "FALSE");
//        try {
//            ntsConfig = new SpireConfig("Neat The Spire", "neatTheSpireConfig", innerFireSettings); // ...right here
//            // the "fileName" parameter is the name of the file MTS will create where it will save our setting.
//            ntsConfig.load(); // Load the setting and set the boolean to equal it
//            innerFirePrev = ntsConfig.getBool(INNER_FIRE_DAMAGE_PREVIEW);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

    // ====== NO EDIT AREA ======
    // DON'T TOUCH THIS STUFF. IT IS HERE FOR STANDARDIZATION BETWEEN MODS AND TO ENSURE GOOD CODE PRACTICES.
    // IF YOU MODIFY THIS I WILL HUNT YOU DOWN AND DOWNVOTE YOUR MOD ON WORKSHOP

    public static void setModID(String ID) { // DON'T EDIT
        Gson coolG = new Gson(); // EY DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i hate u Gdx.files
        InputStream in = neatTheSpireMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THIS ETHER
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // OR THIS, DON'T EDIT IT
        logger.info("You are attempting to set your mod ID as: " + ID); // NO WHY
        if (ID.equals(EXCEPTION_STRINGS.DEFAULTID)) { // DO *NOT* CHANGE THIS ESPECIALLY, TO EDIT YOUR MOD ID, SCROLL UP JUST A LITTLE, IT'S JUST ABOVE
            throw new RuntimeException(EXCEPTION_STRINGS.EXCEPTION); // THIS ALSO DON'T EDIT
        } else if (ID.equals(EXCEPTION_STRINGS.DEVID)) { // NO
            modID = EXCEPTION_STRINGS.DEFAULTID; // DON'T
        } else { // NO EDIT AREA
            modID = ID; // DON'T WRITE OR CHANGE THINGS HERE NOT EVEN A LITTLE
        } // NO
        logger.info("Success! ID is " + modID); // WHY WOULD U WANT IT NOT TO LOG?? DON'T EDIT THIS.
    } // NO

    public static String getModID() { // NO
        return modID; // DOUBLE NO
    } // NU-UH

    private static void pathCheck() { // ALSO NO
        Gson coolG = new Gson(); // NOPE DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i still hate u btw Gdx.files
        InputStream in = neatTheSpireMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THISSSSS
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // NAH, NO EDIT
        String packageName = neatTheSpireMod.class.getPackage().getName(); // STILL NO EDIT ZONE
        FileHandle resourcePathExists = Gdx.files.internal(getModID() + "Resources"); // PLEASE DON'T EDIT THINGS HERE, THANKS
        if (!modID.equals(EXCEPTION_STRINGS.DEVID)) { // LEAVE THIS EDIT-LESS
            if (!packageName.equals(getModID())) { // NOT HERE ETHER
                throw new RuntimeException(EXCEPTION_STRINGS.PACKAGE_EXCEPTION + getModID()); // THIS IS A NO-NO
            } // WHY WOULD U EDIT THIS
            if (!resourcePathExists.exists()) { // DON'T CHANGE THIS
                throw new RuntimeException(EXCEPTION_STRINGS.RESOURCE_FOLDER_EXCEPTION + getModID() + "Resources"); // NOT THIS
            }// NO
        }// NO
    }// NO

    // ====== YOU CAN EDIT AGAIN ======


    @SuppressWarnings("unused")
    public static void initialize() {
        logger.info("========================= Initializing Default Mod. Hi. =========================");
        neatTheSpireMod defaultmod = new neatTheSpireMod();
        logger.info("========================= /Default Mod Initialized. Hello World./ =========================");
    }

    // ============== /SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE/ =================

    // =============== POST-INITIALIZE =================

    @Override
    public void receivePostInitialize() {
        logger.info("Loading badge image and mod options");

        // Load the Mod Badge
        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);

        // Create the Mod Menu
        ModPanel settingsPanel = new ModPanel();

        // Create the on/off button:
        ModLabeledToggleButton enableNormalsButton = new ModLabeledToggleButton("Bosses have an extra relic to chose from",
                350.0f, 720.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, // Position (trial and error it), color, font
                enableFourthBossRelic, // Boolean it uses
                settingsPanel, // The mod panel in which this button will be in
                (label) -> {}, // thing??????? idk
                (button) -> { // The actual button:

                    enableFourthBossRelic = button.enabled; // The boolean true/false will be whether the button is enabled or not
                    try {
                        // And based on that boolean, set the settings and save them
                        //SpireConfig config = new SpireConfig("defaultMod", "theDefaultConfig", ntsDefaultSettings);
                        ntsConfig.setBool(ENABLE_FOURTHBOSSRELIC_SETTINGS, enableFourthBossRelic);
                        //config.save();
                        ntsConfig.save();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

        settingsPanel.addUIElement(enableNormalsButton); // Add the button to the settings panel. Button is a go.

        ModLabeledToggleButton enableClawButton = new ModLabeledToggleButton("Claw increases Rip and Tear damage",
                350.0f, 670.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, // Position (trial and error it), color, font
                enableNtsClaw, // Boolean it uses
                settingsPanel, // The mod panel in which this button will be in
                (label) -> {}, // thing??????? idk
                (button) -> { // The actual button:

                    enableNtsClaw = button.enabled; // The boolean true/false will be whether the button is enabled or not
                    try {
                        // And based on that boolean, set the settings and save them
                        //SpireConfig config = new SpireConfig("defaultMod", "theDefaultConfig", ntsDefaultSettings);
                        ntsConfig.setBool(NTS_CLAW, enableNtsClaw);
                        //config.save();
                        ntsConfig.save();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

        settingsPanel.addUIElement(enableClawButton); // Add the button to the settings panel. Button is a go.

//        ModLabeledToggleButton enableGiltHeadButton = new ModLabeledToggleButton("If you often play to beat the heart, tick this box to balance gilt-head around that (6 -> 4)",
//                350.0f, 620.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, // Position (trial and error it), color, font
//                enableNtsGiltHead, // Boolean it uses
//                settingsPanel, // The mod panel in which this button will be in
//                (label) -> {}, // thing??????? idk
//                (button) -> { // The actual button:
//
//                    enableNtsGiltHead = button.enabled; // The boolean true/false will be whether the button is enabled or not
//                    try {
//                        // And based on that boolean, set the settings and save them
//                        //SpireConfig config = new SpireConfig("defaultMod", "theDefaultConfig", ntsDefaultSettings);
//                        ntsConfig.setBool(NTS_GILTHEAD, enableNtsGiltHead);
//                        //config.save();
//                        ntsConfig.save();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                });
//
//        settingsPanel.addUIElement(enableGiltHeadButton); // Add the button to the settings panel. Button is a go.

//        ModLabeledToggleButton enableInnerFireButton = new ModLabeledToggleButton("Inner Fire previews the additional damage if you have a non-Attack in hand",
//                350.0f, 670.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, // Position (trial and error it), color, font
//                innerFirePrev, // Boolean it uses
//                settingsPanel, // The mod panel in which this button will be in
//                (label) -> {}, // thing??????? idk
//                (button) -> { // The actual button:
//
//                    innerFirePrev = button.enabled; // The boolean true/false will be whether the button is enabled or not
//                    try {
//                        // And based on that boolean, set the settings and save them
//                        //SpireConfig config = new SpireConfig("defaultMod", "theDefaultConfig", innerFireSettings);
//                        ntsConfig.setBool(INNER_FIRE_DAMAGE_PREVIEW, innerFirePrev);
//                        //config.save();
//                        ntsConfig.save();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                });
//
//        settingsPanel.addUIElement(enableInnerFireButton); // Add the button to the settings panel. Button is a go.

        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);


        // =============== EVENTS =================

        // This event will be exclusive to the City (act 2). If you want an event that's present at any
        // part of the game, simply don't include the dungeon ID
        // If you want to have a character-specific event, look at slimebound (CityRemoveEventPatch).
        // Essentially, you need to patch the game and say "if a player is not playing my character class, remove the event from the pool"
        //BaseMod.addEvent(IdentityCrisisEvent.ID, IdentityCrisisEvent.class, TheCity.ID);

        // =============== /EVENTS/ =================
        logger.info("Done loading badge Image and mod options");
    }

    // =============== / POST-INITIALIZE/ =================


    // ================ ADD POTIONS ===================

    public void receiveEditPotions() {
        logger.info("Beginning to edit potions");

        // Class Specific Potion. If you want your potion to not be class-specific,
        // just remove the player class at the end (in this case the "TheDefaultEnum.THE_DEFAULT".
        // Remember, you can press ctrl+P inside parentheses like addPotions)
        //BaseMod.addPotion(EquilibriumPotion.class, Color.DARK_GRAY.cpy(), Color.CHARTREUSE.cpy(), Color.CORAL.cpy(), EquilibriumPotion.POTION_ID);

        logger.info("Done editing potions");
    }

    // ================ /ADD POTIONS/ ===================


    // ================ ADD RELICS ===================

    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics");

        BaseMod.addRelic(new SmoothedVajra(), RelicType.SHARED);
        UnlockTracker.markRelicAsSeen(SmoothedVajra.ID);
        BaseMod.addRelic(new PaintRoller(), RelicType.SHARED);
        UnlockTracker.markRelicAsSeen(PaintRoller.ID);
        BaseMod.addRelic(new Seesaw(), RelicType.SHARED);
        UnlockTracker.markRelicAsSeen(Seesaw.ID);
        BaseMod.addRelic(new Gilthead(), RelicType.SHARED);
        UnlockTracker.markRelicAsSeen(Gilthead.ID);
        BaseMod.addRelic(new SecretStash(), RelicType.SHARED);
        UnlockTracker.markRelicAsSeen(SecretStash.ID);

        //---GEODE---RARE/BOSS---//
        BaseMod.addRelic(new Geode(), RelicType.SHARED);
        UnlockTracker.markRelicAsSeen(Geode.ID);
        BaseMod.addRelic(new OpenedGeode(), RelicType.SHARED);

        //---BOSS---//
        BaseMod.addRelic(new InfinitePower(), RelicType.SHARED);
        UnlockTracker.markRelicAsSeen(InfinitePower.ID);
        BaseMod.addRelic(new HauntedAnvil(), RelicType.SHARED);
        UnlockTracker.markRelicAsSeen(HauntedAnvil.ID);
        BaseMod.addRelic(new BloodHourglass(), RelicType.SHARED);
        UnlockTracker.markRelicAsSeen(BloodHourglass.ID);

        //---GREEN---//
        BaseMod.addRelic(new BloodyClaws(), RelicType.RED);
        UnlockTracker.markRelicAsSeen(BloodyClaws.ID);
        BaseMod.addRelic(new TechniqueScroll(), RelicType.RED);
        UnlockTracker.markRelicAsSeen(TechniqueScroll.ID);

        //---GREEN---//
        BaseMod.addRelic(new PrisonerBall(), RelicType.GREEN);
        UnlockTracker.markRelicAsSeen(PrisonerBall.ID);
        BaseMod.addRelic(new BottledIceCube(), RelicType.GREEN);
        UnlockTracker.markRelicAsSeen(BottledIceCube.ID);

        //---BLUE---//
        BaseMod.addRelic(new FireWall(), RelicType.BLUE);
        UnlockTracker.markRelicAsSeen(FireWall.ID);
        BaseMod.addRelic(new SolarPanel(), RelicType.BLUE);
        UnlockTracker.markRelicAsSeen(SolarPanel.ID);

        //---PURPLE---//
        BaseMod.addRelic(new PocketLightning(), RelicType.PURPLE);
        UnlockTracker.markRelicAsSeen(PocketLightning.ID);
        BaseMod.addRelic(new FocusBand(), RelicType.PURPLE);
        UnlockTracker.markRelicAsSeen(FocusBand.ID);

        logger.info("Done adding relics!");
    }

    // ================ /ADD RELICS/ ===================


    // ================ ADD CARDS ===================

    @Override
    public void receiveEditCards() {
        logger.info("Adding variables");
        //Ignore this
        pathCheck();
        // Add the Custom Dynamic Variables
        logger.info("Add variables");
        // Add the Custom Dynamic variables
        BaseMod.addDynamicVariable(new DefaultCustomVariable());
        BaseMod.addDynamicVariable(new DefaultSecondMagicNumber());

        logger.info("Adding cards");
        // Add the cards
        // Don't comment out/delete these cards (yet). You need 1 of each type and rarity (technically) for your game not to crash
        // when generating card rewards/shop screen items.

//        BaseMod.addCard(new Sout());

        //---RED---//
        BaseMod.addCard(new BloodRage());
        UnlockTracker.unlockCard(BloodRage.ID);
//        BaseMod.addCard(new MovedDesolationStrike());
//        UnlockTracker.unlockCard(MovedDesolationStrike.ID);
        BaseMod.addCard(new DoubleBlade());
        UnlockTracker.unlockCard(DoubleBlade.ID);
        BaseMod.addCard(new FlameBlow());
        UnlockTracker.unlockCard(FlameBlow.ID);
        BaseMod.addCard(new InnerFire());
        UnlockTracker.unlockCard(InnerFire.ID);
        BaseMod.addCard(new OozingGloves());
        UnlockTracker.unlockCard(OozingGloves.ID);
        BaseMod.addCard(new PiercingStrike());
        UnlockTracker.unlockCard(PiercingStrike.ID);
        BaseMod.addCard(new PocketBlade());
        UnlockTracker.unlockCard(PocketBlade.ID);
//        BaseMod.addCard(new Shieldrang());
//        UnlockTracker.unlockCard(Shieldrang.ID);
        BaseMod.addCard(new Simpleton());
        UnlockTracker.unlockCard(Simpleton.ID);
        BaseMod.addCard(new LavaHand());
        UnlockTracker.unlockCard(LavaHand.ID);
//        BaseMod.addCard(new TormentingStrike());
//        UnlockTracker.unlockCard(TormentingStrike.ID);
//        BaseMod.addCard(new TrueGuts());
//        UnlockTracker.unlockCard(TrueGuts.ID);

        //---GREEN---//
//        BaseMod.addCard(new AlwaysLearn());
//        UnlockTracker.unlockCard(AlwaysLearn.ID);
        BaseMod.addCard(new BlurryFumes());
        UnlockTracker.unlockCard(BlurryFumes.ID);
        BaseMod.addCard(new DodgeAndDash());
        UnlockTracker.unlockCard(DodgeAndDash.ID);
        BaseMod.addCard(new Droplets());
        UnlockTracker.unlockCard(Droplets.ID);
        BaseMod.addCard(new Reload());
        UnlockTracker.unlockCard(Reload.ID);
        BaseMod.addCard(new Shred());
        UnlockTracker.unlockCard(Shred.ID);
        BaseMod.addCard(new SpecialConcoction());
        UnlockTracker.unlockCard(SpecialConcoction.ID);
        BaseMod.addCard(new Stalwart());
        UnlockTracker.unlockCard(Stalwart.ID);
        BaseMod.addCard(new TheaterMask());
        UnlockTracker.unlockCard(TheaterMask.ID);
        BaseMod.addCard(new ThrowIt());
        UnlockTracker.unlockCard(ThrowIt.ID);

        //---BLUE---//
        BaseMod.addCard(new Brightness());
        UnlockTracker.unlockCard(Brightness.ID);
        BaseMod.addCard(new ClosedCircuit());
        UnlockTracker.unlockCard(ClosedCircuit.ID);
        BaseMod.addCard(new Construct());
        UnlockTracker.unlockCard(Construct.ID);
        BaseMod.addCard(new DeepLearningCore());
        UnlockTracker.unlockCard(DeepLearningCore.ID);
//        BaseMod.addCard(new Dispense());
//        UnlockTracker.unlockCard(Dispense.ID);
        BaseMod.addCard(new EnergyDrink());
        UnlockTracker.unlockCard(EnergyDrink.ID);
        BaseMod.addCard(new FineAndShine());
        UnlockTracker.unlockCard(FineAndShine.ID);
//        BaseMod.addCard(new LightSwitch());
//        UnlockTracker.unlockCard(LightSwitch.ID);
        BaseMod.addCard(new MeteorSpin());
        UnlockTracker.unlockCard(MeteorSpin.ID);
        BaseMod.addCard(new Polarization());
        UnlockTracker.unlockCard(Polarization.ID);
        BaseMod.addCard(new ReachStability());
        UnlockTracker.unlockCard(ReachStability.ID);
        BaseMod.addCard(new ScienceCuriosity());
        UnlockTracker.unlockCard(ScienceCuriosity.ID);

        //---PURPLE---//
        BaseMod.addCard(new Ascended());
        UnlockTracker.unlockCard(Ascended.ID);
        BaseMod.addCard(new Aspiration());
        UnlockTracker.unlockCard(Aspiration.ID);
//        BaseMod.addCard(new Contemplation());
//        UnlockTracker.unlockCard(Contemplation.ID);
        BaseMod.addCard(new DejaVu());
        UnlockTracker.unlockCard(DejaVu.ID);
        BaseMod.addCard(new DesolationStrike());
        UnlockTracker.unlockCard(DesolationStrike.ID);
        BaseMod.addCard(new Elevation());
        UnlockTracker.unlockCard(Elevation.ID);
        BaseMod.addCard(new EyeBeam());
        UnlockTracker.unlockCard(EyeBeam.ID);
        BaseMod.addCard(new FoolReality());
        UnlockTracker.unlockCard(FoolReality.ID);
        BaseMod.addCard(new Impatient());
        UnlockTracker.unlockCard(Impatient.ID);
//        BaseMod.addCard(new InnerPain());
//        BaseMod.addCard(new MoodyStrike());
//        UnlockTracker.unlockCard(MoodyStrike.ID);
        BaseMod.addCard(new UseTheHead());
        UnlockTracker.unlockCard(UseTheHead.ID);

        //---COLORLESS---//
        BaseMod.addCard(new TurnOn());
        UnlockTracker.unlockCard(TurnOn.ID);
        BaseMod.addCard(new TurnOff());
        UnlockTracker.unlockCard(TurnOff.ID);

        //---CURSE---//
        BaseMod.addCard(new CurseOfTheAnvil());
        UnlockTracker.unlockCard(CurseOfTheAnvil.ID);

        logger.info("Making sure the cards are unlocked.");
        // Unlock the cards
        // This is so that they are all "seen" in the library, for people who like to look at the card list
        // before playing your mod.



        logger.info("Done adding cards!");
    }

    // There are better ways to do this than listing every single individual card, but I do not want to complicate things
    // in a "tutorial" mod. This will do and it's completely ok to use. If you ever want to clean up and
    // shorten all the imports, go look take a look at other mods, such as Hubris.

    // ================ /ADD CARDS/ ===================

    @SpireEnum
    public static AbstractCard.CardTags RESONATE;
    public static AbstractCard.CardTags ITEM;
    public static AbstractCard.CardTags OTHERTURNRELATED;


    // ================ LOAD THE TEXT ===================

    @Override
    public void receiveEditStrings() {
        logger.info("Beginning to edit strings for mod with ID: " + getModID());

        // CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class,
                getModID() + "Resources/localization/eng/DefaultMod-Card-Strings.json");

        // PowerStrings
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                getModID() + "Resources/localization/eng/DefaultMod-Power-Strings.json");

        // RelicStrings
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                getModID() + "Resources/localization/eng/DefaultMod-Relic-Strings.json");

        // Event Strings
        BaseMod.loadCustomStringsFile(EventStrings.class,
                getModID() + "Resources/localization/eng/DefaultMod-Event-Strings.json");

        // PotionStrings
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                getModID() + "Resources/localization/eng/DefaultMod-Potion-Strings.json");

        // CharacterStrings
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                getModID() + "Resources/localization/eng/DefaultMod-Character-Strings.json");

        // OrbStrings
        BaseMod.loadCustomStringsFile(OrbStrings.class,
                getModID() + "Resources/localization/eng/DefaultMod-Orb-Strings.json");

        BaseMod.loadCustomStringsFile(UIStrings.class,
                getModID() + "Resources/localization/eng/DefaultMod-UIStrings.json");

        logger.info("Done edittting strings");
    }

    // ================ /LOAD THE TEXT/ ===================

    public static ArrayList<TooltipInfo> resonanceTooltip; //define static list of tooltip info
    static { //static code block, is executed the first time the class is used
        resonanceTooltip = new ArrayList<TooltipInfo>(); //create new instance
        resonanceTooltip.add(new TooltipInfo("Resonance", "When at 3 stacks draw two cards, deal damage equal to your strength and gain 4 Block, can be activated only once per turn per enemy.")); //Add a tooltip to the list
    }
    public static ArrayList<TooltipInfo> itemTooltip; //define static list of tooltip info
    static { //static code block, is executed the first time the class is used
        itemTooltip = new ArrayList<TooltipInfo>(); //create new instance
        itemTooltip.add(new TooltipInfo("Items affected", "Banshee's veil NL Guinsoo's rageblade NL Haunting guise NL Hextech protobelt NL Iceborn gauntlet NL Lich bane NL Nashor's tooth NL Needlessly large rod NL Trinity force NL Zhonyas hourglass")); //Add a tooltip to the list
    }
    public static ArrayList<TooltipInfo> dupTooltip; //define static list of tooltip info
    static { //static code block, is executed the first time the class is used
        dupTooltip = new ArrayList<TooltipInfo>(); //create new instance
        dupTooltip.add(new TooltipInfo("Powers affected", "Common effects : NL Energized NL Draw more cards on next turn NL Next turn block NL Next turn lose strength NL Next turn lose dexterity NL Next turn gain strength NL Delayed resonance NL Delayed damage" +
                " NL NL Rare effects : NL Next turn death NL Gain an extra turn NL Phantasmal NL Nightmare NL Draw less cards on next turn")); //Add a tooltip to the list
    }

    // ================ LOAD THE KEYWORDS ===================

    @Override
    public void receiveEditKeywords() {
        // Keywords on cards are supposed to be Capitalized, while in Keyword-String.json they're lowercase
        //
        // Multiword keywords on cards are done With_Underscores
        //
        // If you're using multiword keywords, the first element in your NAMES array in your keywords-strings.json has to be the same as the PROPER_NAME.
        // That is, in Card-Strings.json you would have #yA_Long_Keyword (#y highlights the keyword in yellow).
        // In Keyword-Strings.json you would have PROPER_NAME as A Long Keyword and the first element in NAMES be a long keyword, and the second element be a_long_keyword

        Gson gson = new Gson();
        String json = Gdx.files.internal(getModID() + "Resources/localization/eng/DefaultMod-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(getModID().toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
                //  getModID().toLowerCase() makes your keyword mod specific (it won't show up in other cards that use that word)
            }
        }
    }

    // ================ /LOAD THE KEYWORDS/ ===================

    public static void onGenerateCardMidcombat(AbstractCard c)
    {
        AbstractDungeon.player.relics.stream().filter(r -> r instanceof onGenerateCardMidcombatInterface).forEach(r -> ((onGenerateCardMidcombatInterface)r).onCreateCard(c));
        AbstractDungeon.player.powers.stream().filter(r -> r instanceof onGenerateCardMidcombatInterface).forEach(r -> ((onGenerateCardMidcombatInterface)r).onCreateCard(c));
        AbstractDungeon.player.hand.group.stream().filter(card -> card instanceof onGenerateCardMidcombatInterface).forEach(card -> ((onGenerateCardMidcombatInterface)card).onCreateCardCard(c));
        AbstractDungeon.player.discardPile.group.stream().filter(card -> card instanceof onGenerateCardMidcombatInterface).forEach(card -> ((onGenerateCardMidcombatInterface)card).onCreateCardCard(c));
        AbstractDungeon.player.drawPile.group.stream().filter(card -> card instanceof onGenerateCardMidcombatInterface).forEach(card -> ((onGenerateCardMidcombatInterface)card).onCreateCardCard(c));
        AbstractDungeon.getMonsters().monsters.stream().filter(mon -> !mon.isDeadOrEscaped()).forEach(m -> m.powers.stream().filter(pow -> pow instanceof onGenerateCardMidcombatInterface).forEach(pow -> ((onGenerateCardMidcombatInterface)pow).onCreateCard(c)));
        if (c instanceof onGenerateCardMidcombatInterface)
        {
            ((onGenerateCardMidcombatInterface)c).onCreateCard(c);
        }
//        cardsCreatedThisCombat++;
//        cardsCreatedThisTurn++;
    }

    @Override
    public void receivePostUpdate() {
        AbstractPlayer p = AbstractDungeon.player;
        if (p == null) return;
        if (p.hasRelic(SmoothedVajra.ID)) SmoothedVajra.getRelics();
    }

    // this adds "ModName:" before the ID of any card/relic/power etc.
    // in order to avoid conflicts if any other mod uses the same ID.
    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }
}
