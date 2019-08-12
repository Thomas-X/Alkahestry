package codes.zwarts.alkahestry;

public class Util {
    public static String generateRegistryName(String name) {
        return Alkahestry.MODID + ":" + name;
    }

    public static String stripModIdFromRegistryName(String name) {
        return name.replace(Alkahestry.MODID + ":", "");
    }
}
