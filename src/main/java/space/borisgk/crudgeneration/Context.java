package space.borisgk.crudgeneration;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Context {
    public static Logger logger;

    static {
        logger = Logger.getLogger("GenerationLogger");
        logger.setLevel(Level.ALL);
    }
}
