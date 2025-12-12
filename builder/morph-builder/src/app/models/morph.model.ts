export interface MorphFunction {
  id: string;
  name: string;
  parameters: MorphParameter[];
  returnType?: string;
  body: string;
  position?: { x: number; y: number };
  jsonInput?: JsonInputConfig;
  outputConfig?: OutputConfig;
}

export interface MorphParameter {
  name: string;
  type?: string;
}

export interface MorphFile {
  name: string;
  functions: MorphFunction[];
}

export interface MorphElement {
  type: 'function' | 'if' | 'return' | 'variable' | 'expression';
  label: string;
  icon?: string;
  template?: string;
}

export interface JsonInputConfig {
  enabled: boolean;
  sampleJson: string;
  mappings: JsonMapping[];
}

export interface JsonMapping {
  id: string;
  jsonPath: string;
  parameterName: string;
  transformExpression?: string;
}

export interface OutputConfig {
  enabled: boolean;
  format: 'json' | 'xml' | 'text' | 'custom';
  template?: string;
  mappings: OutputMapping[];
}

export interface OutputMapping {
  id: string;
  outputPath: string;
  sourceExpression: string;
  transformExpression?: string;
}
