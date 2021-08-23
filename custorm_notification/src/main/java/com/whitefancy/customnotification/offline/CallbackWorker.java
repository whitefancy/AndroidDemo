package com.whitefancy.customnotification.offline;

//如果您的工作停止会发生什么？如果预计工作会停止，则始终会取消 ListenableWorker 的 ListenableFuture。通过使用 CallbackToFutureAdapter，您只需添加一个取消监听器即可，如下所示：

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.ListenableWorker;
import androidx.work.WorkerParameters;

import com.google.common.util.concurrent.ListenableFuture;

public class CallbackWorker extends ListenableWorker {
    public CallbackWorker(Context context, WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public ListenableFuture<Result> startWork() {
//        return CallbackToFutureAdapter.getFuture(completer -> {
//            Callback callback = new Callback() {
//                int successes = 0;
//
//                @Override
//                public void onFailure(Call call, IOException e) {
//                    completer.setException(e);
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) {
//                    ++successes;
//                    if (successes == 100) {
//                        completer.set(Result.success());
//                    }
//                }
//            };
//            completer.addCancellationListener(cancelDownloadsRunnable, executor);
//            for (int i = 0; i < 100; ++i) {
//                downloadAsynchronously("https://www.example.com", callback);
//            }
//            return callback;
//        });
        return null;
    }
}