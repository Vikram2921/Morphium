import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CdkDragDrop, DragDropModule, moveItemInArray } from '@angular/cdk/drag-drop';
import { MorphService } from '../../services/morph.service';
import { ToolboxService } from '../../services/toolbox.service';
import { MorphFile, MorphFunction, MorphElement } from '../../models/morph.model';
import { ToolboxCategory, ToolboxItem } from '../../models/toolbox.model';
import { IoConfiguratorComponent } from '../io-configurator/io-configurator.component';

@Component({
  selector: 'app-morph-builder',
  standalone: true,
  imports: [CommonModule, FormsModule, DragDropModule, IoConfiguratorComponent],
  templateUrl: './morph-builder.component.html',
  styleUrls: ['./morph-builder.component.scss']
})
export class MorphBuilderComponent {
  morphFile: MorphFile = {
    name: 'untitled.morph',
    functions: []
  };

  selectedFunction: MorphFunction | null = null;
  codePreview: string = '';
  showIoConfig: boolean = false;
  ioConfigFunction: MorphFunction | null = null;

  toolboxCategories: ToolboxCategory[] = [];
  searchQuery: string = '';
  searchResults: ToolboxItem[] = [];
  allCollapsed: boolean = false;
  draggedItem: ToolboxItem | null = null;

  availableElements: MorphElement[] = [
    { type: 'function', label: 'New Function', icon: '⚡', template: 'function newFunction() {\n    \n}' },
    { type: 'if', label: 'If Statement', icon: '❓', template: 'if (condition) {\n    \n}' },
    { type: 'return', label: 'Return', icon: '↩️', template: 'return value;' },
    { type: 'variable', label: 'Variable', icon: '📦', template: 'let variable = value;' },
    { type: 'expression', label: 'Expression', icon: '➕', template: 'expression' }
  ];

  constructor(
    private morphService: MorphService,
    private toolboxService: ToolboxService
  ) {
    this.toolboxCategories = this.toolboxService.getCategories();
    this.updatePreview();
  }

  onFileSelected(event: any) {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = (e: any) => {
        const content = e.target.result;
        this.morphFile = this.morphService.parseFile(content);
        this.morphFile.name = file.name;
        this.updatePreview();
      };
      reader.readAsText(file);
    }
  }

  addNewFunction() {
    const newFunc = this.morphService.createNewFunction();
    this.morphFile.functions.push(newFunc);
    this.selectedFunction = newFunc;
    this.updatePreview();
  }

  selectFunction(func: MorphFunction) {
    this.selectedFunction = func;
  }

  deleteFunction(func: MorphFunction) {
    this.morphFile.functions = this.morphFile.functions.filter(f => f.id !== func.id);
    if (this.selectedFunction?.id === func.id) {
      this.selectedFunction = null;
    }
    this.updatePreview();
  }

  updateFunctionName(func: MorphFunction, newName: string) {
    func.name = newName;
    this.updatePreview();
  }

  updateFunctionBody(func: MorphFunction, newBody: string) {
    func.body = newBody;
    this.updatePreview();
  }

  updateParameters(func: MorphFunction, paramsString: string) {
    func.parameters = paramsString
      .split(',')
      .map(p => p.trim())
      .filter(p => p)
      .map(p => ({ name: p }));
    this.updatePreview();
  }

  getParametersString(func: MorphFunction): string {
    return func.parameters.map(p => p.name).join(', ');
  }

  drop(event: CdkDragDrop<MorphFunction[]>) {
    moveItemInArray(this.morphFile.functions, event.previousIndex, event.currentIndex);
    this.updatePreview();
  }

  updatePreview() {
    this.codePreview = this.morphService.generateCode(this.morphFile);
  }

  exportFile() {
    const blob = this.morphService.exportFile(this.morphFile);
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = this.morphFile.name;
    a.click();
    window.URL.revokeObjectURL(url);
  }

  newFile() {
    if (confirm('Create a new file? Unsaved changes will be lost.')) {
      this.morphFile = {
        name: 'untitled.morph',
        functions: []
      };
      this.selectedFunction = null;
      this.updatePreview();
    }
  }

  copyCode() {
    navigator.clipboard.writeText(this.codePreview);
  }

  toggleIoConfig(func: MorphFunction) {
    this.showIoConfig = true;
    this.ioConfigFunction = func;
  }

  closeIoConfig() {
    this.showIoConfig = false;
    this.ioConfigFunction = null;
  }

  onIoConfigChange(updatedFunction: MorphFunction) {
    const index = this.morphFile.functions.findIndex(f => f.id === updatedFunction.id);
    if (index !== -1) {
      this.morphFile.functions[index] = updatedFunction;
      this.updatePreview();
    }
  }

  // Toolbox methods
  toggleCategory(category: ToolboxCategory) {
    category.expanded = !category.expanded;
  }

  collapseAllCategories() {
    this.allCollapsed = !this.allCollapsed;
    this.toolboxCategories.forEach(cat => cat.expanded = !this.allCollapsed);
  }

  onSearchChange() {
    if (this.searchQuery.trim()) {
      this.searchResults = this.toolboxService.searchItems(this.searchQuery, this.toolboxCategories);
    } else {
      this.searchResults = [];
    }
  }

  clearSearch() {
    this.searchQuery = '';
    this.searchResults = [];
  }

  onDragStart(event: DragEvent, item: ToolboxItem) {
    this.draggedItem = item;
    if (event.dataTransfer) {
      event.dataTransfer.effectAllowed = 'copy';
      event.dataTransfer.setData('text/plain', item.template);
    }
  }

  onDragEnd(event: DragEvent) {
    this.draggedItem = null;
  }

  onDragOver(event: DragEvent) {
    event.preventDefault();
    if (event.dataTransfer) {
      event.dataTransfer.dropEffect = 'copy';
    }
    const target = event.target as HTMLElement;
    if (target.classList.contains('body-textarea')) {
      target.classList.add('drag-over');
    }
  }

  onDragLeave(event: DragEvent) {
    const target = event.target as HTMLElement;
    if (target.classList.contains('body-textarea')) {
      target.classList.remove('drag-over');
    }
  }

  onDropInBody(event: DragEvent, func: MorphFunction, textarea: HTMLTextAreaElement) {
    event.preventDefault();
    const target = event.target as HTMLElement;
    target.classList.remove('drag-over');

    if (this.draggedItem) {
      const template = this.draggedItem.template;
      const cursorPos = textarea.selectionStart;
      const textBefore = func.body.substring(0, cursorPos);
      const textAfter = func.body.substring(cursorPos);
      
      // Add proper indentation
      const lines = textBefore.split('\n');
      const lastLine = lines[lines.length - 1];
      const indent = lastLine.match(/^\s*/)?.[0] || '    ';
      
      // Format template with indentation
      const formattedTemplate = template.split('\n').map((line, index) => {
        return index === 0 ? line : indent + line;
      }).join('\n');
      
      func.body = textBefore + '\n' + indent + formattedTemplate + '\n' + textAfter;
      this.updateFunctionBody(func, func.body);
      
      // Set cursor position after inserted text
      setTimeout(() => {
        const newPos = cursorPos + formattedTemplate.length + indent.length + 2;
        textarea.setSelectionRange(newPos, newPos);
        textarea.focus();
      }, 0);
    }
  }
}
