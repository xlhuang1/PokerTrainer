package local.pokertrainer;

public class Debugger {
    private String DebugLevel;

    private static boolean isEnabled() {
        return false;
    }

    public String getDebugLevel() {
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
}
