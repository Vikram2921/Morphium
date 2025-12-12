# Morphium Builder

A visual drag-and-drop builder for creating and editing Morphium `.morph` files.

## Features

- **Visual Function Editor**: Create and edit functions with an intuitive interface
- **Drag & Drop**: Reorder functions with drag-and-drop functionality
- **Import/Export**: Import existing `.morph` files and export your work
- **Live Preview**: See generated code in real-time as you edit
- **Code Generation**: Automatically generates properly formatted `.morph` files

## Getting Started

### Prerequisites

- Node.js (v18 or higher)
- npm (v9 or higher)

### Installation

```bash
cd builder/morph-builder
npm install
```

### Development server

To start a local development server, run:

```bash
ng serve
```

Once the server is running, open your browser and navigate to `http://localhost:4200/`. The application will automatically reload whenever you modify any of the source files.

### Building for Production

To build the project run:

```bash
ng build
```

This will compile your project and store the build artifacts in the `dist/` directory.

## Usage

### Creating a New Function

1. Click the "Add Function" button in the toolbox
2. Enter the function name
3. Add parameters (comma-separated)
4. Write the function body in the code editor
5. See the generated code in the preview panel

### Importing Existing Files

1. Click the "Import" button in the header
2. Select a `.morph` file from your computer
3. The file will be parsed and displayed in the editor

### Exporting Files

1. Make sure your filename is set (top of the page)
2. Click the "Export" button
3. The file will be downloaded as a `.morph` file

### Editing Functions

- Click on any function card to select and edit it
- Drag the handle (⋮⋮) to reorder functions
- Click the delete icon (🗑️) to remove a function
- Changes are reflected in real-time in the preview panel

## Project Structure

```
src/
├── app/
│   ├── components/
│   │   └── morph-builder/          # Main builder component
│   ├── models/
│   │   └── morph.model.ts          # Type definitions
│   └── services/
│       └── morph.service.ts        # Parser and code generator
```

## Technologies

- **Angular 21**: Modern web framework
- **Angular CDK**: Drag & Drop functionality
- **TypeScript**: Type-safe development
- **SCSS**: Styling

## Additional Resources

For more information on using the Angular CLI, visit the [Angular CLI Overview and Command Reference](https://angular.dev/tools/cli) page.

## License

Part of the Morphium project
