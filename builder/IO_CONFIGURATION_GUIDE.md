# Input/Output Configuration Guide

## Overview

The Morphium Builder now includes advanced Input/Output configuration capabilities, allowing you to:
- Map JSON input to function parameters
- Configure output format and templates
- Transform data with custom expressions
- Drag and drop connections

## Accessing I/O Configuration

1. Select a function by clicking on its card
2. The function body editor will expand
3. Click the "⚙️ Configure Input/Output" button at the bottom
4. The right panel switches from Code Preview to I/O Configuration

---

## JSON Input Configuration

### Enable JSON Input

1. Click the "📥 JSON Input" tab
2. Toggle "Enable JSON Input" to ON
3. The configuration panel will appear

### Sample JSON Input

**Purpose**: Define a sample JSON structure that will be mapped to function parameters

```json
{
  "id": 1,
  "name": "example",
  "value": 100,
  "metadata": {
    "createdAt": "2024-01-01",
    "author": "user"
  }
}
```

**Features**:
- Click "✓ Validate" to check JSON syntax
- Green checkmark appears for valid JSON
- Red error message shows syntax issues
- Supports nested objects

### JSON to Parameter Mappings

#### Auto-Mapping
1. Define your sample JSON
2. Add parameters to your function
3. Click "🔄 Auto Map"
4. System automatically maps JSON keys to parameters

#### Manual Mapping
1. Click "➕ Add" to create a new mapping
2. Fill in the fields:
   - **JSON Path**: Use JSONPath syntax (e.g., `$.property`, `$.nested.value`)
   - **Parameter**: Select which function parameter to map to
   - **Transform**: Optional expression to transform the value

**Example Mappings**:
```
$.id          → param1
$.name        → param2
$.value       → param3 (Transform: value * 2)
$.metadata.author → param4
```

#### Drag and Drop
- Grab the mapping handle (⋮⋮) to reorder mappings
- Reordering doesn't affect functionality, just organization

#### Testing Mappings
- Click 🧪 icon on any mapping
- See the extracted value from sample JSON
- Verify your JSONPath is correct

#### Available JSON Paths
- Bottom section shows all detected JSON paths
- Click/drag these chips to quickly add paths
- Includes nested properties

---

## Output Configuration

### Enable Output Config

1. Click the "📤 Output Config" tab  
2. Toggle "Enable Output Configuration" to ON
3. Configure output format and mappings

### Output Format Selection

Choose from 4 formats:

#### 📄 JSON
```json
{
  "result": "{{output}}",
  "status": "success"
}
```

#### 📝 XML
```xml
<response>
  <result>{{output}}</result>
  <status>success</status>
</response>
```

#### 📃 Text
```
Result: {{output}}
```

#### ⚙️ Custom
Define your own template with placeholders

### Output Template

**Placeholders**:
- `{{output}}` - Function return value
- `{{functionName}}` - Name of the function
- `{{param1}}`, `{{param2}}` - Function parameters
- Custom variables from mappings

**Example Templates**:

**JSON Response**:
```json
{
  "data": {
    "result": "{{output}}",
    "function": "{{functionName}}",
    "timestamp": "{{timestamp}}"
  },
  "status": "success"
}
```

**CSV Format**:
```
{{param1}},{{param2}},{{output}}
```

**HTML**:
```html
<div class="result">
  <h3>{{functionName}} Result</h3>
  <p>{{output}}</p>
</div>
```

### Output Value Mappings

Map internal values to specific output structure paths.

**Fields**:
- **Source Expression**: Internal value/variable (e.g., `returnValue`, `param1`)
- **Output Path**: Where in output structure (e.g., `$.result.data`)
- **Transform**: Optional transformation expression

**Example Mappings**:
```
returnValue    → $.result.value
param1         → $.input.original
timestamp      → $.metadata.processedAt
```

---

## Complete Workflow Example

### Scenario: User Registration Function

**1. Create Function**
```
Name: registerUser
Parameters: email, password, fullName
Body:
  let user = {
    email: email,
    name: fullName,
    active: true
  };
  return user;
```

**2. Configure JSON Input**

Sample JSON:
```json
{
  "userEmail": "user@example.com",
  "userPassword": "secret123",
  "firstName": "John",
  "lastName": "Doe"
}
```

Mappings:
```
$.userEmail    → email
$.userPassword → password
$.firstName    → fullName (Transform: firstName + " " + lastName)
```

