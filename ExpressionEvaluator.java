import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ExpressionEvaluator {
    private static final Map<String, String> FUNCTIONS = new HashMap<>();

    static {
        FUNCTIONS.put("sin", "Math.sin");
        FUNCTIONS.put("cos", "Math.cos");
        FUNCTIONS.put("tan", "Math.tan");
        FUNCTIONS.put("asin", "Math.asin");
        FUNCTIONS.put("acos", "Math.acos");
        FUNCTIONS.put("atan", "Math.atan");
        FUNCTIONS.put("log10", "Math.log10");
        FUNCTIONS.put("ln", "Math.log");
        FUNCTIONS.put("exp", "Math.exp");
        FUNCTIONS.put("abs", "Math.abs");
        FUNCTIONS.put("sqrt", "Math.sqrt");
        FUNCTIONS.put("ceil", "Math.ceil");
        FUNCTIONS.put("floor", "Math.floor");
        FUNCTIONS.put("pi", String.valueOf(Math.PI));
        FUNCTIONS.put("e", String.valueOf(Math.E));
        FUNCTIONS.put("LCM", "MathUtils.lcm");
        FUNCTIONS.put("GCD", "MathUtils.gcd");
    }

    public static BigInteger evaluate(String expression) throws ScriptException {
        // Replace functions
        for (Map.Entry<String, String> entry : FUNCTIONS.entrySet()) {
            expression = expression.replaceAll("(?<![a-zA-Z])" + entry.getKey() + "(?![a-zA-Z])", entry.getValue());
        }

        // Replace power operator
        expression = convertPowers(expression);

        ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
        if (engine == null) {
            throw new RuntimeException("No JavaScript engine found. Please use JDK 8-14 or add a JS engine dependency.");
        }
        return BigInteger.valueOf(((Number) engine.eval(expression)).longValue());
    }

    public static double evaluate(String expression, double x) throws ScriptException {
        expression = expression.replaceAll("(?<![a-zA-Z])x(?![a-zA-Z])", String.valueOf(x));
        for (Map.Entry<String, String> entry : FUNCTIONS.entrySet()) {
            expression = expression.replaceAll("(?<![a-zA-Z])" + entry.getKey() + "(?![a-zA-Z])", entry.getValue());
        }
        expression = convertPowers(expression);
        
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
        if (engine == null) {
            throw new RuntimeException("No JavaScript engine found.");
        }
        return ((Number) engine.eval(expression)).doubleValue();
    }

    private static String convertPowers(String expr) {
        Pattern pattern = Pattern.compile("([\\d\\.]+|[a-zA-Z_]+)\\s*\\^\\s*([\\d\\.]+|[a-zA-Z_]+)");
        Matcher matcher = pattern.matcher(expr);
        StringBuffer sb = new StringBuffer();

        while (matcher.find()) {
            String base = matcher.group(1);
            String exponent = matcher.group(2);
            matcher.appendReplacement(sb, "Math.pow(" + base + "," + exponent + ")");
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}