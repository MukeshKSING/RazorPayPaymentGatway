package com.razorpayPaymentgateway.Razorpay.RazorpayService;

import com.razorpay.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
package com.razorpayPaymentgateway.Razorpay.PaymentRequest;
package com.razorpayPaymentgateway.Razorpay.Transaction;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class RazorpayService {

    @Value("${razorpay.api.key}")
    private String apiKey;

    @Value("${razorpay.api.secret}")
    private String apiSecret;

    @Autowired
    private TransactionRepository transactionRepository;

    public String initiatePayment(int amount, String orderId) throws RazorpayException {
        RazorpayClient razorpayClient = new RazorpayClient(apiKey, apiSecret);

        Map<String, Object> params = new HashMap<>();
        params.put("amount", amount * 100);
        params.put("currency", "INR");
        params.put("receipt", orderId);
        params.put("payment_capture", 1);

        Order order = razorpayClient.Orders.create(params);

        return order.toString();
    }

    public void handlePaymentSuccess(String orderId, String paymentId) {
        if (isPaymentSuccessful(paymentId)) {
            saveTransaction(orderId, paymentId, "Successful");
        } else {
           
            saveTransaction(orderId, paymentId, "Verification Failed");
        }
    }

    private boolean isPaymentSuccessful(String paymentId) {
        try {
          
            RazorpayClient razorpayClient = new RazorpayClient(apiKey, apiSecret);

            Payment payment = razorpayClient.Payments.fetch(paymentId);

           
            return "captured".equals(payment.get("status"));
        } catch (RazorpayException e) {
           
            e.printStackTrace();
            return false;
        }
    }

    private void saveTransaction(String orderId, String paymentId, String status) {
        Transaction transaction = new Transaction();
        transaction.setOrderId(orderId);
        transaction.setPaymentId(paymentId);
        transaction.setStatus(status);
        transaction.setTimestamp(new Date());

        transactionRepository.save(transaction);
    }
}
