package com.tix.concur;

public class FlowBuilderTest {

//    class TestFlow extends AbstractFlow<String> {
//
//        public TestFlow(String getId) {
//            super(getId);
//        }
//
//        @Override
//        public void createNew(FlowBuilder<String> flowBuilder) {
//            flowBuilder.map(this::mapToInt)
//                    .mapSynchronized(this::mapToString)
//                    .bind(this::bindToInt)
//                    .forEach(this::onInt)
//                    .map(this::mapToString)
//                    .catching(this::catchAll)
//            ;
//        }
//
//        private String catchAll(Throwable throwable) throws Throwable {
//            if (throwable instanceof NumberFormatException) {
//                return "0";
//            } else {
//                throw throwable;
//            }
//        }
//
//        private void onInt(Integer integer) {
//
//        }
//
//        private void bindToInt(String s, Continuation<Integer> continuation) throws Throwable {
//            continuation.continuing(() -> 1);
//        }
//
//        private int mapToInt(String s) {
//            return 1;
//        }
//
//        private String mapToString(int i) {
//            return "";
//        }
//    }

}
