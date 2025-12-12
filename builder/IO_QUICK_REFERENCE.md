# 🔗 I/O Configuration Quick Reference

## 🚀 Quick Access
```
Function → Click card → Expand → ⚙️ Configure Input/Output
```

---

## 📥 JSON Input Tab

### Setup
1. ☑ Enable JSON Input
2. Paste/Edit sample JSON
3. Click ✓ Validate

### Mapping
```
[➕ Add]  or  [🔄 Auto Map]

⋮⋮ $.jsonPath  →  parameter  [🧪][🗑️]
   Transform: optional expression
```

### JSONPath Examples
```
$.id                    → Simple property
$.user.name             → Nested property
$.items.price           → Nested in object
```

---

## 📤 Output Config Tab

### Setup
1. ☑ Enable Output Configuration
2. Select format: 📄 JSON | 📝 XML | 📃 Text | ⚙️ Custom
3. Edit template

### Template Placeholders
```
{{output}}        → Function return value
{{functionName}}  → Function name
{{param1}}        → Parameter value
{{customVar}}     → From output mapping
```

### Mapping
```
[➕ Add]

⋮⋮ sourceExpression  →  $.outputPath  [🗑️]
   Transform: optional
```

---

## 🎯 Common Patterns

### API Request → Function
```json
Input: {"userId": 123, "action": "update"}

Mappings:
$.userId  → id
$.action  → operation

Output Template:
{
  "status": "success",
  "userId": "{{id}}",
  "result": "{{output}}"
}
```

### Database → JSON Response
```
Input: N/A (use function params)

Output Template:
{
  "data": {{output}},
  "meta": {
    "function": "{{functionName}}",
    "count": {{output.length}}
  }
}
```

---

## ⌨️ Actions

| Button | Action |
|--------|--------|
| ✓ Validate | Check JSON syntax |
| 🔄 Auto Map | Auto-map to params |
| ➕ Add | Add new mapping |
| 🧪 Test | Test mapping extraction |
| 🗑️ Remove | Delete mapping |
| ⋮⋮ Handle | Drag to reorder |

---

## 💡 Tips

✅ **Do**:
- Validate JSON first
- Use auto-map as starting point
- Test complex paths
- Keep transforms simple

❌ **Don't**:
- Skip validation
- Use complex nested transforms
- Forget placeholder syntax
- Leave mappings untested

---

## 🐛 Quick Fixes

| Problem | Solution |
|---------|----------|
| ❌ Invalid JSON | Check commas, brackets, quotes |
| JSONPath null | Verify path with 🧪 test |
| Placeholder not working | Use {{name}} exact syntax |
| Mapping not applied | Check toggle is ON |

---

## 📚 Full Docs

- **Complete Guide**: IO_CONFIGURATION_GUIDE.md
- **Feature Summary**: IO_FEATURE_SUMMARY.md
- **Main Guide**: USER_GUIDE.md

---

## 🎓 30-Second Tutorial

1. **Select function** → Click card
2. **Open config** → Click ⚙️ button
3. **Enable input** → Toggle ON
4. **Add JSON** → Paste sample
5. **Auto-map** → Click 🔄
6. **Enable output** → Toggle ON
7. **Choose format** → Select type
8. **Done!** → Close panel

---

## 🔥 Power User Shortcuts

```bash
# Quick mapping pattern
$.path → param (Transform: value * 2)

# Multi-parameter from nested
$.user.profile.* → name, email, age

# Conditional output
Transform: value > 100 ? "high" : "low"

# Array element (future)
$.items[0].price → firstPrice
```

---

## 📊 Format Cheatsheet

### JSON
```json
{"key": "{{value}}"}
```

### XML
```xml
<tag>{{value}}</tag>
```

### Text
```
Result: {{value}}
```

### Custom
```
Any format with {{placeholders}}
```

---

**Quick Start**: Select function → ⚙️ Configure → Enable → Map → Done! 🎉
