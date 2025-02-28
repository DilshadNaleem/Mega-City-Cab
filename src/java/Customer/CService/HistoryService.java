package Customer.CService;

import java.util.List;

public class HistoryService {
    private final HistoryRepository historyRepository;

    public HistoryService(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    public List<OrderHistory> getHistoryByEmail(String email) throws Exception {
        return historyRepository.fetchHistoryByEmail(email);
    }
}
