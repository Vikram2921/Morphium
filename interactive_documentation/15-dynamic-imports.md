# Dynamic Imports

Learn how to dynamically load and execute Morphium scripts at runtime based on conditions, user input, or data.

---

## Overview

Dynamic imports enable you to:
- **Load scripts conditionally** based on runtime data
- **Create plugin systems** with dynamically loaded transformations
- **Build flexible pipelines** that adapt to input
- **Implement strategy patterns** with script selection

---

## Syntax

```javascript
// Get morph script dynamically
variable scriptContent = getMorphScript(...parameters);

// Execute dynamic script
runMorph(scriptContent, context)
```

---

## Basic Examples

### Example 1: Simple Dynamic Execution

```javascript
{
    // Define script as string
    script: "{result: $.value * 2}",
    
    // Execute it
    output: runMorph("{result: $.value * 2}", {value: 10})
}
```

**Output:**
```json
{
    "script": "{result: $.value * 2}",
    "output": {
        "result": 20
    }
}
```

---

### Example 2: Conditional Script Loading

```javascript
{
    transformation: if ($.type == "user") {
        runMorph("{name: $.firstName + ' ' + $.lastName, email: $.email}", $)
    } else if ($.type == "order") {
        runMorph("{orderId: $.id, total: $.amount + ($.amount * 0.1)}", $)
    } else {
        $
    }
}
```

**Input (user):**
```json
{
    "type": "user",
    "firstName": "John",
    "lastName": "Doe",
    "email": "john@example.com"
}
```

**Output:**
```json
{
    "transformation": {
        "name": "John Doe",
        "email": "john@example.com"
    }
}
```

---

### Example 3: Script Selection from Data

```javascript
{
    selectedScript: $.config.scriptType,
    
    result: switch ($.config.scriptType) {
        case "simple": runMorph("{value: $.data}", $)
        case "double": runMorph("{value: $.data * 2}", $)
        case "triple": runMorph("{value: $.data * 3}", $)
        default: $
    }
}
```

---

## Advanced Examples

### Example 4: Dynamic Import Interface

**Define a function that returns morph script based on parameters:**

```javascript
function getMorphScript(transformType, options) {
    switch (transformType) {
        case "enrich": 
            "{
                id: $.id,
                name: $.name,
                enriched: true,
                timestamp: now()
            }"
            
        case "filter":
            "{
                items: filter($.items, 'i', i.active == true)
            }"
            
        case "aggregate":
            "{
                total: sum(map($.items, 'i', i.price)),
                count: len($.items),
                average: avg(map($.items, 'i', i.price))
            }"
            
        case "custom":
            options.customScript
            
        default:
            "{error: 'Unknown transformation type'}"
    }
}

{
    script: getMorphScript($.transformType, $.options),
    result: runMorph(getMorphScript($.transformType, $.options), $.data)
}
```

**Input:**
```json
{
    "transformType": "enrich",
    "data": {
        "id": 123,
        "name": "Product A"
    }
}
```

**Output:**
```json
{
    "script": "{\n    id: $.id,\n    name: $.name,\n    enriched: true,\n    timestamp: 1699804800000\n}",
    "result": {
        "id": 123,
        "name": "Product A",
        "enriched": true,
        "timestamp": 1699804800000
    }
}
```

---

### Example 5: Plugin System

```javascript
// Define available plugins
variable plugins = {
    "validator": "{
        valid: exists($.email) && exists($.name),
        errors: []
    }",
    
    "enricher": "{
        id: $.id,
        fullName: $.firstName + ' ' + $.lastName,
        createdAt: now()
    }",
    
    "pricer": "{
        basePrice: $.price,
        tax: $.price * 0.1,
        total: $.price * 1.1
    }"
};

function applyPlugin(pluginName, data) {
    variable script = plugins[pluginName];
    if (exists(script)) {
        runMorph(script, data)
    } else {
        {error: "Plugin not found: " + pluginName}
    }
}

{
    // Apply multiple plugins
    validated: applyPlugin("validator", $.user),
    enriched: applyPlugin("enricher", $.user),
    priced: applyPlugin("pricer", $.product)
}
```

---

### Example 6: Transformation Pipeline

