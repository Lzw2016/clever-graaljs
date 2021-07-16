package org.clever.graaljs.core.constant;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/07/16 17:22 <br/>
 */
public interface BarcodeFormatEnum {
    final class BarcodeFormat {
        public static final BarcodeFormat Instance = new BarcodeFormat();

        private BarcodeFormat() {
        }

        /**
         * Aztec 2D barcode format.
         */
        public final String AZTEC = "AZTEC";

        /**
         * CODABAR 1D format.
         */
        public final String CODABAR = "CODABAR";

        /**
         * Code 39 1D format.
         */
        public final String CODE_39 = "CODE_39";

        /**
         * Code 93 1D format.
         */
        public final String CODE_93 = "CODE_93";

        /**
         * Code 128 1D format.
         */
        public final String CODE_128 = "CODE_128";

        /**
         * Data Matrix 2D barcode format.
         */
        public final String DATA_MATRIX = "DATA_MATRIX";

        /**
         * EAN-8 1D format.
         */
        public final String EAN_8 = "EAN_8";

        /**
         * EAN-13 1D format.
         */
        public final String EAN_13 = "EAN_13";

        /**
         * ITF (Interleaved Two of Five) 1D format.
         */
        public final String ITF = "ITF";

        /**
         * MaxiCode 2D barcode format.
         */
        public final String MAXICODE = "MAXICODE";

        /**
         * PDF417 format.
         */
        public final String PDF_417 = "PDF_417";

        /**
         * QR Code 2D barcode format.
         */
        public final String QR_CODE = "QR_CODE";

        /**
         * RSS 14
         */
        public final String RSS_14 = "RSS_14";

        /**
         * RSS EXPANDED
         */
        public final String RSS_EXPANDED = "RSS_EXPANDED";

        /**
         * UPC-A 1D format.
         */
        public final String UPC_A = "UPC_A";

        /**
         * UPC-E 1D format.
         */
        public final String UPC_E = "UPC_E";

        /**
         * UPC/EAN extension format. Not a stand-alone format.
         */
        public final String UPC_EAN_EXTENSION = "UPC_EAN_EXTENSION";
    }
}
