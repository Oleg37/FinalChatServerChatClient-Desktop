package viewmodel;

import lombok.experimental.Delegate;
import model.Repository;

public class ViewModel {

    @Delegate
    private final Repository repository;

    public ViewModel() {
        repository = new Repository();
    }
}
