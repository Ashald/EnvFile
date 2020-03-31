package net.ashald.envfile.products.pycharm;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.configurations.RunnerSettings;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.WriteExternalException;
import com.jetbrains.python.run.AbstractPythonRunConfiguration;
import com.jetbrains.python.run.PythonRunConfigurationExtension;
import net.ashald.envfile.platform.ui.EnvVarsConfigurationEditor;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class PyCharmRunConfigurationExtension extends PythonRunConfigurationExtension {

    @Nullable
    @Override
    protected String getEditorTitle() {
        return EnvVarsConfigurationEditor.getEditorTitle();
    }

    @Nullable
    @Override
    protected <P extends AbstractPythonRunConfiguration> SettingsEditor<P> createEditor(@NotNull P configuration) {
        return new EnvVarsConfigurationEditor<P>(configuration);
    }

    @NotNull
    @Override
    protected String getSerializationId() {
        return EnvVarsConfigurationEditor.getSerializationId();
    }

    @Override
    protected void writeExternal(@NotNull AbstractPythonRunConfiguration runConfiguration, @NotNull Element element) throws WriteExternalException {
        EnvVarsConfigurationEditor.writeExternal(runConfiguration, element);
    }

    @Override
    protected void readExternal(@NotNull AbstractPythonRunConfiguration runConfiguration, @NotNull Element element) throws InvalidDataException {
        EnvVarsConfigurationEditor.readExternal(runConfiguration, element);
    }

    @Override
    protected void validateConfiguration(@NotNull AbstractPythonRunConfiguration configuration, boolean isExecution) throws Exception {
        EnvVarsConfigurationEditor.validateConfiguration(configuration, isExecution);
    }

    @Override
    protected void patchCommandLine(@NotNull AbstractPythonRunConfiguration configuration, @Nullable RunnerSettings runnerSettings, @NotNull GeneralCommandLine cmdLine, @NotNull String runnerId) throws ExecutionException {
        Map<String, String> currentEnv = cmdLine.getEnvironment();
        Map<String, String> newEnv = EnvVarsConfigurationEditor.collectEnv(configuration, new HashMap<>(currentEnv));
        currentEnv.clear();
        currentEnv.putAll(newEnv);
    }

    //

    @Override
    protected boolean isApplicableFor(@NotNull AbstractPythonRunConfiguration configuration) {
        return true;
    }

    @Override
    protected boolean isEnabledFor(@NotNull AbstractPythonRunConfiguration applicableConfiguration, @Nullable RunnerSettings runnerSettings) {
        return true;
    }
}
