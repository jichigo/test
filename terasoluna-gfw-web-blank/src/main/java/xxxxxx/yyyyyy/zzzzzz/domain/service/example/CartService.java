package xxxxxx.yyyyyy.zzzzzz.domain.service.example;

import xxxxxx.yyyyyy.zzzzzz.domain.model.Cart;
import xxxxxx.yyyyyy.zzzzzz.domain.model.CartItem;

public interface CartService {

    Cart getCart(Integer id);

    Cart addCartItem(Cart cart, CartItem cartItem);

    Cart saveCart(Cart cart);

}
