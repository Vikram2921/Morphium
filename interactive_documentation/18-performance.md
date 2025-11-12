# Performance Optimization Guide

Comprehensive guide to optimizing Morphium DSL transformations for maximum performance.

---

## Overview

Morphium DSL is designed for high performance with several built-in optimizations:
- Parser caching for repeated transformations
- Context pooling for reduced GC pressure
- Optimized function dispatch
- Lazy evaluation where possible
- Efficient JSON processing with Jackson

---

## Built-In Optimizations

### 1. Parser Caching

**Description**: Parsed expressions are automatically cached based on script content and source path.

**Benefit**: 10-100x faster for repeated transformations with the same script.

**Example**:
```java
MorphiumEngine engine = new MorphiumEngine();
String script = "map($.users, \"u\", u.name)";

// First call: Parses and caches
JsonNode result1 = engine.transformFromString(script, input1);

// Subsequent calls: Reuses parsed AST
JsonNode result2 = engine.transformFromString(script, input2); // Much faster!
JsonNode result3 = engine.transformFromString(script, input3); // Much faster!
```

**Cache Key**: `sourcePath:scriptHashCode`

### 2. Context Reuse

**Description**: Child contexts reuse parent context data without copying.

**Benefit**: 50-70% reduction in GC pressure for large array operations.

**Example**:
```javascript
// Efficient - contexts are reused across iterations
for (item of largeArray) {
  // New context created but reuses parent data
  transform(item)
}
```

### 3. Function Dispatch Optimization

**Description**: Functions are looked up via HashMap for O(1) access.

**Types**:
- **Stream Functions**: Receive unevaluated expressions (map, filter, reduce)
- **Eager Functions**: Receive pre-evaluated arguments (merge, len, upper)

### 4. Module Caching

**Description**: Imported modules are parsed once and cached.

**Benefit**: Fast module reuse across transformations.

```javascript
// Module parsed and cached on first import
import "utils.morph" as utils;

// Subsequent imports use cached version
import "utils.morph" as u;
```

---

## Optimization Techniques

### 1. Use Built-In Functions

✅ **Optimized**:
```javascript
map($.items, "item", item.price * 2)
```

❌ **Slower**:
```javascript
for (item of $.items) {
  item.price * 2
}
```

**Why**: Built-in functions are optimized native implementations.

### 2. Minimize Array Iterations

✅ **Single Pass**:
```javascript
map(filter($.items, "i", i.active), "i", i.name)
```

❌ **Multiple Passes**:
```javascript
let active = filter($.items, "i", i.active);
let names = map(active, "i", i.name);
names
```

**Why**: Combining operations reduces intermediate array allocations.

### 3. Use Early Exit with break

✅ **Efficient**:
```javascript
for (item of $.items) {
  item.value > 1000 ? break : null;
  process(item)
}
```

❌ **Processes All**:
```javascript
filter($.items, "i", i.value <= 1000)
```

**When**: Use when you don't need all results and can exit early.

### 4. Avoid Unnecessary Transformations

✅ **Direct Access**:
```javascript
{
  id: $.user.id,
  name: $.user.name
}
```

❌ **Unnecessary Variable**:
```javascript
let user = $.user;
let id = user.id;
let name = user.name;
{
  id: id,
  name: name
}
```

### 5. Cache Expensive Computations

✅ **Cached**:
```javascript
let taxRate = 0.08;
let items = $.items;

{
  totals: map(items, "i", i.price * (1 + taxRate))
}
```

❌ **Recomputed**:
```javascript
{
  totals: map($.items, "i", i.price * 1.08)
}
```

**Why**: Variables are cheaper than repeated calculations.

### 6. Use Appropriate Data Structures

✅ **indexBy for Lookups**:
```javascript
let userMap = indexBy($.users, "u", "id", u);
// O(1) lookup
userMap[userId]
```

❌ **Linear Search**:
```javascript
// O(n) lookup every time
findFirst($.users, "u", u.id == userId)
```

### 7. Batch Operations

✅ **Batched**:
```javascript
{
  stats: {
    count: len($.items),
    total: sum(map($.items, "i", i.price)),
    avg: avg(map($.items, "i", i.price))
  }
}
```

Better yet:
```javascript
let prices = map($.items, "i", i.price);
{
  stats: {
    count: len(prices),
    total: sum(prices),
    avg: avg(prices)
  }
}
```

---

## Performance Patterns

### Pattern 1: Pipeline Optimization

```javascript
// Efficient pipeline
let result = limit(
  sorted(
    filter(
      map($.items, "i", { name: i.name, score: i.value * 2 }),
      "i", 
      i.score > 50
    )
  ),
  10
);
```

### Pattern 2: Early Filtering

```javascript
// Filter early, transform later
let active = filter($.users, "u", u.active && u.verified);
let transformed = map(active, "u", expensiveTransform(u));
```

### Pattern 3: Avoid Deep Nesting

❌ **Deeply Nested**:
```javascript
map($.orders, "o",
  map(o.items, "i",
    map(i.tags, "t",
      upper(t)
    )
  )
)
```

✅ **Flattened**:
```javascript
flatMap($.orders, "o",
  flatMap(o.items, "i",
    map(i.tags, "t", upper(t))
  )
)
```

### Pattern 4: Conditional Execution

```javascript
// Skip expensive operations when not needed
let needsProcessing = $.config.enableProcessing;

{
  result: needsProcessing 
    ? expensiveTransformation($.data)
    : $.data
}
```

---

## Anti-Patterns to Avoid

### ❌ 1. Repeated Expensive Calls

```javascript
// BAD: Calls len() in every iteration
for (i in $.items) {
  i < len($.items) - 1 ? doSomething() : null;
}

// GOOD: Cache the length
let lastIndex = len($.items) - 1;
for (i in $.items) {
  i < lastIndex ? doSomething() : null;
}
```

