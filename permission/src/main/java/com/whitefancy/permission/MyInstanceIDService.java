package com.whitefancy.permission;

//public class MyInstanceIDService extends InstanceIDListenerService {
//    public void onTokenRefresh() {
//        refreshAllTokens();
//    }
//
//    //刷新令牌
////实例ID服务会定期（例如，每6个月一次）启动回调，以请求您的应用刷新其令牌。在以下情况下，它也可能会启动回调：
////存在安全问题；例如SSL或平台问题。
////设备信息不再有效；例如备份和还原。
////否则实例ID服务会受到影响。
////在您的应用中实现实例ID侦听器服务以接收以下回调：
////您还必须在项目的清单文件中配置此服务：
//    private void refreshAllTokens() {
//        // assuming you have defined TokenList as
//        // some generalized store for your tokens
//        ArrayList<TokenList> tokenList = TokensList.get();
//        InstanceID iid = InstanceID.getInstance(this);
//        for (tokenItem:
//             tokenList) {
//            tokenItem.token =
//                    iid.getToken(tokenItem.authorizedEntity, tokenItem.scope, tokenItem.options);
//            // send this tokenItem.token to your server
//        }
//    }
//};
