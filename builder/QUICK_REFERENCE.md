# 🔮 Morphium Builder - Quick Reference

## 🚀 Quick Start

```bash
# Windows
cd builder && start-builder.bat

# Mac/Linux  
cd builder && ./start-builder.sh

# Or manually
cd builder/morph-builder
npm install
npm start
```

Open: **http://localhost:4200**

---

## 🎯 Main Features

| Feature | Action |
|---------|--------|
| **New File** | Click "New" button |
| **Import** | Click "Import" → Select `.morph` file |
| **Export** | Set filename → Click "Export" |
| **Add Function** | Click "➕ Add Function" |
| **Edit Function** | Click function card → Edit fields |
| **Reorder** | Drag handle (⋮⋮) to move |
| **Delete** | Click 🗑️ icon |
| **Copy Code** | Click 📋 Copy in preview |

---

## 📝 Creating a Function

1. **Add**: Click "Add Function" button
2. **Name**: Type function name (e.g., `add`)
3. **Parameters**: Enter comma-separated (e.g., `a, b`)
4. **Body**: Click card → Edit in textarea
5. **Preview**: Auto-updates in right panel

---

## 💡 Example

**Simple Math Function:**
```
Name: add
Parameters: a, b
Body: return a + b;
```

**Complex Function:**
```
Name: calculate
Parameters: num, operation
Body:
if (operation == "square") {
    return num * num;
}
if (operation == "double") {
    return num * 2;
}
return num;
```

---

## 🎨 Interface Layout

```
┌─────────────────────────────────────────────┐
│  🔮 Morphium Builder    [filename] [N][I][E]│
├─────────┬───────────────────────┬───────────┤
│ Toolbox │      Canvas           │  Preview  │
│         │                       │           │
│ • Func  │  ┌─────────────────┐  │  Code     │
│ • If    │  │  Function Card  │  │  Preview  │
│ • Return│  │  ⋮⋮ [name]  🗑️  │  │           │
│ • Var   │  │  Params: a, b   │  │  [Copy]   │
│ • Expr  │  └─────────────────┘  │           │
│         │                       │           │
│ [Add]   │  [+ Add Function]     │           │
└─────────┴───────────────────────┴───────────┘
```

---

## ⌨️ Keyboard Tips

- **Tab**: Navigate fields
- **Enter**: Confirm input
- **Escape**: Click elsewhere to deselect

---

## 🐛 Troubleshooting

| Problem | Solution |
|---------|----------|
| Import fails | Check `.morph` extension |
| Preview doesn't update | Check syntax, click elsewhere |
| Build errors | Run `npm install` again |
| Port 4200 busy | Stop other Angular apps |

---

## 📚 Documentation

- **README.md** - Overview & setup
- **USER_GUIDE.md** - Complete guide
- **IMPLEMENTATION_SUMMARY.md** - Technical details

---

## 🛠️ Commands

```bash
# Development
npm start              # Start dev server
npm run build          # Build for production
npm test               # Run tests (if configured)

# Production
npm run build -- --configuration=production
```

---

## 📦 Output

**Exported files:**
- Format: `.morph`
- Location: Browser downloads folder
- Compatible: With Morphium main project

**Build output:**
- Location: `dist/morph-builder/`
- Size: ~80 KB (gzipped)
- Ready: For deployment

---

## 🎓 Workflow

1. **Start** → `npm start`
2. **Create/Import** → Add/load functions
3. **Edit** → Modify in visual editor
4. **Preview** → Check generated code
5. **Export** → Download `.morph` file
6. **Use** → In Morphium project

---

## ✨ Pro Tips

- Functions are ordered top-to-bottom
- Use drag handles to reorder
- Preview updates in real-time
- Always export before closing
- Test in Morphium playground

---

## 📞 Support

For issues:
- Check USER_GUIDE.md
- Review example `.morph` files
- Check console for errors

---

**Made with ❤️ for Morphium**
