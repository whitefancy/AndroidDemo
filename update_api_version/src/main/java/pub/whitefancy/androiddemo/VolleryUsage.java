package pub.whitefancy.androiddemo;

public class VolleryUsage {
}
//Android-Volley网络连接
//表单请求
//StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        JSONObject res = null;
//                        try {
//                            res = new JSONObject(response);
//                            if ((2 == res.getInt("code") || 4 == res.getInt("code")) && !purchase.isAcknowledged()) {
//                                Log.i(PAY_TAG, "delivered");
//                                ConsumeParams consumeParams =
//                                        ConsumeParams.newBuilder()
//                                                .setPurchaseToken(purchase.getPurchaseToken())
//                                                .build();
//                                ConsumeResponseListener listener = new ConsumeResponseListener() {
//                                    @Override
//                                    public void onConsumeResponse(BillingResult billingResult, String purchaseToken) {
//                                        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
//                                            // Handle the success of the consume operation.
//                                            new Thread(new Runnable() {
//                                                @Override
//                                                public void run() {
//                                                    PlatformOnPayEnd(1, purchase.getSku());
//                                                    AppFlyesHelper.getInstance().trackPurchase(purchase.getOrderId(), "USD", purchase.getSku());
//                                                    db.orderDao().delete(finalOrder);
//                                                }
//                                            }).start();
//                                        } else if (BillingClient.BillingResponseCode.ITEM_NOT_OWNED == billingResult.getResponseCode()) {
//                                        }
//                                    }
//                                };
//                                billingClient.consumeAsync(consumeParams, listener);
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.w(PAY_TAG, error.toString());
//            }
//        }) {
//            @Override
//            public String getBodyContentType() {
//                return "application/x-www-form-urlencoded";
//            }
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                return finalOrder.getMap();
//            }
//        };
//        // Add the request to the RequestQueue.
//        queue.add(stringRequest);
//Json请求
//queue = Volley.newRequestQueue(mActivity);
//        JSONObject payData = new JSONObject();
//        try {
//            payData.put("statusUpdateNotification", inAppPurchaseData);
//            payData.put("notifycationSignature", InAppSignature);
//            payData.put("playerId", PlatformHelper.getInstance().getAccount());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        String URL = "http://p20cn.jedi-games.com/p20gate/pay/huawei_core_callback.php";
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
//                (Request.Method.POST, URL, payData, new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject ret) {
//                        Log.i(TAG, ret.toString());
//                        try {
//                            if (ret.has("code") && ("1" == ret.getString("code") || "1002" == ret.getString("code"))) {
//                                consumePurchase();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.w(TAG, "deliver error" + error.toString());
//                    }
//                }) {
//            @Override
//            public String getBodyContentType() {
//                return "application/json";
//            }
//        };
//        // Access the RequestQueue through your singleton class.
//        queue.add(jsonObjectRequest);
//String请求
//StringRequest request = new StringRequest(Request.Method.POST, url,
//        new Response.Listener<String>() {
//            public void onResponse(String response) {
//            }
//        },
//        new Response.ErrorListener() {
//            public void onErrorResponse(VolleyError error) {
//            }
//        }
//    ) {
//        public byte[] getBody() {
//            return new JSONObject(params).toString().getBytes();
//        }
//        public String getBodyContentType() {
//            return "application/json";
//        }
//};