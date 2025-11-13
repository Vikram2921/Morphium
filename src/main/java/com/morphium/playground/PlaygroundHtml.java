package com.morphium.playground;

public class PlaygroundHtml {
    public static String getHtml() {
        return "<!DOCTYPE html>\n" +
"<html lang=\"en\">\n" +
"<head>\n" +
"    <meta charset=\"UTF-8\">\n" +
"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
"    <meta name=\"description\" content=\"Morphium - Professional JSON transformation DSL playground\">\n" +
"    <meta name=\"author\" content=\"Morphium\">\n" +
"    <title>Morphium Playground - JSON Transformation DSL</title>\n" +
"    <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.65.2/codemirror.min.css\">\n" +
"    <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.65.2/theme/monokai.min.css\">\n" +
"    <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.65.2/theme/material.min.css\">\n" +
"    <style>\n" +
"        * { margin: 0; padding: 0; box-sizing: border-box; }\n" +
"        body {\n" +
"            font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;\n" +
"            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);\n" +
"            min-height: 100vh;\n" +
"            padding: 20px;\n" +
"        }\n" +
"        .container {\n" +
"            max-width: 1800px;\n" +
"            margin: 0 auto;\n" +
"            background: white;\n" +
"            border-radius: 12px;\n" +
"            box-shadow: 0 20px 60px rgba(0,0,0,0.3);\n" +
"            overflow: hidden;\n" +
"        }\n" +
"        .header {\n" +
"            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);\n" +
"            color: white;\n" +
"            padding: 30px;\n" +
"            text-align: center;\n" +
"            position: relative;\n" +
"        }\n" +
"        .header h1 {\n" +
"            font-size: 2.5em;\n" +
"            margin-bottom: 10px;\n" +
"            text-shadow: 2px 2px 4px rgba(0,0,0,0.2);\n" +
"            font-weight: 700;\n" +
"        }\n" +
"        .header p {\n" +
"            font-size: 1.1em;\n" +
"            opacity: 0.95;\n" +
"            font-weight: 300;\n" +
"        }\n" +
"        .header .version {\n" +
"            position: absolute;\n" +
"            top: 10px;\n" +
"            right: 20px;\n" +
"            font-size: 0.85em;\n" +
"            opacity: 0.8;\n" +
"            background: rgba(255,255,255,0.2);\n" +
"            padding: 5px 12px;\n" +
"            border-radius: 15px;\n" +
"        }\n"+
"        .content {\n" +
"            display: grid;\n" +
"            grid-template-columns: 1fr 1fr;\n" +
"            gap: 20px;\n" +
"            padding: 30px;\n" +
"        }\n" +
"        .panel {\n" +
"            display: flex;\n" +
"            flex-direction: column;\n" +
"        }\n" +
"        .panel-header {\n" +
"            display: flex;\n" +
"            justify-content: space-between;\n" +
"            align-items: center;\n" +
"            margin-bottom: 12px;\n" +
"        }\n" +
"        .panel-title {\n" +
"            font-size: 1.3em;\n" +
"            font-weight: 600;\n" +
"            color: #333;\n" +
"        }\n" +
"        .auto-transform-toggle {\n" +
"            display: flex;\n" +
"            align-items: center;\n" +
"            gap: 8px;\n" +
"            font-size: 0.9em;\n" +
"            color: #666;\n" +
"        }\n" +
"        .auto-transform-toggle input[type=\"checkbox\"] {\n" +
"            width: 18px;\n" +
"            height: 18px;\n" +
"            cursor: pointer;\n" +
"        }\n" +
"        .editor-container {\n" +
"            flex: 1;\n" +
"            border: 2px solid #e0e0e0;\n" +
"            border-radius: 8px;\n" +
"            overflow: hidden;\n" +
"            position: relative;\n" +
"        }\n" +
"        .CodeMirror {\n" +
"            height: 400px;\n" +
"            font-size: 14px;\n" +
"            font-family: 'Consolas', 'Monaco', 'Courier New', monospace;\n" +
"        }\n" +
"        .transform-section .editor-container .CodeMirror {\n" +
"            height: 300px;\n" +
"        }\n" +
"        .output-area {\n" +
"            width: 100%;\n" +
"            height: 400px;\n" +
"            font-family: 'Consolas', 'Monaco', 'Courier New', monospace;\n" +
"            font-size: 14px;\n" +
"            padding: 15px;\n" +
"            background: #1e1e1e;\n" +
"            color: #d4d4d4;\n" +
"            overflow: auto;\n" +
"            border: none;\n" +
"        }\n" +
"        .transform-section {\n" +
"            grid-column: 1 / -1;\n" +
"        }\n" +
"        .button-container {\n" +
"            grid-column: 1 / -1;\n" +
"            display: flex;\n" +
"            justify-content: center;\n" +
"            gap: 15px;\n" +
"            margin: 20px 0;\n" +
"        }\n" +
"        button {\n" +
"            padding: 15px 40px;\n" +
"            font-size: 16px;\n" +
"            font-weight: 600;\n" +
"            border: none;\n" +
"            border-radius: 8px;\n" +
"            cursor: pointer;\n" +
"            transition: all 0.3s ease;\n" +
"            box-shadow: 0 4px 6px rgba(0,0,0,0.1);\n" +
"        }\n" +
"        .btn-primary {\n" +
"            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);\n" +
"            color: white;\n" +
"        }\n" +
"        .btn-primary:hover {\n" +
"            transform: translateY(-2px);\n" +
"            box-shadow: 0 6px 12px rgba(0,0,0,0.2);\n" +
"        }\n" +
"        .btn-secondary {\n" +
"            background: #e0e0e0;\n" +
"            color: #333;\n" +
"        }\n" +
"        .btn-secondary:hover {\n" +
"            background: #d0d0d0;\n" +
"        }\n" +
"        .status-bar {\n" +
"            grid-column: 1 / -1;\n" +
"            padding: 15px;\n" +
"            background: #f8f9fa;\n" +
"            border-radius: 8px;\n" +
"            display: flex;\n" +
"            justify-content: space-between;\n" +
"            align-items: center;\n" +
"        }\n" +
"        .status-message {\n" +
"            font-weight: 500;\n" +
"        }\n" +
"        .status-success { color: #28a745; }\n" +
"        .status-error { color: #dc3545; }\n" +
"        .status-info { color: #17a2b8; }\n" +
"        .execution-time {\n" +
"            font-size: 0.9em;\n" +
"            color: #666;\n" +
"        }\n" +
"        .examples {\n" +
"            grid-column: 1 / -1;\n" +
"            margin-top: 20px;\n" +
"        }\n" +
"        .examples-header {\n" +
"            font-size: 1.2em;\n" +
"            font-weight: 600;\n" +
"            margin-bottom: 15px;\n" +
"            color: #333;\n" +
"        }\n" +
"        .example-buttons {\n" +
"            display: flex;\n" +
"            flex-wrap: wrap;\n" +
"            gap: 10px;\n" +
"        }\n" +
"        .example-btn {\n" +
"            padding: 8px 16px;\n" +
"            font-size: 14px;\n" +
"            background: white;\n" +
"            border: 2px solid #667eea;\n" +
"            color: #667eea;\n" +
"            border-radius: 6px;\n" +
"            cursor: pointer;\n" +
"            transition: all 0.2s;\n" +
"        }\n" +
"        .example-btn:hover {\n" +
"            background: #667eea;\n" +
"            color: white;\n" +
"        }\n" +
"        pre {\n" +
"            margin: 0;\n" +
"            white-space: pre-wrap;\n" +
"            word-wrap: break-word;\n" +
"        }\n" +
"        @media (max-width: 1200px) {\n" +
"            .content {\n" +
"                grid-template-columns: 1fr;\n" +
"            }\n" +
"            .transform-section {\n" +
"                grid-column: 1;\n" +
"            }\n" +
"            .button-container, .status-bar, .examples {\n" +
"                grid-column: 1;\n" +
"            }\n" +
"        }\n" +
"    </style>\n" +
"</head>\n" +
"<body>\n" +
"    <div class=\"container\">\n" +
"        <div class=\"header\">\n" +
"            <div class=\"version\">v1.0.0</div>\n" +
"            <h1>Morphium Playground</h1>\n" +
"            <p>Professional JSON Transformation DSL - Enterprise-Ready Development Environment</p>\n" +
"        </div>\n" +
"        \n"+
"        <div class=\"content\">\n" +
"            <div class=\"panel transform-section\">\n" +
"                <div class=\"panel-header\">\n" +
"                    <span class=\"panel-title\">Transform (Morphium DSL)</span>\n" +
"                    <div class=\"auto-transform-toggle\">\n" +
"                        <input type=\"checkbox\" id=\"autoTransform\" checked>\n" +
"                        <label for=\"autoTransform\">Auto-transform on change</label>\n" +
"                    </div>\n" +
"                </div>\n" +
"                <div class=\"editor-container\">\n" +
"                    <textarea id=\"transform\">{\n"+
"  fullName: $.person.first + \" \" + $.person.last,\n" +
"  age: $.person.age,\n" +
"  email: $.person.email,\n" +
"  isAdult: $.person.age >= 18\n" +
"}</textarea>\n" +
"                </div>\n" +
"            </div>\n" +
"            \n" +
"            <div class=\"panel\">\n" +
"                <div class=\"panel-header\">\n" +
"                    <span class=\"panel-title\">Input JSON</span>\n" +
"                </div>\n" +
"                <div class=\"editor-container\">\n" +
"                    <textarea id=\"input\">{\n"+
"  \"person\": {\n" +
"    \"first\": \"John\",\n" +
"    \"last\": \"Doe\",\n" +
"    \"age\": 25,\n" +
"    \"email\": \"john.doe@example.com\"\n" +
"  }\n" +
"}</textarea>\n" +
"                </div>\n" +
"            </div>\n" +
"            \n" +
"            <div class=\"panel\">\n" +
"                <div class=\"panel-header\">\n" +
"                    <span class=\"panel-title\">Output JSON</span>\n" +
"                </div>\n" +
"                <div class=\"editor-container\">\n" +
"                    <div id=\"output\" class=\"output-area\"><pre>Output will appear here...</pre></div>\n" +
"                </div>\n" +
"            </div>\n" +
"            \n" +
"            <div class=\"button-container\">\n" +
"                <button class=\"btn-primary\" onclick=\"transform()\">Transform</button>\n" +
"                <button class=\"btn-secondary\" onclick=\"clearAll()\">Clear All</button>\n" +
"                <button class=\"btn-secondary\" onclick=\"formatJson()\">Format JSON</button>\n" +
"                <button class=\"btn-secondary\" onclick=\"copyOutput()\">Copy Output</button>\n" +
"            </div>\n"+
"            \n" +
"            <div class=\"status-bar\">\n" +
"                <span id=\"status\" class=\"status-message status-info\">Ready to transform</span>\n" +
"                <span id=\"execTime\" class=\"execution-time\"></span>\n" +
"            </div>\n" +
"            \n" +
"            <div class=\"examples\">\n" +
"                <div class=\"examples-header\">Example Transformations</div>\n" +
"                <div class=\"example-buttons\">\n"+
"                    <button class=\"example-btn\" onclick=\"loadExample('basic')\">Basic Transform</button>\n" +
"                    <button class=\"example-btn\" onclick=\"loadExample('array')\">Array Map</button>\n" +
"                    <button class=\"example-btn\" onclick=\"loadExample('filter')\">Filter</button>\n" +
"                    <button class=\"example-btn\" onclick=\"loadExample('merge')\">Merge</button>\n" +
"                    <button class=\"example-btn\" onclick=\"loadExample('conditional')\">Conditional</button>\n" +
"                    <button class=\"example-btn\" onclick=\"loadExample('function')\">Custom Function</button>\n" +
"                    <button class=\"example-btn\" onclick=\"loadExample('variables')\">$ Variables</button>\n" +
"                    <button class=\"example-btn\" onclick=\"loadExample('streams')\">Streams API</button>\n" +
"                    <button class=\"example-btn\" onclick=\"loadExample('advanced')\">Advanced Streams</button>\n" +
"                    <button class=\"example-btn\" onclick=\"loadExample('aggregation')\">Aggregation</button>\n" +
"                </div>\n" +
"            </div>\n" +
"        </div>\n" +
"    </div>\n" +
"    <script src=\"https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.65.2/codemirror.min.js\"></script>\n" +
"    <script src=\"https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.65.2/mode/javascript/javascript.min.js\"></script>\n" +
"    " + getJavaScript() + "\n" +
"</body>\n" +
"</html>";
    }

