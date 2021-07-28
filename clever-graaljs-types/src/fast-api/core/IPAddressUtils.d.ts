interface IPAddressUtils {
    /**
     * 获取本机所有的IPv4地址,已经获取过就直接返回
     */
    getInet4Address(): JSet<JString>;

    /**
     * 重新获取本机所有的IPv4地址
     */
    refreshInet4Address(): JSet<JString>;

    /**
     * 获取本机所有的IPv4地址,有多个用“;”分隔
     *
     * @return 失败返回"未知"
     */
    getInet4AddressStr(): JString;
}

declare const IPAddressUtils: IPAddressUtils;
