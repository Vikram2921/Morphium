# Common Patterns

Reusable transformation patterns and techniques for Morphium DSL.

---

## Data Validation Patterns

### Pattern 1: Field Validation

```javascript
function validateField(value, fieldName, validators) {
    variable errors = [];
    
    if (validators.required && (!exists(value) || value == "")) {
        errors = concat(errors, [fieldName + " is required"])
    }
    
    if (exists(validators.minLength) && len(value) < validators.minLength) {
        errors = concat(errors, [fieldName + " must be at least " + toString(validators.minLength) + " characters"])
    }
    
    if (exists(validators.maxLength) && len(value) > validators.maxLength) {
        errors = concat(errors, [fieldName + " must be at most " + toString(validators.maxLength) + " characters"])
    }
    
    if (exists(validators.pattern) && !matches(value, validators.pattern)) {
        errors = concat(errors, [fieldName + " format is invalid"])
    }
    
    {value: value, errors: errors, valid: len(errors) == 0}
}
```

---

### Pattern 2: Nested Validation

```javascript
function validateNested(obj, schema) {
    variable allErrors = [];
    
    for (field of keys(schema)) {
        variable validator = schema[field];
        variable value = obj[field];
        variable result = validateField(value, field, validator);
        
        if (!result.valid) {
            allErrors = concat(allErrors, result.errors)
        }
    }
    
    {data: obj, valid: len(allErrors) == 0, errors: allErrors}
}
```

---

## Transformation Patterns

### Pattern 3: Safe Navigation

```javascript
function safeGet(obj, path, defaultValue) {
    variable value = get(obj, path);
    if (exists(value)) { value } else { defaultValue }
}

// Usage
{
    userName: safeGet($, "user.profile.name", "Unknown"),
    userAge: safeGet($, "user.profile.age", 0)
}
```

---

### Pattern 4: Conditional Enrichment

```javascript
{
    base: $,
    enriched: merge($, if ($.type == "premium") {
        {
            benefits: ["priority", "discount", "support"],
            discount: 0.2
        }
    } else {
        {
            benefits: ["basic"],
            discount: 0
        }
    })
}
```

---

### Pattern 5: Field Mapping

```javascript
function mapFields(source, mappings) {
    variable result = {};
    
    for (entry of entries(mappings)) {
        variable targetField = entry.key;
        variable sourceField = entry.value;
        result = set(result, targetField, get(source, sourceField))
    }
    
    result
}

// Usage
{
    user: mapFields($, {
        "id": "user_id",
        "name": "full_name",
        "email": "email_address"
    })
}
```

---

## Aggregation Patterns

### Pattern 6: Group and Aggregate

```javascript
{
    grouped: groupBy($.items, "item", "category", {
        category: item.category,
        count: 1,
        totalValue: item.price,
        avgValue: item.price
    })
}
```

---

### Pattern 7: Running Totals

```javascript
{
    items: for (item, index of $.items) {
        variable previousTotal = if (index == 0) { 0 } else {
            sum(map(slice($.items, 0, index), "i", i.amount))
        };
        
        {
            id: item.id,
            amount: item.amount,
            runningTotal: previousTotal + item.amount
        }
    }
}
```

---

### Pattern 8: Percentages and Distributions

```javascript
{
    total: sum(map($.items, "i", i.value)),
    items: map($.items, "item", {
        name: item.name,
        value: item.value,
        percentage: item.value / sum(map($.items, "i", i.value)) * 100,
        share: if (item.value / sum(map($.items, "i", i.value)) > 0.5) { "major" }
               else if (item.value / sum(map($.items, "i", i.value)) > 0.1) { "significant" }
               else { "minor" }
    })
}
```

---

## Filtering Patterns

### Pattern 9: Multi-Condition Filter

```javascript
function filterByConditions(items, conditions) {
    filter(items, "item", 
        allMatch(
            map(entries(conditions), "c", 
                switch (c.value.operator) {
                    case "eq": get(item, c.key) == c.value.value
                    case "gt": get(item, c.key) > c.value.value
                    case "lt": get(item, c.key) < c.value.value
                    case "contains": exists(split(toString(get(item, c.key)), c.value.value)[1])
                    default: true
                }
            ),
            "cond",
            cond
        )
    )
}
```

---

### Pattern 10: Dynamic Filter Chain

```javascript
function applyFilters(data, filters) {
    reduce(filters, "filter", "acc",
        filter(acc, "item", runMorph(filter.condition, {item: item})),
        data
    )
}
```

---

## Sorting Patterns

### Pattern 11: Multi-Field Sort

```javascript
function sortByMultiple(items, sortSpecs) {
    sorted(items, "a", "b",
        reduce(sortSpecs, "spec", "result",
            if (result != 0) {
                result
            } else {
                variable aVal = get(a, spec.field);
                variable bVal = get(b, spec.field);
                variable cmp = if (aVal < bVal) { -1 } else if (aVal > bVal) { 1 } else { 0 };
                if (spec.order == "desc") { -cmp } else { cmp }
            },
            0
        )
    )
}
```

---

## Pagination Patterns

### Pattern 12: Offset Pagination

