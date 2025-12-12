# 🧰 Comprehensive Toolbox Guide

## Overview

The Morphium Builder now features a **comprehensive, categorized toolbox** with 70+ drag-and-drop code elements organized into 10 categories.

---

## 🎯 Quick Access

### Opening the Toolbox
The toolbox is always visible on the left side of the builder interface.

### Search Functionality
1. Click the search box at the top
2. Type keywords (e.g., "loop", "array", "string")
3. See filtered results instantly
4. Drag any result to your code

### Collapsing/Expanding
- Click category headers to expand/collapse
- Click ⬆️/⬇️ button to collapse/expand all

---

## 📚 Categories

### 1. 🔀 Control Flow (7 items)
Flow control and conditional logic

| Tool | Icon | Description | Template |
|------|------|-------------|----------|
| If Statement | ❓ | Conditional execution | `if (condition) { }` |
| If-Else | ↔️ | With alternative | `if (condition) { } else { }` |
| If-Else-If | 🔄 | Multiple conditions | `if...else if...else` |
| For Loop | 🔁 | Counter-based iteration | `for (let i = 0; i < n; i++)` |
| While Loop | ⭕ | Condition-based loop | `while (condition) { }` |
| Switch Case | 🎛️ | Multiple branches | `switch (value) { case: }` |
| Ternary | ❓ | Inline conditional | `condition ? true : false` |

### 2. 📦 Variables & Data (5 items)
Data declarations and structures

| Tool | Icon | Description |
|------|------|-------------|
| Variable (let) | 📌 | Mutable variable |
| Constant | 🔒 | Immutable value |
| Object | 🗂️ | Key-value structure |
| Array | 📚 | Ordered collection |
| Destructuring | 📤 | Extract values |

### 3. ⚡ Functions (3 items)
Function-related constructs

| Tool | Icon | Description |
|------|------|-------------|
| Return | ↩️ | Return value |
| Arrow Function | ➡️ | Anonymous function |
| Callback | 📞 | Callback pattern |

### 4. 🔢 Array Operations (9 items)
Array transformation and manipulation

| Tool | Icon | Description | Example |
|------|------|-------------|---------|
| map() | 🗺️ | Transform array | `map(arr, "item", expr)` |
| filter() | 🔍 | Filter elements | `filter(arr, "item", cond)` |
| reduce() | ➖ | Reduce to value | `reduce(arr, "acc", "item", expr)` |
| forEach() | 👉 | Iterate | `forEach(arr, "item", expr)` |
| find() | 🎯 | Find element | `find(arr, "item", cond)` |
| sort() | ↕️ | Sort array | `sort(arr, "a", "b", cmp)` |
| concat() | ➕ | Combine arrays | `concat(arr1, arr2)` |
| slice() | ✂️ | Extract portion | `slice(arr, start, end)` |
| distinct() | ✨ | Remove duplicates | `distinct(arr)` |

### 5. 📝 String Operations (8 items)
String manipulation functions

| Tool | Icon | Description |
|------|------|-------------|
| Concatenate | ➕ | Join strings |
| substring() | ✂️ | Extract substring |
| toUpperCase() | ⬆️ | Convert to uppercase |
| toLowerCase() | ⬇️ | Convert to lowercase |
| split() | ✂️ | Split string |
| replace() | 🔄 | Replace text |
| trim() | ✂️ | Remove whitespace |
| length() | 📏 | Get length |

### 6. 🔢 Math & Numbers (10 items)
Mathematical operations

| Tool | Icon | Description |
|------|------|-------------|
| Addition | ➕ | Add numbers |
| Subtraction | ➖ | Subtract |
| Multiplication | ✖️ | Multiply |
| Division | ➗ | Divide |
| Modulo | % | Remainder |
| round() | 🔘 | Round number |
| max() | ⬆️ | Maximum value |
| min() | ⬇️ | Minimum value |
| abs() | \| | Absolute value |
| sum() | ➕ | Sum array |

### 7. ⚖️ Comparisons (6 items)
Comparison operators

| Tool | Icon | Template |
|------|------|----------|
| Equals | = | `a == b` |
| Not Equals | ≠ | `a != b` |
| Greater Than | > | `a > b` |
| Less Than | < | `a < b` |
| Greater or Equal | ≥ | `a >= b` |
| Less or Equal | ≤ | `a <= b` |

### 8. 🔗 Logical Operators (3 items)
Boolean logic

| Tool | Icon | Template |
|------|------|----------|
| AND | & | `cond1 && cond2` |
| OR | \| | `cond1 \|\| cond2` |
| NOT | ! | `!condition` |

### 9. 🛠️ Utility Functions (7 items)
Helper functions

| Tool | Icon | Description |
|------|------|-------------|
| exists() | ✅ | Check existence |
| isEmpty() | ❌ | Check if empty |
| len() | 📏 | Get length |
| contains() | 🔍 | Check contains |
| groupBy() | 📊 | Group by key |
| limit() | 🎚️ | Limit size |
| skip() | ⏭️ | Skip elements |