```javascript
function buildPipeline(steps) {
    // Build a script that chains transformations
    variable script = "$";
    
    for (step of steps) {
        script = "runMorph('" + step.script + "', " + script + ")"
    }
    
    script
}

// Define pipeline steps
variable pipeline = [
    {name: "filter", script: "{items: filter($.items, 'i', i.active)}"},
    {name: "enrich", script: "{items: map($.items, 'i', {id: i.id, name: i.name, processed: true})}"},
    {name: "aggregate", script: "{count: len($.items), items: $.items}"}
];

{
    pipelineScript: buildPipeline(pipeline),
    result: runMorph(buildPipeline(pipeline), $)
}
```

---

### Example 7: Template-Based Transformations

```javascript
function fillTemplate(template, values) {
    variable script = template;
    
    for (entry of entries(values)) {
        script = replace(script, "{{" + entry.key + "}}", toString(entry.value))
    }
    
    script
}

variable template = "{
    userId: {{userId}},
    userName: '{{userName}}',
    status: '{{status}}',
    timestamp: {{timestamp}}
}";

variable values = {
    userId: 123,
    userName: "John Doe",
    status: "active",
    timestamp: now()
};

{
    generatedScript: fillTemplate(template, values),
    result: runMorph(fillTemplate(template, values), {})
}
```

---

### Example 8: Conditional Transformation Chain

```javascript
function getTransformationChain(dataType, features) {
    variable transformations = [];
    
    // Add base transformation
    transformations = concat(transformations, [{
        name: "base",
        script: "{type: '" + dataType + "', data: $}"
    }]);
    
    // Conditionally add features
    if (features.validate) {
        transformations = concat(transformations, [{
            name: "validate",
            script: "{data: $.data, valid: exists($.data)}"
        }])
    }
    
    if (features.enrich) {
        transformations = concat(transformations, [{
            name: "enrich",
            script: "{data: $.data, enriched: true, timestamp: now()}"
        }])
    }
    
    if (features.aggregate) {
        transformations = concat(transformations, [{
            name: "aggregate",
            script: "{data: $.data, count: 1}"
        }])
    }
    
    transformations
}

variable chain = getTransformationChain("user", {
    validate: true,
    enrich: true,
    aggregate: false
});

{
    chain: chain,
    result: reduce(chain, "t", "acc", 
        if (acc == null) {
            runMorph(t.script, $)
        } else {
            runMorph(t.script, acc)
        },
        null
    )
}
```

---

## Import from External Sources

### Example 9: Import Custom Script

```javascript
// Function to simulate getting script from external source
function getImportMorph(source, scriptName, parameters) {
    // In real implementation, this could:
    // - Load from database
    // - Fetch from API
    // - Read from file system
    // - Get from cache
    
    switch (source) {
        case "library":
            switch (scriptName) {
                case "userTransform":
                    "{
                        id: $.id,
                        name: $.name,
                        email: lower($.email),
                        params: " + jsonStringify(parameters) + "
                    }"
                    
                case "orderTransform":
                    "{
                        orderId: $.id,
                        total: $.amount * (1 + " + toString(parameters.taxRate) + "),
                        status: upper($.status)
                    }"
                    
                default: "{error: 'Script not found'}"
            }
            
        case "custom":
            parameters.customScript
            
        default:
            "{error: 'Unknown source'}"
    }
}

// Usage example
{
    // Import from library
    userScript: getImportMorph("library", "userTransform", {format: "standard"}),
    
    // Execute imported script
    transformedUser: runMorph(
        getImportMorph("library", "userTransform", {format: "standard"}),
        $.user
    ),
    
    // Import custom script
    customScript: getImportMorph("custom", null, {
        customScript: "{customField: $.data * 100}"
    }),
    
    customResult: runMorph(
        getImportMorph("custom", null, {customScript: "{customField: $.data * 100}"}),
        {data: 5}
    )
}
```

**Input:**
```json
{
    "user": {
        "id": 1,
        "name": "Alice",
        "email": "ALICE@EXAMPLE.COM"
    }
}
```

**Output:**
```json
{
    "userScript": "{\n    id: $.id,\n    name: $.name,\n    email: lower($.email),\n    params: {\"format\":\"standard\"}\n}",
    "transformedUser": {
        "id": 1,
        "name": "Alice",
        "email": "alice@example.com",
        "params": {
            "format": "standard"
        }
    },
    "customScript": "{customField: $.data * 100}",
    "customResult": {
        "customField": 500
    }
}
```

