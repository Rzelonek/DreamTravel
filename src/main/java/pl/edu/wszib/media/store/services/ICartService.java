package pl.edu.wszib.media.store.services;

public interface ICartService {
    double calculateCartSum();

    void addTripToCart(Long id);
}
