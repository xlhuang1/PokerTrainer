package local.pokertrainer;

public class Debugger {
    private static String DebugLevel = "";
    // "TRACE"

    private static boolean isEnabled() {
        return true;
    }

    public static String getDebugLevel() {
        return DebugLevel;
    }

    public void setDebugLevel(String level) {
        DebugLevel = level;
    }

    public static void log(Object message) {
        if (isEnabled()) {
            System.out.println(message.toString());
        }
    }

    public static void trace(Object message) {
        if (isEnabled() && DebugLevel.equals("TRACE")) {
            System.out.println(message.toString());
        }
    }
}
