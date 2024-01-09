package com.razorpayPaymentgateway.Razorpay.PaymentController;

package com.razorpayPaymentgateway.Razorpay.PaymentRequest;
package com.razorpayPaymentgateway.Razorpay.RazorpayService;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private RazorpayService razorpayService;

    @PostMapping("/initiate")
    public String initiatePayment(@RequestBody PaymentRequest paymentRequest) throws RazorpayException {
        int amount = paymentRequest.getAmount();
        String orderId = paymentRequest.getOrderId();

        return razorpayService.initiatePayment(amount, orderId);
    }
}
