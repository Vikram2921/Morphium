# Complete Documentation Index

This is a comprehensive index of all Morphium DSL documentation with learning paths and quick navigation.

---

## 🚀 Quick Navigation

### By Topic
- [Getting Started](#getting-started)
- [Core Concepts](#core-concepts)
- [Functions](#functions)
- [Control Flow](#control-flow)
- [Advanced Topics](#advanced-topics)

### By Level
- [Beginner](#beginner-track) - New to Morphium
- [Intermediate](#intermediate-track) - Familiar with basics
- [Advanced](#advanced-track) - Master level

---

## 📚 Complete Documentation Index

### Getting Started

1. **[Quick Start Guide](01-quick-start.md)** ⚡ `5 min`
   - Your first Morphium script
   - Basic transformations
   - Working with arrays
   - Filtering and mapping
   - Aggregation basics
   - **Start here if:** You're brand new to Morphium

2. **[Basic Concepts](02-basic-concepts.md)** 📖 `30 min`
   - What is Morphium DSL?
   - Core philosophy (immutability, composition, readability)
   - Data flow model and pipelines
   - Variables and assignment
   - Data types (numbers, strings, booleans, arrays, objects)
   - Comments (single-line and multi-line)
   - Expressions vs statements
   - Function chaining
   - Error handling with error()
   - Best practices
   - **Start here if:** You want to understand the fundamentals

3. **[Syntax Overview](03-syntax-overview.md)** 📝 `30 min`
   - Variables and naming conventions
   - All operators (arithmetic, comparison, logical)
   - Literals (numbers, strings, booleans, null)
   - Arrays (creation, indexing, nesting)
   - Objects (creation, nesting, property access)
   - Lambda expressions (single and block)
   - Control flow syntax
   - Method calls and chaining
   - Property access (dot and bracket notation)
   - Spread operator (objects and arrays)
   - String interpolation
   - Operator precedence
   - Syntax rules and formatting
   - **Start here if:** You need a complete syntax reference

4. **[Function Reference](FUNCTION_REFERENCE.md)** 📚 `15 min browse, 1 hour study`
   - Quick reference for all 50+ functions
   - Organized by category
   - Syntax and examples for each
   - **Categories:**
     - Array Functions (map, filter, reduce, etc.)
     - String Functions (split, join, upper, etc.)
     - Object Functions (merge, pluck, groupBy, etc.)
     - Aggregation (count, sum, avg, min, max)
     - Matching (anyMatch, allMatch, noneMatch, findFirst)
     - Type Conversion (toNumber, toString, etc.)
     - Utilities (exists, len, now, formatDate)
     - Logging (log, logInfo, logWarn, logError)
   - **Start here if:** You need to look up a specific function

5. **[Control Flow Guide](08-if-else.md)** 🔀 `30 min`
   - If-else statements and expressions
   - Switch statements (with and without break)
   - For-of loops (iterate over arrays)
   - For-in loops (iterate with index)
   - Break and continue
   - Loop control patterns
   - **Start here if:** You need conditionals and loops

---

## 📂 Detailed Function Documentation

### Core Array Functions

1. **[map()](functions/map.md)** `10 min`
   - Transform every element in an array
   - 7 examples from basic to advanced
   - Common patterns and use cases
   - Performance tips

2. **[filter()](functions/filter.md)** `10 min`
   - Select elements that match a condition
   - 7 examples including complex filtering
   - Combining multiple conditions
   - Performance optimization

3. **[reduce()](functions/reduce.md)** `10 min`
   - Reduce array to a single value
   - 7 examples from sum to complex objects
   - Building objects and arrays
   - Advanced patterns

---

## 🎓 Learning Paths

### Beginner Track (~1.5 hours to productivity)

**Goal:** Write basic transformation scripts

1. [Quick Start Guide](01-quick-start.md) - 15 min
   - Learn by doing with simple examples
   
2. [Basic Concepts](02-basic-concepts.md) - 30 min
   - Understand core philosophy
   - Variables and data types
   - Function chaining basics
   
3. [Syntax Overview](03-syntax-overview.md) - 30 min
   - Operators and expressions
   - Arrays and objects
   - Lambda functions
   
4. [Function Reference](FUNCTION_REFERENCE.md) - 15 min
   - Focus on: map, filter, reduce, count, sum
   - Array and string basics

**Practice Project:** Transform a list of user objects, filter by criteria, calculate statistics

---

### Intermediate Track (~2 hours to proficiency)

**Goal:** Write production-ready transformation scripts

1. [Syntax Overview](03-syntax-overview.md) - Deep dive - 30 min
   - All operators and precedence
   - Advanced lambda patterns
   - Property access patterns
   
2. [Function Reference](FUNCTION_REFERENCE.md) - 30 min
   - Study all array functions
   - Object manipulation
   - String processing
   
3. [Control Flow](08-if-else.md) - 30 min
   - If-else and switch
   - Loops and iteration
   - Break and continue
   
4. [Detailed Functions](functions/) - 30 min
   - map(), filter(), reduce() in depth
   - Advanced patterns
   - Performance tips

**Practice Project:** ETL pipeline that cleanses, transforms, groups, and aggregates complex data

---

### Advanced Track (~4 hours to mastery)

**Goal:** Optimize performance and handle complex scenarios

1. All Beginner + Intermediate content - Review

2. [Function Reference](FUNCTION_REFERENCE.md) - Complete - 1 hour
   - All 50+ functions
   - Advanced combinations
   
3. [Detailed Functions](functions/) - Deep dive - 1 hour
   - All patterns and edge cases
   - Performance optimization
   - Memory efficiency
   
4. Advanced Patterns - 2 hours
   - Complex aggregations
   - Multi-step pipelines
   - Error handling strategies
   - Dynamic script generation
   - Custom import functions

**Practice Project:** Build a complete data processing system with error handling, logging, and performance monitoring

---

## 📖 Documentation by Category

### Data Transformation

**Core Operations:**
- [map()](functions/map.md) - Transform elements
- [filter()](functions/filter.md) - Select elements
- [reduce()](functions/reduce.md) - Aggregate values
- [flatMap()](FUNCTION_REFERENCE.md#flatmap) - Map and flatten

**Array Manipulation:**
- [concat()](FUNCTION_REFERENCE.md#concat) - Join arrays
- [slice()](FUNCTION_REFERENCE.md#slice) - Extract portion
- [reverse()](FUNCTION_REFERENCE.md#reverse) - Reverse order
- [distinct()](FUNCTION_REFERENCE.md#distinct) - Remove duplicates
- [sorted()](FUNCTION_REFERENCE.md#sorted) - Sort elements

### Data Extraction

**Object Operations:**
- [pluck()](FUNCTION_REFERENCE.md#pluck) - Extract property from objects
- [keys()](FUNCTION_REFERENCE.md#keys) - Get object keys
- [values()](FUNCTION_REFERENCE.md#values) - Get object values
- [entries()](FUNCTION_REFERENCE.md#entries) - Get key-value pairs
- [get()](FUNCTION_REFERENCE.md#get) - Safe property access

**Array Search:**
- [findFirst()](FUNCTION_REFERENCE.md#findfirst) - Find first match
- [anyMatch()](FUNCTION_REFERENCE.md#anymatch) - Check existence
- [allMatch()](FUNCTION_REFERENCE.md#allmatch) - Check all
- [noneMatch()](FUNCTION_REFERENCE.md#nonematch) - Check none

### Data Aggregation

**Statistical Functions:**
- [count()](FUNCTION_REFERENCE.md#count) - Count elements
- [sum()](FUNCTION_REFERENCE.md#sum) - Sum numbers
- [avg()](FUNCTION_REFERENCE.md#avg) - Calculate average
- [min()](FUNCTION_REFERENCE.md#min) - Find minimum
- [max()](FUNCTION_REFERENCE.md#max) - Find maximum

**Grouping:**
- [groupBy()](FUNCTION_REFERENCE.md#groupby) - Group by key
- [indexBy()](FUNCTION_REFERENCE.md#indexby) - Index by unique key
- [partition()](FUNCTION_REFERENCE.md#partition) - Split by condition

### String Processing

- [split()](FUNCTION_REFERENCE.md#split) - Split string to array
- [join()](FUNCTION_REFERENCE.md#join) - Join array to string
- [upper()](FUNCTION_REFERENCE.md#upper) - Convert to uppercase
- [lower()](FUNCTION_REFERENCE.md#lower) - Convert to lowercase
- [trim()](FUNCTION_REFERENCE.md#trim) - Remove whitespace
- [replace()](FUNCTION_REFERENCE.md#replace) - Replace substring

### Type Conversion

- [toNumber()](FUNCTION_REFERENCE.md#tonumber) - Convert to number
- [toString()](FUNCTION_REFERENCE.md#tostring) - Convert to string
- [toBool()](FUNCTION_REFERENCE.md#tobool) - Convert to boolean
- [jsonParse()](FUNCTION_REFERENCE.md#jsonparse) - Parse JSON
- [jsonStringify()](FUNCTION_REFERENCE.md#jsonstringify) - Stringify to JSON

### Utilities

- [len()](FUNCTION_REFERENCE.md#len) - Get length
- [exists()](FUNCTION_REFERENCE.md#exists) - Check existence
- [now()](FUNCTION_REFERENCE.md#now) - Current timestamp
- [formatDate()](FUNCTION_REFERENCE.md#formatdate) - Format date

### Debugging & Logging

- [log()](FUNCTION_REFERENCE.md#log) - General logging
- [logInfo()](FUNCTION_REFERENCE.md#loginfo) - Info messages
- [logWarn()](FUNCTION_REFERENCE.md#logwarn) - Warnings
- [logError()](FUNCTION_REFERENCE.md#logerror) - Errors
- [logDebug()](FUNCTION_REFERENCE.md#logdebug) - Debug info
- [peek()](FUNCTION_REFERENCE.md#peek) - Debug in chains
- [error()](FUNCTION_REFERENCE.md#error) - Throw errors

---

## 🔍 Find Documentation By Use Case

### "I want to..."

**Transform Data:**
- Filter and select → [filter()](functions/filter.md)
- Transform elements → [map()](functions/map.md)
- Flatten nested arrays → [flatMap()](FUNCTION_REFERENCE.md#flatmap)
- Remove duplicates → [distinct()](FUNCTION_REFERENCE.md#distinct)
- Sort data → [sorted()](FUNCTION_REFERENCE.md#sorted)

**Aggregate Data:**
- Count items → [count()](FUNCTION_REFERENCE.md#count)
- Sum values → [sum()](FUNCTION_REFERENCE.md#sum) or [reduce()](functions/reduce.md)
- Calculate average → [avg()](FUNCTION_REFERENCE.md#avg)
- Find min/max → [min()](FUNCTION_REFERENCE.md#min) / [max()](FUNCTION_REFERENCE.md#max)
- Group by key → [groupBy()](FUNCTION_REFERENCE.md#groupby)

**Search Data:**
- Find specific item → [findFirst()](FUNCTION_REFERENCE.md#findfirst)
- Check if exists → [anyMatch()](FUNCTION_REFERENCE.md#anymatch)
- Check if all match → [allMatch()](FUNCTION_REFERENCE.md#allmatch)

**Manipulate Strings:**
- Split into array → [split()](FUNCTION_REFERENCE.md#split)
- Join from array → [join()](FUNCTION_REFERENCE.md#join)
- Change case → [upper()](FUNCTION_REFERENCE.md#upper) / [lower()](FUNCTION_REFERENCE.md#lower)
- Find and replace → [replace()](FUNCTION_REFERENCE.md#replace)

**Work with Objects:**
- Extract values → [pluck()](FUNCTION_REFERENCE.md#pluck)
- Merge objects → [merge()](FUNCTION_REFERENCE.md#merge)
- Get keys/values → [keys()](FUNCTION_REFERENCE.md#keys) / [values()](FUNCTION_REFERENCE.md#values)
- Safe access → [get()](FUNCTION_REFERENCE.md#get)

**Control Logic:**
- Conditionals → [If-Else Guide](08-if-else.md)
- Multiple conditions → [Switch Guide](08-if-else.md#switch-statements)
- Iterate arrays → [For-Of Loop](08-if-else.md#for-of-loops)
- Iterate with index → [For-In Loop](08-if-else.md#for-in-loops)

**Debug Issues:**
- Add logging → [log()](FUNCTION_REFERENCE.md#log)
- Debug chains → [peek()](FUNCTION_REFERENCE.md#peek)
- Handle errors → [error()](FUNCTION_REFERENCE.md#error)

---

## 📊 Documentation Statistics

- **Total Documentation Files:** 18
- **Core Guides:** 5
- **Detailed Function Docs:** 3 (map, filter, reduce)
- **Quick Reference Functions:** 50+
- **Code Examples:** 150+
- **Total Content:** ~80 KB

---

## 🗺️ Suggested Reading Order

### For Quick Results (30 minutes)
1. [Quick Start](01-quick-start.md)
2. [Function Reference](FUNCTION_REFERENCE.md) - Skim for needed functions
3. Start coding!

### For Solid Foundation (2 hours)
1. [Quick Start](01-quick-start.md)
2. [Basic Concepts](02-basic-concepts.md)
3. [Syntax Overview](03-syntax-overview.md)
4. [Function Reference](FUNCTION_REFERENCE.md)
5. [Control Flow](08-if-else.md)

### For Complete Mastery (4+ hours)
1. All core guides in order
2. All detailed function documentation
3. Practice with real-world examples
4. Build a complete project

---

## 🔖 Bookmarks for Common Tasks

### Daily Reference
- [Function Reference](FUNCTION_REFERENCE.md) - Look up function syntax
- [Syntax Overview](03-syntax-overview.md) - Check operator precedence
- [Control Flow](08-if-else.md) - Loop and conditional syntax

### Learning & Examples
- [Quick Start](01-quick-start.md) - Simple working examples
- [map()](functions/map.md) - Transform patterns
- [filter()](functions/filter.md) - Filtering patterns
- [reduce()](functions/reduce.md) - Aggregation patterns

### Deep Dives
- [Basic Concepts](02-basic-concepts.md) - Philosophy and best practices
- [Syntax Overview](03-syntax-overview.md) - Complete language reference

---

## 📝 Contributing to Documentation

Found an error or want to improve the docs? Here's how:

1. Documentation lives in `interactive_documentation/`
2. Each file is a self-contained guide
3. Follow the existing structure and style
4. Include practical examples
5. Cross-link related topics

---

## 🆘 Getting Help

### Can't find what you need?

1. **Search this index** - Use Ctrl+F to find keywords
2. **Check Function Reference** - All functions listed with examples
3. **Read Quick Start** - Covers most common scenarios
4. **Review examples** - Detailed docs have 7+ examples each

### Still stuck?

- Check the main [README](README.md)
- Review [Basic Concepts](02-basic-concepts.md) for fundamentals
- Look at [Syntax Overview](03-syntax-overview.md) for language details

---

## Navigation

- [Main README](README.md)
- [Quick Start](01-quick-start.md)
- [Function Reference](FUNCTION_REFERENCE.md)

---

*Last updated: 2025-01-12*
*Total documentation: 18 files, 150+ examples*
