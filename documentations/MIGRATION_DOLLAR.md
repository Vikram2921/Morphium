# Migration to `$` Variable

## Summary

Changed Morphium DSL to use `$` instead of `input` for accessing the root JSON input, making it more concise and familiar to developers who use JSONata and similar DSLs.

## What Changed

### 1. Core Engine
- **File**: `MorphiumEngine.java`
- **Change**: Now defines `$` instead of `input` in the evaluation context
- **Code**: `evalContext.define("$", input);`

### 2. All Tests Updated
- **Files**: 
  - `MorphiumEngineTest.java` (11 tests)
  - `UserDefinedFunctionsTest.java` (11 tests)
- **Change**: All test transforms now use `$` instead of `input`
- **New Tests**: Added 2 new tests demonstrating `$` assignment to variables

### 3. Playground Examples
- **File**: `PlaygroundHtml.java`
- **Change**: All 6 example transforms updated to use `$`
- **New Example**: Added 7th example showing variable assignment with `$`

### 4. Documentation
- **File**: `README.md`
- **Change**: Updated examples and added section explaining `$` variable

## Before and After

### Before (using `input`)
```javascript
{
  fullName: input.person.first + " " + input.person.last,
  age: input.person.age
}
```

### After (using `$`)
```javascript
{
  fullName: $.person.first + " " + $.person.last,
  age: $.person.age
}
```

## Variable Assignment Examples

### Direct Usage
```javascript
{ name: $.person.name }
```

### Assign to Local Variable
```javascript
let root = $
let person = root.person
{ fullName: person.first + " " + person.last }
```

### Extract Sub-Objects
```javascript
let root = $
let config = root.config
let data = root.data

{
  status: config.enabled ? "Active" : "Inactive",
  items: map(data.items, "item", item.value * config.multiplier)
}
```

### Global Variables Work Too
```javascript
global root = $
function getConfig() {
  return root.config
}
```

## Benefits

1. **Shorter Syntax**: `$.name` vs `input.name` (saves 4 characters per access)
2. **Industry Standard**: Similar to JSONata (`$`) and jq (`$`)
3. **More Intuitive**: `$` suggests "root" or "current" in many languages
4. **Easier to Type**: No shift key needed for `$`
5. **Flexible**: Can assign to variables: `let root = $`

## Backward Compatibility

⚠️ **Breaking Change**: This is a breaking change. Old transforms using `input` will no longer work and must be updated to use `$`.

To migrate existing transforms, simply replace all occurrences of `input` with `$`:
```bash
# Simple find and replace
sed -i 's/\binput\b/$/g' your-transform.morph
```

## Test Results

✅ All 22 tests pass:
- 11 MorphiumEngineTest
- 11 UserDefinedFunctionsTest (including 2 new tests for `$` assignment)

## Playground

The interactive playground now includes:
- 7 examples (all using `$`)
- New "$ Variables" example showing assignment patterns
- Updated default transform on page load

Try it:
```bash
mvn compile exec:java
# Open http://localhost:8080
```

## Technical Notes

### Lexer Support
The lexer already supported `$` in identifiers:
```java
private boolean isAlpha(char c) {
    return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_' || c == '$';
}
```

No parser changes were needed - `$` is just another identifier.

### Scoping
`$` follows normal variable scoping rules:
- Defined in root context
- Can be shadowed in inner scopes
- Can be assigned to other variables
- Accessible in functions (via closure)

## Migration Checklist

- [x] Update MorphiumEngine to define `$`
- [x] Update all unit tests
- [x] Add tests for variable assignment
- [x] Update playground examples
- [x] Add new playground example
- [x] Update README documentation
- [x] All tests passing (22/22)
- [x] Playground working correctly

## Ready to Use!

The migration is complete and all systems are working with the new `$` syntax.
