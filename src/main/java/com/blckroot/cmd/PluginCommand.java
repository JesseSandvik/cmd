package com.blckroot.cmd;

import com.blckroot.cmd.filesystem.FileSystemService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class PluginCommand extends Command implements PluginCommandContract {
    private String executableFilePath;
    private final List<PluginCommand> pluginSubcommands = new ArrayList<>();
    private final List<String> arguments = new ArrayList<>();

    public PluginCommand(String name, String executableFilePath) {
        super(name);
        this.executableFilePath = executableFilePath;
    }

    public String getExecutableFilePath() {
        return this.executableFilePath;
    }

    @Override
    public List<PluginCommand> getPluginSubcommands() {
        return this.pluginSubcommands;
    }

    @Override
    public void addPluginSubcommand(PluginCommand pluginCommand) {
        this.pluginSubcommands.add(pluginCommand);
    }

    @Override
    public Integer call() {
        try {
            FileSystemService fileSystemService = new FileSystemService();

            if (fileSystemService.fileExists(executableFilePath) && fileSystemService.fileCanExecute(executableFilePath)) {
                if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                    String bashExecutablePath = "C:\\Program Files\\Git\\bin\\bash.exe";
                    String shExecutablePath = "C:\\Program Files\\Git\\bin\\sh.exe";
                    if (fileSystemService.fileExists(bashExecutablePath) &&
                            fileSystemService.fileCanExecute(bashExecutablePath)) {
                        this.arguments.add(bashExecutablePath);
                    } else if (fileSystemService.fileExists(shExecutablePath) &&
                            fileSystemService.fileCanExecute(shExecutablePath)) {
                        this.arguments.add(shExecutablePath);
                    }
                }
                this.arguments.add(executableFilePath);

                if (!this.getPositionalParameters().isEmpty()) {
                    setPositionalParameterArguments();
                }

                if (!this.getOptions().isEmpty()) {
                    setOptionArguments();
                }

                return execute(arguments);
            } else {
                if (this.getParentCommandName() != null) {
                    String executableFileParentDirectory =
                            executableFilePath.substring(0, executableFilePath.lastIndexOf("/"));
                    System.out.println("EXECUTABLE PARENT DIRECTORY: " + executableFileParentDirectory);
                    String parentCommandDirectory = executableFileParentDirectory + "/" + this.getParentCommandName() + "/";

                    if (fileSystemService.fileExists(parentCommandDirectory)) {
                        executableFilePath = parentCommandDirectory + this.getName();

                        System.out.println("EXECUTABLE FILE PATH: " + executableFilePath);

                        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                            String bashExecutablePath = "C:\\Program Files\\Git\\bin\\bash.exe";
                            String shExecutablePath = "C:\\Program Files\\Git\\bin\\sh.exe";
                            if (fileSystemService.fileExists(bashExecutablePath) &&
                                    fileSystemService.fileCanExecute(bashExecutablePath)) {
                                this.arguments.add(bashExecutablePath);
                            } else if (fileSystemService.fileExists(shExecutablePath) &&
                                    fileSystemService.fileCanExecute(shExecutablePath)) {
                                this.arguments.add(shExecutablePath);
                            }
                        }
                        this.arguments.add(executableFilePath);

                        if (!this.getPositionalParameters().isEmpty()) {
                            setPositionalParameterArguments();
                        }

                        if (!this.getOptions().isEmpty()) {
                            setOptionArguments();
                        }

                        return execute(arguments);
                    }
                }
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    private void setPositionalParameterArguments() {
        for (PositionalParameter positionalParameter : this.getPositionalParameters()) {
            if (positionalParameter.getValue() != null) {
                this.arguments.add(positionalParameter.getValue().toString());
            }
        }
    }

    private void setOptionArguments() {
        for (Option option : this.getOptions()) {
            if (option.getValue() != null) {
                if (option.getParameterLabel() == null) {
                    addArgumentForOptionTypeBoolean(option);
                } else {
                    addArgumentsForOptionTypeString(option);
                }
            }
        }
    }

    private void addArgumentForOptionTypeBoolean(Option option) {
        arguments.add(option.getLongestName());
    }

    private void addArgumentsForOptionTypeString(Option option) {
        arguments.add(option.getLongestName());
        arguments.add(option.getValue().toString());
    }

    private Integer execute(List<String> arguments) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(arguments);
        Process process = processBuilder.start();

        InputStream inputStream = process.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
        }
        process.waitFor();
        return process.exitValue();
    }
}
