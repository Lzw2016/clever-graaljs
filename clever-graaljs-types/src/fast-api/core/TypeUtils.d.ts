declare const isString = (str: any): str is string => {
    return typeof str === 'string';
}

declare const isNumber = (num: any): num is number => {
    return typeof num === 'number';
}

declare const isUndefined = (un: any): un is undefined => {
    return typeof un === 'undefined';
}

declare const isObject = (obj: any): obj is object => {
    return typeof obj === 'object';
}

declare const isBoolean = (b: any): b is boolean => {
    return typeof b === 'boolean';
}

declare const isFunction = (fuc: any): fuc is Function => {
    return typeof fuc === 'function';
}

declare const isBigint = (big: any): big is bigint => {
    return typeof big === 'bigint';
}

declare const isSymbol = (sym: any): sym is symbol => {
    return typeof sym === 'symbol';
}
