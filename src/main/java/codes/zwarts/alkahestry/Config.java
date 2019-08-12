package codes.zwarts.alkahestry;

import codes.zwarts.alkahestry.proxy.CommonProxy;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Level;

import java.io.File;

public class Config {
    private static final String CATEGORY_GENERAL = "general";
    public static String yourRealName = "Unknown";
    public static Configuration configuration;

    public static void preInit (FMLPreInitializationEvent event) {
        File directory = event.getSuggestedConfigurationFile();
        configuration = new Configuration(new File(directory.getPath()), "alkahestry.cfg");
        Config.readConfig();
    }

    public static void postInit (FMLPostInitializationEvent event) {
        if (configuration.hasChanged()) {
            configuration.save();
        }
    }

    public static void readConfig () {
        Configuration cfg = configuration;
        try {
            cfg.load();
            initGeneralConfig(cfg);
        } catch (Exception err) {
            Alkahestry.logger.log(Level.ERROR, "Problem loading config file!", err);
        } finally {
            if (cfg.hasChanged()) {
                cfg.save();
            }
        }
    }

    private static void initGeneralConfig (Configuration cfg) {
        cfg.addCustomCategoryComment(Config.CATEGORY_GENERAL, "General Configuration");
        yourRealName = cfg.getString("realName", CATEGORY_GENERAL, yourRealName, "Set your real name here");
    }
}
