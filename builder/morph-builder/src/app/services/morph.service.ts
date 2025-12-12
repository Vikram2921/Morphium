import { Injectable } from '@angular/core';
import { MorphFile, MorphFunction } from '../models/morph.model';

@Injectable({
  providedIn: 'root'
})
export class MorphService {
  private currentFile: MorphFile | null = null;

  parseFile(content: string): MorphFile {
    const functions: MorphFunction[] = [];
    const functionRegex = /function\s+(\w+)\s*\(([^)]*)\)\s*\{([^}]*(?:\{[^}]*\}[^}]*)*)\}/gs;
    let match;

    while ((match = functionRegex.exec(content)) !== null) {
      const name = match[1];
      const params = match[2].trim();
      const body = match[3].trim();
      
      const parameters = params
        ? params.split(',').map(p => ({
            name: p.trim(),
            type: undefined
          }))
        : [];

      functions.push({
        id: this.generateId(),
        name,
        parameters,
        body
      });
    }

    return {
      name: 'untitled.morph',
      functions
    };
  }

  generateCode(file: MorphFile): string {
    return file.functions
      .map(func => {
        const params = func.parameters.map(p => p.name).join(', ');
        return `function ${func.name}(${params}) {\n${this.indentCode(func.body)}\n}`;
      })
      .join('\n\n');
  }

  private indentCode(code: string): string {
    return code.split('\n').map(line => '    ' + line).join('\n');
  }

  private generateId(): string {
    return Math.random().toString(36).substr(2, 9);
  }

  setCurrentFile(file: MorphFile) {
    this.currentFile = file;
  }

  getCurrentFile(): MorphFile | null {
    return this.currentFile;
  }

  createNewFunction(): MorphFunction {
    return {
      id: this.generateId(),
      name: 'newFunction',
      parameters: [],
      body: '    return null;'
    };
  }

  exportFile(file: MorphFile): Blob {
    const content = this.generateCode(file);
    return new Blob([content], { type: 'text/plain' });
  }
}
