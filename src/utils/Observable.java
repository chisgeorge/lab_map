package utils;
public interface Observable<E> {
    /**
     * Register an observer.
     * @param observer the observer
     */
    void addObserver(Observer<E> observer);
    /**
     * Unregister an observer.
     * @param observer the observer
     */
    void removeObserver(Observer<E> observer);


    void notifyObservers(ListEvent<E> event);
}