**3. Configure Output**

Format: JSON

Template:
```json
{
  "success": true,
  "user": {{output}},
  "message": "User {{email}} registered successfully"
}
```

Output Mappings:
```
returnValue → $.user
email       → $.userEmail  
timestamp() → $.registeredAt
```

**4. Result**

Input:
```json
{
  "userEmail": "john@example.com",
  "userPassword": "pass123",
  "firstName": "John",
  "lastName": "Doe"
}
```

Output:
```json
{
  "success": true,
  "user": {
    "email": "john@example.com",
    "name": "John Doe",
    "active": true
  },
  "message": "User john@example.com registered successfully",
  "registeredAt": "2024-11-24T13:45:00Z"
}
```

---

## Advanced Features

### JSONPath Syntax

Supported patterns:
```
$               Root
$.property      Direct property
$.nested.prop   Nested property
$[0]            Array index (future)
$.*             Wildcard (future)
```

### Transform Expressions

Common transformations:
```javascript
value * 2           // Multiply
toUpperCase(value)  // String manipulation
value > 100         // Boolean check
default(value, 0)   // Default value
concat(a, " ", b)   // Concatenation
```

### Drag and Drop

**Reordering Mappings**:
1. Click and hold the ⋮⋮ handle
2. Drag to new position
3. Drop to reorder

**Using JSON Path Chips**:
1. Drag chip from "Available JSON Paths"
2. Drop into mapping field
3. Auto-fills the JSONPath

---

## Tips & Best Practices

### JSON Input
✅ **DO**:
- Validate JSON before creating mappings
- Use meaningful JSONPath expressions
- Test mappings with the 🧪 button
- Use transforms for data conversion

❌ **DON'T**:
- Leave invalid JSON (blocks mapping)
- Use spaces in JSONPath
- Skip testing complex mappings

### Output Configuration
✅ **DO**:
- Choose appropriate format for use case
- Use clear, consistent templates
- Map all necessary values
- Test with real data

❌ **DON'T**:
- Mix formats in template
- Forget placeholder syntax
- Ignore escape characters

### Performance
- Keep mappings simple
- Avoid complex transformations
- Use direct paths when possible
- Test with realistic data volumes

---

## Keyboard Shortcuts

| Action | Shortcut |
|--------|----------|
| Switch tabs | Click tab headers |
| Add mapping | Click ➕ button |
| Remove mapping | Click 🗑️ button |
| Test mapping | Click 🧪 button |
| Close config | Click ✕ Close |

---

## Troubleshooting

### JSON Input Issues

**Problem**: "Invalid JSON" error
- **Solution**: Check for missing commas, brackets, quotes
- **Tool**: Use online JSON validator

**Problem**: JSONPath returns null
- **Solution**: Verify path exists in sample JSON
- **Tool**: Use 🧪 test button

**Problem**: Transform doesn't work
- **Solution**: Check expression syntax
- **Tool**: Test with console.log equivalent

### Output Issues

**Problem**: Placeholders not replaced
- **Solution**: Use exact syntax: `{{name}}`
- **Check**: Double braces, no spaces

**Problem**: Format doesn't match template
- **Solution**: Select correct format type
- **Verify**: Template follows format rules

**Problem**: Mapping not appearing in output
- **Solution**: Check output path syntax
- **Verify**: Source expression exists

---

## Integration with Morphium

The I/O configuration generates additional metadata that can be used by:

1. **API Gateway Integration**: Map HTTP requests to function calls
2. **Data Transformation**: Convert between formats
3. **Event Processing**: Handle event payloads
4. **Testing**: Generate test cases from samples

**Generated Code** (conceptual):
```java
// Input mapping
JsonNode input = objectMapper.readTree(jsonString);
String email = input.at("/userEmail").asText();
String password = input.at("/userPassword").asText();

// Function execution  
Object result = function.execute(email, password, fullName);

// Output mapping
JsonNode output = buildOutput(result, template, mappings);
```

---

## Future Enhancements

Planned features:
- Visual flow diagram
- Array mapping support
- Conditional mappings
- Expression builder UI
- Import/export mapping sets
- Mapping templates library
- Real-time preview

---

## Support

For help with I/O configuration:
1. Check the sample JSON is valid
2. Test mappings individually
3. Verify output template syntax
4. Review this guide for examples

**Need more help?** Check the main USER_GUIDE.md or QUICK_REFERENCE.md
