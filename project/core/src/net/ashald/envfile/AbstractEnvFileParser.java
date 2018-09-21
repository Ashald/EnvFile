package net.ashald.envfile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jetbrains.annotations.NotNull;

public abstract class AbstractEnvFileParser implements EnvFileParser {

    private static final Pattern pattern = Pattern.compile("\\$\\{([A-Za-z0-9._]+)}");

    @NotNull
    protected abstract Map<String, String> readFile(@NotNull String path) throws EnvFileErrorException, IOException;

    @NotNull
    @Override
    public Map<String, String> process(@NotNull String path, @NotNull Map<String, String> source) throws EnvFileErrorException, IOException {
        Map<String, String> sourceEnv = new HashMap<>(source);
        Map<String, String> overrides = readFile(path);

        sourceEnv.putAll(overrides);

        return expandEnvironmentVariables(sourceEnv);
    }

    Map<String, String>  expandEnvironmentVariables(Map<String, String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String value = entry.getValue();

            Matcher matcher = pattern.matcher(value);
            while (matcher.find()) {
                String envValue = getSystemValue(matcher.group(1));
                if (envValue != null) {
                    envValue = envValue.replace("\\", "\\\\");
                }
                Pattern subexpr = Pattern.compile(Pattern.quote(matcher.group(0)));
                value = subexpr.matcher(value).replaceAll(envValue);
            }

            entry.setValue(value);
        }

        return map;
    }

    // precedence System properties, those set via -D, then env properties
    // Assumption is that environment variables are all UPPERCASE
    private String getSystemValue(final String name) {
        return System.getProperty(name, System.getenv(name.toUpperCase()));
    }

}