```javascript
function paginate(items, page, pageSize) {
    {
        data: slice(items, (page - 1) * pageSize, page * pageSize),
        meta: {
            currentPage: page,
            pageSize: pageSize,
            totalItems: len(items),
            totalPages: (len(items) + pageSize - 1) / pageSize,
            hasNext: page * pageSize < len(items),
            hasPrev: page > 1
        }
    }
}
```

---

### Pattern 13: Cursor-Based Pagination

```javascript
function cursorPaginate(items, cursor, limit) {
    variable startIndex = if (cursor == null) { 0 } else {
        findIndex(items, "item", item.id == cursor) + 1
    };
    
    variable pageItems = slice(items, startIndex, startIndex + limit);
    
    {
        data: pageItems,
        cursor: if (len(pageItems) > 0) { pageItems[len(pageItems) - 1].id } else { null },
        hasMore: startIndex + limit < len(items)
    }
}
```

---

## Error Handling Patterns

### Pattern 14: Try-Catch Simulation

```javascript
function tryTransform(data, transformer) {
    if (!exists(data)) {
        {success: false, error: "Data is null"}
    } else {
        variable result = runMorph(transformer, data);
        if (exists(result)) {
            {success: true, data: result}
        } else {
            {success: false, error: "Transformation failed"}
        }
    }
}
```

---

### Pattern 15: Error Recovery

```javascript
function withFallback(primaryFn, fallbackFn, data) {
    variable primaryResult = runMorph(primaryFn, data);
    if (exists(primaryResult)) {
        primaryResult
    } else {
        runMorph(fallbackFn, data)
    }
}
```

---

## Data Normalization Patterns

### Pattern 16: Flatten Nested Objects

```javascript
function flatten(obj, prefix) {
    variable result = {};
    
    for (entry of entries(obj)) {
        variable key = if (prefix == "") { entry.key } else { prefix + "." + entry.key };
        variable value = entry.value;
        
        if (isObject(value)) {
            result = merge(result, flatten(value, key))
        } else {
            result = set(result, key, value)
        }
    }
    
    result
}
```

---

### Pattern 17: Unflatten Objects

```javascript
function unflatten(flatObj) {
    variable result = {};
    
    for (entry of entries(flatObj)) {
        variable path = split(entry.key, ".");
        result = set(result, entry.key, entry.value)
    }
    
    result
}
```

---

## Deduplication Patterns

### Pattern 18: Deduplicate by Field

```javascript
function deduplicateBy(items, field) {
    variable seen = {};
    
    filter(items, "item",
        variable key = get(item, field);
        variable isDuplicate = exists(seen[key]);
        seen = set(seen, key, true);
        !isDuplicate
    )
}
```

---

### Pattern 19: Merge Duplicates

```javascript
function mergeDuplicates(items, keyField) {
    variable grouped = groupBy(items, "item", get(item, keyField), item);
    
    map(values(grouped), "group",
        reduce(group, "item", "acc", merge(acc, item), {})
    )
}
```

---

## Transformation Pipeline Pattern

### Pattern 20: Pipeline Builder

```javascript
function pipeline(data, steps) {
    reduce(steps, "step", "acc",
        runMorph(step.transform, acc),
        data
    )
}

// Usage
{
    result: pipeline($, [
        {name: "filter", transform: "{items: filter($.items, 'i', i.active)}"},
        {name: "sort", transform: "{items: sorted($.items, 'i', i.priority, 'desc')}"},
        {name: "limit", transform: "{items: limit($.items, 10)}"}
    ])
}
```

---

## Caching Pattern

### Pattern 21: Memoization

```javascript
variable cache = {};

function memoize(key, computer) {
    if (exists(cache[key])) {
        cache[key]
    } else {
        variable result = runMorph(computer, {});
        cache = set(cache, key, result);
        result
    }
}
```

---

## Data Enrichment Pattern

### Pattern 22: Lookup and Enrich

```javascript
function enrichWithLookup(items, lookupTable, lookupField, enrichFields) {
    map(items, "item",
        variable lookupKey = get(item, lookupField);
        variable lookupData = lookupTable[lookupKey];
        
        if (exists(lookupData)) {
            merge(item, pluck(lookupData, enrichFields))
        } else {
            item
        }
    )
}
```

---

## Conditional Transformation Pattern

### Pattern 23: Strategy Pattern

```javascript
variable strategies = {
    "typeA": "{result: $.value * 2}",
    "typeB": "{result: $.value + 10}",
    "typeC": "{result: $.value / 2}"
};

function transformByType(data, type) {
    variable strategy = strategies[type];
    if (exists(strategy)) {
        runMorph(strategy, data)
    } else {
        data
    }
}
```

---

## Batching Pattern

### Pattern 24: Chunk Processing

```javascript
function processinBatches(items, batchSize, processor) {
    variable batches = [];
    variable i = 0;
    
    while (i < len(items)) {
        variable batch = slice(items, i, i + batchSize);
        variable processed = runMorph(processor, {batch: batch});
        batches = concat(batches, [processed]);
        i = i + batchSize
    }
    
    batches
}
```

---

## Related Topics

- [Real-World Examples](22-real-world-examples.md) - Complete examples
- [User-Defined Functions](13-user-functions.md) - Create reusable functions
- [Performance Tips](18-performance.md) - Optimize patterns

---

[← Back to Documentation](README.md)
