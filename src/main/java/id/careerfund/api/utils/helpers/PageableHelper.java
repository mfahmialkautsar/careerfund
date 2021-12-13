package id.careerfund.api.utils.helpers;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Objects;

public final class PageableHelper {
    public static Pageable getPageable(String sort, String order) {
        Sort sortOrder = null;
        if (sort != null) {
            if (Objects.equals(order.toLowerCase(), "desc")) {
                sortOrder = Sort.by(sort).descending();
            } else if (Objects.equals(order.toLowerCase(), "asc")) {
                sortOrder = Sort.by(sort).ascending();
            }
        }

        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        if (sortOrder != null) {
            pageable = PageRequest.of(0, Integer.MAX_VALUE, sortOrder);
        }
        return pageable;
    }
}
