package br.com.stoom.utils;

import java.util.List;
import java.util.function.Function;

public record Pagination<T>(
        List<T> content,
        int currentPage,
        int size,
        long totalElements,
        int totalPages
) {

    public static <R> Pagination<R> with(
            final List<R> aContent,
            final int aCurrentPage,
            final int aSize,
            final long aTotalElements,
            final int aTotalPages
    ) {
        return new Pagination<>(aContent, aCurrentPage, aSize, aTotalElements, aTotalPages);
    }

    public <R> Pagination<R> map(final Function<T, R> mapper) {
        final List<R> aNewList = this.content.stream()
                .map(mapper)
                .toList();

        return new Pagination<>(aNewList, currentPage(), size(), totalElements(), totalPages());
    }

    public static <T>Pagination<T> empty() {
        return new Pagination<>(
                List.of(),
                0,
                0,
                0,
                0
        );
    }

}
