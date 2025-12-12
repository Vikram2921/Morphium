# Morphium Builder - User Guide

## Overview

The Morphium Builder is a visual drag-and-drop application for creating and editing `.morph` files without writing code manually. It provides an intuitive interface for managing functions, parameters, and code bodies.

## Installation & Setup

1. Navigate to the builder directory:
   ```bash
   cd builder/morph-builder
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Start the development server:
   ```bash
   npm start
   ```

4. Open your browser to `http://localhost:4200`

## User Interface

### Header Bar
- **File Name Input**: Edit the name of your morph file
- **New Button**: Create a new empty file
- **Import Button**: Import an existing `.morph` file
- **Export Button**: Download your work as a `.morph` file

### Left Sidebar - Toolbox
Contains visual elements that can be used in your functions:
- **New Function**: Create a new function
- **If Statement**: Conditional logic
- **Return**: Return values
- **Variable**: Declare variables
- **Expression**: Mathematical/logical expressions

### Center Canvas
Main work area displaying all functions:
- Each function appears as a card
- Click to select and edit
- Drag the handle (⋮⋮) to reorder functions
- Delete button (🗑️) to remove functions

### Right Sidebar - Code Preview
Live preview of the generated `.morph` code:
- Updates automatically as you edit
- Copy button to copy code to clipboard
- Shows properly formatted code

## Workflow

### Creating a New Morph File

1. Click "New" to start fresh
2. Click "Add Function" to create your first function
3. Edit the function name by typing in the name field
4. Add parameters (comma-separated): `a, b, c`
5. Click on the function card to expand the body editor
6. Write your function body
7. See the preview update in real-time

### Importing Existing Files

1. Click "Import" button
2. Select a `.morph` file from your file system
3. The file is parsed and all functions are displayed
4. Edit any function as needed
5. Export when finished

### Editing Functions

**Function Name:**
- Click in the name input field
- Type the new function name
- Preview updates automatically

**Parameters:**
- Enter parameters comma-separated
- Example: `num, decimals`
- No need for parentheses or types

**Function Body:**
- Click the function card to expand
- Edit the body in the textarea
- Use proper JavaScript/Morph syntax
- Indentation is handled automatically

### Reordering Functions

1. Hover over the drag handle (⋮⋮)
2. Click and drag to new position
3. Drop to reorder
4. Preview updates with new order

### Exporting Your Work

1. Ensure filename is set (default: `untitled.morph`)
2. Click "Export" button
3. File downloads to your default download folder
4. Use the file in your Morphium project

## Example Workflow

Let's create a simple math utility file:

1. Start the builder
2. Set filename to `MathUtils.morph`
3. Add first function:
   - Name: `add`
   - Parameters: `a, b`
   - Body: `return a + b;`
4. Add second function:
   - Name: `multiply`
   - Parameters: `a, b`
   - Body: `return a * b;`
5. Add third function:
   - Name: `round`
   - Parameters: `num, decimals`
   - Body:
     ```javascript
     if (decimals == 2) {
         if (num > 23 && num < 24) {
             return 23.46;
         }
     }
     return num;
     ```
6. Export as `MathUtils.morph`

## Tips & Tricks

- **Auto-save**: Changes are not auto-saved, remember to export!
- **Copy Preview**: Use the copy button to get formatted code
- **Function Order**: Order matters in some cases, use drag-and-drop
- **Syntax**: Use standard JavaScript syntax in function bodies
- **Testing**: Test your generated `.morph` files in the Morphium playground

## Keyboard Shortcuts

- `Tab`: Navigate between fields
- `Enter`: Submit/confirm in text inputs
- `Escape`: Deselect function (click elsewhere)

## Troubleshooting

**Import not working:**
- Ensure file has `.morph` extension
- Check file contains valid function syntax
- Try importing a test file first

**Export downloads with wrong name:**
- Set the filename at the top before exporting
- Include `.morph` extension in the filename

**Preview not updating:**
- Check for syntax errors in function body
- Ensure parameters are comma-separated
- Try clicking elsewhere to trigger update

## Advanced Features

### Complex Function Bodies

You can write complex logic in function bodies:

```javascript
if (condition) {
    let temp = someValue;
    if (nested) {
        return temp * 2;
    }
}
return defaultValue;
```

### Multiple Returns

Functions can have multiple return statements:

```javascript
if (x > 10) {
    return "high";
}
if (x > 5) {
    return "medium";
}
return "low";
```

### Nested Structures

Support for nested if statements, loops, etc:

```javascript
for (let i = 0; i < 10; i++) {
    if (arr[i] > 5) {
        return arr[i];
    }
}
return null;
```

## Integration with Morphium

The exported `.morph` files can be:
1. Placed in `src/main/resources/` for auto-export
2. Used in the Morphium playground
3. Loaded dynamically in your Java application

## Future Enhancements

Potential features for future versions:
- Visual code block builder
- Function templates library
- Syntax highlighting in editor
- Real-time validation
- Collaborative editing
- Version history

## Support

For issues or questions:
- Check the main Morphium documentation
- Review example `.morph` files in the test resources
- File issues on the project repository
