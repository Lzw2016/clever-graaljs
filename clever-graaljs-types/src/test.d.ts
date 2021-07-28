interface Test {
    a(): void;
}

interface Test {

    b(): void;
    /** 自定义函数 */
    c(): void;
}

declare const Test: Test;