package ObjectsForMapper.GIOSData;


    public class GIOSData {
        private Values[] values;

        private String key;

        public Values[] getValues() {
            return values;
        }

        public void setValues(Values[] values) {
            this.values = values;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        @Override
        public String toString() {
            return "ClassPojo [values = " + values + ", key = " + key + "]";
        }
    }