    private static String getJavaScript() {
        return "<script>\n" +
"let transformEditor, inputEditor;\n" +
"let autoTransformTimeout = null;\n" +
"\n" +
"window.addEventListener('load', function() {\n" +
"    transformEditor = CodeMirror.fromTextArea(document.getElementById('transform'), {\n" +
"        mode: 'javascript',\n" +
"        theme: 'material',\n" +
"        lineNumbers: true,\n" +
"        lineWrapping: true,\n" +
"        indentUnit: 2,\n" +
"        tabSize: 2,\n" +
"        matchBrackets: true,\n" +
"        autoCloseBrackets: true\n" +
"    });\n" +
"    \n" +
"    inputEditor = CodeMirror.fromTextArea(document.getElementById('input'), {\n" +
"        mode: {name: 'javascript', json: true},\n" +
"        theme: 'material',\n" +
"        lineNumbers: true,\n" +
"        lineWrapping: true,\n" +
"        indentUnit: 2,\n" +
"        tabSize: 2,\n" +
"        matchBrackets: true,\n" +
"        autoCloseBrackets: true\n" +
"    });\n" +
"    \n" +
"    transformEditor.on('change', function() {\n" +
"        scheduleAutoTransform();\n" +
"    });\n" +
"    \n" +
"    inputEditor.on('change', function() {\n" +
"        scheduleAutoTransform();\n" +
"    });\n" +
"    \n" +
"    transformEditor.setSize(null, 300);\n" +
"    inputEditor.setSize(null, 400);\n" +
"    \n" +
"    transform();\n" +
"});\n" +
"\n" +
"function scheduleAutoTransform() {\n" +
"    if (!document.getElementById('autoTransform').checked) return;\n" +
"    \n" +
"    if (autoTransformTimeout) {\n" +
"        clearTimeout(autoTransformTimeout);\n" +
"    }\n" +
"    \n" +
"    autoTransformTimeout = setTimeout(function() {\n" +
"        transform();\n" +
"    }, 500);\n" +
"}\n" +
"\n" +
"const examples = {\n" +
"    basic: {\n" +
"        transform: `{\n" +
"  fullName: $.person.first + \" \" + $.person.last,\n" +
"  age: $.person.age,\n" +
"  email: $.person.email,\n" +
"  isAdult: $.person.age >= 18\n" +
"}`,\n" +
"        input: `{\n" +
"  \"person\": {\n" +
"    \"first\": \"John\",\n" +
"    \"last\": \"Doe\",\n" +
"    \"age\": 25,\n" +
"    \"email\": \"john.doe@example.com\"\n" +
"  }\n" +
"}`\n" +
"    },\n" +
"    array: {\n" +
"        transform: `{\n" +
"  items: map($.items, \"item\", {\n" +
"    id: item.id,\n" +
"    total: item.qty * item.price,\n" +
"    discounted: (item.qty * item.price) * 0.9\n" +
"  })\n" +
"}`,\n" +
"        input: `{\n" +
"  \"items\": [\n" +
"    {\"id\": \"A\", \"qty\": 2, \"price\": 10},\n" +
"    {\"id\": \"B\", \"qty\": 3, \"price\": 15},\n" +
"    {\"id\": \"C\", \"qty\": 1, \"price\": 25}\n" +
"  ]\n" +
"}`\n" +
"    },\n" +
"    filter: {\n" +
"        transform: `{\n" +
"  adults: filter($.users, \"u\", u.age >= 18),\n" +
"  minors: filter($.users, \"u\", u.age < 18)\n" +
"}`,\n" +
"        input: `{\n" +
"  \"users\": [\n" +
"    {\"name\": \"Alice\", \"age\": 25},\n" +
"    {\"name\": \"Bob\", \"age\": 16},\n" +
"    {\"name\": \"Charlie\", \"age\": 30},\n" +
"    {\"name\": \"Diana\", \"age\": 14}\n" +
"  ]\n" +
"}`\n" +
"    },\n" +
"    merge: {\n" +
"        transform: `merge(\n" +
"  {status: \"active\"},\n" +
"  $.user,\n" +
"  {lastUpdated: now()}\n" +
")`,\n" +
"        input: `{\n" +
"  \"user\": {\n" +
"    \"name\": \"John Doe\",\n" +
"    \"email\": \"john@example.com\"\n" +
"  }\n" +
"}`\n" +
"    },\n" +
"    conditional: {\n" +
"        transform: `{\n" +
"  users: map($.users, \"u\", {\n" +
"    name: u.name,\n" +
"    age: u.age,\n" +
"    category: u.age < 18 ? \"minor\" : (u.age < 65 ? \"adult\" : \"senior\"),\n" +
"    status: u.active ? \"Active\" : \"Inactive\"\n" +
"  })\n" +
"}`,\n" +
"        input: `{\n" +
"  \"users\": [\n" +
"    {\"name\": \"Alice\", \"age\": 16, \"active\": true},\n" +
"    {\"name\": \"Bob\", \"age\": 35, \"active\": false},\n" +
"    {\"name\": \"Charlie\", \"age\": 70, \"active\": true}\n" +
"  ]\n" +
"}`\n" +
"    },\n" +
"    function: {\n" +
"        transform: `function calculateTax(amount) {\n" +
"  return amount * 0.1\n" +
"}\n" +
"\n" +
"function calculateTotal(price, qty) {\n" +
"  let subtotal = price * qty\n" +
"  let tax = calculateTax(subtotal)\n" +
"  return subtotal + tax\n" +
"}\n" +
"\n" +
"{\n" +
"  items: map($.items, \"item\", {\n" +
"    name: item.name,\n" +
"    subtotal: item.price * item.qty,\n" +
"    tax: calculateTax(item.price * item.qty),\n" +
"    total: calculateTotal(item.price, item.qty)\n" +
"  })\n" +
"}`,\n" +
"        input: `{\n" +
"  \"items\": [\n" +
"    {\"name\": \"Widget\", \"price\": 10, \"qty\": 3},\n" +
"    {\"name\": \"Gadget\", \"price\": 25, \"qty\": 2}\n" +
"  ]\n" +
"}`\n" +
"    },\n" +
"    variables: {\n" +
"        transform: `// Assign $ to variables\n" +
"let root = $\n" +
"let config = root.config\n" +
"let data = root.data\n" +
"\n" +
"{\n" +
"  status: config.enabled ? \"Active\" : \"Inactive\",\n" +
"  items: map(data.items, \"item\", {\n" +
"    id: item.id,\n" +
"    name: item.name,\n" +
"    multiplier: config.multiplier,\n" +
"    value: item.value * config.multiplier\n" +
"  })\n" +
"}`,\n" +
"        input: `{\n" +
"  \"config\": {\n" +
"    \"enabled\": true,\n" +
"    \"multiplier\": 2\n" +
"  },\n" +
"  \"data\": {\n" +
"    \"items\": [\n" +
"      {\"id\": \"A\", \"name\": \"Item A\", \"value\": 10},\n" +
"      {\"id\": \"B\", \"name\": \"Item B\", \"value\": 20}\n" +
"    ]\n" +
"  }\n" +
"}`\n" +
"    },\n" +
"    streams: {\n" +
"        transform: `// Java Streams API equivalent\n" +
"let numbers = $.numbers\n" +
"\n" +
"{\n" +
"  // Basic operations\n" +
"  first: findFirst(numbers, \"n\", n > 5),\n" +
"  hasLarge: anyMatch(numbers, \"n\", n > 100),\n" +
"  allPositive: allMatch(numbers, \"n\", n > 0),\n" +
"  count: count(numbers),\n" +
"  \n" +
"  // Transformations\n" +
"  doubled: map(numbers, \"n\", n * 2),\n" +
"  filtered: filter(numbers, \"n\", n >= 10),\n" +
"  sorted: sorted(numbers),\n" +
"  distinct: distinct($.duplicates),\n" +
"  \n" +
"  // Limit and skip\n" +
"  first3: limit(numbers, 3),\n" +
"  skip2: skip(numbers, 2),\n" +
"  sliced: slice(numbers, 1, 4)\n" +
"}`,\n" +
"        input: `{\n" +
"  \"numbers\": [3, 7, 12, 5, 18, 9, 15],\n" +
"  \"duplicates\": [1, 2, 2, 3, 3, 3, 4]\n" +
"}`\n" +
"    },\n" +
"    advanced: {\n" +
"        transform: `// Advanced stream operations\n" +
"let users = $.users\n" +
"\n" +
"{\n" +
"  // FlatMap - flatten nested arrays\n" +
"  allTags: distinct(flatMap(users, \"u\", u.tags)),\n" +
"  \n" +
"  // GroupBy\n" +
"  byStatus: groupBy(users, \"status\"),\n" +
"  byAge: groupBy(users, \"age\"),\n" +
"  \n" +
"  // Partition\n" +
"  activeUsers: partition(users, \"u\", u.active),\n" +
"  \n" +
"  // Reverse and concat\n" +
"  reversed: reverse(users),\n" +
"  combined: concat(filter(users, \"u\", u.age < 25), filter(users, \"u\", u.age >= 25))\n" +
"}`,\n" +
"        input: `{\n" +
"  \"users\": [\n" +
"    {\"name\": \"Alice\", \"age\": 25, \"status\": \"active\", \"active\": true, \"tags\": [\"admin\", \"user\"]},\n" +
"    {\"name\": \"Bob\", \"age\": 30, \"status\": \"inactive\", \"active\": false, \"tags\": [\"user\"]},\n" +
"    {\"name\": \"Charlie\", \"age\": 25, \"status\": \"active\", \"active\": true, \"tags\": [\"user\", \"guest\"]}\n" +
"  ]\n" +
"}`\n" +
"    },\n" +
"    aggregation: {\n" +
"        transform: `// Aggregation functions\n" +
"let products = $.products\n" +
"let prices = pluck(products, \"price\")\n" +
"let quantities = pluck(products, \"quantity\")\n" +
"\n" +
"{\n" +
"  products: products,\n" +
"  totalProducts: count(products),\n" +
"  \n" +
"  priceStats: {\n" +
"    sum: sum(prices),\n" +
"    avg: avg(prices),\n" +
"    min: min(prices),\n" +
"    max: max(prices)\n" +
"  },\n" +
"  \n" +
"  totalRevenue: reduce(\n" +
"    products,\n" +
"    \"acc\",\n" +
"    \"p\",\n" +
"    0,\n" +
"    acc + (p.price * p.quantity)\n" +
"  ),\n" +
"  \n" +
"  expensiveProducts: count(products, \"p\", p.price > 50),\n" +
"  sortedByPrice: sorted(products, \"price\")\n" +
"}`,\n" +
"        input: `{\n" +
"  \"products\": [\n" +
"    {\"name\": \"Widget\", \"price\": 29.99, \"quantity\": 10},\n" +
"    {\"name\": \"Gadget\", \"price\": 79.99, \"quantity\": 5},\n" +
"    {\"name\": \"Doohickey\", \"price\": 19.99, \"quantity\": 15},\n" +
"    {\"name\": \"Thingamajig\", \"price\": 49.99, \"quantity\": 8}\n" +
"  ]\n" +
"}`\n" +
"    }\n" +
"};\n" +
"\n" +
"function loadExample(exampleName) {\n" +
"    const example = examples[exampleName];\n" +
"    if (example) {\n" +
"        transformEditor.setValue(example.transform);\n" +
"        inputEditor.setValue(example.input);\n" +
"        setStatus('Example loaded: ' + exampleName, 'info');\n" +
"        transform();\n" +
"    }\n" +
"}\n" +
"\n" +
"async function transform() {\n" +
"    const transformText = transformEditor.getValue();\n" +
"    const inputText = inputEditor.getValue();\n" +
"    const outputEl = document.getElementById('output');\n" +
"    \n" +
"    if (!transformText.trim()) {\n" +
"        setStatus('Please enter a transform', 'error');\n" +
"        return;\n" +
"    }\n" +
"    \n" +
"    if (!inputText.trim()) {\n" +
"        setStatus('Please enter input JSON', 'error');\n" +
"        return;\n" +
"    }\n" +
"    \n" +
"    setStatus('Transforming...', 'info');\n" +
"    \n" +
"    try {\n" +
"        const response = await fetch('/api/transform', {\n" +
"            method: 'POST',\n" +
"            headers: { 'Content-Type': 'application/json' },\n" +
"            body: JSON.stringify({\n" +
"                transform: transformText,\n" +
"                input: inputText\n" +
"            })\n" +
"        });\n" +
"        \n" +
"        const result = await response.json();\n" +
"        \n" +
"        if (result.success) {\n" +
"            outputEl.innerHTML = '<pre>' + JSON.stringify(result.result, null, 2) + '</pre>';\n" +
"            setStatus('Transform completed successfully', 'success');\n" +
"            document.getElementById('execTime').textContent = 'Executed in ' + result.executionTime + 'ms';\n" +
"        } else {\n" +
"            outputEl.innerHTML = '<pre style=\"color: #ff6b6b;\">Error: ' + escapeHtml(result.error) + '</pre>';\n" +
"            setStatus('Transform failed', 'error');\n" +
"            document.getElementById('execTime').textContent = '';\n" +
"        }\n" +
"    } catch (error) {\n" +
"        outputEl.innerHTML = '<pre style=\"color: #ff6b6b;\">Network error: ' + escapeHtml(error.message) + '</pre>';\n" +
"        setStatus('Network error occurred', 'error');\n" +
"        document.getElementById('execTime').textContent = '';\n" +
"    }\n" +
"}\n" +
"\n" +
"function escapeHtml(text) {\n" +
"    const map = {\n" +
"        '&': '&amp;',\n" +
"        '<': '&lt;',\n" +
"        '>': '&gt;',\n" +
"        '\"': '&quot;',\n" +
"        \"'\": '&#039;'\n" +
"    };\n" +
"    return text.replace(/[&<>\"']/g, function(m) { return map[m]; });\n" +
"}\n"+
"\n" +
"function clearAll() {\n" +
"    transformEditor.setValue('');\n" +
"    inputEditor.setValue('');\n" +
"    document.getElementById('output').innerHTML = '<pre>Output will appear here...</pre>';\n" +
"    setStatus('Ready to transform', 'info');\n" +
"    document.getElementById('execTime').textContent = '';\n" +
"}\n" +
"\n" +
"function formatJson() {\n" +
"    try {\n" +
"        const parsed = JSON.parse(inputEditor.getValue());\n" +
"        inputEditor.setValue(JSON.stringify(parsed, null, 2));\n" +
"        setStatus('JSON formatted successfully', 'success');\n" +
"    } catch (error) {\n" +
"        setStatus('Invalid JSON: ' + error.message, 'error');\n" +
"    }\n" +
"}\n" +
"\n" +
"function copyOutput() {\n" +
"    const outputEl = document.getElementById('output');\n" +
"    const text = outputEl.innerText;\n" +
"    \n" +
"    if (text === 'Output will appear here...') {\n" +
"        setStatus('No output to copy', 'error');\n" +
"        return;\n" +
"    }\n" +
"    \n" +
"    navigator.clipboard.writeText(text).then(function() {\n" +
"        setStatus('Output copied to clipboard', 'success');\n" +
"    }).catch(function(err) {\n" +
"        setStatus('Failed to copy: ' + err.message, 'error');\n" +
"    });\n" +
"}\n" +
"\n"+
"function setStatus(message, type) {\n" +
"    const statusEl = document.getElementById('status');\n" +
"    statusEl.textContent = message;\n" +
"    statusEl.className = 'status-message status-' + type;\n" +
"}\n" +
"</script>";
    }
}
