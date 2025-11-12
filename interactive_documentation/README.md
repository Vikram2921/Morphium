# Morphium DSL - Interactive Documentation

Welcome to the complete interactive documentation for Morphium DSL! This guide covers every feature, function, and capability of the Morphium Data Transformation Language.

---

## 📚 Table of Contents

### Getting Started
- [Quick Start Guide](01-quick-start.md) - Get productive in 5 minutes
- [Basic Concepts](02-basic-concepts.md) - Core philosophy and fundamentals
- [Syntax Overview](03-syntax-overview.md) - Complete syntax reference

### Core Features
- [Variables and Scope](04-variables-scope.md) ✅
- [Data Types](05-data-types.md) ✅
- [Operators](06-operators.md) ✅
- [JSON Path Access](07-json-path.md) ✅

### Control Flow
- [If-Else Statements](08-if-else.md) ✅
- [Switch Statements](09-switch.md) ✅
- [For-Of Loops](10-for-of.md) ✅
- [For-In Loops](11-for-in.md) ✅
- [Break & Continue](12-break-continue.md) ✅

### Functions

#### Array Functions
- [map()](functions/map.md) - Transform array elements
- [filter()](functions/filter.md) - Filter array elements
- [reduce()](functions/reduce.md) - Reduce array to single value
- [flatMap()](functions/flatMap.md) - Map and flatten arrays
- [forEach()](functions/forEach.md) - Iterate with side effects
- [distinct()](functions/distinct.md) - Remove duplicates
- [sorted()](functions/sorted.md) - Sort arrays
- [reverse()](functions/reverse.md) - Reverse array order
- [concat()](functions/concat.md) - Concatenate arrays
- [slice()](functions/slice.md) - Extract array slice
- [skip()](functions/skip.md) - Skip first N elements
- [limit()](functions/limit.md) - Take first N elements

#### Matching & Search
- [anyMatch()](functions/anyMatch.md) - Check if any element matches
- [allMatch()](functions/allMatch.md) - Check if all elements match
- [noneMatch()](functions/noneMatch.md) - Check if no elements match
- [findFirst()](functions/findFirst.md) - Find first matching element

#### Aggregation
- [count()](functions/count.md) - Count elements
- [sum()](functions/sum.md) - Sum numeric values
- [avg()](functions/avg.md) - Calculate average
- [min()](functions/min.md) - Find minimum value
- [max()](functions/max.md) - Find maximum value

#### Object Functions
- [merge()](functions/merge.md) - Merge objects
- [pluck()](functions/pluck.md) - Extract field from objects
- [indexBy()](functions/indexBy.md) - Create object indexed by field
- [groupBy()](functions/groupBy.md) - Group elements by key
- [partition()](functions/partition.md) - Split array into two groups
- [keys()](functions/keys.md) - Get object keys
- [values()](functions/values.md) - Get object values
- [entries()](functions/entries.md) - Get key-value pairs
- [get()](functions/get.md) - Get nested value safely
- [set()](functions/set.md) - Set nested value

#### String Functions
- [split()](functions/split.md) - Split string into array
- [join()](functions/join.md) - Join array into string
- [upper()](functions/upper.md) - Convert to uppercase
- [lower()](functions/lower.md) - Convert to lowercase
- [trim()](functions/trim.md) - Remove whitespace
- [replace()](functions/replace.md) - Replace substring

#### Type Conversion
- [toNumber()](functions/toNumber.md) - Convert to number
- [toString()](functions/toString.md) - Convert to string
- [toBool()](functions/toBool.md) - Convert to boolean
- [jsonParse()](functions/jsonParse.md) - Parse JSON string
- [jsonStringify()](functions/jsonStringify.md) - Convert to JSON string

#### Utility Functions
- [exists()](functions/exists.md) - Check if value exists
- [len()](functions/len.md) - Get length of array/string
- [now()](functions/now.md) - Get current timestamp
- [formatDate()](functions/formatDate.md) - Format date string
- [peek()](functions/peek.md) - Debug peek at values

#### Advanced
- [runMorph()](functions/runMorph.md) - Run nested transformation
- [error()](functions/error.md) - Throw custom error ✅
- [log(), logInfo(), logWarn(), logError(), logDebug()](functions/logging.md) - Logging functions ✅

### Advanced Topics
- [User-Defined Functions](13-user-functions.md)
- [Module System](14-modules.md)
- [Dynamic Imports](15-dynamic-imports.md)
- [Error Handling](16-error-handling.md)
- [Logging](17-logging.md)
- [Performance Tips](18-performance.md)

### Integration
- [Java API](19-java-api.md)
- [Custom Functions](20-custom-functions.md)
- [Custom Logger](21-custom-logger.md)

### Examples
- [Real-World Examples](22-real-world-examples.md)
- [Common Patterns](23-common-patterns.md)
- [Migration Guide](24-migration-guide.md)

---

## 🚀 Quick Examples

### Basic Transformation
```javascript
{
  name: $.user.name,
  age: $.user.age,
  email: $.user.contact.email
}
```

### Array Processing
```javascript
{
  activeUsers: filter($.users, "user", user.active),
  names: map($.users, "user", user.name)
}
```

### Control Flow
```javascript
{
  result: for (item of $.items) {
    item.price > 100 ? break : null;
    !item.available ? continue : null;
    {
      name: item.name,
      discount: if (item.category == 'premium') { 0.2 } else { 0.1 }
    }
  }
}
```

### Complex Transformation
```javascript
{
  summary: {
    total: sum(map($.orders, "o", o.amount)),
    average: avg(map($.orders, "o", o.amount)),
    grouped: groupBy($.orders, "o", "status", o)
  }
}
```

---

## 📖 How to Use This Documentation

1. **Start with Quick Start** if you're new to Morphium DSL
2. **Browse by Category** to find specific functions or features
3. **Check Examples** for real-world use cases
4. **Use Search** (Ctrl+F) to find specific topics

Each function documentation includes:
- ✅ Syntax and parameters
- ✅ Description and behavior
- ✅ Multiple examples (basic to advanced)
- ✅ Common use cases
- ✅ Tips and best practices
- ✅ Related functions

---

## 🎯 Key Features

- ✅ **Full Control Flow** - if-else, switch, for-of, for-in, break, continue
- ✅ **Rich Function Library** - 50+ built-in functions
- ✅ **Type Safety** - Type conversion and validation
- ✅ **Error Handling** - Custom errors and logging
- ✅ **Module System** - Import/export with dynamic loading
- ✅ **User Functions** - Define your own functions
- ✅ **High Performance** - Optimized with caching
- ✅ **Java Integration** - Easy to embed and extend

---

## 📦 Installation

```xml
<dependency>
    <groupId>com.morphium</groupId>
    <artifactId>morphium-dsl</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

---

## 🔗 Quick Links

- [GitHub Repository](#)
- [API Javadocs](#)
- [Report Issues](#)
- [Community Forum](#)

---

## 📄 License

Apache License 2.0

---

**Version:** 1.0.0-SNAPSHOT  
**Last Updated:** November 12, 2025

---

Happy Transforming! 🎉
