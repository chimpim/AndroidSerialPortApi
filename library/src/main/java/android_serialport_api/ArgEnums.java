package android_serialport_api;


public interface ArgEnums {

    enum DataBit {
        DATA_BIT_5(-1),
        DATA_BIT_6(1),
        DATA_BIT_7(2),
        DATA_BIT_8(3),;
        public final int val;


        DataBit(int val) {
            this.val = val;
        }
    }

    enum CheckBit {
        CHECK_BIT_NONE(-1),
        CHECK_BIT_ODD(1),
        CHECK_BIT_EVEN(2),
        CHECK_BIT_SPACE(3),;
        public final int val;


        CheckBit(int val) {
            this.val = val;
        }
    }

    enum StopBit {
        STOP_BIT_1(-1),
        STOP_BIT_2(1),;
        public final int val;


        StopBit(int val) {
            this.val = val;
        }
    }
}
