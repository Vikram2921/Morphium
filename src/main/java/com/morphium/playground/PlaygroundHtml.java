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
"        /* Examples Browser Modal */\n" +
"        .modal {\n" +
"            display: none;\n" +
"            position: fixed;\n" +
"            z-index: 1000;\n" +
"            left: 0;\n" +
"            top: 0;\n" +
"            width: 100%;\n" +
"            height: 100%;\n" +
"            background-color: rgba(0,0,0,0.6);\n" +
"            animation: fadeIn 0.3s;\n" +
"        }\n" +
"        @keyframes fadeIn {\n" +
"            from { opacity: 0; }\n" +
"            to { opacity: 1; }\n" +
"        }\n" +
"        .modal-content {\n" +
"            background-color: white;\n" +
"            margin: 2% auto;\n" +
"            padding: 0;\n" +
"            width: 90%;\n" +
"            max-width: 1400px;\n" +
"            height: 90vh;\n" +
"            border-radius: 12px;\n" +
"            box-shadow: 0 20px 60px rgba(0,0,0,0.5);\n" +
"            display: flex;\n" +
"            flex-direction: column;\n" +
"            animation: slideIn 0.3s;\n" +
"        }\n" +
"        @keyframes slideIn {\n" +
"            from { transform: translateY(-50px); opacity: 0; }\n" +
"            to { transform: translateY(0); opacity: 1; }\n" +
"        }\n" +
"        .modal-header {\n" +
"            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);\n" +
"            color: white;\n" +
"            padding: 20px 30px;\n" +
"            border-radius: 12px 12px 0 0;\n" +
"            display: flex;\n" +
"            justify-content: space-between;\n" +
"            align-items: center;\n" +
"        }\n" +
"        .modal-header h2 {\n" +
"            margin: 0;\n" +
"            font-size: 1.8em;\n" +
"        }\n" +
"        .close {\n" +
"            color: white;\n" +
"            font-size: 35px;\n" +
"            font-weight: bold;\n" +
"            cursor: pointer;\n" +
"            transition: transform 0.2s;\n" +
"        }\n" +
"        .close:hover {\n" +
"            transform: scale(1.2);\n" +
"        }\n" +
"        .modal-body {\n" +
"            flex: 1;\n" +
"            display: flex;\n" +
"            overflow: hidden;\n" +
"        }\n" +
"        .examples-sidebar {\n" +
"            width: 350px;\n" +
"            border-right: 2px solid #e0e0e0;\n" +
"            display: flex;\n" +
"            flex-direction: column;\n" +
"            background: #f8f9fa;\n" +
"        }\n" +
"        .search-box {\n" +
"            padding: 20px;\n" +
"            border-bottom: 2px solid #e0e0e0;\n" +
"        }\n" +
"        .search-box input {\n" +
"            width: 100%;\n" +
"            padding: 12px;\n" +
"            font-size: 14px;\n" +
"            border: 2px solid #ddd;\n" +
"            border-radius: 6px;\n" +
"            transition: border-color 0.2s;\n" +
"        }\n" +
"        .search-box input:focus {\n" +
"            outline: none;\n" +
"            border-color: #667eea;\n" +
"        }\n" +
"        .examples-list {\n" +
"            flex: 1;\n" +
"            overflow-y: auto;\n" +
"            padding: 10px;\n" +
"        }\n" +
"        .category-group {\n" +
"            margin-bottom: 20px;\n" +
"        }\n" +
"        .category-title {\n" +
"            font-weight: 600;\n" +
"            font-size: 0.95em;\n" +
"            color: #667eea;\n" +
"            padding: 8px 12px;\n" +
"            margin-bottom: 5px;\n" +
"            text-transform: uppercase;\n" +
"            letter-spacing: 0.5px;\n" +
"        }\n" +
"        .example-item {\n" +
"            padding: 12px 15px;\n" +
"            margin: 5px 0;\n" +
"            cursor: pointer;\n" +
"            border-radius: 6px;\n" +
"            transition: all 0.2s;\n" +
"            background: white;\n" +
"            border: 1px solid #e0e0e0;\n" +
"        }\n" +
"        .example-item:hover {\n" +
"            background: #f0f0ff;\n" +
"            border-color: #667eea;\n" +
"            transform: translateX(5px);\n" +
"        }\n" +
"        .example-item.active {\n" +
"            background: #667eea;\n" +
"            color: white;\n" +
"            border-color: #667eea;\n" +
"        }\n" +
"        .example-item-title {\n" +
"            font-weight: 600;\n" +
"            font-size: 0.95em;\n" +
"            margin-bottom: 3px;\n" +
"        }\n" +
"        .example-item-desc {\n" +
"            font-size: 0.85em;\n" +
"            opacity: 0.8;\n" +
"        }\n" +
"        .example-item.active .example-item-desc {\n" +
"            opacity: 0.9;\n" +
"        }\n" +
"        .example-detail {\n" +
"            flex: 1;\n" +
"            padding: 30px;\n" +
"            overflow-y: auto;\n" +
"        }\n" +
"        .example-detail h3 {\n" +
"            margin-top: 0;\n" +
"            margin-bottom: 10px;\n" +
"            color: #333;\n" +
"            font-size: 1.5em;\n" +
"        }\n" +
"        .example-detail p {\n" +
"            color: #666;\n" +
"            margin-bottom: 20px;\n" +
"            line-height: 1.6;\n" +
"        }\n" +
"        .example-code-block {\n" +
"            margin: 20px 0;\n" +
"        }\n" +
"        .example-code-block h4 {\n" +
"            margin-bottom: 10px;\n" +
"            color: #555;\n" +
"            font-size: 1.1em;\n" +
"        }\n" +
"        .example-code-block pre {\n" +
"            background: #1e1e1e;\n" +
"            color: #d4d4d4;\n" +
"            padding: 15px;\n" +
"            border-radius: 6px;\n" +
"            overflow-x: auto;\n" +
"            font-size: 13px;\n" +
"        }\n" +
"        .load-example-btn {\n" +
"            padding: 12px 30px;\n" +
"            font-size: 15px;\n" +
"            font-weight: 600;\n" +
"            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);\n" +
"            color: white;\n" +
"            border: none;\n" +
"            border-radius: 6px;\n" +
"            cursor: pointer;\n" +
"            margin-top: 20px;\n" +
"            transition: transform 0.2s;\n" +
"        }\n" +
"        .load-example-btn:hover {\n" +
"            transform: translateY(-2px);\n" +
"            box-shadow: 0 4px 12px rgba(102, 126, 234, 0.4);\n" +
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
"            .modal-body {\n" +
"                flex-direction: column;\n" +
"            }\n" +
"            .examples-sidebar {\n" +
"                width: 100%;\n" +
"                max-height: 40vh;\n" +
"                border-right: none;\n" +
"                border-bottom: 2px solid #e0e0e0;\n" +
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
"                <button class=\"btn-secondary\" onclick=\"openExamplesBrowser()\">📚 Browse Examples</button>\n" +
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
"    \n" +
"    <!-- Examples Browser Modal -->\n" +
"    <div id=\"examplesModal\" class=\"modal\">\n" +
"        <div class=\"modal-content\">\n" +
"            <div class=\"modal-header\">\n" +
"                <h2>📚 Function Examples & Features</h2>\n" +
"                <span class=\"close\" onclick=\"closeExamplesBrowser()\">&times;</span>\n" +
"            </div>\n" +
"            <div class=\"modal-body\">\n" +
"                <div class=\"examples-sidebar\">\n" +
"                    <div class=\"search-box\">\n" +
"                        <input type=\"text\" id=\"exampleSearch\" placeholder=\"🔍 Search functions, features...\" onkeyup=\"filterExamples()\">\n" +
"                    </div>\n" +
"                    <div class=\"examples-list\" id=\"examplesList\"></div>\n" +
"                </div>\n" +
"                <div class=\"example-detail\" id=\"exampleDetail\">\n" +
"                    <h3>Select an example</h3>\n" +
"                    <p>Choose a function or feature from the list to see its documentation and example.</p>\n" +
"                </div>\n" +
"            </div>\n" +
"        </div>\n" +
"    </div>\n" +
"    \n" +
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
"\n" +
"// Examples Browser System\n" +
"const comprehensiveExamples = {\n" +
"    // Type Functions (Week 1-2)\n" +
"    isString: {\n" +
"        category: 'Type Functions',\n" +
"        title: 'isString() - Check String',\n" +
"        description: 'Check if value is a string',\n" +
"        transform: `{\n" +
"  nameCheck: isString($.name),\n" +
"  ageCheck: isString($.age),\n" +
"  emailCheck: isString($.email)\n" +
"}`,\n" +
"        input: `{\n" +
"  \"name\": \"Alice\",\n" +
"  \"age\": 25,\n" +
"  \"email\": \"alice@example.com\"\n" +
"}`\n" +
"    },\n" +
"    isNumber: {\n" +
"        category: 'Type Functions',\n" +
"        title: 'isNumber() - Check Number',\n" +
"        description: 'Check if value is a number',\n" +
"        transform: `{\n" +
"  ageIsNum: isNumber($.age),\n" +
"  nameIsNum: isNumber($.name),\n" +
"  priceIsNum: isNumber($.price)\n" +
"}`,\n" +
"        input: `{\n" +
"  \"name\": \"Product\",\n" +
"  \"age\": 25,\n" +
"  \"price\": 99.99\n" +
"}`\n" +
"    },\n" +
"    isArray: {\n" +
"        category: 'Type Functions',\n" +
"        title: 'isArray() - Check Array',\n" +
"        description: 'Check if value is an array',\n" +
"        transform: `{\n" +
"  itemsCheck: isArray($.items),\n" +
"  nameCheck: isArray($.name),\n" +
"  tagsCheck: isArray($.tags)\n" +
"}`,\n" +
"        input: `{\n" +
"  \"name\": \"Product\",\n" +
"  \"items\": [1, 2, 3],\n" +
"  \"tags\": [\"new\", \"sale\"]\n" +
"}`\n" +
"    },\n" +
"    typeOf: {\n" +
"        category: 'Type Functions',\n" +
"        title: 'typeOf() - Get Type',\n" +
"        description: 'Get the type of a value',\n" +
"        transform: `{\n" +
"  types: {\n" +
"    name: typeOf($.name),\n" +
"    age: typeOf($.age),\n" +
"    active: typeOf($.active),\n" +
"    items: typeOf($.items)\n" +
"  }\n" +
"}`,\n" +
"        input: `{\n" +
"  \"name\": \"Alice\",\n" +
"  \"age\": 25,\n" +
"  \"active\": true,\n" +
"  \"items\": [1, 2, 3]\n" +
"}`\n" +
"    },\n" +
"    toInt: {\n" +
"        category: 'Type Functions',\n" +
"        title: 'toInt() - Convert to Integer',\n" +
"        description: 'Convert value to integer',\n" +
"        transform: `{\n" +
"  age: toInt($.age),\n" +
"  count: toInt($.count),\n" +
"  price: toInt($.price)\n" +
"}`,\n" +
"        input: `{\n" +
"  \"age\": \"25\",\n" +
"  \"count\": \"100\",\n" +
"  \"price\": \"99.99\"\n" +
"}`\n" +
"    },\n" +
"    isEmpty: {\n" +
"        category: 'Type Functions',\n" +
"        title: 'isEmpty() - Check Empty',\n" +
"        description: 'Check if value is empty (null, empty string, empty array/object)',\n" +
"        transform: `{\n" +
"  emptyStr: isEmpty($.emptyStr),\n" +
"  emptyArr: isEmpty($.emptyArr),\n" +
"  name: isEmpty($.name),\n" +
"  nullVal: isEmpty($.nullVal)\n" +
"}`,\n" +
"        input: `{\n" +
"  \"emptyStr\": \"\",\n" +
"  \"emptyArr\": [],\n" +
"  \"name\": \"Alice\",\n" +
"  \"nullVal\": null\n" +
"}`\n" +
"    },\n" +
"    // Null Safety (Week 3-4)\n" +
"    coalesce: {\n" +
"        category: 'Null Safety',\n" +
"        title: 'coalesce() - First Non-Null',\n" +
"        description: 'Returns the first non-null value from a list of arguments',\n" +
"        transform: `{\n" +
"  name: coalesce($.nickname, $.firstName, $.fullName, \"Guest\"),\n" +
"  email: coalesce($.email, $.contact.email, \"no-email@example.com\")\n" +
"}`,\n" +
"        input: `{\n" +
"  \"firstName\": \"John\",\n" +
"  \"contact\": { \"email\": \"john@example.com\" }\n" +
"}`\n" +
"    },\n" +
"    ifNull: {\n" +
"        category: 'Null Safety',\n" +
"        title: 'ifNull() - Default Value',\n" +
"        description: 'Return default if value is null',\n" +
"        transform: `{\n" +
"  name: ifNull($.name, \"Unknown\"),\n" +
"  age: ifNull($.age, 0),\n" +
"  city: ifNull($.city, \"N/A\")\n" +
"}`,\n" +
"        input: `{\n" +
"  \"name\": \"Alice\",\n" +
"  \"age\": null\n" +
"}`\n" +
"    },\n" +
"    tryGet: {\n" +
"        category: 'Null Safety',\n" +
"        title: 'tryGet() - Try Get with Default',\n" +
"        description: 'Try to get value with fallback',\n" +
"        transform: `{\n" +
"  phone: tryGet($.user, \"contact.phone\", \"N/A\"),\n" +
"  zip: tryGet($.user, \"address.zip\", \"00000\")\n" +
"}`,\n" +
"        input: `{\n" +
"  \"user\": {\n" +
"    \"contact\": { \"phone\": \"555-1234\" }\n" +
"  }\n" +
"}`\n" +
"    },\n" +
"    firstValid: {\n" +
"        category: 'Null Safety',\n" +
"        title: 'firstValid() - First Valid Array Item',\n" +
"        description: 'Get first non-null/non-empty value from array',\n" +
"        transform: `{\n" +
"  value: firstValid([$.a, $.b, $.c, \"default\"])\n" +
"}`,\n" +
"        input: `{\n" +
"  \"a\": null,\n" +
"  \"b\": \"\",\n" +
"  \"c\": \"valid\"\n" +
"}`\n" +
"    },\n" +
"    isNullOrEmpty: {\n" +
"        category: 'Null Safety',\n" +
"        title: 'isNullOrEmpty() - Check Null/Empty',\n" +
"        description: 'Check if value is null or empty',\n" +
"        transform: `{\n" +
"  nameEmpty: isNullOrEmpty($.name),\n" +
"  emailEmpty: isNullOrEmpty($.email),\n" +
"  phoneEmpty: isNullOrEmpty($.phone)\n" +
"}`,\n" +
"        input: `{\n" +
"  \"name\": \"Alice\",\n" +
"  \"email\": \"\",\n" +
"  \"phone\": null\n" +
"}`\n" +
"    },\n" +
"    safeGet: {\n" +
"        category: 'Null Safety',\n" +
"        title: 'safeGet() - Safe Property Access',\n" +
"        description: 'Safely access nested properties without errors',\n" +
"        transform: `{\n" +
"  city: safeGet($.user, \"address.city\"),\n" +
"  phone: safeGet($.user, \"contact.phone\"),\n" +
"  zip: safeGet($.user, \"address.zipcode\")\n" +
"}`,\n" +
"        input: `{\n" +
"  \"user\": {\n" +
"    \"address\": { \"city\": \"NYC\" }\n" +
"  }\n" +
"}`\n" +
"    },\n" +
"    removeNulls: {\n" +
"        category: 'Null Safety',\n" +
"        title: 'removeNulls() - Clean Object',\n" +
"        description: 'Remove all null properties from an object',\n" +
"        transform: `{\n" +
"  cleaned: removeNulls($.data)\n" +
"}`,\n" +
"        input: `{\n" +
"  \"data\": {\n" +
"    \"name\": \"Alice\",\n" +
"    \"age\": null,\n" +
"    \"email\": \"alice@test.com\",\n" +
"    \"phone\": null\n" +
"  }\n" +
"}`\n" +
"    },\n" +
"    // Path Operations (Week 5-6)\n" +
"    getIn: {\n" +
"        category: 'Path Operations',\n" +
"        title: 'getIn() - Deep Path Access',\n" +
"        description: 'Get value at deep path with optional default',\n" +
"        transform: `{\n" +
"  city: getIn($, \"user.address.city\", \"Unknown\"),\n" +
"  name: getIn($, [\"user\", \"profile\", \"name\"], \"Guest\"),\n" +
"  age: getIn($, \"user.age\", 0)\n" +
"}`,\n" +
"        input: `{\n" +
"  \"user\": {\n" +
"    \"address\": { \"city\": \"NYC\" },\n" +
"    \"profile\": { \"name\": \"Alice\" }\n" +
"  }\n" +
"}`\n" +
"    },\n" +
"    setIn: {\n" +
"        category: 'Path Operations',\n" +
"        title: 'setIn() - Set Deep Path',\n" +
"        description: 'Set value at deep path (auto-creates structure)',\n" +
"        transform: `{\n" +
"  result: setIn(setIn({}, \"user.name\", \"Bob\"), \"user.age\", 30)\n" +
"}`,\n" +
"        input: `{}`\n" +
"    },\n" +
"    hasPath: {\n" +
"        category: 'Path Operations',\n" +
"        title: 'hasPath() - Check Path Exists',\n" +
"        description: 'Check if a path exists in an object',\n" +
"        transform: `{\n" +
"  hasEmail: hasPath($, \"user.email\"),\n" +
"  hasPhone: hasPath($, \"user.contact.phone\"),\n" +
"  hasCity: hasPath($, \"user.address.city\")\n" +
"}`,\n" +
"        input: `{\n" +
"  \"user\": {\n" +
"    \"email\": \"test@example.com\",\n" +
"    \"address\": { \"city\": \"LA\" }\n" +
"  }\n" +
"}`\n" +
"    },\n" +
"    deleteIn: {\n" +
"        category: 'Path Operations',\n" +
"        title: 'deleteIn() - Delete Deep Path',\n" +
"        description: 'Delete value at deep path',\n" +
"        transform: `{\n" +
"  result: deleteIn($, \"user.password\")\n" +
"}`,\n" +
"        input: `{\n" +
"  \"user\": {\n" +
"    \"name\": \"Alice\",\n" +
"    \"password\": \"secret\",\n" +
"    \"email\": \"alice@example.com\"\n" +
"  }\n" +
"}`\n" +
"    },\n" +
"    getPaths: {\n" +
"        category: 'Path Operations',\n" +
"        title: 'getPaths() - List All Paths',\n" +
"        description: 'Get all paths in an object',\n" +
"        transform: `{\n" +
"  paths: getPaths($.data)\n" +
"}`,\n" +
"        input: `{\n" +
"  \"data\": {\n" +
"    \"user\": {\n" +
"      \"name\": \"Bob\",\n" +
"      \"age\": 30\n" +
"    },\n" +
"    \"active\": true\n" +
"  }\n" +
"}`\n" +
"    },\n" +
"    pathDepth: {\n" +
"        category: 'Path Operations',\n" +
"        title: 'pathDepth() - Get Path Depth',\n" +
"        description: 'Get the maximum depth of nested paths',\n" +
"        transform: `{\n" +
"  depth: pathDepth($.nested)\n" +
"}`,\n" +
"        input: `{\n" +
"  \"nested\": {\n" +
"    \"level1\": {\n" +
"      \"level2\": {\n" +
"        \"level3\": \"value\"\n" +
"      }\n" +
"    }\n" +
"  }\n" +
"}`\n" +
"    },\n" +
"    // String Utilities (Week 7-8)\n" +
"    titleCase: {\n" +
"        category: 'String Functions',\n" +
"        title: 'titleCase() - Title Case',\n" +
"        description: 'Convert string to Title Case',\n" +
"        transform: `{\n" +
"  title: titleCase($.text),\n" +
"  name: titleCase($.name)\n" +
"}`,\n" +
"        input: `{\n" +
"  \"text\": \"hello world from morphium\",\n" +
"  \"name\": \"john doe\"\n" +
"}`\n" +
"    },\n" +
"    matches: {\n" +
"        category: 'String Functions',\n" +
"        title: 'matches() - Regex Validation',\n" +
"        description: 'Test if string matches regex pattern',\n" +
"        transform: `{\n" +
"  validEmail: matches($.email, \"^[^@]+@[^@]+\\\\.[^@]+$\"),\n" +
"  validPhone: matches($.phone, \"^\\\\d{10}$\"),\n" +
"  hasNumber: matches($.text, \".*\\\\d.*\")\n" +
"}`,\n" +
"        input: `{\n" +
"  \"email\": \"test@example.com\",\n" +
"  \"phone\": \"1234567890\",\n" +
"  \"text\": \"hello123\"\n" +
"}`\n" +
"    },\n" +
"    padStart: {\n" +
"        category: 'String Functions',\n" +
"        title: 'padStart() - Pad String',\n" +
"        description: 'Pad string at the start to reach target length',\n" +
"        transform: `{\n" +
"  id: padStart(toString($.id), 8, \"0\"),\n" +
"  code: padStart($.code, 5, \"#\")\n" +
"}`,\n" +
"        input: `{\n" +
"  \"id\": 42,\n" +
"  \"code\": \"ABC\"\n" +
"}`\n" +
"    },\n" +
"    contains: {\n" +
"        category: 'String Functions',\n" +
"        title: 'contains() - Check Substring',\n" +
"        description: 'Check if string contains substring',\n" +
"        transform: `{\n" +
"  hasAt: contains($.email, \"@\"),\n" +
"  hasWorld: contains($.text, \"world\")\n" +
"}`,\n" +
"        input: `{\n" +
"  \"email\": \"test@example.com\",\n" +
"  \"text\": \"hello world\"\n" +
"}`\n" +
"    },\n" +
"    startsWith: {\n" +
"        category: 'String Functions',\n" +
"        title: 'startsWith() - Check Prefix',\n" +
"        description: 'Check if string starts with prefix',\n" +
"        transform: `{\n" +
"  isHttps: startsWith($.url, \"https\"),\n" +
"  startsHello: startsWith($.text, \"Hello\")\n" +
"}`,\n" +
"        input: `{\n" +
"  \"url\": \"https://example.com\",\n" +
"  \"text\": \"Hello World\"\n" +
"}`\n" +
"    },\n" +
"    endsWith: {\n" +
"        category: 'String Functions',\n" +
"        title: 'endsWith() - Check Suffix',\n" +
"        description: 'Check if string ends with suffix',\n" +
"        transform: `{\n" +
"  isJpg: endsWith($.filename, \".jpg\"),\n" +
"  isPdf: endsWith($.document, \".pdf\")\n" +
"}`,\n" +
"        input: `{\n" +
"  \"filename\": \"photo.jpg\",\n" +
"  \"document\": \"report.pdf\"\n" +
"}`\n" +
"    },\n" +
"    substring: {\n" +
"        category: 'String Functions',\n" +
"        title: 'substring() - Extract Substring',\n" +
"        description: 'Extract substring from string',\n" +
"        transform: `{\n" +
"  first3: substring($.text, 0, 3),\n" +
"  last3: substring($.text, -3)\n" +
"}`,\n" +
"        input: `{\n" +
"  \"text\": \"Hello World\"\n" +
"}`\n" +
"    },\n" +
"    capitalize: {\n" +
"        category: 'String Functions',\n" +
"        title: 'capitalize() - Capitalize First Letter',\n" +
"        description: 'Capitalize first letter of string',\n" +
"        transform: `{\n" +
"  name: capitalize($.name),\n" +
"  city: capitalize($.city)\n" +
"}`,\n" +
"        input: `{\n" +
"  \"name\": \"alice\",\n" +
"  \"city\": \"new york\"\n" +
"}`\n" +
"    },\n" +
"    cleanWhitespace: {\n" +
"        category: 'String Functions',\n" +
"        title: 'cleanWhitespace() - Clean Whitespace',\n" +
"        description: 'Remove extra whitespace from string',\n" +
"        transform: `{\n" +
"  cleaned: cleanWhitespace($.text)\n" +
"}`,\n" +
"        input: `{\n" +
"  \"text\": \"  hello    world  \"\n" +
"}`\n" +
"    },\n" +
"    repeat: {\n" +
"        category: 'String Functions',\n" +
"        title: 'repeat() - Repeat String',\n" +
"        description: 'Repeat string n times',\n" +
"        transform: `{\n" +
"  repeated: repeat(\"*\", 5),\n" +
"  line: repeat(\"-\", 10)\n" +
"}`,\n" +
"        input: `{}`\n" +
"    },\n" +
"    reverseStr: {\n" +
"        category: 'String Functions',\n" +
"        title: 'reverseStr() - Reverse String',\n" +
"        description: 'Reverse a string',\n" +
"        transform: `{\n" +
"  reversed: reverseStr($.text)\n" +
"}`,\n" +
"        input: `{\n" +
"  \"text\": \"Hello World\"\n" +
"}`\n" +
"    },\n" +
"    indexOf: {\n" +
"        category: 'String Functions',\n" +
"        title: 'indexOf() - Find Position',\n" +
"        description: 'Find position of substring',\n" +
"        transform: `{\n" +
"  atPos: indexOf($.email, \"@\"),\n" +
"  worldPos: indexOf($.text, \"world\")\n" +
"}`,\n" +
"        input: `{\n" +
"  \"email\": \"user@example.com\",\n" +
"  \"text\": \"hello world\"\n" +
"}`\n" +
"    },\n" +
"    padEnd: {\n" +
"        category: 'String Functions',\n" +
"        title: 'padEnd() - Pad End',\n" +
"        description: 'Pad string at the end to reach target length',\n" +
"        transform: `{\n" +
"  padded: padEnd($.code, 10, \"-\")\n" +
"}`,\n" +
"        input: `{\n" +
"  \"code\": \"ABC\"\n" +
"}`\n" +
"    },\n" +
"    // Collection Operations (Week 9-10)\n" +
"    chunk: {\n" +
"        category: 'Collection Functions',\n" +
"        title: 'chunk() - Split Into Batches',\n" +
"        description: 'Split array into chunks of specified size',\n" +
"        transform: `{\n" +
"  batches: chunk($.items, 3)\n" +
"}`,\n" +
"        input: `{\n" +
"  \"items\": [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
"}`\n" +
"    },\n" +
"    unique: {\n" +
"        category: 'Collection Functions',\n" +
"        title: 'unique() - Remove Duplicates',\n" +
"        description: 'Get unique values from array',\n" +
"        transform: `{\n" +
"  distinct: unique($.values),\n" +
"  uniqueIds: unique($.items)\n" +
"}`,\n" +
"        input: `{\n" +
"  \"values\": [1, 2, 2, 3, 3, 3, 4, 5, 5],\n" +
"  \"items\": [\"A\", \"B\", \"A\", \"C\", \"B\"]\n" +
"}`\n" +
"    },\n" +
"    cumSum: {\n" +
"        category: 'Collection Functions',\n" +
"        title: 'cumSum() - Cumulative Sum',\n" +
"        description: 'Calculate cumulative sum of array',\n" +
"        transform: `{\n" +
"  running: cumSum($.sales),\n" +
"  total: sum($.sales)\n" +
"}`,\n" +
"        input: `{\n" +
"  \"sales\": [10, 20, 30, 40]\n" +
"}`\n" +
"    },\n" +
"    movingAvg: {\n" +
"        category: 'Collection Functions',\n" +
"        title: 'movingAvg() - Moving Average',\n" +
"        description: 'Calculate moving average with window size',\n" +
"        transform: `{\n" +
"  trend: movingAvg($.prices, 3)\n" +
"}`,\n" +
"        input: `{\n" +
"  \"prices\": [10, 20, 30, 40, 50, 60]\n" +
"}`\n" +
"    },\n" +
"    zip: {\n" +
"        category: 'Collection Functions',\n" +
"        title: 'zip() - Combine Arrays',\n" +
"        description: 'Zip multiple arrays together',\n" +
"        transform: `{\n" +
"  combined: zip($.names, $.ages, $.cities)\n" +
"}`,\n" +
"        input: `{\n" +
"  \"names\": [\"Alice\", \"Bob\", \"Charlie\"],\n" +
"  \"ages\": [25, 30, 35],\n" +
"  \"cities\": [\"NYC\", \"LA\", \"Chicago\"]\n" +
"}`\n" +
"    },\n" +
"    compact: {\n" +
"        category: 'Collection Functions',\n" +
"        title: 'compact() - Remove Falsy Values',\n" +
"        description: 'Remove null, undefined, false, 0, NaN, empty string',\n" +
"        transform: `{\n" +
"  cleaned: compact($.values)\n" +
"}`,\n" +
"        input: `{\n" +
"  \"values\": [1, null, 2, false, 3, \"\", 0, 4]\n" +
"}`\n" +
"    },\n" +
"    uniqueBy: {\n" +
"        category: 'Collection Functions',\n" +
"        title: 'uniqueBy() - Unique By Property',\n" +
"        description: 'Get unique items by a property',\n" +
"        transform: `{\n" +
"  uniqueUsers: uniqueBy($.users, \"id\")\n" +
"}`,\n" +
"        input: `{\n" +
"  \"users\": [\n" +
"    {\"id\": 1, \"name\": \"Alice\"},\n" +
"    {\"id\": 2, \"name\": \"Bob\"},\n" +
"    {\"id\": 1, \"name\": \"Alice2\"}\n" +
"  ]\n" +
"}`\n" +
"    },\n" +
"    unzip: {\n" +
"        category: 'Collection Functions',\n" +
"        title: 'unzip() - Unzip Arrays',\n" +
"        description: 'Unzip array of arrays',\n" +
"        transform: `{\n" +
"  separated: unzip($.combined)\n" +
"}`,\n" +
"        input: `{\n" +
"  \"combined\": [[\"Alice\", 25], [\"Bob\", 30], [\"Charlie\", 35]]\n" +
"}`\n" +
"    },\n" +
"    diff: {\n" +
"        category: 'Collection Functions',\n" +
"        title: 'diff() - Array Differences',\n" +
"        description: 'Calculate differences between consecutive elements',\n" +
"        transform: `{\n" +
"  changes: diff($.values)\n" +
"}`,\n" +
"        input: `{\n" +
"  \"values\": [10, 15, 12, 20, 18]\n" +
"}`\n" +
"    },\n" +
"    flatten: {\n" +
"        category: 'Collection Functions',\n" +
"        title: 'flatten() - Flatten Nested Arrays',\n" +
"        description: 'Flatten nested arrays to specified depth',\n" +
"        transform: `{\n" +
"  flat: flatten($.nested, 2)\n" +
"}`,\n" +
"        input: `{\n" +
"  \"nested\": [[1, 2], [3, [4, 5]], [6, [7, [8]]]]\n" +
"}`\n" +
"    },\n" +
"    take: {\n" +
"        category: 'Collection Functions',\n" +
"        title: 'take() - Take First N',\n" +
"        description: 'Take first n elements from array',\n" +
"        transform: `{\n" +
"  top3: take($.items, 3)\n" +
"}`,\n" +
"        input: `{\n" +
"  \"items\": [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
"}`\n" +
"    },\n" +
"    drop: {\n" +
"        category: 'Collection Functions',\n" +
"        title: 'drop() - Drop First N',\n" +
"        description: 'Drop first n elements from array',\n" +
"        transform: `{\n" +
"  remaining: drop($.items, 3)\n" +
"}`,\n" +
"        input: `{\n" +
"  \"items\": [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
"}`\n" +
"    },\n" +
"    // Object Transform (Week 11-12)\n" +
"    pick: {\n" +
"        category: 'Object Transform',\n" +
"        title: 'pick() - Select Keys',\n" +
"        description: 'Pick specific keys from an object',\n" +
"        transform: `{\n" +
"  public: pick($.user, [\"name\", \"email\", \"avatar\"])\n" +
"}`,\n" +
"        input: `{\n" +
"  \"user\": {\n" +
"    \"name\": \"Alice\",\n" +
"    \"email\": \"alice@example.com\",\n" +
"    \"password\": \"secret123\",\n" +
"    \"avatar\": \"avatar.jpg\",\n" +
"    \"internal_id\": \"123\"\n" +
"  }\n" +
"}`\n" +
"    },\n" +
"    omit: {\n" +
"        category: 'Object Transform',\n" +
"        title: 'omit() - Remove Keys',\n" +
"        description: 'Remove specific keys from an object',\n" +
"        transform: `{\n" +
"  safe: omit($.user, [\"password\", \"internal_id\", \"ssn\"])\n" +
"}`,\n" +
"        input: `{\n" +
"  \"user\": {\n" +
"    \"name\": \"Bob\",\n" +
"    \"email\": \"bob@example.com\",\n" +
"    \"password\": \"secret\",\n" +
"    \"internal_id\": \"456\"\n" +
"  }\n" +
"}`\n" +
"    },\n" +
"    toCamelCase: {\n" +
"        category: 'Object Transform',\n" +
"        title: 'toCamelCase() - Convert Keys',\n" +
"        description: 'Convert all keys to camelCase (nested)',\n" +
"        transform: `{\n" +
"  result: toCamelCase($.data)\n" +
"}`,\n" +
"        input: `{\n" +
"  \"data\": {\n" +
"    \"first_name\": \"Charlie\",\n" +
"    \"last_name\": \"Brown\",\n" +
"    \"email_address\": \"charlie@example.com\"\n" +
"  }\n" +
"}`\n" +
"    },\n" +
"    flattenObj: {\n" +
"        category: 'Object Transform',\n" +
"        title: 'flattenObj() - Flatten Nested',\n" +
"        description: 'Flatten nested object to dot notation',\n" +
"        transform: `{\n" +
"  flat: flattenObj($.nested)\n" +
"}`,\n" +
"        input: `{\n" +
"  \"nested\": {\n" +
"    \"user\": {\n" +
"      \"name\": \"David\",\n" +
"      \"address\": {\n" +
"        \"city\": \"NYC\",\n" +
"        \"zip\": \"10001\"\n" +
"      }\n" +
"    }\n" +
"  }\n" +
"}`\n" +
"    },\n" +
"    invert: {\n" +
"        category: 'Object Transform',\n" +
"        title: 'invert() - Swap Keys/Values',\n" +
"        description: 'Swap object keys and values',\n" +
"        transform: `{\n" +
"  inverted: invert($.roles)\n" +
"}`,\n" +
"        input: `{\n" +
"  \"roles\": {\n" +
"    \"admin\": \"1\",\n" +
"    \"user\": \"2\",\n" +
"    \"guest\": \"3\"\n" +
"  }\n" +
"}`\n" +
"    },\n" +
"    mapKeys: {\n" +
"        category: 'Object Transform',\n" +
"        title: 'mapKeys() - Transform Keys',\n" +
"        description: 'Transform all object keys',\n" +
"        transform: `{\n" +
"  upper: mapKeys($.data, \"upper\"),\n" +
"  lower: mapKeys($.data, \"lower\")\n" +
"}`,\n" +
"        input: `{\n" +
"  \"data\": {\n" +
"    \"firstName\": \"Alice\",\n" +
"    \"lastName\": \"Smith\"\n" +
"  }\n" +
"}`\n" +
"    },\n" +
"    toSnakeCase: {\n" +
"        category: 'Object Transform',\n" +
"        title: 'toSnakeCase() - Convert to snake_case',\n" +
"        description: 'Convert all keys to snake_case (nested)',\n" +
"        transform: `{\n" +
"  result: toSnakeCase($.data)\n" +
"}`,\n" +
"        input: `{\n" +
"  \"data\": {\n" +
"    \"firstName\": \"Emma\",\n" +
"    \"lastName\": \"Wilson\",\n" +
"    \"emailAddress\": \"emma@example.com\"\n" +
"  }\n" +
"}`\n" +
"    },\n" +
"    toKebabCase: {\n" +
"        category: 'Object Transform',\n" +
"        title: 'toKebabCase() - Convert to kebab-case',\n" +
"        description: 'Convert all keys to kebab-case (nested)',\n" +
"        transform: `{\n" +
"  result: toKebabCase($.data)\n" +
"}`,\n" +
"        input: `{\n" +
"  \"data\": {\n" +
"    \"firstName\": \"Frank\",\n" +
"    \"lastName\": \"Brown\"\n" +
"  }\n" +
"}`\n" +
"    },\n" +
"    unflattenObj: {\n" +
"        category: 'Object Transform',\n" +
"        title: 'unflattenObj() - Unflatten to Nested',\n" +
"        description: 'Convert dot notation to nested object',\n" +
"        transform: `{\n" +
"  nested: unflattenObj($.flat)\n" +
"}`,\n" +
"        input: `{\n" +
"  \"flat\": {\n" +
"    \"user.name\": \"Grace\",\n" +
"    \"user.address.city\": \"NYC\",\n" +
"    \"user.address.zip\": \"10001\"\n" +
"  }\n" +
"}`\n" +
"    },\n" +
"    deepClone: {\n" +
"        category: 'Object Transform',\n" +
"        title: 'deepClone() - Deep Copy',\n" +
"        description: 'Create a deep copy of an object',\n" +
"        transform: `{\n" +
"  cloned: deepClone($.original)\n" +
"}`,\n" +
"        input: `{\n" +
"  \"original\": {\n" +
"    \"name\": \"Test\",\n" +
"    \"nested\": {\n" +
"      \"value\": 42\n" +
"    }\n" +
"  }\n" +
"}`\n" +
"    },\n" +
"    // Built-in Utility Functions\n" +
"    split: {\n" +
"        category: 'Built-in Functions',\n" +
"        title: 'split() - Split String',\n" +
"        description: 'Split string by delimiter',\n" +
"        transform: `{\n" +
"  tags: split($.tags, \",\"),\n" +
"  words: split($.sentence, \" \")\n" +
"}`,\n" +
"        input: `{\n" +
"  \"tags\": \"javascript,java,python\",\n" +
"  \"sentence\": \"Hello World\"\n" +
"}`\n" +
"    },\n" +
"    join: {\n" +
"        category: 'Built-in Functions',\n" +
"        title: 'join() - Join Array',\n" +
"        description: 'Join array elements into string',\n" +
"        transform: `{\n" +
"  csv: join($.items, \", \"),\n" +
"  path: join($.parts, \"/\")\n" +
"}`,\n" +
"        input: `{\n" +
"  \"items\": [\"apple\", \"banana\", \"cherry\"],\n" +
"  \"parts\": [\"home\", \"user\", \"documents\"]\n" +
"}`\n" +
"    },\n" +
"    upper: {\n" +
"        category: 'Built-in Functions',\n" +
"        title: 'upper() - Uppercase',\n" +
"        description: 'Convert string to uppercase',\n" +
"        transform: `{\n" +
"  uppercase: upper($.text)\n" +
"}`,\n" +
"        input: `{\n" +
"  \"text\": \"hello world\"\n" +
"}`\n" +
"    },\n" +
"    lower: {\n" +
"        category: 'Built-in Functions',\n" +
"        title: 'lower() - Lowercase',\n" +
"        description: 'Convert string to lowercase',\n" +
"        transform: `{\n" +
"  lowercase: lower($.text)\n" +
"}`,\n" +
"        input: `{\n" +
"  \"text\": \"HELLO WORLD\"\n" +
"}`\n" +
"    },\n" +
"    trim: {\n" +
"        category: 'Built-in Functions',\n" +
"        title: 'trim() - Trim Whitespace',\n" +
"        description: 'Remove leading/trailing whitespace',\n" +
"        transform: `{\n" +
"  trimmed: trim($.text)\n" +
"}`,\n" +
"        input: `{\n" +
"  \"text\": \"  hello world  \"\n" +
"}`\n" +
"    },\n" +
"    replace: {\n" +
"        category: 'Built-in Functions',\n" +
"        title: 'replace() - Replace Text',\n" +
"        description: 'Replace text in string',\n" +
"        transform: `{\n" +
"  replaced: replace($.text, \"world\", \"universe\")\n" +
"}`,\n" +
"        input: `{\n" +
"  \"text\": \"hello world\"\n" +
"}`\n" +
"    },\n" +
"    toNumber: {\n" +
"        category: 'Built-in Functions',\n" +
"        title: 'toNumber() - Convert to Number',\n" +
"        description: 'Convert value to number',\n" +
"        transform: `{\n" +
"  num1: toNumber($.str1),\n" +
"  num2: toNumber($.str2)\n" +
"}`,\n" +
"        input: `{\n" +
"  \"str1\": \"42\",\n" +
"  \"str2\": \"3.14\"\n" +
"}`\n" +
"    },\n" +
"    toString: {\n" +
"        category: 'Built-in Functions',\n" +
"        title: 'toString() - Convert to String',\n" +
"        description: 'Convert value to string',\n" +
"        transform: `{\n" +
"  str1: toString($.num),\n" +
"  str2: toString($.bool)\n" +
"}`,\n" +
"        input: `{\n" +
"  \"num\": 42,\n" +
"  \"bool\": true\n" +
"}`\n" +
"    },\n" +
"    reverse: {\n" +
"        category: 'Built-in Functions',\n" +
"        title: 'reverse() - Reverse Array',\n" +
"        description: 'Reverse an array',\n" +
"        transform: `{\n" +
"  reversed: reverse($.items)\n" +
"}`,\n" +
"        input: `{\n" +
"  \"items\": [1, 2, 3, 4, 5]\n" +
"}`\n" +
"    },\n" +
"    concat: {\n" +
"        category: 'Built-in Functions',\n" +
"        title: 'concat() - Concatenate Arrays',\n" +
"        description: 'Concatenate multiple arrays',\n" +
"        transform: `{\n" +
"  combined: concat($.arr1, $.arr2, $.arr3)\n" +
"}`,\n" +
"        input: `{\n" +
"  \"arr1\": [1, 2],\n" +
"  \"arr2\": [3, 4],\n" +
"  \"arr3\": [5, 6]\n" +
"}`\n" +
"    },\n" +
"    slice: {\n" +
"        category: 'Built-in Functions',\n" +
"        title: 'slice() - Slice Array',\n" +
"        description: 'Extract slice of array',\n" +
"        transform: `{\n" +
"  middle: slice($.items, 2, 5)\n" +
"}`,\n" +
"        input: `{\n" +
"  \"items\": [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
"}`\n" +
"    },\n" +
"    keys: {\n" +
"        category: 'Built-in Functions',\n" +
"        title: 'keys() - Object Keys',\n" +
"        description: 'Get all keys from object',\n" +
"        transform: `{\n" +
"  allKeys: keys($.data)\n" +
"}`,\n" +
"        input: `{\n" +
"  \"data\": {\n" +
"    \"name\": \"Alice\",\n" +
"    \"age\": 25,\n" +
"    \"city\": \"NYC\"\n" +
"  }\n" +
"}`\n" +
"    },\n" +
"    values: {\n" +
"        category: 'Built-in Functions',\n" +
"        title: 'values() - Object Values',\n" +
"        description: 'Get all values from object',\n" +
"        transform: `{\n" +
"  allValues: values($.data)\n" +
"}`,\n" +
"        input: `{\n" +
"  \"data\": {\n" +
"    \"name\": \"Bob\",\n" +
"    \"age\": 30,\n" +
"    \"city\": \"LA\"\n" +
"  }\n" +
"}`\n" +
"    },\n" +
"    len: {\n" +
"        category: 'Built-in Functions',\n" +
"        title: 'len() - Length',\n" +
"        description: 'Get length of string or array',\n" +
"        transform: `{\n" +
"  strLen: len($.text),\n" +
"  arrLen: len($.items)\n" +
"}`,\n" +
"        input: `{\n" +
"  \"text\": \"hello\",\n" +
"  \"items\": [1, 2, 3, 4, 5]\n" +
"}`\n" +
"    },\n" +
"    exists: {\n" +
"        category: 'Built-in Functions',\n" +
"        title: 'exists() - Check Property Exists',\n" +
"        description: 'Check if property exists in object',\n" +
"        transform: `{\n" +
"  hasName: exists($.user, \"name\"),\n" +
"  hasAge: exists($.user, \"age\"),\n" +
"  hasPhone: exists($.user, \"phone\")\n" +
"}`,\n" +
"        input: `{\n" +
"  \"user\": {\n" +
"    \"name\": \"Alice\",\n" +
"    \"age\": 25\n" +
"  }\n" +
"}`\n" +
"    },\n" +
"    merge: {\n" +
"        category: 'Built-in Functions',\n" +
"        title: 'merge() - Merge Objects',\n" +
"        description: 'Merge multiple objects',\n" +
"        transform: `{\n" +
"  merged: merge($.obj1, $.obj2, $.obj3)\n" +
"}`,\n" +
"        input: `{\n" +
"  \"obj1\": {\"a\": 1, \"b\": 2},\n" +
"  \"obj2\": {\"c\": 3},\n" +
"  \"obj3\": {\"d\": 4}\n" +
"}`\n" +
"    },\n" +
"    pluck: {\n" +
"        category: 'Built-in Functions',\n" +
"        title: 'pluck() - Extract Property',\n" +
"        description: 'Extract specific property from array of objects',\n" +
"        transform: `{\n" +
"  names: pluck($.users, \"name\"),\n" +
"  ages: pluck($.users, \"age\")\n" +
"}`,\n" +
"        input: `{\n" +
"  \"users\": [\n" +
"    {\"name\": \"Alice\", \"age\": 25},\n" +
"    {\"name\": \"Bob\", \"age\": 30},\n" +
"    {\"name\": \"Charlie\", \"age\": 35}\n" +
"  ]\n" +
"}`\n" +
"    }\n" +
"};\n" +
"\n" +
"let currentExample = null;\n" +
"\n" +
"function openExamplesBrowser() {\n" +
"    document.getElementById('examplesModal').style.display = 'block';\n" +
"    renderExamplesList();\n" +
"}\n" +
"\n" +
"function closeExamplesBrowser() {\n" +
"    document.getElementById('examplesModal').style.display = 'none';\n" +
"}\n" +
"\n" +
"window.onclick = function(event) {\n" +
"    const modal = document.getElementById('examplesModal');\n" +
"    if (event.target === modal) {\n" +
"        closeExamplesBrowser();\n" +
"    }\n" +
"}\n" +
"\n" +
"function renderExamplesList(filter = '') {\n" +
"    const categories = {};\n" +
"    \n" +
"    Object.keys(comprehensiveExamples).forEach(key => {\n" +
"        const ex = comprehensiveExamples[key];\n" +
"        const searchText = (ex.title + ' ' + ex.description + ' ' + key).toLowerCase();\n" +
"        \n" +
"        if (filter === '' || searchText.includes(filter.toLowerCase())) {\n" +
"            if (!categories[ex.category]) {\n" +
"                categories[ex.category] = [];\n" +
"            }\n" +
"            categories[ex.category].push({key, ...ex});\n" +
"        }\n" +
"    });\n" +
"    \n" +
"    let html = '';\n" +
"    Object.keys(categories).sort().forEach(category => {\n" +
"        html += `<div class=\"category-group\">`;\n" +
"        html += `<div class=\"category-title\">${category}</div>`;\n" +
"        categories[category].forEach(ex => {\n" +
"            const active = currentExample === ex.key ? 'active' : '';\n" +
"            html += `<div class=\"example-item ${active}\" onclick=\"selectExample('${ex.key}')\">`;\n" +
"            html += `<div class=\"example-item-title\">${ex.title}</div>`;\n" +
"            html += `<div class=\"example-item-desc\">${ex.description}</div>`;\n" +
"            html += `</div>`;\n" +
"        });\n" +
"        html += `</div>`;\n" +
"    });\n" +
"    \n" +
"    document.getElementById('examplesList').innerHTML = html;\n" +
"}\n" +
"\n" +
"function filterExamples() {\n" +
"    const filter = document.getElementById('exampleSearch').value;\n" +
"    renderExamplesList(filter);\n" +
"}\n" +
"\n" +
"function selectExample(key) {\n" +
"    currentExample = key;\n" +
"    renderExamplesList(document.getElementById('exampleSearch').value);\n" +
"    const ex = comprehensiveExamples[key];\n" +
"    \n" +
"    let html = `<h3>${ex.title}</h3>`;\n" +
"    html += `<p>${ex.description}</p>`;\n" +
"    \n" +
"    html += `<div class=\"example-code-block\">`;\n" +
"    html += `<h4>Transform:</h4>`;\n" +
"    html += `<pre>${escapeHtml(ex.transform)}</pre>`;\n" +
"    html += `</div>`;\n" +
"    \n" +
"    html += `<div class=\"example-code-block\">`;\n" +
"    html += `<h4>Input:</h4>`;\n" +
"    html += `<pre>${escapeHtml(ex.input)}</pre>`;\n" +
"    html += `</div>`;\n" +
"    \n" +
"    html += `<button class=\"load-example-btn\" onclick=\"loadSelectedExample()\">Load This Example</button>`;\n" +
"    \n" +
"    document.getElementById('exampleDetail').innerHTML = html;\n" +
"}\n" +
"\n" +
"function loadSelectedExample() {\n" +
"    if (!currentExample) return;\n" +
"    \n" +
"    const ex = comprehensiveExamples[currentExample];\n" +
"    transformEditor.setValue(ex.transform);\n" +
"    inputEditor.setValue(ex.input);\n" +
"    \n" +
"    closeExamplesBrowser();\n" +
"    transform();\n" +
"    \n" +
"    setStatus('Example loaded: ' + ex.title, 'success');\n" +
"}\n" +
"</script>";
    }
}
