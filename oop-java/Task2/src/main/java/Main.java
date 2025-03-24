import calculator.Calculator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Program started");
        String configFileName = null;
        if (args.length == 1) {
            configFileName = args[0];
        }
        Calculator calculator = new Calculator(configFileName);
        calculator.execute();
        logger.info("Program finished");
    }
}
