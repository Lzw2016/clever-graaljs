/**
 * Java线程对象
 */
interface JThread extends JObject {
    java_lang_Thread: "java.lang.Thread";

    start(): void;

    run(): void;

    stop(): void;

    interrupt(): void;

    isInterrupted(): JBoolean;

    isAlive(): JBoolean;

    suspend(): void;

    resume(): void;

    setPriority(newPriority: JInt): void;

    getPriority(): JInt;

    setName(name: JString): void;

    getName(): JString;

    countStackFrames(): JInt;

    join(millis: JLong): void;

    join(millis: JLong, nanos: JInt): void;

    join(): void;

    setDaemon(on: JBoolean): void;

    isDaemon(): JBoolean;

    checkAccess(): void;

    getId(): JLong;
}
