# Morphium Visual Builder

This directory contains the Angular-based visual builder for creating and editing Morphium `.morph` files with advanced Input/Output configuration.

## Quick Start

### Windows
```bash
start-builder.bat
```

### Mac/Linux
```bash
./start-builder.sh
```

Or manually:
```bash
cd morph-builder
npm install
npm start
```

The application will be available at `http://localhost:4200`

## Features

### Core Functionality
- **Drag & Drop Interface**: Easily reorder functions
- **Import/Export**: Import existing `.morph` files and export your changes
- **Live Preview**: See generated code in real-time
- **Visual Editor**: No need to manually write code syntax

### 🆕 Comprehensive Toolbox (NEW!)
- **70+ Code Templates**: All organized in 10 categories
- **Drag & Drop**: Drag tools from toolbox directly into code
- **Search**: Find tools instantly with search box
- **Smart Insertion**: Auto-indentation and cursor positioning
- **Categories**: Control Flow, Arrays, Strings, Math, and more
- **Visual Feedback**: Green highlight on drop target

### 🆕 I/O Configuration
- **JSON Input Mapping**: Map JSON data to function parameters with JSONPath
- **Auto-Mapping**: Automatically map JSON keys to parameters
- **Output Configuration**: Configure output format (JSON/XML/Text/Custom)
- **Template System**: Use placeholders for dynamic output
- **Transform Expressions**: Transform data during mapping
- **Drag & Drop Mapping**: Visual mapping with drag-and-drop
- **Live Validation**: Real-time JSON validation

## Documentation

### Getting Started
- [morph-builder/README.md](./morph-builder/README.md) - Project documentation
- [USER_GUIDE.md](./USER_GUIDE.md) - Complete user guide
- [QUICK_REFERENCE.md](./QUICK_REFERENCE.md) - Quick tips

### Toolbox (NEW!)
- [TOOLBOX_GUIDE.md](./TOOLBOX_GUIDE.md) - Complete toolbox guide with 70+ tools

### I/O Configuration
- [IO_CONFIGURATION_GUIDE.md](./IO_CONFIGURATION_GUIDE.md) - Complete I/O guide
- [IO_FEATURE_SUMMARY.md](./IO_FEATURE_SUMMARY.md) - Feature overview
- [IO_QUICK_REFERENCE.md](./IO_QUICK_REFERENCE.md) - Quick reference

### Technical
- [IMPLEMENTATION_SUMMARY.md](./IMPLEMENTATION_SUMMARY.md) - Technical details
- [VISUAL_GUIDE.md](./VISUAL_GUIDE.md) - Interface guide
