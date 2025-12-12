# 🎨 Morphium Builder - Visual Guide

## Interface Overview

The Morphium Builder provides a clean, modern interface divided into three main sections:

### 1. Header Section
```
┌──────────────────────────────────────────────────────────────┐
│ 🔮 Morphium Builder                                          │
│                        [filename.morph] [New] [Import] [Export]│
└──────────────────────────────────────────────────────────────┘
```
- **Title**: Branding with gradient purple background
- **Filename Input**: Edit your file name
- **Action Buttons**: New, Import, Export

---

### 2. Three-Panel Layout

```
┌─────────────┬────────────────────────────┬─────────────────┐
│             │                            │                 │
│  TOOLBOX    │         CANVAS             │    PREVIEW      │
│  (250px)    │        (Flexible)          │    (400px)      │
│             │                            │                 │
│  [Elements] │   [Function Cards]         │  [Live Code]    │
│             │                            │                 │
│  [Add Btn]  │   [Empty State]            │  [Copy Btn]     │
│             │                            │                 │
└─────────────┴────────────────────────────┴─────────────────┘
```

---

### 3. Toolbox (Left Sidebar)

```
┏━━━━━━━━━━━━━━━┓
┃   Toolbox     ┃
┣━━━━━━━━━━━━━━━┫
┃               ┃
┃ ⚡ New Function┃
┃ ❓ If Statement┃
┃ ↩️  Return     ┃
┃ 📦 Variable    ┃
┃ ➕ Expression  ┃
┃               ┃
┃ ┌───────────┐ ┃
┃ │➕ Add Func│ ┃
┃ └───────────┘ ┃
┗━━━━━━━━━━━━━━━┛
```

**Visual Elements:**
- Dashed borders (draggable style)
- Icons for each element type
- Hover effects (shifts right)
- Big "Add Function" button

---

### 4. Canvas (Center Area)

```
┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃ Functions (3)                    ┃
┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫
┃                                  ┃
┃  ┌────────────────────────────┐  ┃
┃  │ ⋮⋮  add              🗑️   │  ┃  ← Collapsed
┃  │ Parameters: a, b           │  ┃
┃  └────────────────────────────┘  ┃
┃                                  ┃
┃  ┌────────────────────────────┐  ┃
┃  │ ⋮⋮  multiply         🗑️   │  ┃  ← Selected
┃  │ Parameters: x, y           │  ┃  (Expanded)
┃  │ ┌────────────────────────┐ │  ┃
┃  │ │ Body:                  │ │  ┃
┃  │ │ return x * y;          │ │  ┃
┃  │ │                        │ │  ┃
┃  │ └────────────────────────┘ │  ┃
┃  └────────────────────────────┘  ┃
┃                                  ┃
┃  ┌────────────────────────────┐  ┃
┃  │ ⋮⋮  round            🗑️   │  ┃  ← Collapsed
┃  │ Parameters: num, decimals  │  ┃
┃  └────────────────────────────┘  ┃
┃                                  ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛
```

**Function Card States:**
- **Normal**: White background, gray border
- **Hover**: Purple border, shadow
- **Selected**: Purple border, light purple background
- **Dragging**: Elevated shadow, slight opacity

---

### 5. Preview Panel (Right Sidebar)

```
┏━━━━━━━━━━━━━━━━━━━━━┓
┃ Code Preview   📋  ┃
┣━━━━━━━━━━━━━━━━━━━━━┫
┃                     ┃
┃ function add(a, b) {┃
┃     return a + b;   ┃
┃ }                   ┃
┃                     ┃
┃ function multiply(  ┃
┃   x, y              ┃
┃ ) {                 ┃
┃     return x * y;   ┃
┃ }                   ┃
┃                     ┃
┃ function round(     ┃
┃   num, decimals     ┃
┃ ) {                 ┃
┃     if (decimals... ┃
┃         return ...  ┃
┃     }               ┃
┃     return num;     ┃
┃ }                   ┃
┃                     ┃
┗━━━━━━━━━━━━━━━━━━━━━┛
```

**Features:**
- Monospace font
- Auto-formatting
- Copy button
- Scrollable content

---

## Visual Interactions

### Drag and Drop

