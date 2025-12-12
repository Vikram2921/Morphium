import { Injectable } from '@angular/core';
import { ToolboxCategory, ToolboxItem } from '../models/toolbox.model';

@Injectable({
  providedIn: 'root'
})
export class ToolboxService {
  getCategories(): ToolboxCategory[] {
    return [
      {
        id: 'control',
        name: 'Control Flow',
        icon: '🔀',
        color: '#667eea',
        expanded: true,
        items: [
          {
            id: 'if',
            name: 'If Statement',
            icon: '❓',
            category: 'control',
            description: 'Conditional execution',
            template: 'if (condition) {\n    // code\n}',
            insertType: 'block'
          },
          {
            id: 'if-else',
            name: 'If-Else',
            icon: '↔️',
            category: 'control',
            description: 'Conditional with alternative',
            template: 'if (condition) {\n    // if true\n} else {\n    // if false\n}',
            insertType: 'block'
          },
          {
            id: 'if-else-if',
            name: 'If-Else-If',
            icon: '🔄',
            category: 'control',
            description: 'Multiple conditions',
            template: 'if (condition1) {\n    // first\n} else if (condition2) {\n    // second\n} else {\n    // default\n}',
            insertType: 'block'
          },
          {
            id: 'for',
            name: 'For Loop',
            icon: '🔁',
            category: 'control',
            description: 'Iterate with counter',
            template: 'for (let i = 0; i < length; i++) {\n    // loop code\n}',
            insertType: 'block'
          },
          {
            id: 'while',
            name: 'While Loop',
            icon: '⭕',
            category: 'control',
            description: 'Loop while condition true',
            template: 'while (condition) {\n    // loop code\n}',
            insertType: 'block'
          },
          {
            id: 'switch',
            name: 'Switch Case',
            icon: '🎛️',
            category: 'control',
            description: 'Multiple case branches',
            template: 'switch (value) {\n    case option1:\n        // code\n        break;\n    case option2:\n        // code\n        break;\n    default:\n        // default code\n}',
            insertType: 'block'
          },
          {
            id: 'ternary',
            name: 'Ternary Operator',
            icon: '❓',
            category: 'control',
            description: 'Inline conditional',
            template: 'condition ? valueIfTrue : valueIfFalse',
            insertType: 'inline'
          }
        ]
      },
      {
        id: 'variables',
        name: 'Variables & Data',
        icon: '📦',
        color: '#48bb78',
        expanded: false,
        items: [
          {
            id: 'let',
            name: 'Variable (let)',
            icon: '📌',
            category: 'variables',
            description: 'Declare variable',
            template: 'let variableName = value;',
            insertType: 'block'
          },
          {
            id: 'const',
            name: 'Constant',
            icon: '🔒',
            category: 'variables',
            description: 'Declare constant',
            template: 'const CONSTANT_NAME = value;',
            insertType: 'block'
          },
          {
            id: 'object',
            name: 'Object',
            icon: '🗂️',
            category: 'variables',
            description: 'Create object',
            template: 'let obj = {\n    key1: value1,\n    key2: value2\n};',
            insertType: 'block'
          },
          {
            id: 'array',
            name: 'Array',
            icon: '📚',
            category: 'variables',
            description: 'Create array',
            template: 'let arr = [item1, item2, item3];',
            insertType: 'block'
          },
          {
            id: 'destructure',
            name: 'Destructuring',
            icon: '📤',
            category: 'variables',
            description: 'Extract values',
            template: 'let {key1, key2} = object;',
            insertType: 'block'
          }
        ]
      },
      {
        id: 'functions',
        name: 'Functions',
        icon: '⚡',
        color: '#ed8936',
        expanded: false,
        items: [
          {
            id: 'return',
            name: 'Return',
            icon: '↩️',
            category: 'functions',
            description: 'Return value',
            template: 'return value;',
            insertType: 'block'
          },
          {
            id: 'arrow-func',
            name: 'Arrow Function',
            icon: '➡️',
            category: 'functions',
            description: 'Anonymous function',
            template: '(param1, param2) => {\n    // code\n    return result;\n}',
            insertType: 'inline'
          },
          {
            id: 'callback',
            name: 'Callback',
            icon: '📞',
            category: 'functions',
            description: 'Function callback',
            template: 'function callback(param) {\n    // code\n}',
            insertType: 'block'
          }
        ]
      },
      {
        id: 'arrays',
        name: 'Array Operations',
        icon: '🔢',
        color: '#9f7aea',
        expanded: false,
        items: [
          {
            id: 'map',
            name: 'map()',
            icon: '🗺️',
            category: 'arrays',
            description: 'Transform array',
            template: 'map(array, "item", expression)',
            insertType: 'inline'
          },
          {
            id: 'filter',
            name: 'filter()',
            icon: '🔍',
            category: 'arrays',
            description: 'Filter array',
            template: 'filter(array, "item", condition)',
            insertType: 'inline'
          },
          {
            id: 'reduce',
            name: 'reduce()',
            icon: '➖',
            category: 'arrays',
            description: 'Reduce to value',
            template: 'reduce(array, "acc", "item", expression, initialValue)',
            insertType: 'inline'
          },
          {
            id: 'foreach',
            name: 'forEach()',
            icon: '👉',
            category: 'arrays',
            description: 'Iterate array',
            template: 'forEach(array, "item", expression)',
            insertType: 'inline'
          },
          {
            id: 'find',
            name: 'find()',
            icon: '🎯',
            category: 'arrays',
            description: 'Find element',
            template: 'find(array, "item", condition)',
            insertType: 'inline'
          },
          {
            id: 'sort',
            name: 'sort()',
            icon: '↕️',
            category: 'arrays',
            description: 'Sort array',
            template: 'sort(array, "a", "b", comparator)',
            insertType: 'inline'
          },
          {
            id: 'concat',
            name: 'concat()',
            icon: '➕',
            category: 'arrays',
            description: 'Combine arrays',
            template: 'concat(array1, array2)',
            insertType: 'inline'
          },
          {
            id: 'slice',
            name: 'slice()',
            icon: '✂️',
            category: 'arrays',
            description: 'Extract portion',
            template: 'slice(array, start, end)',
            insertType: 'inline'
          },
          {
            id: 'distinct',
            name: 'distinct()',
            icon: '✨',
            category: 'arrays',
            description: 'Remove duplicates',
            template: 'distinct(array)',
            insertType: 'inline'
          }
        ]
      },
      {
        id: 'string',
        name: 'String Operations',
        icon: '📝',
        color: '#38b2ac',
        expanded: false,
        items: [
          {
            id: 'concat-str',
            name: 'Concatenate',
            icon: '➕',
            category: 'string',
            description: 'Join strings',
            template: 'concat(str1, str2)',
            insertType: 'inline'
          },
          {
            id: 'substring',
            name: 'substring()',
            icon: '✂️',
            category: 'string',
            description: 'Extract substring',
            template: 'substring(str, start, length)',
            insertType: 'inline'
          },
          {
            id: 'uppercase',
            name: 'toUpperCase()',
            icon: '⬆️',
            category: 'string',
            description: 'Convert to uppercase',
            template: 'toUpperCase(str)',
            insertType: 'inline'
          },
          {
            id: 'lowercase',
            name: 'toLowerCase()',
            icon: '⬇️',
            category: 'string',
            description: 'Convert to lowercase',
            template: 'toLowerCase(str)',
            insertType: 'inline'
          },
          {
            id: 'split',
            name: 'split()',
            icon: '✂️',
            category: 'string',
            description: 'Split string',
            template: 'split(str, delimiter)',
            insertType: 'inline'
          },
          {
            id: 'replace',
            name: 'replace()',
            icon: '🔄',
            category: 'string',
            description: 'Replace text',
            template: 'replace(str, search, replacement)',
            insertType: 'inline'
          },
          {
            id: 'trim',
            name: 'trim()',
            icon: '✂️',
            category: 'string',
            description: 'Remove whitespace',
            template: 'trim(str)',
            insertType: 'inline'
          },
          {
            id: 'length',
            name: 'length()',
            icon: '📏',
            category: 'string',
            description: 'Get length',
            template: 'length(str)',
            insertType: 'inline'
          }
        ]
      },
      {
        id: 'math',
        name: 'Math & Numbers',
        icon: '🔢',
        color: '#f56565',
        expanded: false,
        items: [
          {
            id: 'add',
            name: 'Addition',
            icon: '➕',
            category: 'math',
            description: 'Add numbers',
            template: 'a + b',
            insertType: 'inline'
          },
          {
            id: 'subtract',
            name: 'Subtraction',
            icon: '➖',
            category: 'math',
            description: 'Subtract numbers',
            template: 'a - b',
            insertType: 'inline'
          },
          {
            id: 'multiply',
            name: 'Multiplication',
            icon: '✖️',
            category: 'math',
            description: 'Multiply numbers',
            template: 'a * b',
            insertType: 'inline'
          },
          {
            id: 'divide',
            name: 'Division',
            icon: '➗',
            category: 'math',
            description: 'Divide numbers',
            template: 'a / b',
            insertType: 'inline'
          },
          {
            id: 'modulo',
            name: 'Modulo',
            icon: '%',
            category: 'math',
            description: 'Remainder',
            template: 'a % b',
            insertType: 'inline'
          },
          {
            id: 'round',
            name: 'round()',
            icon: '🔘',
            category: 'math',
            description: 'Round number',
            template: 'round(number, decimals)',
            insertType: 'inline'
          },
          {
            id: 'max',
            name: 'max()',
            icon: '⬆️',
            category: 'math',
            description: 'Maximum value',
            template: 'max(array)',
            insertType: 'inline'
          },
          {
            id: 'min',
            name: 'min()',
            icon: '⬇️',
            category: 'math',
            description: 'Minimum value',
            template: 'min(array)',
            insertType: 'inline'
          },
          {
            id: 'abs',
            name: 'abs()',
            icon: '|',
            category: 'math',
            description: 'Absolute value',
            template: 'abs(number)',
            insertType: 'inline'
          },
          {
            id: 'sum',
            name: 'sum()',
            icon: '➕',
            category: 'math',
            description: 'Sum array',
            template: 'sum(array)',
            insertType: 'inline'
          }
        ]
      },
      {
        id: 'comparison',
        name: 'Comparisons',
        icon: '⚖️',
        color: '#ed64a6',
        expanded: false,
        items: [
          {
            id: 'equals',
            name: 'Equals',
            icon: '=',
            category: 'comparison',
            description: 'Check equality',
            template: 'a == b',
            insertType: 'inline'
          },
          {
            id: 'not-equals',
            name: 'Not Equals',
            icon: '≠',
            category: 'comparison',
            description: 'Check inequality',
            template: 'a != b',
            insertType: 'inline'
          },
          {
            id: 'greater',
            name: 'Greater Than',
            icon: '>',
            category: 'comparison',
            description: 'Check if greater',
            template: 'a > b',
            insertType: 'inline'
          },
          {
            id: 'less',
            name: 'Less Than',
            icon: '<',
            category: 'comparison',
            description: 'Check if less',
            template: 'a < b',
            insertType: 'inline'
          },
          {
            id: 'gte',
            name: 'Greater or Equal',
            icon: '≥',
            category: 'comparison',
            description: 'Check >=',
            template: 'a >= b',
            insertType: 'inline'
          },
          {
            id: 'lte',
            name: 'Less or Equal',
            icon: '≤',
            category: 'comparison',
            description: 'Check <=',
            template: 'a <= b',
            insertType: 'inline'
          }
        ]
      },
      {
        id: 'logical',
        name: 'Logical Operators',
        icon: '🔗',
        color: '#4299e1',
        expanded: false,
        items: [
          {
            id: 'and',
            name: 'AND',
            icon: '&',
            category: 'logical',
            description: 'Logical AND',
            template: 'condition1 && condition2',
            insertType: 'inline'
          },
          {
            id: 'or',
            name: 'OR',
            icon: '|',
            category: 'logical',
            description: 'Logical OR',
            template: 'condition1 || condition2',
            insertType: 'inline'
          },
          {
            id: 'not',
            name: 'NOT',
            icon: '!',
            category: 'logical',
            description: 'Logical NOT',
            template: '!condition',
            insertType: 'inline'
          }
        ]
      },
      {
        id: 'utility',
        name: 'Utility Functions',
        icon: '🛠️',
        color: '#718096',
        expanded: false,
        items: [
          {
            id: 'exists',
            name: 'exists()',
            icon: '✅',
            category: 'utility',
            description: 'Check if exists',
            template: 'exists(value)',
            insertType: 'inline'
          },
          {
            id: 'isEmpty',
            name: 'isEmpty()',
            icon: '❌',
            category: 'utility',
            description: 'Check if empty',
            template: 'isEmpty(array)',
            insertType: 'inline'
          },
          {
            id: 'len',
            name: 'len()',
            icon: '📏',
            category: 'utility',
            description: 'Get length',
            template: 'len(array)',
            insertType: 'inline'
          },
          {
            id: 'contains',
            name: 'contains()',
            icon: '🔍',
            category: 'utility',
            description: 'Check if contains',
            template: 'contains(array, item)',
            insertType: 'inline'
          },
          {
            id: 'groupBy',
            name: 'groupBy()',
            icon: '📊',
            category: 'utility',
            description: 'Group by key',
            template: 'groupBy(array, keyExpression)',
            insertType: 'inline'
          },
          {
            id: 'limit',
            name: 'limit()',
            icon: '🎚️',
            category: 'utility',
            description: 'Limit array size',
            template: 'limit(array, count)',
            insertType: 'inline'
          },
          {
            id: 'skip',
            name: 'skip()',
            icon: '⏭️',
            category: 'utility',
            description: 'Skip elements',
            template: 'skip(array, count)',
            insertType: 'inline'
          }
        ]
      },
      {
        id: 'comments',
        name: 'Comments & Docs',
        icon: '💬',
        color: '#a0aec0',
        expanded: false,
        items: [
          {
            id: 'comment-line',
            name: 'Line Comment',
            icon: '///',
            category: 'comments',
            description: 'Single line comment',
            template: '// comment',
            insertType: 'block'
          },
          {
            id: 'comment-block',
            name: 'Block Comment',
            icon: '/* */',
            category: 'comments',
            description: 'Multi-line comment',
            template: '/*\n * comment\n */',
            insertType: 'block'
          },
          {
            id: 'todo',
            name: 'TODO',
            icon: '📌',
            category: 'comments',
            description: 'TODO marker',
            template: '// TODO: task description',
            insertType: 'block'
          }
        ]
      }
    ];
  }

  searchItems(query: string, categories: ToolboxCategory[]): ToolboxItem[] {
    const lowerQuery = query.toLowerCase();
    const results: ToolboxItem[] = [];

    categories.forEach(category => {
      category.items.forEach(item => {
        if (
          item.name.toLowerCase().includes(lowerQuery) ||
          item.description.toLowerCase().includes(lowerQuery) ||
          item.category.toLowerCase().includes(lowerQuery)
        ) {
          results.push(item);
        }
      });
    });

    return results;
  }
}