### 10. 💬 Comments & Docs (3 items)
Code documentation

| Tool | Icon | Description |
|------|------|-------------|
| Line Comment | /// | Single line |
| Block Comment | /* */ | Multi-line |
| TODO | 📌 | Task marker |

---

## 🎨 How to Use

### Drag and Drop

**Step 1**: Find the tool
- Browse categories OR
- Use search box

**Step 2**: Drag to code
1. Click and hold on any tool
2. Drag to function body textarea
3. Drop at desired location

**Step 3**: Automatic insertion
- Code is inserted with proper indentation
- Cursor positioned for editing
- Template ready to customize

### Example Workflow

**Scenario**: Build a data processing function

1. **Start with function**
   - Click "Add New Function"
   - Name it `processData`
   - Add parameter `items`

2. **Add variable** (from Variables category)
   - Drag "Variable (let)"
   - Drop in body
   - Result: `let variableName = value;`
   - Edit to: `let filtered = [];`

3. **Add filter** (from Array Operations)
   - Drag "filter()"
   - Drop below variable
   - Result: `filter(array, "item", condition)`
   - Edit to: `filtered = filter(items, "item", item.active == true);`

4. **Add return** (from Functions)
   - Drag "Return"
   - Drop at end
   - Result: `return value;`
   - Edit to: `return filtered;`

**Final Result**:
```javascript
function processData(items) {
    let filtered = [];
    filtered = filter(items, "item", item.active == true);
    return filtered;
}
```

---

## ✨ Advanced Features

### Smart Indentation
- Code is automatically indented to match context
- Respects existing indentation level
- Multi-line templates properly formatted

### Cursor Positioning
- After drop, cursor moves to logical editing position
- Ready to customize placeholders
- Tab to navigate between fields

### Visual Feedback
- Drag: Tool shows "grabbing" cursor
- Hover: Textarea highlights with green border
- Drop: Smooth insertion animation

---

## 🔍 Search Tips

### Effective Searches

**By Function Type**:
- "loop" → Shows for, while
- "conditional" → Shows if, ternary, switch
- "array" → Shows all array operations

**By Operation**:
- "transform" → Shows map, filter
- "compare" → Shows all comparisons
- "math" → Shows mathematical operations

**By Use Case**:
- "string manipulation"
- "data transformation"
- "conditional logic"

### Search Shortcuts
- Clear search: Click ✕ button
- Results count displayed
- All matching items shown across categories

---

## 💡 Best Practices

### When to Use Toolbox

✅ **DO use for**:
- Learning Morphium syntax
- Quick code scaffolding
- Discovering available functions
- Reducing typos
- Speeding up development

✅ **ALSO valid**:
- Type code manually
- Copy-paste from examples
- Mix dragging and typing

### Tips for Efficiency

1. **Learn keyboard shortcuts** for frequently used items
2. **Keep popular categories expanded**
3. **Use search for specific operations**
4. **Customize templates after insertion**
5. **Combine multiple tools for complex logic**

---

## 🎯 Category Usage Guide

### For Beginners
Start with these categories:
1. **Variables & Data** - Create data structures
2. **Control Flow** - Add logic
3. **Functions** - Return values
4. **Comments** - Document code

### For Intermediate Users
Explore:
1. **Array Operations** - Transform data
2. **String Operations** - Manipulate text
3. **Comparisons** - Build conditions
4. **Math & Numbers** - Calculations

### For Advanced Users
Master:
1. **Utility Functions** - Advanced operations
2. **Logical Operators** - Complex conditions
3. **Custom combinations** - Build patterns

---

## 🚀 Keyboard & Mouse

### Mouse Actions
| Action | Result |
|--------|--------|
| Click category | Expand/collapse |
| Drag tool | Pick up item |
| Drop in textarea | Insert code |
| Hover over tool | Show description |

### Keyboard
| Key | Action |
|-----|--------|
| Type in search | Filter tools |
| Esc | Clear search |
| Tab | Navigate fields |

---

## 📊 Statistics

**Total Categories**: 10  
**Total Tools**: 70+  
**Search Capability**: Yes  
**Drag & Drop**: Full support  
**Auto-indentation**: Yes  
**Cursor positioning**: Smart  

---

## 🔮 Coming Soon

Planned enhancements:
- [ ] Favorites/Recently used
- [ ] Custom tool templates
- [ ] Keyboard shortcuts
- [ ] Tool snippets library
- [ ] Multi-select drag
- [ ] Tool preview on hover

---

## 💬 Feedback

The toolbox is designed for maximum productivity. All 70+ tools are:
- ✅ Categorized logically
- ✅ Searchable
- ✅ Draggable
- ✅ Auto-formatted
- ✅ Well-documented

**Start dragging and building amazing Morphium functions!** 🎉
