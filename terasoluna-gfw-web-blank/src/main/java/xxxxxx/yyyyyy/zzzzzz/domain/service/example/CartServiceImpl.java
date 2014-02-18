package xxxxxx.yyyyyy.zzzzzz.domain.service.example;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import xxxxxx.yyyyyy.zzzzzz.domain.model.Cart;
import xxxxxx.yyyyyy.zzzzzz.domain.model.CartItem;
import xxxxxx.yyyyyy.zzzzzz.domain.repository.example.CartRepository;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    @Inject
    CartRepository cartRepository;

    public Cart getCart(Integer id) {
        return cartRepository.findOne(id);
    }

    public Cart addCartItem(Cart cart, CartItem cartItem) {
        Cart savedCart = cartRepository.save(cart);
        cartItem.setCart(savedCart);
        savedCart.getCartItems().add(cartItem);
        return savedCart;
    }

    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }

}
