//package com.yfy.app.gRPC;
//
//import android.app.Activity;
//import android.content.Context;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.text.method.ScrollingMovementMethod;
//import android.view.View;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import com.yfy.base.R;
//import com.yfy.base.activity.BaseActivity;
//import com.yfy.final_tag.stringtool.Logger;
//
//import java.io.PrintWriter;
//import java.io.StringWriter;
//import java.lang.ref.WeakReference;
//import java.util.concurrent.TimeUnit;
//
//import butterknife.OnClick;
//import io.g.rpc.GreeterGrpc;
//import io.g.rpc.HelloReply;
//import io.g.rpc.HelloRequest;
//import io.grpc.ManagedChannel;
//import io.grpc.ManagedChannelBuilder;
//import rx.Observable;
//import rx.functions.Action1;
//
//
//public class GRpcMainActivity extends BaseActivity {
//    public Button sendButton;
//    public EditText hostEdit;
//    public EditText portEdit;
//    public EditText messageEdit;
//    public TextView resultText;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_helloworld);
//        sendButton =  findViewById(R.id.send_button);
//        hostEdit =  findViewById(R.id.host_edit_text);
//        portEdit =  findViewById(R.id.port_edit_text);
//        messageEdit =  findViewById(R.id.message_edit_text);
//        resultText =  findViewById(R.id.grpc_response_text);
//        resultText.setMovementMethod(new ScrollingMovementMethod());
//    }
//
//    public void sendMessage(View view) {
//        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(hostEdit.getWindowToken(), 0);
//        sendButton.setEnabled(false);
//        resultText.setText("");
//        new GrpcTask(this).execute(hostEdit.getText().toString(), messageEdit.getText().toString(), portEdit.getText().toString());
//    }
//
//
//
//
//    @OnClick(R.id.send_button)
//    void setSendButton(View view){
//        sendMessage(view);
//    }
//
//
//
//    private static class GrpcTask extends AsyncTask<String, Void, String> {
//        private final WeakReference<Activity> activityReference;
//        private ManagedChannel channel;
//
//        private GrpcTask(Activity activity) {
//            this.activityReference = new WeakReference<>(activity);
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            String host = params[0];
//            String message = params[1];
//            String portStr = params[2];
//            int port = TextUtils.isEmpty(portStr) ? 0 : Integer.valueOf(portStr);
//
//            try {
//                channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
//                GreeterGrpc.GreeterBlockingStub stub = GreeterGrpc.newBlockingStub(channel);
//
//                HelloRequest request = HelloRequest.newBuilder().setGetname("").build();
//                HelloReply reply = stub.sayHello(request);
//                return reply.getMessage();
//            } catch (Exception e) {
//                StringWriter sw = new StringWriter();
//                PrintWriter pw = new PrintWriter(sw);
//                e.printStackTrace(pw);
//                pw.flush();
//                return String.format("Failed... : %n%s", sw);
//            }
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            try {
//                channel.shutdown().awaitTermination(1, TimeUnit.SECONDS);
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }
//            Activity activity = activityReference.get();
//            if (activity == null) {
//                return;
//            }
//            TextView resultText =  activity.findViewById(R.id.grpc_response_text);
//            Button sendButton =  activity.findViewById(R.id.send_button);
//            Logger.e(result);
//            resultText.setText(result);
//            sendButton.setEnabled(true);
//        }
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//
////
////
////    public static String bytesToString(ByteString src, String charSet) {
////        if(StringUtils.isEmpty(charSet)) {
////            charSet = "GB2312";
////        }
////        return bytesToString(src.toByteArray(), charSet);
////    }
////
////    public static String bytesToString(byte[] input, String charSet) {
////        if(ArrayUtils.isEmpty(input)) {
////            return StringUtils.EMPTY;
////        }
////
////        ByteBuffer buffer = ByteBuffer.allocate(input.length);
////        buffer.put(input);
////        buffer.flip();
////
////        Charset charset = null;
////        CharsetDecoder decoder = null;
////        CharBuffer charBuffer = null;
////
////        try {
////            charset = Charset.forName(charSet);
////            decoder = charset.newDecoder();
////            charBuffer = decoder.decode(buffer.asReadOnlyBuffer());
////
////            return charBuffer.toString();
////        } catch (Exception ex) {
////            ex.printStackTrace();
////        }
////    }
//}
