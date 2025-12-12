import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CdkDragDrop, DragDropModule, moveItemInArray } from '@angular/cdk/drag-drop';
import { MorphFunction, JsonInputConfig, JsonMapping, OutputConfig, OutputMapping } from '../../models/morph.model';

@Component({
  selector: 'app-io-configurator',
  standalone: true,
  imports: [CommonModule, FormsModule, DragDropModule],
  templateUrl: './io-configurator.component.html',
  styleUrls: ['./io-configurator.component.scss']
})
export class IoConfiguratorComponent {
  @Input() function!: MorphFunction;
  @Output() functionChange = new EventEmitter<MorphFunction>();

  activeTab: 'input' | 'output' = 'input';
  
  jsonPreview: any = null;
  jsonError: string | null = null;

  ngOnInit() {
    if (!this.function.jsonInput) {
      this.function.jsonInput = {
        enabled: false,
        sampleJson: '{\n  "id": 1,\n  "name": "example",\n  "value": 100\n}',
        mappings: []
      };
    }
    if (!this.function.outputConfig) {
      this.function.outputConfig = {
        enabled: false,
        format: 'json',
        template: '{\n  "result": "{{output}}"\n}',
        mappings: []
      };
    }
    this.parseJsonInput();
  }

  toggleInputEnabled() {
    if (this.function.jsonInput) {
      this.function.jsonInput.enabled = !this.function.jsonInput.enabled;
      this.emitChange();
    }
  }

  toggleOutputEnabled() {
    if (this.function.outputConfig) {
      this.function.outputConfig.enabled = !this.function.outputConfig.enabled;
      this.emitChange();
    }
  }

  parseJsonInput() {
    if (!this.function.jsonInput) return;
    
    try {
      this.jsonPreview = JSON.parse(this.function.jsonInput.sampleJson);
      this.jsonError = null;
    } catch (e: any) {
      this.jsonError = e.message;
      this.jsonPreview = null;
    }
  }

  updateJsonInput(value: string) {
    if (this.function.jsonInput) {
      this.function.jsonInput.sampleJson = value;
      this.parseJsonInput();
      this.emitChange();
    }
  }

  addJsonMapping() {
    if (!this.function.jsonInput) return;

    const newMapping: JsonMapping = {
      id: this.generateId(),
      jsonPath: '$.property',
      parameterName: this.function.parameters[0]?.name || 'param',
      transformExpression: ''
    };
    this.function.jsonInput.mappings.push(newMapping);
    this.emitChange();
  }

  removeJsonMapping(mapping: JsonMapping) {
    if (!this.function.jsonInput) return;

    this.function.jsonInput.mappings = this.function.jsonInput.mappings.filter(m => m.id !== mapping.id);
    this.emitChange();
  }

  addOutputMapping() {
    if (!this.function.outputConfig) return;

    const newMapping: OutputMapping = {
      id: this.generateId(),
      outputPath: '$.result',
      sourceExpression: 'returnValue',
      transformExpression: ''
    };
    this.function.outputConfig.mappings.push(newMapping);
    this.emitChange();
  }

  removeOutputMapping(mapping: OutputMapping) {
    if (!this.function.outputConfig) return;

    this.function.outputConfig.mappings = this.function.outputConfig.mappings.filter(m => m.id !== mapping.id);
    this.emitChange();
  }

  onMappingDrop(event: CdkDragDrop<any[]>) {
    if (event.previousContainer === event.container) {
      moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
    }
    this.emitChange();
  }

  getJsonKeys(): string[] {
    if (!this.jsonPreview) return [];
    return this.flattenJsonKeys(this.jsonPreview, '$');
  }

  private flattenJsonKeys(obj: any, prefix: string): string[] {
    const keys: string[] = [];
    for (const key in obj) {
      const path = `${prefix}.${key}`;
      keys.push(path);
      if (typeof obj[key] === 'object' && obj[key] !== null && !Array.isArray(obj[key])) {
        keys.push(...this.flattenJsonKeys(obj[key], path));
      }
    }
    return keys;
  }

  autoMapJsonToParams() {
    if (!this.function.jsonInput || !this.jsonPreview) return;

    const keys = this.getJsonKeys();
    this.function.jsonInput.mappings = [];

    this.function.parameters.forEach((param, index) => {
      if (index < keys.length) {
        this.function.jsonInput!.mappings.push({
          id: this.generateId(),
          jsonPath: keys[index],
          parameterName: param.name,
          transformExpression: ''
        });
      }
    });
    this.emitChange();
  }

  updateOutputFormat(format: 'json' | 'xml' | 'text' | 'custom') {
    if (!this.function.outputConfig) return;

    this.function.outputConfig.format = format;
    
    switch (format) {
      case 'json':
        this.function.outputConfig.template = '{\n  "result": "{{output}}",\n  "status": "success"\n}';
        break;
      case 'xml':
        this.function.outputConfig.template = '<response>\n  <result>{{output}}</result>\n  <status>success</status>\n</response>';
        break;
      case 'text':
        this.function.outputConfig.template = 'Result: {{output}}';
        break;
      case 'custom':
        this.function.outputConfig.template = '';
        break;
    }
    this.emitChange();
  }

  updateOutputTemplate(value: string) {
    if (this.function.outputConfig) {
      this.function.outputConfig.template = value;
      this.emitChange();
    }
  }

  private generateId(): string {
    return Math.random().toString(36).substr(2, 9);
  }

  emitChange() {
    this.functionChange.emit(this.function);
  }

  testMapping(mapping: JsonMapping) {
    if (!this.jsonPreview) return;
    
    try {
      const value = this.evaluateJsonPath(this.jsonPreview, mapping.jsonPath);
      alert(`Value at ${mapping.jsonPath}: ${JSON.stringify(value)}`);
    } catch (e: any) {
      alert(`Error: ${e.message}`);
    }
  }

  private evaluateJsonPath(obj: any, path: string): any {
    const parts = path.replace(/^\$\./, '').split('.');
    let current = obj;
    for (const part of parts) {
      if (current && typeof current === 'object' && part in current) {
        current = current[part];
      } else {
        throw new Error(`Path not found: ${path}`);
      }
    }
    return current;
  }
}