```
Before:
┌──────────┐
│ Function A│
├──────────┤
│ Function B│  ← Grab handle ⋮⋮
├──────────┤
│ Function C│
└──────────┘

During Drag:
┌──────────┐
│ Function A│
├──────────┤
│ Function C│
├──────────┤  ← Drop zone
│ [Dragging]│  ← Semi-transparent
└──────────┘

After:
┌──────────┐
│ Function A│
├──────────┤
│ Function C│
├──────────┤
│ Function B│  ← New position!
└──────────┘
```

---

### Editing Flow

```
Step 1: Click Function Card
┌────────────────┐
│ ⋮⋮  add    🗑️ │ ← Click!
│ Params: a, b   │
└────────────────┘

Step 2: Expands
┌────────────────────┐
│ ⋮⋮  add        🗑️ │
│ Params: a, b       │
│ ┌────────────────┐ │
│ │ Body:          │ │ ← Edit here
│ │ return a + b;  │ │
│ └────────────────┘ │
└────────────────────┘

Step 3: Preview Updates
┌─────────────────┐
│ Code Preview 📋│
├─────────────────┤
│ function add(   │ ← Auto-generated!
│   a, b          │
│ ) {             │
│   return a + b; │
│ }               │
└─────────────────┘
```

---

## Color Scheme

### Primary Colors
```
Header Background:  ████████  Linear Gradient
                    #667eea → #764ba2
                    
Primary Buttons:    ████████  #667eea (Purple)

Function Border:    ════════  #667eea (Selected)
                    --------  #e0e0e0 (Normal)

Background:         ░░░░░░░░  #f5f5f5 (Canvas)
                    ████████  #ffffff (Cards)
```

### Semantic Colors
```
Success/Primary:    ████████  #667eea
Danger/Delete:      ████████  #dc3545
Text Primary:       ████████  #333333
Text Secondary:     ████████  #666666
Border:             --------  #e0e0e0
Shadow:             ▓▓▓▓▓▓▓▓  rgba(0,0,0,0.1)
```

---

## Responsive Behavior

### Desktop (1920px)
```
[Toolbox: 250px][       Canvas: ~1270px      ][Preview: 400px]
```

### Laptop (1366px)
```
[Toolbox: 250px][   Canvas: ~716px   ][Preview: 400px]
```

### Tablet (768px) - Future
```
[Toolbox: Hidden]
[     Canvas + Preview Stacked     ]
```

---

## Animation Effects

### Hover States
- **Buttons**: Lift up (-1px) + shadow
- **Tool Items**: Slide right (4px) + color change
- **Function Cards**: Border glow + shadow

### Drag States
- **Start**: Opacity 0.8 + shadow increase
- **Move**: Follow cursor smoothly
- **Drop**: Animate to position (250ms)

### Transitions
```css
All Interactive Elements:
  transition: all 0.2s ease;

Drag Animations:
  transition: transform 250ms cubic-bezier(0, 0, 0.2, 1);
```

---

## Icons Used

| Icon | Element | Unicode |
|------|---------|---------|
| 🔮 | Logo | U+1F52E |
| ⚡ | Function | U+26A1 |
| ❓ | If Statement | U+2753 |
| ↩️ | Return | U+21A9 |
| 📦 | Variable | U+1F4E6 |
| ➕ | Expression/Add | U+2795 |
| ⋮⋮ | Drag Handle | U+22EE |
| 🗑️ | Delete | U+1F5D1 |
| 📋 | Copy | U+1F4CB |

---

## User Journey

```
1. Open App
   └─→ See empty canvas with "Add Function" prompt

2. Add Function
   └─→ New card appears with default values

3. Click Card
   └─→ Expands to show body editor

4. Edit Details
   └─→ Name, params, body all editable
   └─→ Preview updates in real-time

5. Add More Functions
   └─→ Stack vertically in canvas

6. Reorder (Optional)
   └─→ Drag by handle to reposition

7. Export
   └─→ Set filename → Click Export → Download!
```

---

## Visual Feedback

### Success States
- File imported: ✅ Functions appear
- File exported: ✅ Download starts
- Function added: ✅ New card appears

### Interactive States
- Hovering button: Shadow + lift
- Selecting function: Purple border + background
- Dragging: Elevated shadow + opacity
- Typing: Border highlight on focus

### Empty States
```
┌──────────────────────────────────┐
│                                  │
│   No functions yet.              │
│   Click "Add Function" to start! │
│                                  │
└──────────────────────────────────┘
```

---

**The interface is designed to be intuitive, modern, and efficient for creating Morphium files!**
