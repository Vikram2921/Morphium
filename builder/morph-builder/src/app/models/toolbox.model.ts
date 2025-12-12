export interface ToolboxCategory {
  id: string;
  name: string;
  icon: string;
  color: string;
  items: ToolboxItem[];
  expanded?: boolean;
}

export interface ToolboxItem {
  id: string;
  name: string;
  icon: string;
  category: string;
  description: string;
  template: string;
  insertType: 'inline' | 'block' | 'function';
  parameters?: ToolboxParameter[];
}

export interface ToolboxParameter {
  name: string;
  type: string;
  default?: string;
  required?: boolean;
}
