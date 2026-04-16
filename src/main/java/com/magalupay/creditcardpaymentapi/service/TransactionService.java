    package com.magalupay.creditcardpaymentapi.service;

    import com.magalupay.creditcardpaymentapi.model.Transaction;
    import com.magalupay.creditcardpaymentapi.repository.TransactionRepository;
    import org.springframework.stereotype.Service;

    import java.util.List;
    import java.util.Optional;

    @Service
    public class TransactionService {
        private final TransactionRepository transactionRepository;

        public TransactionService(TransactionRepository transactionRepository) {
            this.transactionRepository = transactionRepository;
        }

        public Transaction saveTransaction(Transaction transaction) {
        log.info("Saving transaction: {}", transaction);

            return transactionRepository.save(transaction);
        }

        public List<Transaction> getAllTransactions() {
        log.info("Fetching all transactions");

            return transactionRepository.findAll();
        }

        public Optional<Transaction> getTransactionById(Long id) {
        log.info("Fetching transaction by id: {}", id);

            return transactionRepository.findById(id);
        }

        public void deleteTransaction(Long id) {
        log.info("Deleting transaction by id: {}", id);

            transactionRepository.deleteById(id);
        }
    }