### ❌ 2. Unnecessary Deep Copies

```javascript
// BAD: Creating copies
let copy = merge({}, $.user);
{
  name: copy.name,
  age: copy.age
}

// GOOD: Direct access
{
  name: $.user.name,
  age: $.user.age
}
```

### ❌ 3. Excessive Logging in Loops

```javascript
// BAD: Logs every iteration
for (item of largeArray) {
  logDebug("Processing:", item.id);
  process(item)
}

// GOOD: Log summary
log("Processing", len(largeArray), "items");
let result = map(largeArray, "i", process(i));
log("Completed processing");
```

### ❌ 4. String Concatenation in Loops

```javascript
// BAD: String concatenation
global result = "";
for (item of $.items) {
  global result = result + item.name + ",";
}

// GOOD: Use join
join(map($.items, "i", i.name), ",")
```

---

## Benchmarking Guide

### Simple Performance Test

```java
MorphiumEngine engine = new MorphiumEngine();
String script = "map($.items, \"i\", i.value * 2)";
JsonNode input = createTestInput(10000); // 10K items

// Warm up JVM
for (int i = 0; i < 100; i++) {
    engine.transformFromString(script, input);
}

// Measure
long start = System.nanoTime();
for (int i = 0; i < 1000; i++) {
    engine.transformFromString(script, input);
}
long elapsed = System.nanoTime() - start;

System.out.println("Avg time: " + (elapsed / 1000.0 / 1000) + " μs");
```

### Comparing Approaches

```java
// Approach 1: Built-in map
String script1 = "map($.items, \"i\", i.value * 2)";

// Approach 2: For-of loop
String script2 = "for (i of $.items) { i.value * 2 }";

// Benchmark both and compare
```

---

## Memory Optimization

### 1. Stream Large Arrays

For very large arrays (100K+ elements), consider processing in chunks:

```javascript
let chunkSize = 1000;
let totalItems = len($.items);

{
  chunks: for (i in $.items) {
    i % chunkSize != 0 ? continue : null;
    i + chunkSize > totalItems ? break : null;
    
    processChunk(slice($.items, i, i + chunkSize))
  }
}
```

### 2. Limit Result Size

```javascript
// Don't return unnecessary data
{
  summary: {
    count: count($.items),
    total: sum(map($.items, "i", i.value))
  }
  // Don't include: items: $.items  (if not needed)
}
```

### 3. Use Aggregations

```javascript
// Instead of returning all items
let items = filter($.items, "i", i.active);

// Return aggregated data
{
  activeCount: len(items),
  totalValue: sum(map(items, "i", i.value)),
  avgValue: avg(map(items, "i", i.value))
}
```

---

## Java Integration Performance

### Reuse Engine Instance

✅ **Good**:
```java
// Create once, reuse many times
MorphiumEngine engine = new MorphiumEngine();

for (JsonNode input : inputs) {
    JsonNode result = engine.transformFromString(script, input);
}
```

❌ **Bad**:
```java
// Creating new engine each time
for (JsonNode input : inputs) {
    MorphiumEngine engine = new MorphiumEngine();
    JsonNode result = engine.transformFromString(script, input);
}
```

### Pre-Register Functions

```java
MorphiumEngine engine = new MorphiumEngine();

// Register once
engine.registerFunction("custom", "validate", args -> {
    // validation logic
});

// Use many times
for (JsonNode input : inputs) {
    engine.transformFromString(script, input);
}
```

---

## Performance Checklist

### ✅ Do:
- [ ] Reuse MorphiumEngine instances
- [ ] Use built-in functions when possible
- [ ] Cache expensive computations in variables
- [ ] Filter early, transform late
- [ ] Use break for early exit
- [ ] Batch operations when possible
- [ ] Use appropriate data structures (indexBy for lookups)
- [ ] Limit result size to what's needed

### ❌ Don't:
- [ ] Create new engines for each transformation
- [ ] Repeat expensive calculations
- [ ] Log excessively in tight loops
- [ ] Create unnecessary deep copies
- [ ] Iterate multiple times when one pass works
- [ ] Return large unused datasets
- [ ] Use string concatenation in loops
- [ ] Nest loops more than necessary

---

## Performance Metrics

### Expected Performance (Indicative)

| Operation | Items | Time (approx) |
|-----------|-------|---------------|
| Simple map | 10K | 2-5 ms |
| Filter + map | 10K | 3-8 ms |
| Nested loops | 1K x 100 | 10-20 ms |
| Complex transform | 10K | 10-30 ms |
| Cached parse | Any | <1 μs overhead |

*Actual performance depends on hardware, JVM, and transformation complexity*

---

## Debugging Performance Issues

### 1. Enable Timing Logs

```java
engine.setLogger(new Logger() {
    @Override
    public void log(Level level, String message) {
        System.out.println("[" + System.currentTimeMillis() + "] " + message);
    }
});
```

### 2. Profile with JProfiler/YourKit

Identify hot spots in transformations.

### 3. Check Cache Hit Rate

```java
System.out.println("Cache size: " + engine.getCacheSize());
```

### 4. Test with Different Input Sizes

Test with 100, 1K, 10K, 100K items to find performance characteristics.

---

## Related Topics

- [Basic Concepts](02-basic-concepts.md) - Understanding fundamentals
- [Built-in Functions](functions/map.md) - Optimized functions
- [For Loops](10-for-of.md) - Loop optimizations
- [Java API](19-java-api.md) - Integration patterns

---

**See Also:**
- [Quick Start](01-quick-start.md)
- [Best Practices](23-common-patterns.md)
- [Real-World Examples](22-real-world-examples.md)