---

### Example 10: Dynamic Import as Module

```javascript
// Simulate import statement
function importMorph(modulePath, exportName) {
    // This would load the module and return specific export
    variable modules = {
        "lib/user": {
            "transform": "{id: $.id, name: upper($.name)}",
            "validate": "{valid: exists($.id) && exists($.name)}"
        },
        "lib/order": {
            "calculate": "{total: $.amount * 1.1}",
            "enrich": "{orderId: $.id, enriched: true}"
        }
    };
    
    modules[modulePath][exportName]
}

// Use like: import { transform } from "lib/user"
variable customTransform = importMorph("lib/user", "transform");

{
    script: customTransform,
    result: runMorph(customTransform, {id: 1, name: "alice"})
}
```

---

## Practical Use Cases

### Use Case 1: Multi-Tenant Transformations

```javascript
function getTenantTransformation(tenantId) {
    // Different tenants might have different transformation rules
    variable tenantRules = {
        "tenant1": "{name: $.name, type: 'premium'}",
        "tenant2": "{name: $.name, type: 'standard', discount: 0.1}",
        "tenant3": "{name: $.name, type: 'enterprise', features: ['all']}"
    };
    
    tenantRules[tenantId]
}

{
    tenant: $.tenantId,
    data: runMorph(getTenantTransformation($.tenantId), $.data)
}
```

---

### Use Case 2: A/B Testing Transformations

```javascript
function getExperimentTransformation(experimentGroup) {
    switch (experimentGroup) {
        case "A":
            "{layout: 'classic', data: $.data}"
        case "B":
            "{layout: 'modern', data: $.data, enhanced: true}"
        default:
            "{layout: 'default', data: $.data}"
    }
}

{
    group: $.experimentGroup,
    result: runMorph(getExperimentTransformation($.experimentGroup), $)
}
```

---

### Use Case 3: Version-Specific Transformations

```javascript
function getVersionedTransform(apiVersion) {
    switch (apiVersion) {
        case "v1":
            "{id: $.id, name: $.name}"
        case "v2":
            "{id: $.id, fullName: $.name, metadata: {version: 'v2'}}"
        case "v3":
            "{userId: $.id, profile: {name: $.name}, apiVersion: 'v3'}"
        default:
            $
    }
}

{
    version: $.apiVersion,
    response: runMorph(getVersionedTransform($.apiVersion), $.data)
}
```

---

## Best Practices

### ✅ Do's

1. **Validate script content** before execution
2. **Cache generated scripts** for performance
3. **Handle script errors** gracefully
4. **Document script generators** clearly
5. **Test dynamic scripts** thoroughly
6. **Use script validation** to prevent errors

### ❌ Don'ts

1. **Don't execute untrusted scripts** - Security risk
2. **Don't generate overly complex scripts** - Hard to debug
3. **Don't skip error handling** - Scripts can fail
4. **Don't regenerate unnecessarily** - Cache when possible
5. **Don't create circular dependencies** - Scripts calling themselves

---

## Security Considerations

1. **Validate input** - Don't trust user-provided scripts
2. **Sandbox execution** - Limit what scripts can access
3. **Audit script execution** - Log what scripts run
4. **Set resource limits** - Prevent infinite loops
5. **Use whitelisting** - Only allow known-safe scripts

---

## Performance Tips

1. **Cache compiled scripts** - Don't reparse repeatedly
2. **Lazy load scripts** - Only load when needed
3. **Minimize script size** - Smaller scripts parse faster
4. **Batch operations** - Combine multiple transformations
5. **Profile performance** - Identify slow scripts

---

## Related Topics

- [User-Defined Functions](13-user-functions.md) - Create reusable functions
- [Module System](14-modules.md) - Organize code in modules
- [Error Handling](16-error-handling.md) - Handle script errors
- [Performance Tips](18-performance.md) - Optimize dynamic imports

---

## Summary

Dynamic imports in Morphium DSL enable:
- ✅ Runtime script loading and execution
- ✅ Flexible transformation pipelines
- ✅ Plugin systems and extensibility
- ✅ Conditional logic based on data
- ✅ Multi-tenant and versioned transformations
- ✅ Template-based script generation

---

[← Back to Documentation](README.md)
