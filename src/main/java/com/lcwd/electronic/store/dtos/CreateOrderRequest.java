package com.lcwd.electronic.store.dtos;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class CreateOrderRequest {
    @NotBlank(message = "cart id is required!!")
    private String cartId;
    @NotBlank(message = "user id is required!!")
    private String userId;


    //pending,delivered,dispatched
    private String orderStatus="PENDING";
    //not paid,paid
    private String paymentStatus="NOTPAID";


    @NotBlank(message = "address is required!!")

    private String billingAddress;
    @NotBlank(message = "phone is required!!")
    private String billingPhone;
    @NotBlank(message = " Billing name is required!!")
    private String billingName;




}
