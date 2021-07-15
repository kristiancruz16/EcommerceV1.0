package com.springboot.ecommercev1.controllers;


import com.springboot.ecommercev1.domain.*;
import com.springboot.ecommercev1.services.*;
import org.dom4j.rule.Mode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static com.springboot.ecommercev1.domain.OrderStatus.*;

/**
 * @author KMCruz
 * 6/17/2021
 */
@Controller
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final ShoppingCartLineItemService shoppingCartLineItemService;
    private final CustomerService customerService;
    private final CustomerOrderService customerOrderService;


    public ShoppingCartController(ShoppingCartLineItemService shoppingCartLineItemService,
                                  CustomerService customerService, CustomerOrderService customerOrderService) {
        this.shoppingCartLineItemService = shoppingCartLineItemService;
        this.customerService = customerService;
        this.customerOrderService = customerOrderService;
    }



    @GetMapping
    public String showShoppingCart(Model model, Principal principal) {
        Customer customer = customerService.findLoggedInCustomer(principal.getName());
        ShoppingCart shoppingCart = customer.getShoppingCart();
        model.addAttribute("cartLineItems", shoppingCart.getShoppingCartList());
        return "cart/shoppingCart";
    }

    @PostMapping
    public ModelAndView checkOutShoppingCart(ModelMap model, Principal principal){
        Customer customer = customerService.findLoggedInCustomer(principal.getName());
        ShoppingCart shoppingCart = customer.getShoppingCart();
        CustomerOrder customerOrderToSave = customerOrderService.save(new CustomerOrder());
        List<OrderLineItem> orderLineItem = new ArrayList<>();

        shoppingCart.getShoppingCartList().stream()
                .forEach(cartItem->{
                    OrderLineItem orderItem = new OrderLineItem();
                    OrderLineItemKey key = new OrderLineItemKey();
                    key.setProductId(cartItem.getProduct().getId());
                    key.setCustomerOrderId(customerOrderToSave.getId());
                    orderItem.setId(key);
                    orderItem.setCustomerOrder(customerOrderToSave);
                    orderItem.setProduct(cartItem.getProduct());
                    orderItem.setQuantity(cartItem.getQuantity());
                    orderItem.setLineAmount(cartItem.getLineAmount());
                    orderLineItem.add(orderItem);
                });
        customerOrderToSave.setCustomer(customer);
        customerOrderToSave.setOrderStatus(PENDING);
        customerOrderToSave.setOrderItems(orderLineItem);
        CustomerOrder savedCustomerOrder = customerOrderService.save(customerOrderToSave);
        model.addAttribute("orderNo",savedCustomerOrder.getId());

        return new ModelAndView("redirect:/shoppingCart/checkOut",model);
    }

    @GetMapping("/checkOut")
    public ModelAndView showCheckOutForm(ModelMap model,@RequestParam Long orderNo){
        CustomerOrder customerOrder = customerOrderService.findById(orderNo);
        LOGGER.info("Customer Id No.: "+customerOrder.getId());
        model.addAttribute("order",customerOrder);
        model.addAttribute("creditCardInfo", new CreditCardInfo());
        model.addAttribute("address", new Address());
        return new ModelAndView("cart/checkOut",model);
    }
    @PostMapping("/checkOut")
    public ModelAndView processCheckOutForm(ModelMap model, @RequestParam Long orderNo,
                                            @Valid CreditCardInfo creditCardInfo, BindingResult ccErrors,
                                            @Valid Address address, BindingResult addressErrors){

        CustomerOrder customerOrder = customerOrderService.findById(orderNo);
        if(ccErrors.hasFieldErrors()||addressErrors.hasFieldErrors()){
            model.addAttribute("order",customerOrder);
            model.addAttribute("creditCardInfo",creditCardInfo);
            model.addAttribute("address",address);
            return new ModelAndView("cart/checkOut",model);
        }

        Payment payment = new Payment();
        payment.setCreditCardInfo(creditCardInfo);
        creditCardInfo.setPayment(payment);
        customerOrder.setAddress(address);
        address.setOrder(customerOrder);
        customerOrder.getOrderItems().stream().forEach(lineItem->{
            payment.setPaymentAmount(lineItem.getLineAmount()+ payment.getPaymentAmount());
        });
        payment.setOrder(customerOrder);
        customerOrder.setPayment(payment);
        customerOrderService.save(customerOrder);
        LOGGER.info("Customer Id: "+customerOrder.getId());
        return new ModelAndView("redirect:/shoppingCart/checkOut/success");
    }
    @GetMapping("/checkOut/success")
    public ModelAndView showCheckOutSuccessForm(){
        return new ModelAndView("cart/success");
    }

    @PostMapping("/add")
    public String addLineItemQuantity(@RequestParam Long productId, Principal principal){
        Customer customer = customerService.findLoggedInCustomer(principal.getName());
        ShoppingCart shoppingCart = customer.getShoppingCart();
        ShoppingCartLineItemKey key = ShoppingCartLineItemKey.builder()
                .shoppingCartId(shoppingCart.getId()).productId(productId).build();
        ShoppingCartLineItem cartLineItem = shoppingCartLineItemService.findByID(key);
        ShoppingCartLineItem cartLineItemToSave = cartLineItem.addCartLineItem();
        shoppingCartLineItemService.save(cartLineItemToSave);
        return "redirect:/shoppingCart";
    }

    @PostMapping("/delete")
    public String deleteLineItemQuantity(@RequestParam Long productId, Principal principal){
        Customer customer = customerService.findLoggedInCustomer(principal.getName());
        ShoppingCart shoppingCart = customer.getShoppingCart();
        ShoppingCartLineItemKey key = ShoppingCartLineItemKey.builder()
                .shoppingCartId(shoppingCart.getId()).productId(productId).build();
        ShoppingCartLineItem cartLineItem = shoppingCartLineItemService.findByID(key);
        Integer lineItemQuantity = cartLineItem.getQuantity() - 1;
        cartLineItem.setQuantity(lineItemQuantity);

        if (lineItemQuantity==0)
            shoppingCartLineItemService.delete(cartLineItem);
        else
            shoppingCartLineItemService.save(cartLineItem);

        return "redirect:/shoppingCart";
    }

    @PostMapping("/deleteAll")
    public String deleteAllLineItems (Principal principal) {
        Customer customer = customerService.findLoggedInCustomer(principal.getName());
        ShoppingCart shoppingCart = customer.getShoppingCart();
        shoppingCart.getShoppingCartList().stream()
                .forEach(shoppingCartLineItemService::delete);
        return "redirect:/shoppingCart";
    }

}
