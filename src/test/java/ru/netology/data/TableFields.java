package ru.netology.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableFields {
    private String id;
    private int amount;
    private String bank_id;
    private Timestamp created;
    private String credit_id;
    private String payment_id;
    private String status;
    private String transaction_id;
}
