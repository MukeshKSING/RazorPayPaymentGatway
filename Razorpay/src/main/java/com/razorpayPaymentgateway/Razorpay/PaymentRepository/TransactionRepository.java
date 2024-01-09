package com.razorpayPaymentgateway.Razorpay.TransactionRepository;
package com.razorpayPaymentgateway.Razorpay.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
