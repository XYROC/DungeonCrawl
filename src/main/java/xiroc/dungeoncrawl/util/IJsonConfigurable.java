package xiroc.dungeoncrawl.util;

/*
 * DungeonCrawl (C) 2019 - 2020 XYROC (XIROC1337), All Rights Reserved
 */

import com.google.gson.JsonObject;

import java.io.File;
import java.util.HashMap;

/**
 * This is a part of the json config system. All instances of this interface
 * should be manually added to the static initialization block of JsonConfig.
 */
public interface IJsonConfigurable {

    public File getFile();

    public void load(JsonObject object, File file);

    public JsonObject create(JsonObject object);

    public HashMap<String, Object> getDefaults();

    public String[] getKeys();

    public int getVersion();

    public default boolean deleteOldVersions() {
        return false;
    }

}
