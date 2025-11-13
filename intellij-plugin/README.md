# Morphium IntelliJ Plugin

IntelliJ IDEA plugin for Morphium DSL (.morph files).

## Features

- **Syntax Highlighting**: Keywords, functions, strings, numbers, comments, and operators
- **Code Navigation**: Ctrl+Click (Cmd+Click on Mac) to navigate to function definitions
- **Error Highlighting**: Real-time syntax error detection
- **Code Completion**: Auto-completion for keywords and built-in functions
- **Brace Matching**: Automatic matching of parentheses, braces, and brackets
- **Code Folding**: Collapse/expand code blocks
- **Commenting**: Line and block comments support (Ctrl+/ and Ctrl+Shift+/)
- **Find Usages**: Find all usages of functions and variables

## Building the Plugin

```bash
cd intellij-plugin
./gradlew buildPlugin
```

The plugin will be built in `build/distributions/`.

## Installing the Plugin

1. Build the plugin using the command above
2. In IntelliJ IDEA, go to `File > Settings > Plugins`
3. Click the gear icon and select `Install Plugin from Disk...`
4. Select the `.zip` file from `build/distributions/`
5. Restart IntelliJ IDEA

## Development

To run the plugin in a development environment:

```bash
./gradlew runIde
```

This will start a new IntelliJ IDEA instance with the plugin installed.

## Requirements

- IntelliJ IDEA 2023.2 or later
- JDK 17 or later
- Gradle 8.4 or later
