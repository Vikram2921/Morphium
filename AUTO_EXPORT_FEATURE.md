# Morphium Auto-Export Feature

## Summary

**Functions in Morphium (.morph files) are now automatically exported!** 

You no longer need to write explicit `export` statements for functions. Simply define your functions and they will be available when importing the module.

## How It Works

### Before (Old Way - Still Works)
```javascript
function add(a, b) {
    return a + b;
}

function multiply(a, b) {
    return a * b;
}

// Had to explicitly export
export add = add;
export multiply = multiply;
```

### Now (New Way - Recommended)
```javascript
function add(a, b) {
    return a + b;
}

function multiply(a, b) {
    return a * b;
}

// No export needed! Functions are automatically available
```

### Using Imported Functions
```javascript
import "./math-utils.morph" as math;

let sum = math.add(10, 20);          // 30
let product = math.multiply(5, 6);   // 30
let result = math.add(sum, product); // 60
```

## Key Points

1. **All top-level functions are automatically exported** - No need for explicit export statements
2. **Export statements are still supported** - For backwards compatibility and for exporting variables/constants
3. **Functions can call other functions** - Within the same module, functions can reference each other
4. **Import with alias** - Use `import "path/to/file.morph" as alias` syntax

## Examples

### Example 1: Math Utilities
**File: utils/math.morph**
```javascript
function square(x) {
    return x * x;
}

function cube(x) {
    return x * x * x;
}

function power(base, exp) {
    if (exp == 0) return 1;
    if (exp == 1) return base;
    return base * power(base, exp - 1);
}
```

**Usage:**
```javascript
import "utils/math.morph" as math;

let squared = math.square(5);      // 25
let cubed = math.cube(3);          // 27
let powered = math.power(2, 8);    // 256
```

### Example 2: String Utilities
**File: utils/strings.morph**
```javascript
function uppercase(str) {
    // Implementation
    return str;
}

function lowercase(str) {
    // Implementation
    return str;
}

function trim(str) {
    // Implementation
    return str;
}
```

**Usage:**
```javascript
import "utils/strings.morph" as str;

let result = str.uppercase("hello"); // "HELLO"
```

## IntelliJ Plugin

A complete IntelliJ IDEA plugin has been created in the `intellij-plugin` folder with the following features:

### Features
- ✅ **Syntax Highlighting**: Keywords, strings, numbers, comments, operators
- ✅ **Code Completion**: Auto-complete for keywords and built-in functions
- ✅ **Brace Matching**: Automatic matching of (), {}, []
- ✅ **Code Navigation**: Ctrl+Click to navigate to definitions
- ✅ **Error Highlighting**: Real-time syntax error detection
- ✅ **Code Folding**: Collapse/expand code blocks
- ✅ **Commenting**: Line (Ctrl+/) and block (Ctrl+Shift+/) comments

### Installation
1. Build the plugin: `cd intellij-plugin && ./gradlew buildPlugin`
2. In IntelliJ IDEA: `Settings > Plugins > ⚙️ > Install Plugin from Disk`
3. Select the built `.zip` file from `intellij-plugin/build/distributions/`
4. Restart IntelliJ IDEA

### Development
To test the plugin in development mode:
```bash
cd intellij-plugin
./gradlew runIde
```

This will launch a new IntelliJ IDEA instance with the plugin installed.

## Technical Implementation

The auto-export feature is implemented in the `MorphiumEngine` class:

```java
// After evaluating module, import all user-defined functions
importModuleFunctions(moduleContext, context, alias);
```

The `importModuleFunctions` method automatically makes all functions from the module context available under the import alias, eliminating the need for explicit exports.

## Testing

All tests pass with the new auto-export feature:
- ✅ `AutoExportTest`: Tests that functions work without explicit exports
- ✅ `ModuleImportTest`: Tests existing import functionality
- ✅ `UserDefinedFunctionsTest`: Tests function definitions and calls

Run tests with:
```bash
mvn test
```
