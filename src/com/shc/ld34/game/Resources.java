package com.shc.ld34.game;

import com.shc.silenceengine.audio.Sound;
import com.shc.silenceengine.core.ResourceLoader;
import com.shc.silenceengine.core.SilenceEngine;
import com.shc.silenceengine.graphics.Sprite;
import com.shc.silenceengine.graphics.TrueTypeFont;
import com.shc.silenceengine.graphics.opengl.Texture;
import com.shc.silenceengine.io.FilePath;

/**
 * @author Sri Harsha Chilakapati
 */
public final class Resources
{
    private static ResourceLoader loader;

    private static void resourceLoadCallback(String info, float percentage)
    {
        loader.defaultRenderProgressCallback(info, percentage);

        if (percentage >= 50)
            loader.setLogo(Textures.LOGO);
    }

    public static void init()
    {
        loader = new ResourceLoader();

        Textures.LOGO = Texture.fromFilePath(FilePath.getResourceFile("resources/Logo.png"));

        int texKeyAltID = loader.loadResource(Texture.class, FilePath.getResourceFile("resources/key_alt.png"));
        int texBackgroundID = loader.loadResource(Texture.class, FilePath.getResourceFile("resources/Background.png"));
        int texBulletID = loader.loadResource(Texture.class, FilePath.getResourceFile("resources/bullet.png"));
        int texShipBlackID = loader.loadResource(Texture.class, FilePath.getResourceFile("resources/space_ship_black.png"));
        int texShipGoldID = loader.loadResource(Texture.class, FilePath.getResourceFile("resources/space_ship_gold.png"));
        int texShipWhiteID = loader.loadResource(Texture.class, FilePath.getResourceFile("resources/space_ship_white.png"));
        int texShieldID = loader.loadResource(Texture.class, FilePath.getResourceFile("resources/shield.png"));
        int texMotherShipID = loader.loadResource(Texture.class, FilePath.getResourceFile("resources/space_ship_mother.png"));
        int texNewWaveID = loader.loadResource(Texture.class, FilePath.getResourceFile("resources/new_wave.png"));
        int texShield2ID = loader.loadResource(Texture.class, FilePath.getResourceFile("resources/secure_shield.png"));

        int sndMusicID = loader.loadResource(Sound.class, FilePath.getResourceFile("resources/music.ogg"));
        int sndShootID = loader.loadResource(Sound.class, FilePath.getResourceFile("resources/shoot.wav"));
        int sndExplosionID = loader.loadResource(Sound.class, FilePath.getResourceFile("resources/explosion.wav"));

        loader.setProgressRenderCallback(Resources::resourceLoadCallback);
        loader.startLoading();

        Textures.KEY_ALT = loader.getResource(texKeyAltID);
        Textures.BACKGROUND = loader.getResource(texBackgroundID);
        Textures.BULLET = loader.getResource(texBulletID);
        Textures.SHIP_BLACK = loader.getResource(texShipBlackID);
        Textures.SHIP_GOLD = loader.getResource(texShipGoldID);
        Textures.SHIP_WHITE = loader.getResource(texShipWhiteID);
        Textures.SHIELD = loader.getResource(texShieldID);
        Textures.MOTHER_SHIP = loader.getResource(texMotherShipID);
        Textures.NEW_WAVE = loader.getResource(texNewWaveID);
        Textures.SHIELD2 = loader.getResource(texShield2ID);

        Sounds.MUSIC = loader.getResource(sndMusicID);
        Sounds.SHOOT = loader.getResource(sndShootID);
        Sounds.EXPLOSION = loader.getResource(sndExplosionID);

        Sounds.MUSIC.setLooping(true);

        Sprites.BULLET = new Sprite(Textures.BULLET);
        Sprites.SHIELD = new Sprite(Textures.SHIELD);
        Sprites.SHIP_BLACK = new Sprite(Textures.SHIP_BLACK);
        Sprites.SHIP_GOLD = new Sprite(Textures.SHIP_GOLD);
        Sprites.SHIP_WHITE = new Sprite(Textures.SHIP_WHITE);
        Sprites.MOTHER_SHIP = new Sprite(Textures.MOTHER_SHIP);
        Sprites.NEW_WAVE = new Sprite(Textures.NEW_WAVE);
        Sprites.SHIELD2 = new Sprite(Textures.SHIELD2);

        ((TrueTypeFont) SilenceEngine.graphics.getGraphics2D().getFont()).setStyle(TrueTypeFont.STYLE_BOLD);
    }

    public static void dispose()
    {
        Textures.LOGO.dispose();
        loader.dispose();
    }

    public static final class Textures
    {
        public static Texture BACKGROUND;
        public static Texture LOGO;
        public static Texture BULLET;
        public static Texture SHIP_BLACK;
        public static Texture SHIP_GOLD;
        public static Texture SHIP_WHITE;
        public static Texture SHIELD;
        public static Texture KEY_ALT;
        public static Texture MOTHER_SHIP;
        public static Texture NEW_WAVE;
        public static Texture SHIELD2;
    }

    public static final class Sounds
    {
        public static Sound MUSIC;
        public static Sound SHOOT;
        public static Sound EXPLOSION;
    }

    public static final class Sprites
    {
        public static Sprite BULLET;
        public static Sprite SHIP_BLACK;
        public static Sprite SHIP_GOLD;
        public static Sprite SHIP_WHITE;
        public static Sprite SHIELD;
        public static Sprite MOTHER_SHIP;
        public static Sprite NEW_WAVE;
        public static Sprite SHIELD2;
    }
}
